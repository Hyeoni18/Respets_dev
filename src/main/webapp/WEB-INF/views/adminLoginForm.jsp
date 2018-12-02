<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Respets :: 관리자로그인</title>
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
						</div> <!-- Logo end -->

						<div class="card-body p-4">
							<div class="text-center w-75 m-auto">
								<h4 class="text-dark-50 text-center mt-0 font-weight-bold">관리자 로그인</h4>
								<p class="text-muted mb-4">관리자 로그인 페이지 입니다.</p>
							</div>

							<form action="adminLogin" method="post">
								<div class="form-group">
									<label for="adm_no">관리자 번호</label>
									<input class="form-control" type="text" id="adm_no" name="adm_no" placeholder="관리자 아이디를 입력하세요." />
								</div>
								
								<div class="form-group">
									<label for="adm_pw">비밀번호</label> <br /> <input type="password" class="form-control"
										id="adm_pw" name="adm_pw" placeholder="비밀번호를 입력하세요." />
								</div>
								
								<div class="form-group mb-0 text-center">
									<button class="btn btn-success" type="submit">로그인</button>
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

	<footer class="footer footer-alt"> Copyright Respets Corp. All rights reserved. </footer>
	<script src="/resources/dist/assets/js/app.min.js"></script>
</body>
</html>