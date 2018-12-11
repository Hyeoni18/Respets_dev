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
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/dist/assets/images/logo-sm.png">

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />

</head>
</head>
<body>
	<div class="row">
		<div class="col-12">
			<div class="card">
				<div class="card-body">
					<div class="table-responsive-sm">
						<table class="table table-striped table-centered mb-0" style='text-align:center;'>
							<thead>
								<tr>
									<th>게시글 번호</th>
									<th>카테고리</th>
									<th>글제목</th>
									<th>작성일</th>
								</tr>
							</thead>
							${nList}
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- App js -->
	<script src="resources/dist/assets/js/app.min.js"></script>

	<!-- third party js -->
	<script src="resources/dist/assets/js/vendor/jquery.dataTables.js"></script>
	<script src="resources/dist/assets/js/vendor/dataTables.bootstrap4.js"></script>
	<script
		src="resources/dist/assets/js/vendor/dataTables.responsive.min.js"></script>
	<script
		src="resources/dist/assets/js/vendor/responsive.bootstrap4.min.js"></script>
	<!-- third party js ends -->

	<!-- demo app -->
	<script src="resources/dist/assets/js/pages/demo.dashboard.js"></script>
	<!-- end demo js-->
</body>
</html>