<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	
</script>
<body>
	<table>
		<tr>
			<td><img src="${bus_img}" alt="business profile image"></td>
		</tr>
		<tr>
			<td>${bus_name}(${bct_name})</td>
		</tr>
		<tr>
				<!-- Center modal -->
				<c:if test="${bsd_date}">
				
				</c:if>
				<a href="bookingForm?bus_no=${bus_no}&bct_code=${bct_code}&date=18/11/15" 
				class="btn btn-block btn-sm btn-success">예약하기</a>
			</td>
		</tr>
		<tr>
			<td>${rev_avg}<c:if test="${rev_avg == null}">아직 평점이 없습니다</c:if>
				(${rev_count})
				<div id="favorite">
					<button type="button" id="fav-button">
						<i class="mdi mdi-heart mr-1" id="fav-icon"></i> <span>즐겨찾기</span>
					</button>
					<!-- <button type="button" class="btn btn-danger" id="favorite-after">
						<i class="mdi mdi-heart mr-1" style="color: white"></i> <span
							style="color: white">즐겨찾기</span>
					</button> -->

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

	<div class="col-xl-6">
		<div class="card">
			<div class="card-body">
				<ul class="nav nav-pills bg-light nav-justified mb-3">
					<li class="nav-item"><a href="#businessBasicInfo" data-toggle="tab"
						aria-expanded="false" class="nav-link rounded-0 active show">
							<i class="mdi mdi-home-variant d-lg-none d-block mr-1"></i> <span
							class="d-none d-lg-block">기본정보</span>
					</a></li>
					<li class="nav-item"><a href="#businessGallery" data-toggle="tab"
						aria-expanded="true" class="nav-link rounded-0" onclick='businessGallery();'> <i
							class="mdi mdi-account-circle d-lg-none d-block mr-1"></i> <span
							class="d-none d-lg-block">갤러리</span>
					</a></li>
					<li class="nav-item"><a href="#businessDetailNoticeList" data-toggle="tab"
						aria-expanded="false" class="nav-link rounded-0" onclick='businessDetailNoticeList();'> <i
							class="mdi mdi-settings-outline d-lg-none d-block mr-1"></i> <span
							class="d-none d-lg-block">공지사항</span>
					</a></li>
				</ul>

				<div class="tab-content">
					<div class="tab-pane active show" id="businessBasicInfo">
						
						
					</div>
					<div class="tab-pane" id="businessGallery">
						
					</div>
					<div class="tab-pane" id="businessDetailNoticeList">
						
					</div>
				</div>

			</div>
			<!-- end card-body-->
		</div>
		<!-- end card-->
	</div>

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

	<script
		src="<c:url value="/resources/dist/assets/js/jquery/jquery-migrate.min.js"/>"></script>

</body>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=01f5de4fda83eccb6c481552ba87be63&libraries=services"></script>
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
$(document).ready(function(){
	$.ajax({
		type: 'post',
		url: 'businessBasicInfo?bus_no='+bus_no+'&bct_code='+bct_code,
		dataType: 'html',
		async: false,
		success: function(data) {
			console.log(data);
			$('#businessBasicInfo').html(data);
		},
		error: function(error) {
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
		type: 'post',
		url: 'businessGallery?bus_no='+bus_no+'&bct_code='+bct_code,
		dataType: 'html',
		success: function(data) {
			$('#businessGallery').html(data);
		},
		error: function(error) {
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
		type: 'post',
		url: 'businessDetailNoticeList?bus_no='+bus_no+'&bct_code='+bct_code,
		dataType: 'html',
		success: function(data) {
			$('#businessDetailNoticeList').html(data);
		},
		error: function(error) {
			console.log(error);
		}
	});
}
</script>

</html>