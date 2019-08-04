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
	// 회원가입 실패 경우 안내창
	var msg = '${param.msg}';
	if(msg != null && msg === "fail") {
		alert("회원가입을 실패하였습니다.");
	}
	
	var idcheck = "";
	
	
	function lastTenYear() {
		var d = new Date();
		var year = d.getFullYear();
		d.setYear(year - 15);
		return d;
	}
	
	function check() {
		var date = document.getElementsByName("birthDate")[0].valueAsDate;
		console.log(date);
		console.log(lastTenYear());
		if(date > lastTenYear()) {
			alert("15세 이상 이용가능합니다.");
			event.preventDefault();
			return;
		}
	}

	window.onload = function() {
		document.getElementsByName("birthDate")[0].valueAsDate = lastTenYear();

		var form = document.getElementById('signup');
		form.addEventListener('submit', function(event) {
			if (document.getElementsByName("memberId")[1].value == "") {
				alert("이메일 형식을 확인 바랍니다.");
				event.preventDefault();
				return;
			}
			if (document.getElementsByName("memberTel")[0].value == "") {
				alert("전화번호 형식을 확인 바랍니다.");
				event.preventDefault();
				return;
			}
			var password = document.getElementsByName("memberPw")[0].value;
			var passwordConfirm = document.getElementsByName("memberPwConfirm")[0].value;
			if (password != passwordConfirm) {
				alert("비밀번호가 동일하지 않습니다.");
				event.preventDefault();
				return;
			}
			var date = document.getElementsByName("birthDate")[0].valueAsDate;
			if(date > lastTenYear()) {
				alert("15세 이상 이용가능합니다.");
				event.preventDefault();
				return;
			}
			if (idcheck != "success") {
				alert("아이디 중복확인 바랍니다.")
				event.preventDefault();
				return;
			}
		});
	}

	function idCheck() {
		if (document.getElementsByName("memberId")[0].value == "" || document.getElementsByName("memberId")[1].value == "") {
			alert("이메일 형식을 확인 바랍니다.");
			return;
		}
		var id = document.getElementsByName("memberId")[0].value + "@";
		id += document.getElementsByName("memberId")[1].value;

		$.ajax({
			data : {"id" : id},
			type : "POST",
			url : "idCheck.do",
			datatype : "text",
			success : function(data) {
				if (data !== '0') {
					document.getElementsByName("memberId")[0].value = "";
					document.getElementsByName("memberId")[1].value = "";
					idcheck = "";
					alert("아이디가 존재합니다. 다른 아이디를 입력해주세요.");
				} else {
					idcheck = "success";
					alert("사용가능한 아이디입니다.");
				}
			}
		});
	}

	function telCheck() {
		if (document.getElementsByName("memberTel")[0].value == "" || document.getElementsByName("memberTel")[1].value == "" || document.getElementsByName("memberTel")[2].value == "") {
			alert("전화번호 형식을 확인 바랍니다.");
			return;
		}
		var tel = document.getElementsByName("memberTel")[0].value + "-";
		tel += document.getElementsByName("memberTel")[1].value + "-";
		tel += document.getElementsByName("memberTel")[2].value;
		console.log(tel);
		
		$.ajax({
			data : {"tel" : tel},
			type : "POST",
			url : "telCheck.do",
			datatype : "text",
			success : function(data) {
				if (data !== '0') {
					document.getElementsByName("memberTel")[0].value = "";
					document.getElementsByName("memberTel")[1].value = "";
					document.getElementsByName("memberTel")[2].value = "";
					idcheck = "";
					alert("사용중인 전화번호입니다. 다른 전화번호를 입력해주세요.");
				} else {
					idcheck = "success";
					alert("사용가능한 전화번호입니다.");
				}
			}
		});
	}
	/* 비밀번호 보이기 / 숨기기
	function showPassword(object) {
		console.log(object.src);
		var srcArray = object.src.split("/");
		var src = srcArray[srcArray.length - 2] + "/" + srcArray[srcArray.length - 1];
		console.log(src);
		if(object.src !== "img/unshow.png") {
			console.log("sssss");
		} else {
			
		}
	}
	 */
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
				<h2 class="sub-heading">회원가입</h2>
			</div>
			<div class="sub-register">
				<form id="signup" action="signup.do" method="post">
					<table class="register-contents">
						<tbody>
							<tr>
								<td><span>이름</span></td>
								<td>
									<input class="text_inpt" type="text" name="memberName" placeholder="이름" pattern="^[가-힣]{2,3}$" required autofocus>
									<span>(비밀번호 찾기에 사용되니 존재하는 이메일을 입력요망)</span>
								</td>
							</tr>
							<tr>
								<td><span>아이디</span></td>
								<td><input class="text_inpt" type="text" name="memberId" placeholder="아이디" pattern="^[a-zA-Z0-9]{6,12}$" required><span> @ </span>
								<select class="text_inpt" name="memberId">
									<option value="">선택</option>
									<option value="gmail.com">gmail.com</option>
									<option value="naver.com">naver.com</option>
									<option value="yahoo.com">yahoo.com</option>
									<option value="nate.com">nate.com</option>
									<option value="daum.net">daum.net</option>
								</select><input type="button" class="normal_red_btn" value="중복확인" onclick="idCheck()"></td>
							</tr>
							<tr>
								<td><span>비밀번호</span></td>
								<td>
									<input class="text_inpt" type="password" name="memberPw" placeholder="비밀번호 (8자이상)" pattern="^[a-zA-Z0-9]{8,12}$" required>
									<span>(비밀번호 8~12자, 영어대문자/소문자/숫자 조합 가능)</span>
								</td>
							</tr>
							<tr>
								<td><span>비밀번호 확인</span></td>
								<td>
									<input class="text_inpt" type="password" name="memberPwConfirm" placeholder="비밀번호 확인" pattern="^[a-zA-Z0-9]{8,12}$" required>
									
									<!-- 나중에 개발 예정
									<span>
										<img src="img/show.png" alt="비밀번호 보이기/숨기기" title="password (un)show" onclick="showPassword(this)">
									</span>
									 -->
									
								</td>
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
									<input class="text_inpt" style="width:100px" type="text" name="memberTel" placeholder="1234" pattern="^[0-9]{3,4}$" required><span> - </span>
									<input class="text_inpt" style="width:100px" type="text" name="memberTel" placeholder="5678" pattern="^[0-9]{4}$" required>
									<input type="button" class="normal_red_btn" value="중복확인" onclick="telCheck()">
								</td>
							</tr>
							<tr>
								<td><span>성별</span></td>
								<td>
									<input type="radio" id="gender_m" name="memberGender" value="M" checked><label for="gender_m">남성</label>
									<input type="radio" id="gender_w" name="memberGender" value="W"><label for="gender_w">여성</label>
									<span style="float:right;">(ID/PW 찾기에 사용되니 존재하는 전화번호를 입력요망)</span>
								</td>
							</tr>
							<tr>
								<td><span>생년월일</span></td>
								<td>
									<input class="text_inpt" type="date" name="birthDate">
								</td>
							</tr>
						</tbody>
					</table>
					<div style="width: 95%; margin-top: 20px;">
						<input type="submit" class="normal_gray_center_btn" value="가입">
						<input type="reset" class="normal_gray_center_btn" value="초기화">
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>