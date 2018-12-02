<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 직원 등록 하기 </title>
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
	<form action="stepInsert" name="step" method="post"
		enctype="multipart/form-data">
		 ${type}
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
<%@ include file="footer.html"%>
	</div>
	<!-- App js -->
    <script src="/resources/dist/assets/js/app.min.js"></script>
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