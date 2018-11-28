<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현휘// 직원관리 페이지</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<div id="div_top"><jsp:include page="topBar.jsp"/></div>
<form action="stepInsertFormBut">
<button>직원 추가 버튼</button> <br/>
${code} <br/>
<div id="List">

</div>
</form>
</body>
<script>


function chk(val) {
	$("#List *").remove();
	var code = val.value;
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