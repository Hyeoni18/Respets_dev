<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>topbar-dashboard</title>
</head>
<body>
	<div class="navbar-custom">
		<ul class="list-unstyled topbar-right-menu float-right mb-0">

			<li class="dropdown notification-list"><a
				class="nav-link dropdown-toggle nav-user arrow-none mr-0"
				data-toggle="dropdown" href="#" role="button" aria-haspopup="false"
				aria-expanded="false"> <span class="account-user-avatar">
				<c:if test="${fn:substring(no,0,1) == 'P' || fn:substring(no,0,1) == 'B'}">
				<img src="${loc}${photo}" alt="user-image" class="rounded-circle">
				</c:if>
				<c:if test="${fn:substring(no,0,1) == '1'}">
				<img src="resources/images/defaultProfile/user.png" alt="user-image" class="rounded-circle">
				</c:if>
						
				</span> <span> <span class="account-user-name">${name}
							</span> <span class="account-position"> <c:if
										test="${fn:substring(no,0,1) == 'P'}">개인회원</c:if> <c:if
										test="${fn:substring(no,0,1) == 'B'}">기업회원</c:if> <c:if
										test="${fn:substring(no,0,1) == '1'}">관리자</c:if>
							</span>
				</span>
			</a>
				<div
					class="dropdown-menu dropdown-menu-right dropdown-menu-animated profile-dropdown ">
					<!-- item-->
					<div class=" dropdown-header noti-title">
						<h6 class="text-overflow m-0">Welcome Respets!</h6>
					</div>

					<!-- item-->
					<a href="./myPage" class="dropdown-item notify-item">
						<i class="mdi mdi-account-circle"></i> <span>마이페이지</span>
					</a>

					<c:if test="${fn:substring(no,0,1) == 'P'}">
						<!-- item-->
						<a href="./personalCalendar" class="dropdown-item notify-item">
							<i class="mdi mdi-calendar"></i> <span>캘린더</span>
						</a>
					</c:if>

					<form action="logout" id="logoutFrm" method="post">
						<!-- item-->
						<a href="javascript:logoutFrm()" class="dropdown-item notify-item">
							<i class="mdi mdi-logout"></i> <span>로그아웃</span>
						</a>
					</form>


				</div></li>

		</ul>
		<button class="button-menu-mobile open-left disable-btn">
			<i class="mdi mdi-menu"></i>
		</button>
	</div>
</body>
<script>
function logoutFrm() {
	$('#logoutFrm').submit();
}
</script>
</html>