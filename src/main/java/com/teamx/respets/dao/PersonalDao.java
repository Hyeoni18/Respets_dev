package com.teamx.respets.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.teamx.respets.bean.Personal;
import com.teamx.respets.bean.Pet;

public interface PersonalDao {
	
	//최근나의예약리스트
	public List<HashMap<String, Object>> recentMyBookingList(HashMap<String, Object> hmap);
	
	//개인 최근예약목록 카운트
	public int recentMyBookingListCount(String no);
	
	//개인 예약 디테일
	public HashMap<String, Object> myBookingDetail(String bk_no);
	
	//개인 예약 디테일 서비스 셀렉트
	public List<HashMap<String, Object>> getMenu(String bk_no);
	
	//개인 펫 디테일 셀렉트
	public List<HashMap<String, Object>> getPetDetail(String bk_no);
	
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
	
	//반려동물 목록
	public ArrayList<Pet> getPetList(String per_no);
	
	//동물 종류 번호
	public String getPetTypeNo(String pty_name);
	//동물 종류명
	public String getPetTypeName(String pty_no);
	//동물 종류 추가
	public boolean petTypeInsert(String pet_type);
	
	//반려동물 필수정보 등록 (사진 첨부X)
	public boolean petInsert(Pet pet);
	//반려동물 필수정보 등록 (사진 첨부O)
	public boolean petAndPhotoInsert(Pet pet);
	//반려동물 선택정보 등록
	public boolean pdtInsert(Pet pet);

	//반려동물 필수정보 수정 (사진 첨부X)
	public boolean petUpdate(Pet pet);
	//반려동물 필수정보 수정 (사진 첨부O)
	public boolean petAndPhotoUpdate(Pet pet);
	//반려동물 선택정보 수정
	public void pdtUpdate(Pet pet);
	
	//반려동물 전체상세정보
	public Pet getPetInfoDetail(String pet_no);
	//반려동물 삭제
	public boolean petDelete(String pet_no);
	
	//반려동물 선택정보
	public String getPdtCtt(Pet pet);
	//반려동물 선택정보 전체삭제
	public boolean pdtAllDelete(String pet_no);
	//반려동물 선택정보 삭제
	public void pdtDelete(Pet pet);
	//반려동물 선택정보 목록
	public List<HashMap<String, Object>> getPdt(String pet_no);
	
	/* 개인 캘린더 */
	public List<HashMap<String, Object>> getPerCalendar(String no);
	
	// 개인 즐겨찾기 기업 SELECT
	public List<HashMap<String, String>> likeBusinessSelect(String per_no);

	// 개인 즐겨찾기 삭제
	public void likeBusinessDelete(Map<String, String> hMap);

}
