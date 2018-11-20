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
<div id=wrapper>
	<div id="workingTime">
		<div id="workingTime"><h4>영업 시간</h4>
			<div id="weekday">평일(월~금): </div> <span id="weekdayTime"> </span>
			<div id="sat">토요일: </div> <span id="satTime"> </span>
			<div id="sun">일요일: </div> <span id="sunTime"> </span>
			<div id="hld">공휴일: </div> <span id="hldTime"> </span>
			<div id="lch">점심시간: </div> <span id="lchTime"> </span>
			<div id="extra">기타휴무일: </div> <span id="extraDay"> </span>
		</div>
	</div>
	<div id="location">
		<h4>업체 위치</h4>
		<span id="address">주소: </span> <span id="addr">${bus_addr} </span> <span id="addr2">${bus_addr2}</span>
	</div>
	<div id="tag">
		<h4>제공 서비스</h4>
		<span id="tagList">${tList}</span>
	</div>
</div>

</body>
<script>
 var mon = '${mon}';
 var tue = '${tue}';
 var wed = '${wed}';
 var thu = '${thu}';
 var fri = '${fri}';
 var x = 'XXXXXXXX';
 
 if(mon != x) {
	var dayAmHr = mon.substring(0,2);
	var dayAmMin = mon.substring(2,4);
	var dayPmHr = mon.substring(4,6);
	var dayPmMin = mon.substring(6,8);
	$('#weekdayTime').html(dayAmHr + ':' + dayAmMin + ' ~ ' + dayPmHr + ':' + dayPmMin);
 }else {
	 $('#extraDay').append('월요일  ');
 }
 
 
 
 if(tue != x) {
	$('#weekdayTime').html(dayAmHr + ':' + dayAmMin + ' ~ ' + dayPmHr + ':' + dayPmMin);
 }else {
	$('#extraDay').append('화요일  ');
 }

 if(wed != x) {
	$('#weekdayTime').html(dayAmHr + ':' + dayAmMin + ' ~ ' + dayPmHr + ':' + dayPmMin);
 }else {
	$('#extraDay').append('수요일  ');
 }

 if(thu != x) {
	$('#weekdayTime').html(dayAmHr + ':' + dayAmMin + ' ~ ' + dayPmHr + ':' + dayPmMin);
 }else {
	$('#extraDay').append('목요일  ');
 }

 if(fri != x) {
	$('#weekdayTime').html(dayAmHr + ':' + dayAmMin + ' ~ ' + dayPmHr + ':' + dayPmMin);
 }else {
	$('#extraDay').append('목요일  ');
 }
 
 
 
 var sat = '${sat}';
 if(sat != x) {
	 var satAmHr = sat.substring(0,2);
	 var satAmMin = sat.substring(2,4);
	 var satPmHr = sat.substring(4,6);
	 var satPmMin = sat.substring(6,8);
	 $('#satTime').html(satAmHr + ':' + satAmMin + ' ~ ' + satPmHr + ':' + satPmMin);	  
 }else {
	 $('#satTime').html('토요일은 쉽니다.');
 }
 
 
 var sun = '${sun}';
 if(sun != x) {
 	var sunAmHr = sun.substring(0,2);
 	var sunAmMin = sun.substring(2,4);
 	var sunPmHr = sun.substring(4,6);
 	var sunPmMin = sun.substring(6,8);
 	$('#sunTime').html(sunAmHr + ':' + sunAmMin + ' ~ ' + sunPmHr + ':' + sunPmMin);	 
 }else {
	 $('#sunTime').html('일요일은 쉽니다.');
 }
 
 
 
 var hld = '${hld}';
 if(hld != x) {
	 var hldAmHr = hld.substring(0,2);
	 var hldAmMin = hld.substring(2,4);
	 var hldPmHr = hld.substring(4,6);
	 var hldPmMin = hld.substring(6,8);
	 $('#hldTime').html(hldAmHr + ':' + hldAmMin + ' ~ ' + hldPmHr + ':' + hldPmMin);
 }else {
	 $('#hldTime').html('공휴일은 쉽니다.');
 }
 
 
 
 var lch = '${lch}';
	 var lchAmHr = lch.substring(0,2);
	 var lchAmMin = lch.substring(2,4);
	 var lchPmHr = lch.substring(4,6);
	 var lchPmMin = lch.substring(6,8);
	 $('#lchTime').html(lchAmHr + ':' + lchAmMin + '~' + lchPmHr + ':' + lchPmMin); 
 
</script>
</html>