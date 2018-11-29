<!-- 서진 : 기업 새로운 예약 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 기본 정보 수정</title>
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
		
	<div id="newBooking">${list}</div>
	<div id="successBooking"></div>
		<%@ include file="footer.html"%>
	</div>
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