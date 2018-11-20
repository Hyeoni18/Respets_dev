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
					<div class="card">
						<div class="card-body">
							<form name="noticeDetail" method="get">
								<div class="form-group mb-3">
									<label for="example-select">카테고리</label> <input type="text"
										id="simpleinput" class="form-control" name="abc_no"
										value="<c:if test="${1 == abo.abc_no}">개인</c:if><c:if test="${2 == abo.abc_no}">기업</c:if>"
										readonly>
								</div>
								<div class="form-group">
									<label for="simpleinput">제목</label> <input type="text"
										id="simpleinput" class="form-control" name="abo_title"
										value="${abo.abo_title}" readonly>
								</div>
								<div class="form-group">
									<label for="simpleinput">내용</label>
									<textarea data-toggle="maxlength" class="form-control"
										maxlength="225" rows="10" name="abo_ctt" readonly>${abo.abo_ctt}</textarea>
								</div>
								<input type="button" class="btn btn-warning"
									onclick="forward(this)" value="수정" />
								<!-- Warning Alert modal -->
								<input type="button" class="btn btn-warning" data-toggle="modal"
									data-target="#warning-alert-modal" value="삭제" />

								<!-- Warning Alert Modal -->
								<div id="warning-alert-modal" class="modal fade" tabindex="-1"
									role="dialog" aria-hidden="true">
									<div class="modal-dialog modal-sm">
										<div class="modal-content">
											<div class="modal-body p-4">
												<div class="text-center">
													<i class="dripicons-warning h1 text-warning"></i>
													<h4 class="mt-2">Incorrect Information</h4>
													<p class="mt-3">게시물을 정말 삭제하시겠습니까?</p>
													<input type="button" class="btn btn-warning my-2"
														data-dismiss="modal" value="취소" /> <input type="button"
														class="btn btn-warning my-2" onclick="forward(this)"
														value="삭제" />
												</div>
											</div>
										</div>
										<!-- /.modal-content -->
									</div>
									<!-- /.modal-dialog -->
								</div>
								<!-- /.modal -->
								<input type="text" value="${abo.abo_no}" name="abo_no" />
							</form>
						</div>
					</div>
					<!-- end card-->
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
	<script src="<c:url value="resources/dist/assets/js/app.min.js"/>"></script>

	<!-- third party js -->
	<!-- 게시판 -->
	<script src="resources/dist/assets/js/vendor/summernote-bs4.min.js"></script>

	<script
		src="<c:url value="resources/dist/assets/js/vendor/Chart.bundle.min.js"/>"></script>
	<script
		src="<c:url value="resources/dist/assets/js/vendor/jquery-jvectormap-1.2.2.min.js"/>"></script>
	<script
		src="<c:url value="resources/dist/assets/js/vendor/jquery-jvectormap-world-mill-en.js"/>"></script>
	<!-- third party js ends -->

	<!-- demo app -->
	<script
		src="<c:url value="resources/dist/assets/js/pages/demo.dashboard.js"/>"></script>
	<!-- end demo js-->
</body>
<script>
	function forward(button) {
		console.log("button=" + button.value);
		var frm = document.noticeDetail;
		if (button.value == '수정') {
			frm.action = "redirect:noticeUpdateForm?abo_no=${abo.abo_no}";
		}
		if (button.value == '삭제') {
			frm.action = "redirect:noticeDelete?abo_no=${abo.abo_no}";
		}
		frm.submit();
	}
</script>
</html>