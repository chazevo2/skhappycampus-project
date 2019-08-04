<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IdolLove</title>
<link type="text/css" rel="stylesheet" href="./css/signup.css">
<style type="text/css">
.content {
	text-align: center;
}
#result {
	margin-left: 20px;
	float: left;
}
#change {
	height: 30px;
    width: 100px;
    border: 1px solid #d6d6d6;
    background-color: #333333;
    color: #fff;
    cursor: pointer;
    margin-top: 15px;
    margin-left: 10px;
}
</style>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
<script type="text/javascript">
	function change() {
		if(opener == null) {
			alert("잘못된 접근입니다.");
			window.close();
			return;
		}
		var id = opener.document.getElementById("memberId").value;
		var pw = $("#memberPw").val();
		var newPw = $("#newPassword").val();
		var newPwConfirm = $("#newPwConfirm").val();
		if(newPw !== newPwConfirm) {
			alert("새 비밀번호가 동일하지 않습니다.");
		} else {
			if(pw === newPw) {
				alert("현재 비밀번호와 새 비밀번호가 같습니다.")
			} else {
				$.ajax({
					data : "id=" + id + "&pw=" + pw + "&newPw=" + newPw,
					type : "POST",
					url : "pwChange.do",
					success : function(data) {
						console.log(data);
						if(data === "access") {
							alert("잘못된 접근입니다.")
							window.close();
						} else {
							if(data === "notMatch") {
								alert("회원 정보를 확인부탁드립니다.");
								$("#memberPw").val("");
							} else {
								alert("비밀번호 변경이 완료되었습니다.");
								window.close();
							}
						}
					}
				});
			}
		}
	}
</script>
</head>
<body>
	<div class="content">
		<table class="register-contents">
			<tbody>
				<tr>
					<td><span>현재 비밀번호</span></td>
					<td>
						<input class="text_inpt" type="password" id="memberPw" placeholder="비밀번호 (8자이상)" pattern="^[a-zA-Z0-9]{8,12}$" required>
					</td>
				</tr>
				<tr>
					<td><span>새 비밀번호</span></td>
					<td>
						<input class="text_inpt" type="password" id="newPassword" placeholder="비밀번호 (8자이상)" pattern="^[a-zA-Z0-9]{8,12}$" required>
					</td>
				</tr>
				<tr>
					<td><span>새 비밀번호 확인</span></td>
					<td>
						<input class="text_inpt" type="password" id="newPwConfirm" placeholder="비밀번호 확인" pattern="^[a-zA-Z0-9]{8,12}$" required>
					</td>
				</tr>
			</tbody>
		</table>
		<br>
		<div style="text-align: center;">
			<span id="result"></span>
			<input type="button" id="change" value="비밀번호 변경" onclick="change()">
		</div>
	</div>
</body>
</html>