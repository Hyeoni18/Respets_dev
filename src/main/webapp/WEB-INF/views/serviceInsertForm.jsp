<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 업종 등록 신청</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/dist/assets/images/logo-sm.png">
<!-- third party css -->
<link
	href="resources/dist/assets/css/vendor/jquery-jvectormap-1.2.2.css"
	rel="stylesheet" type="text/css" />
<!-- third party css end -->

<!-- third party css -->
<link
	href="resources/dist/assets/css/vendor/jquery-jvectormap-1.2.2.css"
	rel="stylesheet" type="text/css" />
<!-- third party css end -->

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />
<style>
.custom-control {
    position: relative;
    display: inline;
    min-height: 1.3125rem;
    padding-left: 1.5rem;
}
#medical {
	display: none;
	width: 100%;
}
#beauty {
	display: none;
	width: 100%;
}
#hotel {
	display: none;
	width: 100%;
}
</style>

</head>
<body>

<div class="wrapper">
		<%@ include file="left-sidebar.jsp"%>
		<div class="content-page">
			<div class="content">
				<%@ include file="topbar-dashboard.jsp"%>

				<div class="container-fluid"><br/>
				
				<div class="row">
						<div class="col-12">
							<div class="page-title-box">
								<div class="page-title-right">
									<form class="form-inline"></form>
								</div>
								<h4 class="page-title">업종 등록 신청</h4>
							</div>
						</div>
					</div>

<div class="row">
<div class="col-12">					
<!-- <div class="col-xl-6"> -->
<div class="card">
<div class="card-body">
	 <ul class="nav nav-pills bg-light nav-justified mb-3">
	${codeCheck} <!--업종 선택 버튼-->
	 </ul>
	<!-- 병 원 폼 -->
	<div class="row" id="medical">
	<div class="col-12">
	<div class="card">
	<div class="card-body">
		<form action="serviceInsert" name="medical" method="post" enctype="multipart/form-data">
		<div class="row">
		<div class="col-lg-6">
			<input type="hidden" name="bct_code" value="M" /> 
			<input type="hidden" name="cnt" value="${cnt}" /> &nbsp; 
		</div></div>
			<div class="row">
	<div class="col-lg-6">
	<div class="form-group mb-3">
	<label for="simpleinput">업체명</label>
	<input type="text" class="form-control" name="bus_name" value="${bus_name}" readonly />
	</div>
	<div class="form-group mb-3">
	<label for="simpleinput">연락처</label>
	<input type="text" class="form-control" name="bus_phone" value="${bus_phone}" readonly />
	</div>
	<div class="form-group mb-3">
	<label for="example-fileinput" style="margin-right:15px;">사업장 사진</label>
	<input type="file" name="bgl_ori" multiple />
	</div>
	<div class="form-group mb-3">
	<label for="simpleinput">제공서비스</label><br/>
	${medical} </div> 
	<div class="form-group mb-3">
	<label for="simpleinput">서비스 가능한 동물종류</label><br/>
	<div class='custom-control custom-checkbox'>
	<input type='checkbox' class='custom-control-input checkbox' name='animal_code' value='소형견' id='소형견'/> <label class='custom-control-label' for='소형견'>소형견</label>
	</div>
	<div class='custom-control custom-checkbox'>
	<input type='checkbox' class='custom-control-input checkbox' name='animal_code' value='중형견' id='중형견'/> <label class='custom-control-label' for='중형견'>중형견</label>
	</div>
	<div class='custom-control custom-checkbox'>
	<input type='checkbox' class='custom-control-input checkbox' name='animal_code' value='대형견' id='대형견'/> <label class='custom-control-label' for='대형견'>대형견</label>
	</div>
	<div class='custom-control custom-checkbox'>
	<input type='checkbox' class='custom-control-input checkbox' name='cat_code' value='고양이' id='고양이'/> <label class='custom-control-label' for='고양이'>고양이</label>
	</div>
	<div class='custom-control custom-checkbox'>
	<input type='checkbox' class='custom-control-input checkbox' name='animal_code' value='소동물' id='소동물'/> <label class='custom-control-label' for='소동물'>소동물</label>
	</div>
	<div class='custom-control custom-checkbox'>
	<input type='checkbox' class='custom-control-input checkbox' name='animal_code' value='파충류' id='파충류'/> <label class='custom-control-label' for='파충류'>파충류</label>
	</div>
	<div class='custom-control custom-checkbox'>
	<input type='checkbox' class='custom-control-input checkbox' name='animal_code' value='가축' id='가축'/> <label class='custom-control-label' for='가축'>가축</label>
	</div>
	</div>
	</div>
	<div class="col-lg-6">
	<div class="form-group mb-3">
	<label for="simpleinput">영업 오픈시간</label>
	<input type="time" class="form-control" name="work_o" id="am_open" step="1800">
	</div>
	<div class="form-group mb-3">
	<label for="simpleinput">영업 마감시간</label>
	<input type="time" class="form-control" name="work_c" id="pm_close" step="1800">
	</div>
  	<div class="form-group mb-3">
	<label for="simpleinput">점심 시작시간</label>
	<input type="time" class="form-control" name=lunch_o id="lunch_open" step="1800">
	</div>
	<div class="form-group mb-3">
	<label for="simpleinput">점심 마감시간</label>
	<input type="time" class="form-control" name="lunch_c" id="lunch_close" step="1800">
	</div>
	<div class="form-group mb-3">
	<label for="simpleinput">고정휴무일</label><br/>
	${holiday} </div> </div>
	 안내- 등록신청 시 등록완료까지 인증시간이 소요됩니다. <br /> &nbsp;
	<div class="col-lg-12">
	<button class="btn btn-outline-secondary">등록 신청</button>
	<input type="reset" class="btn btn-outline-danger" value="취소" />
	</div></div>
	</form>
	</div>
	</div>
	</div>
	</div>

	<!-- 미 용 폼 -->
	<div class="row" id="beauty">
	<div class="col-12">
	<div class="card">
	<div class="card-body">
		<form action="serviceInsert" name="beauty" method="post" enctype="multipart/form-data">
		<div class="row">
		<div class="col-lg-6">
			<input type="hidden" name="bct_code" value="B" /> 
			<input type="hidden" name="cnt" value="${cnt}" /> &nbsp; 
		</div></div>
	 <div class="row">
	 <div class="col-lg-6">
	 <div class="form-group mb-3">
	 <label for="simpleinput">업체명</label>
	 <input type="text" class="form-control" name="bus_name" value="${bus_name}" readonly />
	 </div>
	 <div class="form-group mb-3">
	 <label for="simpleinput">연락처</label>
	 <input type="text" class="form-control" name="bus_phone" value="${bus_phone}" readonly />
	 </div>
	 <div class="form-group mb-3">
	 <label for="example-fileinput" style="margin-right:15px;">사업장 사진</label>
	 <input type="file" name="bgl_ori" multiple />
	 </div>
	 <div class="form-group mb-3">
	 <label for="simpleinput">서비스 가능한 동물종류</label><br/>
	 <div class='custom-control custom-checkbox'>
 	 <input type='checkbox' class='custom-control-input checkbox checkbox' name='animal_code' value='소형견' id='소형견_b'/> <label class='custom-control-label' for='소형견_b'>소형견</label>
	 </div>
	 <div class='custom-control custom-checkbox'>
 	 <input type='checkbox' class='custom-control-input checkbox checkbox' name='animal_code' value='중형견' id='중형견_b'/> <label class='custom-control-label' for='중형견_b'>중형견</label>
	 </div>
	 <div class='custom-control custom-checkbox'>
 	 <input type='checkbox' class='custom-control-input checkbox checkbox' name='animal_code' value='대형견' id='대형견_b'/> <label class='custom-control-label' for='대형견_b'>대형견</label>
	 </div>
	 <div class='custom-control custom-checkbox'>
 	 <input type='checkbox' class='custom-control-input checkbox checkbox' name='cat_code' value='고양이' id='고양이_b'/> <label class='custom-control-label' for='고양이_b'>고양이</label>
	 </div>
     </div>
     <div class="form-group mb-3">
	 <label for="simpleinput">제공서비스</label><br/>
	  ${beauty} </div> 
	 </div>
	 <div class="col-lg-6">
	 <div class="form-group mb-3">
	 <label for="simpleinput">영업 오픈시간</label>
	 <input type="time" class="form-control" name="work_o" id="am_open" step="1800">
	 </div>
	 <div class="form-group mb-3">
	 <label for="simpleinput">영업 마감시간</label>
	 <input type="time" class="form-control" name="work_c" id="pm_close" step="1800">
	 </div>
	 <div class="form-group mb-3">
	 <label for="simpleinput">점심 시작시간</label>
	 <input type="time" class="form-control" name=lunch_o id="lunch_open" step="1800">
	 </div>
	 <div class="form-group mb-3">
	 <label for="simpleinput">점심 마감시간</label>
	 <input type="time" class="form-control" name="lunch_c" id="lunch_close" step="1800">
	 </div>
	 <div class="form-group mb-3">
     <label for="simpleinput">고정휴무일</label><br/>
	 <div class='custom-control custom-checkbox'> 
	 <input type="checkbox" class='custom-control-input' name="holiday" value="월요일" id='월요일_b'/> <label class='custom-control-label' for='월요일_b'>월요일</label>
	 </div>
	 <div class='custom-control custom-checkbox'> 
	 <input type="checkbox" class='custom-control-input' name="holiday" value="화요일" id='화요일_b'/> <label class='custom-control-label' for='화요일_b'>화요일</label> 
	 </div>
	 <div class='custom-control custom-checkbox'> 
	 <input type="checkbox" class='custom-control-input' name="holiday" value="수요일" id='수요일_b'/> <label class='custom-control-label' for='수요일_b'>수요일</label> 
	 </div>
	 <div class='custom-control custom-checkbox'> 
	 <input type="checkbox" class='custom-control-input' name="holiday" value="목요일" id='목요일_b'/> <label class='custom-control-label' for='목요일_b'>목요일</label> 
	 </div>
	 <div class='custom-control custom-checkbox'> 
	 <input type="checkbox" class='custom-control-input' name="holiday" value="금요일" id='금요일_b'/> <label class='custom-control-label' for='금요일_b'>금요일</label> 
	 </div>
	 <div class='custom-control custom-checkbox'> 
	 <input type="checkbox" class='custom-control-input' name="holiday" value="토요일" id='토요일_b'/> <label class='custom-control-label' for='토요일_b'>토요일</label> 
	 </div>
	 <div class='custom-control custom-checkbox'> 
	 <input type="checkbox" class='custom-control-input' name="holiday" value="일요일" id='일요일_b'/> <label class='custom-control-label' for='일요일_b'>일요일</label> 
	 </div>
	 <div class='custom-control custom-checkbox'> 
	 <input type="checkbox" class='custom-control-input' name="holiday" value="공휴일" id='공휴일_b'/> <label class='custom-control-label' for='공휴일_b'>공휴일</label> 
	 </div>
	 </div> 
	 </div>
	 <div class="col-lg-12">
	 <button type="button" class="btn btn-outline-secondary" name="B" onclick="priceBox(this)">가격 등록 </button>&nbsp;
	 <input type="reset" class="btn btn-outline-danger" value="취소" onclick="cancel()"/>
	 <br/><br/>
	 <div id="B"></div> <!--서비스 가격 찍히는 div-->
	 <div id="B_price"></div> <!--고양이 서비스 가격 찍히는 div-->
	 <div id="but"></div>
	 </div>
	 </div>
	</form>
	</div>
	</div>
	</div>
	</div>

	<!-- 호 텔 폼 -->
	<div class="row" id="hotel">
	<div class="col-12">
	<div class="card">
	<div class="card-body">
		<form action="serviceInsert" name="hotel" method="post" enctype="multipart/form-data">
		<div class="row">
		<div class="col-lg-6">
			<input type="hidden" name="bct_code" value="H" /> 
			<input type="hidden" name="cnt" value="${cnt}" /> &nbsp; 
		</div></div>
	  <div class="row">
	  <div class="col-lg-6">
	  <div class="form-group mb-3">
	  <label for="simpleinput">업체명</label>
	  <input type="text" class="form-control" name="bus_name" value="${bus_name}" readonly />
	  </div>
	  <div class="form-group mb-3">
	  <label for="simpleinput">연락처</label>
	  <input type="text" class="form-control" name="bus_phone" value="${bus_phone}" readonly />
	  </div>
	  <div class="form-group mb-3">
	  <label for="example-fileinput" style="margin-right:15px;">사업장 사진</label>
	  <input type="file" name="bgl_ori" multiple />
	  </div>
	  <div class="form-group mb-3">
	  <label for="simpleinput">서비스 가능한 동물종류</label><br/>
	  <div class='custom-control custom-checkbox'>
 	  <input type='checkbox' class='custom-control-input checkbox checkbox' name='animal_code' value='소형견' id='소형견_h'/> <label class='custom-control-label' for='소형견_h'>소형견</label>
	  </div>
	  <div class='custom-control custom-checkbox'>
 	  <input type='checkbox' class='custom-control-input checkbox checkbox' name='animal_code' value='중형견' id='중형견_h'/> <label class='custom-control-label' for='중형견_h'>중형견</label>
	  </div>
	  <div class='custom-control custom-checkbox'>
 	  <input type='checkbox' class='custom-control-input checkbox checkbox' name='animal_code' value='대형견' id='대형견_h'/> <label class='custom-control-label' for='대형견_h'>대형견</label>
	  </div>
	  <div class='custom-control custom-checkbox'>
 	  <input type='checkbox' class='custom-control-input checkbox checkbox' name='cat_code' value='고양이' id='고양이_h'/> <label class='custom-control-label' for='고양이_h'>고양이</label>
	  </div>
	  <div class='custom-control custom-checkbox'>
 	  <input type='checkbox' class='custom-control-input checkbox checkbox' name='animal_code' value='소동물' id='소동물_h'/> <label class='custom-control-label' for='소동물_h'>소동물</label>
	  </div>
      </div> 
	  <div class="form-group mb-3">
 	  <label for="simpleinput">제공서비스</label><br/>
	  ${hotel} </div> 
	  </div>
	  <div class="col-lg-6">
	  <div class="form-group mb-3">
	  <label for="simpleinput">영업 오픈시간</label>
	  <input type="time" class="form-control" name="work_o" id="am_open" step="1800">
 	  </div>
	  <div class="form-group mb-3">
	  <label for="simpleinput">영업 마감시간</label>
	  <input type="time" class="form-control" name="work_c" id="pm_close" step="1800">
	  </div>
	  <div class="form-group mb-3">
	  <label for="simpleinput">점심 시작시간</label>
	  <input type="time" class="form-control" name=lunch_o id="lunch_open" step="1800">
	  </div>
	  <div class="form-group mb-3">
	  <label for="simpleinput">점심 마감시간</label>
	  <input type="time" class="form-control" name="lunch_c" id="lunch_close" step="1800">
	  </div>
	  <div class="form-group mb-3">
      <label for="simpleinput">고정휴무일</label><br/>
	  <div class='custom-control custom-checkbox'> 
	  <input type="checkbox" class='custom-control-input' name="holiday" value="월요일" id='월요일_h'/> <label class='custom-control-label' for='월요일_h'>월요일</label>
	  </div>
  	  <div class='custom-control custom-checkbox'> 
	  <input type="checkbox" class='custom-control-input' name="holiday" value="화요일" id='화요일_h'/> <label class='custom-control-label' for='화요일_h'>화요일</label> 
	  </div>
	  <div class='custom-control custom-checkbox'> 
	  <input type="checkbox" class='custom-control-input' name="holiday" value="수요일" id='수요일_h'/> <label class='custom-control-label' for='수요일_h'>수요일</label> 
	  </div>
	  <div class='custom-control custom-checkbox'> 
	  <input type="checkbox" class='custom-control-input' name="holiday" value="목요일" id='목요일_h'/> <label class='custom-control-label' for='목요일_h'>목요일</label> 
	  </div>
	  <div class='custom-control custom-checkbox'> 
	  <input type="checkbox" class='custom-control-input' name="holiday" value="금요일" id='금요일_h'/> <label class='custom-control-label' for='금요일_h'>금요일</label> 
	  </div>
	  <div class='custom-control custom-checkbox'> 
	  <input type="checkbox" class='custom-control-input' name="holiday" value="토요일" id='토요일_h'/> <label class='custom-control-label' for='토요일_h'>토요일</label> 
	  </div>
	  <div class='custom-control custom-checkbox'> 
	  <input type="checkbox" class='custom-control-input' name="holiday" value="일요일" id='일요일_h'/> <label class='custom-control-label' for='일요일_h'>일요일</label> 
	  </div>
	  <div class='custom-control custom-checkbox'> 
	  <input type="checkbox" class='custom-control-input' name="holiday" value="공휴일" id='공휴일_h'/> <label class='custom-control-label' for='공휴일_h'>공휴일</label> 
	  </div>
	  </div> 
	  </div>
	  <div class="col-lg-12">
	  <button type="button" class="btn btn-outline-secondary" name="H" onclick="priceBox(this)"> 가격 등록 </button>&nbsp;
	  <input type="reset" class="btn btn-outline-danger" value="취소" onclick="cancel()"/>
	  <br/><br/>
	  <div id="H"></div> <!--서비스 가격 찍히는 div  -->
	  <div id="H_price"></div> <!--고양이 서비스 가격 찍히는 div-->
	  <div id="but"></div></div></div>
		</form>
	</div>
	</div> <!-- end card-body-->
	</div> <!-- end card-->
	</div> <!-- end col-xl-6 -->
</div></div></div></div></div>
<%@ include file="footer.html"%>
	</div></div></div>
	<!-- App js -->
    <script src="/resources/dist/assets/js/app.min.js"></script>
</body>

<script>
function cancel() {
	$("#H *").remove();
	$("#B *").remove();
	$("#H_price *").remove();
	$("#B_price *").remove();
	$("#but *").remove();
}
function checkCode(code) {
	if (code.value=='M' || code=='M') {
		$(medical).show();
		$(beauty).hide();
		$(hotel).hide();
	} else if (code.value=='B' || code=='B') {
		$(medical).hide();
		$(beauty).show();
		$(hotel).hide();
	} else if (code.value=='H' || code=='H') {
		$(medical).hide();
		$(beauty).hide();
		$(hotel).show();
	}
	$(':checkbox').prop('checked', false) ;
	$("#H *").remove();
	$("#B *").remove();
	$("#H_price *").remove();
	$("#B_price *").remove();
	$("#but *").remove();
}

function priceBox(cnt) {
	var code = $(cnt).attr('name');
	var values = document.getElementsByName("tag_name");
	var animal = document.getElementsByName("animal_code");
	var cat_code = document.getElementsByName("cat_code"); 
	var cat_tag_ck = document.getElementsByName("cat_tag");
	var dog_tag_ck = document.getElementsByName("dog_tag");
	var tagName = new Array(); //태그 값 담기는 배열 
	var aniName = new Array(); //동물 종류 담기는 배열 
	var catName = new Array(); //고양이가 담기는 변수
	var cat_tag = new Array(); //고양이만 해당하는 무마취 서비스 태그
	var dog_tag = new Array(); //강아지만 해당하는 가위컷 서비스 태그
	//catName, cat_tag, dog_tag를 나눠준 이유는 제공되는 서비스가 다르기 때문에 구분을 지어줘야한다.
	var tag_cnt=0; //태그 개수 
	var ani_cnt=0; //동물 개수 
	var cat_cnt=0; //고양이 유무 
	var cat_tag_cnt=0; //무마취 태그의 유무 
	var dog_tag_cnt=0; //가위컷 태그의 유무
	//values, animal은 2개 이상일 수 있기에, 반복문을 통해 위에서 만들어놓은 배열에 값을 담아준다.
	for(var i=0; i<values.length; i++) { //태그네임 들어온 값 만큼 돌기 
		if(values[i].checked) { //체크된 값 확인 
			tagName[tag_cnt] = values[i].value; //체크된 값은 배열에 넣어주기 
			tag_cnt++; //태그 개수 카운트 올려주기
		} //체크된 박스 확인 if End
	} //태그네임 새로 담는 for End 
	for(var i=0; i<animal.length; i++) { //애니멀코드 들어온 값 만큼 돌기 
		if(animal[i].checked) { //체크된 값 확인 
			aniName[ani_cnt] = animal[i].value; //체크된 값은 배열에 넣어주기 
			ani_cnt++; //애니멀 개수 카운트 올려주기 
		} //체크된 박스 확인 if End 
	} //애니멀코드 새로 담는 for End 
	for(var i=0; i<cat_code.length; i++) { 
		if(cat_code[i].checked) { 
			catName[cat_cnt] = cat_code[i].value;
			cat_cnt++; //동물종류 중 고양이가 들어왔을 경우 cnt를 증가시켜 유무를 표시한다.
		} 
	} 
	for(var i=0; i<cat_tag_ck.length; i++) {
		if(cat_tag_ck[i].checked) {
			cat_tag[cat_tag_cnt] = cat_tag_ck[i].value;
			cat_tag_cnt++; //고양이무마취 태그가 들어왔을 경우 cnt를 증가시켜 유무를 표시한다.
		}
	}
	for(var i=0; i<dog_tag_ck.length; i++) {
		if(dog_tag_ck[i].checked) {
			dog_tag[dog_tag_cnt] = dog_tag_ck[i].value;
			dog_tag_cnt++; //가위컷 태그가 들어왔을 경우 cnt를 증가시켜 유무를 표시한다.
		}
	}
	
	var trObj; //tr태그 생성에 쓰일 변수
    var div; 
 		div = $("#"+code); //id가 넘어온 업종코드로 이루어진 div를 찾는다.
		$("#"+code+" *").remove(); //만약 해당 div에 append 된 것이 있다면 지워줘.
		var cat_div = $("#"+code+"_price");
		$("#"+code+"_price *").remove();
		var but = $("#but");
		$("#but *").remove();
		var num1=aniName.length; //num1에 애니멀 개수를 넣어줘.
		var num2=tagName.length; //num2에 서비스 개수를 넣어줘. 
		
		var inputTag; //input태그 생성에 쓰일 변수 
		
		var box = new Array(); //선택된 서비스를 한곳에 저장하는 배열
		var m=0; //배열의 위치를 잡아주기 위한 cnt 변수 
		
		if(aniName.length != 0) { //동물태그가 존재하는지 확인 후 
			if(dog_tag[0] != null){ //가위컷 태그가 있을시 
				box[m]=dog_tag[0]; //배열에 넣어주고 
				m++; //box의 배열순서를 증가시켜 다음 값을 받을 수 있도록 준비해준다.
			} 
			//그 외 서비스 태그가 있을시 반복문을 통해 box배열에 전부 넣어준다.
			for(var i=0; i<num2; i++) { 
				box[m]=tagName[i];
				m++;
			}
		}
		var num3=box.length; //넘어온 서비스의 개수를 담아준다.
		
		for(var i=0; i<num1+1; i++){ // 애니멀 개수만큼 돈다.
		     trObj=document.createElement("tr");     // tr 생성      
		     for(var j=0; j<num3+1; j++){ //서비스 개수만큼 돈다.
			      var tdObj=document.createElement("td");  //td 생성 
			      trObj.appendChild(tdObj); //td를 tr에 붙여준다.
			      if(i==0) { 
			    	  if(j==0) { //(0,0) 은 빈칸이여야 한다.
			    		  tdObj.innerHTML = " ";	    		  
			    	  } else { //그게 0이 아니라면 차례대로 서비스를 넣어준다. (테이블의 맨 윗 줄)
			    		  tdObj.innerHTML = box[j-1];
			    	  }
			      } //if(i==0) End 
		  	   else {
		    	  if(j==0) { //테이블의 가장 왼쪽 줄은 동물들의 이름이 들어간다.
		    		  tdObj.innerHTML = aniName[i-1];
		    	  } else { //그 외에는 가격 창이 만들어진다.
		    		  inputTag = document.createElement("input");
		    		  inputTag.setAttribute("type", "text");
		    		  inputTag.setAttribute("name", "price");
		    		  inputTag.setAttribute("class", "col-sm-6");
		    		  var price;
		    		  //가격을 수정하는 경우, 이전의 가격을 불러와야 하기 때문에 ajax를 통해 DB를 검색해온다.
		    		  var url = "searchPrice?tag_name="+box[j-1]+"&ani_name="+aniName[i-1]+"&bct_code="+code;
		    			Aj(url);
		    			function Aj(url) { 
		    				$.ajax({
		    					url: url,
		    					type: "post",
		    					dataType: "text", 
		    					async: false, 
		    					//비동기 ajax를 동기화 시킨 이유는, 비동기시 값을 가지러 서버에 다녀올 동안 input태그가 먼저 만들어진다.
		    					//그럴 경우, 값이 존재하지 않는 price를 가지고 value 값을 취한 태그는 오류가 나고 원하는 정보를 보여주지 못한다.
		    					success: function(data) {
		    						price = data; //service 단에서 가져온 값을 price에 담아준다.
		    					},
		    					error: function(error) {
		    						console.log("error");
		    					}
		    				}); //ajax End
		    			} 
		    		  inputTag.setAttribute("value", price); 
		    		  tdObj.appendChild(inputTag);
		    	  }
		      } //if (i!=0) End 	      	          
		    } //for End (td)    
		    div.append(trObj);    //div 태그에 tr태그를 붙여넣는다.
	    } //for End (tr)
		
		var catBox = new Array();
		var o=0;
				
		if(catName.length != 0) {
			if(cat_tag[0] != null){
				catBox[o]=cat_tag[0];
				o++;
				console.log("고양이태그");
			} 
			for(var i=0; i<num2; i++) {
				catBox[o]=tagName[i];
				o++;
			}
		}
		var num4 = catBox.length;
		
		for(var i=0; i<catName.length+1; i++){ // 애니멀 개수만큼 돌거야.
		     trObj=document.createElement("tr");     // tr 생성      
		     for(var j=0; j<num4+1; j++){ //서비스 개수만큼 돌거야. 
			      var tdObj=document.createElement("td");  //td 생성 
			      trObj.appendChild(tdObj); //td를 tr에 붙여줘. 
			      if(i==0) { 
			    	  if(j==0) { //(0,0) 은 빈칸이여야해. 
			    		  tdObj.innerHTML = " ";	    		  
			    	  } else {
			    		  tdObj.innerHTML = catBox[j-1];
			    	  }
			      } //if(i==0) End 
		  	   else {
		    	  if(j==0) {
		    		  tdObj.innerHTML = catName[i-1];
		    	  } else {
		    		  inputTag = document.createElement("input");
		    		  inputTag.setAttribute("type", "text");
		    		  inputTag.setAttribute("name", "price");
		    		  inputTag.setAttribute("class", "col-sm-6");
		    		  var price;
		    		  var url = "searchPrice?tag_name="+catBox[j-1]+"&ani_name="+catName[i-1]+"&bct_code="+code;
		    			Aj(url);
		    			function Aj(url) { 
		    				$.ajax({
		    					url: url,
		    					type: "post",
		    					dataType: "text", 
		    					async: false,
		    					success: function(data) {
		    						price = data;
		    					},
		    					error: function(error) {
		    						console.log("error");
		    					}
		    				}); //ajax End
		    			} 
		    		  inputTag.setAttribute("value", price);
		    		  tdObj.appendChild(inputTag);
		    	  }
		      } //if (i!=0) End 	      	          
		    } //for End (td)    
		    cat_div.append(trObj);    //div 태그에 tr태그를 붙여넣는다.
	    } //for End (tr)
 //	}//beauty else if End
 var para = document.createElement("P");  
 var noti = document.createTextNode("제공을 원하지 않는 서비스에는 'X'를 입력해주세요.");
 para.appendChild(noti);
 cat_div.append(para);
 var btn = document.createElement("BUTTON");
 btn.setAttribute("class", "btn btn-outline-secondary")
 var t = document.createTextNode("등록신청"); 
 btn.appendChild(t);	
 cat_div.append(btn);
 
}	
</script>
</html>