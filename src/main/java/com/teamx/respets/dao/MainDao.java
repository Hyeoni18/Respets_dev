package com.teamx.respets.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

public interface MainDao {
	
	//기업상세페이지 하단 기본정보
	public HashMap<String, Object> businessBasiceInfo(HashMap<String, Object> hmap);
	
	//기업상세페이지 하단 기본정보 태그
	public List<HashMap<String, Object>> selectTag(HashMap<String, Object> tMap);
	
	//기업상세페이지 하단 갤러리
	public List<HashMap<String, Object>> businessGallery(HashMap<String, Object> hmap);
	
	//기업 상세페이지 기업공지 리스트
	public List<HashMap<String, Object>> businessDetailNoticeList(HashMap<String, Object> hmap);
	
	//기업 상세페이지 기업공지 리스트 카운트
	public int getBusinessNoticeDetailCount(HashMap<String, Object> hmap);


}
