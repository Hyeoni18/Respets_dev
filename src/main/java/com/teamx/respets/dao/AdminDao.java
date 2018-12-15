package com.teamx.respets.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

public interface AdminDao {
	
	public HashMap<String, Object> adminLogin(HashMap<String, Object> hmap);
	
	//미인증 기업목록 
	public ArrayList<HashMap<String, Object>> getBusinessList();
	
	//미인증 기업정보
	public HashMap<String, Object> getBusinessInfo(HashMap<String, Object> bMap);

	//미인증 기업 인증
	public int confirmLicense(String bus_no);

}
