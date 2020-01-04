<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<c:if test = "${msg==1}">
   <script type="text/javascript">
      alter("수정 비밀번호가 틀렸습니다");
   </script>
</c:if>
<c:if test = "${msg==2}">
   <script type="text/javascript">
      alter("삭제 비밀번호가 틀렸습니다");
   </script>
</c:if>
	<h2>전체글보기</h2>
	<table width="700" border="1">
		<tr height = "40" align = "right">
			<td colspan = "5" align="right">
			<button onclick="location.href='BoardWriteForm.jsp'">글쓰기</button>
			</td>
		</tr>
		<tr height = "40">
			<td width="50" align="center">번호</td>
			<td width="320" align="center">제목</td>
			<td width="100" align="center">작성자</td>
			<td width="150" align="center">작성일</td>
			<td width="80" align="center">조회수></td>
		</tr>
		<c:set var= "number" value ="${number }"/>
		<c:forEach var="bean" items="${v}">
		
		<tr height = "40">
			<td width="50" align="Left">${number }</td>
			<td width="320" align="Left">
				<c:if test="${bean.re_step > 1 }">
					<c:forEach var="j" begin="1" end="${(bean.re_step-1)*5}">
						&nbsp;
					</c:forEach>
				</c:if>
				<a href="BoardInfoControl.do?num=${bean.num }"> ${bean.subject } ${bean.num }</a>
			</td>
			<td width="100" align="Left">${bean.writer }</td>
			<td width="150" align="Left">${bean.reg_date }</td>
			<td width="80" align="Left">${bean.readcount  }></td>
		</tr>
		<c:set var ="number" value="${number-1}"/>
		</c:forEach>
	</table>
	<p>
	<!-- 페이지 카운터링 -->
	<c:if test="${count >0 }">
		<c:set var="pageCount" value="${count/pageSize+(count%pageSize==0 ? 0 : 1 )}"/>
		<c:set var= "startPage" value="${1 }"/>
		<c:if test="${currnetPage %10 !=0 }">
			<!-- 결과를 정수형으로 리턴받음 -->
			<fmt:parseNumber var= "result" value="${currentPage/10 }" integerOnly="true"/>
			<c:set var="startPage" value="${result*10+1 }"/>
		</c:if>
		
		<c:if test="${currnetPage %10 ==0 }">
			<!-- 결과를 정수형으로 리턴받음 -->
			<c:set var="startPage" value="${(result-1)*10+1 }"/>
		</c:if>
		<c:set var="startPage" value="${1 }"/>
		<!-- 화면에 보여질 페이지 처리 숫자 표현 -->
		<c:set var= "pageBlock" value="${10 }"/>
		<c:set var= "endPage" value="${startPage+pageBlock-1 }"/>
		<c:if test="${endPage>pageCount }">
			<c:set var="endPage" value="${pageCount }"/>
		</c:if>
		<!-- 이전링크 걸지 파악 -->
		<c:if test="${startPage>10 }">
			<a href="BoardListCon.do?pageNum=${startPage-10 }">[이전으로] </a>		
		</c:if>
		<!-- 페이징 처리 -->
		<c:forEach var="i" begin="${startPage}" end="${endPage}">
			<a href="BoardListCon.do?pageNum=${i }">[${i }]</a>
			
		</c:forEach>
		<!-- 다음  -->
		<c:if test="${endPage<pageCount }">
		<a href="BoardListCon.do?pageNum=${startPage+10 }">[다음으로]</a>		
		</c:if>
	</c:if>
</body>
</html>