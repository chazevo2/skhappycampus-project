<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IdolLove</title>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
<script>
	// 2. This code loads the IFrame Player API code asynchronously.
	var tag = document.createElement('script');

	tag.src = "https://www.youtube.com/iframe_api";
	var firstScriptTag = document.getElementsByTagName('script')[0];
	firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

	// 3. This function creates an <iframe> (and YouTube player)
	//    after the API code downloads.
	var player;
	function onYouTubeIframeAPIReady() {
		var rand = Math.floor(Math.random() * 4);
		var src = ["2S24-y0Ij3Y", "0rtV5esQT6I", "7C2z4GqqS5E", "EVaV7AwqBWg"];
		var video = src[rand];
		player = new YT.Player('player', {
			height : '480',
			width : '853',
			videoId : video,
			events : {
				'onReady' : onPlayerReady
			}
		});
	}

	// 4. The API will call this function when the video player is ready.
	function onPlayerReady(event) {
		event.target.playVideo();
	}

	// 5. The API calls this function when the player's state changes.
	//    The function indicates that when playing a video (state=1),
	//    the player should play for six seconds and then stop.
	var done = false;
	function onPlayerStateChange(event) {
		if (event.data == YT.PlayerState.PLAYING && !done) {
			setTimeout(stopVideo, 60000);
			done = true;
		}
	}
	function stopVideo() {
		player.stopVideo();
	}
</script>
<link type="text/css" rel="stylesheet" href="css/header-1.css">
<link type="text/css" rel="stylesheet" href="css/header-2.css">
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

/* content */
.sub-content {
	min-height: 700px;
	position: relative;
	top: 90px;
	width: 100%;
	background-color: #eff0f2;
}
.sub-container {
	width: 1096px;
	margin: 0 auto;
	text-align: center;
	overflow: hidden;
}
.sub-index {
	position: relative;
	overflow: hidden;
	width: 1096px;
	height: 600px;
	background-color: #fff;
	border: 1px solid #e6e6e6;
	margin-bottom: 50px;
	padding: 50px 30px;
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
			<div class="sub-index">
				<!-- 1. The <iframe> (and video player) will replace this <div> tag. -->
				<div id="player"></div>
			</div>
		</div>
	</div>
</body>
</html>