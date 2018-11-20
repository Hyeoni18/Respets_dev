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
	<div id="personal">
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
													<p class="text-muted font-14 mb-4">반려동물의 예약 상세 내역입니다.</p>

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
																${tList}
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

											</div>
											<!-- end card body-->
										</div>
										<!-- end card -->
									</div>
								</div>
							</div>

						</div>
					</div>
					<br /> <br /> <input type="button" id="cancelButton" class="btn btn-success" value="예약 취소"
										onclick="location.href='./myBookingCancelPage'" />
									<input type="button" class="btn btn-success" value="목록으로 돌아가기" 
										onclick="location.href='./recentMyBookingList'" >
				</div></div></div></div></div>
<%-- 
														<button id="reviewButton" onclick="reviewTest?no=${no}">리뷰
															작성</button>
														<div id="review"></div>
														<!-- Standard  modal -->
														<button id="reviewButton2" type="button"
															class="btn btn-primary" data-toggle="modal"
															data-target="#myModal">리뷰 작성</button>

														<!-- Standard modal content -->
														<div id="myModal" class="modal fade" tabindex="-1"
															role="dialog" aria-labelledby="myModalLabel"
															aria-hidden="true">
															<div class="modal-dialog">
																<div class="modal-content">
																	<div class="modal-header">
																		<h4 class="modal-title" id="myModalLabel">Modal
																			Heading</h4>
																		<button type="button" class="close"
																			data-dismiss="modal" aria-hidden="true">×</button>
																	</div>
																	<div class="modal-body">
																		<h6>리뷰 작성</h6>
																		<table>
																			<tr>
																				<td width="500" height="50" colspan="2">
																					<div id="rating" align="center">
																						<span> <img id="img1" onmouseover="show(1)"
																							onclick="mark(1)" onmouseout="noshow(1)" src="">
																							<img id="img2" onmouseover="show(2)"
																							onclick="mark(2)" onmouseout="noshow(2)" src="">
																							<img id="img3" onmouseover="show(3)"
																							onclick="mark(3)" onmouseout="noshow(3)" src="">
																							<img id="img4" onmouseover="show(4)"
																							onclick="mark(4)" onmouseout="noshow(4)" src="">
																							<img id="img5" onmouseover="show(5)"
																							onclick="mark(5)" onmouseout="noshow(5)" src="">
																						</span>
																					</div> <textarea name="content" rows="50" cols="10"></textarea>
																				</td>
																				<td><input type="submit" name="submit"
																					value="등록"></td>
																		</table>
																		<span class="star_rating"><span
																			style="width: 50%"></span></span>
																		<hr>

																	</div>
																	<div class="modal-footer">
																		<button type="button" class="btn btn-light"
																			data-dismiss="modal">Close</button>
																		<button type="button" class="btn btn-primary">Save
																			changes</button>
																	</div>
																</div>
																<!-- /.modal-content -->
															</div>
															<!-- /.modal-dialog -->
														</div>
														<!-- /.modal -->
													</div> --%>

													<div id="business">
														<form name="myBookingDetail" method="post">
															<h1>예약 상세내역 확인</h1>
															<div>
																<div>
																	<h3>예약한 업체 정보</h3>
																	<br /> - 업체명 : ${bt.bus_name}<br /> - 업체전화 :
																	${bt.bus_phone}<br /> - 업체주소 : ${bt.bus_post}
																	${bt.bus_addr} ${bt.bus_addr2}
																</div>
																<hr />
																<div>
																	<h3>예약한 서비스</h3>
																	- 예약일자 : ${bt.bk_time}<br /> - 서비스 종류 : ${bt.tag_name}<br />
																	- 스텝 : ${bt.emp_name}
																</div>
																<hr />
																<div>
																	<h3>내 동물 정보</h3>
																	- 예약 동물 이름 : ${bt.pet_name}<br /> - 동물 종류 :
																	${bt.pty_name}<br /> - 동물 사이즈 : ${bt.pty_name}
																</div>
																<hr />
																<div>
																	<h3>보호자 정보</h3>
																	- 보호자 이름 : ${bt.per_name}<br /> - 연락처 :
																	${bt.per_phone}
																</div>
																<h3>예약 코멘트</h3>
																${bt.bk_cmt}
															</div>
														</form>
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
	console.log(jsonData.VS_CHK);
	$('#reviewButton').hide();
	$('#reviewButton2').hide();
	$('#cancelButton').hide();
	
	if(jsonData.VS_CHK=='X') {
		$('#reviewButton').hide();
		$('#cancelButton').show()
	}else {
		$('#reviewButton').show();
		$('#cancelButton').hide
	}
	
	if(jsonData.VS_CHK=='X') {
		$('#reviewButton2').hide();
		$('#cancelButton').show()
	}else{
		$('#reviewButton2').show();
		$('#cancelButton').hide
	}

	$.each(jsonData, function(key, value) {
		console.log("key: " + key + " / " + "value" + value);
		$('#' + key).html(value);
	});
</script>

<script>
	var no = '$no';
	$('#personal').hide();
	$('#business').hide();

	if (no.indexOf('P')) {
		$('#personal').show();
		$('#business').hide();
	} else {
		$('#personal').hide();
		4('#business').show();
	}
</script>
<script>
	/* Aj("reviewInsertPage", "#review");
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