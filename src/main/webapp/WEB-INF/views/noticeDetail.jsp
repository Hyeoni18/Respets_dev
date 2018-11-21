<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>관리자 공지사항</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon" href="resources/images/logo-sm.png">

<!-- third party css -->
<!-- 게시판 -->
<link href="resources/dist/assets/css/vendor/summernote-bs4.css"
	rel="stylesheet" type="text/css">
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
<body>
	<!-- Begin page -->
	<div class="wrapper">

		<!-- ========== Left Sidebar Start ========== -->

		<!-- Left Sidebar End -->

		<!-- ============================================================== -->
		<!-- Start Page Content here -->
		<!-- ============================================================== -->

		<div class="container-fluid">
			<!-- start page title -->
			<div class="row">
				<div class="col-12">
					<div class="page-title-box">
						<h4 class="page-title">게시글 내용</h4>
					</div>
				</div>
			</div>
			<!-- end page title -->

			<div class="row">
				<div class="col-12">
				<div class="card d-block">
                                    <div class="card-body">
                                        <div class="dropdown card-widgets">
                                            <a href="javascript:void(0);" class="dropdown-toggle arrow-none" data-toggle="dropdown" aria-expanded="false">
                                                <i class="dripicons-dots-3"></i>
                                            </a>
                                            <div class="dropdown-menu dropdown-menu-right" x-placement="bottom-end" style="position: absolute; will-change: transform; top: 0px; left: 0px; transform: translate3d(-142px, 20px, 0px);">
                                                <!-- item-->
                                                <a href="javascript:void(0);" class="dropdown-item"><i class="mdi mdi-pencil mr-1"></i>Edit</a>
                                                <!-- item-->
                                                <a href="javascript:void(0);" class="dropdown-item"><i class="mdi mdi-delete mr-1"></i>Delete</a>
                                                <!-- item-->
                                                <a href="javascript:void(0);" class="dropdown-item"><i class="mdi mdi-email-outline mr-1"></i>Invite</a>
                                                <!-- item-->
                                                <a href="javascript:void(0);" class="dropdown-item"><i class="mdi mdi-exit-to-app mr-1"></i>Leave</a>
                                            </div>
                                        </div>
                                        <!-- project title-->
                                        <h3 class="mt-0">
                                            App design and development
                                        </h3>
                                        <div class="badge badge-secondary mb-3">Ongoing</div>

                                        <h5>Project Overview:</h5>

                                        <p class="text-muted mb-2">
                                            With supporting text below as a natural lead-in to additional contenposuere erat a ante. Voluptates, illo, iste itaque voluptas
                                            corrupti ratione reprehenderit magni similique? Tempore, quos delectus asperiores libero voluptas quod perferendis! Voluptate,
                                            quod illo rerum? Lorem ipsum dolor sit amet.
                                        </p>

                                        <p class="text-muted mb-4">
                                            Voluptates, illo, iste itaque voluptas corrupti ratione reprehenderit magni similique? Tempore, quos delectus asperiores
                                            libero voluptas quod perferendis! Voluptate, quod illo rerum? Lorem ipsum dolor sit amet. With supporting text below
                                            as a natural lead-in to additional contenposuere erat a ante.
                                        </p>

                                        <div class="row">
                                            <div class="col-md-4">
                                                <div class="mb-4">
                                                    <h5>Start Date</h5>
                                                    <p>17 March 2018 <small class="text-muted">1:00 PM</small></p>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="mb-4">
                                                    <h5>End Date</h5>
                                                    <p>22 December 2018 <small class="text-muted">1:00 PM</small></p>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="mb-4">
                                                    <h5>Budget</h5>
                                                    <p>$15,800</p>
                                                </div>
                                            </div>
                                        </div>

                                        <div>
                                            <h5>Team Members:</h5>
                                            <a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" title="" data-original-title="Mat Helme" class="d-inline-block">
                                                <img src="assets/images/users/avatar-6.jpg" class="rounded-circle img-thumbnail avatar-sm" alt="friend">
                                            </a>
    
                                            <a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" title="" data-original-title="Michael Zenaty" class="d-inline-block">
                                                <img src="assets/images/users/avatar-7.jpg" class="rounded-circle img-thumbnail avatar-sm" alt="friend">
                                            </a>
    
                                            <a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" title="" data-original-title="James Anderson" class="d-inline-block">
                                                <img src="assets/images/users/avatar-8.jpg" class="rounded-circle img-thumbnail avatar-sm" alt="friend">
                                            </a>

                                            <a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" title="" data-original-title="Mat Helme" class="d-inline-block">
                                                <img src="assets/images/users/avatar-4.jpg" class="rounded-circle img-thumbnail avatar-sm" alt="friend">
                                            </a>
    
                                            <a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" title="" data-original-title="Michael Zenaty" class="d-inline-block">
                                                <img src="assets/images/users/avatar-5.jpg" class="rounded-circle img-thumbnail avatar-sm" alt="friend">
                                            </a>
    
                                            <a href="javascript:void(0);" data-toggle="tooltip" data-placement="top" title="" data-original-title="James Anderson" class="d-inline-block">
                                                <img src="assets/images/users/avatar-3.jpg" class="rounded-circle img-thumbnail avatar-sm" alt="friend">
                                            </a>
                                        </div>

                                    </div> <!-- end card-body-->
                                    
                                </div>
					<div class="card">
						<div class="card-body">
							<form name="noticeDetail" method="get">
								<%-- <h4 class="header-title">Description list alignment</h4>
								<p class="text-muted">
									Align terms and descriptions horizontally by using our grid
									system’s predefined classes (or semantic mixins). For longer
									terms, you can optionally add a
									<code class="highlighter-rouge">.text-truncate</code>
									class to truncate the text with an ellipsis.
								</p> --%>

								<dl class="row">
									<dt class="col-sm-3">구분</dt>
									<dd class="col-sm-9">
										<p class="text-muted">
											<c:if test="${1 == abo.abc_no}">개인</c:if>
											<c:if test="${2 == abo.abc_no}">기업</c:if>
										</p>
									</dd>
									<dt class="col-sm-3">제목</dt>
									<dd class="col-sm-9">
										<p class="text-muted">${abo.abo_title}</p>
									</dd>

									<dt class="col-sm-3">내용</dt>
									<dd class="col-sm-9">
										<p class="text-muted">${abo.abo_ctt}</p>
									</dd>
								</dl>
								<div style="text-align:right;">
									<input type="button" class="btn btn-warning"
									onclick="forward(this)" value="목록" />
									<input type="button" class="btn btn-warning"
										onclick="forward(this)" value="수정" />
									<!-- Warning Alert modal -->
									<input type="button" class="btn btn-warning"
										data-toggle="modal" data-target="#warning-alert-modal"
										value="삭제" />

									<!-- Warning Alert Modal -->
									<div id="warning-alert-modal" class="modal fade" tabindex="-1"
										role="dialog" aria-hidden="true">
										<div class="modal-dialog modal-sm">
											<div class="modal-content">
												<div class="modal-body p-4">
													<div class="text-center">
														<i class="dripicons-warning h1 text-warning"></i>
														<h4 class="mt-2">Incorrect Information</h4>
														<p class="mt-3">게시물을 정말 삭제하시겠습니까?</p>
														<input type="button" class="btn btn-warning my-2"
															data-dismiss="modal" value="취소" /> <input type="button"
															class="btn btn-warning my-2" onclick="forward(this)"
															value="삭제" />
													</div>
												</div>
											</div>
											<!-- /.modal-content -->
										</div>
										<!-- /.modal-dialog -->
									</div>
									<!-- /.modal -->
									<input type="hidden" value="${abo.abo_no}" name="abo_no" />
								</div>

							</form>
						</div>
					</div>
					<!-- end card-->
				</div>
				<!-- end col -->
			</div>
			<!-- end row -->

		</div>
		<!-- content -->

		<!-- Footer Start -->

		<!-- end Footer -->

		<!-- ============================================================== -->
		<!-- End Page content -->
		<!-- ============================================================== -->


	</div>
	<!-- END wrapper -->

	<!-- alert -->
	${Fail}


	<!-- App js -->
	<script src="<c:url value="resources/dist/assets/js/app.min.js"/>"></script>

	<!-- third party js -->
	<!-- 게시판 -->
	<script src="resources/dist/assets/js/vendor/summernote-bs4.min.js"></script>

	<script
		src="<c:url value="resources/dist/assets/js/vendor/Chart.bundle.min.js"/>"></script>
	<script
		src="<c:url value="resources/dist/assets/js/vendor/jquery-jvectormap-1.2.2.min.js"/>"></script>
	<script
		src="<c:url value="resources/dist/assets/js/vendor/jquery-jvectormap-world-mill-en.js"/>"></script>
	<!-- third party js ends -->

	<!-- demo app -->
	<script
		src="<c:url value="resources/dist/assets/js/pages/demo.dashboard.js"/>"></script>
	<!-- end demo js-->
</body>
<script>
function forward(button) {
	console.log("button=" + button.value);
	var frm = document.noticeDetail;
	if (button.value == '목록') {
		frm.action = "noticeList";
	}
	if (button.value == '수정') {
		frm.action = "noticeUpdateForm?abo_no=${abo.abo_no}";
	}
	if (button.value == '삭제') {
		frm.action = "noticeDelete?abo_no=${abo.abo_no}";
	}
	frm.submit();
}
</script>
</html>