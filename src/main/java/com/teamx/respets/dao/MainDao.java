package com.teamx.respets.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	//주소선택 후 기업리스트 검색 
	public List<Map<String, Object>> searchBUSaddr(Map<String, Object> map);
	//주소선택 후 각 기업에 대표이미지 검색
	public Map<String, Object> selectGallery(Map<String, Object> map);
	//주소선택 후 기업리스트 페이징 카운트
	public int countBusiList(Map<String, Object> map);
	//기업리스트가 가진 태그 검색
	public List<Map<String, Object>> selectTAG(String bct_code);
	//태그 번호를 태그 이름으로 변경
	public String changeTAG(String tag_no);
	
	//업종버튼선택 후 기업리스트 검색
	public List<Map<String, Object>> selectSVCcode(Map<String, Object> map);
	//업종버튼선택 후 기업리스트 페이징 카운트
	public int countBusiButList(String bct_code);
	
	//주소선택 후 기업리스트에서 태그 선택시 기업리스트 검색
	public List<Map<String, Object>> searchBUSaddrTag(Map<String, Object> map);
	//주소선택 후 기업리스트에서 태그 선택시 기업리스트 페이징 카운트
	public int countSearchBUSaddr(Map<String, Object> map);

	//업종버튼선택 후 기업리스트에서 태그 선택시 기업리스트 검색
	public List<Map<String, Object>> butTagSelectList(Map<String, Object> map);
	//업종버튼선택 후 기업리스트에서 태그 선택시 기업리스트 페이징 카운트
	public int countButTagSelectList(Map<String, Object> map);
}
