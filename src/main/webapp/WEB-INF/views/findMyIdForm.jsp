<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Respets :: 계정 찾기</title>
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
								<h4 class="text-dark-50 text-center mt-0 font-weight-bold">계정(email)
									찾기</h4>
								<p class="text-muted mb-4"></p>
							</div>
							<!--현휘; 아이디 찾는 메소드로 이동 ,
		  					기업을 클릭해도 Personal bean을 이용하기 위해 name을 per_로 설정 -->
							<form action="findMyId" method="POST">

								<div class="form-group">
									<div class="row">
										<div class="col-6">
												<div class="custom-control custom-checkbox" style="text-align:right;">
													<input type="checkbox" class="custom-control-input"
														id="customCheck1" name="type" value="P"> <label
														class="custom-control-label" for="customCheck1">개인</label>
												</div>
											</div>
											<div class="col-6">
												<div class="custom-control custom-checkbox">
													<input type="checkbox" class="custom-control-input"
														id="customCheck2" name="type" value="B"> <label
														class="custom-control-label" for="customCheck2">기업</label>
												</div>
											</div>

									</div>


								</div>

								<div class="form-group">
									<label for="per_name">이름(대표자명)</label> <input
										class="form-control" type="text" name="per_name" />
								</div>

								<div class="form-group">
									<label for="per_phone">전화번호(사업자번호)</label> <input
										class="form-control" type="text" name="per_phone" /> <br />
								</div>
								<div class="form-group mb-0 text-center">
									<button class="btn btn-success">아이디 찾기</button>
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

	${none}
	<!-- 찾는 아이디가 없을 경우 alert창이 뜬다. -->
	<footer class="footer footer-alt"> Copyright Respets Corp. All
		rights reserved. </footer>
</body>
<script>
	/* 개인,기업 선택 하지 않으면 넘어가지 않도록. */
	/* 유효성 검사 나중 */
</script>
</html>