<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Respets :: 기업 회원 가입</title>
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
 
							<form name="joinForm" action="businessJoin" method="post" enctype="multipart/form-data"
								onsubmit="return formCheck();">


								<!-- 필수 입력 항목에 빨간 * 추가하기 -->
								<!-- 이메일 유효성 검사 추가할 것 -->
								<div class="form-group">
									<label for="bus_email">이메일 주소 <span style="color: red">*</span></label>
									<input type="text" class="form-control" name="bus_email" id="이메일"
										onchange="emailChk();" placeholder="ex)respets@respets.com"
										required >
									<div id="emailChkMsg"></div>
								</div>
								<!-- 비밀번호 유효성 검사, 확인 폼 추가할 것 -->
								<div class="form-group">
									<label for="bus_pw">비밀번호 <span style="color: red">*</span></label>
									<input type="password" class="form-control" name="bus_pw" id="비밀번호" placeholder="비밀번호를 입력해주세요." required >
								</div>
									
								<div class="form-group">
									<label for="bus_name">업체명 <span style="color: red">*</span></label>
									<input type="text" class="form-control" name="bus_name" id="업체명" placeholder="업체명을 입력해주세요." required>
								</div>
							
								<div class="form-group">
									<label for="bus_ceo">대표자명 <span style="color: red">*</span></label>
									<input type="text" class="form-control" name="bus_ceo" id="대표자명" placeholder="대표자명을 입력해주세요." required>
								</div>
								
								<div class="form-group">
									<label for="bus_lcno">사업자등록번호 <span style="color: red">*</span></label>
									<input type="text" class="form-control" name="bus_lcno" id="사업자 등록 번호" placeholder="ex)000-00-00000" onchange="taxIdChk();" required/>
									<div id="taxIdChkMsg"></div>
								</div>
								
								<div class="form-group">
									<label>사업자등록증 사진 <span style="color: red">*</span></label>
									<input type="file" class="form-control" name="busLicense" id="busLicense" />
								</div>
								
								<div class="form-group">
									<label for="bus_phone">업체 연락처 <span style="color: red">*</span></label>
									<input type="text" class="form-control" name="bus_phone" id="업체 연락처" />
								</div>
								<div class="form-row">								
									<div class="form-group col-md-12" style="margin-bottom:0;">										
										<label for="bus_address">업체 주소 <span style="color: red">*</span></label>
									</div>
									<div class="form-group col-md-6">
										<input type="text" class="form-control" name="bus_post" id="우편번호" placeholder="우편번호" readonly="readonly" />
									</div>
									<div class="form-group col-md-6">
										<input type="button" onclick="findAddr()" value="우편번호 찾기" class="btn btn-outline-success"/>
									</div>
								</div>
								
								<div class="form-group">
									<input type="text" class="form-control" name="bus_addr" id="주소" placeholder="주소" readonly="readonly" /> 
								</div>
								<div class="form-group">
									<input type="text" class="form-control" name="bus_addr2" id="상세주소" placeholder="상세주소" />
								</div>
				
								
								<div class="form-group">
									<label for="service">주력 서비스 <span style="color: red">*</span></label>
									<br />
									${input}
								</div>
								<div class="form-group">
									<label for="mainPhoto">대표 사진</label>
									<input type="file" class="form-control" name="mainPhoto" onchange="fileChk(this)" /> 
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


	<!-- footer start -->
	<footer class="footer footer-alt"> Copyright Respets Corp. All rights reserved. </footer>
	<!-- end footer -->


	<!-- App js -->
	<script src="/resources/dist/assets/js/app.min.js"></script>
	        <!-- third party js -->
        <script src="/resources/dist/assets/js/vendor/jquery-jvectormap-1.2.2.min.js"></script>
        <script src="/resources/dist/assets/js/vendor/jquery-jvectormap-world-mill-en.js"></script>
        <!-- third party js ends -->

        <!-- demo app -->
        <script src="/resources/dist/assets/js/pages/demo.dashboard.js"></script>
        <!-- end demo js-->
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

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

<!-- 폼 입력하지 않았을 때 제어하는 Script -->
<script>
	function formCheck() {
		var input = $('input[type!=file][type!=button][type!=submit][type!=radio]')
		for (var k = 0; k < input.length; k++) {
			if (input[k].value == "") {
				alert("'" + input[k].id + "'을(를) 입력해주세요.");
				return false;
			} // if End
		} // for End

		if ($(":file")[0].value == "") {
			alert("'사업자등록증 사진'을 등록해주세요.");
			return false;
		} // if End

		if ($(":radio[name='bct_code']:checked").length < 1) {
			alert("'주력 서비스'를 선택해주세요.");
			return false;
		} // if End
	} // fct End
</script>

<!-- 다음 API 사용을 위한 Script -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
	// 다음 주소 API 함수
	function findAddr() {
		new daum.Postcode(
				{
					oncomplete : function(data) {
						// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

						// 각 주소의 노출 규칙에 따라 주소를 조합한다.
						// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
						var fullAddr = ''; // 최종 주소 변수
						var extraAddr = ''; // 조합형 주소 변수

						// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
						if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
							fullAddr = data.roadAddress;

						} else { // 사용자가 지번 주소를 선택했을 경우(J)
							fullAddr = data.jibunAddress;
						}

						// 사용자가 선택한 주소가 도로명 타입일때 조합한다.
						if (data.userSelectedType === 'R') {
							// 법정 동명이 있을 경우 추가한다.
							if (data.bname !== '') {
								extraAddr += data.bname;
							}
							// 건물명이 있을 경우 추가한다.
							if (data.buildingName !== '') {
								extraAddr += (extraAddr !== '' ? ', '
										+ data.buildingName : data.buildingName);
							}
							// 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
							fullAddr += (extraAddr !== '' ? ' (' + extraAddr
									+ ')' : '');
						}

						// 우편번호와 주소 정보를 해당 필드에 넣는다.
						document.getElementById('우편번호').value = data.zonecode; //5자리 새우편번호 사용
						document.getElementById('주소').value = fullAddr;

						// 커서를 상세주소 필드로 이동한다.
						document.getElementById('상세주소').focus();
					}
				}).open();
	}
</script>

<!-- 사업자 등록 번호 사용 가능 여부를 확인하는 Ajax Script -->
<script>
	function taxIdChk() {
		var taxId = $('input[name=bus_lcno]').val();
		$.ajax({
			url : 'taxIdCheck',
			type : 'post',
			data : {
				'taxId' : taxId
			},
			success : function(data) {
				if (data == 0) {
					$('#taxIdChkMsg').html("<p style='color: blue'>사용 가능한 사업자등록번호입니다.</p>");
					$('#submit').attr('disabled', false);
				} else {
					$('#taxIdChkMsg').html("<p style='color: red'>사용 중인 사업자등록번호입니다.</p>");
					$('#submit').attr('disabled', true);
				}
			},
			error : function(error) {
				console.log(error);
			}
		});
	} // fct End
</script>

<!-- 이메일 사용 가능 여부를 확인하는 Ajax Script -->
<script>
	function emailChk() {
		var email = $('input[name=bus_email]').val();
		$.ajax({
			url : 'emailCheck',
			type : 'post',
			data : {
				'email' : email
			},
			success : function(data) {
				if (data == 0) {
					$('#emailChkMsg').html("<p style='color: blue'>사용 가능한 이메일입니다.</p>");
					$('#submit').attr('disabled', false);
				} else {
					$('#emailChkMsg').html("<p style='color: red'>사용 중인 이메일입니다.</p>");
					$('#submit').attr('disabled', true);
				}
			},
			error : function(error) {
				console.log(error);
			}
		});
	} // fct End
</script>

</html>