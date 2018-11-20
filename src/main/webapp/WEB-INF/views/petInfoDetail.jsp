<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>반려동물 상세정보</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/images/logo-sm.png">

<!-- third party css -->
<link
	href="resources/dist/assets/css/vendor/jquery-jvectormap-1.2.2.css"
	rel="stylesheet" type="text/css" />
<!-- third party css end -->

<!-- App css -->
<link
	href="resources/dist/assets/css/icons.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="resources/dist/assets/css/app.min.css"
	rel="stylesheet" type="text/css" />
<style type="text/css">
#petProfile {
	width: 150px;
	height: 150px;
	margin-top: 20px;
}
</style>
</head>
<body>
	반려동물 상세정보
	<br> 동물번호 : ${pet.pet_no}
	<form name="petInfoDetail" method="get">
		<table>
			<tr>
				<td>프로필 사진</td>
				<td>
					<img class="rounded-circle" id="petProfile" 
					src="${pet.pet_loc}/${pet.pet_photo}"
					alt="pet profile"></td>
			</tr>
			<tr>
				<td>이름</td>
				<td>${pet.pet_name}</td>
			</tr>
			<tr>
				<td>종류</td>
				<td>${pet.pty_name}</td>
			</tr>
			<tr>
				<td>품종</td>
				<td>${pet.pet_vrt}</td>
			</tr>
			
			<tr>
				<td>출생년도</td>
				<c:set var="now" value="<%=new java.util.Date()%>" />
				<c:set var="sysYear">
					<fmt:formatDate value="${now}" pattern="yyyy" />
				</c:set>
				<td>${pet.pet_age}(${sysYear-pet.pet_age+1}살)</td>
			</tr>
			<tr>
				<td>성별</td>
				<td>
					<c:if test="${'F' eq pet.pet_sex}">암컷</c:if>
					<c:if test="${'M' eq pet.pet_sex}">수컷</c:if></td>
			</tr>
			<tr>
				<td>중성화 수술 여부</td>
				<td>${pet.pet_ntr}</td>
			</tr>
			<tr>
				<td>몸무게</td>
				<td>${pet.pet_wght}
					<c:if test="${null == pet.pet_wght}">-</c:if>
				</td>
			</tr>
			<tr>
				<td>질병</td>
				<td>${pet.pet_sick}
					<c:if test="${null == pet.pet_sick}">-</c:if>
				</td>
			</tr>
			<tr>
				<td>만난 날</td>
				<td>${pet.pet_mday}
					<c:if test="${null == pet.pet_mday}">-</c:if>
				</td>
			</tr>
			<tr>
				<td>주의해야할 음식</td>
				<td>${pet.pet_food}
					<c:if test="${null == pet.pet_food}">-</c:if>
				</td>
			</tr>
			<tr>
				<td>하루 식사 횟수 및 1회 배급량</td>
				<td>${pet.pet_rat}
					<c:if test="${null == pet.pet_rat}">-</c:if>
				</td>
			</tr>
			<tr>
				<td>배변 훈련 여부</td>
				<td>${pet.pet_tot}
					<c:if test="${null == pet.pet_tot}">-</c:if>
				</td>
			</tr>
			<tr>
				<td>기타 특이사항</td>
				<td>${pet.pet_etc}
					<c:if test="${null == pet.pet_etc}">-</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: right;">					
					<input type="button" class="btn btn-warning" onclick="forward(this)" value="수정"/>
					<!-- Warning Alert modal -->
					<input type="button" class="btn btn-warning" data-toggle="modal" data-target="#warning-alert-modal" value="삭제"/>
					
					<!-- Warning Alert Modal -->
					<div id="warning-alert-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
					    <div class="modal-dialog modal-sm">
					        <div class="modal-content">
					            <div class="modal-body p-4">
					                <div class="text-center">
					                    <i class="dripicons-warning h1 text-warning"></i>
					                    <h4 class="mt-2">Incorrect Information</h4>
					                    <p class="mt-3">${pet.pet_name}의 정보를 정말 삭제하시겠습니까?</p>
					                    <input type="button" class="btn btn-warning my-2" data-dismiss="modal" value="취소"/>
					                    <input type="button" class="btn btn-warning my-2" onclick="forward(this)" value="삭제"/>
					                </div>
					            </div>
					        </div><!-- /.modal-content -->
					    </div><!-- /.modal-dialog -->
					</div><!-- /.modal -->
				</td>
			</tr>
		</table>
		<input type="hidden" value="${pet.per_no}" name="per_no" />
		<input type="hidden" value="${pet.pet_no}" name="pet_no" /> 
	</form>
	
	<!-- alert -->
	${Fail}
	
	
	<!-- App js -->
	<script src="<c:url value="/resources/dist/assets/js/app.min.js"/>"></script>

	<!-- third party js -->
	<script
		src="<c:url value="/resources/dist/assets/js/vendor/Chart.bundle.min.js"/>"></script>
	<script
		src="<c:url value="/resources/dist/assets/js/vendor/jquery-jvectormap-1.2.2.min.js"/>"></script>
	<script
		src="<c:url value="/resources/dist/assets/js/vendor/jquery-jvectormap-world-mill-en.js"/>"></script>
	<!-- third party js ends -->

	<!-- demo app -->
	<script
		src="<c:url value="/resources/dist/assets/js/pages/demo.dashboard.js"/>"></script>
	<!-- end demo js-->
</body>
<script>
	function forward(button) {
		console.log("button="+button.value);
		var frm = document.petInfoDetail;
		if (button.value == '수정') {
			frm.action = "petInfoUpdateForm?pet_no=${pet.pet_no}";
		}
		if (button.value == '삭제') {
			frm.action = "petDelete?pet_no=${pet.pet_no}";
		}
		frm.submit();
	}
</script>
</html>