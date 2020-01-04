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
		
		//������ �Ѿ�� �����͸� �޾���
		int num=Integer.parseInt(request.getParameter("num"));
		String password = request.getParameter("password");//����ڷκ��� �Է¹��� �н�����
		String pass = request.getParameter("pass");//����  db���ִ� �н����尪
		String subject = request.getParameter("subject");
		String content= request.getParameter("content");
		//pass �� password ���� ��
		
		if(password.equals(pass)) {//�н����� ���ٸ� ������ ����
			BoardDAO dao = new BoardDAO();
			dao.updateBoard(num,subject,content);
			//���� �Ϸ� �Ǿ����� ��ü�Խñ� �����ֱ�
			request.setAttribute("msg", "������ �Ϸ�Ǿ����ϴ�.");
			RequestDispatcher dis = request.getRequestDispatcher("BoardListCon.do");
			dis.forward(request, response);
		}else {
			//�н����� Ʋ�ȴٸ� ���� �������� �̵�
			request.setAttribute("msg", "1");
			RequestDispatcher dis = request.getRequestDispatcher("BoardListCon.do");
			dis.forward(request, response);
		}
	}

}
