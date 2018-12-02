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
<body>
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
						
									
										<div id="No" style="border: 1px solid red" class="table-responsive-sm">
										
										</div>
										<div id="Ok" style="border: 1px solid yellow">
										
										</div>
											
								

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
</body>
<script>
	$(document).ready(function() { /* 사용 */
		$('input[type="radio"]').click( function() {
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
			if (radio == '병원' || radio == '미용' || radio == '호텔') { /* 수정 */
				$.ajax({
					url : 'bctBookingList?no=${no}&bct_name='+ radio,
					type : 'post',
					dataType : "text",
					success : function(data) {
						$('#No').html(data);
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