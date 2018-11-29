<!-- 서진 : 예약 페이지 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스펫츠 : 예약 페이지</title>
<style>
#petDiv {
	width: 20%;
	margin: 30px;
	margin-bottom: 20px;
	float: left;
	border: 1px solid #bcbcbc;
}

#bkDiv {
	width: 70%;
	margin: 30px;
	margin-bottom: 20px;
	float: left;
	border: 1px solid #bcbcbc;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<form action="booking" method="post">
		<div id="wrap">
			<div id="petDiv">${petList}</div>
			<div id="bkDiv">
				<div id="svcDiv">${svcList}</div>
				<div id="empDiv">${empList}</div>
				<div id="day">
					<p>날짜 선택</p>
					<table id="dayTable">
					</table>
				</div>
				<div id="time">
					<p>시간 선택</p>
					<table id="timeTable">
					</table>
				</div>
				<div id="comment">
					<p>코멘트</p>
					<textarea id="comment" name="bk_cmt" row="10" cols="60" onkeyup="lengthCheck();"></textarea>
				</div>
				<input type="submit" id="submit" value="예약하기" />
			</div>
		</div>
	</form>
</body>
<!-- <script>
	function lengthCheck() {
		var form = document.getElementById('comment').value.length
	}
	function len_chk(){  
	  var frm = document.insertFrm.test; 
	     
	  if(frm.value.length > 4000){  
	       alert("글자수는 영문4000, 한글2000자로 제한됩니다.!");  
	       frm.value = frm.value.substring(0,4000);  
	       frm.focus();  
	  } 
</script> -->
<script>
	$('#dayTable').html("${dayList}");
	$('#day').hide();
	$('#time').hide();
	$('#comment').hide();
	$('#submit').hide();

	var emp = null;

	$('input[name=emp_no]').change(function() {
		emp = $(this).val();
		$('#day').show();
	});

	$('input[name=day]').change(function() {
		date = $(this).val();
		$.ajax({
			url : 'timeSelect',
			type : 'post',
			dataType : 'html',
			data : {
				'date' : date,
				'emp' : emp
			},
			success : function(data) {
				$('#timeTable').html(data);
				$('#time').show();
				$('#comment').show();
				$('#submit').show();
			},
			error : function(error) {
				console.log(error);
			}
		});
	});
</script>
</html>
