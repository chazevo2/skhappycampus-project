<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IdolLove</title>
<link type="text/css" rel="stylesheet" href="css/header-1.css">
<link type="text/css" rel="stylesheet" href="css/login.css">
<script type="text/javascript">
	var msg = '${param.msg}';
	if(msg != null && msg === "success") {
		alert("회원가입을 축하드립니다.\n로그인 후 이용바랍니다.");
	}
	
	// 로그인 실패 경우 안내창
	if(msg != null && msg === "fail") {
		alert("로그인을 실패했습니다.\n아이디와 비밀번호를 확인하세요.");
	}
	
	// 잘못된 접근
	if(msg != null && msg === "access") {
		alert("잘못된 접근입니다.");
	}
	
	window.onload = function() {
		var form = document.getElementById('loginForm');
		form.addEventListener('submit', function(event) {
			if (document.getElementsByName("memberId")[1].value == "") {
				alert("이메일 형식을 확인 바랍니다.");
				event.preventDefault();
				return;
			}
		});
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
						</ul>
					</li>
				</ul>
			</nav>
			<div class="login">
				<img src="img/login.png"><span><a href="login">로그인</a></span>
			</div>
		</div>
	</header>
	
	<!-- content -->
	<div class="sub-content">
		<div class="sub-container">
			<div class="sub-login">
				<div style="margin-left: 10px;">
					<h3>로그인</h3>
				</div>
				<div class="login-area">
					<form id="loginForm" action="login.do" method="post">
						<div class="inpt_field">
							<input class="text_inpt" type="text" name="memberId" placeholder="아이디" required autofocus><span> @ </span>
							<select class="text_inpt" name="memberId" style="width: 140px;">
								<option value="">선택</option>
								<option value="gmail.com">gmail.com</option>
								<option value="naver.com">naver.com</option>
								<option value="yahoo.com">yahoo.com</option>
								<option value="nate.com">nate.com</option>
								<option value="daum.net">daum.net</option>
							</select>
						</div>
						<div class="inpt_field">
							<input class="text_inpt" type="password" name="memberPw" placeholder="비밀번호" required style="width: 270px;"><br>
						</div>
						<div class="login_field">
							<input class="signup_btn" type="button" value="회원가입" onclick="location.href='signup'">
							<input class="login_btn" type="submit" value="로그인">
						</div>
						<div class="login_below_comment">
							<div><a href="idFind">아이디 찾기</a>&nbsp;/&nbsp;<a href="pwFind">비밀번호 찾기</a></div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>