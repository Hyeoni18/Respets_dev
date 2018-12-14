<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 기업 공지사항 상세</title>
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

		<!-- Left Sidebar Start -->
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
									<div class="dropdown card-widgets" id='dot'>
										<a href="javascript:void(0);"
											class="dropdown-toggle arrow-none" data-toggle="dropdown"
											aria-expanded="false"> <i class="dripicons-dots-3"></i>
										</a>
										<div class="dropdown-menu dropdown-menu-right"
											x-placement="bottom-end"
											style="position: absolute; will-change: transform; top: 0px; left: 0px; transform: translate3d(-142px, 20px, 0px);">
											<!-- item-->
											<a href="businessNoticeUpdateForm?${bbo_no}"
												class="dropdown-item"><i class="mdi mdi-pencil mr-1"></i>Edit</a>
											<!-- item-->
											<a href="businessNoticeDelete?${bbo_no}"
												onclick="return deleteChk(this)" class="dropdown-item"><i
												class="mdi mdi-delete mr-1"></i> Delete</a>
										</div>
									</div>

									<!-- badge-->
									<div class="badge badge-primary mb-2"
										style="margin-bottom: 0.5rem">
										<c:if test="${'병원' == bct_name}">병원</c:if>
										<c:if test="${'미용' == bct_name}">미용</c:if>
										<c:if test="${'호텔' == bct_name}">호텔</c:if>
									</div>
									<div class="badge badge-secondary mb-2"
										style="margin-bottom: 0.5rem">
										<c:if test="${'공지사항' == bbc_name}">공지사항</c:if>
										<c:if test="${'이벤트' == bbc_name}">이벤트</c:if>
									</div>
									<!-- title-->
									<h3 class="mt-0 mb-3">${bbo_title}</h3>

									<h5>내용</h5>
									<p class="text-muted mb-2">${bbo_ctt}</p>

									<div class="row">
										<div class="col-md-4">
											<div class="mb-4">
												<h5>작성일</h5>
												<p>${bbo_date}</p>
											</div>
										</div>
									</div>
									<div style="text-align: right">
										<input type="hidden" value="${bbo_no}" name="bbo_no" /> <input
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

			</div>
			<!-- Footer Start -->
			<%@ include file="footer.html"%>
			<!-- end Footer -->
		</div>
	</div>
	<!-- content -->


	<!-- ============================================================== -->
	<!-- End Page content -->
	<!-- ============================================================== -->


	<!-- END wrapper -->



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

	$('#dot').hide();
	if ('${session}' != null) {
		if ('${session}' == '${bus_no}') {
			$('#dot').show();
		} else {
			$('#dot').hide();
		}
	}
</script>
</html>