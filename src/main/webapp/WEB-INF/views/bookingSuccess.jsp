<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 예약 완료</title>
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
					<div class="card" style="margin-top: 160px">

						<!-- Logo -->
						<div class="card-header pt-4 pb-4 text-center bg-success">
							<a href="./"> <span><img
									src="resources/dist/assets/images/logo-white.png" alt="리스펫츠 로고"
									height="45" width="250"></span>
							</a>
						</div>

						<div class="card-body p-4">
							<div class="text-center w-75 m-auto">
								<br />
								<b>${vs_start}</b>에<br />
								${bus_name} ${bct_name} 서비스 예약이 완료되었습니다.<br />
								<br />
								<button class="btn-outline-success" onclick='myPage();'>마이페이지</button>
								<button class="btn-outline-info" onclick='schedule();'>캘린더</button>
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
</body>
	<script src="resources/dist/assets/js/app.min.js"></script>
<script>
	function myPage() {
		location.href = "./myPage";
	} // fct End
	
	function schedule() {
		location.href = "./personalCalendar";
	} // fct End
</script>
</html>