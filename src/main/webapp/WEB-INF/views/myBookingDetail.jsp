<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 예약 상세 내역</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/logo-sm.png">

<!-- third party css -->
<link
	href="${pageContext.request.contextPath}/resources/dist/assets/css/vendor/jquery-jvectormap-1.2.2.css"
	rel="stylesheet" type="text/css" />
<!-- third party css end -->

<!-- App css -->
<link
	href="${pageContext.request.contextPath}/resources/dist/assets/css/icons.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/resources/dist/assets/css/app.min.css"
	rel="stylesheet" type="text/css" />
<style>
.star-rating {
	width: 205px;
}

.star-rating, star-rating span {
	display: inline-block;
	height: 39px;
	overflow: hidden;
	background: url(star.png) no-repeat;
}

.star-rating span {
	background-position: left bottom;
	line-height: 0;
	vertical-align: top;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<div id="wrapper">
		<%@ include file="left-sidebar.jsp"%>

		<div class="content-page">
			<div class="content">

				<%@ include file="topbar-dashboard.jsp"%>

				<div class="container-fluid">

					<!-- start page title -->
					<div class="row">
						<div class="col-12">
							<div class="page-title-box">
								<div class="page-title-right">
									<form class="form-inline"></form>
								</div>
								<h4 class="page-title">예약 상세 내역</h4>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xl-12">
							<div class="card mb-0">
								<div class="card-body">
									<span class="text-muted font-14 mb-4">예약 서비스 상세 내역입니다.</span> <br />
									<br /> <br />

									<div class="table-responsive-sm">
										<table class="table table-centered mb-0">
											<thead>
												<tr>
													<td colspan='2'><h4>예약 업체 정보</h4></td>
												</tr>
												<tr>
													<td>업체명</td>
													<td id="BUS_NAME"></td>
												</tr>
												<tr>
													<td>연락처</td>
													<td id="BUS_PHONE"></td>
												</tr>
												<tr>
													<td>주소</td>
													<td><span id="BUS_ADDR"></span>&nbsp;<span
														id="BUS_ADDR2"></span></td>
												</tr>
												<tr></tr>
												<tr>
													<td colspan='2'><h4>예약한 서비스</h4></td>
												</tr>
												<tr>
													<td>예약일시</td>
													<td id="VS_START"></td>
												</tr>
												<tr>
													<td>서비스 종류</td>
													<td id="MENU_NAME">${mList}</td>
												</tr>
												<tr>
													<td>스탭명</td>
													<td id="EMP_NAME"></td>
												</tr>
												<tr></tr>
												<tr>
													<td colspan='2'><h4>반려동물 정보</h4></td>
												</tr>
												<tr>
													<td>이름</td>
													<td id="PET_NAME"></td>
												</tr>
												<tr>
													<td>종류</td>
													<td id="PTY_NAME"></td>
												</tr>
												${tList} ${pList}
												<tr></tr>
												<tr>
													<td colspan='2'><h4>보호자 정보</h4></td>
												</tr>
												<tr>
													<td>보호자명</td>
													<td id="PER_NAME"></td>
												</tr>
												<tr>
													<td>연락처</td>
													<td id="PER_PHONE"></td>
												</tr>
												<tr></tr>
												<tr>
													<td colspan='2'><h4>예약 코멘트</h4></td>
												</tr>
												<tr>
													<td colspan='2' id="BK_CMT"></td>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
									<div id="cencel">
										&emsp;<input type="button" id="perBut"
											class="btn btn-outline-success btn-rounded" value="목록으로 돌아가기"
											onclick="location.href='./recentMyBookingList'">
										&emsp;<input type="button" id="busBut"
											class="btn btn-outline-success btn-rounded" value="목록으로 돌아가기"
											onclick="location.href='./todayScheduleList'"> &emsp;<input
											type="button" class='btn btn-outline-danger btn-rounded'
											id="cancel" value="예약 취소"
											onclick="location.href='myBookingCancelPage?bk_no=${bk_no}'" />
									</div>
								</div>
								<!-- end card body-->
							</div>
							<!-- end card -->
						</div>
						<!-- end col -->
					</div>
					<!-- end row -->

				</div>
				<!-- end card body-->
			</div>
			<!-- end card -->
		</div>
	</div>
	<br />
	<br />
</body>
<script src="/resources/dist/assets/js/app.min.js"></script>
<script>
	var jsonData = ${result};
	$('#reviewButton').hide();
	$('#reviewButton2').hide();
	$('#cancelButton').hide();

	if (jsonData.VS_CHK == 'X') {
		$('#reviewButton').hide();
		$('#cancelButton').show()
	} else {
		$('#reviewButton').show();
		$('#cancelButton').hide
	}

	if (jsonData.VS_CHK == 'X') {
		$('#reviewButton2').hide();
		$('#cancelButton').show()
	} else {
		$('#reviewButton2').show();
		$('#cancelButton').hide
	}

	$.each(jsonData, function(key, value) {
		console.log("key: " + key + " / " + "value" + value);
		$('#' + key).html(value);
	});
</script>

<script>
	var no = '${no}';
	var chk = '${chk}';
	$('#cancel').hide();
	$('#perBut').hide();
	$('#busBut').hide();
	if (no.indexOf('P') == 0) {
		$('#perBut').show();
		if (chk == '신청' || chk == '승인') {
			$('#cancel').show();
		} else if (chk == '취소' || chk == '거절') {
			$('#cancel').hide();
		}
	} else {
		$('#busBut').show();
	}
</script>
</html>