<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Respets :: 회원가입 선택</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon" href="resources/images/logo-sm.png">

<!-- third party css -->
<link
	href="resources/dist/assets/css/vendor/jquery-jvectormap-1.2.2.css"
	rel="stylesheet" type="text/css" />
<!-- third party css end -->

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
#personal {
	width: 150px;
	height: 150px;
	margin-top: 20px;
}

#business {
	width: 150px;
	height: 150px;
	margin-top: 20px;
}
</style>
</head>
<body>
	<div class="row justify-content-center">
		<div class="col-7">
			<div class="card">
				<div class="row">
					<div class="col-12">
						<div class="card-deck-wrapper">
							<div class="card-deck">
								<div class="card d-block" style="text-align: center;">
									<img id="personal"
										src="resources/images/defaultProfile/woman.png"
										alt="pet profile">
									<div class="card-body">
										<h5 class="card-title">개인회원 ${petList.pet_name}</h5>
										<p class="text-muted mb-2">
											가입하기 버튼 클릭 후 <br /> 서비스 이용하기<br />
											<br />
											<br />
											<button class="btn btn-success"
												onclick="location.href='./personalJoinAgreement'">가입하기</button>
										</p>
									</div>
								</div>

								<div class="card d-block" style="text-align: center;">
									<img id="business"
										src="resources/images/defaultProfile/pet-shop.png"
										alt="pet profile">
									<div class="card-body">
										<h5 class="card-title">기업회원 ${petList.pet_name}</h5>
										<p class="text-muted mb-2">
											가입하기 버튼 클릭 후 <br /> 서비스 이용하기<br />
											<br />
											<br />
											<button class="btn btn-success"
												onclick="location.href='./businessJoinAgreement'">가입하기</button>
										</p>
									</div>
								</div>

							</div>
							<!-- end card-deck-->
						</div>
						<!-- end card-deck-wrapper-->
					</div>
					<!-- end col-->
				</div>
				<!-- end row-->
			</div>
			<!-- card -->
		</div>
		<!-- col-7 -->
	</div>
	<!-- row justify-content-center -->

	<!-- App js -->
	<script src="/resources/dist/assets/js/app.min.js" /></script>

	<!-- third party js -->
	<script src="/resources/dist/assets/js/vendor/Chart.bundle.min.js" /></script>
	<script
		src="/resources/dist/assets/js/vendor/jquery-jvectormap-1.2.2.min.js" /></script>
	<script
		src="/resources/dist/assets/js/vendor/jquery-jvectormap-world-mill-en.js" /></script>
	<!-- third party js ends -->

	<!-- demo app -->
	<script src="/resources/dist/assets/js/pages/demo.dashboard.js" /></script>
	<!-- end demo js-->

</body>
</html>