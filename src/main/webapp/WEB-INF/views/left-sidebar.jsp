<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>left-sidebar</title>
</head>
<body>

	<div class="left-side-menu">

		<div class="slimscroll-menu">

			<!-- LOGO -->
			<a href="./" class="logo text-center"> <span
				class="logo-lg"> <img
					src="resources/images/logo-white.png"
					alt="respets logo" height="30">
			</span> <span class="logo-sm"> <img
					src="resources/images/logo-sm-white.png"
					alt="respets logo" height="30">
			</span>
			</a>

			<!--- Sidemenu -->
			<ul class="metismenu side-nav">
				<c:if test="${fn:substring(no,0,1) eq 'P'}">
					<li class="side-nav-title side-nav-item"><h5>${name} 님의 마이페이지</h5></li>
	
					<li class="side-nav-item"><a href="recentMyBookingList" class="side-nav-link">
							<i class="dripicons-to-do"></i> <!-- <span class="badge badge-success float-right">
							1</span> --> <span> 최근	예약 목록 </span>
					</a></li>
	
					<li class="side-nav-item"><a href="personalAllBookingList" class="side-nav-link">
							<i class="dripicons-view-list"></i> <span> 전체 예약 목록 </span>
					</a></li>
	
					<li class="side-nav-item"><a href="likeBusinessList" class="side-nav-link">
							<i class="dripicons-heart"></i> <span> 즐겨찾기 목록 </span>
					</a></li>
	
					<li class="side-nav-item"><a href="petList" class="side-nav-link">
							<i class="dripicons-article"></i> <span> 나의 반려동물 정보 </span>
					</a></li>
	
					<li class="side-nav-item"><a href="myInfo" class="side-nav-link">
							<i class="dripicons-user"></i> <span> 나의 회원 정보 </span>
					</a></li>
				</c:if>

				<c:if test="${fn:substring(no,0,1) eq 'B'}">
					<li class="side-nav-title side-nav-item"><h5>${name} 마이페이지</h5></li>
	
					<li class="side-nav-item"><a href="todayScheduleList" class="side-nav-link">
							<i class="dripicons-to-do"></i> <!-- <span class="badge badge-success float-right">
							1</span> --> <span> 오늘 일정 목록 </span>
					</a></li>
	
					<li class="side-nav-item"><a href="newScheduleList" class="side-nav-link">
							<i class="dripicons-checklist"></i> <span> 새로운 예약 </span>
					</a></li>
	
					<li class="side-nav-item"><a href="businessBookingList" class="side-nav-link">
							<i class="dripicons-view-list"></i> <span> 전체 예약 목록 </span>
					</a></li>
	
					<li class="side-nav-item"><a href="serviceManagement" class="side-nav-link">
							<i class="dripicons-gear"></i> <span> 서비스 관리 </span>
					</a></li>
	
					<li class="side-nav-item"><a href="stepListBut" class="side-nav-link">
							<i class="dripicons-user-group"></i> <span> 직원 관리 </span>
					</a></li>
					
					<li class="side-nav-item"><a href="businessInfoDetail" class="side-nav-link">
							<i class="dripicons-user"></i> <span> 나의 회원 정보 </span>
					</a></li>
	
					<li class="side-nav-item"><a href="businessNoticeList" class="side-nav-link">
							<i class="dripicons-clipboard"></i> <span> 공지사항 관리 </span>
					</a></li>
				
				</c:if>

				<c:if test="${fn:substring(no,0,1) eq '1'}">
					<li class="side-nav-title side-nav-item"><h5>관리자 마이페이지</h5></li>
	
					<li class="side-nav-item"><a href="unconfirmBusiness" class="side-nav-link">
							<i class="dripicons-checklist"></i> <!-- <span class="badge badge-success float-right">
							1</span> --> <span> 미인증 기업 목록 </span>
					</a></li>
	
					<!-- <li class="side-nav-item"><a href="#" class="side-nav-link">
							<i class="dripicons-view-list"></i> <span> 인증 기업 목록 </span>
					</a></li>
	 -->
					<!-- <li class="side-nav-item"><a href="#" class="side-nav-link">
							<i class="dripicons-user-id"></i> <span> 미인증 직원 목록 </span>
					</a></li> -->
	<!-- 
					<li class="side-nav-item"><a href="personalBlackListPage" class="side-nav-link">
							<i class="dripicons-warning"></i> <span> 블랙리스트 </span>
					</a></li> -->
	
					<li class="side-nav-item"><a href="noticeList" class="side-nav-link">
							<i class="dripicons-clipboard"></i> <span> 공지사항 관리 </span>
					</a></li>
				
				</c:if>
				
			</ul>
			<!-- End Sidebar -->

			<div class="clearfix"></div>

		</div>
		<!-- Sidebar -left -->
	</div>
</body>
</html>