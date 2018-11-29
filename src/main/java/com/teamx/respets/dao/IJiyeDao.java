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
	
	public HashMap<String, Object> noticeDetailPage(HashMap<String, Object> hmap);
	public List<HashMap<String, Object>> searchBusinessNotice(HashMap<String, Object> hmap);
	public List<HashMap<String, Object>> searchBusinessAllNotice(HashMap<String, Object> hmap);
	
	public int getBusinessNoticeCount(String no);
	public int getSearchBusinessNoticeCount(HashMap<String, Object> hmap);
	public int getSearchBusinessAllNoticeCount(HashMap<String, Object> hmap);
	public int businessNoticeInsert(HashMap<String, Object> hmap);
	public HashMap<String, Object> businessNoticeUpdateForm(String no);
	public int businessNoticeUpdate(HashMap<String, Object> hmap);
	public int businessNoticeDelete(Integer bbo_no);
	public HashMap<String, Object> businessBasiceInfo(HashMap<String, Object> hmap);
	public List<HashMap<String, Object>> selectTag(HashMap<String, Object> tMap);
	
	//기업상세페이지 하단 갤러리
	public List<HashMap<String, Object>> businessGallery(HashMap<String, Object> hmap);

	public List<HashMap<String, Object>> getPerCalendar(String no);

	public int adminChk(String adm_no);

	public ArrayList<HashMap<String, Object>> getBusinessList();

	public HashMap<String, Object> getBusinessInfo(HashMap<String, Object> bMap);

	public int confirmLicense(String bus_no);

	public int getRecentMyBookingList(String no);

	public List<HashMap<String, Object>> businessDetailNoticeList(HashMap<String, Object> hmap);




}
