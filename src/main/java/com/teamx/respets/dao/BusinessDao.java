package com.teamx.respets.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Gallery;

public interface BusinessDao {

	// 기업공지사항
	public List<HashMap<String, Object>> businessNotice(HashMap<String, Object> hmap);

	// 기업공지사항 상세 페이지
	public HashMap<String, Object> noticeDetailPage(HashMap<String, Object> hmap);

	// 기업공지사항 검색 리스트
	public List<HashMap<String, Object>> searchBusinessNotice(HashMap<String, Object> hmap);

	// 기업공지사항 (카테고리 전체 선택시)
	public List<HashMap<String, Object>> searchBusinessAllNotice(HashMap<String, Object> hmap);

	// 기업공지사항 카운트
	public int getBusinessNoticeCount(String no);

	// 기업공지사항 검색 카운트
	public int getSearchBusinessNoticeCount(HashMap<String, Object> hmap);

	// 기업전체공지사항 카운트 (카테고리 전체)
	public int getSearchBusinessAllNoticeCount(HashMap<String, Object> hmap);

	// 기업공지사항 글쓰기
	public int businessNoticeInsert(HashMap<String, Object> hmap);

	// 기업공지사항 수정 폼(기존 정보 가져오기)
	public HashMap<String, Object> businessNoticeUpdateForm(String no);

	// 기업공지사항 수정
	public int businessNoticeUpdate(HashMap<String, Object> hmap);

	// 기업공지사항 삭제
	public int businessNoticeDelete(Integer bbo_no);

	public ArrayList<HashMap<String, Object>> getSvcPri(String no); /* 업종 종류 불러오기(전체예약페이지) */

	public String getBk_chk(String bk_no); /* 예약 상세피이지 */

	public HashMap<String, Object> myBookingDetail(String bk_no); // 예약 리스트로 가져오기

	public ArrayList<HashMap<String, Object>> getMenu(String bk_no); // 서비스 메뉴 불러오기

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

	// 기업이 가지고 있는 업종 검색
	public List<Map<String, Object>> serviceInsertForm(String bus_no);

	// 해당 업종의 메뉴 검색
	public List<Map<String, Object>> selectMENU(String code);

	// 기업 정보 검색
	public Map<String, Object> searchBUS(String bus_no);

	// 기업의 갤러리사진 등록
	public int insertFile(Map<String, Object> map);

	// 기업의 고정 스케줄 등록
	public int serviceInsertBFX(Map<String, Object> map);

	// 기업의 한달 스케줄 등록
	public void serviceInsertBSD(Map<String, Object> map);

	// 제공서비스가 태그테이블에 있는지 검색
	public String searchTAG(String string);

	// 태그테이블에 서비스 등록
	public void insertTAG(String string);

	// 기업이 제공하는 서비스를 해시태그 테이블에 저장
	public void insertBTG(Map<String, Object> map);

	// 해당 동물의 코드를 검색
	public String selectAnimalCode(String ani_code);

	// 해당 서비스 코드를 검색
	public String selectMenuNo(Map<String, Object> map);

	// 서비스의 가격을 등록
	public void insertPrice(Map<String, Object> map);

	// 기업의 업종 순서 검색
	public String countSVC(String bus_no);

	// 기업이 제공하는 업종 추가 등록
	public void addBusinessCode(Map<String, Object> map);

	// 업종 이름을 코드로 변경
	public String searchBCT(String bct_code);

	// 업종 코드를 이름으로 변경
	public String searchBCTname(String bct_code);

	// 기업의 하루 스케줄 검색
	public Map<String, Object> searchBSD(Map<String, Object> map);

	// 기업의 고정 스케줄 검색
	public Map<String, Object> searchBFX(Map<String, Object> map);

	// 제공 서비스 검색
	public List<Map<String, Object>> selectMenuTag(Map<String, Object> map);

	// 메뉴 이름 검색
	public String selectMenuName(String menu_no);

	// 동물 검색
	public List<Map<String, Object>> selectAnimalTag(Map<String, Object> map);

	// 동물 종류 검색
	public String selectAnimalName(String animal_no);

	// 모든 동물 종류 검색
	public List<Map<String, Object>> animalSelect();

	// 기업의 기본정보 수정
	public void updateServiceBUS(Map<String, Object> map);

	// 기업의 하루 스케줄 수정
	public void updateServiceBSD(Map<String, Object> map);

	// 기업의 고정 스케줄 수정
	public void updateServiceBFX(Map<String, Object> map);

	// 해당 기업-업종의 서비스 가격 삭제
	public void deletePRC(Map<String, Object> map);

	// 해당 기업-업종의 해시태그 테이블 삭제
	public void deleteBTG(Map<String, Object> map);

	// 삭제 전 주업종인지 확인
	public String searchSVC(Map<String, Object> map);

	// 해당 기업-업종의 직원 검색
	public List<Map<String, Object>> selectEMP(Map<String, Object> map);

	// 해당 직원이 담당했던 예약 삭제
	public void deleteBK(String emp_no);

	// 해당 직원의 하루 스케줄 삭제
	public void deleteESD(String emp_no);

	// 해당 직원의 고정 스케줄 삭제
	public void deleteEFX(String emp_no);

	// 해당 직원의 상세 정보 삭제
	public void deleteEMP(String emp_no);

	// 해당 기업-업종의 하루 스케줄 삭제
	public void deleteBSD(Map<String, Object> map);

	// 해당 기업-업종의 고정 스케줄 삭제
	public void deleteBFX(Map<String, Object> map);

	// 해당 기업-업종의 사진 테이블 삭제
	public void deleteGLR(Map<String, Object> map);

	// 해당 기업-업종의 정보 삭제
	public void deleteSVC(Map<String, Object> map);

	// 기업이 가진 업종 검색
	public List<Map<String, Object>> selectSVC(String bus_no);

	// 직원 등록
	public int stepInsert(Map<String, Object> map);

	// 직원 고정스케줄 등록
	public int empInsertEFX(Map<String, Object> map);

	// 직원 하루스케줄 등록
	public void empInsertESD(Map<String, Object> map);

	// 직원 정보 검색
	public Map<String, Object> searchEMP(String emp_no);

	// 기업의 고정휴무일 검색
	public Map<String, Object> holidaySelected(Map<String, Object> map);

	// 직원의 고정휴무일 검색
	public Map<String, Object> searchEFX(String emp_no);

	// 직원의 하루스케줄 검색
	public Map<String, Object> searchESD(String emp_no);

	// 직원 정보 수정
	public void updateEMP(Map<String, Object> map);

	// 직원 프로필 사진 수정
	public void updateEMPPhoto(Map<String, Object> map);

	// 직원 하루스케줄 수정
	public void updateESD(Map<String, Object> map);

	// 직원 고정스케줄 수정
	public void updateEFX(Map<String, Object> map);

	// 메뉴 번호 검색
	public String searchMENUNO(Map<String, Object> map);

	// 동물 번호 검색
	public String searchPTYNO(String ani_name);

	// 가격 검색
	public String searchPRC(Map<String, Object> map);

	// 기업 상세페이지
	public HashMap<String, Object> getBusinessImage(HashMap<String, Object> hmap);

	public String getBusName(HashMap<String, Object> hmap);

	public String getBctName(HashMap<String, Object> hmap);

	public List<HashMap<String, Object>> getHaveService(String bus_no);

	public String getFavorite(HashMap<String, Object> hmap);

	// 기업상세페이지 - 즐겨찾기 추가
	public int favoriteInsert(HashMap<String, Object> hmap);

	// 기업상세페이지 - 즐겨찾기 삭제
	public int favoriteDelete(HashMap<String, Object> hmap);

	public int nowPwCheck(Business b);

	public void businessPwUpdate(Business b);

	public Business businessInfoUpdateForm(Business b);

	// 기업 새로운 예약
	public List<HashMap<String, String>> selectBooking(String bus_no);

	// 예약 수락
	public void bookingAccept(String bk_no);

	// 예약 거절
	public void bookingReject(String bk_no);

}
