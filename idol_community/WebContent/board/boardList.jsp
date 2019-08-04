<%@page import="com.sk.idol.board.Board"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IdolLove</title>
<link type="text/css" rel="stylesheet" href="css/header-1.css">
<link type="text/css" rel="stylesheet" href="css/header-2.css">
<link type="text/css" rel="stylesheet" href="css/boardList.css">
<script type="text/javascript">
	function searchArticle() {
		if(document.getElementsByName("searchValue")[0].value === "") {
			alert("검색어를 입력하세요.");
		} else {
			document.getElementById('search').submit();		
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
			<c:choose>
				<c:when test="${empty sessionScope.grade}">
					<div class="login">
						<img src="./img/login.png"><span><a href="login">로그인</a></span>
					</div>
				</c:when>
				<c:otherwise>
					<div class="logout">
						<img src="./img/logout.png"><span><a href="logout.do">로그아웃</a></span>
					</div>
					<div class="mypage">
						<img src="./img/mypage.png"><span><a href="myInfo">마이페이지</a></span>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</header>

	<!-- content -->
	<div class="sub-content">
		<div class="sub-container">
			<div class="category-menu">
				<div id="gnb_wrap">
				<div id="gnb">
					<ul>
						<li class="on">
							<a href="#none" style="width:180px;">카테고리</a>
							<ul class="2th">
								<li><a href="categoryBoard?category=트와이스">트와이스</a></li>
								<li><a href="categoryBoard?category=블랙핑크">블랙핑크</a></li>
								<li><a href="categoryBoard?category=방탄소년단">방탄소년단</a></li>
								<li><a href="categoryBoard?category=워너원">워너원</a></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			</div>
			<div class="boardList">
				<div class="sub-info">
					<h2 class="sub-heading">게시판</h2>
					<div class="sub_heading_text">아이돌 관련 정보를 유저들과 공유해보세요.</div>
				</div>
				<div class="sub-boardList">
					<div class="boardList_contents">
						<div class="boardList_contents_body">
							<fieldset>
								<form id="search" action="search">
									<div class="top_form">
										<select name="searchCategory" style="width:120px;" class="sel_type">
											<option value="">카테고리</option>
											<option value="트와이스">트와이스</option>
											<option value="블랙핑크">블랙핑크</option>
											<option value="방탄소년단">방탄소년단</option>
											<option value="워너원">워너원</option>
										</select>
										<select name="searchType" style="width:120px;" class="sel_type ml5">
											<option value="title">제목</option>
											<option value="content">내용</option>
										</select>
										<input name="searchValue" placeholder="검색어를 입력하세요" class="ip_type2 ml5" type="search" size="25">
										<a onclick="searchArticle()" class="btn_search va_m ml5">검색</a>
										<a href="write" class="btn_search va_m ml5" style="float: right;">글 작성</a>
									</div>
								</form>
							</fieldset>
							<div id="con_body">
								<table class="tb_base" summary="회원정보 테이블입니다.">
									<colgroup>
										<col width="5%">
										<col width="10%">
										<col width="33%">
										<col width="23%">
										<col width="15%">
										<col width="7%">
										<col width="7%">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">번호</th>
											<th scope="col">카테고리</th>
											<th scope="col">제목</th>
											<th scope="col">글쓴이(아이디)</th>
											<th scope="col">작성날짜</th>
											<th scope="col">추천수</th>
											<th scope="col">조회수</th>
										</tr>
									</thead>
									<tbody class="tb_center">
										<c:choose>
											<c:when test="${list != null && list.size() > 0}">
												<c:forEach var="m" items="${list}">
													<tr>
														<td>${m.boardNum}</td>
														<td>${m.category}</td>
														<td><a href="article?num=${m.boardNum}">${m.boardTitle}</a></td>
														<td>${m.boardWriter}</td>
														<td>${m.writeDate}</td>
														<td>${m.boardLike}</td>
														<td>${m.viewCnt}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr><td colspan="7">게시물이 없습니다.</td></tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
			
								<!-- 페이징 -->
								<ul class="paging">
									<li class="btn_first"><a href="#" onclick="cfnPageLink(1); return false;" title="처음"><span class="hidden">처음</span></a></li>
									<li class="btn_prev"><a href="#" onclick="cfnPageLink(1); return false;" title="이전 10 페이지"><span class="hidden">이전 10페이지 이동</span></a></li>
									<li class="on"><a href="javascript://" title="현재페이지">1</a></li>
									<li class="btn_next"><a href="#" onclick="cfnPageLink(1); return false;" title="다음 10 페이지"><span class="hidden">다음 10 페이지 이동</span></a></li>
									<li class="btn_last"><a href="#" onclick="cfnPageLink(1); return false;" title="마지막"><span class="hidden">마지막</span></a></li>
								</ul>
								<!-- //페이징 -->
			
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>