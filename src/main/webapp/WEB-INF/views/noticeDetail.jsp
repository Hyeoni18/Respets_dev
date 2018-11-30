<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>관리자 공지사항</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon" href="resources/images/logo-sm.png">

<!-- third party css -->
<!-- 게시판 -->
<link href="resources/dist/assets/css/vendor/summernote-bs4.css"
	rel="stylesheet" type="text/css">
<link
	href="resources/dist/assets/css/vendor/jquery-jvectormap-1.2.2.css"
	rel="stylesheet" type="text/css" />
<!-- third party css end -->

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

		<!-- Left Sidebar End -->

		<!-- ============================================================== -->
		<!-- Start Page Content here -->
		<!-- ============================================================== -->

		<div class="container-fluid">
			<!-- start page title -->
			<div class="row">
				<div class="col-12">
					<div class="page-title-box">
						<h4 class="page-title">게시글 내용</h4>
					</div>
				</div>
			</div>
			<!-- end page title -->

			<div class="row">
				<div class="col-12">
					<div class="card d-block">
						<div class="card-body">
							<div class="dropdown card-widgets">
								<a href="javascript:void(0);" class="dropdown-toggle arrow-none"
									data-toggle="dropdown" aria-expanded="false"> <i
									class="dripicons-dots-3"></i>
								</a>
								<div class="dropdown-menu dropdown-menu-right"
									x-placement="bottom-end"
									style="position: absolute; will-change: transform; top: 0px; left: 0px; transform: translate3d(-142px, 20px, 0px);">
									<!-- item-->
									<a href="noticeUpdateForm?abo_no=${abo.abo_no}"
										class="dropdown-item"><i class="mdi mdi-pencil mr-1"></i>Edit</a>
									<!-- item-->
									<a href="noticeDelete?abo_no=${abo.abo_no}"
										onclick="return deleteChk(this)" class="dropdown-item"><i
										class="mdi mdi-delete mr-1"></i> Delete</a>
								</div>
							</div>

							<!-- badge-->
							<div class="badge badge-info mb-2"
								style="margin-bottom: 0.5rem">
								<c:if test="${1 == abo.abc_no}">개인</c:if>
								<c:if test="${2 == abo.abc_no}">기업</c:if>
							</div>
							<!-- title-->
							<h3 class="mt-0 mb-3">${abo.abo_title}</h3>

							<h5>내용</h5>
							<p class="text-muted mb-2">${abo.abo_ctt}</p>

							<div class="row">
								<div class="col-md-4">
									<div class="mb-4">
										<h5>작성일</h5>
										<p>${abo.abo_date}</p>
									</div>
								</div>
							</div>
							<input type="hidden" value="${abo.abo_no}" name="abo_no" /> 
							<div style="text-align: right">
								<input
								type="button" class="btn btn-warning"
								onclick="javascript:history.back();" value="목록" />
							</div>
							

						</div>
						<!-- end card-body-->

					</div>
					<!-- end card -->

				</div>
				<!-- end col -->
			</div>
			<!-- end row -->

		</div>
		<!-- content -->

		<!-- Footer Start -->

		<!-- end Footer -->

		<!-- ============================================================== -->
		<!-- End Page content -->
		<!-- ============================================================== -->


	</div>
	<!-- END wrapper -->

	<!-- alert -->
	${Fail}


	<!-- App js -->
	<script src="resources/dist/assets/js/app.min.js"></script>

	<!-- third party js -->
	<!-- 게시판 -->
	<script src="resources/dist/assets/js/vendor/summernote-bs4.min.js"></script>
	<!-- third party js ends -->

	<!-- demo app -->
	<script src="resources/dist/assets/js/pages/demo.project-detail.js"></script>
	<script src="resources/dist/assets/js/pages/demo.dashboard.js"></script>
	<!-- end demo js-->
</body>
<script>
	function deleteChk(a) {
		var con = confirm('정말로 삭제하겠습니까?');
		con;
		if (con == false) {
			return false;
		}
	}
</script>
</html>