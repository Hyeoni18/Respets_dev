package com.teamx.respets.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IJiyeDao {
	
	// 개인 이메일 중복 확인
	public int emailChkSignUp(String email);
	
	// 개인회원가입
	public boolean personalJoin(Map<String, Object> hmap);
	
	//개인회원가입(사진포함)
	public boolean personalJoinPhoto(Map<String, Object> hmap);
	
	//로그인
	public HashMap<String, Object> loginProcess(HashMap<String, Object> hmap);
	
	//이메일인증성공
	public int emailConfirmSuccess(String email);
	
	//관리자 로그인
	public HashMap<String, Object> adminLogin(HashMap<String, Object> hmap);
	
	//블랙리스트 체크
	public HashMap<String, Object> blackChk(HashMap<String, Object> hmap);
	
	//최근나의예약리스트
	public List<HashMap<String, Object>> recentMyBookingList(HashMap<String, Object> hmap);
	
	//개인 예약 디테일
	public HashMap<String, Object> myBookingDetail(String bk_no);
	
	//개인 예약 디테일 서비스 셀렉트
	public List<HashMap<String, Object>> getMenu(String bk_no);
	
	//개인 펫 디테일 셀렉트
	public List<HashMap<String, Object>> getPetDetail(String bk_no);
	
	//기업공지사항리스트
	public List<HashMap<String, Object>> businessNotice(String no);
	
	//개인이메일 인증 여부 확인
	public HashMap<String, Object> chkPerEm(HashMap<String, Object> hmap);
	
	//기업이메일 인증 여부 확인
	public HashMap<String, Object> chkBusEm(HashMap<String, Object> hmap);
	
	//기업공지사항
	public List<HashMap<String, Object>> businessNotice(HashMap<String, Object> hmap);
	
	//기업공지사항 상세 페이지
	public HashMap<String, Object> noticeDetailPage(HashMap<String, Object> hmap);
	
	//기업공지사항 검색 리스트
	public List<HashMap<String, Object>> searchBusinessNotice(HashMap<String, Object> hmap);
	
	//기업공지사항 (카테고리 전체 선택시)
	public List<HashMap<String, Object>> searchBusinessAllNotice(HashMap<String, Object> hmap);
	
	//기업공지사항 카운트
	public int getBusinessNoticeCount(String no);
	
	//기업공지사항 검색 카운트
	public int getSearchBusinessNoticeCount(HashMap<String, Object> hmap);
	
	//기업전체공지사항 카운트 (카테고리 전체)
	public int getSearchBusinessAllNoticeCount(HashMap<String, Object> hmap);
	
	//기업공지사항 글쓰기
	public int businessNoticeInsert(HashMap<String, Object> hmap);
	
	//기업공지사항 수정 폼(기존 정보 가져오기)
	public HashMap<String, Object> businessNoticeUpdateForm(String no);
	
	//기업공지사항 수정
	public int businessNoticeUpdate(HashMap<String, Object> hmap);
	
	//기업공지사항 삭제
	public int businessNoticeDelete(Integer bbo_no);
	
	//기업상세페이지 하단 기본정보
	public HashMap<String, Object> businessBasiceInfo(HashMap<String, Object> hmap);
	
	//기업상세페이지 하단 기본정보 태그
	public List<HashMap<String, Object>> selectTag(HashMap<String, Object> tMap);
	
	//기업상세페이지 하단 갤러리
	public List<HashMap<String, Object>> businessGallery(HashMap<String, Object> hmap);

	//미인증 기업목록 
	public ArrayList<HashMap<String, Object>> getBusinessList();
	
	//미인증 기업정보
	public HashMap<String, Object> getBusinessInfo(HashMap<String, Object> bMap);

	//미인증 기업 인증
	public int confirmLicense(String bus_no);

	//개인 최근예약목록 리스트
	public int getRecentMyBookingList(String no);

	//개인 최근예약목록 카운트
	public int recentMyBookingListCount(String no);

	//기업 상세페이지 기업공지 리스트
	public List<HashMap<String, Object>> businessDetailNoticeList(HashMap<String, Object> hmap);
	
	//기업 상세페이지 기업공지 리스트 카운트
	public int getBusinessNoticeDetailCount(HashMap<String, Object> hmap);

	//기업 로그인시 사진테이블의 대표사진 가져오기
	public HashMap<String, Object> getBusinessPhoto(String bus_no);

	//기업 로그인시 기업명 가져오기
	public String selectBus_name(String no);

} //interface End
