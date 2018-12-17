<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 나의 회원 정보</title>
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
	<!-- Begin page -->
	<div class="wrapper">

		<!-- ========== Left Sidebar Start ========== -->
		<%@ include file="left-sidebar.jsp"%>
		<!-- Left Sidebar End -->

		<!-- ============================================================== -->
		<!-- Start Page Content here -->
		<!-- ============================================================== -->

		<div class="content-page">
			<div class="content">

				<!-- Topbar Start -->
				<%@ include file="topbar-dashboard.jsp"%>
				<!-- end Topbar -->

				<!-- Start Content-->
				<div class="container-fluid">

					<!-- start page title -->
					<div class="row">
						<div class="col-12">
							<div class="page-title-box">
								<h4 class="page-title">나의 회원 정보</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->
					<div class="row">
						<div class="col-12">
							<div class="card bg-primary">
								<div class="card-body profile-user-box">
									<div class="row">
										<div class="media">
											<span class="float-left mr-4"> <img id="perProfile"
												src="${glr_loc}${glr_file}"
												style="width: 150px; height: 150px; margin-top: 15px; margin-left: 20px;"
												class="rounded-circle img-thumbnail"></span>
											<div class="media-body">
												<br />
												<h3 class="mt-1 mb-1 text-white">
													<p id="BUS_NAME"></p>
												</h3>
												<p class="mt-1 mb-1 text-white">
													<span id="BUS_PHONE"></span>
												</h5>
												<p class="mt-1 mb-1 text-white">
													<span id="BUS_ADDR"></span>&nbsp;<span id="BUS_ADDR2"></span>
												</h5>
												<br /> <br />
												<button type="button" class="btn btn-light"
													onclick="location.href='./businessPwUpdateForm'">
													<i class=" mdi mdi-lock"></i> 비밀번호 수정
												</button>
												&nbsp;&nbsp;
												<button type="button" class="btn btn-light"
													onclick="location.href='./businessInfoUpdateForm'">
													<i class="mdi mdi-pencil mr-1"></i> 회원 정보 수정
												</button>
												&nbsp;&nbsp;
												<button type="button" class="btn btn-light"
													onclick="businessDelete();" id="delete">
													<i class="mdi mdi-delete-empty mr-1"></i> 회원 탈퇴
												</button>
											</div>
											<!-- end media-body-->
										</div>
										<!-- end col-->
									</div>
									<!-- end row -->
								</div>
								<!-- end card-body/ profile-user-box-->
							</div>
							<!-- end card -->
							<div class="card">
								<div class="card-body">
									<h4 class="header-title mt-0 mb-3">Detail Information</h4>
									<div class="row">
										<div class="col-lg-6">
											<div class="text-left">
												<table>
													<tr>
														<td><p class="text-muted" align="right">
																<strong>주력 서비스 :&nbsp;&nbsp;</strong>
															</p></td>
														<td><p class="text-muted" id="BCT_NAME"></p></td>
													</tr>
													<tr>
														<td><p class="text-muted" align="right">
																<strong>대표자명 :&nbsp;&nbsp;</strong>
															</p></td>
														<td><p class="text-muted" id="BUS_CEO"></p></td>
													</tr>
													<tr>
														<td><p class="text-muted" align="right">
																<strong>사업자등록번호 :&nbsp;&nbsp;</strong>
															</p></td>
														<td><p class="text-muted" id="BUS_LCNO"></p></td>
													</tr>
												</table>
											</div>
										</div>
										<!-- end col -->

									</div>
									<!-- end row-->

								</div>
								<!-- end card-body -->
							</div>
							<!-- end card -->
						</div>
					</div>
				</div>
				<!-- container -->

			</div>
			<!-- content -->

			<!-- Footer Start -->
			<%@ include file="footer.html"%>
			<!-- end Footer -->

		</div>

		<!-- ============================================================== -->
		<!-- End Page content -->
		<!-- ============================================================== -->

	</div>
	<!-- END wrapper -->

</body>
<script src="resources/dist/assets/js/app.min.js"></script>
<script>
	var jsonData = ${result};
	$.each(jsonData, function(key, value) {
		$('#' + key).html(value);
	});

	function businessDelete() {
		var result = confirm("예약 내역을 제외한 모든 데이터가 삭제되고, 재가입 시 데이터 복구가 어렵습니다. 정말 탈퇴하시겠습니까?");
		if (result) {
			location.href = "businessPartDelete";
		}
	}
</script>
</html>