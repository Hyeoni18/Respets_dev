<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현휘// 직원관리 페이지</title>
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.9.0/jquery.js"></script>
</head>
<body>
<form action="stepInsertFormBut">
<button>직원 추가 버튼</button> <br/>
${code} <br/>
<div id="List">

</div>
</form>
</body>
<script>
function chk(val) {
	console.log("찍혀랏");
	var code = val;
	var url = "stepList?bct_code="+code;
	Aj(url,"#List");
	function Aj(url, position) { //파라미터 값이 3개라고 꼭 3개를 받을 필요는 없다.
		$.ajax({
			url: url,
			type: "post",
			//dataType: 'html',
			contentType: 'application/json; charset=utf-8',
			success: function(html) {
				$(position).html(html);
			},
			error: function(error) {
				console.log("error");
			}
		}); //ajax End
	} 
}

function chk(val) {
	var code = val;
	var url = "stepList?bct_code="+encodeURI(code);
	Aj(url,"#List");
	function Aj(url, position) {
		$.ajax({
			url: url,
			type: "post",
			dataType: "text",
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