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
								<div class="col-lg-8">
									<form action="noticeListSearch" name="noticeListForm"
										class="form-inline">
										<div class="form-group mb-3">
											<label for="status-select" class="mr-2"> <!-- 카테고리&nbsp; -->
												<select class="custom-select" name="abc_name" id="abc_name">
													<option>전체</option>
													<option>개인</option>
													<option>기업</option>
											</select>
											</label>
										</div>
										<div class="form-group mb-3">
											<label for="status-select" class="mr-2"> <!-- 검색&nbsp; -->
												<input type="search" class="form-control form-control-sm"
												placeholder="search" aria-controls="basic-datatable"
												name="search">
												<button type="submit" class="btn btn-success btn-sm">검색</button>
											</label>
										</div>
									</form>
								</div>
								<div class="col-lg-4">
									<div class="text-lg-right">
										<a href="noticeWriteForm" class="btn btn-danger mb-2"><i
											class="mdi mdi-plus-circle mr-2"></i> Add Notice</a>
									</div>
								</div>
								<!-- end col-->
							</div>

							<div class="table-responsive-sm">
								<table class="table table-striped table-centered mb-0">
									<thead>
										<tr>
											<th>글번호</th>
											<th>카테고리</th>
											<th>제목</th>
											<th>날짜</th>
											<th>수정/삭제</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="notice" items="${aboList}">
											<tr>
												<td>${notice.abo_no}</td>
												<td>${notice.abc_name}</td>
												<td><a href="noticeDetail?abo_no=${notice.abo_no}">
														${notice.abo_title}</a></td>
												<td>${notice.abo_date}</td>
												<td class="table-action"><a href="noticeUpdateForm?abo_no=${notice.abo_no}"
													class="action-icon"> <i class="mdi mdi-pencil"></i></a> <a
													href="noticeDelete?abo_no=${notice.abo_no}" class="action-icon"> <i
														class="mdi mdi-delete"></i></a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<!-- end card-body-->
					</div>
					<!-- end card-->
				</div>
				<!-- end col -->
			</div>
			<div class="row">
				<div class="col-12">
					<%-- <div class="dataTables_paginate paging_simple_numbers"
						id="selection-datatable_paginate">
						<ul class="pagination pagination-rounded">
							<li class="paginate_button page-item previous disabled"
								id="selection-datatable_previous"><a href="#"
								aria-controls="selection-datatable" data-dt-idx="0" tabindex="0"
								class="page-link"><i class="mdi mdi-chevron-left"></i></a></li>
							<c:forEach var="paging" items="" varStatus="status">
								<li class="paginate_button page-item active"><a href="#"
								aria-controls="selection-datatable" data-dt-idx="${status.count}" tabindex="0"
								class="page-link">${status.count}</a></li>					
							</c:forEach>
							<li class="paginate_button page-item next"
								id="selection-datatable_next"><a href="#"
								aria-controls="selection-datatable" data-dt-idx="7" tabindex="0"
								class="page-link"><i class="mdi mdi-chevron-right"></i></a></li>
						</ul>
					</div> --%>
					${paging}
				</div>
			</div>
			<!-- end row -->

		</div>
		<!-- content -->

		<!-- Footer Start -->

		<!-- end Footer -->

	</div>

	<!-- ============================================================== -->
	<!-- End Page content -->
	<!-- ============================================================== -->


	</div>
	<!-- END wrapper -->

	<!-- alert -->
	${Fail}


	<!-- App js -->
	<script src="<c:url value="/resources/dist/assets/js/app.min.js"/>"></script>

	<!-- third party js -->
	<script
		src="<c:url value="/resources/dist/assets/js/vendor/Chart.bundle.min.js"/>"></script>
	<script
		src="<c:url value="/resources/dist/assets/js/vendor/jquery-jvectormap-1.2.2.min.js"/>"></script>
	<script
		src="<c:url value="/resources/dist/assets/js/vendor/jquery-jvectormap-world-mill-en.js"/>"></script>
	<!-- third party js ends -->

	<!-- demo app -->
	<script
		src="<c:url value="/resources/dist/assets/js/pages/demo.dashboard.js"/>"></script>
	<!-- end demo js-->
</body>
<script>
	$(document).ready(function() {
		//불러온 값에 selected속성 부여하기
		$("#abc_name option").each(function() {
			if ($(this).val() == "${abc_name}")
				$(this).attr("selected", "selected");
		})
	});
</script>
</html>