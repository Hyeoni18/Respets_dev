<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<br/><br/>
	<form name="businessButtonPage">
		<div>
			<input type="button" name="todayScheduleList" value="오늘 일정 목록" onclick="forward(this)" /><br /> 
			<input type="button" name="newScheduleList" value="새로운 예약" onclick="forward(this)" /><br />
			<input type="button" name="businessBookingList" value="전체 예약 목록" onclick="forward(this)" /><br /> 
			<input type="button" name="serviceManagement" value="서비스 관리" onclick="forward(this)" /><br />
			<input type="button" name="stepManagement" value="직원 관리" onclick="forward(this)" /><br /> 
			<!-- <input type="button" name="customerManagement" value="고객 관리" onclick="forward(this)" /><br /> -->
		</div>
	</form>
	<form id="businessNoticeList" action="./businessNoticeList">
		<input type="submit" value="공지사항 관리" />
	</form>
</body>
<script>
	function forward(button) {
		var frm = document.businessButtonPage;
		if (button.value == '오늘 일정 목록') {
			frm.action = "todayScheduleList";
		}
		if (button.value == '새로운 예약') {
			frm.action = "newScheduleList";
		}
		if (button.value == '전체 예약 목록') {
			frm.action = "businessBookingList";
		}
		if (button.value == '서비스 관리') {
			frm.action = "serviceManagement";
		}
		 if (button.value == '직원 관리') {
			frm.action = "stepListBut";
		}
		 if (button.value == '고객 관리') {
			 
		 }
		frm.submit();
	}
</script>
</html>