package com.sk.idol.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BoardDao {
	// ConnectionPooling을 위한 DataSource 객체 멤버 변수 선언
	private DataSource ds;

	/**
	 * 기본 생성자 : DataSource lookup 설정 초기화 %tomcat_home%\conf\context.xml => codeName
	 * = "java/comp/env/" => name = "jdbc/Oracle"
	 */
	public BoardDao() {
		String dsName = "java:comp/env/jdbc/Oracle";
		try {
			ds = (DataSource) (new InitialContext().lookup(dsName));
		} catch (NamingException e) {
			System.out.println("error:ds lookup error");
			e.printStackTrace();
		}
	}

	// 글 작성
	public int insertBoard(Board b) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();

			String sql = "insert into board values(?, board_seq.nextval, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getCategory());
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setString(4, b.getBoardWriter());
			pstmt.setString(5, b.getWriteDate());
			pstmt.setInt(6, b.getViewCnt());
			pstmt.setInt(7, b.getBoardLike());

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

	// 게시글 전체 목록
	public ArrayList<Board> selectBoardList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ArrayList<Board> list = new ArrayList<Board>();
		try {
			conn = ds.getConnection();

			String sql = "select * from board order by board_num desc";
			pstmt = conn.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Board b = new Board();
				b.setBoardNum(rs.getInt("board_num"));
				b.setBoardTitle(rs.getString("board_title"));
				b.setCategory(rs.getString("category"));
				b.setBoardWriter(rs.getString("board_writer"));
				b.setWriteDate(rs.getString("write_date"));
				b.setViewCnt(rs.getInt("view_cnt"));
				b.setBoardLike(rs.getInt("board_like"));
				list.add(b);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Error : 게시글 전체 목록 출력 오류 발생");
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
	
	// 카테고리 게시판
	public ArrayList<Board> selectBoardList(String category) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ArrayList<Board> list = new ArrayList<Board>();
		try {
			conn = ds.getConnection();

			String sql = "select * from board where category=? order by board_num desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new Board(rs.getString(1), rs.getInt(2), rs.getString(3), "", rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8)));
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Error : 게시글 카테고리별 목록 출력 오류 발생");
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
	
	// 글검색
	public ArrayList<Board> selectBoardList(String category, String searchValue, String searchType) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ArrayList<Board> list = new ArrayList<Board>();
		try {
			conn = ds.getConnection();

			String sql = "select * from board where ";
			int i = 1;
			if(category != null && category.length() != 0) {
				sql += "category=? and ";
			}
			if(searchType.equals("title")) {
				sql += "board_title like '%'||?||'%' ";
			} else {
				sql += "board_content like '%'||?||'%' ";
			}
			sql += "order by board_num desc";
			pstmt = conn.prepareStatement(sql);
			if(category != null && category.length() != 0) {
				pstmt.setString(i++, category);
			}
			System.out.println(searchValue);
			System.out.println(sql);
			pstmt.setString(i, searchValue);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new Board(rs.getString(1), rs.getInt(2), rs.getString(3), "", rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8)));
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Error : 게시글 검색 오류 발생");
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

	// 내목록 가져오기
	public ArrayList<Board> selectMyList(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ArrayList<Board> list = new ArrayList<Board>();

		try {
			conn = ds.getConnection();

			String sql = "select board_num, board_title, write_date from (select * from board where board_writer=? order by board_num desc) where rownum<6";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Board dto = new Board();
				dto.setBoardNum(rs.getInt("board_num"));
				dto.setBoardTitle(rs.getString("board_title"));
				dto.setWriteDate(rs.getString("write_date"));
				list.add(dto);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Error : 내 게시글 검색 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error : 자원해제시 오류 발생 ");
				e.printStackTrace();
			}
		}
		return null;
	}
	
	// 게시글 상세보기
	public Board selectByNum(int boardNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Board b = new Board();
		try {
			conn = ds.getConnection();
			String sql = "select * from board where board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				b.setCategory(rs.getString("category"));
				b.setBoardNum(rs.getInt("board_num"));
				b.setBoardTitle(rs.getString("board_title"));
				b.setBoardContent(rs.getString("board_content"));
				b.setBoardWriter(rs.getString("board_writer"));
				b.setWriteDate(rs.getString("write_date"));
				b.setViewCnt(rs.getInt("view_cnt"));
				b.setBoardLike(rs.getInt("board_like"));
			}
			return b;
		} catch (SQLException e) {
			System.out.println("Error : 게시글 상세보기 발생");
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
		return null;
	}
	
	// 조회수
	public int updateViewCnt(int boardNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			String sql = "update board set view_cnt=view_cnt+1 where board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 조회수 증가 오류 발생");
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
	
	// 추천수
	public int updateLike(int boardNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			String sql = "update board set board_like=board_like+1 where board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 좋아요 수정 오류 발생");
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
	
	// 글삭제
	public int deleteByNum(int boardNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			String sql = "delete from board where board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 글 삭제 오류 발생");
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

	// 글 수정
	public int updateBoard(Board b) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();

			String sql = "update board set category=?, board_title=?, board_content=? where board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getCategory());
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setInt(4, b.getBoardNum());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 레코드 변경시 오류 발생");
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
}
