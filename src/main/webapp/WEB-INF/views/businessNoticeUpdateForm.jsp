<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 기업 공지사항 수정</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon" href="resources/images/logo-sm.png">

<!-- third party css -->
<!-- 게시판 -->
<link href="resources/dist/assets/css/vendor/summernote-bs4.css"
	rel="stylesheet" type="text/css">
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
<body>
	<!-- Begin page -->
	<div class="wrapper">

		<!-- Left Sidebar Start -->
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

				<div class="container-fluid">

					<!-- start page title -->
					<div class="row">
						<div class="col-12">
							<div class="page-title-box">
								<h4 class="page-title">게시글 수정</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->
					<div class="row">
						<div class="col-12">
							<div class="card">
								<div class="card-body">
									<form action="businessNoticeUpdate"
										name="businessNoticeUpdateForm" method="get">
										<div class="form-group mb-3">
											<label for="example-select">업종</label> <select
												class="form-control" name="bct_code" id="bct_code">
												<option value="">선택</option>
												<option value="M">병원</option>
												<option value="B">미용</option>
												<option value="H">호텔</option>
											</select>
										</div>
										<div class="form-group mb-3">
											<label for="example-select">카테고리</label> <select
												class="form-control" name="bbc_no" id="bbc_no">
												<option value="">선택</option>
												<option value="1">공지사항</option>
												<option value="2">이벤트</option>
											</select>
										</div>
										<div class="form-group">
											<label for="simpleinput">제목</label> <input type="text"
												class="form-control" name="bbo_title" id="bbo_title"
												placeholder="게시글 제목을 입력하세요">
										</div>
										<div class="form-group">
											<label for="simpleinput">내용</label>
											<textarea data-toggle="maxlength" class="form-control"
												maxlength="1000" rows="10" name="bbo_ctt" id="bbo_ctt"
												placeholder="게시글 내용을 입력하세요"></textarea>
										</div>
										<div style="text-align: right">
											<button id="businessNoticeUpdateSubmit"
												class="btn btn-success btn-sm mt-2">
												<i class="mdi mdi-content-save-outline mr-1"></i> Save
											</button>
										</div>
										<input type="hidden" name="bbo_no" id="bbo_no" value="0" />
									</form>
								</div>
							</div>
							<!-- end card-->
						</div>
						<!-- end col -->
					</div>
					<!-- end row -->

				</div>
				<!-- content -->

				<!-- Footer Start -->

				<!-- end Footer -->

				<!-- ============================================================== -->
				<!-- End Page content -->
				<!-- ============================================================== -->


			</div>
			<!-- END wrapper -->

			<!-- alert -->
			${Fail}


			<!-- App js -->
			<script src="resources/dist/assets/js/app.min.js"></script>

			<!-- third party js -->
			<!-- 게시판 -->
			<script src="resources/dist/assets/js/vendor/summernote-bs4.min.js"></script>
			<!-- third party js ends -->

			<!-- demo app -->
			<script src="resources/dist/assets/js/pages/demo.dashboard.js"></script>
			<!-- end demo js-->
</body>
<script>
	$(document).ready(function() {
		var jsonData = ${result};
		console.log(jsonData.BBO_TITLE);
		$("#bbo_title").attr("value", jsonData.BBO_TITLE);
		$('#bbo_no').attr('value', jsonData.BBO_NO);
		$('#bbo_ctt').html(jsonData.BBO_CTT);

		//불러온 값에 selected속성 부여하기
		$("#bct_code option").each(function() {
			if ($(this).val() == jsonData.BCT_CODE)
				$(this).attr("selected", "selected");
		})
		$("#bbc_no option").each(function() {
			if ($(this).val() == jsonData.BBC_NO)
				$(this).attr("selected", "selected");
		})
	});
	$("#businessNoticeUpdateSubmit").click(function() {
		console.log(this.value + " 클릭함");
		var frm = document.businessNoticeUpdateForm;
		var length = frm.length - 1;
		for (var i = 0; i < length; i++) {
			if (frm[i].name == "bct_code") {
				if (frm[i].value == "" || frm[i].value == null) {
					alert("업종이 입력되지 않았습니다");
					frm[i].focus();
					return false;
				}
			} else if (frm[i].name == "bbc_no") {
				if (frm[i].value == "" || frm[i].value == null) {
					alert("카테고리가 입력되지 않았습니다");
					frm[i].focus();
					return false;
				}
			} else if (frm[i].name == "bbo_title") {
				if (frm[i].value == "" || frm[i].value == null) {
					alert("제목이 입력되지 않았습니다");
					frm[i].focus();
					return false;
				}
			} else if (frm[i].name == "bbo_ctt") {
				if (frm[i].value == "" || frm[i].value == null) {
					alert("내용이 입력되지 않았습니다");
					frm[i].focus();
					return false;
				}
			}
		}
		frm.submit();
	});
</script>
</html>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<form id="businessNoticeUpdateForm" action="businessNoticeUpdate">
<input type="hidden" name="bbo_no" id="BBO_NO" value="0"/>
<select name="bct_code" >
	<option id="m" value="M">병원</option>
	<option id="b" value="B">미용</option>
	<option id="h" value="H">호텔</option>
</select>

<select name="bbc_no" >
	<option id="notice" value="1">공지사항</option>
	<option id="event" value="2">이벤트</option>
</select> <br/>
<input type="text" name="bbo_title" id="BBO_TITLE" /> <br/>
<textarea rows="20" name="bbo_ctt" id="BBO_CTT" ></textarea> <br/>
<button>수정</button>
</form>
</body>
<script>
	${alert}
	var jsonData = ${result};
	console.log(jsonData.BBO_TITLE);
	
	$('#BBO_CTT').html(jsonData.BBO_CTT);
	$('#BBO_TITLE').attr('value', jsonData.BBO_TITLE);
	$('#BBO_NO').attr('value', jsonData.BBO_NO);
	console.log(jsonData.BBO_NO);
	
	if(jsonData.BCT_CODE=='M') {
		$('#m').attr('selected', 'selected');
	}else if(jsonData.BCT_CODE=='B') {
		$('#b').attr('selected', 'selected');
	}else {
		$('#h').attr('selected', 'selected');
	}
	
	if(jsonData.BBC_NO==1) {
		$('#notice').attr('selected', 'selected');
	}else {
		$('#event').attr('selected', 'selected');
	}
</script>
</html> -->