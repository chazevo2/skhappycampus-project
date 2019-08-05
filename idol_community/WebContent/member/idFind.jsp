<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IdolLove</title>
<link type="text/css" rel="stylesheet" href="css/header-1.css">
<link type="text/css" rel="stylesheet" href="css/signup.css">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
<script type="text/javascript">
	function idFind() {
		var name = document.getElementsByName("memberName")[0].value;
		var tel = document.getElementsByName("memberTel")[0].value;
		tel += "-" + document.getElementsByName("memberTel")[1].value;
		tel += "-" + document.getElementsByName("memberTel")[2].value;
		
		$.ajax({
			data : "name=" + name + "&tel=" + tel,
			type : "POST",
			url : "idFind.do",
			datatype : "text",
			success : function(data) {
				console.log(data);
				if (data === 'fail') {
					alert("존재하지 않는 회원정보입니다.");
				} else {
					alert("등록된 아이디는 " + data + "입니다.");
					location.href= "login";
				}
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
			<div class="sub-info">
				<h2 class="sub-heading">아이디 찾기</h2>
			</div>
			<div class="sub-register">
				<table class="register-contents">
					<tbody>
						<tr>
							<td><span>이름</span></td>
							<td><input class="text_inpt" type="text" name="memberName" placeholder="이름" pattern="^[가-힣]{2,3}$" required autofocus></td>
						</tr>
						<tr>
							<td><span>전화번호</span></td>
							<td>
								<select class="text_inpt" name="memberTel" style="width: 100px;">
									<option value="">선택</option>
									<option value="010">010</option>
									<option value="010">011</option>
									<option value="010">016</option>
									<option value="010">017</option>
									<option value="010">018</option>
									<option value="010">019</option>
								</select><span> - </span>
								<input class="text_inpt" type="text" name="memberTel" pattern="^[0-9]{3,4}$" required><span> - </span>
								<input class="text_inpt" type="text" name="memberTel" pattern="^[0-9]{4}$" required>
							</td>
						</tr>
					</tbody>
				</table>
				<div style="width: 95%; margin-top: 20px;">
					<input type="button" class="normal_gray_center_btn" value="아이디 찾기" onclick="idFind()">
				</div>
			</div>
		</div>
	</div>

</body>
</html>