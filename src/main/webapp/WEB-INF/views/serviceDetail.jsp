<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 업종 상세 정보</title>
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
<style>
.star-rating {
	width: 205px;
}

.star-rating, star-rating span {
	display: inline-block;
	height: 39px;
	overflow: hidden;
	background: url(star.png) no-repeat;
}

.star-rating span {
	background-position: left bottom;
	line-height: 0;
	vertical-align: top;
}
</style>
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
								<h4 class="page-title">업종 상세 정보</h4>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xl-12">
							<div class="card mb-0">
								<div class="card-body">
									<p class="text-muted font-14 mb-4" id="comanet">해당 업종의 정보를
										확인하세요.</p>
									<div class="table-responsive-sm">
										<table class="table table-centered mb-0">
											<thead>
												<tr>
													<td>서비스 종류</td>
													<td>${bct_name}</td>
												</tr>
												<tr>
													<td>업체명</td>
													<td>${name}</td>
												</tr>
												<tr>
													<td>연락처</td>
													<td>${phone}</td>
												</tr>
												<tr>
													<td>영업시간</td>
													<td>${work}</td>
												</tr>
												<tr>
													<td>고정휴무일</td>
													<td>${holiday}</td>
												</tr>
												<tr>
													<td>제공서비스</td>
													<td>${menu}</td>
												</tr>
												<tr>
													<td>서비스 가능한 동물종류</td>
													<td>${animal}</td>
												</tr>
												<tr>
													<td colspan='2'></td>
												</tr>

											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
									<div>
										<form name="serviceDetail">
											<input type="hidden" name="bct_code" value="${bct_code}" />
											<input type="hidden" name="first" value="${first}" />
											&nbsp;&nbsp;&nbsp;<input type="button"
												class="btn btn-outline-secondary" name="but" value="수정"
												onclick="update(this)" /> <input type="button"
												class="btn btn-outline-danger" name="but" value="삭제"
												onclick="update(this)" />
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%@ include file="footer.html"%>
			</div>
		</div>
	</div>
	<script src="/resources/dist/assets/js/app.min.js"></script>
</body>
<script>
	function update(but) {
		var frm = document.serviceDetail;
		if (but.value == '수정') {
			frm.action = "serviceUpdateForm";
			frm.method = "post";
		} else if (but.value == '삭제') {
			frm.action = "serviceDelete";
			frm.method = "post";
		}
		frm.submit();
	}
</script>
</html>