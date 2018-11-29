<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
/* 모달 배경 */
.modal {
display: none;
position: fixed;
z-index: 1;
padding-top: 100px;
left: 0;
top: 0;
width: 100%;
height: 100%;
overflow: auto;
background-color: rgb(0,0,0);
/* background-color: rgba(0,0,0,0.4); */
background-color: rgba(255, 0, 0, 0.2);
}
/* 모달 내용 */
.modal-context {
background-color: #f7e6e6;
margin: auto;
padding: 20px;
border: 1px solid #888;
width: 80%;
}
/* 닫기 버튼 */
.close {
color: #aaaaaa;
float: right;
font-size: 28px;
font-weight: bold;
}
.close: hover,
.close: focus {
color: #000;
text-decoration: none;
cursor: pointer;
}
</style>

</head>
<body>
<h2>모달박스 테스트</h2>
<button id="myBtn"> 모달박스 버튼 </button>
<!-- 모달 박스 -->
<div id="myModal" class="modal">
<!-- 모달 박스 내용 -->
<div class="modal-content">
	<span id="close"></span>
	<p>modal-content~~~~~~~~~~~~~~</p>
</div>
</div>
<script>
var modal = document.getElementById("myModal");
var btn = document.getElementById("myBtn");
var span = document.getElementById("close");
btn.onclick = function() {
	modal.style.display="block";
}
span.onclick = function() {
	modal.style.display="none";
}
window.onclick = function(event) {
	if(event.target == modal) {
		modal.style.display = "none";
	}
}
</script>
</body>
</html>












