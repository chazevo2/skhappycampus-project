package com.sk.idol.reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.sk.idol.board.Board;

public class ReplyDao {
	// ConnectionPooling을 위한 DataSource 객체 멤버 변수 선언
	private DataSource ds;

	/**
	 * 기본 생성자 : DataSource lookup 설정 초기화 %tomcat_home%\conf\context.xml => codeName
	 * = "java/comp/env/" => name = "jdbc/Oracle"
	 */
	public ReplyDao() {
		String dsName = "java:comp/env/jdbc/Oracle";
		try {
			ds = (DataSource) (new InitialContext().lookup(dsName));
		} catch (NamingException e) {
			System.out.println("error:ds lookup error");
			e.printStackTrace();
		}
	}

	// 글 작성
	public int insertReply(Reply r) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();

			String sql = "insert into reply values(reply_seq.nextval, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, r.getBoardNum());
			pstmt.setString(2, r.getReplyWriter());
			pstmt.setString(3, r.getReplyContent());
			pstmt.setString(4, r.getWriteDate());
			pstmt.setInt(5, r.getParentNum());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 레코드 추가시 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return 0;
	}

	// 게시글 해당 댓글 목록
	public ArrayList<Reply> selectReplyList(int boardNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ArrayList<Reply> list = new ArrayList<>();
		try {
			conn = ds.getConnection();

			String sql = "select r1.reply_num, r1.board_num, r1.reply_writer, r1.reply_content, r1.write_date, r1.parent_num, nvl(r2.reply_num, r1.reply_num) ";
			sql += "from reply r1, reply r2 ";
			sql += "where r1.parent_num = r2.reply_num(+) ";
			sql += "and r1.board_num = ? ";
			sql += "order by 7, 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new Reply(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Error : 댓글 리스트 검색 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return null;
	}

	// 내가 작성한 댓글
	public ArrayList<Reply> selectMyList(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ArrayList<Reply> list = new ArrayList<>();
		try {
			conn = ds.getConnection();

			String sql = "select board_num, reply_content, write_date from (select * from reply where reply_writer=? order by reply_num desc) where rownum<6";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Reply r = new Reply();
				r.setBoardNum(rs.getInt(1));
				r.setReplyContent(rs.getString(2));
				r.setWriteDate(rs.getString(3));
				list.add(r);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Error : 내 댓글 리스트 검색 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return null;
	}
	
	// 댓글 삭제
	public int deleteByNum(int replyNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			String sql = "delete from reply where reply_num=? or parent_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyNum);
			pstmt.setInt(2, replyNum);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 댓글 삭제 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	
}
