<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets 예약 상세 내역</title>
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
	${no} || ${bk_no}
	<div id="wrapper">
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
												<h2>예약 상세 내역</h2>
												<p class="text-muted font-14 mb-4" id="comanet">반려동물의 예약
													상세 내역입니다.</p>

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
																<td colspan='2'><h4>내 반려동물 정보</h4></td>
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
													<input type="button" id="cenc" value="예약 취소"
														onclick="location.href='myBookingCancelPage?bk_no=${bk_no}'" />
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
					<br /> <br />
					<!-- <input type="button" id="cancelButton"
						class="btn btn-success" value="예약 취소"
						onclick="location.href='./myBookingCancelPage'" />  -->
					<input type="button" id="perBut" class="btn btn-success" value="목록으로 돌아가기"
						onclick="location.href='./recentMyBookingList'">
						<input type="button" id="busBut" class="btn btn-success" value="목록으로 돌아가기"
						onclick="location.href='./todayScheduleList'">
				</div>
			</div>
		</div>
	</div>



	<!-- App js -->
	<%-- <script src="<c:url value="/resources/dist/assets/js/app.min.js"/>"></script> --%>

	<!-- third party js -->
	<%-- <script
		src="<c:url value="/resources/dist/assets/js/vendor/Chart.bundle.min.js"/>"></script>
	<script
		src="<c:url value="/resources/dist/assets/js/vendor/jquery-jvectormap-1.2.2.min.js"/>"></script>
	<script
		src="<c:url value="/resources/dist/assets/js/vendor/jquery-jvectormap-world-mill-en.js"/>"></script> --%>
	<!-- third party js ends -->

	<!-- demo app -->
	<%-- <script
		src="<c:url value="/resources/dist/assets/js/pages/demo.dashboard.js"/>"></script> --%>
	<!-- end demo js-->
</body>
<script src="/resources/dist/assets/js/app.min.js"></script>
<script>
	var jsonData = ${result};
	console.log(jsonData.BK_NO);
	console.log(jsonData.VS_CHK);
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
	$('#cencel').hide();
	$('#perBut').hide();
	$('#busBut').hide();
	if (no.indexOf('P') == 0) {
		//$('#cencel').show();
		$('#perBut').show();
		if (chk == '신청' || chk == '승인') {
			$('#cencel').show();
		} else if (chk == '취소' || chk == '거절') {
			$('#cencel').hide();
		}
	} else {
		$('#busBut').show();
	}
</script>
<script>
	/*  Aj("reviewInsertPage", "#review");
	 if(${page!=null}){
	 Aj("${page}", "#review");
	 }
	 function reviewInsertPage(no){
	 function Aj(url, position){
	 $('#review').addClass('open');
	 $.ajax({
	 url:url,
	 type:"get",
	 date:{no:no},
	 dataType:"html",
	 success : function(page){
	 $(position).html(page);
	 },
	 error:function(error){
	 console.log(error);
	 }
	 });
	 }
	 } */
</script>
</html>