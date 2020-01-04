package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardDAO;

/**
 * Servlet implementation class BoardUpdateProcCon
 */
@WebServlet("/BoardUpdateProcCon.do")
public class BoardUpdateProcCon extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request,response);
	}
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		//폼에서 넘어온 데이터를 받아줌
		int num=Integer.parseInt(request.getParameter("num"));
		String password = request.getParameter("password");//사용자로부터 입력받은 패스워드
		String pass = request.getParameter("pass");//실제  db에있는 패스워드값
		String subject = request.getParameter("subject");
		String content= request.getParameter("content");
		//pass 와 password 값을 비교
		
		if(password.equals(pass)) {//패스워드 같다면 데이터 수정
			BoardDAO dao = new BoardDAO();
			dao.updateBoard(num,subject,content);
			//수정 완료 되엇으면 전체게시글 보여주기
			request.setAttribute("msg", "수정이 완료되었습니다.");
			RequestDispatcher dis = request.getRequestDispatcher("BoardListCon.do");
			dis.forward(request, response);
		}else {
			//패스워드 틀렸다면 이전 페이지로 이동
			request.setAttribute("msg", "1");
			RequestDispatcher dis = request.getRequestDispatcher("BoardListCon.do");
			dis.forward(request, response);
		}
	}

}
