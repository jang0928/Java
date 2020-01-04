<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
     <%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<form action = "BoardUpdateProcCon.do" method="post">
	<table width="600" border="1">
	<tr height= "40">
		<td align="center" width="120">작성자</td>
		<td align="center" width="180">${bean.writer}</td>
		<td align="center" width="120">작성일</td>
		<td align="center" width="180">${bean.reg_date }</td>
	</tr>
	
	<tr height= "40">
		<td align="center" width="120">제목</td>
		<td width="480" colspan="3"><input type ="text" name="subject" value="${bean.subject }">
		</td> 
	</tr>
	<tr height= "40">
		<td align="center" width="120">패스워드</td>
		<td width="480" colspan="3"><input type ="password" name="password" >
		</td> 
	</tr>
	
	<tr height= "40">
		<td align="center" width="120">글내용</td>
		<td width="480" colspan="3"><textarea rows="10" cols="60" name="content" align ="left">${bean.content }</textarea>
		</td> 
	</tr>
	<tr height="40">
		<td align="center" colspan="4">
		<input type="hidden" name="num" value="${bean.num }">
		<input type="hidden" name="pass" value="${bean.password }">
		<input type="submit" value="글수정">&nbsp;&nbsp;
		<input type="reset" value="취소">&nbsp;&nbsp;
		<input type="button" onclick="location.href='BoardListCon.do'" value="전체글보기">
		</td>
		</tr>
	</table>
	
	
	</form>
</body>
</html>