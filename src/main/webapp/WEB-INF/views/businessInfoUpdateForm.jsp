<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 회원 정보 수정</title>
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
								<h4 class="page-title">회원 정보 수정</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->
					<div class="row">
						<div class="col-6">
							<div class="card">
								<div class="card-body">
									<form action="businessInfoUpdate" method="post"
										enctype="multipart/form-data">
										<div class="row">
											<div class="col-lg-12">
												<div class="form-group mb-3">
													<label style="margin-right: 15px;">대표 사진</label> <input
														type="file" name="mainPhoto" onchange="fileChk(this)" />
													<input type="hidden" name="fileCheck" id="fileCheck"
														value="0" />
												</div>
												<div class="form-group mb-3">
													<label>이메일</label> <input type="text" class="form-control"
														value="${b.bus_email}" disabled="disabled" />
												</div>
												<div class="form-group mb-3">
													<label>업체명</label> <input type="text" class="form-control"
														value="${b.bus_name}" disabled="disabled" />
												</div>
												<div class="form-group mb-3">
													<label>대표자명</label> <input type="text" class="form-control"
														value="${b.bus_ceo}" disabled="disabled" />
												</div>
												<div class="form-group mb-3">
													<label>사업자등록번호</label> <input type="text"
														class="form-control" value="${b.bus_lcno}"
														disabled="disabled" />
												</div>
												<div class="form-group mb-3">
													<label>업체 연락처 <span style="color: red">*</span></label> <input
														type="text" class="form-control" name="bus_phone"
														value="${b.bus_phone}" />
												</div>
												<div class="form-row">
													<div class="form-group col-md-12" style="margin-bottom: 0;">
														<label for="bus_address">업체 주소 <span
															style="color: red">*</span></label>
													</div>
													<div class="form-group col-md-6">
														<input type="text" class="form-control" name="bus_post"
															id="우편번호" placeholder="우편번호" value="${b.bus_post}"
															readonly="readonly" />
													</div>
													<div class="form-group col-md-6">
														<input type="button" onclick="findAddr()" value="우편번호 찾기"
															class="btn btn-outline-success" />
													</div>
												</div>
												<div class="form-group mb-3">
													<input type="text" class="form-control" name="bus_addr"
														id="주소" value="${b.bus_addr}" readonly="readonly" />
												</div>
												<div class="form-group mb-3">
													<input type="text" class="form-control" name="bus_addr2"
														id="상세주소" value="${b.bus_addr2}" />
												</div>
												<div>
													<input type="submit" class="btn btn-success"
														onclick="return check();" value="수정 완료" />
												</div>
											</div>
										</div>
										<!-- end row-->
									</form>
								</div>
								<!-- end card-body -->
							</div>
							<!-- end card -->
						</div>
						<!-- end col -->
					</div>
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
	<!-- App js -->
	<script src="resources/dist/assets/js/app.min.js"></script>

</body>
<script>
	function check() {
		alert('회원 정보 수정이 완료되었습니다.');
	}

	function fileChk(file) {
		if (file.value == "") {
			$("#fileCheck").val(0);
		} else {
			$("#fileCheck").val(1);
		} // else End
	} // fct End
</script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
	// 다음 주소 API 함수
	function findAddr() {
		new daum.Postcode(
				{
					oncomplete : function(data) {
						var fullAddr = ''; // 최종 주소 변수
						var extraAddr = ''; // 조합형 주소 변수
						if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
							fullAddr = data.roadAddress;

						} else { // 사용자가 지번 주소를 선택했을 경우(J)
							fullAddr = data.jibunAddress;
						}
						if (data.userSelectedType === 'R') {
							if (data.bname !== '') {
								extraAddr += data.bname;
							}
							if (data.buildingName !== '') {
								extraAddr += (extraAddr !== '' ? ', '
										+ data.buildingName : data.buildingName);
							}
							fullAddr += (extraAddr !== '' ? ' (' + extraAddr
									+ ')' : '');
						}
						document.getElementById('우편번호').value = data.zonecode;
						document.getElementById('주소').value = fullAddr;

						document.getElementById('상세주소').focus();
					}
				}).open();
	}
</script>
</html>