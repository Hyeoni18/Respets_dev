<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현휘// 직원 등록 페이지</title>
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.9.0/jquery.js"></script>
</head>
<body>
	<form action="stepInsert" name="step" method="post"
		enctype="multipart/form-data">
		-업종: ${type} <br /> 
		-프로필사진: <input type="file" name="emp_photo" /><br />
		-이름: <input type="text" name="emp_name" /><br /> 
		-직급: <input type="text" name="emp_pos" /><br /> 
		-담당분야: <input type="text" name="emp_part" /><br /> 
<div id="changeCode"> 
-근무시간: ${time} <br/>
-점심시간: ${lunch} <br/>
-고정휴무일: ${holiday} <br/>
</div>
-자격증: <input type="file" name="emp_license"/><br/>
*안내- 등록신청시 등록완료까지 인증시간이 소요됩니다.<br/>
<button>등록 버튼</button>
	</form>
</body>
<script>
function chk(val) {
	var radio = $('input:radio[name=bct_code]');
	var code = val.value;
	console.log(code+"mm");
	
	var url = "stepInsertForm?bct_code="+code;
	Aj(url,"#changeCode");
	function Aj(url, position) { 
		$.ajax({
			url: url,
			type: "post",
			dataType: "text", 
			success: function(data) {
				$(position).html(data);
			},
			error: function(error) {
				console.log("error");
			}
		}); //ajax End
	} 
}
</script>
</html>