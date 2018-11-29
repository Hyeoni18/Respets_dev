<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<style>
#div_menu {
width: 15%;
float:left;
text-align: center;
}
#div_content {
width: 85%;
float:right;
}
</style>
</head>
<body>
<div id="div_top"><jsp:include page="topBar.jsp"/></div>
<div id="div_menu"><jsp:include page="businessButtonPage.jsp"/></div>
<div id="div_content">
<form name="stepDetail" method="post"
			enctype="multipart/form-data">
${text}
</form>
</div>
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