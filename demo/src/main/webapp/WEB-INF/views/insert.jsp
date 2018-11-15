<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="layoutTag" tagdir="/WEB-INF/tags"%>
<!-- 추가 -->
<layoutTag:layout>


	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert Form</title>
</head>
<body>
	<div class="container">
		<div class="col-xs-12" style="margin: 15px auto;">
			<label style="font-size: 20px"><span
				class="glyphicon glyphicon-edit"></span>게시글 작성</label>
		</div>

		<div class="col-xs-12">
			<form action="/insertProc" method="post" enctype="multipart/form-data"><!-- 추가 -->
				<!--  파일 업로드를 할 경우 http 요청시 헤더의 Content-Type을 multipart/form-data 이어야 하기때문에 form의 enctype을 추가-->
				<div class="form-group">
					<label for="named">이름</label> <input type="text"
						class="form-control" id="name" name="name" placeholder="이름입력">
				</div>

				<div class="form-group">
					<label for="subject">제목</label> <input type="text" class="form-control" id="subject" name="subject" placeholder="제목입력">
				</div>
				<div class="form-group">
					<label for="writer">작성자</label> <input type="text" class="form-control" id="writer" name="writer" palaceholder="내용입력">
				</div>
				<div class="form-group">
					<label for="content">내용</label>
					<textarea rows="3" class="form-control" id="content" name="content"></textarea>
				</div>

				<div class="form-group">
            		<label for="file">파일 업로드</label>
           			<input type="file" id="file" name="files">
          		</div>
				<!-- 추가 -->

				<button type="submit" class="btn btn-primary">작성</button>
			</form>
		</div>
	</div>
	<%-- <%@ include file="bootstrap.jsp" %> 제거--%>
</body>
	</html>
</layoutTag:layout>