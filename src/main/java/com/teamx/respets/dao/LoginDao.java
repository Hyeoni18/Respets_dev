package com.teamx.respets.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

public interface LoginDao {
	
	// 개인 이메일 중복 확인
	public int emailChkSignUp(String email);
	
	// 개인회원가입
	public boolean personalJoin(Map<String, Object> hmap);
	
	//개인회원가입(사진포함)
	public boolean personalJoinPhoto(Map<String, Object> hmap);
	
	//로그인
	public HashMap<String, Object> loginProcess(HashMap<String, Object> hmap);
	
	//이메일인증성공
	public int emailConfirmSuccess(String email);
	
	//관리자 로그인
	public HashMap<String, Object> adminLogin(HashMap<String, Object> hmap);
	
	//블랙리스트 체크
	public HashMap<String, Object> blackChk(HashMap<String, Object> hmap);
	
	//개인이메일 인증 여부 확인
	public HashMap<String, Object> chkPerEm(HashMap<String, Object> hmap);
	
	//기업이메일 인증 여부 확인
	public HashMap<String, Object> chkBusEm(HashMap<String, Object> hmap);
	
	//기업 로그인시 사진테이블의 대표사진 가져오기
	public HashMap<String, Object> getBusinessPhoto(String bus_no);

	//기업 로그인시 기업명 가져오기
	public String selectBus_name(String no);

}
