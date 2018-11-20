<!-- 서진 : 기업 회원 가입 동의 폼 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>리스펫츠 : 기업 회원 가입 동의</title>
</head>
<body>
	동의?
	<input type="checkbox" id="agreeChk" />
	<input type="button" value="동의" onclick="agreementChk()" />
</body>
<script>
	function agreementChk() {
		if ($('#agreeChk').is(':checked')) {
			location.href = "./businessJoinForm";
		} else {
			alert("약관에 동의해주세요.");
		} // else End
	} // fct End
</script>
</html>
