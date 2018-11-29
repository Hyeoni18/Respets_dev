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

		<h3>미확인 예약 (*예약을 확인해주세요*)</h3>
		<input type='radio' name='radio' class='radio' value="전체" />전체
		${bctList}
		<hr>
		<div class="No" style="border: 2px solid red"></div>
		<div class="Ok"></div>

		<%@ include file="footer.html"%>
	</div>
</body>
<script>
	$(document).ready(function() {
		$('input[type="radio"]').click(function() {
			var radio = $('input[type="radio"]:checked').val();
			console.log(radio);
			//전체리스트 불러오기
			if (radio == '전체') {
				$.ajax({
					url : "todayAllScheduleList?no=${no}",
					type : "post",
					dataType : "text",
					success : function(data) {
						$('.No').html(data);
						$('.unNoshow').hide();
					},
					error : function(error) {
						console.log(error);
					}
				});
			}
			if (radio == '병원' || radio == '미용' || radio == '호텔') {
				$.ajax({
					url : 'bctBookingList?no=${no}&bct_name=' + radio,
					type : 'post',
					dataType : "text",
					success : function(data) {
						$('.No').html(data);
						$('.unNoshow').hide();
					},
					error : function(error) {
						console.log(error);
					}
				});
			}
		});
	});
</script>
<script>
	function com(bk_no) {
		var but = $('span[class="' + bk_no + '"]');
		var div = $('div[id="' + bk_no + '"]');
		console.log(but);
		console.log(bk_no);
		$.ajax({
			url : 'todayScheduleListCheck',
			type : 'post',
			data : {
				'bk_no' : bk_no
			},
			success : function(data) {
				if (data != 0) {
					console.log("성공");
					$()
					$(but).html("방문 완료");
					$('#' + bk_no).hide();
					$(div).insertAfter('.Ok');
				} else {
					console.log("실패");
				}
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
						$('.unNoshow').show();
						$('.noshow').hide();
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
	function unNoshow(pno) {
		$.ajax({
			url : 'todayScheduleListUnNoShow',
			type : 'post',
			data : {
				'per_no' : pno
			},
			success : function(data) {
				console.log(data);
				if (data != 0) {
					console.log("성공");
					$('.noshow').show();
					$('.unNoshow').hide();
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