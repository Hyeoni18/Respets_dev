<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 직원 정보 수정</title>
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
								<h4 class="page-title">직원 정보 수정</h4>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-12">
							<div class="card">
								<div class="card-body">

									<form name="stepDetail" method="post"
										enctype="multipart/form-data">
										<div class="row">
										${text}
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%@ include file="footer.html"%>
			</div>
		</div>
	</div>
</body>
<script>
	function but(val) {
		var frm = document.stepDetail;
		if (val.value == '수정완료') {
			frm.action = "stepUpdate";
		} else if (val.value == '삭제') {
			frm.action = "stepDelete";
		}
		frm.submit();
	}
</script>
</html>