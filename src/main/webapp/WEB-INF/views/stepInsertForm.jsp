<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현휘// 직원 등록 페이지</title>
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
	<form action="stepInsert" name="step" method="post"
		enctype="multipart/form-data">
		업종: ${type}
		프로필사진: <input type="file" name="emp_photo" /><br />
		이름: <input type="text" name="emp_name" /><br /> 
		직급: <input type="text" name="emp_pos" /><br /> 
		담당분야: <input type="text" name="emp_part" /><br /> 
<div id="changeCode"> 
근무시간: ${time} <br/>
점심시간: ${lunch} <br/>
고정휴무일: ${holiday} <br/>
</div>
자격증: <input type="file" name="emp_license"/><br/>
*안내- 등록신청시 등록완료까지 인증시간이 소요됩니다.<br/><br/>
<button class="btn btn-outline-secondary">등록 하기</button>
	</form>
</div>
</body>
<script>
function chk(val) {
	var code = val;
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