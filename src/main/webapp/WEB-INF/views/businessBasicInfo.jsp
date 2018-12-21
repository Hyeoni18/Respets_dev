<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Respets :: 기업상세페이지</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/dist/assets/images/logo-sm.png">

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>

<div id=wrapper>
	<div id="workingTime">
		<div id="workingTime">
			<h5 class="text-success">영업 시간</h5>
			<div id="weekday">
				평일(월~금): <span id="weekdayTime"> </span>
			</div>
			<div id="sat">
				토요일: <span id="satTime"> </span>
			</div>
			<div id="sun">
				일요일: <span id="sunTime"> </span>
			</div>
			<div id="hld">
				공휴일: <span id="hldTime"> </span>
			</div>
			<div id="lch">
				점심시간: <span id="lchTime"> </span>
			</div>
			<div id="extra">
				기타 휴무일: <span id="extraDay"> </span>
			</div>
		</div>
	</div>
	<hr />
	<div id="tag">
		<h5 class="text-success">제공 서비스</h5>
		<span id="tagList">${tList}</span>
	</div>
	<hr />

	<div id="location">
		<h5 class="text-success">업체 위치</h5>
		<span id="address"> </span> <span id="addr">${bus_addr} </span> <span
			id="addr2">${bus_addr2}</span>
	</div>
	<br />

	<div id="map" style="width: 100%; height: 250px;"></div>
	</div>


	<!-- App js -->
	<script src="/resources/dist/assets/js/app.min.js"></script>

	<!-- demo app -->
	<script src="/resources/dist/assets/js/pages/demo.project-detail.js"></script>
	<!-- demo app -->
	<script src="resources/dist/assets/js/pages/demo.widgets.js"></script>

</body>

<!--다음 지도 api 불러오기 -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=01f5de4fda83eccb6c481552ba87be63&libraries=services"></script>
<script>
	var mon = '${mon}';
	var tue = '${tue}';
	var wed = '${wed}';
	var thu = '${thu}';
	var fri = '${fri}';
	var x = 'XXXXXXXX';

	if (mon != x) {
		var dayAmHr = mon.substring(0, 2);
		var dayAmMin = mon.substring(2, 4);
		var dayPmHr = mon.substring(4, 6);
		var dayPmMin = mon.substring(6, 8);
		$('#weekdayTime').html(
				dayAmHr + ':' + dayAmMin + ' ~ ' + dayPmHr + ':' + dayPmMin);
	} else {
		$('#extraDay').append('월요일  ');
	}

	if (tue != x) {
		$('#weekdayTime').html(
				dayAmHr + ':' + dayAmMin + ' ~ ' + dayPmHr + ':' + dayPmMin);
	} else {
		$('#extraDay').append('화요일  ');
	}

	if (wed != x) {
		$('#weekdayTime').html(
				dayAmHr + ':' + dayAmMin + ' ~ ' + dayPmHr + ':' + dayPmMin);
	} else {
		$('#extraDay').append('수요일  ');
	}

	if (thu != x) {
		$('#weekdayTime').html(
				dayAmHr + ':' + dayAmMin + ' ~ ' + dayPmHr + ':' + dayPmMin);
	} else {
		$('#extraDay').append('목요일  ');
	}

	if (fri != x) {
		$('#weekdayTime').html(
				dayAmHr + ':' + dayAmMin + ' ~ ' + dayPmHr + ':' + dayPmMin);
	} else {
		$('#extraDay').append('목요일  ');
	}

	var sat = '${sat}';
	if (sat != x) {
		var satAmHr = sat.substring(0, 2);
		var satAmMin = sat.substring(2, 4);
		var satPmHr = sat.substring(4, 6);
		var satPmMin = sat.substring(6, 8);
		$('#satTime').html(
				satAmHr + ':' + satAmMin + ' ~ ' + satPmHr + ':' + satPmMin);
	} else {
		$('#satTime').html('휴무');
	}

	var sun = '${sun}';
	if (sun != x) {
		var sunAmHr = sun.substring(0, 2);
		var sunAmMin = sun.substring(2, 4);
		var sunPmHr = sun.substring(4, 6);
		var sunPmMin = sun.substring(6, 8);
		$('#sunTime').html(
				sunAmHr + ':' + sunAmMin + ' ~ ' + sunPmHr + ':' + sunPmMin);
	} else {
		$('#sunTime').html('휴무');
	}

	var hld = '${hld}';
	if (hld != x) {
		var hldAmHr = hld.substring(0, 2);
		var hldAmMin = hld.substring(2, 4);
		var hldPmHr = hld.substring(4, 6);
		var hldPmMin = hld.substring(6, 8);
		$('#hldTime').html(
				hldAmHr + ':' + hldAmMin + ' ~ ' + hldPmHr + ':' + hldPmMin);
	} else {
		$('#hldTime').html('휴무');
	}

	var lch = '${lch}';
	var lchAmHr = lch.substring(0, 2);
	var lchAmMin = lch.substring(2, 4);
	var lchPmHr = lch.substring(4, 6);
	var lchPmMin = lch.substring(6, 8);
	$('#lchTime').html(
			lchAmHr + ':' + lchAmMin + '~' + lchPmHr + ':' + lchPmMin);
</script>

<script>
	/* 다음 지도 api */
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
		center : new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		level : 3
	// 지도의 확대 레벨
	};
	//주소-좌표 변환 객체를 생성
	var geocoder = new daum.maps.services.Geocoder();
	//실제 주소를 좌표로 변환
	geocoder.addressSearch('${bus_addr}', function(result, status) {
		if (status === daum.maps.services.Status.OK) {
			var coords = new daum.maps.LatLng(result[0].y, result[0].x);
			var marker = new daum.maps.Marker({
				map : map,
				position : coords
			});
			map.setCenter(coords); //중심 좌표를 가져온 주소로 변환
		}
	});

	//지도 생성
	var map = new daum.maps.Map(mapContainer, mapOption);

	//일반 지도와 스카이뷰 지도 타입을 전환 할 수 있는 컨트롤러 생성
	var mapTypeControl = new daum.maps.MapTypeControl();

	//daum.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
	map.addControl(mapTypeControl, daum.maps.ControlPosition.TOPRIGHT);

	//지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
	var zoomControl = new daum.maps.ZoomControl();
	map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);

	//아래 코드는 지도 위의 마커를 제거하는 코드입니다
	//marker.setMap(null);
</script>
</html>