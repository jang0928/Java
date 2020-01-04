<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<h2>게시글 삭제</h2>
<form action = "BoardDeleteProcCon.do" method="post">
	<table width="600" border="1">

	<tr height= "40">
		<td align="center" width="120">패스워드</td>
		<td width="480" colspan="3"><input type ="password" name="password" >
		</td> 
	</tr>
	<tr height="40">
		<td align="center" colspan="4">
		<input type="hidden" name="num" value="${bean.num }">
		<input type="hidden" name="pass" value="${bean.password }">
		<input type="submit" value="글삭제">&nbsp;&nbsp;
		<input type="button" onclick="location.href='BoardListCon.do'" value="전체글보기">
		</td>
		</tr>
	</table>
	
	
	</form>

</body>
</html>