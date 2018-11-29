<!-- 서진 : 기업 새로운 예약 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="newBooking">${list}</div>
	<div id="successBooking"></div>
</body>
<script>
	$(':button').click(function() {
		var name = $(this).attr('name');
		var val = $(this).val();

		if (val === "확정") {
			accept(name);
		} else if (val === "거절") {
			reject(name);
		} // if End
	});

	function accept(bk_no) {
		$.ajax({
			url : 'bookingAccept',
			type : 'post',
			data : {
				'bk_no' : bk_no
			},
			success : function() {
				alert("예약이 확정되었습니다.");
				$('#' + bk_no).html("확정된 예약");
			},
			error : function(error) {
				console.log(error);
			}
		});
	}

	function reject(bk_no) {
		$.ajax({
			url : 'bookingReject',
			type : 'post',
			data : {
				'bk_no' : bk_no
			},
			success : function() {
				alert("예약이 거절되었습니다.");
				$('#' + bk_no).html("거절된 예약");
			},
			error : function(error) {
				console.log(error);
			}
		});
	}
</script>
</html>