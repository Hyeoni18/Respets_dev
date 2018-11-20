package com.teamx.respets.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.teamx.respets.bean.AdminBoard;
import com.teamx.respets.bean.Booking;
import com.teamx.respets.bean.Pet;

public interface SunnyDao {
	//접속한 회원 아이디로 회원번호 SELECT
	public String getPerNo(String userId);
	//메뉴 나의 동물 정보에서 동물 목록 불러오기 
	public ArrayList<Pet> getPetList(String per_no);
	
	/*------------ PTY_TB ------------*/
	public String getPetTypeNo(String pty_name);
	public String getPetTypeName(String pty_no);
	public boolean petTypeInsert(String pet_type);
	
	/*---------- PET정보 insert ----------*/
	//PET_TB 사진 첨부X
	public boolean petInsert(Pet pet);
	//PET_TB 사진 첨부O
	public boolean petAndPhotoInsert(Pet pet);
	//PETDETAIL_TB insert
	public boolean pdtInsert(Pet pet);

	/*---------- PET정보 Update ----------*/
	//PET_TB 사진 첨부X
	public boolean petUpdate(Pet pet);
	//PET_TB 사진 첨부O
	public boolean petAndPhotoUpdate(Pet pet);
	//PETDETAIL_TB Update
	public void pdtUpdate(Pet pet);
	
	//상세정보
	public Pet getPetInfoDetail(String pet_no);
	//삭제
	public boolean petDelete(String pet_no);
	
	/*------------ PDT_TB ------------*/
	public String getPdtCtt(Pet pet);
	public boolean pdtAllDelete(String pet_no);
	public void pdtDelete(Pet pet);
	
	
	/*------------ ABO_TB ------------*/
	public AdminBoard getBoardDetail(String abo_no);
	public boolean boardInsert(AdminBoard abo);
	public boolean boardDelete(String abo_no);
	public boolean boardUpdate(AdminBoard abo);
	public List<AdminBoard> getNoticeList(int pageNum);
	public List<AdminBoard> getNoticeListAllSearch(AdminBoard abo);
	public List<AdminBoard> getNoticeListCategoriSearch(AdminBoard abo);
	public int getBoardCount();
	public int getBoardCountAllSearch(AdminBoard abo);
	public int getBoardCountCategoriSearch(AdminBoard abo);
	
	/* 기업 상세페이지 */
	public HashMap<String, Object> getBusinessImage(HashMap<String, Object> hmap);
	public String getBusName(HashMap<String, Object> hmap);
	public String getBctName(HashMap<String, Object> hmap);
	public String getReviewCount(HashMap<String, Object> hmap);
	public String getReviewAvg(HashMap<String, Object> hmap);
	public List<HashMap<String, Object>> getHaveService(String bus_no);
	public String getFavorite(HashMap<String, Object> hmap);
	public int favoriteInsert(HashMap<String, Object> hmap);
	public int favoriteDelete(HashMap<String, Object> hmap);
	
	/* 개인 캘린더 */
	public List<HashMap<String, Object>> getPerCalendar(String no);
	
	
	
}
