<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>개인 정보 확인</h1>
	<hr />
	<form name="myInfo">
		<div id="cold">
			<div id="info">
				- 이름 : ${mb.per_name}${per_name}<br />- 이메일 : ${mb.per_email} <br />- 연락처 :
				${mb.per_phone}<br />
			</div>
			<div>사진 : ${mb.per_photo}</div>
		</div>
		<input type="button" value="비밀번호변경" onclick="forward(this)" /> <input
			type="button" value="정보수정" onclick="forward(this)" /> <input
			type="button" value="탈퇴" onclick="forward(this)" />
	</form>
	${delete} ${infoSuccess}
</body>
<script>
	function forward(button) {
		var frm = document.myInfo;
		if (button.value == '비밀번호변경') {
			frm.action = "myPwUpdateForm";
		}
		if (button.value == '정보수정') {
			frm.action = "myInfoUpdateForm";
		}
		if (button.value == '탈퇴') {
			var det;
			det = confirm("제가입 시 데이터 복구가 어렵습니다. 정말 탈퇴하시겠습니까?");
			if (det) {
				frm.action = "personalPartDelete";
			} else {
				frm.action = "myInfo"
			}
		}
		frm.submit();
	}
</script>
</html>