<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 최근 예약 목록</title>
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
								<h4 class="page-title">최근 예약 목록</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->

					<div class="row">
						<div class="col-xl-12">
							<div class="card mb-0">
								<div class="card-body">
									<span class="text-muted font-14 mb-4">내 반려동물의 최근 서비스 예약 목록을
										확인해보세요.</span> <br/><br/>
										<div class="button-list" style='float:right;'>
                                            <button type="button" class="btn btn-outline-info" disabled="">신청</button>
                                            <button type="button" class="btn btn-outline-success" disabled="">승인</button>
                                            <button type="button" class="btn btn-outline-danger" disabled="">거절</button>
                                            <button type="button" class="btn btn-outline-warning" disabled="">취소</button>
                                        </div>
                                        <br/>
                                        <br/>

									<div class="table-responsive-sm">
										<table class="table table-centered mb-0" style='text-align: center;'>
											<thead>
												<tr>
													<th>예약 번호</th>
													<th>업체명</th>
													<th>동물 종류</th>
													<th>동물 이름</th>
													<th>예약자명</th>
													<th>예약 일시</th>
													<th>방문 일시</th>
													<th>예약 상태</th>
												</tr>
												${hList}
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
									<br/>
									<br/>
									${paging}
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


	</div>
	<!-- END wrapper -->
${cancInsertSucess}
</body>
<script src="/resources/dist/assets/js/app.min.js"></script>
<script>
	$(document).ready(function() {
		$.ajax({
			url : "recentMyBookingList",
			type : "get",
			success : function() {
				console.log("성공");
			}
		});
	})
</script>

</html>