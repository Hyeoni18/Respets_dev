<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Respets</title>
<!-- App favicon -->
<link rel="shortcut icon" href="resources/images/logo-sm.png">
<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />
</head>
<body data-layout="topnav">
	${alert}
	<!-- 메일의 유효시간이 경과했다는 안내 || 존재하지 않는 메일이라는 안내 alert -->
	${findPw}
	<!-- 비밀번호 설정 이메일을 보냈다는 안내 alert -->

	<!-- Begin page -->
	<div class="wrapper">

		<!-- ============================================================== -->
		<!-- Start Page Content here -->
		<!-- ============================================================== -->

		<div class="content-page">
			<div class="content">

				<!-- Topbar Start -->
				<jsp:include page="topbar-landing.jsp">
					<jsp:param name="no" value="${no}" />
				</jsp:include>
				<!-- end Topbar -->


				<!-- Start Content-->
				<div class="container-fluid">

					<!-- start page title -->
					<div class="row">
						<div class="col-12">
							<div class="page-title-box">
								<h4 class="page-title" style="text-align: center;">
									<c:if test="${no == null}">
								로그인을 하면 더 많은 서비스 이용이 가능합니다!
								</c:if>
									<c:if test="${no != null}">
										<c:if test="${fn:substring(no,0,1) eq 'P'}">
											${name} 회원님, 반갑습니다! 반려동물 관련 예약서비스를 편리하게 누려보세요:)
										</c:if>
										<c:if test="${fn:substring(no,0,1) eq 'B'}">
											${name} 회원님, 반갑습니다! 새로운 예약을 확인해보세요:)
										</c:if>
										<c:if test="${fn:substring(no,0,1) eq '1'}">
											관리자로 로그인 했습니다.
										</c:if>
									</c:if>
								</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->

					<div class="row">

						<div class="col-12">

							<!-- Search-card Start-->
							<div class="card text-white bg-mint">
								<div class="card-body">
									<blockquote class="card-bodyquote">
										<form action="searchList" method="get" name="searchFrm">
											<div class="col-lg-2" data-select2-id="276"
												style="float: left;">
												<h5>서비스 선택</h5>
												<select name="bct_code" id="bct_code"
													class="form-control select2" data&ndash;toggle="select2">
												</select>
											</div>

											<div class="col-lg-3" data-select2-id="160"
												style="float: left;">
												<h5>지역 선택</h5>
												<select name="city"
													class="select2 form-control select2-multiple select2-hidden-accessible"
													data-toggle="select2" multiple=""
													data-placeholder="Choose ..." data-select2-id="4"
													tabindex="-1" aria-hidden="true">
													<option value="서울">서울</option>
													<option value="부산">부산</option>
													<option value="대구">대구</option>
													<option value="인천">인천</option>
													<option value="광주">광주</option>
													<option value="대전">대전</option>
													<option value="울산">울산</option>
													<option value="세종시">세종시</option>
													<option value="경기">경기도</option>
													<option value="고양">고양</option>
													<option value="부천">부천</option>
													<option value="성남">성남</option>
													<option value="수원">수원</option>
													<option value="안양">안양</option>
													<option value="용인">용인</option>
													<option value="의정부">의정부</option>
													<option value="강원도">강원도</option>
													<option value="강릉">강릉</option>
													<option value="속초">속초</option>
													<option value="춘천">춘천</option>
													<option value="충청북도">충청북도</option>
													<option value="청주시">청주시</option>
													<option value="충주시">충주시</option>
													<option value="충청남도">충청남도</option>
													<option value="천안시">천안시</option>
													<option value="전라북도">전라북도</option>
													<option value="전주시">전주시</option>
													<option value="전라남도">전라남도</option>
													<option value="목포시">목포시</option>
													<option value="순천시">순천시</option>
													<option value="여수시">여수시</option>
													<option value="경상북도">경상북도</option>
													<option value="경주시">경주시</option>
													<option value="안동시">안동시</option>
													<option value="포항시">포항시</option>
													<option value="경상남도">경상남도</option>
													<option value="김해시">김해시</option>
													<option value="밀양시">밀양시</option>
													<option value="창원시">창원시</option>
													<option value="제주">제주</option>
												</select>
											</div>
											<div class="col-lg-5" style="float: left;">
												<div class="form-group mb-3">
													<h5>날짜 선택</h5>
													<input type="text" name="bk_date" class="form-control date"
														id="singledaterange" data-toggle="date-picker"
														data-cancel-class="btn-warning">
												</div>
											</div>
											<div class="col-lg-2"
												style="float: left; margin-top: 26px; text-align: center;">
												<input type="submit" class="btn btn-secondary btn-big"
													id="searchBtn" value="검색" />
											</div>
											<!-- end col -->
										</form>
									</blockquote>
								</div>
							</div>
							<!-- end Search-card -->
							<div class="card-deck-wrapper">
								<div class="card-deck">
									<div class="card">
										<img class="card-img-top img-fluid"
											src="resources/images/card-medical.jpg" alt="Card image cap">
										<div class="card-body">
											<a href="./businessList?bct_code=M"
												class="btn btn-block btn-success">병원</a>
										</div>
									</div>
									<!-- end card-->
									<div class="card">
										<img class="card-img-top img-fluid"
											src="resources/images/card-beauty.jpg" alt="Card image cap">
										<div class="card-body">
											<a href="./businessList?bct_code=B"
												class="btn btn-block btn-success">미용</a>
										</div>
									</div>
									<!-- end card-->
									<div class="card">
										<img class="card-img-top img-fluid"
											src="resources/images/card-hotel.jpg" alt="Card image cap">
										<div class="card-body">
											<a href="./businessList?bct_code=H"
												class="btn btn-block btn-success">호텔</a>
										</div>
									</div>
									<!-- end card-->
								</div>
								<!-- end card-deck-->
							</div>
							<!-- end card-deck-wrapper-->


							<div class="card" style="margin-top: 30px;">
								<div class="card-body">
									<h3 class="card-title" style="text-align: center;">공지사항</h3>
									<div class="table-responsive-sm" style="margin-top: 20px;">
										<table class="table table-centered mb-0">
											<thead>
												<tr style="text-align: center;">
													<th width="25%">분류</th>
													<th>제목</th>
													<th width="25%">날짜</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="list" items="${list}">
													<tr>
														<td style="text-align: center;">${list.abc_name}</td>
														<td><a href="#" data-toggle="modal"
															data-target="#B${list.abo_no}">${list.abo_title}</a></td>
														<!-- Standard modal content -->
														<div id="B${list.abo_no}" class="modal fade" tabindex="-1"
															role="dialog" aria-labelledby="myModalLabel"
															aria-hidden="true">
															<div class="modal-dialog modal-dialog-centered">
																<div class="modal-content">
																	<div class="modal-header">

																		<h4 class="modal-title" id="myModalLabel">${list.abo_title}</h4>
																		<div class="badge badge-secondary"
																			style="margin-top: 5px; margin-left: 10px">
																			<c:if test="${'개인' == list.abc_name}">개인</c:if>
																			<c:if test="${'기업' == list.abc_name}">기업</c:if>
																		</div>
																		<button type="button" class="close"
																			data-dismiss="modal" aria-hidden="true">×</button>
																	</div>
																	<div class="modal-body">
																		<p>${list.abo_ctt}</p>
																	</div>
																	<!-- <div class="modal-footer">
																		<button type="button" class="btn btn-light"
																			data-dismiss="modal">Close</button>
																	</div> -->
																</div>
																<!-- /.modal-content -->
															</div>
															<!-- /.modal-dialog -->
														</div>
														<!-- /.modal -->
														<td style="text-align: center;">${list.abo_date_string}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>

								</div>
							</div>

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



	<!-- App js -->
	<script src="resources/dist/assets/js/app.min.js"></script>

	<!-- demo app -->
	<script src="resources/dist/assets/js/pages/demo.dashboard.js"></script>
	<!-- end demo js-->

</body>
<script>
	document.getElementById('bct_code').innerHTML = "${bct}";

	$("#searchBtn").click(function() {
		var frm = document.searchFrm;
		for (var i = 0; i < frm.length; i++) {
			if (frm[i].value == "" || frm[i].value == null) {
				if (frm[i].name == "bct_code") {
					alert("서비스를 선택해주세요");
					frm[i].focus();
					return false;
				} else if (frm[i].name == "city") {
					alert("지역을 선택해주세요");
					frm[i].focus();
					return false;
				} else if (frm[i].name == "bk_date") {
					alert("날짜를 선택해주세요");
					frm[i].focus();
					return false;
				}
			}
		}
	});
</script>
</html>
