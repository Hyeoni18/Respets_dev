<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>전체 예약 목록</h1>
	<form id="serch">
		<input type="text" name="tt" /><input type="button" name="sub"
			onclick="serch(this)" />
	</form>
	<div id="list">${toSdList}</div>
</body>
<script>
var tt = document.serch.tt.val();
console.log(tt);
function serch(button){
	$.ajax
}
</script>
</html>