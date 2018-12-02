<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html auto-config="true">
<head>
<meta charset="utf-8" />
<title>Respets :: 기업 공지사항</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/dist/assets/images/logo-sm.png">

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">

</style>
<style>
#div_menu {
width: 15%;
float:left;
text-align: center;
}
#div_content {
width: 85%;
float:right;
}
</style>
</head>
<body>
<div id="div_top"><jsp:include page="topBar.jsp"/></div>
<div id="div_menu"><jsp:include page="businessButtonPage.jsp"/></div>
<div id="div_content">
	<div class="page-content">
		<div class="card">
			<div class="card-body">
				<div class="p-lg-1"></div>

				<div class="p-lg-1">
					<div class="example-container">
						<div class="row">
							<div class="col-xl-12">
								<div class="card mb-0">
									<div class="card-body">
										<h3 class="header-title">공지사항 관리</h3>
										<p class="text-muted font-14 mb-4">내 서비스의 공지사항을 관리하세요.</p>
										<div class="table-responsive-sm">
											<table class="table table-centered mb-0">
												<thead>
													<tr>
														<td>게시글번호</td>
														<td>업종</td>
														<td>카테고리</td>
														<td>제목</td>
														<td>작성일</td>
														<td>수정/삭제</td>
													</tr>
													${nList}
												</thead>
												<tbody>
												</tbody>
											</table>
										</div>

									</div>
									<!-- end card body-->
								</div>
								<!-- end card -->
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
	<ul class="pagination-rounded"> 
		${paging} 
	</ul>
	<form id="searchBusinessNotice" action="searchBusinessNotice">
	<select name="select">
		<option value="공지사항" selected="selected">공지사항</option>
		<option value="이벤트">이벤트</option>
	</select> 
		<input type="text" name="search"/>
		<span><button class="btn btn-success"> 검색</button></span>
	</form>
	<span><button class="btn btn-success" onclick="location.href='./writeBusinessNoticePage'">글쓰기</button></span>                   
                                    
<script src="/resources/dist/assets/js/app.min.js"></script>
</div>
</body>
<script>
${alert}
function deleteChk(a) {
	var con = confirm('정말로 삭제하겠습니까?');
	con;
	if(con == false) {
		return false;
	}
}
</script>
</html>