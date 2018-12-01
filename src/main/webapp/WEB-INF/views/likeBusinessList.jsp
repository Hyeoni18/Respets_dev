<!-- 서진 : 개인 즐겨찾기 페이지 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 즐겨찾기 목록</title>
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
#petProfile {
	width: 150px;
	height: 150px;
	margin-top: 20px;
}
</style>
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
				<%-- <jsp:include page="topbar-dashboard.jsp">
					<jsp:param name="no" value="${no}" />
				</jsp:include> --%>
				<%@ include file="topbar-dashboard.jsp"%>
				<!-- end Topbar -->

				<!-- Start Content-->
				<div class="container-fluid">

					<!-- start page title -->
					<div class="page-title-box">
						<h4 class="page-title">나의 즐겨찾기 목록</h4>
					</div>
					<!-- end page title -->
					<div class="row">
						<div class="col-12">
							<div class="card-deck-wrapper">
								<div class="card-deck">
									${list}


									<!-- end card-deck-->
								</div>
								<!-- end card-deck-wrapper-->
							</div>
							<!-- end col-->
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
		<!-- App js -->
		<script src="/resources/dist/assets/js/app.min.js"></script>

		<!-- third party js -->
		<script src="/resources/dist/assets/js/vendor/Chart.bundle.min.js"></script>

		<!-- demo app -->
		<script src="/resources/dist/assets/js/pages/demo.project-detail.js"></script>
		<!-- demo app -->
		<script src="/resources/dist/assets/js/pages/demo.widgets.js"></script>
</body>
<script>
	function check() {
		var result = confirm('즐겨찾기를 삭제하시겠습니까?');
			if (result === true) {
				return true;
			} // if End
		return false;
	} // fct End
</script>
</html>