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
								style="margin-top: 30px; margin-bottom: 30px;">
								<div class="card-body">

									<!-- project title-->
									<div class="project-title" style="text-align: center;">
										<div id="bus_img">
											<!-- business profile image -->
											<%-- <img src="${bus_img}" alt="business profile image"> --%>
											<img src="${bus_img}" alt="business profile image"
												style="width: 100%; height: 100%;">

										</div>
										<div class="badge badge-secondary"
											style="margin-top: 20px; margin-botton: 10px;">${bct_name}</div>
										<h3 class="mb-3">${bus_name}</h3>
									</div>

									<h5>전체 서비스</h5>
									<div class="row" style="margin-bottom: 15px;">
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

									<c:if test="${bsd_date == null}">

										<a href="bookingForm?bus_no=${bus_no}&bct_code=${bct_code}"
											class="btn btn-block btn-xs btn-success" id="bookingBtn"
											style="margin: 0 0 15px 0;">예약하기</a>
									</c:if>
									<c:if test="${bsd_date != null}">
										<a
											href="bookingForm?bus_no=${bus_no}&bct_code=${bct_code}&date=${bsd_date}"
											class="btn btn-block btn-xs btn-success" id="bookingBtn"
											style="margin: 0 0 15px 0;">예약하기</a>
									</c:if>

									<ul class="nav nav-pills bg-light nav-justified mb-3">
										<li class="nav-item"><a href="#businessBasicInfo"
											data-toggle="tab" aria-expanded="false"
											class="nav-link rounded-0 active show"> <i
												class="mdi mdi-information-outline d-lg-none d-block mr-1"></i>
												<span class="d-none d-lg-block">기본정보</span>
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







	<!-- App js -->
	<script src="resources/dist/assets/js/app.min.js"></script>

	<!-- demo app -->
	<script src="resources/dist/assets/js/pages/demo.dashboard.js"></script>
	<!-- <script src="resources/dist/assets/js/pages/demo.project-detail.js"></script> --> 
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

	$("#bookingBtn").click(function() {
		var no = "${no}";
		if (no == null || no == "") {
			var con = confirm("예약서비스는 로그인 후 이용이 가능합니다. 로그인 하시겠습니까?");
			con;
			if (con == false) {
				return false;
			} else {
				$("#bookingBtn").attr('href', 'loginForm');
				$("#bookingBtn").submit();
			}
		}else if(no.charAt(0)=='B' || no.charAt(0)=='1'){
			alert("예약서비스는 개인회원만 이용이 가능합니다.");
			return false;
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
					url : 'businessBasicInfo?bus_no=' + bus_no + '&bct_code='
							+ bct_code,
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
			url : 'businessDetailNoticeList?bus_no=' + bus_no + '&bct_code='
					+ bct_code,
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