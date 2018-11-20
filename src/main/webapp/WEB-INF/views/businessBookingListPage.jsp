<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>전체 예약 목록</h1>
	<form action="businessBookingListSearch" name="noticeListForm"
		class="form-inline">
		<div class="form-group mb-3">
			<label for="status-select" class="mr-2"> <!-- 카테고리&nbsp; -->
				<select class="custom-select" name="abc_name" id="abc_name">
					<option>보호자</option>
					<option>동물</option>
			</select>`
			</label>
		</div>
		<div class="form-group mb-3">
			<label for="status-select" class="mr-2"> <!-- 검색&nbsp; --> <input
				type="search" class="form-control form-control-sm"
				placeholder="search" aria-controls="basic-datatable" name="search">
				<button type="submit" class="btn btn-success btn-sm">검색</button>
			</label>
		</div>
	</form>
	<div id="list">${toSdList}</div>
	<div>${paging}</div>
</body>
<script>
	$(document).ready(function() {
		//불러온 값에 selected속성 부여하기
		$("#abc_name option").each(function() {
			if ($(this).val() == "${abc_name}")
				$(this).attr("selected", "selected");
		})
	});
</script>
</html>