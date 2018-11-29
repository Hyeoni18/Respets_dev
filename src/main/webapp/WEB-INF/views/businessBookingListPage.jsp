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
	<div id="list"></div>
	<div id="page_navi"></div>
	<input type="hidden" id="page_index" class="page_index" />
</body>
<script>
		$('input[type="radio"]').click(function() {
		var radio = $("input[name='radio']:checked").val();
		console.log(radio);
		var per = $('#per_name').val();
		var pet = $('#pet_name').val();
		console.log(per + ' ' + pet);
		$result['paging'] = array(
				'startPage' => $startPage, //시작페이지
				'endPage' => $endPage, //종료페이지
				'totalBlock' => $totalBlock, //전체페이지 블럭 수
				'totalPage' => $totalPage, //전체 페이지 수
				'blockPageNum' => $blockPageNum, //한 페이지에 나올 블럭 수
				'rowPage' => $rowPage, //한 페이지에 나올 리스트 수
				'totalCount' => $totalCount, //전체 리스트 수
				'block' => $block, //현재 페이지가 어느 블럭에 포함되어있는지
				'page' => $page //현재 페이지
				);
		var page = 1;
		var searchKey = "";
			if (radio == '전체') {
				if(per == "" || pet == "" && per == null || pet == null){
					$.ajax({
						url : "businessAllBookingList?no=${no}",
						type : "post",
						dataType : "text",
						success : function(data) {
							$('#list').html(data);
							paging = result.paging;
							
							if(paging.page != 1){            // 페이지가 1페이지 가아니면
						       	$(".pagination").append("<li class=\"goFirstPage\"><a><<</a></li>");        //첫페이지로가는버튼 활성화
						    }else{
						       	$(".pagination").append("<li class=\"disabled\"><a><<</a></li>");        //첫페이지로가는버튼 비활성화
						    }
							
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