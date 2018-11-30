<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html auto-config="true">
<head>
<head>
<meta charset="utf-8" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 공지사항 관리</title>
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
		<%@ include file="left-sidebar.jsp"%>
		<div class="content-page">
			<%@ include file="topbar-dashboard.jsp"%>
			<div class="container-fluid">

				<!-- start page title -->
				<div class="row">
					<div class="col-12">
						<div class="page-title-box">
							<h4 class="page-title">공지사항</h4>
						</div>
					</div>
				</div>
				<!-- end page title -->

				<div class="row">
					<div class="col-12">
						<div class="card">
							<div class="card-body">
								<div class="row mb-2">
									<div class="col-lg-6">
										<form action="searchBusinessNotice"
											name="businessNoticeListForm" class="form-inline">
											<div class="form-group mb-3">
												<label for="status-select" class="mr-2"> <!-- 카테고리&nbsp; -->
													<select class="custom-select" name="select" id="select">
														<option>전체</option>
														<option>공지사항</option>
														<option>이벤트</option>
												</select>
												</label>
											</div>
											<div class="form-group mb-3">
												<label for="status-select" class="mr-2"> <!-- 검색&nbsp; -->
													<input type="search" class="form-control form-control-sm"
													placeholder="제목" aria-controls="basic-datatable"
													name="search">
													<button type="submit" class="btn btn-success btn-sm">검색</button>
												</label>
											</div>
										</form>
									</div>
									<div class="col-lg-6">
										<div class="text-lg-right">
											<a href="businessNoticeWriteForm" class="btn btn-danger mb-2"><i
												class="mdi mdi-plus-circle mr-2"></i> Add Notice</a>
										</div>
									</div>
									<!-- end col-->
								</div>
								<!-- end row -->

								${searchNotifications}
								<div class="table-responsive-sm">
									<table class="table table-striped table-centered mb-0">
										<thead>
											<tr>
												<th>글번호</th>
												<th>업종</th>
												<th>카테고리</th>
												<th>제목</th>
												<th>작성일</th>
												<th>수정/삭제</th>
											</tr>
										</thead>
										<tbody>${nList}</tbody>
									</table>
								</div>
							</div>
							<!-- end card-body-->
						</div>
						<!-- end card-->
					</div>
					<!-- end col -->
				</div>
				<!-- end row -->
				<div class="row mb-2" style="">
					<div class="col-lg-8">${paging}</div>
				</div>
				<!-- end row -->



			</div>
			<%@ include file="footer.html"%>
	</div>

		</div>
		<!-- END wrapper -->

		<!-- App js -->
		<script src="resources/dist/assets/js/app.min.js"></script>

		<!-- third party js -->
		<script src="resources/dist/assets/js/vendor/jquery.dataTables.js"></script>
		<script src="resources/dist/assets/js/vendor/dataTables.bootstrap4.js"></script>
		<script
			src="resources/dist/assets/js/vendor/dataTables.responsive.min.js"></script>
		<script
			src="resources/dist/assets/js/vendor/responsive.bootstrap4.min.js"></script>
		<!-- third party js ends -->

		<!-- demo app -->
		<script src="resources/dist/assets/js/pages/demo.dashboard.js"></script>
		<!-- end demo js-->
</body>
<script>
	$(document).ready(function() {
		//불러온 값에 selected속성 부여하기
		$("#select option").each(function() {
			if ($(this).val() == "${param.select}")
				$(this).attr("selected", "selected");
		})
	});
	$
	{
		alert
	}
	function deleteChk(a) {
		var con = confirm('정말로 삭제하겠습니까?');
		con;
		if (con == false) {
			return false;
		}
	}
</script>
</html>