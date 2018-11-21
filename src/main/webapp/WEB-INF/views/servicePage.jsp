<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현휘 // 서비스페이지</title>
</head>
<body>
${text} 
	<p>${no}</p>
	<form action="businessInfoDetail" name="servicePage">
		<button>기본 정보 확인</button>
	</form>
	<!--현휘; 기업의 업종 등록 신청 페이지로 이동 (M,B,H)  -->
	<form action="serviceInsertForm">
		<input type="hidden" name="no" value="${no}" />
		<button>업종 등록 신청</button>
	</form>
	<hr>
	${servList}
	<!--현휘; 서비스단에서 가져온 서비스 리스트 // 삭제예정 -->
	${add}
	<div>
		<input type="button" value="회원탈퇴" onclick="forward(this)" />
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