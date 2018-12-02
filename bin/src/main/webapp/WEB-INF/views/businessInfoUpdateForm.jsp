<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 기본 정보 수정</title>
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
	<h1>기본 정보 수정</h1>
	<hr />
	<form action="businessInfoUpdate" method="post">
		<table>
			<tr>
				<td>사업자 번호 :</td>
				<td id="BUS_LCNO"></td>
			</tr>
			<tr>
				<td>업체명 :</td>
				<td><input type="text" name="bus_name" value="${bmap.BUS_NAME}"/></td>
			</tr>
			<tr>
				<td>대표자명 :</td>
				<td id="BUS_CEO"><%-- <input type="text" value="${bmap.BUS_CEO}" /> --%></td>
			</tr>
			<tr>
				<td>업체 연락처 :</td>
				<td><input type="text" name="bus_phone" value="${bmap.BUS_PHONE}" /></td>
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
			<tr>
				<td>대표사진 :</td>
				<td><input type="file" name="mainPhoto"
					onchange="fileChk(this)" /><br /> <input type="hidden"
					name="fileCheck" id="fileCheck" value="0" /></td>
			</tr>
		</table> <br/>
		<button class="btn btn-outline-secondary">수정하기</button>
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
<script>
	function fileChk(file) {
		if (file.value == "") {
			$("#fileCheck").val(0);
		} else {
			$("#fileCheck").val(1);
		} // else End
	} // fct End
</script>
<!-- <script>
	// 다음 주소 API 함수
	function findAddr() {
		new daum.Postcode(
				{
					oncomplete : function(data) {
						// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

						// 각 주소의 노출 규칙에 따라 주소를 조합한다.
						// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
						var fullAddr = ''; // 최종 주소 변수
						var extraAddr = ''; // 조합형 주소 변수

						// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
						if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
							fullAddr = data.roadAddress;

						} else { // 사용자가 지번 주소를 선택했을 경우(J)
							fullAddr = data.jibunAddress;
						}

						// 사용자가 선택한 주소가 도로명 타입일때 조합한다.
						if (data.userSelectedType === 'R') {
							// 법정 동명이 있을 경우 추가한다.
							if (data.bname !== '') {
								extraAddr += data.bname;
							}
							// 건물명이 있을 경우 추가한다.
							if (data.buildingName !== '') {
								extraAddr += (extraAddr !== '' ? ', '
										+ data.buildingName : data.buildingName);
							}
							// 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
							fullAddr += (extraAddr !== '' ? ' (' + extraAddr
									+ ')' : '');
						}

						// 우편번호와 주소 정보를 해당 필드에 넣는다.
						document.getElementById('우편번호').value = data.zonecode; //5자리 새우편번호 사용
						document.getElementById('주소').value = fullAddr;

						// 커서를 상세주소 필드로 이동한다.
						document.getElementById('상세주소').focus();
					}
				}).open();
	}
</script> -->
</html>