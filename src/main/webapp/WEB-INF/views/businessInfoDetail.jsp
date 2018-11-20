<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	
</script>
</head>
<body>
	<h1>기본 정보 확인</h1>
	<hr />
	<form action="businessInfoUpdateForm?no=${bi.bus_no}"
		name="businessInfoDetail">
		${bi.bus_no}
		<div>
			- 사업자 번호: ${bi.bus_lcno }<br /> - 업체명: ${bi.bus_name }<br /> -
			대표자명: ${bi.bus_ceo }<br /> - 업체 연락처: ${bi.bus_phone }<br /> -우편번호:
			${bi.bus_post}- 사업장 기본주소: ${bi.bus_addr }, 상세주소${bi.bus_addr2 }<br />
			- 주력 서비스: ${bi.bct_name }
		</div>
		<h2>※ 업종 정보 및 직원 정보를 등록하셔야 예약서비스 제공이 가능합니다.</h2>
		<div>
			<button>수정</button>
		</div>
	</form>
</body>
</html>