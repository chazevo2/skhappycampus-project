<%@page import="com.sk.idol.reply.Reply"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sk.idol.board.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IdolLove</title>
<link type="text/css" rel="stylesheet" href="./css/header-1.css">
<link type="text/css" rel="stylesheet" href="./css/header-2.css">
<link type="text/css" rel="stylesheet" href="./css/article.css">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script type="text/javascript">
	var msg = '${param.msg}';
	// 게시물 & 댓글 삭제 
	if(msg != null && msg === "fail") {
		alert("삭제를 실패하였습니다.");
		location.href = "boardList";
	}
	// 게시물 수정 
	if(msg != null && msg === "access") {
		alert("잘못된 접근입니다.");
		location.href = "login";
	}
	
	function like(num, board_like) {
		if('${sessionScope.memberId}' == null || '${sessionScope.memberId}' == "") {
			alert("로그인 후 이용바랍니다.");
			return;
		} else {
			$.ajax({
				data : "num=" + num,
				type : "POST",
				url : "like.do",
				success : function(data) {
					console.log(data.msg);
					if(data.msg === "fail") {
						alert("게시글 추천을 실패하였습니다.");
					} else if (data.msg === "done") {
						alert("이미 추천한 게시물입니다.");
					} else {
						alert("게시글 추천하였습니다.");
						location.href = "article?num=" + num;
					}
				}
			});			
		}
	}
	
	function reply(boardNum, parentNum) {
		if('${sessionScope.memberId}' == null || '${sessionScope.memberId}' == "") {
			alert("로그인 후 이용바랍니다.");
			return;
		} else {
			var reply;
			if(parentNum == 0) {
				reply = $("#reply").val();
			} else {
				reply = $("#reply_" + parentNum).val();
			}
			$.ajax({
				data : "boardNum=" + boardNum + "&parentNum=" + parentNum + "&replyContent=" + reply,
				type : "POST",
				url : "reply.do",
				success : function(data) {
					if(data.msg === "fail") {
						alert("댓글 작성을 실패하였습니다.");
					} else {
						alert("댓글을 작성하였습니다.");
						location.href="article?num=" + boardNum;
					}
				}
			});
		}
	}
	
	function delReply(num, boardNum) {
		if('${sessionScope.memberId}' == null || '${sessionScope.memberId}' == "") {
			alert("로그인 후 이용바랍니다.");
			return;
		} else {
			$.ajax({
				data : "num=" + num,
				type : "POST",
				url : "delReply.do",
				success : function(data) {
					if(data.msg === "fail") {
						alert("댓글 삭제를 실패하였습니다.");
					} else {
						alert("댓글을 삭제하였습니다.");
						location.href="article?num=" + boardNum;
					}
				}
			});
		}
	}
	
	function reReply(num) {
		if('${sessionScope.memberId}' == null || '${sessionScope.memberId}' == "") {
			alert("로그인 후 이용바랍니다.");
			return;
		} else {
			if($("#reReply_"+num).css("display") == "none") {
				$("#reReply_"+num).show();								
			} else {
				$("#reReply_"+num).hide();																	
			}			
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
				<a href="home"><img src="img/logo.png" alt="로고" title="Go Home"></a>
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
		<div class="write-container">
			<div class="write-info">
				<h2 class="info-heading">게시글 상세 페이지</h2>
			</div>
			<div class="write-content">
				<div id="con_body">
					<div id="article_view">
						<!-- 게시판출력 -->
						<div class="article_header">
							<h4>${b.getBoardTitle()}</h4>
							<p class="article_date">
								<span>글쓴이 : ${b.getBoardWriter()}</span>&nbsp;
								<span>등록일 : ${b.getWriteDate()}</span>&nbsp;
								<span class="m_none">조회수 : ${b.getViewCnt()}</span>&nbsp;
								<span id="board_like">추천수 : ${b.getBoardLike()}</span>
							</p>
						</div>
						<div class="article_body">
							<div class="article_content">
								<br>${b.getBoardContent()}<br>
							</div>
						</div>
						<div class="likeArea">
							<span onclick="like(${b.getBoardNum()}, ${b.getBoardLike()})"><img src="img/like.png"><a>좋아요</a></span>
						</div>

						<!-- 댓글 영역 -->
						<div class="answer">
							<c:if test="${replyList != null && replyList.size() > 0}">
								<c:forEach var="r" items="${replyList}">
									<c:choose>
										<c:when test="${r.getParentNum() == 0}">
											<dl class="reply_box">
												<dt><a href="#none"><img src="img/profile.png" alt="프로필사진"></a></dt>
												<dd><p class="name">${r.getReplyWriter()}<span>${r.getWriteDate()}</span></p>
													<p>${r.getReplyContent()}</p>
													<p style="float:right;"><a onclick="reReply(${r.getReplyNum()})" class="re_reply">댓글달기</a>
													<c:if test="${sessionScope.memberId == r.getReplyWriter()}">	
														<a onclick="delReply(${r.getReplyNum()}, ${b.getBoardNum()})" class="re_reply">댓글삭제</a>
													</c:if>
													</p>
												</dd>
												<dd id="reReply_${r.getReplyNum()}" style="display:none; margin-top:50px;float:none;">
													<textarea id="reply_${r.getReplyNum()}" class="textarea_st"
														style="width: 70%; min-height: 50px; height: 50px;"></textarea>
													<a class="board_button2 fr" onclick="reply(${b.getBoardNum()}, ${r.getReplyNum()})"
														style="width: 5%; height: 60px; line-height: 50px;">댓글입력</a>
												</dd>
											</dl>										
										</c:when>
										<c:otherwise>
											<dl class="reply_box re">
												<dt><a href="#none"><img src="img/profile.png" alt="프로필사진"></a></dt>
												<dd><p class="name">${r.getReplyWriter()}<span>${r.getWriteDate()}</span></p>
													<p>${r.getReplyContent()}</p>
													<c:if test="${sessionScope.memberId == r.getReplyWriter()}">	
														<p style="float:right;"><a onclick="delReply(${r.getReplyNum()}, ${b.getBoardNum()})" class="re_reply">댓글삭제</a></p>
													</c:if>
												</dd>
											</dl>										
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:if>

							<div id="input_reply" class="clear_box">
								<textarea id="reply" class="textarea_st"
									style="width: 80%; min-height: 70px; height: 70px;"></textarea>
								<a class="board_button2 fr" onclick="reply(${b.getBoardNum()}, 0)"
									style="width: 10%; height: 80px; line-height: 70px;">댓글입력</a>
							</div>
						</div>

						<p class="btnArea">
							<a href="write" class="board_button1 mt5">글작성</a>
							<a href="boardList" class="board_button2 mt5">목록</a>
							<c:if test="${sessionScope.memberId == b.getBoardWriter()}">
								<a href="edit?num=${b.getBoardNum()}" class="board_button2 mt5">글수정</a>
							</c:if>
							<c:if test="${sessionScope.memberId == b.getBoardWriter() || sessionScope.grade == 'A'}">
								<a href="delArticle.do?num=${b.getBoardNum()}" class="board_button2 mt5">글삭제</a>
							</c:if>
						</p>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>