<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Respets :: 개인 회원 가입</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description" />
        <meta content="Coderthemes" name="author" />
        <!-- App favicon -->
        <link rel="shortcut icon" href="resources/dist/assets/images/logo-sm.png">

        <!-- App css -->
        <link href="resources/dist/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="resources/dist/assets/css/app.min.css" rel="stylesheet" type="text/css" />

    </head>
  <body class="authentication-bg">

        <div class="account-pages mt-5 mb-5">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-5">
                        <div class="card">
                            <!-- Logo-->
                            <div class="card-header pt-4 pb-4 text-center bg-success">
                                <a href="./">
                                    <span><img src="resources/dist/assets/images/logo-white.png" alt="리스펫츠 로고" height="30"></span>
                                </a>
                            </div>

                            <div class="card-body p-4">
                                
                                <div class="text-center w-75 m-auto">
                                    <h4 class="text-dark-50 text-center mt-0 font-weight-bold">Join us!</h4>
                                    <p class="text-muted mb-4">Respets에 계정을 등록하고 편리한 서비스를 이용해보세요. </p>
                                </div>

                                	<form action="personalJoin" name="personalJoin" id="personalJoin" onsubmit="return formChk();" method="post" 
									enctype="multipart/form-data">

                                    <div class="form-group">
                                        <label for="email">이메일 주소 <span style="color: red">*</span></label>
                                        <input class="form-control" type="text" id="per_email" name="per_email" onchange="emailChk()" placeholder="ex)respets@respets.com" required/>
                                    	<div id="chkMsg"></div>
                                    </div>

                                    <div class="form-group">
                                        <label for="pw">비밀번호 <span style="color: red">*</span></label>
                                        <input class="form-control" type="password" id="per_pw" name="per_pw" required placeholder="비밀번호를 입력해주세요.">
                                    </div>

                                    <div class="form-group">
                                        <label for="password">비밀번호 확인 <span style="color: red">*</span></label>
                                        <input class="form-control" type="password" required id="pwChk" name="pwChk" placeholder="비밀번호를 재입력해주세요.">
                                        		<div id="chkSame" style="color:blue">비밀번호가 일치합니다.</div>
												<div id="chkAlert" style="color:red">비밀번호가 일치하지 않습니다.</div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label for="name">이름 <span style="color: red">*</span></label>
                                        <input class="form-control" type="text" id="per_name" name="per_name" placeholder="이름을 입력해주세요." required>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label for="phone">연락처 <span style="color: red">*</span></label>
                                        <input class="form-control" type="text" id="per_phone" name="per_phone" placeholder="연락처를 입력해주세요(010-0000-0000)" required>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label for="file">프로필 사진(선택사항)</label>
                                        <input class="form-control" type="file" id="photo" name="photo" onchange="fileChk(this);">
                                        <input type="hidden" name="fileCheck" id="fileCheck" value="0" />
                                    </div>

                                    <div class="form-group mb-0 text-center">
                                        <button class="btn btn-success" id="submit" type="submit"> 가입하기 </button>
                                    </div>
                                </form>

                       	 <div class="row mt-3">
                            <div class="col-12 text-center">
                                <p class="text-muted">계정이 이미 있으신가요? <a href="./loginForm" class="text-dark ml-1"><b>로그인 하기</b></a></p>
                            </div> <!-- end col-->
                        </div>
                        
                        <!-- end row -->
                            </div> <!-- end card-body -->
                        </div>
                        <!-- end card -->

                    </div> <!-- end col -->
                </div>
                <!-- end row -->
            </div>
            <!-- end container -->
        </div>
        <!-- end page -->

        <footer class="footer footer-alt"> Copyright Respets Corp. All rights reserved. </footer>

        <!-- App js -->
        <script src="/resources/dist/assets/js/app.min.js"></script>
	
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(function(){
	$('#chkSame').hide();
	$('#chkAlert').hide();
	$('#pwChk').keyup(function(){
		var pw = $('#per_pw').val();
		var pwChk = $('#pwChk').val();
		
		if(pw == "" || pwChk== "") {
			$('#chkAlert').hide();
		} else{
			if(pw == pwChk) {
				$('#chkSame').show();
				$('#chkAlert').hide();
				$('#submit').removeAttr("disabled");
			}else {
				$('#chkSame').hide();
				$('#chkAlert').show();
				$('#submit').attr('disabled', 'disabled');
			}
		}
	});
});
</script>

<script>
function emailChk() {
	var email = $('#per_email').val();
	console.log(email);
	$.ajax({
		url:'emailCheck',
		type: 'post',
		data: {email : email},
		success:function(data) {
			console.log(data);
			if(data != 1) {
				$('#chkMsg').html("<p style='COLOR:blue'>사용 가능한 이메일입니다.</p>");
			}else {
				$('#chkMsg').html("<p style='COLOR:red'>이미 사용 중인 이메일입니다.</p>");
				$('#submit').attr('disable', 'disabled');
			}
		},
		error:function(error) {
			console.log(error);
		}
	});
};
</script>

<script>
function formChk() {
	var email = $('#per_email');
	var pw = $('#per_pw');
	var pwChk = $('#pwChk');
	var name = $('#per_name');
	var phone = $('#per_phone');
	
	if(email.val() == "" || email.val() == null ) {
		alert("이메일 주소를 입력해주세요.");
		email.focus();
		return false;
	} else if(pw.val() == "" || pw.val() == null) {
		alert("비밀번호를 입력해주세요.");
		pw.focus();
		return false;
	} else if(pwChk.val()=="" || pwChk.val() == null) {
		alert("비밀번호 확인을 입력해주세요.");
		pwChk.focus();
		return false;
	} else if(pwChk.val() != pw.val()) {
		alert("비밀번호 확인이 일치하지 않습니다.");
		pwChk.focus();
		return false;
	} else if(name.val() == "" || name.val() == null) {
		alert("이름을 입력해주세요.");
		name.focus();
		return false;
	} else if(phone.val() == "" || phone.val() == null){
		alert("전화번를 입력해주세요.");
		phone.focus();
		return false;
	} else {
		return true;
	}
}
</script>
<!-- 대표 사진 체크 Script -->
<script>
function fileChk(file) {
	if (file.value == "") {
		$("#fileCheck").val(0);
	} else {
		$("#fileCheck").val(1);
		} // else End
	} // fct End
</script>

<!-- <script>
function fileChk(file) {
	/* if(file.length>1){
		alert("하나의 파일만 첨부해주세요");
		return;
	} */
	console.log(file.value);
	if (file.value == "") {
		console.log("empty");
		$("#fileCheck").val(0); //파일 첨부 안했음
	} else {
		console.log("not empty");
		$("#fileCheck").val(1); //파일 첨부 했음						
	}
}
</script> -->
</html>