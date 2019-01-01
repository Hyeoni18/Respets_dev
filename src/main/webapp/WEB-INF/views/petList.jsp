<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Respets :: 나의 반려동물 정보</title>
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
<style type="text/css">
#petProfile {
	width: 150px;
	height: 150px;
	margin-top: 20px;
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
					<div class="page-title-box">
						<div class="page-title-right">
							<form class="form-inline">
								<a href="petInsertForm?per_no=${per_no}" class="btn btn-outline-success">반려동물
									등록</a>
							</form>
						</div>
						<h4 class="page-title">나의 반려동물 목록</h4>
						<c:if test="${empty petList}">
							<div class='alert alert-info' role='alert' style='margin-bottom:0;'>
							<i class='dripicons-information mr-2'></i> 반려동물을 등록하셔야 <strong>예약 서비스</strong>
						&nbsp;이용이 가능합니다!</div>
						</c:if>
					</div>
					<!-- end page title -->

					<div class="row">
						<div class="col-12">
						
							<div class="card-deck-wrapper">
								<div class="card-deck">
									
									<c:forEach var="petList" items="${petList}" varStatus="status">
										<div class="col-lg-4" style="padding: 0;">
											<div class="card d-block"
												style="text-align: center; margin-bottom: 20px;">
												<a href="petInfoDetail?pet_no=${petList.pet_no}"> <img
													class="rounded-circle img-thumbnail" id="petProfile"
													src="${petList.pet_loc}/${petList.pet_photo}"
													alt="pet profile">
												</a>
												<div class="card-body">
													<h5 class="card-title">
														<%-- 내동물${status.count}. --%>
														${petList.pet_name}
													</h5>
													<p class="card-text">
														품종 : ${petList.pet_vrt}<br> 성별 :
														<c:if test="${'F' eq petList.pet_sex}">암컷</c:if>
														<c:if test="${'M' eq petList.pet_sex}">수컷</c:if>
														<br>
														<c:set var="now" value="<%=new java.util.Date()%>" />
														<c:set var="sysYear">
															<fmt:formatDate value="${now}" pattern="yyyy" />
														</c:set>
														나이 : ${sysYear-petList.pet_age+1}살
													</p>
												</div>
											</div>
										</div>

									</c:forEach>

								</div>
								<!-- end card-deck-->
							</div>
							<!-- end card-deck-wrapper-->
						</div>
						<!-- end col-->
					</div>
					<!-- end row-->
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


	<!-- demo app -->
	<script
		src="resources/dist/assets/js/pages/demo.dashboard.js"></script>
	<!-- end demo js-->
</body>
</html>