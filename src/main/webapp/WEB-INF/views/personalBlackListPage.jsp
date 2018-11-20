<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form>
<input type="radio" name="black" value="yellow" onclick="black(this)"/> 경고 (한달 정지)
<input type="radio" name="black" value="red" onclick="black(this)"/> 정지 (영구 정지)
<div id="blackList">
</div>
</form>
</body>
<script>
function black(val) {
	console.log("찍혀랏");
	var black = $('input[name=black]').val();
	var url = "blackList?black="+black;
	console.log(url);
	Aj(url,"#blackList");
	function Aj(url, position) { //파라미터 값이 3개라고 꼭 3개를 받을 필요는 없다.
		$.ajax({
			url: url,
			type: "post",
			//dataType: "html", //생략가능 //menu.jsp 에 가서 모든 정보를 긁어와서 밑에 success나 error로 갈거야. 그래서 해당 div에 박아줄거야.
			success: function(page) {
				console.log(page);
				$(position).html(page);
			},
			error: function(error) {
				console.log("error");
			}
		}); //ajax End
	} 
}
</script>
</html>