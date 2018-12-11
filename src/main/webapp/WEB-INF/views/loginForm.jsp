<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Respets :: 로그인</title>
<!-- App favicon -->
<link rel="shortcut icon" href="resources/dist/assets/images/logo-sm.png">

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
									height="45" width="250"></span>
							</a>
						</div>

						<div class="card-body p-4">
							<div class="text-center w-75 m-auto">
								<h4 class="text-dark-50 text-center mt-0 font-weight-bold">로그인</h4>
								<p class="text-muted mb-4">계정과 비밀번호를 입력해주세요.</p>
							</div>

							<form action="loginProcess" method="post">
								<div class="form-group">
									<a href="./findMyIdForm" class="text-muted float-right">
									<small>이메일/비밀번호 찾기</small></a> 
																		
									<label for="email">이메일 주소</label> 
									<input class="form-control" type="text" id="email" name="email" placeholder="이메일을 입력해주세요.">
								</div>

								<div class="form-group">
									
									<label for="pw">비밀번호</label> <input
										class="form-control" type="password" id="pw" name="pw"
										placeholder="비밀번호를 입력해주세요.">
								</div>
								
								<div class="form-group mb-0 text-center">
									<button class="btn btn-success" type="submit">로그인</button>
								</div>
							</form>
							<div class="row mt-3">
								<div class="col-12 text-center">
									<p class="text-muted">
										계정이 없으십니까? <a href='./joinChoiceForm' class="text-dark ml-1">
											<b>회원가입</b>
										</a>
									</p>
								</div>
							</div>
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

	<footer class="footer footer-alt"> Copyright Respets Corp. All rights reserved. </footer>

	<!-- <form name="loginProcess" action="loginProcess" method="post">
id: <input type="text" id="email" name="email"/> <br/>
pw: <input type="password" id="pw" name="pw"/> <br/>
<button>로그인</button>
</form> -->
	<!-- <button id="joinChoiceForm" onclick="location.href='./joinChoiceForm'">회원가입</button> -->
	<!-- <form name="findMyIdForm" action="findMyIdForm">
<button> 아이디 찾기 </button> -->

	${updateMyPw}
	<!--현휘; 비밀번호 변경이 완료됐다는 안내 alert -->
</body>
<script>
	${alert}
	${loginFailed}
	${noEmail}
	${leave}
</script>
</html>