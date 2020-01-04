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
	
	//데이터 베이스 연결 메서드
	public void getCon() {
		try {
			Context initctx = new InitialContext();
			Context envcts = (Context)initctx.lookup("java:comp/env");
			DataSource ds =(DataSource)envcts.lookup("jdbc/pool");
			con=ds.getConnection();//커넥션 연결 메소드
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	//전체 글 갯수 리턴
	public int getAllCount() {
		getCon();// 데이터베이스 연결
		int count = 0;
		try {
			String sql= "select count(*) from board ";
			pstmt = con.prepareStatement(sql);
			//쿼리 실행 후 리턴받기
			rs= pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);//전체 게시글수
			}
			con.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return count;
	}
	//화면에 보여질 데이터 10개씩 뽑아서 리턴 메소드
	public Vector<BoardBean> getAllBoard(int startRow, int endRow){
		Vector<BoardBean> v= new Vector<>();
		
		getCon();
		try {
			String sql= "select  * from (Select A.* , Rownum Rnum from(select * from board order by ref desc ,re_step asc)A)"
					+"where Rnum>= ? and Rnum<=?";
			pstmt = con.prepareStatement(sql);
			//?갑승ㄹ 대임
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			//쿼리 실행 후 리턴받기
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
	
	//하나의 게시글 저장 메소드호출
	public void insertBoard(BoardBean bean) {
		getCon();
		int ref=0;
		int re_step=1;//새글
		int re_level=1;//새글
		try {
			String refsql="select  max(ref) from board";
			pstmt=con.prepareStatement(refsql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				ref = rs.getInt(1)+1;//가장 큰값에 1을 더해줌
			}
			//데이터를 삽입하는 쿼리;
			String sql ="insert into board values(board_seq.NEXTVAL,?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			//?에 값 대입
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
	
	//하나 게시글 읽어드리는 메소드
	public BoardBean getOneBoard(int num) {
		// TODO Auto-generated method stub
		getCon();
		BoardBean bean = null;
		try {
			//하나 게시글 읽엇을때 조회수 증가\
			String countsql="update board set readcount = readcount+1 where num=?";
			pstmt=con.prepareStatement(countsql);
			//?값집어넣기
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			//한게시글에 정보를 리턴해주는 쿼리
			String sql ="select * from board where num =?";
		
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//쿼리실행후 결과를 리턴
			rs=pstmt.executeQuery();
			if(rs.next()) {//하나의 게시글이 존재하면
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
	//답글저장
	public void reInsertBoard(BoardBean bean) {
		getCon();
		int ref=bean.getRef();
		int re_step=bean.getRe_step();
		int re_level=bean.getRe_level();
		try {
			///핵심코드
			String levelsql="update board set re_level =re_level+1 where ref=? and re_level >?";
			pstmt=con.prepareStatement(levelsql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, re_level);
			pstmt.executeUpdate();
		
			//데이터를 삽입하는 쿼리;
			String sql ="insert into board values(board_seq.NEXTVAL,?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			//?에 값 대입
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);	
			pstmt.setInt(6, re_step+1);//기존 부모글에 스텝보다 1을 증가
			pstmt.setInt(7, re_level+1);//답글이기때문에
			pstmt.setString(8, bean.getContent());
			pstmt.executeUpdate();
			con.close();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//조회수 증가 안시키는 게시글 보기
	public BoardBean getoneUpdateBoard(int num) {
		getCon();
		BoardBean bean = null;
		try {
		
			//한게시글에 정보를 리턴해주는 쿼리
			String sql ="select * from board where num =?";
		
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//쿼리실행후 결과를 리턴
			rs=pstmt.executeQuery();
			if(rs.next()) {//하나의 게시글이 존재하면
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
	//bean 을 안쓰고 하나의 게시글 수정 메소드
	public void updateBoard(int num, String subject, String content) {
		
		getCon();//db 연결
		try{
			String sql ="update board set subject=?,content=?where num=?";
			pstmt=con.prepareStatement(sql);
			//?에 값 대입
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setInt(3, num);
			//쿼리 실행
			pstmt.executeUpdate();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	//하나의 게시글을 삭제
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
