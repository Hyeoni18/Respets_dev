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
<!-- App favicon -->
<link rel="shortcut icon" href="assets/images/favicon.ico">

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<style>
/* #busiList {
	width: 30%;
}

#busi {
	height: 20%;
	display: inline;
}

#busiImage {
	width: 30%;
}

#busiInfo {
	width: 50%;
} */
</style>
</head>
<body>
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