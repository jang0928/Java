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
		//ȭ�鿡 ������ �Խñ� ��������
		int pageSize=10;
		//���� ���̴� �������� ���ڰ� �о�帲
		String pageNum= request.getParameter("pageNum");
		//Null ó��
		if(pageNum==null) {
			pageNum="1";
		}
		//��ü �Խñ��� ����
		int count=0;
		//jsp ���������� ������ �ѹ� ���ڰ� ���庯��
		int number=0;
		
		int currentPage = Integer.parseInt(pageNum);
		//��ü ���ñ��� ������ �����;��ϴϱ� ��� ��ü
		BoardDAO dao = new BoardDAO();
		count= dao.getAllCount();
		
		//���� �������� ������ ���� ��ȣ ����
		int startRow=(currentPage-1)*pageSize+1;
		//�������� ���� ������ ��ȣ
		int endRow=currentPage*pageSize;
		
		//�ֽű� 10 �� �������� �Խñ� ���� �޾��ִ� �޼ҵ�

		Vector<BoardBean> v = dao.getAllBoard(startRow,endRow);
		number = count -(currentPage-1);
		
		//����, ������ ��й�ȣ Ʋ���� ��
		
		String msg= (String) request.getAttribute("msg");
		//jsp ������ request ��ü�� ��Ƽ� �Ѱ��ش�
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
