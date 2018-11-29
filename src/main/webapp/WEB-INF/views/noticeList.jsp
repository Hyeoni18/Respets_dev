<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Respets :: 관리자 공지사항</title>
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
											<form action="noticeListSearch" name="noticeListForm"
												class="form-inline">
												<div class="form-group mb-3">
													<label for="status-select" class="mr-2"> <!-- 카테고리&nbsp; -->
														<select class="custom-select" name="abc_name"
														id="abc_name">
															<option>전체</option>
															<option>개인</option>
															<option>기업</option>
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
												<a href="noticeWriteForm" class="btn btn-danger mb-2"><i
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
													<th>카테고리</th>
													<th>제목</th>
													<th>작성일</th>
													<th>수정/삭제</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="abo" items="${aboList}" varStatus="status">
													<tr>
														<td>${abo.abo_no}</td>
														<td>${abo.abc_name}</td>
														<td><a href="noticeDetail?abo_no=${abo.abo_no}">
																${abo.abo_title}</a></td>
														<td>${abo.abo_date}</td>
														<td class="table-action"><a
															href="noticeUpdateForm?abo_no=${abo.abo_no}"
															class="action-icon"><i class="mdi mdi-pencil"></i></a> <a
															href="noticeDelete?abo_no=${abo.abo_no}"
															onclick="return deleteChk(this)" class="action-icon">
																<i class="mdi mdi-delete"></i>
														</a></td>
													</tr>
												</c:forEach>
												<c:if test="${empty aboList}">
													<tr>
														<td colspan="5" style="text-align: center">검색한 내용이
															없습니다</td>
													</tr>
												</c:if>
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
					<!-- end row -->
					<div class="row mb-2" style="">
						<div class="col-lg-8">${paging}</div>
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

	<!-- alert -->
	${Fail}


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
		$("#abc_name option").each(function() {
			if ($(this).val() == "${abc_name}")
				$(this).attr("selected", "selected");
		})
	});
	function deleteChk(a) {
		var con = confirm('정말로 삭제하겠습니까?');
		con;
		if (con == false) {
			return false;
		}
	}
</script>
</html>