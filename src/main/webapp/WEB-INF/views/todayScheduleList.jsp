<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 오늘 일정 목록</title>
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
	<div class="wrapper">
		<%@ include file="left-sidebar.jsp"%>
		<div class="content-page">
			<%@ include file="topbar-dashboard.jsp"%>
			<div class="container-fluid">
				<div class="row">
					<div class="col-12">
						<div class="page-title-box">
							<div class="page-title-right">
								<form class="form-inline"></form>
							</div>
							<h4 class="page-title">오늘 일정 목록</h4>
						</div>
					</div>
				</div>

			<div class="row">
				<div class="col-xl-12">
					<div class="card mb-0">
						<div class="card-body">
							<span class="text-muted font-14 mb-4"> 오늘의 예약을 확인하세요. </span> <br />
							<br /> <input type='radio' name='radio' class='radio' value="전체" />전체
							${bctList}
							<div class="table-responsive-sm">
								<table class="table table-centered mb-0"
									style='text-align: center;'>
									<thead>
										<div class="No" style="border: 1px solid red"></div>
										<div id="Ok" style="border: 1px solid yellow"></div>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
								<!-- end card body-->
							</div>
							<!-- end card body-->
						</div>
						<!-- end card -->
					</div>
					<!-- end col -->
				</div>
			</div>
		</div>
		<%@ include file="footer.html"%>
		<script src="/resources/dist/assets/js/app.min.js"></script>
	</div>
</body>
<script>
	$(document).ready(function() {
	}
	
	function radio() {
		if($('input[type="radio"]:checked').val() ) {
			
			var radio = $('input[type="radio"]:checked').val();
			var no = "${no}";

			if (radio == '전체') {
				$.ajax({
					url : "todayAllScheduleList",
					data : { "no" : no },
					type : "post",
					dataType : "text",
					success : function(data) {
						$('#No').html(data);
						$('.unNoshow').hide();
						$.ajax({
							url : "todayAllScheduleListOk",
							data : { "no" : no },
							type : "post",
							dataType : "text",
							success : function(data) {
								$('#Ok').html(data);
							}, // success End
							error : function(error) {
								console.log(error);
							} // error End
						}); // ajax End
					}, // success End
					error : function(error) {
						console.log(error);
					}
				});
			}
			if (radio == '병원' || radio == '미용' || radio == '호텔') { /* 수정 */
				$.ajax({
					url : 'bctBookingList?no=${no}&bct_name='+ radio,
					type : 'post',
					dataType : "text",
					success : function(data) {
						$('.No').html(data);
						$('.unNoshow').hide();
						$.ajax({
							url : "bctBookingListOk?no=${no}&bct_name="+ radio,
							type : "post",
							dataType : "text",
							success : function(data) {
								$('#Ok').html(data);
							},
							error : function(error) {
								console.log(error);
							}
						});
					},
					error : function(error) {
						console.log(error);
					}
				});
			}
		});
	});

	function com(bk_no) { /* 사용 */
		var but = $('span[class="' + bk_no + '"]');
		var div = $('div[id="' + bk_no + '"]');
		console.log(but);
		console.log(bk_no);
		var no = "${no}";
		$.ajax({
			url : "todayScheduleListCheck?bk_no=" + bk_no + "&no=" + no,
			type : "post",
			dataType : "text",
			success : function(data) {
				$('#No').html(data);
				$('.unNoshow').hide();
				$.ajax({
					url : "todayAllScheduleListOk?no=${no}",
					type : "post",
					dataType : "text",
					success : function(data) {
						$('#Ok').html(data);
					},
					error : function(error) {
						console.log(error);
					}
				});

			},
			error : function(error) {
				console.log(error);
			}
		});
	}

	function comCode(bk_no, bct_name) { /* 사용 */
		var but = $('span[class="' + bk_no + '"]');
		var div = $('div[id="' + bk_no + '"]');
		console.log(but);
		console.log(bk_no);
		var no = "${no}";
		$.ajax({
			url : "bctBookingListCheck?bk_no=" + bk_no + "&no=" + no
					+ "&bct_name=" + bct_name,
			type : "post",
			dataType : "text",
			success : function(data) {
				$('#No').html(data);
				$('.unNoshow').hide();
				$.ajax({
					url : "bctBookingListOk?no=${no}&bct_name=" + bct_name,
					type : "post",
					dataType : "text",
					success : function(data) {
						$('#Ok').html(data);
					},
					error : function(error) {
						console.log(error);
					}
				});

			},
			error : function(error) {
				console.log(error);
			}
		});
	}

	function cancelCheck(bk_no) { /* 사용 */
		console.log("${no}");
		var no = "${no}";
		console.log(bk_no);
		$.ajax({
			url : "todayScheduleListCancel?no=" + no + "&bk_no=" + bk_no,
			type : "post",
			dataType : "text",
			success : function(data) {
				$('#No').html(data);
				$('.unNoshow').hide();

				$.ajax({
					url : "todayAllScheduleListOk?no=${no}",
					type : "post",
					dataType : "text",
					success : function(data) {
						$('#Ok').html(data);
					},
					error : function(error) {
						console.log(error);
					}
				});

			},
			error : function(error) {
				console.log(error);
			}
		});
	}
	function cancelCodeCheck(bk_no, bct_name) { /* 사용 */
		var radio = $('input[type="radio"]:checked').val();
		console.log(radio);

		$.ajax({
			url : "bctBookingListCancel?no=${no}&bct_name=" + radio + "&bk_no="
					+ bk_no,
			type : "post",
			dataType : "text",
			success : function(data) {
				$('#No').html(data);
				$('.unNoshow').hide();
				$.ajax({
					url : "bctBookingListOk?no=${no}&bct_name=" + radio,
					type : "post",
					dataType : "text",
					success : function(data) {
						$('#Ok').html(data);
					},
					error : function(error) {
						console.log(error);
					}
				});
			},
			error : function(error) {
				console.log(error);
			}
		});

	}

	function noshow(bk_no, pno) {
		var det;
		det = confirm("노쇼를 선택하시겠습니까?");
		if (det) {
			$.ajax({
				url : 'todayScheduleListNoShow?bk_no=' + bk_no + '&pno=' + pno,
				type : 'post',
				success : function(data) {
					console.log(data);
					if (data != 0) {
						console.log("성공");
						$('#un' + bk_no).show();
						$('#no' + bk_no).hide();
					} else {
						console.log("실패");
					}
				},
				error : function(error) {
					console.log(error);
				}
			});
		}
	}
	function unNoshow(pno, bk_no) {
		$.ajax({
			url : 'todayScheduleListUnNoShow?bk_no=' + bk_no + '&pno=' + pno,
			type : 'post',
			success : function(data) {
				console.log(data);
				if (data != 0) {
					console.log("성공");
					$('#no' + bk_no).show();
					$('#un' + bk_no).hide();
				} else {
					console.log("실패");
				}
			},
			error : function(error) {
				console.log(error);
			}
		});

	}
</script>
</html>