<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: ${bus_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon" href="resources/images/logo-sm.png">

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
				<jsp:include page="topbar-landing.jsp">
					<jsp:param name="no" value="${no}" />
				</jsp:include>
				<!-- end Topbar -->


				<!-- Start Content-->
				<div class="container-fluid">

					<!-- start page title -->
					<div class="row">
						<div class="col-md-2"></div>
						<div class="col-md-8">
							<!-- project card -->
							<div class="card d-block"
								style="margin-top: 50px; margin-bottom: 50px;">
								<div class="card-body">

									<!-- project title-->
									<div class="project-title" style="text-align: center;">
										<div id="bus_img">
											<!-- business profile image -->
											<%-- <img src="${bus_img}" alt="business profile image"> --%>
											<img src="${bus_img}" alt="business profile image" style="width: 100%; height: 300px;">
											
										</div>
										<div class="badge badge-secondary"
											style="margin-top:20px;margin-botton: 10px;">${bct_name}</div>
										<h3 class="mb-3">${bus_name}</h3>
									</div>

									<h5>전체 서비스</h5>
									<div class="row" style="margin-bottom: 20px;">
										<div class="col-6">
											<c:forEach var="list" items="${serviceList}"
												varStatus="status">
												<a class="btn btn-xs btn-outline-success"
													href="businessDetailPage?bus_no=${bus_no}&bct_code=${list['BCT_CODE']}">
													<c:if test="${'병원' eq list['BCT_NAME']}">진료</c:if> <c:if
														test="${'병원' ne list['BCT_NAME']}">${list['BCT_NAME']}</c:if>
												</a>
											</c:forEach>

										</div>
										<div class="col-6">
											<div id="favorite" style="text-align: right;">
												<button type="button" id="fav-button">
													<i class="mdi mdi-heart mr-1" id="fav-icon"></i> <span>즐겨찾기</span>
												</button>
											</div>

										</div>

									</div>

									<ul class="nav nav-pills bg-light nav-justified mb-3">
										<li class="nav-item"><a href="#businessBasicInfo"
											data-toggle="tab" aria-expanded="false"
											class="nav-link rounded-0 active show"> <i
												class="mdi mdi-information-outline d-lg-none d-block mr-1"></i> <span
												class="d-none d-lg-block">기본정보</span>
										</a></li>
										<li class="nav-item"><a href="#businessGallery"
											data-toggle="tab" aria-expanded="true"
											class="nav-link rounded-0" onclick='businessGallery();'>
												<i class="mdi mdi-image-multiple d-lg-none d-block mr-1"></i>
												<span class="d-none d-lg-block">갤러리</span>
										</a></li>
										<li class="nav-item"><a href="#businessDetailNoticeList"
											data-toggle="tab" aria-expanded="false"
											class="nav-link rounded-0"
											onclick='businessDetailNoticeList();'> <i
												class="mdi mdi-clipboard-outline d-lg-none d-block mr-1"></i>
												<span class="d-none d-lg-block">공지사항</span>
										</a></li>
									</ul>

									<div class="tab-content">
										<div class="tab-pane active show" id="businessBasicInfo">


										</div>
										<div class="tab-pane" id="businessGallery"></div>
										<div class="tab-pane" id="businessDetailNoticeList"></div>
									</div>

								</div>
								<!-- end card-body-->

							</div>
							<!-- end card-->

							<!-- <div class="card">
								<div class="card-body">
									<h4 class="mt-0 mb-3">Comments (258)</h4>

									<textarea class="form-control form-control-light mb-2"
										placeholder="Write message" id="example-textarea" rows="3"></textarea>
									<div class="text-right">
										<div class="btn-group mb-2">
											<button type="button"
												class="btn btn-link btn-sm text-muted font-18">
												<i class="dripicons-paperclip"></i>
											</button>
										</div>
										<div class="btn-group mb-2 ml-2">
											<button type="button" class="btn btn-primary btn-sm">Submit</button>
										</div>
									</div>

									<div class="media mt-2">
										<img class="mr-3 avatar-sm rounded-circle"
											src="assets/images/users/avatar-3.jpg"
											alt="Generic placeholder image">
										<div class="media-body">
											<h5 class="mt-0">Jeremy Tomlinson</h5>
											Cras sit amet nibh libero, in gravida nulla. Nulla vel metus
											scelerisque ante sollicitudin. Cras purus odio, vestibulum in
											vulputate at, tempus viverra turpis. Fusce condimentum nunc
											ac nisi vulputate fringilla. Donec lacinia congue felis in
											faucibus.

											<div class="media mt-3">
												<a class="pr-3" href="#"> <img
													src="assets/images/users/avatar-4.jpg"
													class="avatar-sm rounded-circle"
													alt="Generic placeholder image">
												</a>
												<div class="media-body">
													<h5 class="mt-0">Kathleen Thomas</h5>
													Cras sit amet nibh libero, in gravida nulla. Nulla vel
													metus scelerisque ante sollicitudin. Cras purus odio,
													vestibulum in vulputate at, tempus viverra turpis. Fusce
													condimentum nunc ac nisi vulputate fringilla. Donec lacinia
													congue felis in faucibus.
												</div>
											</div>
										</div>
									</div>

									<div class="text-center mt-2">
										<a href="javascript:void(0);" class="text-danger">Load
											more </a>
									</div>
								</div>
								end card-body
							</div>
							end card -->
						</div>
						<div class="col-md-2"></div>
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



	<%-- 	<table>
		<tr>
			<td><img src="${bus_img}" alt="business profile image"></td>
		</tr>
		<tr>
			<td>${bus_name}(${bct_name})</td>
		</tr>
		<tr>
<<<<<<< HEAD
			<!-- Center modal -->
			<c:if test="${bsd_date==null}">
				<a href="bookingForm?bus_no=${bus_no}&bct_code=${bct_code}"
					class="btn btn-block btn-sm btn-success">예약하기</a>
			</c:if>
			<c:if test="${bsd_date!=null}">
				<a
					href="bookingForm?bus_no=${bus_no}&bct_code=${bct_code}&date=${bsd_date}"
					class="btn btn-block btn-sm btn-success">예약하기</a>
			</c:if>

			</td>
		</tr>
		<tr>
			<td>
				${rev_avg} <c:if test="${rev_avg == null}">아직 평점이 없습니다</c:if>
=======
			<td>
				<!-- Center modal -->
				<c:if test="${bsd_date}">
				
				</c:if>
				<a href="bookingForm?bus_no=${bus_no}&bct_code=${bct_code}&date=18/11/15" 
				class="btn btn-block btn-sm btn-success">예약하기</a>
			</td>
		</tr>
		<tr>
			<td>${rev_avg} <c:if test="${rev_avg == null}">아직 평점이 없습니다</c:if>
>>>>>>> origin/master
				(${rev_count})
				<div id="favorite">
					<button type="button" id="fav-button">
						<i class="mdi mdi-heart mr-1" id="fav-icon"></i> <span>즐겨찾기</span>
					</button>
				</div>
			</td>
		</tr>
		<tr>
			<td><c:forEach var="list" items="${serviceList}"
					varStatus="status">
					<a class="btn btn-lg btn-xs btn-success"
						href="businessDetailPage?bus_no=${bus_no}&bct_code=${list['BCT_CODE']}">${list['BCT_NAME']}</a>
				</c:forEach></td>
		</tr>
	</table>

<<<<<<< HEAD
	<div class="col-xl-6">
		<div class="card">
			<div class="card-body">
				<ul class="nav nav-pills bg-light nav-justified mb-3">
					<li class="nav-item"><a href="#businessBasicInfo"
						data-toggle="tab" aria-expanded="false"
						class="nav-link rounded-0 active show"> <i
							class="mdi mdi-home-variant d-lg-none d-block mr-1"></i> <span
							class="d-none d-lg-block">기본정보</span>
					</a></li>
					<li class="nav-item"><a href="#businessGallery"
						data-toggle="tab" aria-expanded="true" class="nav-link rounded-0"
						onclick='businessGallery();'> <i
							class="mdi mdi-account-circle d-lg-none d-block mr-1"></i> <span
							class="d-none d-lg-block">갤러리</span>
					</a></li>
					<li class="nav-item"><a href="#businessDetailNoticeList"
						data-toggle="tab" aria-expanded="false" class="nav-link rounded-0"
						onclick='businessDetailNoticeList();'> <i
							class="mdi mdi-settings-outline d-lg-none d-block mr-1"></i> <span
							class="d-none d-lg-block">공지사항</span>
					</a></li>
				</ul>

				<div class="tab-content">
					<div class="tab-pane active show" id="businessBasicInfo"></div>
					<div class="tab-pane" id="businessGallery"></div>
					<div class="tab-pane" id="businessDetailNoticeList"></div>
				</div>

			</div>
			<!-- end card-body-->
		</div>
		<!-- end card-->
	</div> --%>

	<!-- Center modal content 
	
	<a class="btn btn-block btn-sm btn-success"
					data-toggle="modal" data-target="#centermodal">예약하기</a>
	
	<div class="modal fade" id="centermodal" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content" style="height: 500px;">
				<div class="modal-header">
					<h4 class="modal-title" id="myCenterModalLabel">예약할 동물을 선택해주세요</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
				</div>
				<div class="modal-body" style="text-align:center;">
					<div class="form-control" style="position: relative; width: auto; height: 350px;">
						<div class="slimscroll"	style="max-height: 350px; width: auto; height: 350px;">
							동물리스트 부분
						</div>
						<div class="slimScrollBar"
							style="background: rgb(158, 165, 171); width: 8px; position: absolute; top: 0px; opacity: 0.4; display: none; border-radius: 7px; z-index: 99; right: 1px; height: 115.576px;"></div>
						<div class="slimScrollRail"
							style="width: 8px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: rgb(51, 51, 51); opacity: 0.2; z-index: 90; right: 1px;"></div>
					</div>
					<button type="button" class="btn btn-success"
					style="margin-top:20px;">선택완료</button>
				</div>
			</div>
		</div>
	</div>
	-->


	<!-- App js -->
	<script src="resources/dist/assets/js/app.min.js"></script>

	<!-- demo app -->
	<script src="/resources/dist/assets/js/pages/demo.project-detail.js"></script>
	<!-- end demo js-->

</body>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=01f5de4fda83eccb6c481552ba87be63&libraries=services"></script>
<script>
	$(document).ready(function() {
		var no = "${no}";
		var code = "${code}";
		console.log("no = " + no);
		console.log("code = " + code);
		if (code == 'P') {
			var fav = "${favorite}";
			if (fav > 0) {
				$("#fav-button").attr('class', 'btn btn-danger');
				//$("#fav-icon").attr('style', 'color: white');
			} else {
				$("#fav-button").attr('class', 'btn btn-light');
			}
		} else {
			$("#favorite").hide(); //즐겨찾기 숨기기
		}
	});

	$("#fav-button").click(function() {
		var no = "${no}";
		var bus_no = "${bus_no}";
		var cName = $(this).attr('class');
		//alert("cName="+cName);		
		if (cName == "btn btn-light") {
			$.ajax({
				type : 'post',
				url : 'favoriteChange',
				data : {
					per_no : no,
					bus_no : bus_no,
					action : 'insert'
				},
				dataType : 'text',
				success : function(data) {
					if (data > 0) {
						$("#fav-button").attr('class', 'btn btn-danger');
						//$("#fav-icon").attr('style', 'color: white');
						alert("즐겨찾기로 등록되었습니다");
					} else {
						alert("즐겨찾기 등록을 실패했습니다");
					}
				},
				error : function(error) {
					alert('error');
					consol.log(error);
				}
			});//ajax End
		}
		if (cName == "btn btn-danger") {
			$.ajax({
				type : 'post',
				url : 'favoriteChange',
				data : {
					per_no : no,
					bus_no : bus_no,
					action : 'delete'
				},
				dataType : 'text',
				success : function(data) {
					if (data > 0) {
						$("#fav-button").attr('class', 'btn btn-light');
						alert("즐겨찾기가 취소되었습니다");
					} else {
						alert("즐겨찾기 취소를 실패했습니다");
					}
				},
				error : function(error) {
					alert('error');
					consol.log(error);
				}
			});//ajax End
		}

	});
</script>


<script>
	var bus_no = '${bus_no}';
	var bct_code = '${bct_code}';
	console.log(bct_code);
	$(document).ready(
			function() {
				$.ajax({
					type : 'post',
					url : 'businessBasicInfo?bus_no=' + bus_no + '&bct_code=' + bct_code,
					dataType : 'html',
					async : false,
					success : function(data) {
						console.log(data);
						$('#businessBasicInfo').html(data);
					},
					error : function(error) {
						console.log(error);
					}
				});
			});
</script>

<script>
	var bus_no = '${bus_no}';
	var bct_code = '${bct_code}';
	function businessGallery() {
		$.ajax({
			type : 'post',
			url : 'businessGallery?bus_no=' + bus_no + '&bct_code=' + bct_code,
			dataType : 'html',
			success : function(data) {
				$('#businessGallery').html(data);
			},
			error : function(error) {
				console.log(error);
			}
		});
	}
</script>
<script>
	var bus_no = '${bus_no}';
	var bct_code = '${bct_code}';
	function businessDetailNoticeList() {
		$.ajax({
			type : 'post',
			url : 'businessDetailNoticeList?bus_no=' + bus_no + '&bct_code=' + bct_code,
			dataType : 'html',
			success : function(data) {
				$('#businessDetailNoticeList').html(data);
			},
			error : function(error) {
				console.log(error);
			}
		});
	}
</script>

</html>