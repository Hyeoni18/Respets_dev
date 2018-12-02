<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 기업상세 갤러리</title>
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
.photo {
	max-width: 30%;
	width: 30%;
	height: 50%;
	margin: 1.5px;
}
</style>
</head>
<body>
	<div class="row justify-content-center">
		<div class="col-12">
			<h5 class="text-success">상세 사진</h5>
			<!-- <br/> -->
			<div class="card-deck-wrapper">
				${gList}
				<!-- <div class="card-deck"></div> -->
				<!-- end card-deck-->
			</div>
			<!-- end card-deck-wrapper-->
		</div>
		<!-- end col-->
	</div>

	<!-- App js -->
	<script src="/resources/dist/assets/js/app.min.js"></script>

	<!-- third party js -->
	<script src="/resources/dist/assets/js/vendor/Chart.bundle.min.js"></script>

	<!-- demo app -->
	<script src="/resources/dist/assets/js/pages/demo.project-detail.js"></script>
	<!-- demo app -->
	<script src="resources/dist/assets/js/pages/demo.widgets.js"></script>
</body>
</html>