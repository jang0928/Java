package controller;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardBean;
import model.BoardDAO;

/**
 * Servlet implementation class BoardListCon
 */
@WebServlet("/BoardListCon.do")
public class BoardListCon extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request,response);
	}
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//화면에 보여질 게시글 개수지정
		int pageSize=10;
		//현재 보이는 페이지의 숫자값 읽어드림
		String pageNum= request.getParameter("pageNum");
		//Null 처리
		if(pageNum==null) {
			pageNum="1";
		}
		//전체 게시글의 갯수
		int count=0;
		//jsp 페이지내에 보여질 넘버 숫자값 저장변수
		int number=0;
		
		int currentPage = Integer.parseInt(pageNum);
		//전체 개시글의 갯수를 가져와야하니까 디비 객체
		BoardDAO dao = new BoardDAO();
		count= dao.getAllCount();
		
		//현재 보여지는 페이지 시작 번호 설정
		int startRow=(currentPage-1)*pageSize+1;
		//마지막에 보일 페이지 번호
		int endRow=currentPage*pageSize;
		
		//최신글 10 개 기준으로 게시글 리턴 받아주는 메소드

		Vector<BoardBean> v = dao.getAllBoard(startRow,endRow);
		number = count -(currentPage-1);
		
		//수정, 삭제시 비밀번호 틀렷을 때
		
		String msg= (String) request.getAttribute("msg");
		//jsp 쪽으로 request 객체에 담아서 넘겨준다
		request.setAttribute("v", v);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("msg", msg);
	
		
		RequestDispatcher dis = request.getRequestDispatcher("BoardList.jsp");
		dis.forward(request, response);
		
	}

}
