<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현휘 // 서비스페이지</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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
<div id="div_menu"><jsp:include page="businessMenu.jsp"/></div>
<div id="div_content">
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