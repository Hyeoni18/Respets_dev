<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Respets 최근 예약 목록</title>
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
<body>
	<div id="recentBookingList">
		<div class="page-content">
			<div class="card">
				<div class="card-body">
					<div class="p-lg-1"></div>

					<div class="p-lg-1">
						<div class="example-container">
							<div class="row">
								<div class="col-xl-12">
									<div class="card mb-0">
										<div class="card-body">
											<h3 class="header-title">최근 예약 목록</h3>
											<p class="text-muted font-14 mb-4">내 반려동물의 최근 서비스 예약 목록을
												확인해보세요.</p>

											<div class="table-responsive-sm">
												<table class="table table-centered mb-0">
													<thead>
														<tr>
															<th>예약번호</th>
															<th>업체명</th>
															<th>동물종류</th>
															<th>동물이름</th>
															<th>예약자명</th>
															<th>예약일시</th>
															<th>방문일시</th>
															<th>예약확정상태</th>
														</tr>
														${hList}
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
	</div>
</body>
<script src="/resources/dist/assets/js/app.min.js"></script>

</html>