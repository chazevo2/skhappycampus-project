package com.sk.idol.member;

import java.util.ArrayList;
import java.util.HashMap;

import com.sk.idol.util.MailSend;
import com.sk.idol.util.Utility;

public class MemberService {
	private MemberDao dao;

	public MemberService() {
		dao = new MemberDao(); 
	}
	
	// 회원가입
	public boolean signup(Member m) {
		m.setMemberGrade("G");// 일반회원
		m.setSignDate(Utility.getCurrentDate());
		int result = dao.insertMember(m);
		if(result == 1) {
			return true;
		}
		return false;
	}
	
	// 아이디 중복확인
	public int idCheck(String memberId) {
		return dao.selectId(memberId);
	}
	
	// 아이디 중복확인
	public int telCheck(String memberTel) {
		return dao.selectTel(memberTel);
	}
	
	// 로그인
	public String login(String memberId, String memberPw) {
		return dao.selectMember(memberId, memberPw);
	}
	
	// 아이디 찾기
	public String idFind(String memberName, String memberTel) {
		return dao.selectId(memberName, memberTel);
	}
	
	// 비밀번호 찾기 - 미완
	public Boolean pwFind(String memberName, String memberId, String memberTel) {
		String newPassword = Utility.getSecureCode3(10);
		System.out.println(newPassword);
		Member m = new Member(memberId, newPassword, memberName, memberTel, "", "");
		int result = dao.updatePw(m);
		if(result > 0) {
			MailSend.send(memberId, newPassword);
			return true;
		}
		return false;
	}
	
	// 내정보
	public Member myInfo(String memberId) {
		return dao.selectMyInfo(memberId);
	}

	// 비밀번호 변경
	public int pwChange(String memberId, String memberPw, String newPassword) {
		return dao.updatePw(memberId, memberPw, newPassword);
	}

	// 프로필 사진 변경
	public int setProfile(String memberId, String uploadPath) {
		return dao.updateImg(memberId, uploadPath);
	}
	
	// 회원 리스트 뿌리기
	public ArrayList<Member> getMemberList() {	
		return dao.selectMemberList();
	}
	// 성별 차트
	public HashMap<String, Integer> showGenderChart() {
		return dao.showGenderChart();
	}
	// 연령별 차트
	public HashMap<String, Integer> showAgeChart() {
		return dao.showAgeChart();
	}
	
	// 회원 삭제
	public int delMember(String delId, String password, int i) {
		return dao.delMember(delId, password, i);
	}
	
	// 회원 등급 변경
	public int chGrade(String chId, String newGrade) {
		return dao.updateGrade(chId, newGrade);
	}
}
