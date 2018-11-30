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
	<h1>비밀번호 수정 페이지</h1>
	<hr />
	<form name="myPwUpdateForm" method="post">
		<div>- 이메일 : ${mb.per_email}</div>
		<div>
			- 현재 비밀번호 <br /> <input type="password" name="nowPw" id="nowPw" /><br />
			- 새 비밀번호 <br /> <input type="password" name="newPw" id="newPw"
				onkeyup="pw_check();" /><br /> - 비밀번호 확인 <br /> <input
				type="password" name="newChkPw" id="newChkPw" onkeyup="pw_check();" />
			<div class="registrationFormAlert" id="same"></div>
		</div>
		<hr />
		<input type="button" id="pwOk" onclick="forward(this)" value="수정 완료"
			disabled /> <input type="reset" value="리셋" />
	</form>
</body>
<script>
	$('#nowPw').focusout(function() {
		var pw = ${mb.per_pw}
		var nowPw = $("#nowPw").val();
		if (pw != nowPw) {
			alert("현재 비밀번호가 틀립니다.");
			$('#pwOk').attr('disabled', 'disabled');
		} else if (nowPw == '') {
			alert("현재 비밀번호를 입력해주세요.");
			$('#pwOk').attr('disabled', 'disabled');
		} else {
			$('#pwOk').removeAttr("disabled");
		}
	});
	function pw_check() {
		var newPw = $("#newPw").val();
		var newChkPw = $('#newChkPw').val();
		if (newPw != newChkPw) {
			$("#same").html("비밀번호가 일치하지 않습니다!!").css('color', 'red');
			$('#pwOk').attr('disabled', 'disabled');
		}
		if (newPw == '' || newChkPw == '') {
			$("#same").html("** 비밀번호를 입력해주세요. **").css('color', 'red');
			$('#pwOk').attr('disabled', 'disabled');
		} else {
			$("#same").html("비밀번호가 일치합니다.").css('color', 'blue');
			$('#pwOk').removeAttr("disabled");
		}
	}
	function forward(button) {
		var frm = document.myPwUpdateForm;
		var newPw = $('#newPw').val();
		console.log(newPw);
		if (button.value == "수정 완료") {
			frm.action = "myPwCheck?newPw=" + newPw;
		}
		frm.submit();
	}
</script>
</html>