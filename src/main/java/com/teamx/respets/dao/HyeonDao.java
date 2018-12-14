package com.teamx.respets.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Gallery;
import com.teamx.respets.bean.Personal;

@Repository
public interface HyeonDao {
	/* 개인 */
	public Personal myInfo(String no); /* 개인 회원정보 페이지 */
	public int myPwUpdate(Personal mb); /* 개인 비밀번호 수정 */
	public int myPwCheck(Personal mb); /* 개인 현제 비번과 받아온 비번 비교 ajax */
	public void perPhotoUpdate(Personal p); /* 개인 정보 수정 */
	public void perNoPhotoUpdate(Personal p); /* 개인 정보 수정 */
	public boolean personalPartDelete(String no); /* 개인 회원탈퇴 */
	public Date getBkStart(String bk_no); /* 예약 취소 */
	public int cancelInsert(HashMap<String, Object> map); /* 예약 취소 */
	public int bk_chkUpdate(String bk_no); /* 예약 취소 */
	public ArrayList<HashMap<String, Object>> allBookingList(HashMap<String, Object> map); /* 개인 예약 전체리스트 */
	public int contPerBkList(String no); /* 개인 예약 전체리스트 페이징 */

	/* 기업 */
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
