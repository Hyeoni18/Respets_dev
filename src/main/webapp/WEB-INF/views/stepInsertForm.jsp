<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 직원 등록 하기</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/dist/assets/images/logo-sm.png">

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
<style>
.custom-control {
	position: relative;
	display: inline;
	min-height: 1.3125rem;
	padding-left: 1.5rem;
}
</style>
</head>
<body>
	<div class="wrapper">
		<%@ include file="left-sidebar.jsp"%>
		<div class="content-page">
			<div class="content">
				<%@ include file="topbar-dashboard.jsp"%>
				<div class="container-fluid">
					<div class="row">
						<div class="col-12">
							<div class="page-title-box">
								<div class="page-title-right">
									<form class="form-inline"></form>
								</div>
								<h4 class="page-title">직원 등록 하기</h4>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-12">
							<div class="card">
								<div class="card-body">

									<form action="stepInsert" name="step" method="post"
										enctype="multipart/form-data">
										${type}
										<div class="row">
										<div class="col-lg-6">
										<div class="form-group mb-3">
											<label for="example-fileinput" style="margin-right: 15px;">자격증</label> <input type="file" name="emp_photo" />
										</div>
										<div class="form-group mb-3">
											<label for="simpleinput">이름</label> <input type="text"
												class="form-control" name="emp_name" />
										</div>
										<div class="form-group mb-3">
											<label for="simpleinput">직급</label> <input type="text"
												class="form-control" name="emp_pos" />
										</div>
										<div class="form-group mb-3">
											<label for="simpleinput">담당분야</label> <input type="text"
												class="form-control" name="emp_part" />
										</div>
										<div class="form-group mb-3">
											<label for="example-fileinput" style="margin-right: 15px;">프로필 사진</label>
											<input type="file" name="emp_license" />
										</div>
										</div>
										<div class="col-lg-6">
										<div id="changeCode">
											<div class="form-group mb-3">
												<label for="simpleinput">영업 오픈시간</label> <input type="time"
													class="form-control" name="work_o" id="am_open" step="1800">
											</div>
											<div class="form-group mb-3">
												<label for="simpleinput">영업 마감시간</label> <input type="time"
													class="form-control" name="work_c" id="pm_close" step="1800">
											</div>
											<div class="form-group mb-3">
												<label for="simpleinput">점심 시작시간</label> <input type="time"
													class="form-control" name=lunch_o id="lunch_open" step="1800">
											</div>
											<div class="form-group mb-3">
												<label for="simpleinput">점심 마감시간</label> <input type="time"
													class="form-control" name="lunch_c" id="lunch_close" step="1800">
											</div>
											<div class="form-group mb-3">
												<label for="simpleinput">고정휴무일</label><br /> ${holiday}
											</div>
										</div>
										</div>
										<div class="col-lg-12">
										*안내- 등록신청시 등록완료까지 인증시간이 소요됩니다.<br /> <br />
										<button class="btn btn-outline-secondary">등록 하기</button>
										</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<%@ include file="footer.html"%>
				</div>
			</div>
		</div>
	</div>
	<!-- App js -->
	<script src="/resources/dist/assets/js/app.min.js"></script>
</body>
<script>
	function chk(val) {
		var code = val;
		console.log(code + "mm");
		var url = "stepInsertForm?bct_code=" + code;
		Aj(url, "#changeCode");
		function Aj(url, position) {
			$.ajax({
				url : url,
				type : "post",
				dataType : "text",
				success : function(data) {
					$(position).html(data);
				},
				error : function(error) {
					console.log("error");
				}
			}); //ajax End
		}
	}
</script>
</html>