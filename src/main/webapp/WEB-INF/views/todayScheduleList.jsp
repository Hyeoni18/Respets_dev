
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<h1>오늘 일정 목록</h1>
	${no}
	<div class="No" style="border: 2px solid red">
		<h3>미확인 예약 (*예약을 확인해주세요*)</h3>
		<hr>${toSdList}</div>
	<div class="Ok" style="border: 2px solid black"></div>
	${none}
</body>
<script>
	function forward(button) {
		var com = $("input[name='com']").val();
		console.log(com);
		var but = $("span[name='but']");
		var no = '${no}'
		console.log(no);
		
		if (com == '방문') {
			$.ajax({
				url : 'todayScheduleListCheck',
				type : 'get',
				data : {
					'no' : no
				},
				success : function(data) {
					if (data != 0) {
						console.log("성공");
						$('#list').insertAfter(".Ok");//$("#list").appendTo(".ok");
						but.innerHTML = "방문완료";
					} else {
						console.log("실패");
					}
				},
				error : function(error) {
					console.log(error);
				}
			});
		}
		if (com == '예약취소') {
			$.ajax({
				url : 'todayScheduleCancell',
				type : 'get',
				data : {
					'no' : no
				},
				success : function(data) {
					if (data == 0) {
						console.log("성공");
					} else {
						console.log("실패");
					}
				},
				error : function(error) {
					console.log(error);
				}
			});
		}
		if (com == '노쇼취소'){
			$.ajax({
				url : "notNoshow",
				type : 'get'
			})
		}
		if (com == '노쇼') {
			var det;
			det = confirm("노쇼를 선택하시겠습니까?");
			if (det) {
				console.log("들?");
				console.log('${bkno}');
				$.ajax({
					url : 'todayScheduleListNoShow',
					type : 'get',
					data : {
						'bkno' : '${bkno}'
					},
					success : function(data) {
						if (data != 0) {
							console.log("성공");
						} else {
							console.log("실패");
						}
					},
					error : function(error) {
						console.log(error);
					}
				});
			} else {
				frm.action = "todayScheduleList";
			}
		}
	}
</script>
</html>