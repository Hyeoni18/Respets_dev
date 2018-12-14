package com.teamx.respets.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Gallery;
public interface BusinessDao {
	
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

	public ArrayList<HashMap<String, Object>> getSvcPri(String no); /* 업종 종류 불러오기(전체예약페이지) */
	public String getBk_chk(String bk_no); /* 예약 상세피이지 */
	public HashMap<String, Object> myBookingDetail(String bk_no); // 예약 리스트로 가져오기
	public ArrayList<HashMap<String, Object>> getMenu(String bk_no); //서비스 메뉴 불러오기
	public ArrayList<HashMap<String, Object>> getPetList(String pet_no); // 펫 디테일 리스트로 가져오기
	public int todayScheduleListCheck(String bk_no); /* 오늘 예약 방문 확인 ajax */
	public ArrayList<HashMap<String, Object>> todayScheduleList(Map<String, Object> map); /* 오늘 예약 일정 전체 ajax */
	public ArrayList<HashMap<String, Object>> serviceManagement(String no); /* 서비스 종류 불러오기(서비스페이지) */
	public ArrayList<HashMap<String, Object>> businessBookingList(Map<String, Object> map); /* 기업 전체예약 리스트 ajax */
	public HashMap<String, Object> businessInfo(String no); /* 기업 정보 페이지 */
	public boolean businessPartDelete(String no); /* 기업 회원탈퇴 */
	public int getListCount(String no); /* 전체예약 페이징 ajax */
	public boolean businessInfoUpdate(Business bi); /* 기업정보 수정 */
	public void mainPhotoUpdate(Gallery gy); /* 기업정보 수정 */
	public ArrayList<HashMap<String, Object>> bctBookingList(Map<String, Object> map); /* 서비스별 전체예약 리스트 ajax */
	public ArrayList<HashMap<String, Object>> AllbctBookingList(Map<String, Object> map); /* 서비스별 전체 예약 리스트 ajax */
	public ArrayList<HashMap<String, Object>> searchAllList(Map<String, Object> map); /* 전체 예약에서의 검색 ajax */
	public ArrayList<HashMap<String, Object>> searchBctAllsList(Map<String, Object> map); /* 서비스별 예약에서의 검색 ajax */
	public ArrayList<HashMap<String, Object>> vs_chkOkList(Map<String, Object> map); /* 방문완료 예약리스트 ajax */
	public int bctAllPaging(Map<String, Object> map); /* 서비스별 예약 페이징 ajax */
	public ArrayList<HashMap<String, Object>> todayScheduleListOk(Map<String, Object> map); /* 오늘 방문 확인된 예약 리스트 ajax */
	public int todayScheduleListCancel(String bk_no); /* 오늘 예약 방문취소 ajax */
	public ArrayList<HashMap<String, Object>> bctBookingListOk(Map<String, Object> map); /* 서비스별 방문확인 된 전체예약 리스트 ajax */
	public int searchAllListPaging(Map<String, Object> map); /* 전체 예약에서의 검색 페이징 ajax */
	public int searchBctAllsListPaging(Map<String, Object> map); /* 서비스별 예약에서의 검색 페이징 ajax */
}
