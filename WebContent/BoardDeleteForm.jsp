<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<h2>�Խñ� ����</h2>
<form action = "BoardDeleteProcCon.do" method="post">
	<table width="600" border="1">

	<tr height= "40">
		<td align="center" width="120">�н�����</td>
		<td width="480" colspan="3"><input type ="password" name="password" >
		</td> 
	</tr>
	<tr height="40">
		<td align="center" colspan="4">
		<input type="hidden" name="num" value="${bean.num }">
		<input type="hidden" name="pass" value="${bean.password }">
		<input type="submit" value="�ۻ���">&nbsp;&nbsp;
		<input type="button" onclick="location.href='BoardListCon.do'" value="��ü�ۺ���">
		</td>
		</tr>
	</table>
	
	
	</form>

</body>
</html>