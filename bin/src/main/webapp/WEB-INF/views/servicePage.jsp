<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 서비스 관리</title>
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
<br/>
${text} 
	<form action="businessInfoDetail" name="servicePage">
		<button class="btn btn-outline-success">기본 정보 확인</button>
	</form>
	<br/>
	<!--현휘; 기업의 업종 등록 신청 페이지로 이동 (M,B,H)  -->
	<form action="serviceInsertForm">
		<input type="hidden" name="no" value="${no}" />
		<button class="btn btn-outline-info">업종 등록 신청</button>
	</form>
	<hr>
	<div class="row mt-sm-5 mt-3 mb-3">
	${servList}
	<!--현휘; 서비스단에서 가져온 서비스 리스트 // 삭제예정 -->
	${add}
	</div> <!-- end row mt-sm-5 mt-3 mb-3 -->
	<div>
		<input type="button" class="btn btn-outline-danger" value="회원탈퇴" onclick="forward(this)" />
	</div>
<%@ include file="footer.html"%>
	</div>
</body>
<script>
	function forward(button) {
		var frm = document.servicePage;
		if (button.value == '회원탈퇴') {
			var det;
			det = confirm("예약 내역을 제외한 모든 데이터가 삭제되고, 재가입 시 데이터 복구가 어렵습니다.정말 탈퇴하시겠습니까?");
			if (det) {
				frm.action = "businessPartDelete";
			} else {
				frm.action = "servicePage";
			}
		}
		frm.submit();
	}
</script>
</html>