<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 회원 정보 수정</title>
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
								<div class="page-title-right">
									<form class="form-inline"></form>
								</div>
								<h4 class="page-title">회원 정보 수정</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->
					<div class="row">
						<div class="col-6">
							<div class="card">
								<div class="card-body">
								<!-- form으로 파일을 업로드할 땐 꼭 enctype을 적어줘야 한다. -->
									<form action="myInfoUpdate" method="post" enctype="multipart/form-data">
										<div class="row">
											<div class="col-lg-12">
												<div class="form-group mb-3">
													<label style="margin-right: 15px;">프로필 사진</label> <input
														type="file" name="mainPhoto" onchange="fileChk(this)" />
													<input type="hidden" name="fileCheck" id="fileCheck"
														value="0" />
												</div>
												<div class="form-group mb-3">
													<label>이메일 <span style="color: red">*</span></label> <input
														type="text" class="form-control" value="${mb.per_email}"
														disabled="disabled" />
												</div>
												<div class="form-group mb-3">
													<label>이름 <span style="color: red">*</span></label> <input
														type="text" class="form-control" value="${mb.per_name}"
														disabled="disabled" />
												</div>
												<div class="form-group mb-3">
													<label>연락처 <span style="color: red">*</span></label> <input
														type="text" class="form-control" name="per_phone"
														value="${mb.per_phone}" />
													<div class="registrationFormAlert" id="same"></div>
												</div>
												<div>
													<input type="submit" class="btn btn-success" value="수정 완료" />
												</div>
											</div>
										</div>
										<!-- end row-->
									</form>
								</div>
								<!-- end card-body -->
							</div>
							<!-- end card -->
						</div>
						<!-- end col -->
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
	<!-- App js -->
	<script src="resources/dist/assets/js/app.min.js"></script>
</body>
<script>
	function fileChk(file) {
		if (file.value == "") {
			$("#fileCheck").val(0);
		} else {
			$("#fileCheck").val(1);
		} // else End
	} // fct End
</script>
</html>