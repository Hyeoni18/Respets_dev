<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<div id="div_top"><jsp:include page="topBar.jsp"/></div>
<form name="stepDetail" method="post"
			enctype="multipart/form-data">
${text}
</form>
</body>
<script>
function but(val) {
	var frm = document.stepDetail;
	if (val.value == '수정완료') {
		frm.action = "stepUpdate";
	} else if (val.value == '삭제') {
		frm.action = "stepDelete";
	}
	frm.submit();
}
</script>
</html>