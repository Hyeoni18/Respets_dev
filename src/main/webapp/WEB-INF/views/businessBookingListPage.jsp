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
<div id="div_top"><jsp:include page="topBar.jsp"/></div>
	<h1>전체 예약 목록</h1>
	<form name="noticeListForm" class="form-inline">
		<input type='radio' name='radio' class='radio' value="전체" />전체
		${bctList}
		<div class="form-group mb-3">
			<label for="status-select" class="mr-2"> 검색&nbsp; 보호자<input
				type="search" class="form-control form-control-sm"
				placeholder="search" aria-controls="basic-datatable" name="per_name"
				id="per_name" />&nbsp; 동물<input type="search"
				class="form-control form-control-sm" placeholder="search"
				aria-controls="basic-datatable" name="pet_name" id="pet_name" /> <input
				type="button" onclick="opList()" class="btn" value="검색" />
			</label>
		</div>
	</form>
	<div id="list">${bokList}</div>
	<div id="page_navi">${paging}</div>
	<input type="hidden" id="page_index" class="page_index" />
</body>
<script>
		$('input[type="radio"]').click(function() {
		var radio = $("input[name='radio']:checked").val();
		console.log(radio);
		var per = $('#per_name').val();
		var pet = $('#pet_name').val();
		console.log(per + ' ' + pet);
			if (radio == '전체') {
				if(per == "" || pet == "" && per == null || pet == null){
					$.ajax({
						url : "businessAllBookingList?no=${no}&pageNum=",
						type : "post",
						dataType : "text",
						success : function(data) {
							$('#list').html(data);
						},
						error : function(error) {
							console.log(error);
						}
					});
				}
			}
			function opList(){
				if(radio == '전체') {
					if(per != "" || pet != "" || per !=null || pet != null){
						$.ajax({
							url : "searchAllList?no=${no}&per_name=" + per + "&pet_name=" + pet,
							type : 'post',
							dataType : "text",
							success : function(data) {
								$('#list').html(data);
							},
							error : function(error) {
								console.log("실패");
							}
						});
					}
				}
			}
			if (radio == '병원' || radio == '미용' || radio == '호텔') {
				if(per == "" || pet == "" && per == null || pet == null){
					$.ajax({
						url : "businessAllBctBookingList?no=${no}&bct_name=" + radio,
						type : "post",
						dataType : "text",
						success : function(data){
							$('#list').html(data);
						},
						error : function(error){
							console.log(error);
						}
					});
				}
			}
			function opList(){
				if(radio == '전체') {
					if(per != "" || pet != "" || per !=null || pet != null){
						$.ajax({
							url : "searchBctAllsList?no=${no}&bct_name=" + radio + "&per_name=" + per + "&pet_name=" + pet,
							type : 'post',
							dataType : "text",
							success : function(data) {
								$('#list').html(data);
							},
							error : function(error) {
								console.log(error);
							}
						}); //ajax end
					}
				}
			} //function end
		})
</script>
</html>