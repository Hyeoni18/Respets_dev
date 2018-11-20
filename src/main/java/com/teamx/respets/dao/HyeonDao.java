package com.teamx.respets.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Personal;

@Repository
public interface HyeonDao {
	/* 혜연 개인 */
	public Personal myInfo(String no);
	public boolean myInfoUpdate(Personal mb);
	public boolean personalPartDelete(String no);
	public int myPwUpdate(Personal mb);
	public Personal myPwCheck(String no);
	public String getBkStart(String no);
	public int cancelInsert(String no, String timeS, long cancDay);
	public ArrayList<HashMap<String, Object>> allBookingList(HashMap<String, Object> map);

	/* 혜연 기업 */
	public String myPage(String email, String pw);
	public ArrayList<HashMap<String, Object>> todayScheduleList(HashMap<String, Object> map);
	public ArrayList<HashMap<String, Object>> myBookingDetail(HashMap<String, Object> map);
	public ArrayList<HashMap<String, Object>> serviceManagement(HashMap<String, Object> map);
	public ArrayList<HashMap<String, Object>> businessBookingList(HashMap<String, Object> map);
	public int todayScheduleListNoShow(String bkno);
	public int todayScheduleListCheck(String no);
}
