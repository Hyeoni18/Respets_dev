<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1> 업종 정보 확인</h1>
-서비스 종류 : ${bct_name} <br/>
-업체명 : ${name} <br/>
-연락처 : ${phone} <br/>
-영업시간 : ${work} <br/>
-고정휴무일 : ${holiday} <br/>
-제공서비스 : ${menu} <br/>
-서비스 가능한 동물종류 : ${animal} <br/>
<form name="serviceDetail">
<input type="hidden" name="bct_code" value="${bct_code}"/>
<input type="hidden" name="first" value="${first}"/>
<input type="button" name="but" value="수정" onclick="update(this)"/>
<input type="button" name="but" value="삭제" onclick="update(this)"/>
</form>
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