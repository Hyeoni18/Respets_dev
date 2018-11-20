
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
	<div class="No" style="border: 2px solid red">
		<h3>미확인 예약 (*예약을 확인해주세요*)</h3>
		<hr>${toSdList}</div>
	<div class="Ok" style="border: 2px solid black"></div>
	${none}
</body>
<script>
	$('.unNoshow').hide();
	function com(button, bk_no) {
		console.log(bk_no);
		$.ajax({
			url : 'todayScheduleListCheck?bk_no',
			type : 'post',
			success : function(data) {
				console.log(data);
				if (data != 0) {
					console.log("성공");
					$('.but').html("방문 완료");
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
	function noshow(button, bk_no, pno) {
		var det;
		det = confirm("노쇼를 선택하시겠습니까?");
		if (det) {
			$.ajax({
				url : 'todayScheduleListNoShow',
				type : 'post',
				data : {
					'no' : no,
					'pno' : pno
				},
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
	function unNoshow(button, bk_no, pno) {
		$.ajax({
			url : 'todayScheduleListUnNoShow',
			type : 'post',
			data : {
				'no' : no,
				'pno' : pno
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