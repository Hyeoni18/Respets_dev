<!-- 서진 : 기업 새로운 예약 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 새로운 예약 목록</title>
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
								<h4 class="page-title">새로운 예약 목록</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->

					<div class="row">
						<div class="col-xl-12">
							<div class="card mb-0">
								<div class="card-body">
									<span class="text-muted font-14 mb-4">확인되지 않은 새로운 예약입니다.</span> <br/><br/>
                                        <br/>

									<div class="table-responsive-sm">
										<table class="table table-centered mb-0" style='text-align: center;'>
											<thead>
												<tr>
													<th>예약 번호</th>
													<th>동물 종류</th>
													<th>동물 이름</th>
													<th>예약자명</th>
													<th>서비스 종류</th>
													<th>방문 일시</th>
													<th>예약 상태</th>
												</tr>
												${list}
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
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
</body>
<script src="/resources/dist/assets/js/app.min.js"></script>
<script>
	$(':button').click(function() {
		var name = $(this).attr('name');
		var val = $(this).val();

		if (val === "확정") {
			accept(name);
		} else if (val === "거절") {
			reject(name);
		} // if End
	});

	function accept(bk_no) {
		$.ajax({
			url : 'bookingAccept',
			type : 'post',
			data : {
				'bk_no' : bk_no
			},
			success : function() {
				alert("예약이 확정되었습니다.");
				$('#' + bk_no).html("<span class='text-success'>확정된 예약</span>");
			},
			error : function(error) {
				console.log(error);
			}
		});
	}

	function reject(bk_no) {
		$.ajax({
			url : 'bookingReject',
			type : 'post',
			data : {
				'bk_no' : bk_no
			},
			success : function() {
				alert("예약이 거절되었습니다.");
				$('#' + bk_no).html("<span class='text-danger'>거절된 예약</span>");
			},
			error : function(error) {
				console.log(error);
			}
		});
	}
</script>
</html>