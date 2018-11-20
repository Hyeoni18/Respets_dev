<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현휘 // 아이디찾는폼 </title>
</head>
<body>
<!--현휘; 아이디 찾는 메소드로 이동 ,
		  기업을 클릭해도 Personal bean을 이용하기 위해 name을 per_로 설정 -->
<form action="findMyId" method="POST">
<input type="radio" name="type" value="P"/> 개인 &nbsp;&nbsp; 
<input type="radio" name="type" value="B"/> 기업 <br/>
이름(대표자명) : <input type="text" name="per_name"/> <br/>
전화번호(사업자번호) : <input type="text" name="per_phone"/> <br/>
<button> 아이디 찾기 </button>
</form>
${none} <!-- 찾는 아이디가 없을 경우 alert창이 뜬다. -->
</body>
<script>
/* 개인,기업 선택 하지 않으면 넘어가지 않도록. */
/* 유효성 검사 나중 */
</script>
</html>