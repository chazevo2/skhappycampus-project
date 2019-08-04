<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
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
<link type="text/css" rel="stylesheet" href="./css/memberList.css">
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
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
				<h2 class="sub-heading">회원 관리</h2>
				<div class="sub_heading_text">회원 목록 확인 및 관리 페이지입니다.</div>
			</div>
			<div class="sub-memberList">
				<div id="memberChart">
					<div class="chart_left_box">
						<p class="index_title">회원 성별 차트</p>
						<%
							HashMap<String, Integer> genderRate = (HashMap<String, Integer>) request.getAttribute("genderRate");
							Set<String> genderKeys = genderRate.keySet();
							Iterator<String> iterator1 = genderKeys.iterator();
						%>
						<script type="text/javascript">
							google.charts.load("current", {	packages : [ "corechart" ]});
							google.charts.setOnLoadCallback(drawChart);
							function drawChart() {
								var data = google.visualization.arrayToDataTable([
										[ 'Task', 'Hours per Day' ],
								<%		
									while (iterator1.hasNext()) {
										String key = (String) iterator1.next();
								%>
										[ '<%= key %>', <%= genderRate.get(key) %> ],
								<%
									}
								%>
								]);
					
								var options = {title : '남녀 비율'};
					
								var chart = new google.visualization.PieChart(document.getElementById('genderChart'));
								chart.draw(data, options);
							}
						</script>
						<div id="genderChart"  style="width: 550px; height: 400px;"></div>

					</div>

					<div class="chart_right_box">
						<p class="index_title">회원 연령별 차트</p>
						<%
							HashMap<String, Integer> ageRate = (HashMap<String, Integer>) request.getAttribute("ageRate");
							Set<String> ageKeys = ageRate.keySet();
							Iterator<String> iterator2 = ageKeys.iterator();
						%>
						<script type="text/javascript">
							google.charts.load("current", {	packages : [ "corechart" ]});
							google.charts.setOnLoadCallback(drawChart2);
							function drawChart2() {
								var data = google.visualization.arrayToDataTable([
										[ 'Task', 'Hours per Day' ],
								<%		
									while (iterator2.hasNext()) {
										String key = (String) iterator2.next();
								%>
										[ '<%= key %>', <%= ageRate.get(key) %> ],
								<%
									}
								%>
								]);
					
								var options = {title : '연령별 비율'};
					
								var chart = new google.visualization.PieChart(document.getElementById('ageChart'));
								chart.draw(data, options);
							}
						</script>
						<div id="ageChart"  style="width: 550px; height: 400px;"></div>
					
					</div>
				</div>
				<div id="memberList-content">
					<table class="tb_base" summary="회원정보 테이블입니다.">
						<colgroup>
						<col width="18%">
						<col width="*%" span="5">
						<col width="24%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">아이디(이메일)</th>
								<th scope="col">이름</th>
								<th scope="col">휴대폰</th>
								<th scope="col">게시글수</th>
								<th scope="col">댓글수</th>
								<th scope="col">관리</th>
								<th scope="col">등급</th>
							</tr>
						</thead>
						<tbody class="tb_center">
							<c:forEach var="m" items="${list}">
								<tr>
									<td><c:out value="${m.getMemberId()}" /></td>
									<td><c:out value="${m.getMemberName()}" /></td>
									<td><c:out value="${m.getMemberTel()}" /></td>
									<td>21</td>
									<td>12</td>
									<td>
										<c:if test="${m.getMemberGrade() != 'A'}">
											<input type="button" class="delbtn" value="강퇴" onclick="delMember('${m.getMemberId()}')">
										</c:if>
									</td>
									<td>
										<c:choose>
											<c:when test="${m.getMemberGrade() == 'G'}">일반</c:when>
											<c:otherwise>관리자</c:otherwise>
										</c:choose>
										<select id="" title="등급" class="select_st">
											<option value="G">일반</option>
											<option value="A">관리자</option>
										</select>
										<input type="button" class="gradebtn" value="변경완료" onclick="chGrade(this, '${m.getMemberId()}', '${m.getMemberGrade()}')">
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<script type="text/javascript">
						function delMember(id) {
							var choice = confirm("정말로 " + id + "회원을 삭제하시겠습니까?");
							if(choice) {
								$.ajax({
									data : {"id" : id},
									type : "POST",
									url : "delMember.do",
									datatype : "json",
									success : function(data) {
										console.log(data);
										if(data.msg !== "fail") {
											alert(id + "회원을 강퇴하였습니다.")
											location.href = "memberList";
										} else {
											alert("회원 삭제를 실패하였습니다.");
										}
									}
								});
							} 
						}
						
						function chGrade(obj, id, grade) {
							var select = obj.parentNode.getElementsByClassName("select_st")[0];
							var newGradeChar = select.options[select.selectedIndex].text;
							var newGrade = ""
							if(newGradeChar === "일반") {
								newGrade = "G";
							} else {
								newGrade = "A";
							}
							if(grade === newGrade) {
								alert("이미 같은 등급입니다.");
								return;
							}
							
							var choice = confirm("정말로 " + id + "회원의 등급을 변경하시겠습니까?");
							if(choice) {
								$.ajax({
									data : "id=" + id + "&newGrade=" + newGrade,
									type : "POST",
									url : "chGrade.do",
									datatype : "json",
									success : function(data) {
										console.log(data);
										if(data.msg !== "fail") {
											alert(id + "회원의 등급을 변경하였습니다.")
											location.href = "memberList";
										} else {
											alert("회원 등급 변경을 실패하였습니다.");
										}
									}
								});
							} 
						}
					</script>

					<!-- 페이징 -->
					<ul class="paging">
						<li class="btn_first"><a href="#" onclick="cfnPageLink(1); return false;" title="처음"><span class="hidden">처음</span></a></li>
						<li class="btn_prev"><a href="#" onclick="cfnPageLink(1); return false;" title="이전 10 페이지"><span class="hidden">이전 10페이지 이동</span></a></li>
						<li class="on"><a href="javascript://" title="현재페이지">1</a></li>
						<li class="btn_next"><a href="#" onclick="cfnPageLink(1); return false;" title="다음 10 페이지"><span class="hidden">다음 10 페이지 이동</span></a></li>
						<li class="btn_last"><a href="#" onclick="cfnPageLink(1); return false;" title="마지막"><span class="hidden">마지막</span></a></li>
					</ul>

				</div>
			</div>
		</div>
	</div>
</body>
</html>