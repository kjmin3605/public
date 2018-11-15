package com.example.demo.board.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.demo.board.domain.BoardVO;
import com.example.demo.board.domain.FileVO;
import com.example.demo.board.mapper.BoardMapper;

@Service("com.example.demo.board.service.BoardService")
public class BoardService {
	
	@Resource(name="com.example.demo.board.mapper.BoardMapper")
	BoardMapper mBoardMapper;
	
	//개시글 목록
	public List<BoardVO> boardListService() throws Exception{
		
		return mBoardMapper.boardList();
	}
	//개시글 상세 기능
	public BoardVO boardDetailService(int bno) throws Exception{
		
		return mBoardMapper.boardDetail(bno);
	}
	
	//개시글 등록
	public int boardInsertService(BoardVO board) throws Exception{
		
		return mBoardMapper.boardInsert(board);
	}
	
	//개시글 업데이트
	public int boardUpdateService(BoardVO board) throws Exception{
		
		return mBoardMapper.boardUpdate(board);
	}
	
	//개시글 삭제
	public int boardDeleteService(int bno) throws Exception{
		
		return mBoardMapper.boardDelete(bno);
	}
	
	//파일 업로드
	public int fileInsertService(FileVO file) throws Exception{
		
		return mBoardMapper.fileInsert(file);
	}
	
	//파일 상세
	public FileVO fileDetailService(int bno) throws Exception{
        
	    return mBoardMapper.fileDetail(bno);
	}
}











