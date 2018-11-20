<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- Topbar Start -->
	<div class="navbar-custom">
		<ul class="list-unstyled topbar-right-menu float-right mb-0">

			<li class="dropdown notification-list"><a
				class="nav-link dropdown-toggle arrow-none" data-toggle="dropdown"
				href="#" role="button" aria-haspopup="false" aria-expanded="false">
					<i class="dripicons-bell noti-icon"></i> <span
					class="noti-icon-badge"></span>
			</a>
				<div
					class="dropdown-menu dropdown-menu-right dropdown-menu-animated dropdown-lg">

					<!-- item-->
					<div class="dropdown-item noti-title">
						<h5 class="m-0">
							<span class="float-right"> <a href="javascript: void(0);"
								class="text-dark"> <small>Clear All</small>
							</a>
							</span>알림
						</h5>
					</div>

					<div class="slimscroll" style="max-height: 230px;">
						<!-- item-->
						<a href="javascript:void(0);" class="dropdown-item notify-item">
							<div class="notify-icon bg-primary">
								<i class="mdi mdi-comment-account-outline"></i>
							</div>
							<p class="notify-details">
								Caleb Flakelar commented on Admin <small class="text-muted">1
									min ago</small>
							</p>
						</a>

						<!-- item-->
						<a href="javascript:void(0);" class="dropdown-item notify-item">
							<div class="notify-icon bg-info">
								<i class="mdi mdi-account-plus"></i>
							</div>
							<p class="notify-details">
								New user registered. <small class="text-muted">5 hours
									ago</small>
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
				data-toggle="dropdown" href="#" role="button" aria-haspopup="false"
				aria-expanded="false"> <span class="account-user-avatar">
						<img
						src="${pageContext.request.contextPath}/resources/dist/assets/images/users/avatar-1.jpg"
						alt="user-image" class="rounded-circle">
				</span> <span> <span class="account-user-name">회원이름</span> <span
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

		</ul>
		<button class="button-menu-mobile open-left disable-btn">
			<i class="mdi mdi-menu"></i>
		</button>
		<!-- <div class="app-search">
                            <form>
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Search...">
                                    <span class="mdi mdi-magnify"></span>
                                    <div class="input-group-append">
                                        <button class="btn btn-primary" type="submit">Search</button>
                                    </div>
                                </div>
                            </form>
                        </div> -->
	</div>
	<!-- end Topbar -->
</body>
</html>