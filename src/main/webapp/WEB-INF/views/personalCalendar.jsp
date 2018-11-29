<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Respets :: 캘린더</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon" href="resources/images/logo-sm.png" />

<!-- third party css -->
<link
	href="resources/dist/assets/css/vendor/jquery-jvectormap-1.2.2.css"
	rel="stylesheet" type="text/css" />
<link href="resources/dist/assets/css/vendor/fullcalendar.min.css"
	rel="stylesheet" type="text/css" />
<!-- third party css end -->

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />

</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	
</script>
<body data-layout="topnav">

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
						<a href="index.html" class="topnav-logo"> <span
							class="topnav-logo-lg"> <img
								src="resources/images/logo-mint.png" alt="respets logo"
								height="30">
						</span> <span class="topnav-logo-sm"> <img
								src="resources/images/logo_sm.png" alt="respets logo"
								height="30">
						</span>
						</a>

						<ul class="list-unstyled topbar-right-menu float-right mb-0">

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
											</span>Notification
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

										<!-- item-->
										<a href="javascript:void(0);"
											class="dropdown-item notify-item">
											<div class="notify-icon">
												<img src="resources/dist/assets/images/users/avatar-2.jpg"
													class="img-fluid rounded-circle" alt="" />
											</div>
											<p class="notify-details">Cristina Pride</p>
											<p class="text-muted mb-0 user-msg">
												<small>Hi, How are you? What about our next meeting</small>
											</p>
										</a>

										<!-- item-->
										<a href="javascript:void(0);"
											class="dropdown-item notify-item">
											<div class="notify-icon bg-primary">
												<i class="mdi mdi-comment-account-outline"></i>
											</div>
											<p class="notify-details">
												Caleb Flakelar commented on Admin <small class="text-muted">4
													days ago</small>
											</p>
										</a>

										<!-- item-->
										<a href="javascript:void(0);"
											class="dropdown-item notify-item">
											<div class="notify-icon">
												<img src="resources/dist/assets/images/users/avatar-4.jpg"
													class="img-fluid rounded-circle" alt="" />
											</div>
											<p class="notify-details">Karen Robinson</p>
											<p class="text-muted mb-0 user-msg">
												<small>Wow ! this admin looks good and awesome
													design</small>
											</p>
										</a>

										<!-- item-->
										<a href="javascript:void(0);"
											class="dropdown-item notify-item">
											<div class="notify-icon bg-info">
												<i class="mdi mdi-heart"></i>
											</div>
											<p class="notify-details">
												Carlos Crouch liked <b>Admin</b> <small class="text-muted">13
													days ago</small>
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
										src="resources/dist/assets/images/users/avatar-1.jpg"
										alt="user-image" class="rounded-circle">
								</span> <span> <span class="account-user-name">Dominic
											Keller</span> <span class="account-position">Founder</span>
								</span>
							</a>
								<div
									class="dropdown-menu dropdown-menu-right dropdown-menu-animated profile-dropdown ">
									<!-- item-->
									<div class=" dropdown-header noti-title">
										<h6 class="text-overflow m-0">Welcome ${no}님!</h6>
									</div>

									<!-- item-->
									<a href="pages-profile.html" class="dropdown-item notify-item">
										<i class="mdi mdi-account-circle"></i> <span>마이페이지</span>
									</a>

									<!-- item-->
									<a href="personalCalendar" class="dropdown-item notify-item">
										<i class="mdi mdi-account-settings-variant"></i> <span>캘린더</span>
									</a>

									<!-- item-->
									<a href="pages-logout.html" class="dropdown-item notify-item">
										<i class="mdi mdi-logout"></i> <span>로그아웃</span>
									</a>

								</div></li>

						</ul>
						<a class="navbar-toggle" data-toggle="collapse"
							data-target="#topnav-menu-content">
							<div class="lines">
								<span></span> <span></span> <span></span>
							</div>
						</a>
					</div>
				</div>
				<!-- end Topbar -->

				<div class="topnav">
					<div class="container-fluid">
						<nav class="navbar navbar-dark navbar-expand-lg topnav-menu">

							<div class="collapse navbar-collapse" id="topnav-menu-content">
								<ul class="navbar-nav">
									<li class="nav-item"><a href="index.html" class="nav-link">
											<i class="mdi mdi-clipboard-check mr-1"></i>최근 예약 목록
									</a></li>
									<li class="nav-item"><a href="index.html" class="nav-link">
											<i class="mdi mdi-clipboard-text mr-1"></i>전체 예약 목록
									</a></li>
									<li class="nav-item"><a href="index.html" class="nav-link">
											<i class="mdi mdi-cat mr-1"></i>나의 동물 목록
									</a></li>
									<li class="nav-item"><a href="index.html" class="nav-link">
											<i class="mdi mdi-heart mr-1"></i>즐겨찾기 목록
									</a></li>
									<li class="nav-item"><a href="index.html" class="nav-link">
											<i class="mdi mdi-account-circle mr-1"></i>개인 정보 확인
									</a></li>
								</ul>
							</div>
						</nav>
					</div>
				</div>


				<!-- Start Content-->
				<div class="container-fluid">

					<!-- start page title -->
					<div class="row">
						<div class="col-12">
							<div class="page-title-box">
								<div class="page-title-right">
									<form class="form-inline"></form>
								</div>
								<h4 class="page-title">내 반려동물 달력</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->

					<div class="row">
						<div class="col-12">
							<div class="card">
								<div class="card-body">

									<!-- <h4 class="header-title">캘린더</h4> -->

									<div class="col-12">
										<div id="calendar"></div>
									</div>
									<!-- end col -->


									<!-- 캘린더 div -->

									<!-- <div class="col-12">
										<div id="calendar" class="fc fc-unthemed fc-ltr">
											<div class="fc-toolbar fc-header-toolbar">
												<div class="fc-left">
													
												</div>
												<div class="fc-right">
													<div class="fc-button-group">
														<button type="button"
															class="fc-prev-button fc-button fc-state-default fc-corner-left"
															aria-label="prev">
															<span class="fc-icon fc-icon-left-single-arrow"></span>
														</button>
														<button type="button"
															class="fc-next-button fc-button fc-state-default fc-corner-right"
															aria-label="next">
															<span class="fc-icon fc-icon-right-single-arrow"></span>
														</button>
													</div>
													<button type="button"
														class="fc-today-button fc-button fc-state-default fc-corner-left fc-corner-right fc-state-disabled"
														disabled="">today</button>
												</div>
												<div class="fc-center">
													<h2>November 2018</h2>
												</div>
												<div class="fc-clear"></div>
											</div>
											<div class="fc-view-container" style="">
												<div class="fc-view fc-month-view fc-basic-view" style="">
													<table class="">
														<thead class="fc-head">
															<tr>
																<td class="fc-head-container fc-widget-header"><div
																		class="fc-row fc-widget-header">
																		<table class="">
																			<thead>
																				<tr>
																					<th class="fc-day-header fc-widget-header fc-sun"><span>Sun</span></th>
																					<th class="fc-day-header fc-widget-header fc-mon"><span>Mon</span></th>
																					<th class="fc-day-header fc-widget-header fc-tue"><span>Tue</span></th>
																					<th class="fc-day-header fc-widget-header fc-wed"><span>Wed</span></th>
																					<th class="fc-day-header fc-widget-header fc-thu"><span>Thu</span></th>
																					<th class="fc-day-header fc-widget-header fc-fri"><span>Fri</span></th>
																					<th class="fc-day-header fc-widget-header fc-sat"><span>Sat</span></th>
																				</tr>
																			</thead>
																		</table>
																	</div></td>
															</tr>
														</thead>
														<tbody class="fc-body">
															<tr>
																<td class="fc-widget-content"><div
																		class="fc-scroller fc-day-grid-container"
																		style="overflow: hidden; height: 448.2px;">
																		<div class="fc-day-grid fc-unselectable">
																			<div
																				class="fc-row fc-week fc-widget-content fc-rigid"
																				style="height: 74px;">
																				<div class="fc-bg">
																					<table class="">
																						<tbody>
																							<tr>
																								<td
																									class="fc-day fc-widget-content fc-sun fc-other-month fc-past"
																									data-date="2018-10-28"></td>
																								<td
																									class="fc-day fc-widget-content fc-mon fc-other-month fc-past"
																									data-date="2018-10-29"></td>
																								<td
																									class="fc-day fc-widget-content fc-tue fc-other-month fc-past"
																									data-date="2018-10-30"></td>
																								<td
																									class="fc-day fc-widget-content fc-wed fc-other-month fc-past"
																									data-date="2018-10-31"></td>
																								<td
																									class="fc-day fc-widget-content fc-thu fc-past"
																									data-date="2018-11-01"></td>
																								<td
																									class="fc-day fc-widget-content fc-fri fc-past"
																									data-date="2018-11-02"></td>
																								<td
																									class="fc-day fc-widget-content fc-sat fc-past"
																									data-date="2018-11-03"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																				<div class="fc-content-skeleton">
																					<table>
																						<thead>
																							<tr>
																								<td
																									class="fc-day-top fc-sun fc-other-month fc-past"
																									data-date="2018-10-28"><span
																									class="fc-day-number">28</span></td>
																								<td
																									class="fc-day-top fc-mon fc-other-month fc-past"
																									data-date="2018-10-29"><span
																									class="fc-day-number">29</span></td>
																								<td
																									class="fc-day-top fc-tue fc-other-month fc-past"
																									data-date="2018-10-30"><span
																									class="fc-day-number">30</span></td>
																								<td
																									class="fc-day-top fc-wed fc-other-month fc-past"
																									data-date="2018-10-31"><span
																									class="fc-day-number">31</span></td>
																								<td class="fc-day-top fc-thu fc-past"
																									data-date="2018-11-01"><span
																									class="fc-day-number">1</span></td>
																								<td class="fc-day-top fc-fri fc-past"
																									data-date="2018-11-02"><span
																									class="fc-day-number">2</span></td>
																								<td class="fc-day-top fc-sat fc-past"
																									data-date="2018-11-03"><span
																									class="fc-day-number">3</span></td>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</div>
																			<div
																				class="fc-row fc-week fc-widget-content fc-rigid"
																				style="height: 74px;">
																				<div class="fc-bg">
																					<table class="">
																						<tbody>
																							<tr>
																								<td
																									class="fc-day fc-widget-content fc-sun fc-past"
																									data-date="2018-11-04"></td>
																								<td
																									class="fc-day fc-widget-content fc-mon fc-past"
																									data-date="2018-11-05"></td>
																								<td
																									class="fc-day fc-widget-content fc-tue fc-past"
																									data-date="2018-11-06"></td>
																								<td
																									class="fc-day fc-widget-content fc-wed fc-past"
																									data-date="2018-11-07"></td>
																								<td
																									class="fc-day fc-widget-content fc-thu fc-past"
																									data-date="2018-11-08"></td>
																								<td
																									class="fc-day fc-widget-content fc-fri fc-past"
																									data-date="2018-11-09"></td>
																								<td
																									class="fc-day fc-widget-content fc-sat fc-past"
																									data-date="2018-11-10"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																				<div class="fc-content-skeleton">
																					<table>
																						<thead>
																							<tr>
																								<td class="fc-day-top fc-sun fc-past"
																									data-date="2018-11-04"><span
																									class="fc-day-number">4</span></td>
																								<td class="fc-day-top fc-mon fc-past"
																									data-date="2018-11-05"><span
																									class="fc-day-number">5</span></td>
																								<td class="fc-day-top fc-tue fc-past"
																									data-date="2018-11-06"><span
																									class="fc-day-number">6</span></td>
																								<td class="fc-day-top fc-wed fc-past"
																									data-date="2018-11-07"><span
																									class="fc-day-number">7</span></td>
																								<td class="fc-day-top fc-thu fc-past"
																									data-date="2018-11-08"><span
																									class="fc-day-number">8</span></td>
																								<td class="fc-day-top fc-fri fc-past"
																									data-date="2018-11-09"><span
																									class="fc-day-number">9</span></td>
																								<td class="fc-day-top fc-sat fc-past"
																									data-date="2018-11-10"><span
																									class="fc-day-number">10</span></td>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</div>
																			<div
																				class="fc-row fc-week fc-widget-content fc-rigid"
																				style="height: 74px;">
																				<div class="fc-bg">
																					<table class="">
																						<tbody>
																							<tr>
																								<td
																									class="fc-day fc-widget-content fc-sun fc-past"
																									data-date="2018-11-11"></td>
																								<td
																									class="fc-day fc-widget-content fc-mon fc-past"
																									data-date="2018-11-12"></td>
																								<td
																									class="fc-day fc-widget-content fc-tue fc-past"
																									data-date="2018-11-13"></td>
																								<td
																									class="fc-day fc-widget-content fc-wed fc-past"
																									data-date="2018-11-14"></td>
																								<td
																									class="fc-day fc-widget-content fc-thu fc-past"
																									data-date="2018-11-15"></td>
																								<td
																									class="fc-day fc-widget-content fc-fri fc-today "
																									data-date="2018-11-16"></td>
																								<td
																									class="fc-day fc-widget-content fc-sat fc-future"
																									data-date="2018-11-17"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																				<div class="fc-content-skeleton">
																					<table>
																						<thead>
																							<tr>
																								<td class="fc-day-top fc-sun fc-past"
																									data-date="2018-11-11"><span
																									class="fc-day-number">11</span></td>
																								<td class="fc-day-top fc-mon fc-past"
																									data-date="2018-11-12"><span
																									class="fc-day-number">12</span></td>
																								<td class="fc-day-top fc-tue fc-past"
																									data-date="2018-11-13"><span
																									class="fc-day-number">13</span></td>
																								<td class="fc-day-top fc-wed fc-past"
																									data-date="2018-11-14"><span
																									class="fc-day-number">14</span></td>
																								<td class="fc-day-top fc-thu fc-past"
																									data-date="2018-11-15"><span
																									class="fc-day-number">15</span></td>
																								<td class="fc-day-top fc-fri fc-today "
																									data-date="2018-11-16"><span
																									class="fc-day-number">16</span></td>
																								<td class="fc-day-top fc-sat fc-future"
																									data-date="2018-11-17"><span
																									class="fc-day-number">17</span></td>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td class="fc-event-container"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end bg-success fc-draggable"><div
																											class="fc-content">
																											<span class="fc-time">10:56a</span> <span
																												class="fc-title">See John Deo</span>
																										</div></a></td>
																								<td></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</div>
																			<div
																				class="fc-row fc-week fc-widget-content fc-rigid"
																				style="height: 74px;">
																				<div class="fc-bg">
																					<table class="">
																						<tbody>
																							<tr>
																								<td
																									class="fc-day fc-widget-content fc-sun fc-future"
																									data-date="2018-11-18"></td>
																								<td
																									class="fc-day fc-widget-content fc-mon fc-future"
																									data-date="2018-11-19"></td>
																								<td
																									class="fc-day fc-widget-content fc-tue fc-future"
																									data-date="2018-11-20"></td>
																								<td
																									class="fc-day fc-widget-content fc-wed fc-future"
																									data-date="2018-11-21"></td>
																								<td
																									class="fc-day fc-widget-content fc-thu fc-future"
																									data-date="2018-11-22"></td>
																								<td
																									class="fc-day fc-widget-content fc-fri fc-future"
																									data-date="2018-11-23"></td>
																								<td
																									class="fc-day fc-widget-content fc-sat fc-future"
																									data-date="2018-11-24"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																				<div class="fc-content-skeleton">
																					<table>
																						<thead>
																							<tr>
																								<td class="fc-day-top fc-sun fc-future"
																									data-date="2018-11-18"><span
																									class="fc-day-number">18</span></td>
																								<td class="fc-day-top fc-mon fc-future"
																									data-date="2018-11-19"><span
																									class="fc-day-number">19</span></td>
																								<td class="fc-day-top fc-tue fc-future"
																									data-date="2018-11-20"><span
																									class="fc-day-number">20</span></td>
																								<td class="fc-day-top fc-wed fc-future"
																									data-date="2018-11-21"><span
																									class="fc-day-number">21</span></td>
																								<td class="fc-day-top fc-thu fc-future"
																									data-date="2018-11-22"><span
																									class="fc-day-number">22</span></td>
																								<td class="fc-day-top fc-fri fc-future"
																									data-date="2018-11-23"><span
																									class="fc-day-number">23</span></td>
																								<td class="fc-day-top fc-sat fc-future"
																									data-date="2018-11-24"><span
																									class="fc-day-number">24</span></td>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td class="fc-event-container fc-limited"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end bg-warning fc-draggable"><div
																											class="fc-content">
																											<span class="fc-time">6:49a</span> <span
																												class="fc-title">Hey!</span>
																										</div></a></td>
																								<td class="fc-more-cell" rowspan="1"><div>
																										<a class="fc-more">+2 more</a>
																									</div></td>
																								<td rowspan="2"></td>
																								<td class="fc-event-container" rowspan="2"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end bg-primary fc-draggable"><div
																											class="fc-content">
																											<span class="fc-time">8:49a</span> <span
																												class="fc-title">Buy a Theme</span>
																										</div></a></td>
																								<td rowspan="2"></td>
																								<td rowspan="2"></td>
																								<td rowspan="2"></td>
																								<td rowspan="2"></td>
																							</tr>
																							<tr class="fc-limited">
																								<td class="fc-event-container"><a
																									class="fc-day-grid-event fc-h-event fc-event fc-start fc-end bg-info fc-draggable"><div
																											class="fc-content">
																											<span class="fc-time">9:36a</span> <span
																												class="fc-title">Meet John Deo</span>
																										</div></a></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</div>
																			<div
																				class="fc-row fc-week fc-widget-content fc-rigid"
																				style="height: 74px;">
																				<div class="fc-bg">
																					<table class="">
																						<tbody>
																							<tr>
																								<td
																									class="fc-day fc-widget-content fc-sun fc-future"
																									data-date="2018-11-25"></td>
																								<td
																									class="fc-day fc-widget-content fc-mon fc-future"
																									data-date="2018-11-26"></td>
																								<td
																									class="fc-day fc-widget-content fc-tue fc-future"
																									data-date="2018-11-27"></td>
																								<td
																									class="fc-day fc-widget-content fc-wed fc-future"
																									data-date="2018-11-28"></td>
																								<td
																									class="fc-day fc-widget-content fc-thu fc-future"
																									data-date="2018-11-29"></td>
																								<td
																									class="fc-day fc-widget-content fc-fri fc-future"
																									data-date="2018-11-30"></td>
																								<td
																									class="fc-day fc-widget-content fc-sat fc-other-month fc-future"
																									data-date="2018-12-01"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																				<div class="fc-content-skeleton">
																					<table>
																						<thead>
																							<tr>
																								<td class="fc-day-top fc-sun fc-future"
																									data-date="2018-11-25"><span
																									class="fc-day-number">25</span></td>
																								<td class="fc-day-top fc-mon fc-future"
																									data-date="2018-11-26"><span
																									class="fc-day-number">26</span></td>
																								<td class="fc-day-top fc-tue fc-future"
																									data-date="2018-11-27"><span
																									class="fc-day-number">27</span></td>
																								<td class="fc-day-top fc-wed fc-future"
																									data-date="2018-11-28"><span
																									class="fc-day-number">28</span></td>
																								<td class="fc-day-top fc-thu fc-future"
																									data-date="2018-11-29"><span
																									class="fc-day-number">29</span></td>
																								<td class="fc-day-top fc-fri fc-future"
																									data-date="2018-11-30"><span
																									class="fc-day-number">30</span></td>
																								<td
																									class="fc-day-top fc-sat fc-other-month fc-future"
																									data-date="2018-12-01"><span
																									class="fc-day-number">1</span></td>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</div>
																			<div
																				class="fc-row fc-week fc-widget-content fc-rigid"
																				style="height: 78px;">
																				<div class="fc-bg">
																					<table class="">
																						<tbody>
																							<tr>
																								<td
																									class="fc-day fc-widget-content fc-sun fc-other-month fc-future"
																									data-date="2018-12-02"></td>
																								<td
																									class="fc-day fc-widget-content fc-mon fc-other-month fc-future"
																									data-date="2018-12-03"></td>
																								<td
																									class="fc-day fc-widget-content fc-tue fc-other-month fc-future"
																									data-date="2018-12-04"></td>
																								<td
																									class="fc-day fc-widget-content fc-wed fc-other-month fc-future"
																									data-date="2018-12-05"></td>
																								<td
																									class="fc-day fc-widget-content fc-thu fc-other-month fc-future"
																									data-date="2018-12-06"></td>
																								<td
																									class="fc-day fc-widget-content fc-fri fc-other-month fc-future"
																									data-date="2018-12-07"></td>
																								<td
																									class="fc-day fc-widget-content fc-sat fc-other-month fc-future"
																									data-date="2018-12-08"></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																				<div class="fc-content-skeleton">
																					<table>
																						<thead>
																							<tr>
																								<td
																									class="fc-day-top fc-sun fc-other-month fc-future"
																									data-date="2018-12-02"><span
																									class="fc-day-number">2</span></td>
																								<td
																									class="fc-day-top fc-mon fc-other-month fc-future"
																									data-date="2018-12-03"><span
																									class="fc-day-number">3</span></td>
																								<td
																									class="fc-day-top fc-tue fc-other-month fc-future"
																									data-date="2018-12-04"><span
																									class="fc-day-number">4</span></td>
																								<td
																									class="fc-day-top fc-wed fc-other-month fc-future"
																									data-date="2018-12-05"><span
																									class="fc-day-number">5</span></td>
																								<td
																									class="fc-day-top fc-thu fc-other-month fc-future"
																									data-date="2018-12-06"><span
																									class="fc-day-number">6</span></td>
																								<td
																									class="fc-day-top fc-fri fc-other-month fc-future"
																									data-date="2018-12-07"><span
																									class="fc-day-number">7</span></td>
																								<td
																									class="fc-day-top fc-sat fc-other-month fc-future"
																									data-date="2018-12-08"><span
																									class="fc-day-number">8</span></td>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																								<td></td>
																							</tr>
																						</tbody>
																					</table>
																				</div>
																			</div>
																		</div>
																	</div></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div> -->
								</div>
								<!-- end card-body-->
								<div class="modal fade show" id="event-modal" tabindex="-1"
									style="display: none; padding-right: 16px;">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header pr-4 pl-4 border-bottom-0 d-block">
												<button type="button" class="close" data-dismiss="modal"
													aria-hidden="true">×</button>
												<h4 class="modal-title">일정 확인</h4>
											</div>
											<div class="modal-body pt-3 pr-4 pl-4">
												<form>
													<label>Change event name</label>
													<div class="input-group m-b-15">
														<input class="form-control" type="text"
															value="See John Deo"><span
															class="input-group-append"><button type="submit"
																class="btn btn-success btn-md  ">
																<i class="fa fa-check"></i> Save
															</button></span>
													</div>
												</form>
											</div>
											<div class="text-right pb-4 pr-4">
												<button type="button" class="btn btn-light "
													data-dismiss="modal">Close</button>
												
											</div>
										</div>
										<!-- end modal-content-->
									</div>
									<!-- end modal dialog-->
								</div>
							</div>
							<!-- end card-->
						</div>
						<!-- end col-->
						<!-- end col-->
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
	<script src="/resources/dist/assets/js/app.min.js"></script>

	<!-- third party js -->
	<script src="resources/dist/assets/js/vendor/jquery-jvectormap-1.2.2.min.js"></script>
	<script src="resources/dist/assets/js/vendor/jquery-jvectormap-world-mill-en.js"></script>
	<script src="/resources/dist/assets/js/vendor/jquery-ui.min.js"></script>
	<script src="/resources/dist/assets/js/vendor/fullcalendar-per.min.js"></script>
	<!-- third party js ends -->

	<!-- demo app -->
	<!-- <script src="/resources/dist/assets/js/pages/demo.calendar-per.js"></script> -->
<script>

</script>
<script>
! function (e) {
    "use strict";
    var t = function () {
        this.$body = e("body"), this.$modal = e("#event-modal"),
            this.$event = "#external-events div.external-event",
            this.$calendar = e("#calendar"), this.$saveCategoryBtn = e(".save-category"),
            this.$categoryForm = e("#add-category form"), this.$extEvents = e("#external-events"),
            this.$calendarObj = null
    };
    t.prototype.onDrop = function (t, n) {
            var a = t.data("eventObject"),
                l = t.attr("data-class"),
                i = e.extend({}, a);
            i.start = n, l && (i.className = [l]),
                this.$calendar.fullCalendar("renderEvent", i, !0),
                e("#drop-remove").is(":checked") && t.remove()
        }, t.prototype.onEventClick = function (t, n, a) {
        	var jsonData = ${e};
        	console.log(jsonData);
        	for(var i=0; i<jsonData.length; i++) {
        		console.log(jsonData[i].bk_no);
        	}
            var l = this,
                i = e("<form></form>");
            i.append("<label>정보 확인</label>"),
                i.append("<div class='input-group m-b-15'><input class='form-control' type=text value='" + t.title + "' />" +
                    "<span class='input-group-append'>" +
                    "</div>"),
                l.$modal.modal({
                    backdrop: "static"
                }), l.$modal.find(".delete-event").show().end().find(".save-event").hide().end().find(".modal-body").empty().prepend(i).end().
            find(".delete-event").unbind("click").click(function () {
                l.$calendarObj.fullCalendar("removeEvents", function (e) {
                        return e._id == t._id
                    }),
                    l.$modal.modal("hide")
            }), l.$modal.find("form").on("submit", function () {
                return t.title = i.find("input[type=text]").val(),
                    l.$calendarObj.fullCalendar("updateEvent", t), l.$modal.modal("hide"), !1
            })
        },
        t.prototype.onSelect = function (t, n, a) {
            var l = this;
            l.$modal.modal({
                backdrop: "static"
            });
            var i = e("<form></form>");
        
                l.$modal.find(".delete-event").hide().end().find(".save-event").show().end().find(".modal-body")
                .empty().prepend(i).end().find(".save-event").unbind("click").click(function () {
                    i.submit()
                }), l.$modal.find("form").on("submit", function () {
                    var e = i.find("input[name='title']")
                        .val(),
                        a = (i.find("input[name='beginning']")
                            .val(), i.find("input[name='ending']")
                            .val(), i.find("select[name='category'] option:checked").val());
                    return null !== e && 0 != e.length ? (l.$calendarObj.fullCalendar("renderEvent", {
                            title: e,
                            start: t,
                            end: n,
                            allDay: !1,
                            className: a
                        }, !0),
                        l.$modal.modal("hide")) : alert("You have to give a title to your event"), !1
                }),
                l.$calendarObj.fullCalendar("unselect")
        }, t.prototype.enableDrag = function () {
            e(this.$event).each(function () {
                var t = {
                    title: e.trim(e(this).text())
                };
                e(this).data("eventObject", t),
                    e(this).draggable({
                        zIndex: 999,
                        revert: !0,
                        revertDuration: 0
                    })
            })
        }, t.prototype.init = function () {
            this.enableDrag();
            var data = ${e};
            var t = new Date,
                n = (
                    t.getDate(), t.getMonth(), t.getFullYear(),
                    new Date(e.now())),
                a = data,
                l = this;
            l.$calendarObj = l.$calendar.fullCalendar({
                    slotDuration: "00:15:00",
                    minTime: "08:00:00",
                    maxTime: "19:00:00",
                    defaultView: "month",
                    handleWindowResize: !0,
                    height: e(window).height() - 200,
                    header: {
                        left: "prev,next today",
                        center: "title",
                        right: "month,agendaWeek,agendaDay"
                    },
                    events: a,
                    editable: !0,
                    droppable: !0,
                    eventLimit: !0,
                    selectable: !0,
                    drop: function (t) {
                        l.onDrop(e(this), t)
                    },
                    select: function (e, t, n) {
                        l.onSelect(e, t, n)
                    },
                    eventClick: function (e, t, n) {
                        l.onEventClick(e, t, n)
                    }
                }),
                this.$saveCategoryBtn.on("click", function () {
                    var e = l.$categoryForm.find("input[name='category-name']").val(),
                        t = l.$categoryForm.find("select[name='category-color']").val();
                    null !== e && 0 != e.length && (l.$extEvents.append('<div class="external-event bg-' + t + '" data-class="bg-' + t + '" style="position: relative;"><i class="mdi mdi-checkbox-blank-circle mr-2 vertical-middle"></i>' + e + "</div>"),
                        l.enableDrag())
                })
        }, e.CalendarApp = new t, e.CalendarApp.Constructor = t
}(window.jQuery),
function (e) {
    "use strict";
    e.CalendarApp.init()
}(window.jQuery);
</script>

	<script src="resources/dist/assets/js/pages/demo.widgets.js"></script>
	<script src="resources/dist/assets/js/pages/demo.dashboard.js"></script>
	<!-- end demo js-->

</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script>
/* var jsonData = ${e};
console.log(jsonData.length);
console.log(jsonData);
for(var i=0; i<jsonData.length; i++) {
} */
/* console.log(jsonData.bookingList.length);
for(var i=0; i<jsonData.bookingList.length; i++) {
	console.log(jsonData.bookingList[i]);
		var title = jsonData.bookingList[i].PET_NAME;
		var start = jsonData.bookingList[i].VS_START;
		var end = jsonData.bookingList[i].VS_END;
		console.log("title="+title);
		console.log('start='+ start);
		console.log('end='+ end);
		$('#calendar').fullCalendar
	$.each(jsonData.bookingList[i], function(key, value) {
		console.log("key: " + key + " / " + "value" + value);
	});
}; */

</script>
</html>
