<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Insert title here</title>
</head>
<body>
<form id="writeBusinessNoticePage" action="businessNoticeInsert">
<select name="bct_code">
	<option value="M" selected="selected">병원</option>
	<option value="B">미용</option>
	<option value="H">호텔</option>
</select>

<select name="bbc_no">
	<option value="1" selected="selected">공지사항</option>
	<option value="2">이벤트</option>
</select> <br/>
<input type="text" name="bbo_title" placeholder="제목을 입력해주세요."> <br/>
<textarea rows="20" name="bbo_ctt" placeholder="내용을 입력해주세요."></textarea> <br/>
<button>글쓰기</button>
</form>
</body>
</html>