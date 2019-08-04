package com.sk.idol.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class MemberDao {
	// ConnectionPooling을 위한 DataSource 객체 멤버 변수 선언
	private DataSource ds;
	
	/** 기본 생성자 : DataSource lookup 설정 초기화
	 * %tomcat_home%\conf\context.xml
	 * => codeName = "java/comp/env/"
	 *  => name = "jdbc/Oracle"
	 */
	public MemberDao() {
		String dsName = "java:comp/env/jdbc/Oracle";
		try {
			ds = (DataSource) (new InitialContext().lookup(dsName));
		} catch (NamingException e) {
			System.out.println("error:ds lookup error");
			e.printStackTrace();
		}
	}
	
	// 회원 가입
	public int insertMember(Member m) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			
			String sql = "insert into member values(?, ?, ?, ?, ?, ?, ?, ?, null)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberId());
			pstmt.setString(2, m.getMemberPw());
			pstmt.setString(3, m.getMemberName());
			pstmt.setString(4, m.getMemberTel());
			pstmt.setString(5, m.getMemberGender());
			pstmt.setString(6, m.getBirthDate());
			pstmt.setString(7, m.getMemberGrade());
			pstmt.setString(8, m.getSignDate());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 레코드 추가시 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	// 아이디 중복확인
	public int selectId(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			System.out.println(memberId);
			String sql = "select count(*) from member where member_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Error : 아이디 중복확인 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	// 전화번호 중복확인
	public int selectTel(String memberTel) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			String sql = "select count(*) from member where member_Tel=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberTel);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Error : 전화번호 중복확인 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return 0;
	}

	// 로그인
	public String selectMember(String memberId, String memberPw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			
			String sql = "select member_grade from member where member_id=? and member_pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString("member_grade");
			}
		} catch (SQLException e) {
			System.out.println("Error : 로그인 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return null;
	}
	
	// 아이디 찾기
	public String selectId(String memberName, String memberTel) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			
			String sql = "select member_id from member where member_name=? and member_tel=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberName);
			pstmt.setString(2, memberTel);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString("member_id");
			}
		} catch (SQLException e) {
			System.out.println("Error : 아이디 찾기 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return null;
	}
	
	// 내정보 조회
	public Member selectMyInfo(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			
			String sql = "select * from member where member_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return new Member(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
						rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9));
			}
		} catch (SQLException e) {
			System.out.println("Error : 내정보 찾기 오류 발생");
			e.printStackTrace();
		} finally {
			// 자원 해제
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return null;
	}
	
	// 비밀번호 변경
	public int updatePw(String memberId, String memberPw, String newPassword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			
			String sql = "update member set member_pw=? where member_id=? and member_pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPassword);
			pstmt.setString(2, memberId);
			pstmt.setString(3, memberPw);

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 레코드 변경시 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	// 프로필 사진 변경
	public int updateImg(String memberId, String uploadPath) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			
			String sql = "update member set member_img=? where member_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uploadPath);
			pstmt.setString(2, memberId);

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 레코드 변경시 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	// 회원 목록
	public ArrayList<Member> selectMemberList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ArrayList<Member> list = new ArrayList<Member>();
		try {
			conn = ds.getConnection();
			
			String sql = "select * from member order by sign_date desc";
			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new Member(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
						rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)));
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Error : 내정보 찾기 오류 발생");
			e.printStackTrace();
		} finally {
			// 자원 해제
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return null;
	}
	
	// 성별차트보여주기
	public HashMap<String, Integer> showGenderChart() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			conn = ds.getConnection();

			String sql = "select member_gender, count(*) from member group by member_gender";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				map.put(rs.getString(1), rs.getInt(2));
			}
			return map;
		} catch (SQLException e) {
			System.out.println("Error : 성별 분류 정보 가져오기 실패");
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
	
	// 연령별차트 보여주기
	public HashMap<String, Integer> showAgeChart() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		HashMap<String, Integer> map2 = new HashMap<>();
		try {
			conn = ds.getConnection();

			String sql = "select age*10, count(*) from(select trunc(nvl(trunc(months_between(sysdate, to_date(substr(birth_date, 1, 4), 'yyyy'))/12), 0)/10) as age from member) group by age";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				map2.put(rs.getString(1), rs.getInt(2));
			}
			return map2;
		} catch (SQLException e) {
			System.out.println("Error : 연령별 정보 가져오기 실패");
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
	
	// 회원 삭제
	public int delMember(String delMemberId, String memberPw, int i) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			String sql = "delete from member where member_id=?";
			if(i > 0)
				sql += " and member_pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, delMemberId);
			if(i > 0)
				pstmt.setString(2, memberPw);

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 레코드 삭제시 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	// 회원 등급 변경
	public int updateGrade(String memberId, String memberGrade) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			
			String sql = "update member set member_grade=? where member_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberGrade);
			pstmt.setString(2, memberId);

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 레코드 변경시 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	// 비밀번호 찾기 및 새 비밀번호
	public int updatePw(Member m) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			
			String sql = "update member set member_pw=? where member_name=? and member_id=? and member_tel=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberPw());
			pstmt.setString(2, m.getMemberName());
			pstmt.setString(3, m.getMemberId());
			pstmt.setString(4, m.getMemberTel());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error : 레코드 변경시 오류 발생");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error : 자원 해제시 오류 발생");
				e.printStackTrace();
			}
		}
		return 0;
	}
}
