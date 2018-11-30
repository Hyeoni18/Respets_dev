<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>미인증기업목록</h3>
<table border='1'>
	<tr>
	<td>회원번호</td>
	<td>업종</td>
	<td>이메일주소</td>
	<td>업체명</td>
	<td>대표자명</td>
	<td>사업자등록번호</td>
	<td>연락처</td>
	<td>주소</td>
	<td>회원가입일시</td>
	</tr>
	${bList}
</table>
</body>
</html>