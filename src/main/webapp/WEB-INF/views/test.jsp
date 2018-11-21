<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현휘 // 테스트jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

</head>
<body>
<!-- 기업이 가지고 있는 서비스들을 보여주는 페이지 (M,B,H) -->
<form name="servicePage" action="servicePage" method="get">
<button>서비스 페이지</button>
</form>
<!-- 기업에서 일하고 있는 직원들의 리스트를 보여주는 페이지 -->
<form name="stepList" action="stepListBut" method="get">
<button> 스텝 리스트 </button>
</form>

<form action="personalBlackListPage" method="post">
<button> 블랙 리스트 </button>
</form>

<form action="weekCal" name="weekCal" method="post">
<button> 기업 주 스케줄 </button>
</form>

<select name="am_open" id="time">
<script>
	for (var i = 0; i < 48; i++) {
		var hour = '';
		var	min = '00'
		if ((Math.ceil(i / 2)) < 13) {
			if((Math.floor(i / 2)) < 10){
				hour = '0'+(Math.floor(i / 2));
			} else {
				hour = (Math.floor(i / 2));
			}
		} else {
			hour = (Math.floor(i / 2));
		}
		if (i % 2 != 0) {
			min = '30';
		}
		$('#time').append($('<option>',{ value: hour+min, text: hour+min }));
	}
</script>
</select> ~
<select name="pm_close">
<script>
	for (i = 0; i < 48; i++) {
		var hour = '';
		var min = '00';
		if ((Math.ceil(i / 2)) < 13) {
			if((Math.floor(i / 2)) < 10){
				hour = '0'+(Math.floor(i / 2));
			} else {
				hour = (Math.floor(i / 2));
			}
		} else {
			hour = (Math.floor(i / 2));
		}
		if (i % 2 != 0) {
			min = '30';
		}
		document.write('<option value=' + hour + min + '>' + hour +':'+ min + '</option>');
	}
</script>
</select>
</body>
</html>