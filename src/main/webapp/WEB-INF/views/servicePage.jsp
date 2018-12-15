<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 서비스 관리</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
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
					<div class="page-title-box">
						<div class="page-title-right">
							<form action="serviceInsertForm">
								<input type="hidden" name="no" value="${no}" />
								<button class="btn btn-outline-info">업종 등록 신청</button>
							</form>
						</div>
						<h4 class="page-title">서비스 관리</h4>
					</div>
					<!-- end page title -->

					<div class="row">
						<div class="col-12">
							<div class="row mb-3">
								${servList}
								${add}
							</div>
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
<script>
	function forward(button) {
		var frm = document.servicePage;
		if (button.value == '회원탈퇴') {
			var det;
			det = confirm("예약 내역을 제외한 모든 데이터가 삭제되고, 재가입 시 데이터 복구가 어렵습니다.정말 탈퇴하시겠습니까?");
			if (det) {
				frm.action = "businessPartDelete";
			} else {
				frm.action = "servicePage";
			}
		}
		frm.submit();
	}
</script>
</html>