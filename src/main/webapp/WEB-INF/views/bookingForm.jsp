<!-- 서진 : 예약 페이지 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스펫츠 : 예약 페이지</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/dist/assets/images/logo-sm.png">

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<style>
.card d-block {
	max-width: 400px;;
	width: 400px;
	height: 40%;
	margin: 1.5px;
}

.row justify-content-center {
	margin: auto;
}
</style>

<!-- <style>
#petDiv {
	width: 20%;
	margin: 30px;
	margin-bottom: 20px;
	float: left;
	border: 1px solid #bcbcbc;
}
</style> -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
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
					<div class="row justify-content-center">
						<div class="col-7" style="margin-top: 30px; margin-bottom: 30px;">
							<h5 class="text-success">서비스 예약</h5>
							<hr />
							<br />
							<form action="booking" method="post">
								<!-- <div id="wrap"> -->
								<div class="card d-block"
									style='width: 50%; height: 50%; margin: 1.5px; margin: auto;'>
									<br />
									<p class='text-success' style='text-align: center'>선택한 반려동물</p>
									<hr />
									${petList} <br />
								</div>
								<br />
								<%-- <div id="petDiv">${petList}</div> --%>
								<div id="bkDiv">
									<div id="svcDiv">${svcList}</div>
									<div id="empDiv">${empList}</div>
									<div id="day">
										<h5 class='text-success'>날짜 선택</h5>
										<table id="dayTable">
										</table>
									</div>
									<div id="time">
										<h5 class='text-success'>시간 선택</h5>
										<table id="timeTable">
										</table>
									</div>
									<div id="comment">
										<h5 class='text-success'>코멘트</h5>
										<textarea id="comment" name="bk_cmt" row="10" cols="60"
											onkeyup="lengthCheck();"></textarea>
									</div>
									<input class='btn btn-success' type="submit" id="submit"
										value="예약하기" />
								</div>
							</form>
						</div>
					</div>
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
	<script src="/resources/dist/assets/js/app.min.js"></script>
	<!-- demo app 
	<script src="/resources/dist/assets/js/pages/demo.project-detail.js"></script>
	<script src="/resources/dist/assets/js/pages/demo.widgets.js"></script>-->
</body>
<!-- <script>
	function lengthCheck() {
		var form = document.getElementById('comment').value.length
	}
	function len_chk(){  
	  var frm = document.insertFrm.test; 
	     
	  if(frm.value.length > 4000){  
	       alert("글자수는 영문4000, 한글2000자로 제한됩니다.!");  
	       frm.value = frm.value.substring(0,4000);  
	       frm.focus();  
	  } 
</script> -->
<script>
	var noPet = "${noPet}";
	if(noPet != null && noPet != "") {
		alert("반려동물을 등록해주세요.");
		document.location.href = "./petList";
	}
</script>
<script>
	$('#dayTable').html("${dayList}");
	$('#day').hide();
	$('#time').hide();
	$('#comment').hide();
	$('#submit').hide();

	var emp = null;

	$('input[name=emp_no]').change(function() {
		emp = $(this).val();
		$('#day').show();
	});

	$('input[name=day]').change(function() {
		date = $(this).val();
		$.ajax({
			url : 'timeSelect',
			type : 'post',
			dataType : 'html',
			data : {
				'date' : date,
				'emp' : emp
			},
			success : function(data) {
				$('#timeTable').html(data);
				$('#time').show();
				$('#comment').show();
				$('#submit').show();
			},
			error : function(error) {
				console.log(error);
			}
		});
	});
</script>
</html>
