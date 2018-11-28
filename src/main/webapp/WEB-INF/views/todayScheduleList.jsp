<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<h1>오늘 일정 목록</h1>
	${no}
	<br>
	<h3>미확인 예약 (*예약을 확인해주세요*)</h3>
	<input type='radio' name='radio' class='radio' value="전체" />전체
	${bctList}
	<hr>
	<div class="No" style="border: 2px solid red"></div>
	<div class="Ok"></div>
	${none}
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
		var but = $('span[id]').val();
		console.log(but);
		$.ajax({
			url : 'todayScheduleListCheck',
			type : 'post',
			data : {
				'bk_no' : bk_no
			},
			success : function(data) {
				if (data != 0) {
					console.log("성공");
					$(but).html("방문 완료");
					$('.ton').hide();
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