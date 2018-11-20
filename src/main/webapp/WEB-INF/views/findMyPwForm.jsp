<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현휘 // 비밀번호 찾는 폼 </title>
</head>
<body>
<!--현휘; 치환한 이메일을 화면에 보여주고 로그인을 하던 비밀번호 찾기를 진행하는 폼 -->
<p> 회원님의 이메일 주소는 </p>
<p> ${showEmail} 입니다. </p>
<form action="findMyPw" name="findMyPwForm" method="POST">
<input type="hidden" name="email" value="${email}"/> <!-- 정상적인 이메일주소 -->
<input type="hidden" name="type"	value="${type}"/> <!-- 회원 종류 -->
<input type="submit" value="비밀번호 찾기"/>
</form>
<form action="./" name="loginForm">
<button> 로그인 하기 </button>
</form>
</body>
</html>