<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1">
<tr><td>글번호: </td><td>${bbo_no}</td> <td>업종: </td><td>${bct_name} </td> <td>카테고리: </td><td>${bbc_name}</td>
<td>제목: </td><td>${bbo_title}</td> <td>작성일: </td><td>${bbo_date}</td></tr>
<tr><td colspan="10">${bbo_ctt}</td></tr>
</table>

<button onclick="location.href='./businessNoticeUpdateForm?${bbo_no}'">수정</button>
<form id="businessNoticeDelete" action="businessNoticeDelete">
	<input type="hidden" name="bbo_no" value="${bbo_no}">
	<button onclick="deleteChk(this);">삭제</button>
</form>
<script>
function deleteChk(button) {
	confirm('삭제가 확실한가요?');
}
${alert}
</script>
</body>
</html>