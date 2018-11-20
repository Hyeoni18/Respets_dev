<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>개인 정보 수정</h1>
	<hr />
	<form id="myInfoUpdate" action="myInfoUpdate"
		method="post">
		- 이메일 : ${mb.per_email}
		<hr>
		<div>
			- 프로필 사진 : <input type="text" name="per_photo"
				value="${mb.per_photo}" />
		</div>
		- 이름 : <input type="text" name="per_name" value="${mb.per_name}" /><br>-
		연락처 : <input type="text" name="per_phone" value="${mb.per_phone}" />
		<hr />
		<input type="submit" id="sub" value="수정완료" /> <input type="reset"
			value="리셋" />
	</form>
</body>
</html>