package com.teamx.respets.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;
public interface BusinessDao {
	
	//기업공지사항
	public List<HashMap<String, Object>> businessNotice(HashMap<String, Object> hmap);
	
	//기업공지사항 상세 페이지
	public HashMap<String, Object> noticeDetailPage(HashMap<String, Object> hmap);
	
	//기업공지사항 검색 리스트
	public List<HashMap<String, Object>> searchBusinessNotice(HashMap<String, Object> hmap);
	
	//기업공지사항 (카테고리 전체 선택시)
	public List<HashMap<String, Object>> searchBusinessAllNotice(HashMap<String, Object> hmap);
	
	//기업공지사항 카운트
	public int getBusinessNoticeCount(String no);
	
	//기업공지사항 검색 카운트
	public int getSearchBusinessNoticeCount(HashMap<String, Object> hmap);
	
	//기업전체공지사항 카운트 (카테고리 전체)
	public int getSearchBusinessAllNoticeCount(HashMap<String, Object> hmap);
	
	//기업공지사항 글쓰기
	public int businessNoticeInsert(HashMap<String, Object> hmap);
	
	//기업공지사항 수정 폼(기존 정보 가져오기)
	public HashMap<String, Object> businessNoticeUpdateForm(String no);
	
	//기업공지사항 수정
	public int businessNoticeUpdate(HashMap<String, Object> hmap);
	
	//기업공지사항 삭제
	public int businessNoticeDelete(Integer bbo_no);

}
