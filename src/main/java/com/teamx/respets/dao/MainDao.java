package com.teamx.respets.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.teamx.respets.bean.AdminBoard;
import com.teamx.respets.bean.Booking;
import com.teamx.respets.bean.Business;

public interface MainDao {
	
	//기업상세페이지 하단 기본정보
	public HashMap<String, Object> businessBasiceInfo(HashMap<String, Object> hmap);
	
	//기업상세페이지 하단 기본정보 태그
	public List<HashMap<String, Object>> selectTag(HashMap<String, Object> tMap);
	
	//기업상세페이지 하단 갤러리
	public List<HashMap<String, Object>> businessGallery(HashMap<String, Object> hmap);
	
	//기업 상세페이지 기업공지 리스트
	public List<HashMap<String, Object>> businessDetailNoticeList(HashMap<String, Object> hmap);
	
	//기업 상세페이지 기업공지 리스트 카운트
	public int getBusinessNoticeDetailCount(HashMap<String, Object> hmap);

	//주소선택 후 기업리스트 검색 
	public List<Map<String, Object>> searchBUSaddr(Map<String, Object> map);
	//주소선택 후 각 기업에 대표이미지 검색
	public Map<String, Object> selectGallery(Map<String, Object> map);
	//주소선택 후 기업리스트 페이징 카운트
	public int countBusiList(Map<String, Object> map);
	//기업리스트가 가진 태그 검색
	public List<Map<String, Object>> selectTAG(String bct_code);
	//태그 번호를 태그 이름으로 변경
	public String changeTAG(String tag_no);
	
	//업종버튼선택 후 기업리스트 검색
	public List<Map<String, Object>> selectSVCcode(Map<String, Object> map);
	//업종버튼선택 후 기업리스트 페이징 카운트
	public int countBusiButList(String bct_code);
	
	//주소선택 후 기업리스트에서 태그 선택시 기업리스트 검색
	public List<Map<String, Object>> searchBUSaddrTag(Map<String, Object> map);
	//주소선택 후 기업리스트에서 태그 선택시 기업리스트 페이징 카운트
	public int countSearchBUSaddr(Map<String, Object> map);

	//업종버튼선택 후 스케줄을 등록한 기업인지 확인
	public String searchBFX(Map<String, Object> map);
	//업종버튼선택 후 기업리스트에서 태그 선택시 기업리스트 검색
	public List<Map<String, Object>> butTagSelectList(Map<String, Object> map);
	//업종버튼선택 후 기업리스트에서 태그 선택시 기업리스트 페이징 카운트
	public int countButTagSelectList(Map<String, Object> map);
	
	//메인 페이지 공지사항
	public List<AdminBoard> selectBoardList();

	public int nowPwCheck(Business b);

	public void businessPwUpdate(Business b);

	public Business businessInfoUpdateForm(Business b);
	
	// 기업 회원 가입 폼의 업종 라디오 버튼 목록
	public List<HashMap<String, String>> selectBusCategory();
	
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

}
