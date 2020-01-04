package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	//������ ���̽� ���� �޼���
	public void getCon() {
		try {
			Context initctx = new InitialContext();
			Context envcts = (Context)initctx.lookup("java:comp/env");
			DataSource ds =(DataSource)envcts.lookup("jdbc/pool");
			con=ds.getConnection();//Ŀ�ؼ� ���� �޼ҵ�
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	//��ü �� ���� ����
	public int getAllCount() {
		getCon();// �����ͺ��̽� ����
		int count = 0;
		try {
			String sql= "select count(*) from board ";
			pstmt = con.prepareStatement(sql);
			//���� ���� �� ���Ϲޱ�
			rs= pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);//��ü �Խñۼ�
			}
			con.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return count;
	}
	//ȭ�鿡 ������ ������ 10���� �̾Ƽ� ���� �޼ҵ�
	public Vector<BoardBean> getAllBoard(int startRow, int endRow){
		Vector<BoardBean> v= new Vector<>();
		
		getCon();
		try {
			String sql= "select  * from (Select A.* , Rownum Rnum from(select * from board order by ref desc ,re_step asc)A)"
					+"where Rnum>= ? and Rnum<=?";
			pstmt = con.prepareStatement(sql);
			//?���¤� ����
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			//���� ���� �� ���Ϲޱ�
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_data(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
				v.add(bean);
				
				
			}
			
			con.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return v;
		
	}
	
	//�ϳ��� �Խñ� ���� �޼ҵ�ȣ��
	public void insertBoard(BoardBean bean) {
		getCon();
		int ref=0;
		int re_step=1;//����
		int re_level=1;//����
		try {
			String refsql="select  max(ref) from board";
			pstmt=con.prepareStatement(refsql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				ref = rs.getInt(1)+1;//���� ū���� 1�� ������
			}
			//�����͸� �����ϴ� ����;
			String sql ="insert into board values(board_seq.NEXTVAL,?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			//?�� �� ����
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);	
			pstmt.setInt(6, re_step);
			pstmt.setInt(7, re_level);
			pstmt.setString(8, bean.getContent());
			
			pstmt.executeUpdate();
			con.close();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//�ϳ� �Խñ� �о�帮�� �޼ҵ�
	public BoardBean getOneBoard(int num) {
		// TODO Auto-generated method stub
		getCon();
		BoardBean bean = null;
		try {
			//�ϳ� �Խñ� �о����� ��ȸ�� ����\
			String countsql="update board set readcount = readcount+1 where num=?";
			pstmt=con.prepareStatement(countsql);
			//?������ֱ�
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			//�ѰԽñۿ� ������ �������ִ� ����
			String sql ="select * from board where num =?";
		
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//���������� ����� ����
			rs=pstmt.executeQuery();
			if(rs.next()) {//�ϳ��� �Խñ��� �����ϸ�
				bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_data(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
		
				
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	//�������
	public void reInsertBoard(BoardBean bean) {
		getCon();
		int ref=bean.getRef();
		int re_step=bean.getRe_step();
		int re_level=bean.getRe_level();
		try {
			///�ٽ��ڵ�
			String levelsql="update board set re_level =re_level+1 where ref=? and re_level >?";
			pstmt=con.prepareStatement(levelsql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, re_level);
			pstmt.executeUpdate();
		
			//�����͸� �����ϴ� ����;
			String sql ="insert into board values(board_seq.NEXTVAL,?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			//?�� �� ����
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);	
			pstmt.setInt(6, re_step+1);//���� �θ�ۿ� ���ܺ��� 1�� ����
			pstmt.setInt(7, re_level+1);//����̱⶧����
			pstmt.setString(8, bean.getContent());
			pstmt.executeUpdate();
			con.close();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//��ȸ�� ���� �Ƚ�Ű�� �Խñ� ����
	public BoardBean getoneUpdateBoard(int num) {
		getCon();
		BoardBean bean = null;
		try {
		
			//�ѰԽñۿ� ������ �������ִ� ����
			String sql ="select * from board where num =?";
		
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//���������� ����� ����
			rs=pstmt.executeQuery();
			if(rs.next()) {//�ϳ��� �Խñ��� �����ϸ�
				bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_data(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
		
				
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	//bean �� �Ⱦ��� �ϳ��� �Խñ� ���� �޼ҵ�
	public void updateBoard(int num, String subject, String content) {
		
		getCon();//db ����
		try{
			String sql ="update board set subject=?,content=?where num=?";
			pstmt=con.prepareStatement(sql);
			//?�� �� ����
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setInt(3, num);
			//���� ����
			pstmt.executeUpdate();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	//�ϳ��� �Խñ��� ����
	public void deleteBoard(int num) {
		// TODO Auto-generated method stub
		 getCon();
		 try {
			 String sql ="delete from board where num=?";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setInt(1, num);
			 
			 pstmt.executeUpdate();
			 con.close();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	}
	
}
