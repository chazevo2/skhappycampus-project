<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IdolLove</title>
<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 

<!-- include summernote css/js -->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.js"></script>
<script type="text/javascript">
	var msg = '${param.msg}';
	if(msg != null && msg === "fail") {
		alert("글작성을 실패하였습니다.");
	}

	function sendFile(file, editor) {
		data = new FormData();
		data.append("file", file);
		$.ajax({
			data : data,
			type : "POST",
			url : "board/imgUpload.jsp",
			cache : false,
			contentType : false,
			processData : false,
			enctype : "multipart/form-data",
			success : function(data) {
				$(editor).summernote('editor.insertImage', data.url);
			}
		});
	}
	
	window.onload = function() {
		var form = document.getElementById('write');
		form.addEventListener('submit', function(event) {
			if (document.getElementsByName("category")[0].value == "") {
				alert("카테고리를 지정해주시기 바랍니다.");
				event.preventDefault();
				return;
			}
			if (document.getElementsByName("boardTitle")[0].value == "") {
				alert("글 제목을 작성해주시기 바랍니다.");
				event.preventDefault();
				return;
			}
			if (document.getElementsByName("boardContent")[0].value == "") {
				alert("글 내용을 작성해주시기 바랍니다.");
				event.preventDefault();
				return;
			}
			
		});
	}
	
	function doWrite() {
		$("#submit").click();
	}
</script>
<link type="text/css" rel="stylesheet" href="./css/header-2.css">
<link type="text/css" rel="stylesheet" href="./css/write.css">
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
			<div class="logoutBtn">
				<img src="img/logout.png"><span><a href="logout.do">로그아웃</a></span>
			</div>
			<div class="mypageBtn">
				<img src="img/mypage.png"><span><a href="myInfo">마이페이지</a></span>
			</div>
		</div>
	</header>	
	
	<!-- content -->
	<div class="sub-content">
		<div class="write-container">
			<div class="write-info">
				<h2 class="info-heading">게시글 작성</h2>
			</div>
			<div class="write-content">
				<div id="con_body">
					<form id="write" action="write.do" method="post">
						<table class="tb_write" summary="글작성 테이블입니다.">
							<colgroup>
								<col width="15%">
								<col width="85%">
							</colgroup>
							<tbody>
							<tr>
								<th scope="row">카테고리 선택</th>
								<td>
									<select name="category" title="카테고리 선택" class="select_st">
										<option value="">구분</option>
										<option value="트와이스">트와이스</option>
										<option value="블랙핑크">블랙핑크</option>
										<option value="방탄소년단">방탄소년단</option>
										<option value="워너원">워너원</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row">글제목</th>
								<td><input name="boardTitle" class="input_st" type="text" placeholder="글제목을 입력해주세요" title="글제목 입력" style="width:100%;" required></td>
							</tr>
							<tr>
								<th scope="row">글내용</th>
								<td><textarea id="summernote" name="boardContent" required></textarea></td>
							</tr>
							</tbody>
						</table>
	
						<p class="ta_c mt30">
							<input type="submit" id="submit" style="display: none">
							<a class="goBackBtn" onclick="window.history.go(-1);">이전으로</a>
							<a class="writeBtn" href="#" onclick="doWrite()">글작성</a>
						</p>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<script>
		$(document).ready(function() {
			$('#summernote').summernote({
				height : 300,
				width : 855,
				callbacks : {
					onImageUpload : function(files, editor, welEditable) {
						sendFile(files[0], this);
					}
				}
			});
		});
	</script>
</body>
</html>