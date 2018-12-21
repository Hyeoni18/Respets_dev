package com.teamx.respets.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Personal;
import com.teamx.respets.bean.RandomTB;

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

	//아이디 찾기
	public Map<String,Object> selectUser (Map<String,Object> map); 
	//비밀번호 변경폼 메일 보내기 전 랜덤값 유무 확인
	public List<Map<String, Object>> searchRND(String userId);
	//메일보내기 전 랜덤값 존재시 삭제 , 비밀번호 변경 후 랜덤값 삭제
	public void deleteRcode(String email); 
	//메일을 보내기 전, 랜덤 테이블에 가지고 온 정보 입력
	public int findMyPw(RandomTB rtb);	
	//신원확인을 위한 랜덤값 비교
	public RandomTB checkRcode(RandomTB rtb); 
	//개인일 경우 비밀번호 변경
	public void resetPerPw(Personal ps); 
	//기업일 경우 비밀번호 변경
	public void resetBusPw(Personal ps); 
	
	//기업 회원 가입 폼의 업종 라디오 버튼 목록
	public List<HashMap<String, String>> selectBusCategory();

	// 기업 회원 가입 시 이메일 확인
	public int emailCheck(String email);

	// 기업 회원 가입 시 사업자 등록 번호 확인
	public int taxIdCheck(String taxId);

	// 기업 회원 가입 BUSINESS_TB INSERT
	public void businessInsert(Business b);

	// 기업 회원 가입 SERVICE_TB INSERT
	public void busJoinSvcInsert(Business b);

	// 기업 회원 가입 사업자등록증 GALLERY_TB INSERT
	public void licenseInsert(Map<String, Object> hMap);

	// 기업 회원 가입 기업대표사진 GALLERY_TB INSERT
	public void mainPhotoInsert(Map<String, Object> hMap);

	// 기업 회원 가입 기업대표사진 없을 때 GALLERY_TB INSERT DEFAULT
	public void mainPhotoDefault(Map<String, Object> hMap);
	
}
