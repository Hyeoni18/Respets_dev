<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 비밀번호 변경</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description">
<meta content="Coderthemes" name="author">
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/dist/assets/images/logo-sm.png">

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css">
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css">
</head>
<body class="authentication-bg enlarged">
	<div class="account-pages mt-5 mb-5">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-lg-5">
					<div class="card">

						<!-- Logo -->
						<div class="card-header pt-4 pb-4 text-center bg-success">
							<a href="./"> <span><img
									src="resources/dist/assets/images/logo-white.png" alt="리스펫츠 로고"
									height="30"></span>
							</a>
						</div>
						<!-- Logo end -->

						<div class="card-body p-4">
							<div class="text-center w-75 m-auto">
								<h4 class="text-dark-50 text-center mt-0 font-weight-bold">비밀번호 재설정</h4>
								<p class="text-muted mb-4"></p>
							</div>
							<!--현휘; 받은 메일을 통해서만 들어올 수 있는 폼. 비밀번호를 변경하는 폼.-->
<form name="resetMyPwForm">
<div class="form-group">
새 비밀번호: <input type="password" class="form-control" name="per_pw" id="per_pw"/> <br/>
새 비밀번호 확인: <input type="password" class="form-control" name="check_pw" id="check_pw"/> <br/>
</div>
<input type="hidden" name="per_email" value="${email}"/>
<input type="hidden" name="code" value="${code}"/>
<input type="hidden" name="type" value="${type}"/>
<div class="form-group mb-0 text-center">
<button class="btn btn-success" onclick="pwCheck()">비밀번호 변경하기</button>
</div>
</form>
</div>
						<!-- end card-body -->
					</div>
					<!-- end card -->
					<!-- end col -->
				</div>
				<!-- end row -->
			</div>
			<!-- end col -->
		</div>
		<!-- end row -->
	</div>
	<!-- end container -->
	<!-- end page -->

</body>
<script>
function pwCheck() {
	var per = document.getElementById("per_pw").value; 
	var check = document.getElementById("check_pw").value;
	var frm = document.resetMyPwForm;
	if(per == check) {
		frm.method = "POST";
		frm.action = "updateMyPw";
	} else {
		alert("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
		frm.action = "resetMyPwForm";
	}
}
</script>
</html>