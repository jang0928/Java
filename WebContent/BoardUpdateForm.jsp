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
		<td align="center" width="120">�ۼ���</td>
		<td align="center" width="180">${bean.writer}</td>
		<td align="center" width="120">�ۼ���</td>
		<td align="center" width="180">${bean.reg_date }</td>
	</tr>
	
	<tr height= "40">
		<td align="center" width="120">����</td>
		<td width="480" colspan="3"><input type ="text" name="subject" value="${bean.subject }">
		</td> 
	</tr>
	<tr height= "40">
		<td align="center" width="120">�н�����</td>
		<td width="480" colspan="3"><input type ="password" name="password" >
		</td> 
	</tr>
	
	<tr height= "40">
		<td align="center" width="120">�۳���</td>
		<td width="480" colspan="3"><textarea rows="10" cols="60" name="content" align ="left">${bean.content }</textarea>
		</td> 
	</tr>
	<tr height="40">
		<td align="center" colspan="4">
		<input type="hidden" name="num" value="${bean.num }">
		<input type="hidden" name="pass" value="${bean.password }">
		<input type="submit" value="�ۼ���">&nbsp;&nbsp;
		<input type="reset" value="���">&nbsp;&nbsp;
		<input type="button" onclick="location.href='BoardListCon.do'" value="��ü�ۺ���">
		</td>
		</tr>
	</table>
	
	
	</form>
</body>
</html>