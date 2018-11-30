<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 나의 회원 정보</title>
<!-- App favicon -->
<link rel="shortcut icon" href="resources/images/logo-sm.png">
<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<h1>개인 정보 수정</h1>
	<hr />
	<form id="myInfoUpdate" action="myInfoUpdate" method="post">
		- 이메일 : ${mb.per_email}
		<hr>
		<div>
			- 프로필 사진 :<input type="file" name="mainPhoto"
				onchange="fileChk(this)" /><br /> <input type="hidden"
				name="fileCheck" id="fileCheck" value="0" />
		</div>
		- 이름 : <input type="text" name="per_name" value="${mb.per_name}" /><br>-
		연락처 : <input type="text" name="per_phone" value="${mb.per_phone}" />
		<hr />
		<input type="submit" id="sub" value="수정완료" /> <input type="reset"
			value="리셋" />
	</form>
</body>
<script>
	function fileChk(file) {
		if (file.value == "") {
			$("#fileCheck").val(0);
		} else {
			$("#fileCheck").val(1);
		} // else End
	} // fct End
</script>
</html>