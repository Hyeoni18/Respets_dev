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
<form id="writeBusinessNoticePage" action="businessNoticeUpdate" method="post">
<input type="hidden" name="bbo_no" id="BBO_NO" value="0"/>
<select name="bct_code" >
	<option id="m" value="M">병원</option>
	<option id="b" value="B">미용</option>
	<option id="h" value="H">호텔</option>
</select>

<select name="bbc_no" >
	<option id="notice" value="1">공지사항</option>
	<option id="event" value="2">이벤트</option>
</select> <br/>
<input type="text" name="bbo_title" id="BBO_TITLE" /> <br/>
<textarea rows="20" name="bbo_ctt" id="BBO_CTT" ></textarea> <br/>
<button>수정</button>
</form>
</body>
<script>
	${alert}
	var jsonData = ${result};
	console.log(jsonData.BBO_TITLE);
	
	$('#BBO_CTT').html(jsonData.BBO_CTT);
	$('#BBO_TITLE').attr('value', jsonData.BBO_TITLE);
	$('#BBO_NO').attr('value', jsonData.BBO_NO);
	console.log(jsonData.BBO_NO);
	
	if(jsonData.BCT_CODE=='M') {
		$('#m').attr('selected', 'selected');
	}else if(jsonData.BCT_CODE=='B') {
		$('#b').attr('selected', 'selected');
	}else {
		$('#h').attr('selected', 'selected');
	}
	
	if(jsonData.BBC_NO==1) {
		$('#notice').attr('selected', 'selected');
	}else {
		$('#event').attr('selected', 'selected');
	}
</script>
</html>