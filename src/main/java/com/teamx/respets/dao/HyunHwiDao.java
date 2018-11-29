package com.teamx.respets.dao;

import java.util.List;
import java.util.Map;

import com.teamx.respets.bean.Personal;
import com.teamx.respets.bean.RandomTB;

public interface HyunHwiDao {


	// 현휘 
	public Map<String,Object> selectUser (Map<String,Object> map); //아이디 찾기
	public List<Map<String, Object>> searchRND(String userId);
	public RandomTB checkRcode(RandomTB rtb); //신원확인을 위한 랜덤값 비교
	public int findMyPw(RandomTB rtb);	//메일을 보내기 전, 랜덤 테이블에 가지고 온 정보 입력
	public void resetPerPw(Personal ps); //개인일 경우 비밀번호 변경
	public void resetBusPw(Personal ps); //기업일 경우 비밀번호 변경
	public void deleteRcode(String email); //변경이 완료 후 랜덤 값 지우기
	
	public List<Map<String, Object>> selectBusinessService(String no); //기업 업종 검색, 삭제예정

	public List<Map<String,Object>> serviceInsertForm(String bus_no); //기업이 가지고 있는 업종 검색
	public List<Map<String, Object>> selectMENU(String code); //해당 업종의 메뉴 검색
	public Map<String, Object> searchBUS(String bus_no); //기업 정보 검색
	public Map<String, Object> searchBUSmap(Map<String, Object> map);
	public int insertFile(Map<String, Object> map); //기업의 갤러리사진 등록
	public int serviceInsertBFX(Map<String, Object> map); //기업의 고정 스케줄 등록
	public void serviceInsertBSD(Map<String, Object> map); //기업의 한달 스케줄 등록
	public String searchTAG(String string); //제공서비스가 태그테이블에 있는지 검색
	public void insertTAG(String string); //태그테이블에 서비스 등록
	public void insertBTG(Map<String, Object> map); //기업이 제공하는 서비스를 해시태그 테이블에 저장
	public String selectAnimalCode(String ani_code); //해당 동물의 코드를 검색
	public String selectMenuNo(Map<String, Object> map); //해당 서비스 코드를 검색
	public void insertPrice(Map<String, Object> map); //서비스의 가격을 등록
	public void addBusinessCode(Map<String, Object> map); //기업이 제공하는 업종 추가 등록
	public String searchBCT(String bct_code); //업종 이름을 코드로 변경
	public String searchBCTname (String bct_code); //업종 코드를 이름으로 변경
	public Map<String, Object> searchBSD(Map<String, Object> map); //기업의 하루 스케줄 검색
	public Map<String, Object> searchBFX(Map<String, Object> map); //기업의 고정 스케줄 검색
	public List<Map<String, Object>> selectMenuTag(Map<String, Object> map); //제공 서비스 검색
	public String selectMenuName(String menu_no); //메뉴 이름 검색
	public List<Map<String, Object>> selectAnimalTag(Map<String, Object> map); //동물 검색
	public String selectAnimalName(String animal_no); //동물 종류 검색
	public List<Map<String, Object>> animalSelect(); //모든 동물 종류 검색
	public void updateServiceBUS(Map<String, Object> map); //기업의 기본정보 수정
	public void updateServiceBSD(Map<String, Object> map); //기업의 하루 스케줄 수정
	public void updateServiceBFX(Map<String, Object> map); //기업의 고정 스케줄 수정
	
	public List<Map<String, Object>> selectEMP(Map<String, Object> map); //해당 기업-업종의 직원 검색
	public String selectEMPstring(Map<String, Object> map); //수정필	
	public void deleteBK(String emp_no); //해당 직원이 담당했던 예약 삭제
	public void deleteESD(String emp_no); //해당 직원의 하루 스케줄 삭제
	public void deleteEFX(String emp_no); //해당 직원의 고정 스케줄 삭제
	public void deleteEMP(String emp_no); //해당 직원의 상세 정보 삭제
	public void deletePRC(Map<String, Object> map); //해당 기업-업종의 서비스 가격 삭제
	public void deleteBSD(Map<String, Object> map); //해당 기업-업종의 하루 스케줄 삭제
	public void deleteBFX(Map<String, Object> map); //해당 기업-업종의 고정 스케줄 삭제
	public void deleteBTG(Map<String, Object> map); //해당 기업-업종의 해시태그 테이블 삭제
	public void deleteGLR(Map<String, Object> map); //해당 기업-업종의 사진 테이블 삭제
	public void deleteSVC(Map<String, Object> map); //해당 기업-업종의 정보 삭제

	public List<Map<String, Object>> selectSVC(String bus_no); //기업이 가진 업종 검색
	
	public List<Map<String, Object>> bctSelect(Map<String, Object> map);
	public Map<String, Object> holidaySelected(Map<String, Object> map);
	public String selectFileNo();
	public int stepInsert(Map<String, Object> map);
	public int empInsertEFX(Map<String, Object> map);
	public void empInsertESD(Map<String, Object> map);
	public List<Map<String, Object>> selectStep(Map<String, Object> map);
	public Map<String, Object> selectGallery(Map<String, Object> map);
	public Map<String, Object> searchEMP(String emp_no);
	public Map<String, Object> searchEFX(String emp_no);
	public String searchLunchTime(String no);
	public Map<String, Object> searchTime(String no);	
	
	
	public String changeCode(String bct_code);
	public Map<String, Object> searchESD(String emp_no);
	public List<Map<String, Object>> yellowList();
	public Map<String, Object> searchPER(String per_no);
	public void updateEMP(Map<String, Object> map);
	public void updateEMPPhoto(Map<String, Object> map);
	public void updateESD(Map<String, Object> map);
	public void updateEFX(Map<String, Object> map);
	
	public List<Map<String, Object>> searchYellowBLK();
	public List<Map<String, Object>> searchRedBLK();
	public List<Map<String, Object>> searchBTG(String bct_code);
	public String countSVC(String bus_no);
	public String searchMENUNO(Map<String, Object> map);
	public String searchPTYNO(String ani_name);
	public String searchPRC(Map<String, Object> map);
	public String searchSVC(Map<String, Object> map);
	public List<Map<String, Object>> selectBSD(Map<String, Object> map);
	public List<Map<String, Object>> selectTAG(String bct_code);
	public String changeTAG(String tag_no);
	public List<Map<String, Object>> searchBSDtoBUS(Map<String, Object> map);
	public String selectBTG(Map<String, Object> map);
	public List<Map<String, Object>> selectSVCcode(Map<String, Object> map);
	public List<Map<String, Object>> selectBTGbus(Map<String, Object> map);
	public List<Map<String, Object>> searchBUSaddr(Map<String, Object> map);
	public Map<String, Object> busiListPaging(Map<String, Object> map);
	public int countBusiList(Map<String, Object> map);
	public int countBusiButList(String bct_code);
	public List<Map<String, Object>> searchBUSaddrTag(Map<String, Object> map);
	public int countSearchBUSaddr(Map<String, Object> map);
	public List<Map<String, Object>> butTagSelectList(Map<String, Object> map);
	public int countButTagSelectList(Map<String, Object> map);
	
}
