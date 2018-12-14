<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 전체 예약 목록</title>
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
<body onload="butListPaging(1,'','')">
	<div class="wrapper">
		<%@ include file="left-sidebar.jsp"%>
		<div class="content-page">
			<%@ include file="topbar-dashboard.jsp"%>
			<div class="container-fluid">

				<!-- start page title -->
				<div class="row">
					<div class="col-12">
						<div class="page-title-box">
							<h4 class="page-title">전체 예약 목록</h4>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<div class="card">
							<div class="card-body">
								<div class="row mb-2">
									<div class="col-lg-6">

										<form name="businessBookingListPage" class="form-inline">
											<div class="form-group mb-3">
												<label for="status-select" class="mr-2"> <!-- 카테고리&nbsp; -->
													<select class="custom-select" name="select" id="select">
														<option>전체</option> ${bctList}
												</select>
												</label>
											</div>
											<div class="form-group mb-3">
												<label for="status-select" class="mr-2"> <!-- 검색&nbsp; -->
													<input type="search" class="form-control form-control-sm"
													placeholder="보호자 이름" aria-controls="basic-datatable"
													name="search">
													<button type="button" class="btn btn-success btn-sm"
														onclick="butListPaging(1,'','')">검색</button>
												</label>
											</div>
										</form>
									</div>
								</div>
								<div class="table-responsive-sm">
									<table class="table table-centered mb-0"
											style='text-align: center;'>
										<thead>
											<tr>
												<th>예약 번호</th>
												<th>동물 종류</th>
												<th>동물 이름</th>
												<th>예약자명</th>
												<th>서비스 종류</th>
												<th>방문 날짜</th>
											</tr>
										</thead>
										<tbody id="list"></tbody>
									</table>
								</div>
							</div>
							<!-- end card-body-->
						</div>
						<!-- end card-->
					</div>
					<!-- end col -->
				</div>
				<!-- end row -->
				<div class="row mb-2" style="">
					<div class="col-lg-8" id="page_navi"></div>
				</div>
				<!-- end row -->
			</div>
			<%@ include file="footer.html"%>
		</div>

	</div>
	<!-- END wrapper -->

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
<script>
	function butListPaging(pNo, code, wordd) {
		console.log(pNo);
		var search = $("input[name='search']").val();
		var select = $("select").val();
		if(select.length == 0) {
			select = "전체";
		}
		if (code.length != 0) {
			select = code;
		}
		if (wordd.length != 0) {
			search = wordd;
		}
		if (search.length != 0) {
			console.log("검색할 문자 존재,");
			if (select == "전체") {
				console.log("업종상관없이, 전체리스트 ");
				$.ajax({
					url : "searchAllList?pageNum="+pNo+"&search="+search,
					type : 'post',
					dataType : "text",
					success : function(data) {
						$('#list').html(data);
						$.ajax({
							url : "searchAllListPaging?pageNum="+pNo+"&search="+search,
							type : "post",
							dataType : "text",
							success : function(data) {
								$('#page_navi').html(data);
							}
						});
					},
					error : function(error) {
						console.log("실패");
					}
				});
			} else {
				console.log("업종상관있이");
				$.ajax({
					url : "searchBctAllsList?pageNum="+pNo+"&search="+search+"&bct_name="+select,
					type : 'post',
					dataType : "text",
					success : function(data) {
						$('#list').html(data);
						$.ajax({
							url : "searchBctAllsListPaging?pageNum="+pNo+"&search="+search+"&bct_name="+select,
							type : "post",
							dataType : "text",
							success : function(data) {
								$('#page_navi').html(data);
							}
						});
					},
					error : function(error) {
						console.log("실패");
					}
				});
			}
		} else {
			if (select == "전체") {
				console.log("그냥 전체리스트 ");
				$.ajax({
					url : "businessAllBookingList?pageNum=" + pNo,
					type : "post",
					dataType : "text",
					success : function(data) {
						$('#list').html(data);
						$.ajax({
							url : "AllPaging?pageNum=" + pNo,
							type : "post",
							dataType : "text",
							success : function(data) {
								$('#page_navi').html(data);
							}
						});
					},
					error : function(error) {
						console.log(error);
					}
				});
			} else {
				console.log("업종에 따른 리스트 ");
				$.ajax({
					url : "businessAllBctBookingList?bct_name=" + select
							+ "&pageNum=" + pNo,
					type : "post",
					dataType : "text",
					success : function(data) {
						$('#list').html(data);
						$.ajax({
							url : "bctAllPaging?bct_name=" + select
									+ "&pageNum=" + pNo,
							type : "post",
							dataType : "text",
							success : function(data) {
								$('#page_navi').html(data);
							}
						});
					},
					error : function(error) {
						console.log(error);
					}
				});
			}
		}
	}
</script>
</html>