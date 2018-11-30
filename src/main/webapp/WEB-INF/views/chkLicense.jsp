<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border='1'>
	<tr>
		<td>서비스종류</td>
		<td>${bct_name}</td>
	</tr>
	<tr>
		<td>업체명</td>
		<td>${bus_name}</td>
	</tr>
	<tr>
		<td>연락처</td>
		<td>${bus_phone}</td>
	</tr>
	<tr>
		<td>이메일</td>
		<td>${bus_email}</td>
	</tr>
	<tr>
		<td colspan='2'>사업자등록증</td>
	</tr>
	<tr>
		<td colspan='2'><img alt="사업자등록증" src="${glr_loc}${glr_file}"/></td>
	</tr>
</table>
<button onclick="confirmChk(this)">승인</button>

<button onclick="location.href='./unconfirmBusiness'">취소</button>
</body>
<script>
function confirmChk(button) {
	var con = confirm('승인하시겠습니까?');
	con;
	if(con == false) {
		return false;
	}else {
		${alert}
		location.href='./confirmLicense?${bus_no}';
	}
}
</script>
</html>