<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 비밀번호 수정</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- App favicon -->
<link rel="shortcut icon" href="resources/images/logo-sm.png">
<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<!-- Begin page -->
	<div class="wrapper">
		<!-- ========== Left Sidebar Start ========== -->
		<%@ include file="left-sidebar.jsp"%>
		<!-- Left Sidebar End -->
		<!-- ============================================================== -->
		<!-- Start Page Content here -->
		<!-- ============================================================== -->
		<div class="content-page">
			<div class="content">
				<!-- Topbar Start -->
				<%@ include file="topbar-dashboard.jsp"%>
				<!-- end Topbar -->
				<!-- Start Content-->
				<div class="container-fluid">
					<!-- start page title -->
					<div class="row">
						<div class="col-12">
							<div class="page-title-box">
								<div class="page-title-right">
									<form class="form-inline"></form>
								</div>
								<h4 class="page-title">비밀번호 수정</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->
					<div class="row">
						<div class="col-6">
							<div class="card">
								<div class="card-body">
									<form action="businessPwUpdate" method="post">
										<div class="row">
											<div class="col-lg-12">
												<div class="form-group mb-3">
													<label>현재 비밀번호 <span style="color: red">*</span></label>
													<input type="password" id="nowPw" onchange="nowPwCheck();" class="form-control" />
													<div id="result"></div>
												</div>
												<div class="form-group mb-3">
													<label>새로운 비밀번호 <span style="color: red">*</span></label>
													<input type="password" name="bus_pw" id="newPw" onchange="newPwcheck();" class="form-control" />
												</div>
												<div class="form-group mb-3">
													<label>새로운 비밀번호 확인<span style="color: red">*</span></label>
													<input type="password" id="newPwChk" onkeyup="pwCheck();" class="form-control" />
													<div id="pwCheck"></div>
												</div>
												<div>
													<input type="submit" class="btn btn-success" id="button" onclick="check();" value="수정 완료" disabled="disabled">
												</div>
											</div>
										</div>
										<!-- end row-->
									</form>
								</div>
								<!-- end card-body -->
							</div>
							<!-- end card -->
						</div>
						<!-- end col -->
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
	<script src="resources/dist/assets/js/app.min.js"></script>
</body>
<script>
	var newPw = null;
	
	function nowPwCheck() {
		var nowPw = $("#nowPw").val();
		$.ajax({
			url : "nowPwCheck",
			data : { "bus_pw" : nowPw },
			type : "post",
			success : function(result) {
				if (result == 1) {
					$('#result').html("비밀번호가 일치합니다.").css('color', 'blue');
					$('#button').removeAttr("disabled");
				} else {
					$("#result").html("비밀번호가 일치하지 않습니다.").css('color', 'red');
				}
			},
			error : function(error) {
				console.log(error);
			}
		});
	}
	
	function newPwcheck() {
		newPw = $("#newPw").val();
	}
	
	function pwCheck() {
		var newPwChk = $("#newPwChk").val();
		if (newPw === newPwChk) {
			$('#pwCheck').html("비밀번호가 일치합니다.").css('color', 'blue');
			$('#button').removeAttr("disabled");
		} else {
			$("#pwCheck").html("비밀번호가 일치하지 않습니다.").css('color', 'red');
		}
	}
	
	function check() {
		alert("비밀번호 수정이 성공했습니다.");
	}
</script>
</html>