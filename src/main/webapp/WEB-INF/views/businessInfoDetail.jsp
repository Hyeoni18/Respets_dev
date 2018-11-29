<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 기본 정보 확인 </title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/dist/assets/images/logo-sm.png">

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />

</head>
<body>
	<%@ include file="left-sidebar.jsp"%>
	<div class="content-page">
		<%@ include file="topbar-dashboard.jsp"%>

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
	
<%@ include file="footer.html"%>
	</div>
</body>
<script>
	var jsonData = ${result};
	$.each(jsonData, function(key, value) {
		$('#' + key).html(value);
	});
</script>
</html>