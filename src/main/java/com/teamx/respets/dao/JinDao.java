package com.teamx.respets.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teamx.respets.bean.AdminBoard;
import com.teamx.respets.bean.Booking;
import com.teamx.respets.bean.Business;

public interface JinDao {

	// 서진 : 기업 회원 가입 폼의 업종 라디오 버튼 목록
	public List<HashMap<String, String>> selectBusCategory();

	// 서진 : 기업 회원 가입 시 이메일 확인
	public int emailCheck(String email);

	// 서진 : 기업 회원 가입 시 사업자 등록 번호 확인
	public int taxIdCheck(String taxId);

	// 서진 : 기업 회원 가입 BUSINESS_TB INSERT
	public void businessInsert(Business b);

	// 서진 : 기업 회원 가입 SERVICE_TB INSERT
	public void busJoinSvcInsert(Business b);

	// 서진 : 기업 회원 가입 사업자등록증 GALLERY_TB INSERT
	public void licenseInsert(Map<String, Object> hMap);

	// 서진 : 기업 회원 가입 기업대표사진 GALLERY_TB INSERT
	public void mainPhotoInsert(Map<String, Object> hMap);

	// 서진 : 기업 회원 가입 기업대표사진 없을 때 GALLERY_TB INSERT DEFAULT
	public void mainPhotoDefault(Map<String, Object> hMap);

	// 서진 : 개인 즐겨찾기 기업 SELECT
	public List<HashMap<String, String>> likeBusinessSelect(String per_no);

	// 서진 : 개인 즐겨찾기 삭제
	public void likeBusinessDelete(Map<String, String> hMap);

	// 서진 : 동물 번호 없을 때 첫 번째 동물 정보 갖고 오기
	public HashMap<String, String> selectFirstPet(HashMap<String, String> hMap);

	// 서진 : 동물 번호 있을 때 첫 번째 동물 정보 갖고 오기
	public HashMap<String, String> firstPet(HashMap<String, String> hMap);

	// 서진 : 사진 없는 동물 리스트 SELECT
	public List<HashMap<String, String>> selectPetList(Map<String, String> hMap);

	// 서진 : 직원 리스트 SELECT
	public List<HashMap<String, String>> selectEmpList(HashMap<String, String> hMap);

	// 서진 : 서비스 리스트 SELECT
	public List<HashMap<String, String>> selectSvcList(HashMap<String, String> hMap);

	// 서진 : 직원 시간 SELECT
	public HashMap<String, String> selectEmpTime(HashMap<String, String> hMap);

	// 서진 : 직원 안 되는 시간 SELECT
	public List<HashMap<String, String>> selectNoTime(HashMap<String, String> hMap);

	// 서진 : 예약 테이블에 INSERT
	public void insertBooking(Booking bk);

	// 서진 : 예약 메뉴 테이블에 INSERT
	public void insertBkMenu(Booking bk);

	// 서진 : 예약 완료 정보 SELECT
	public HashMap<String, String> bookingSuccess(Booking bk);

	// 서진 : 기업 새로운 예약
	public List<HashMap<String, String>> selectBooking(String bus_no);

	// 서진 : 예약 수락
	public void bookingAccept(String bk_no);

	// 서진 : 예약 거절
	public void bookingReject(String bk_no);

	// 서진 : 메인 페이지 공지사항
	public List<AdminBoard> selectBoardList();

} // interface End
