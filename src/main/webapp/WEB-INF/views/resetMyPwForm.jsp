<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현휘 // 새비밀번호 작성폼 </title>
</head>
<body>
<!--현휘; 받은 메일을 통해서만 들어올 수 있는 폼. 비밀번호를 변경하는 폼.-->
<form name="resetMyPwForm">
새 비밀번호 : <input type="password" name="per_pw" id="per_pw"/> <br/>
새 비밀번호 확인: <input type="password" name="check_pw" id="check_pw"/> <br/>
<input type="hidden" name="per_email" value="${email}"/>
<input type="hidden" name="code" value="${code}"/>
<input type="hidden" name="type" value="${type}"/>
<button onclick="pwCheck()"> 비밀번호 재설정 버튼 </button>
</form>
</body>
<script>
function pwCheck() {
	var per = document.getElementById("per_pw").value; 
	var check = document.getElementById("check_pw").value;
	var frm = document.resetMyPwForm;
	if(per == check) {
		frm.method = "POST";
		frm.action = "updateMyPw";
	} else {
		alert("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
		frm.action = "resetMyPwForm";
	}
}
</script>
</html>