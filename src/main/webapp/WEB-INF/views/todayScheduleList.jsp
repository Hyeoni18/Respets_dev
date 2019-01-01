<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 오늘 일정 목록</title>
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
<body onload="test()">
	<!-- Begin page -->
	<div class="wrapper">

		<!-- ========== Left Sidebar Start ========== -->
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

				<!-- Start Content-->
				<div class="container-fluid">

					<!-- start page title -->
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
					<!-- end page title -->
									<input type='radio' name='radio' class='radio' value="전체" onchange='test();' checked="checked" />전체 ${bctList}
									<br /><br />
					<div class="row">
						<div class="col-xl-12">
							<div class="card mb-0">
								<div class="card-body">
									<span class="text-muted font-14 mb-4">방문 확인 처리되지 않은 예약입니다.</span>
									<br /> <br/>
									<div id="No" class="table-responsive-sm"></div>
								</div>
							</div>
							<!-- end card body-->
						</div>
						<!-- end card -->
					</div>
					<!-- end col -->
					<br />
					<div class="row">
						<div class="col-xl-12">
							<div class="card mb-0">
								<div class="card-body">
									<span class="text-muted font-14 mb-4">방문 확인 처리된 예약입니다.</span>
									<br /> <br />
									<div id="Ok" class="table-responsive-sm"></div>
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
			<!-- container -->
		</div>
		<!-- content -->

		<!-- Footer Start -->
		<%@ include file="footer.html"%>
		<!-- end Footer -->

	</div>

	<!-- ============================================================== -->
	<!-- End Page content -->
	<!-- ============================================================== -->
</body>

<script src="/resources/dist/assets/js/app.min.js"></script>

<script>
	function test() {
		var radio = $('input[type="radio"]:checked').val();
		//전체리스트 불러오기
		if (radio == '전체') {
			$.ajax({
				url : "todayAllScheduleList?no=${no}",
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
		
		//서비스별 리스트 불러오기
		if (radio == '병원' || radio == '미용' || radio == '호텔') {
			$.ajax({
				url : 'bctBookingList?no=${no}&bct_name=' + radio,
				type : 'post',
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
	}
	
	//방문 예약 확인
	function com(bk_no) {
		var but = $('span[class="' + bk_no + '"]');
		var div = $('div[id="' + bk_no + '"]');
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

	//서비스별 방문 예약 확인
	function comCode(bk_no, bct_name) {
		var but = $('span[class="' + bk_no + '"]');
		var div = $('div[id="' + bk_no + '"]');
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

	//예약 방문 취소
	function cancelCheck(bk_no) {
		var no = "${no}";
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
	
	//서비스별 예약 방문 취소
	function cancelCodeCheck(bk_no, bct_name) {
		var radio = $('input[type="radio"]:checked').val();
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