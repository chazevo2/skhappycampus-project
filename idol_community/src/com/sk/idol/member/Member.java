package com.sk.idol.member;

public class Member {
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberTel;
	private String memberGender;
	private String birthDate;
	private String memberGrade;
	private String signDate;
	private String memberImg;

	public Member() {
	}

	public Member(String memberId, String memberPw, String memberName, String memberTel, String memberGender, String birthDate) {
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.memberTel = memberTel;
		this.memberGender = memberGender;
		this.birthDate = birthDate;
	}

	public Member(String memberId, String memberPw, String memberName, String memberTel, String memberGender, String birthDate, String memberGrade, String signDate, String memberImg) {
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.memberTel = memberTel;
		this.memberGender = memberGender;
		this.birthDate = birthDate;
		this.memberGrade = memberGrade;
		this.signDate = signDate;
		this.memberImg = memberImg;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberTel() {
		return memberTel;
	}

	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getMemberImg() {
		return memberImg;
	}

	public void setMemberImg(String memberImg) {
		this.memberImg = memberImg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(memberId);
		builder.append(", ");
		builder.append(memberPw);
		builder.append(", ");
		builder.append(memberName);
		builder.append(", ");
		builder.append(memberTel);
		builder.append(", ");
		builder.append(birthDate);
		builder.append(", ");
		builder.append(memberGrade);
		builder.append(", ");
		builder.append(signDate);
		builder.append(", ");
		builder.append(memberImg);
		return builder.toString();
	}

}
