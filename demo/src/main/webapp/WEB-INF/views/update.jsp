<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layoutTag" tagdir="/WEB-INF/tags"%> <!-- �߰� -->
<layoutTag:layout>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update</title>
</head>
<body>
<div class="container">
    <div class="col-xs-12" style="margin:15px auto;">
        <label style="font-size:20px;"><span class="glyphicon glyphicon-edit"></span>�Խñ� ����</label>
    </div>
 
    <div class="col-xs-12">
        <form action="/updateProc" method="post">
          <div class="form-group">
            <label for="subject">����</label>
            <input type="text" class="form-control" id="subject" name="subject" value="${detail.subject}">
          </div>
          <div class="form-group">
            <label for="content">����</label>
            <textarea class="form-control" id="content" name="content" rows="3">${detail.content}</textarea>
          </div>
          <input type="hidden" name="bno" value="${detail.bno}"/>
          <button type="submit" class="btn btn-primary btn-sm" style="float:right;">����</button>
        </form>
    </div>
</div>

</body>
</html>
</layoutTag:layout>