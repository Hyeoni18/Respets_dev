<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Respets :: 기업 목록</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon" href="resources/images/logo-sm.png">

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
</head>
<body data-layout="topnav">
	<div class="wrapper">
		<div class="content-page">
			<div class="content">

<jsp:include page="topbar-landing.jsp"></jsp:include>
<div class="container-fluid">
<br/>
	<div id="tag">${tagList}</div>
	<br/>
	<div class="row">
		<div class="col-12" id="businessList">
					<%-- <div id="businessList">${busiList}</div> --%>
					${busiList}
				<!-- end card-deck-->
			<!-- end card-deck-wrapper-->
		</div>
		<!-- end col-->
	</div>
	<br/>
	<div id="page">${paging}</div>
	</div>
			</div>
			<%@ include file="footer.html"%>
		</div>
	</div>
	
	
	<script src="resources/dist/assets/js/app.min.js"></script>
</body>
<script>
	function businessList(bct_code, tag_no, date, city, pno) {
		console.log(bct_code);
		console.log(tag_no);
		console.log(date);
		console.log(city);
		console.log(pno)
		var url = "tagSelectList?bct_code=" + bct_code + "&tag_no=" + tag_no
				+ "&bsd_date=" + date + "&bus_addr=" + city + "&pageNum=" + pno;
		console.log(url);
		Aj(url, "#businessList");
		function Aj(url, position) {
			$.ajax({
				url : url,
				type : "post",
				dataType : "text",
				//async : false,
				success : function(data) {
					console.log(data);
					$(position).html(data);
					url = "tagSelectListAddr?bct_code=" + bct_code + "&tag_no="
							+ tag_no + "&bsd_date=" + date + "&bus_addr="
							+ city + "&pageNum=" + pno;
					position = "#page";
					$.ajax({
						url : url,
						type : "post",
						dataType : "text",
						//async : false,
						success : function(data) {
							console.log(data);
							$(position).html(data);
						},
						error : function(error) {
							console.log("error");
						}
					}); //ajax End
				},
				error : function(error) {
					console.log("error");
				}
			}); //ajax End
		}
	};
	function butTagSelectList(bct_code, tag_no, pno) {
		console.log(bct_code);
		console.log(tag_no);
		var url = "butTagSelectList?bct_code=" + bct_code + "&tag_no=" + tag_no
				+ "&pageNum=" + pno;
		console.log(url);
		Aj(url, "#businessList");
		function Aj(url, position) {
			$.ajax({
				url : url,
				type : "post",
				dataType : "text",
				//async : false,
				success : function(data) {
					console.log(data);
					$(position).html(data);
					url = "butTagSelectListPaging?bct_code=" + bct_code
							+ "&tag_no=" + tag_no + "&pageNum=" + pno;
					position = "#page";
					$.ajax({
						url : url,
						type : "post",
						dataType : "text",
						//async : false,
						success : function(data) {
							console.log(data);
							$(position).html(data);
						},
						error : function(error) {
							console.log("error");
						}
					}); //ajax End
				},
				error : function(error) {
					console.log("error");
				}
			}); //ajax End
		}
	};
</script>
</html>