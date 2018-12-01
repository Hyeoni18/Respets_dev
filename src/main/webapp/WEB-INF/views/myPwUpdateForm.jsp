<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 비밀번호 수정</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	
</script>
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
									<form name="myPwUpdateForm" action="myPwUpdate" method="post">
										<div class="row">
											<div class="col-lg-12">
												<div class="form-group mb-3">
													<label>현재 비밀번호 <span style="color: red">*</span></label> <input
														type="password" id="nowPw" onchange="nowPwCheck();"
														class="form-control" />
												</div>
												<div class="form-group mb-3">
													<label>새로운 비밀번호 <span style="color: red">*</span></label> <input
														type="password" name="newPw" id="newPw"
														onkeyup="pw_check();" class="form-control" />
												</div>
												<div class="form-group mb-3">
													<label>새로운 비밀번호 확인<span style="color: red">*</span></label>
													<input type="password" id="newChkPw" class="form-control"
														onkeyup="pw_check();" />
													<div class="registrationFormAlert" id="same"></div>
												</div>
												<div>
													<input type="submit" class="btn btn-success" id="pwOk"
														onclick="forward(this)" value="수정 완료" disabled>
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
	function nowPwCheck() {
		var nowPw = $("#nowPw").val();
		$.ajax({
			url : "myPwCheck",
			data : {
				"now" : nowPw
			},
			type : "post",
			success : function(result) {
				console.log("성공");
				if (result == 1) {
					alert("비밀번호 확인 성공");
					$('#pwOk').removeAttr("disabled");
				} else {
					alert("비밀번호가 틀립니다.")
					$('#pwOk').attr('disabled', 'disabled');
				}
			},
			error : function(error) {
				console.log(error);
			}
		});
	}
	function pw_check() {
		var newPw = $("#newPw").val();
		var newChkPw = $('#newChkPw').val();
		if (newPw != newChkPw) {
			$("#same").html("비밀번호가 일치하지 않습니다.").css('color', 'red');
			$('#pwOk').attr('disabled', 'disabled');
		}
		if (newPw == '' || newChkPw == '') {
			$("#same").html("비밀번호를 입력해주세요.").css('color', 'red');
			$('#pwOk').attr('disabled', 'disabled');
		} else {
			$("#same").html("비밀번호가 일치합니다.").css('color', 'blue');
			$('#pwOk').removeAttr("disabled");
		}
	}
	function forward() {
		alert("비밀번호 수정 성공했습니다.");
	}
	${fail}
</script>
</html>