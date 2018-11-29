<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	
</script>
<style>
#div_menu {
width: 15%;
float:left;
text-align: center;
}
#div_content {
width: 85%;
float:right;
}
</style>
</head>
<body>
<div id="div_top"><jsp:include page="topBar.jsp"/></div>
<div id="div_menu"><jsp:include page="businessButtonPage.jsp"/></div>
<div id="div_content">
	<h1>기본 정보 확인</h1>
	<hr />
	<form action="businessInfoUpdateForm">
		<div>
			<table>
				<tr>
					<td>사업자 번호 :</td>
					<td id="BUS_LCNO"></td>
				</tr>
				<tr>
					<td>업체명 :</td>
					<td id="BUS_NAME"></td>
				</tr>
				<tr>
					<td>대표자명 :</td>
					<td id="BUS_CEO"></td>
				</tr>
				<tr>
					<td>업체 연락처 :</td>
					<td id="BUS_PHONE"></td>
				</tr>
				<tr>
					<td>사업장 주소 :</td>
					<td><span id="BUS_ADDR"></span>&nbsp;|&nbsp;<span
						id="BUS_ADDR2"></span></td>
				</tr>
				<tr>
					<td>주력서비스 :</td>
					<td id="BCT_NAME"></td>
				</tr>
			</table>
		<br> ${img}
		</div>
		<h3>※ 업종 정보 및 직원 정보를 등록하셔야 예약서비스 제공이 가능합니다.</h3>
		<div>
			<button class="btn btn-outline-secondary">수정</button>
		</div>
	</form>
	</div>
</body>
<script>
	var jsonData = ${result};
	$.each(jsonData, function(key, value) {
		$('#' + key).html(value);
	});
</script>
</html>