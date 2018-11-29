<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 업종 정보 확인 </title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/dist/assets/images/logo-sm.png">

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />

</head>
<body>
	<%@ include file="left-sidebar.jsp"%>
	<div class="content-page">
		<%@ include file="topbar-dashboard.jsp"%>
<h1> 업종 정보 확인</h1>
서비스 종류 : ${bct_name} <br/>
업체명 : ${name} <br/>
연락처 : ${phone} <br/>
영업시간 : ${work} <br/>
고정휴무일 : ${holiday} <br/>
제공서비스 : ${menu} <br/>
서비스 가능한 동물종류 : ${animal} <br/><br/>
<form name="serviceDetail">
<input type="hidden" name="bct_code" value="${bct_code}"/>
<input type="hidden" name="first" value="${first}"/>
<input type="button" class="btn btn-outline-secondary" name="but" value="수정" onclick="update(this)"/>
<input type="button" class="btn btn-outline-danger" name="but" value="삭제" onclick="update(this)"/>
</form>
<%@ include file="footer.html"%>
	</div>
</body>
<script>
function update(but) {
	var frm = document.serviceDetail;
	if (but.value == '수정') {
		frm.action = "serviceUpdateForm";
		frm.method = "post";
	} else if (but.value =='삭제') {
		frm.action = "serviceDelete";
		frm.method = "post";
	}
	frm.submit();
}
</script>
</html>