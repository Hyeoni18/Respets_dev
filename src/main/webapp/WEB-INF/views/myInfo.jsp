<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 나의 회원 정보</title>
<!-- App favicon -->
<link rel="shortcut icon" href="resources/images/logo-sm.png">
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
					<form name="myInfo" method="get">
						<div class="row">
							<div class="col-12">
								<div class="card bg-primary">
									<div class="card-body profile-user-box">

										<div class="row">
											<div class="media">
												<span class="float-left mr-4"> <img id="perProfile"
													src="${mb.per_loc}${mb.per_photo}"
													style="width: 200px; height: 200px;"
													class="rounded-circle img-thumbnail"></span>
												<div class="media-body">
													<br />
													<h3 class="mt-1 mb-1 text-white">${mb.per_name}</h3>
													<h5 class="mt-1 mb-1 text-white">${mb.per_email}</h5>
													<h5 class="mt-1 mb-1 text-white">${mb.per_phone}</h5>
													<br /> <br />
													<button type="button" class="btn btn-light"
														onclick="javascript:forward(this)" id="pw">
														<i class=" mdi mdi-lock"></i> 비밀번호 수정
													</button>
													&nbsp;&nbsp;
													<button type="button" class="btn btn-light"
														onclick="javascript:forward(this)" id="edit">
														<i class="mdi mdi-pencil mr-1"></i> 회원 정보 수정
													</button>
													&nbsp;&nbsp;
													<button type="button" class="btn btn-light"
														onclick="javascript:forward(this)" id="delete">
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
							</div>
						</div>
					</form>
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

	<!-- alert -->
	${Fail} ${delete} ${infoSuccess}
</body>
<!-- App js -->
<script src="resources/dist/assets/js/app.min.js"></script>
<script>
	function forward(button) {
		var frm = document.myInfo;
		if (button.id == 'pw') {
			frm.action = "myPwUpdateForm";
		}
		if (button.id == 'edit') {
			frm.action = "myInfoUpdateForm";
		}
		if (button.id == 'delete') {
			var det;
			det = confirm("정말 탈퇴하시겠습니까?");
			if (det) {
				frm.action = "personalPartDelete";
			} else {
				frm.action = "myInfo"
			}
		}
		frm.submit();
	}
</script>
</html>