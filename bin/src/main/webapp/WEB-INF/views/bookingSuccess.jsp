<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스펫츠 : 예약 완료</title>
</head>
<body>
	${bus_name} ${vs_start}으로<br />
	[${bct_name}] 서비스 예약이 완료되었습니다.<br />
	<button onclick='myPage();'>마이페이지</button>
	<button onclick='schedule();'>캘린더</button>
</body>
<script>
	function myPage() {
		location.href = "./myPage";
	} // fct End
	
	function schedule() {
		location.href = "./personalCalendar";
	} // fct End
</script>
</html>