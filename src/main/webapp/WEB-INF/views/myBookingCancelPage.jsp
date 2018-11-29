<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>예약 취소</h1>
	<h2>취소 및 환불 규정</h2>
	<h3>
		- 방문일 기준 3일 전 : 100%<br> - 방문일 기준 2일 전 : 80%<br> - 방문일 기준 1일
		전 : 50%<br> - 방문일 당일 및 No-Show : 한불 불가<br> - 취소, 환불 시 수수료가
		발생할 수 있습니다.
	</h3>
	<div>
		<input type="button" id="cenc" value="예약 취소"
			onclick="location.href='myBookingCancel?bk_no=${bk_no}'" />
	</div>
	${flas}
</body>
</html>