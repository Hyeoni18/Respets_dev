<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 기업 인증</title>
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/dist/assets/images/logo-sm.png">

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />
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
								<h4 class="page-title">기업 인증</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->

					<div class="row">
						<div class="col-xl-12">
							<div class="card mb-0">
								<div class="card-body">
									<div class="table-responsive-sm">
										<table class="table table-centered mb-0">
											<tr>
												<th>업체명</th>
												<td>${bus_name}</td>
											</tr>
											<tr>
												<th>서비스 종류</th>
												<td>${bct_name}</td>
											</tr>
											<tr>
												<th>사업자등록번호</th>
												<td>${bus_lcno}</td>
											</tr>
											<tr>
												<th>대표자명</th>
												<td>${bus_ceo}</td>
											</tr>
											<tr>
												<th>연락처</th>
												<td>${bus_phone}</td>
											</tr>
											<tr>
												<th>업체 주소</th>
												<td>${bus_addr}${bus_addr2}</td>
											</tr>
											<tr>
												<th>이메일</th>
												<td>${bus_email}</td>
											</tr>
											<tr>
												<td colspan='2'></td>
											</tr>
											<tr>
												<th style='text-align: center;' colspan='2'>사업자등록증</th>
											</tr>
											<tr>
												<td colspan='2' style='text-align: center;'><img
													style='width: 80%; height: 1000px;' alt="사업자등록증"
													src="${glr_loc}${glr_file}" /></td>
											</tr>
										</table>
										<button class='btn btn-outline-success'
											onclick="confirmChk(this)">승인</button>
										<button class='btn btn-outline-danger'
											onclick="location.href='./unconfirmBusiness'">취소</button>
									</div>
									<br /> <br />
								</div>
								<!-- end card body-->
							</div>
							<!-- end card -->
						</div>
						<!-- end col -->
					</div>
					<!-- end row -->

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
<script src="/resources/dist/assets/js/app.min.js"></script>
</body>
<script>
	function confirmChk(button) {
		var con = confirm('승인하시겠습니까?');
		con;
		if (con == false) {
			return false;
		} else {
			$
			{
				alert
			}
			location.href = './confirmLicense?${bus_no}';
		}
	}
</script>
</html>