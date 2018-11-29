<!-- 서진 : 기업 새로운 예약 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/dist/assets/images/logo-sm.png">

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div class="page-content">
		<div class="card">
			<div class="card-body">
				<div class="p-lg-1">
					<div class="example-container">
						<div class="row">
							<div class="col-xl-12">
								<div class="card mb-0">
									<div class="card-body">
										<h3 class="header-title">새로운 예약 목록</h3>

										<div class="table-responsive-sm">
											<table class="table table-centered mb-0">
												<thead>
													<tr>
														<th>예약번호</th>
														<th>동물종류</th>
														<th>동물이름</th>
														<th>예약자명</th>
														<th>방문일시</th>
														<th>예약상태</th>
													</tr>
													${list}
												</thead>
												<tbody>
												</tbody>
											</table>
										</div>

									</div>
									<!-- end card body-->
								</div>
								<!-- end card -->
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
<script src="/resources/dist/assets/js/app.min.js"></script>
<script>
	$(':button').click(function() {
		var name = $(this).attr('name');
		var val = $(this).val();

		if (val === "확정") {
			accept(name);
		} else if (val === "거절") {
			reject(name);
		} // if End
	});

	function accept(bk_no) {
		$.ajax({
			url : 'bookingAccept',
			type : 'post',
			data : {
				'bk_no' : bk_no
			},
			success : function() {
				alert("예약이 확정되었습니다.");
				$('#' + bk_no).html("<span class='text-success'>확정된 예약</span>");
			},
			error : function(error) {
				console.log(error);
			}
		});
	}

	function reject(bk_no) {
		$.ajax({
			url : 'bookingReject',
			type : 'post',
			data : {
				'bk_no' : bk_no
			},
			success : function() {
				alert("예약이 거절되었습니다.");
				$('#' + bk_no).html("<span class='text-danger'>거절된 예약</span>");
			},
			error : function(error) {
				console.log(error);
			}
		});
	}
</script>
</html>