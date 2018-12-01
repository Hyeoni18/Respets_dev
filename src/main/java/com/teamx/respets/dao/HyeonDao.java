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
	/* 혜연 개인 */
	public Personal myInfo(String no);
	public boolean myInfoUpdate(Personal mb);
	public boolean personalPartDelete(String no);
	public int myPwUpdate(Personal mb);
	public int myPwCheck(Personal mb);
	public Date getBkStart(String bk_no);
	public int cancelInsert(HashMap<String, Object> map);
	public int bk_chkUpdate(String bk_no);
	public ArrayList<HashMap<String, Object>> allBookingList(HashMap<String, Object> map);
	public int contPerBkList(String no);
	public void perPhotoUpdate(Personal p);
	public void perNoPhotoUpdate(Personal p);

	/* 혜연 기업 */
	public ArrayList<HashMap<String, Object>> todayScheduleList(Map<String, Object> map);
	public HashMap<String, Object> myBookingDetail(String bk_no);
	public ArrayList<HashMap<String, Object>> serviceManagement(String no);
	public ArrayList<HashMap<String, Object>> businessBookingList(Map<String, Object> map);
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
	public ArrayList<HashMap<String, Object>> getSvcPri(String no);
	public ArrayList<HashMap<String, Object>> bctBookingList(Map<String, Object> map);
	public ArrayList<HashMap<String, Object>> AllbctBookingList(Map<String, Object> map);
	public String getBk_chk(String bk_no);
	public void mainPhotoUpdate(Map<String, Object> map);
	public ArrayList<HashMap<String, Object>> searchAllList(Map<String, Object> map);
	public ArrayList<HashMap<String, Object>> searchBctAllsList(Map<String, Object> map);
	public int noshowInsert(String per_no);
	public int getnoshowCount(String per_no);
	public int noshowDelete(String per_no);
	public int warningInsert(String per_no);
	public int todayScheduleListUnNoShow(String per_no, String timeS);
	public ArrayList<HashMap<String, Object>> vs_chkOkList(Map<String, Object> map);
	public int bctAllPaging(Map<String, Object> map);
	public ArrayList<HashMap<String, Object>> todayScheduleListOk(Map<String, Object> map);
	public int todayScheduleListCancel(String bk_no);
	public ArrayList<HashMap<String, Object>> bctBookingListOk(Map<String, Object> map);
	public int searchAllListPaging(Map<String, Object> map);
	public int searchBctAllsListPaging(Map<String, Object> map);
	public int getBctListCount(String no, String bct_name);
}
