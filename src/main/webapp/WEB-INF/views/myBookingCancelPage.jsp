<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 예약 취소</title>
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
				<%-- <jsp:include page="topbar-dashboard.jsp">
					<jsp:param name="no" value="${no}" />
				</jsp:include> --%>
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
								<h4 class="page-title">예약 취소</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->

					<div class="row">
						<div class="col-xl-12">
							<div class="card mb-0">
								<div class="card-body">
									<span class="text-muted font-14 mb-4">취소 및 환불 규정</span> <br />
									<br /> - 방문일 기준 3일 전 : 100%<br> - 방문일 기준 2일 전 : 80%<br>
									- 방문일 기준 1일 전 : 50%<br> - 방문일 당일 및 No-Show : 환불 불가<br>
									- 취소, 환불 시 수수료가 발생할 수 있습니다. <br /> <br />
									<div>
										<input type="button"
											class='btn btn-outline-danger btn-rounded' id="cenc"
											value="예약 취소" onclick="return check();" />
									</div>
									${flas}


								</div>

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


</body>
<script>
	function check() {
		var result = confirm("예약 취소하시겠습니까?");
		if (result === true) {
			location.href = 'myBookingCancel?bk_no=${bk_no}'
		} // if End
		return false;
	}
</script>
</html>