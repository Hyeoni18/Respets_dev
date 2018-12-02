<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Respets :: 계정 찾기</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/dist/assets/images/logo-sm.png">

<!-- App css -->
<link href="resouces/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body class="authentication-bg">
	<div class="account-pages mt-5 mb-5">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-lg-5">
					<div class="card">
						<!-- Logo -->
						<div class="card-header pt-4 pb-4 text-center bg-success">
							<a href="index.html"> <span><img
									src="resources/dist/assets/images/logo-white.png" alt=""
									height="30"></span>
							</a>
						</div>

						<!--현휘; 치환한 이메일을 화면에 보여주고 로그인을 하던 비밀번호 찾기를 진행하는 폼 -->
						<div class="card-body p-4" style="text-align:center;">
							<div class="text-center m-auto">
								<h4 class="text-dark-50 text-center mt-4 font-weight-bold">회원님의
									계정을 찾았습니다.</h4>
								<p class="text-muted mb-4" id="emailConfirmOfferLogin">
									회원님의 이메일 주소는 <br/> <b>${showEmail}</b> 입니다.
								</p>
							</div>
						</div>
						<form action="findMyPw" name="findMyPwForm" method="POST">
							<div class="form-group mb-0 text-center" style="margin-bottom:30px; text-align:center;">
								<input type="hidden" name="email" value="${email}" />
								<!-- 정상적인 이메일주소 -->
								<input type="hidden" name="type" value="${type}" />
								<!-- 회원 종류 -->
								<input class="btn btn-success" type="submit" value="비밀번호 찾기"/>
							</div>
						</form>
					    <div class="row mt-3">
                            <div class="col-12 text-center">
                                <p class="text-muted"><a href="./loginForm" class="text-muted"><b>로그인 하러가기</b></a></p>
                            </div> <!-- end col-->
                        </div>

					</div>
					<!-- end card-body-->
				</div>
				<!-- end card-->

			</div>
			<!-- end col -->
		</div>
		<!-- end row -->
	</div>
	<!-- end container -->

	<footer class="footer footer-alt"> Copyright Respets Corp. All rights reserved. </footer>

	<!-- App js -->
	<script src="/resources/dist/assets/js/app.min.js"></script>

</body>
</html>