<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>리스펫츠</title>
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

<body data-layout="topnav">

${no} <!-- 지예 -->
${alert} <!--현휘; 메일의 유효시간이 경과했다는 안내 || 존재하지 않는 메일이라는 안내 alert -->
${findPw} <!--현휘; 비밀번호 설정 이메일을 보냈다는 안내 alert --> 
${sessionCheck} <!-- 진선 -->
	<!-- Begin page -->
	<div class="wrapper">

		<!-- ============================================================== -->
		<!-- Start Page Content here -->
		<!-- ============================================================== -->

		<div class="content-page">
			<div class="content">

				<!-- Topbar Start -->
				<div class="navbar-custom topnav-navbar">
					<div class="container-fluid">

						<!-- LOGO -->
						<a href="index.jsp" class="topnav-logo"> <span
							class="topnav-logo-lg"> <img
								src="resources/images/logo-mint.png"
								alt="respets logo" height="30">
						</span> <span class="topnav-logo-sm"> <img
								src="resources/images/logo-sm.png"
								alt="respets logo" height="30">
						</span>
						</a>
						<button id="loginForm" onclick="location.href='./loginForm'">로그인</button>
						<button id="joinChoiceForm"
							onclick="location.href='./joinChoiceForm'">회원가입</button>

						<%-- 						<ul class="list-unstyled topbar-right-menu float-right mb-0">

							<li class="dropdown notification-list"><a
								class="nav-link dropdown-toggle arrow-none"
								data-toggle="dropdown" href="#" role="button"
								aria-haspopup="false" aria-expanded="false"> <i
									class="dripicons-bell noti-icon"></i> <span
									class="noti-icon-badge"></span>
							</a>
								<div
									class="dropdown-menu dropdown-menu-right dropdown-menu-animated dropdown-lg">

									<!-- item-->
									<div class="dropdown-item noti-title">
										<h5 class="m-0">
											<span class="float-right"> <a
												href="javascript: void(0);" class="text-dark"> <small>Clear
														All</small>
											</a>
											</span>알림
										</h5>
									</div>

									<div class="slimscroll" style="max-height: 230px;">
										<!-- item-->
										<a href="javascript:void(0);"
											class="dropdown-item notify-item">
											<div class="notify-icon bg-primary">
												<i class="mdi mdi-comment-account-outline"></i>
											</div>
											<p class="notify-details">
												Caleb Flakelar commented on Admin <small class="text-muted">1
													min ago</small>
											</p>
										</a>

										<!-- item-->
										<a href="javascript:void(0);"
											class="dropdown-item notify-item">
											<div class="notify-icon bg-info">
												<i class="mdi mdi-account-plus"></i>
											</div>
											<p class="notify-details">
												New user registered. <small class="text-muted">5
													hours ago</small>
											</p>
										</a>
									</div>

									<!-- All-->
									<a href="javascript:void(0);"
										class="dropdown-item text-center text-primary notify-item notify-all">
										View All </a>

								</div></li>

							<li class="dropdown notification-list"><a
								class="nav-link dropdown-toggle nav-user arrow-none mr-0"
								data-toggle="dropdown" href="#" role="button"
								aria-haspopup="false" aria-expanded="false"> <span
									class="account-user-avatar"> <img
										src="${pageContext.request.contextPath}/resources/dist/assets/images/users/avatar-1.jpg"
										alt="user-image" class="rounded-circle">
								</span> <span> <span class="account-user-name">양남</span> <span
										class="account-position">개인회원</span>
								</span>
							</a>
								<div
									class="dropdown-menu dropdown-menu-right dropdown-menu-animated profile-dropdown ">
									<!-- item-->
									<div class=" dropdown-header noti-title">
										<h6 class="text-overflow m-0">Welcome Respets!</h6>
									</div>

									<!-- item-->
									<a href="javascript:void(0);" class="dropdown-item notify-item">
										<i class="mdi mdi-account-settings-variant"></i> <span>스케줄</span>
									</a>

									<!-- item-->
									<a href="javascript:void(0);" class="dropdown-item notify-item">
										<i class="mdi mdi-account-circle"></i> <span>마이페이지</span>
									</a>

									<!-- item-->
									<a href="javascript:void(0);" class="dropdown-item notify-item">
										<i class="mdi mdi-logout"></i> <span>로그아웃</span>
									</a>

								</div></li>

						</ul> --%>
						<a class="navbar-toggle" data-toggle="collapse"
							data-target="#topnav-menu-content">
							<div class="lines">
								<span></span> <span></span> <span></span>
							</div>
						</a>
					</div>
				</div>
				<!-- end Topbar -->


				<!-- Start Content-->
				<div class="container-fluid">

					<!-- start page title -->
					<div class="row">
						<div class="col-12">
							<div class="page-title-box">
								<h4 class="page-title">로그인을 하면 더 많은 서비스 이용이 가능합니다!</h4>
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
										<form action="searchList" method="get">
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
													<option value="경기도">경기도</option>
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
													value="검색" />
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
											src="${pageContext.request.contextPath}/resources/images/card-medical.jpg"
											alt="Card image cap">
										<div class="card-body">
											<h3 class="card-title" style="text-align: center;">병원</h3>
											<a href="./businessList?bct_code=M" class="btn btn-block btn-success">Button</a>
										</div>
									</div>
									<!-- end card-->
									<div class="card">
										<img class="card-img-top img-fluid"
											src="${pageContext.request.contextPath}/resources/images/card-beauty.jpg"
											alt="Card image cap">
										<div class="card-body">
											<h3 class="card-title" style="text-align: center;">미용</h3>
											<a href="./businessList?bct_code=B" class="btn btn-block btn-success">Button</a>
										</div>
									</div>
									<!-- end card-->
									<div class="card">
										<img class="card-img-top img-fluid"
											src="${pageContext.request.contextPath}/resources/images/card-hotel.jpg"
											alt="Card image cap">
										<div class="card-body">
											<h3 class="card-title" style="text-align: center;">호텔</h3>
											<a href="./businessList?bct_code=H" class="btn btn-block btn-success">Button</a>
										</div>
									</div>
									<!-- end card-->
								</div>
								<!-- end card-deck-->
							</div>
							<!-- end card-deck-wrapper-->

							<div class="card" style="margin-top: 30px;">
								<div class="card-body" style="text-align: center;">
									<h3 class="card-title" style="text-align: center;">공지사항</h3>
									<div class="table-responsive-sm" style="margin-top: 20px;">
										<table class="table table-centered mb-0">
											<thead>
												<tr>
													<th>Name</th>
													<th>Phone Number</th>
													<th>Date of Birth</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>Risa D. Pearson</td>
													<td>336-508-2157</td>
													<td>July 24, 1950</td>
												</tr>
												<tr>
													<td>Ann C. Thompson</td>
													<td>646-473-2057</td>
													<td>January 25, 1959</td>
												</tr>
												<tr>
													<td>Paul J. Friend</td>
													<td>281-308-0793</td>
													<td>September 1, 1939</td>
												</tr>
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
			<footer class="footer">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-6">2018 © Hyper - Coderthemes.com</div>
						<div class="col-md-6">
							<div class="text-md-right footer-links d-none d-md-block">
								<a href="javascript: void(0);">About</a> <a
									href="javascript: void(0);">Support</a> <a
									href="javascript: void(0);">Contact Us</a>
							</div>
						</div>
					</div>
				</div>
			</footer>
			<!-- end Footer -->

		</div>

		<!-- ============================================================== -->
		<!-- End Page content -->
		<!-- ============================================================== -->


	</div>
	<!-- END wrapper -->



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
<!-- 서진 : 서비스 종류를 추가하는 script -->
<script>
	document.getElementById('bct_code').innerHTML = "${bct}";
</script>
</html>
