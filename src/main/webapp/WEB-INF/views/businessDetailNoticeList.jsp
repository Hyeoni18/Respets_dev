<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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

</head>
</head>
<body>
	<div class="row">
		<div class="col-12">
			<div class="table-responsive-sm">
				<table class="table table-striped table-centered mb-0"
					style='text-align: center;'>
					<thead>
						<tr>
							<th>게시글 번호</th>
							<th>카테고리</th>
							<th>글제목</th>
							<th>작성일</th>
						</tr>
					</thead>
					<c:forEach var="bboList" items="${bboList}">
						<tr>
							<td style="text-align: center;">${bboList.bbo_no}</td>
							<td style="text-align: center;">${bboList.bbc_name}</td>
							<td><a href="#" data-toggle="modal"
								data-target="#B${bboList.bbo_no}">${bboList.bbo_title}</a></td>
							<!-- Standard modal content -->
							<div id="B${list.bbo_no}" class="modal fade" tabindex="-1"
								role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog modal-dialog-centered">
									<div class="modal-content">
										<div class="modal-header">

											<h4 class="modal-title" id="myModalLabel">${bboList.bbo_title}</h4>
											<div class="badge badge-secondary"
												style="margin-top: 5px; margin-left: 10px">
												<c:if test="${'공지사항' == bboList.bbc_name}">공지사항</c:if>
												<c:if test="${'이벤트' == bboList.bbc_name}">이벤트</c:if>
											</div>
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">×</button>
										</div>
										<div class="modal-body">
											<p>${bboList.bbo_ctt}</p>
										</div>
										<!-- <div class="modal-footer">
																		<button type="button" class="btn btn-light"
																			data-dismiss="modal">Close</button>
																	</div> -->
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal-dialog -->
							</div>
							<!-- /.modal -->
							<td style="text-align: center;">${bboList.bbo_date}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<!-- App js -->
	<script src="resources/dist/assets/js/app.min.js"></script>

	
	<script src="resources/dist/assets/js/vendor/jquery.dataTables.js"></script>
	<script src="resources/dist/assets/js/vendor/dataTables.bootstrap4.js"></script>
	<script
		src="resources/dist/assets/js/vendor/dataTables.responsive.min.js"></script>
	<script
		src="resources/dist/assets/js/vendor/responsive.bootstrap4.min.js"></script>

	<!-- demo app -->
	<script src="resources/dist/assets/js/pages/demo.dashboard.js"></script>
	<!-- end demo js-->
</body>
</html>