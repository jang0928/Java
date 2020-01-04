package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardBean;
import model.BoardDAO;

/**
 * Servlet implementation class BoardUpdateCon
 */
@WebServlet("/BoardUpdateCon.do")
public class BoardUpdateCon extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	reqPro(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request,response);
	}
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//해당 번호를 읽어드림
		 int num= Integer.parseInt(request.getParameter("num"));
		 BoardDAO dao = new BoardDAO();
		 BoardBean bean = dao.getoneUpdateBoard(num);
		 
		 request.setAttribute("bean", bean);
		 RequestDispatcher dis = request.getRequestDispatcher("BoardUpdateForm.jsp");
		 dis.forward(request, response);
	}

}
