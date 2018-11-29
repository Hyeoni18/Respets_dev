<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현휘// 직원관리 페이지</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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
<form action="stepInsertFormBut">
<button class="btn btn-outline-info">직원 등록 하기</button> <br/><br/>
${code} <br/>
<div id="List">
</div>
</form>
</div>
</body>
<script>


function chk(val) {
	console.log(val);
	$("#List *").remove();
	var code = val;
	var url = "stepList?bct_code="+code;
	Aj(url,"#List");
	function Aj(url, position) {
		$.ajax({
			url: url,
			type: "post",
			dataType: "text",
			contentType: 'application/json; charset=utf-8',
			success: function(data) {
				$(position).append(data);
			},
			error: function(error) {
				console.log("error");
			}
		}); //ajax End
	} 
}
</script>
</html>