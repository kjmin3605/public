package com.example.demo.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.board.domain.BoardVO;
import com.example.demo.board.domain.FileVO;
import com.example.demo.board.service.BoardService;

@Controller
public class BoardController {
	@Value("${file.upload.directory}")
	String uploadFileDir;
	
	@Resource(name="com.example.demo.board.service.BoardService")
	BoardService mBoardService;
	
	@RequestMapping("/list")//개시판 리스트 호출
	public String boardList(Model model) throws Exception{
		
		model.addAttribute("list", mBoardService.boardListService());
		
		return "list";//생성할 jsp
	}
	
	@RequestMapping("/detail/{bno}")//bno기준
	public String boardDetail(@PathVariable int bno, Model model) throws Exception{
		
		model.addAttribute("detail", mBoardService.boardDetailService(bno));
		model.addAttribute("files", mBoardService.fileDetailService(bno));//추가
		
		return "detail";
	}
	
	@RequestMapping("/insert")
	public String boardInsertForm() {
		
		return "insert";
	}
	
	@RequestMapping("/insertProc")
	public String boardInsertProc(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception{
		
		BoardVO board = new BoardVO();
		FileVO file = new FileVO();
		
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		board.setWriter(request.getParameter("writer"));
		board.setName(request.getParameter("name"));
		
		if(files.isEmpty()) {//업로드한 파일이 없을시
			mBoardService.boardInsertService(board);//게시글 insert
		}else {
		String fileName = files.getOriginalFilename();
		String sourceFileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
		File destinationFile;
		String destinationFileName;
		//String fileUrl = "C:/Users/BEST/Documents/workspace-sts-3.9.4.RELEASE/demo/src/main/webapp/WEB-INF/uploadFiles/";
		
		/*스프링부트에서 multipart 요청을 처리하려면 multipartConfingElement, multipartResolver 빈이 
		애플리케이션 컨텍스트에 존재해야 하는데 애플리케이션 시작 시 MultipartAutoConfiguration 클래스가 이 작업을 자동으로 수행해줍니다.*/

		/*MultipartFile.transferTo() 는 요청 시점의 임시 파일을 로컬 파일 시스템에 영구적으로 복사해줍니다. 
		단 한번만 실행되며 두번째 실행부터는 성공을 보장할 수 없습니다.*/

		do {
			destinationFileName = RandomStringUtils.randomAlphanumeric(32)+""+sourceFileNameExtension;
			destinationFile = new File(uploadFileDir + destinationFileName);
		} while (destinationFile.exists());
		
		destinationFile.getParentFile().mkdirs();
		files.transferTo(destinationFile);
		
		mBoardService.boardInsertService(board);//게시글 insert
		
		file.setBno(board.getBno());
		file.setFileName(destinationFileName);
		file.setFileOriName(fileName);
		file.setFileUrl(uploadFileDir);
		
		mBoardService.fileInsertService(file);
		}
		
		return "redirect:/list";
	}
	
	@RequestMapping("/update/{bno}")//개시글 수정폼 호출
	public String boardUpdate(@PathVariable int bno, Model model) throws Exception{
		
		model.addAttribute("detail",mBoardService.boardDetailService(bno));
		
		return "update";
	}
	
	@RequestMapping("/updateProc")
	public String boadUpdateProc(HttpServletRequest request) throws Exception{
		
		BoardVO board = new BoardVO();
		
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		board.setBno(Integer.parseInt(request.getParameter("bno")));
		
		mBoardService.boardUpdateService(board);
		
		return "redirect:/detail/"+request.getParameter("bno");
	}
	
	@RequestMapping("/delete/{bno}")
	public String boardDelete(@PathVariable int bno) throws Exception{
		
		mBoardService.boardDeleteService(bno);
		
		return "redirect:/list";
	}
	
	@RequestMapping("/fileDown/{bno}")
    private void fileDown(@PathVariable int bno, HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        request.setCharacterEncoding("UTF-8");
        FileVO fileVO = mBoardService.fileDetailService(bno);
        
        //파일 업로드된 경로 
        try{
            String fileUrl = fileVO.getFileUrl();
            fileUrl += "/";
            String savePath = fileUrl;
            String fileName = fileVO.getFileName();
            
            //실제 내보낼 파일명 
            String oriFileName = fileVO.getFileOriName();
            InputStream in = null;
            OutputStream os = null;
            File file = null;
            boolean skip = false;
            String client = "";
            
            //파일을 읽어 스트림에 담기  
            try{
                file = new File(savePath, fileName);
                in = new FileInputStream(file);
            } catch (FileNotFoundException fe) {
                skip = true;
            }
            
            client = request.getHeader("User-Agent");
            
            //파일 다운로드 헤더 지정 
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description", "JSP Generated Data");
            
            if (!skip) {
                // IE
                if (client.indexOf("MSIE") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                    // IE 11 이상.
                } else if (client.indexOf("Trident") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else {
                    // 한글 파일명 처리
                    response.setHeader("Content-Disposition",
                            "attachment; filename=\"" + new String(oriFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }
                response.setHeader("Content-Length", "" + file.length());
                os = response.getOutputStream();
                byte b[] = new byte[(int) file.length()];
                int leng = 0;
                while ((leng = in.read(b)) > 0) {
                    os.write(b, 0, leng);
                }
            } else {
                response.setContentType("text/html;charset=UTF-8");
                System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
            }
            in.close();
            os.close();
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
        
    }
}





















