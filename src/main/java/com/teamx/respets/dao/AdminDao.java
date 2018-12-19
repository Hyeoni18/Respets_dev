package com.teamx.respets.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.teamx.respets.bean.AdminBoard;

public interface AdminDao {
	
	public HashMap<String, Object> adminLogin(HashMap<String, Object> hmap);
	
	//미인증 기업목록 
	public ArrayList<HashMap<String, Object>> getBusinessList();
	
	//미인증 기업정보
	public HashMap<String, Object> getBusinessInfo(HashMap<String, Object> bMap);

	//미인증 기업 인증
	public int confirmLicense(String bus_no);

	//공지사항 상세정보
	public AdminBoard getBoardDetail(String abo_no);
	
	//공지사항 등록
	public boolean boardInsert(AdminBoard abo);
	
	//공지사항 삭제
	public boolean boardDelete(String abo_no);
	
	//공지사항 수정
	public boolean boardUpdate(AdminBoard abo);
	
	//공지사항 목록
	public List<AdminBoard> getNoticeList(int pageNum);
	
	//공지사항 검색 목록(내용을 입력하지 않고 검색버튼 클릭 시)
	public List<AdminBoard> getNoticeListAllSearch(AdminBoard abo);
	
	//공지사항 검색 목록
	public List<AdminBoard> getNoticeListCategoriSearch(AdminBoard abo);
	
	//공지사항 개수
	public int getBoardCount();
	public int getBoardCountAllSearch(AdminBoard abo);
	public int getBoardCountCategoriSearch(AdminBoard abo);
	
}
