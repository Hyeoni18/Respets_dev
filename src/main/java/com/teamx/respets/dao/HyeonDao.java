package com.teamx.respets.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Gallery;
import com.teamx.respets.bean.Personal;

@Repository
public interface HyeonDao {
	/* 혜연 개인 */
	public Personal myInfo(String no);
	public boolean myInfoUpdate(Personal mb);
	public boolean personalPartDelete(String no);
	public int myPwUpdate(Personal mb);
	public Personal myPwCheck(String no);
	public Date getBkStart(String bk_no);
	public int cancelInsert(String no, String timeS, long cancDay);
	public ArrayList<HashMap<String, Object>> allBookingList(HashMap<String, Object> map);

	/* 혜연 기업 */
	public String myPage(String email, String pw);
	public ArrayList<HashMap<String, Object>> todayScheduleList(HashMap<String, Object> map);
	public HashMap<String, Object> myBookingDetail(String bk_no);
	public ArrayList<HashMap<String, Object>> serviceManagement(HashMap<String, Object> map);
	public ArrayList<HashMap<String, Object>> businessBookingList(HashMap<String, Object> map);
	public int todayScheduleListNoShow(HashMap<String, String> map);
	public int todayScheduleListCheck(String bk_no);

	public ArrayList<HashMap<String, Object>> serviceList(HashMap<String, Object> map);
	public HashMap<String, Object> businessInfo(String no);
	public ArrayList<HashMap<String, Object>> getMenu(String bk_no);
	public boolean businessPartDelete(String no);
	public Gallery gallery(String no);
	public ArrayList<HashMap<String, Object>> getPetList(String pet_no);
	public int getListCount(String no);
	public boolean businessInfoUpdate(Business bi);
}
