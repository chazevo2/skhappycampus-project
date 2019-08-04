<%@page import="com.sk.idol.board.Board"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sk.idol.member.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IdolLove</title>
<link type="text/css" rel="stylesheet" href="./css/header-2.css">
<link type="text/css" rel="stylesheet" href="./css/myinfo.css">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
<script type="text/javascript">
	function changePw() {
		window.open("pwChange", "아이디 중복확인", "top=300, left=700, width=600, height=200");
	}
	
	function changeValue(obj) {
		var file = $("#file")[0].files[0];
		data = new FormData();
		data.append("file", file);
		$.ajax({
			data : data,
			type : "POST",
			url : "myProfile.do",
			cache : false,
			contentType : false,
			processData : false,
			enctype : "multipart/form-data",
			success : function(data) {
				console.log(data.url);
				if(data.url !== "fail") {
					$(".profile_img").children().attr('src', data.url);
				}
			}
		});
	}
	
	function delMyInfo() {
		var msg = "정말로 회원정보를 삭제하시겠습니까?";
		msg += "\n비밀번호를 입력 바랍니다.";
		var check = prompt(msg);
		console.log(check);
		if(check !== "") {
			$.ajax({
				data : "password=" + check,
				type : "POST",
				url : "delMyInfo.do",
				datatype : "json",
				success : function(data) {
					console.log(data);
					if(data.msg !== "fail") {
						alert("회원 탈퇴가 완료되었습니다.\n그동안 이용해주셔서 감사합니다.");
						location.href = "home";
					} else {
						alert("회원 탈퇴를 실패하였습니다.");
					}
				}
			});
		} 	
	}
</script>
<style type="text/css">
/* sub menu */
.menu-1:hover {
	color: #d0112b;
	cursor: pointer;
}
.menu-1:hover .submenu {
	display: block;
}
.submenu {
	font-weight: 300;
	text-transform: none;
	display: none;
	position: absolute;
	width: 130px;
	line-height: 50px;
	left: 12px;
	background-color: #fff;
	border-width : 0px;
	box-shadow: 3px 3px 3px gray;
}
nav ul {
    list-style: none;
    padding-left: 0;
    margin-top: 0;
    margin-bottom: 0;
}
.submenu-item {
    font-size: 11pt;
    font-family: 'Malgun Gothic','맑은 고딕','돋움', 'dotum', sans-serif;
}
.submenu-item a {
    padding: 0 0 0 10px;
    text-decoration: none;
    color: #000;
}
.nav__submenu-item:hover {
    background: rgba(0, 0, 0, 0.05);
    cursor: pointer;
}
.submenu-item:hover a {
	color: #d0112b;
	cursor: pointer;
}
</style>
</head>
<body>
	<!-- header -->
	<header>
		<div class="plate">
			<div class="logo f-l">
				<a href="home"><img src="./img/logo.png" alt="로고" title="Go Home"></a>
			</div>
			<nav class="f-l">
				<ul>
					<li class="f-l menu-1"><span class="menu-1-item"><a href="boardList">게시판</a></span>
						<ul class="submenu">
							<li class="submenu-item"><a href="categoryBoard?category=트와이스">트와이스</a></li>
							<li class="submenu-item"><a href="categoryBoard?category=블랙핑크">블랙핑크</a></li>
							<li class="submenu-item"><a href="categoryBoard?category=방탄소년단">방탄소년단</a></li>
							<li class="submenu-item"><a href="categoryBoard?category=워너원">워너원</a></li>
						</ul></li>
					<c:if test="${!empty sessionScope.grade && sessionScope.grade == 'A'}">
						<li class="f-l menu-1"><span class="menu-1-item"><a href="memberList">회원관리</a></span></li>
					</c:if>
				</ul>
			</nav>
			<div class="logout">
				<img src="./img/logout.png"><span><a href="logout.do">로그아웃</a></span>
			</div>
			<div class="mypage">
				<img src="./img/mypage.png"><span><a href="myInfo">마이페이지</a></span>
			</div>
		</div>
	</header>	
	
	<!-- content -->
	<div class="sub-content">
		<div class="sub-container">
			<div class="sub-info">
				<h2 class="sub-heading">마이페이지</h2>
				<div class="sub_heading_text">내 정보와 내 게시판 활동을 조회하세요.</div>
			</div>
			<div class="sub-myInfo">
				<!-- 내 정보 -->
				<% Member m = (Member) request.getAttribute("m"); %>
				<table class="myInfo_contents" summary="내정보 테이블입니다.">
					<colgroup>
						<col width="16%">
						<col width="15%">
						<col width="27%">
						<col width="15%">
						<col width="27%">
					</colgroup>
					<tbody>
					<tr>
						<td rowspan="4" style="padding: 0">
							<p class="profile_img">
								<c:choose>
									<c:when test="${m.getMemberImg() == null}"><img src="./img/profile.png"></c:when>
									<c:otherwise><img src='${m.getMemberImg()}'></c:otherwise>
								</c:choose>
							</p>
							<input type="file" id="file" name="file" onchange="changeValue(this)" accept="image/gif, image/jpeg, image/png" style="display:none;">
							<a class="changeBtn" onclick="$('#file').click();">이미지 변경</a>
						</td>
						<th class="line" scope="row">이름</th>
						<td><%= m.getMemberName() %></td>
						<th class="line" scope="row">아이디</th>
						<td><%= m.getMemberId() %><input type="hidden" id="memberId" value="<%= m.getMemberId() %>"></td>
					</tr>
					<tr>
						<th class="line" scope="row">휴대전화번호</th>
						<td><%= m.getMemberTel() %></td>
						<th class="line" scope="row">비밀번호</th>
						<td>
							<a class="changeBtn" onclick="changePw()">비밀번호 변경</a>
						</td>
						
					</tr>
					<tr>
						<th class="line" scope="row">성별</th>
						<td>
							<c:choose>
								<c:when test="${m.getMemberGender() == 'M'}"><c:out value="남자"/></c:when>
								<c:otherwise><c:out value="여자"/></c:otherwise>
							</c:choose>
						</td>
						<th class="line" scope="row">등급</th>
						<td>
							<c:choose>
								<c:when test="${m.getMemberGrade() == 'G'}"><c:out value="일반 회원"/></c:when>
								<c:otherwise><c:out value="관리자"/></c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th class="line" scope="row">생년월일</th>
						<td><%= m.getBirthDate() %></td>
						<th class="line" scope="row">가입날짜</th>
						<td><%= m.getSignDate() %>&nbsp;
							<c:if test="${m.getMemberGrade() == 'G'}">
								<a class="changeBtn" onclick="delMyInfo()">회원 탈퇴</a>
							</c:if>
						</td>
					</tr>
					</tbody>
				</table>

				<!-- 내가 작성한 글 -->
				<div class="myBoard_contents">
					<div class="myBoard_contents_list">
						<div class="cont_left_box">
							<p class="index_title">내가 작성한 글<sub style="font-size:15px;margin-left:10px;">최대 5개까지 출력됩니다.</sub></p>
							<table class="tb_base">
								<colgroup>
									<col style="width:12%;">
									<col style="width:*%;">
									<col style="width:25%;">
								</colgroup>
								<thead>
									<tr>
										<th scope="col">글번호</th>
										<th scope="col">게시글 제목</th>
										<th scope="col">날짜</th>
									</tr>
								</thead>
								<tbody class="tb_center">
								<c:choose>
									<c:when test="${list != null && list.size() > 0}">
										<c:forEach var="b" items="${list}">
					                        <tr>
												<td><c:out value="${b.getBoardNum()}"></c:out></td>
												<td><a href="article?num=${b.getBoardNum()}">
													<c:choose>
														<c:when test="${b.getBoardTitle().length() > 15}">
															<c:out value="${b.getBoardTitle().substring(0, 15)}..."></c:out>
														</c:when>
														<c:otherwise>
															<c:out value="${b.getBoardTitle()}"></c:out>
														</c:otherwise>
													</c:choose>
												</a></td>
												<td><c:out value="${b.getWriteDate()}"></c:out></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
				                        <tr>
											<td colspan="2">아직 작성한 글이 없습니다.</td>
										</tr>
									</c:otherwise>
								</c:choose>
								</tbody>
							</table>
						</div>
	
						<div class="cont_right_box">
							<p class="index_title">내가 작성한 댓글<sub style="font-size:15px;margin-left:10px;">최대 5개까지 출력됩니다.</sub></p>
							<table class="tb_base">
								<colgroup>
									<col style="width:23%;">
									<col style="width:*%;">
									<col style="width:25%;">
								</colgroup>
								<thead>
									<tr>
										<th scope="col">해당 글번호</th>
										<th scope="col">댓글 내용</th>
										<th scope="col">날짜</th>
									</tr>
								</thead>
								<tbody class="tb_center">
								<c:choose>
									<c:when test="${list2 != null && list2.size() > 0}">
										<c:forEach var="r" items="${list2}">
					                        <tr>
												<td><c:out value="${r.getBoardNum()}"></c:out></td>
												<td><a href="article?num=${r.getBoardNum()}">
													<c:choose>
														<c:when test="${r.getReplyContent().length() > 13}">
															<c:out value="${r.getReplyContent().substring(0, 13)}..."></c:out>
														</c:when>
														<c:otherwise>
															<c:out value="${r.getReplyContent()}"></c:out>
														</c:otherwise>
													</c:choose>
												</a></td>
												<td><c:out value="${r.getWriteDate()}"></c:out></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
				                        <tr>
											<td colspan="2">아직 작성한 댓글이 없습니다.</td>
										</tr>
									</c:otherwise>
								</c:choose>
								</tbody>
							</table>
						</div>
						
					</div>
				</div>				
			</div>
		</div>
	</div>
</body>
</html>