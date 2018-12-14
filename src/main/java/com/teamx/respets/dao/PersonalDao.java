package com.teamx.respets.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

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

}
