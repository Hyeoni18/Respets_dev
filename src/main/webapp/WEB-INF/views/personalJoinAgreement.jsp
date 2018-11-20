<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
동의? <input type="checkbox" name="agreeChk" id="agreeChk" />
<input type="button" value="동의" onclick="agreementChk()"/> 
</body>
<script>
function agreementChk() {
	if($('#agreeChk').is(':checked')) {
		location.href = "./personalJoinForm";
	}else {
		alert("동의하지 않으면 가입불가");
	}
}
</script>
</body>
</html>