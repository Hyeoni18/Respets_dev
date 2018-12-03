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
	<div class="wrapper">
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
								<h4 class="page-title">게시글 작성</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->
					<div class="row">
						<div class="col-12">
							<div class="card">
								<div class="card-body">
									<form action="noticeInsert" name="noticeWriteForm" method="get">
										<div class="form-group mb-3">
											<label for="example-select">카테고리</label> <select
												class="form-control" name="abc_no">
												<option value="">선택</option>
												<option value="1">개인</option>
												<option value="2">기업</option>
											</select>
										</div>
										<div class="form-group">
											<label for="simpleinput">제목</label> <input type="text"
												id="simpleinput" class="form-control" name="abo_title"
												placeholder="게시글 제목을 입력하세요">
										</div>
										<div class="form-group">
											<label for="simpleinput">내용</label>
											<textarea data-toggle="maxlength" class="form-control"
												maxlength="1000" rows="10" name="abo_ctt"
												placeholder="게시글 내용을 입력하세요"></textarea>
										</div>
										<div style="text-align: right">
											<button id="noticeInsertSubmit"
												class="btn btn-success btn-sm mt-2">
												<i class="mdi mdi-content-save-outline mr-1"></i> Save
											</button>
										</div>

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
				<%@ include file="footer.html"%>
				<!-- end Footer -->

				<!-- ============================================================== -->
				<!-- End Page content -->
				<!-- ============================================================== -->

			</div>
		</div>
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
	<script src="resources/dist/assets/js/pages/demo.dashboard.js"></script>
	<!-- end demo js-->
</body>
<script>
	$("#noticeInsertSubmit").click(function() {
		console.log(this.value + " 클릭함");
		var frm = document.noticeWriteForm;
		var length = frm.length - 1;
		for (var i = 0; i < length; i++) {
			if (frm[i].name == "abc_no") {
				if (frm[i].value == "" || frm[i].value == null) {
					alert("카테고리가 입력되지 않았습니다");
					frm[i].focus();
					return false;
				}
			} else if (frm[i].name == "abo_title") {
				if (frm[i].value == "" || frm[i].value == null) {
					alert("제목이 입력되지 않았습니다");
					frm[i].focus();
					return false;
				}
			} else if (frm[i].name == "abo_ctt") {
				if (frm[i].value == "" || frm[i].value == null) {
					alert("내용이 입력되지 않았습니다");
					frm[i].focus();
					return false;
				}
			}
		}
		frm.submit();
	});
</script>
</html>