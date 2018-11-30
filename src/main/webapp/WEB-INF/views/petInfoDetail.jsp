<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Respets :: 반려동물 상세 정보</title>
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
								<h4 class="page-title">반려동물 상세 정보</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->
					<form name="petInfoDetail" method="get">
						<div class="row">
							<div class="col-12">
								<div class="card bg-primary">
									<div class="card-body profile-user-box">

										<div class="row">
											<div class="col-sm-8">
												<div class="media">
													<span class="float-left mr-4"><img id="petProfile"
														src="${pet.pet_loc}/${pet.pet_photo}" alt="pet profile"
														style="width: 100px;height: 100px;"
														class="rounded-circle img-thumbnail"></span>
													<div class="media-body">

														<h4 class="mt-1 mb-1 text-white">${pet.pet_name}</h4>
														<p class="font-13 text-white-50">${pet.pet_vrt}</p>

														<ul class="mb-0 list-inline text-light">
															<li class="list-inline-item mr-3">
																<h5 class="mb-1">
																	<c:set var="now" value="<%=new java.util.Date()%>" />
																	<c:set var="sysYear">
																		<fmt:formatDate value="${now}" pattern="yyyy" />
																	</c:set>${sysYear-pet.pet_age+1}살
																</h5>
															</li>
															<li class="list-inline-item">
																<h5 class="mb-1">
																	<c:if test="${'F' eq pet.pet_sex}">암컷</c:if>
																	<c:if test="${'M' eq pet.pet_sex}">수컷</c:if>
																</h5>
															</li>
														</ul>
													</div>
													<!-- end media-body-->
												</div>
											</div>
											<!-- end col-->

											<div class="col-sm-4">
												<div class="text-center mt-sm-0 mt-3 text-sm-right">
													<button type="button" class="btn btn-light"
														onclick="javascript:forward(this)" id="edit">
														<i class="mdi mdi-pencil mr-1"></i> 수정
													</button>
													<button type="button" class="btn btn-light"
														style="margin-left: 10px;" onclick="javascript:deleteChk(this)" id="delete">
														<i class="mdi mdi-delete-empty mr-1"></i> 삭제
													</button>
												</div>
											</div>
											<!-- end col-->
										</div>
										<!-- end row -->
									</div>
									<!-- end card-body/ profile-user-box-->
								</div>
								<!-- end card -->

								<div class="card">
									<div class="card-body">
										<h4 class="header-title mt-0 mb-3">Pet Detail Information</h4>

										<div class="row">
											<div class="col-lg-6">
												<div class="text-left">
													<p class="text-muted">
														<strong>중성화 수술 :</strong> <span class="ml-2">${pet.pet_ntr}</span>
													</p>

													<p class="text-muted">
														<strong>몸무게 :</strong> <span class="ml-2">${pet.pet_wght}
															<c:if test="${null == pet.pet_wght}">-</c:if>
														</span>
													</p>

													<p class="text-muted">
														<strong>질병 :</strong><span class="ml-2">${pet.pet_sick}
															<c:if test="${null == pet.pet_sick}">-</c:if>
														</span>
													</p>

													<p class="text-muted">
														<strong>주의해야할 음식 :</strong><span class="ml-2">${pet.pet_food}
															<c:if test="${null == pet.pet_food}">-</c:if>
														</span>
													</p>
												</div>
											</div>
											<!-- end col -->

											<div class="col-lg-6">
												<div class="text-left">

													<p class="text-muted">
														<strong>배변훈련 :</strong> <span class="ml-2">${pet.pet_tot}
															<c:if test="${null == pet.pet_tot}">-</c:if>
														</span>
													</p>

													<p class="text-muted">
														<strong>일일 배식횟수 및 회당 배식량 :</strong> <span class="ml-2">${pet.pet_rat}
															<c:if test="${null == pet.pet_rat}">-</c:if>
														</span>
													</p>

													<p class="text-muted">
														<strong>가족이 된 날 :</strong> <span class="ml-2">${pet.pet_mday}
															<c:if test="${null == pet.pet_mday}">-</c:if>
														</span>
													</p>

													<p class="text-muted">
														<strong>특이사항 :</strong> <span class="ml-2">${pet.pet_etc}
															<c:if test="${null == pet.pet_etc}">-</c:if>
														</span>
													</p>
												</div>
											</div>
											<!-- end col -->
										</div>
										<!-- end row-->

									</div>
									<!-- end card-body -->
								</div>
								<!-- end card -->
							</div>
							<!-- end col -->
						</div>

						<input type="hidden" value="${pet.per_no}" name="per_no" /> <input
							type="hidden" value="${pet.pet_no}" name="pet_no" />
					</form>
					<%-- <div class="row">
						<div class="col-12">
							반려동물 등록 폼 <br> 회원번호 : ${param.per_no}
							반려동물 상세정보
	<br> 동물번호 : ${pet.pet_no}
	<form name="petInfoDetail" method="get">
		<table>
			<tr>
				<td>프로필 사진</td>
				<td>
					<img class="rounded-circle" id="petProfile" 
					src="${pet.pet_loc}/${pet.pet_photo}"
					alt="pet profile"></td>
			</tr>
			<tr>
				<td>이름</td>
				<td>${pet.pet_name}</td>
			</tr>
			<tr>
				<td>종류</td>
				<td>${pet.pty_name}</td>
			</tr>
			<tr>
				<td>품종</td>
				<td>${pet.pet_vrt}</td>
			</tr>
			
			<tr>
				<td>출생년도</td>
				<c:set var="now" value="<%=new java.util.Date()%>" />
				<c:set var="sysYear">
					<fmt:formatDate value="${now}" pattern="yyyy" />
				</c:set>
				<td>${pet.pet_age}(${sysYear-pet.pet_age+1}살)</td>
			</tr>
			<tr>
				<td>성별</td>
				<td>
					<c:if test="${'F' eq pet.pet_sex}">암컷</c:if>
					<c:if test="${'M' eq pet.pet_sex}">수컷</c:if></td>
			</tr>
			<tr>
				<td>중성화 수술 여부</td>
				<td>${pet.pet_ntr}</td>
			</tr>
			<tr>
				<td>몸무게</td>
				<td>${pet.pet_wght}
					<c:if test="${null == pet.pet_wght}">-</c:if>
				</td>
			</tr>
			<tr>
				<td>질병</td>
				<td>${pet.pet_sick}
					<c:if test="${null == pet.pet_sick}">-</c:if>
				</td>
			</tr>
			<tr>
				<td>만난 날</td>
				<td>${pet.pet_mday}
					<c:if test="${null == pet.pet_mday}">-</c:if>
				</td>
			</tr>
			<tr>
				<td>주의해야할 음식</td>
				<td>${pet.pet_food}
					<c:if test="${null == pet.pet_food}">-</c:if>
				</td>
			</tr>
			<tr>
				<td>하루 식사 횟수 및 1회 배급량</td>
				<td>${pet.pet_rat}
					<c:if test="${null == pet.pet_rat}">-</c:if>
				</td>
			</tr>
			<tr>
				<td>배변 훈련 여부</td>
				<td>${pet.pet_tot}
					<c:if test="${null == pet.pet_tot}">-</c:if>
				</td>
			</tr>
			<tr>
				<td>기타 특이사항</td>
				<td>${pet.pet_etc}
					<c:if test="${null == pet.pet_etc}">-</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: right;">					
					<input type="button" class="btn btn-warning" onclick="forward(this)" value="수정"/>
					<!-- Warning Alert modal -->
					<input type="button" class="btn btn-warning" data-toggle="modal" data-target="#warning-alert-modal" value="삭제"/>
					
					<!-- Warning Alert Modal -->
					<div id="warning-alert-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
					    <div class="modal-dialog modal-sm">
					        <div class="modal-content">
					            <div class="modal-body p-4">
					                <div class="text-center">
					                    <i class="dripicons-warning h1 text-warning"></i>
					                    <h4 class="mt-2">Incorrect Information</h4>
					                    <p class="mt-3">${pet.pet_name}의 정보를 정말 삭제하시겠습니까?</p>
					                    <input type="button" class="btn btn-warning my-2" data-dismiss="modal" value="취소"/>
					                    <input type="button" class="btn btn-warning my-2" onclick="forward(this)" value="삭제"/>
					                </div>
					            </div>
					        </div><!-- /.modal-content -->
					    </div><!-- /.modal-dialog -->
					</div><!-- /.modal -->
				</td>
			</tr>
		</table>
		<input type="hidden" value="${pet.per_no}" name="per_no" />
		<input type="hidden" value="${pet.pet_no}" name="pet_no" /> 
	</form>
						</div>
					</div>
 --%>
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
	<!-- <script src="resources/dist/assets/js/pages/demo.dashboard.js"></script> -->
	<script src="resources/dist/assets/js/pages/demo.profile.js"></script>
	<!-- end demo js-->

</body>
<script>
	function deleteChk(button) {
		var con = confirm('정말로 삭제하겠습니까?');
		con;
		if (con == false) {
			return false;
		}else forward(button);
	}
	function forward(button) {
		//alert("button id=" + button.id);
		var frm = document.petInfoDetail;
		if (button.id == 'edit') {
			frm.action = "petInfoUpdateForm?pet_no=${pet.pet_no}";
		}
		if (button.id == 'delete') {
			frm.action = "petDelete?pet_no=${pet.pet_no}";
		}
		frm.submit();
	}
</script>
</html>