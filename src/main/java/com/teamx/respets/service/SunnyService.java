package com.teamx.respets.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamx.respets.bean.AdminBoard;
import com.teamx.respets.bean.Pet;
import com.teamx.respets.dao.SunnyDao;
import com.teamx.respets.userClass.Paging;

@Service
public class SunnyService {
	@Autowired
	private SunnyDao sDao;

	private ModelAndView mav;
	AdminBoard abo = new AdminBoard();

	/*--- 개인회원 PET ---*/

	/* 메뉴 나의 동물 정보 클릭 시 실행 */
	/*
	 * public ModelAndView petList(HttpServletRequest request) { HttpSession session
	 * = request.getSession(); String per_no =
	 * session.getAttribute("no").toString(); ArrayList<Pet> petList = new
	 * ArrayList<Pet>(); mav = new ModelAndView(); String view=null; petList = null;
	 * System.out.println("per_no="+per_no); //검색한 회원번호로 반려동물 리스트 불러오기 petList =
	 * pDao.getPetList(per_no); mav.addObject("per_no", per_no);//회원번호 담기 if
	 * (petList.size() > 0) {//리스트가 존재하면 mav.addObject("petList", petList);//반려동물
	 * 리스트 담기 } else {//등록된 반려동물이 없으면 mav.addObject("petEmpty", "등록된 동물이 없습니다"); }
	 * view = "petList"; mav.setViewName(view); return mav; }
	 */

	/* 메뉴 나의 동물 정보 클릭 시 실행 */
	public ModelAndView petList(String per_no) { // userId는 이메일
		ArrayList<Pet> petList = new ArrayList<Pet>();
		mav = new ModelAndView();
		String view = null;
		petList = null;
		// 검색한 회원번호로 반려동물 리스트 불러오기
		petList = sDao.getPetList(per_no);
		mav.addObject("per_no", per_no);// 회원번호 담기
		if (petList.size() > 0) {// 리스트가 존재하면
			mav.addObject("petList", petList);// 반려동물 리스트 담기
		} else {// 등록된 반려동물이 없으면
			mav.addObject("petEmpty", "등록된 동물이 없습니다");
		}
		view = "petList";
		mav.setViewName(view);
		return mav;
	}

	/* 반려동물 등록 폼에서 [등록 완료] 버튼 클릭 시 실행 */
	public ModelAndView petInsert(MultipartHttpServletRequest multi) {
		Pet pet = new Pet();

		String ptyNo = multi.getParameter("pty_no");
		String ptyEtc = multi.getParameter("pty_etc");
		// 종류가 기타이면
		if (ptyNo.equals("etc")) {
			System.out.println("if문. pet_type=" + ptyNo);
			System.out.println("if문. pet_type_etc=" + ptyEtc);
			// 동물종류TB에 존재하는 값인지 select한다
			String getPtyNo = sDao.getPetTypeNo(ptyEtc);
			if (getPtyNo == null) {// 기타값이 종류TB에 존재하지 않으면
				System.out.println("[DAO] pet_type select value = null");
				// 동물종류TB에 새로운 데이터를 insert한다
				boolean typeInsertResult = sDao.petTypeInsert(ptyEtc);
				if (typeInsertResult) {// insert성공하면
					System.out.println("[DAO] pet_type insert success");
					// 동물종류번호를 select한다
					getPtyNo = sDao.getPetTypeNo(ptyEtc);
				}
			}
			pet.setPty_no(getPtyNo);
		} // 종류가 기타가 아니면 그대로 pet_type에 set(value가 번호로 넘어온다)
		else
			pet.setPty_no(ptyNo);
		/*--- multi에 포함된 파라미터값을 변수 및 bean에 담는다 ---*/
		pet.setPer_no(multi.getParameter("per_no"));
		pet.setPet_name(multi.getParameter("pet_name"));
		pet.setPet_vrt(multi.getParameter("pet_vrt"));
		pet.setPet_age(multi.getParameter("pet_age"));
		pet.setPet_sex(multi.getParameter("pet_sex"));
		pet.setPet_ntr(multi.getParameter("pet_ntr"));
		pet.setPet_wght(multi.getParameter("pet_wght"));
		pet.setPet_sick(multi.getParameter("pet_sick"));
		pet.setPet_mday(multi.getParameter("pet_mday"));
		pet.setPet_food(multi.getParameter("pet_food"));
		pet.setPet_rat(multi.getParameter("pet_rat"));
		pet.setPet_tot(multi.getParameter("pet_tot"));
		pet.setPet_etc(multi.getParameter("pet_etc"));
		int check = Integer.parseInt(multi.getParameter("fileCheck"));
		// log
		System.out.println("---- SunnyService log ----");
		System.out.println("per_no=" + pet.getPer_no());
		System.out.println("pet_name=" + pet.getPet_name());
		System.out.println("pet_type=" + pet.getPty_no());
		System.out.println("pet_vrt=" + pet.getPet_vrt());
		System.out.println("pet_age=" + pet.getPet_age());
		System.out.println("pet_sex=" + pet.getPet_sex());
		System.out.println("pet_ntr=" + pet.getPet_ntr());
		System.out.println("pet_wght=" + pet.getPet_wght());
		System.out.println("pet_sick=" + pet.getPet_sick());
		System.out.println("pet_mday=" + pet.getPet_mday());
		System.out.println("pet_food=" + pet.getPet_food());
		System.out.println("pet_rat=" + pet.getPet_rat());
		System.out.println("pet_tot=" + pet.getPet_tot());
		System.out.println("pet_etc=" + pet.getPet_etc());
		System.out.println("check=" + check);
		System.out.println("--------- logEnd ---------");

		Map<String, String> fMap = new HashMap<String, String>();
		boolean petInsertResult = false; // pet:PET_TB시너님
		if (check == 1) {// 파일을 첨부했을 때
			FileService upload = new FileService();
			fMap = upload.upload(multi);// 서버에 파일을 업로드, 오리지널파일명·시스템파일명·경로를 Map에 저장
			pet.setPet_photo(fMap.get("sysFileName"));
			System.out.println("ServiceSysFileName=" + pet.getPet_photo());
			pet.setPet_loc(fMap.get("location"));
			System.out.println("ServicePath=" + pet.getPet_loc());
			petInsertResult = sDao.petAndPhotoInsert(pet);
			if (petInsertResult)
				System.out.println("사진 첨부 OK 인서트 성공");
		} else {// 파일을 첨부하지 않았을 때
			petInsertResult = sDao.petInsert(pet);
			if (petInsertResult)
				System.out.println("사진 첨부 NO 인서트 성공");
		}
		/* 선택입력사항에 값이 있면 insert */
		if (!pet.getPet_wght().equals("미입력")) {
			pet.setPcl_no(1);
			pet.setPdt_ctt(pet.getPet_wght());
			sDao.pdtInsert(pet);
		}
		if (!pet.getPet_sick().equals("미입력")) {
			pet.setPcl_no(2);
			pet.setPdt_ctt(pet.getPet_sick());
			sDao.pdtInsert(pet);
		}
		if (!pet.getPet_mday().equals("미입력")) {
			pet.setPcl_no(3);
			pet.setPdt_ctt(pet.getPet_mday());
			sDao.pdtInsert(pet);
		}
		if (!pet.getPet_food().equals("미입력")) {
			pet.setPcl_no(4);
			pet.setPdt_ctt(pet.getPet_food());
			sDao.pdtInsert(pet);
		}
		if (!pet.getPet_rat().equals("미입력")) {
			pet.setPcl_no(5);
			pet.setPdt_ctt(pet.getPet_rat());
			sDao.pdtInsert(pet);
		}
		if (!pet.getPet_tot().equals("미입력")) {
			pet.setPcl_no(6);
			pet.setPdt_ctt(pet.getPet_tot());
			sDao.pdtInsert(pet);
		}
		if (!pet.getPet_etc().equals("미입력")) {
			pet.setPcl_no(7);
			pet.setPdt_ctt(pet.getPet_etc());
			sDao.pdtInsert(pet);
		}

		/* 해당 뷰페이지로 포워딩 */
		mav = new ModelAndView();
		String view = null;
		if (petInsertResult) {
			view = "redirect:petList?per_no=P1000004";
		} else {
			mav.addObject("Fail", "<script>alert('petInsert 실패')</script>");
			view = "petInsertForm";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 반려동물 사진 클릭 시 상세정보 */
	public ModelAndView petInfoDetail(String pet_no) {
		mav = new ModelAndView();
		String view = null;
		Pet pet = new Pet();
		// 동물번호로 반려동물 상세정보 불러오기
		pet = sDao.getPetInfoDetail(pet_no);
		if (pet != null) {// 반려동물 상세정보가 존재하면
			String ptyName = sDao.getPetTypeName(pet.getPty_no());
			pet.setPty_name(ptyName);
			for (int pclNo = 1; pclNo <= 7; pclNo++) {
				pet.setPcl_no(pclNo);
				String getPdtCtt = sDao.getPdtCtt(pet);
				switch (pclNo) {
				case 1:
					pet.setPet_wght(getPdtCtt);
					break;
				case 2:
					pet.setPet_sick(getPdtCtt);
					break;
				case 3:
					pet.setPet_mday(getPdtCtt);
					break;
				case 4:
					pet.setPet_food(getPdtCtt);
					break;
				case 5:
					pet.setPet_rat(getPdtCtt);
					break;
				case 6:
					pet.setPet_tot(getPdtCtt);
					break;
				case 7:
					pet.setPet_etc(getPdtCtt);
				}
			}
			mav.addObject("pet", pet);// 반려동물 상세정보 담기
			System.out.println("pet.getPet_type=" + pet.getPty_name());
			view = "petInfoDetail";
		} else {// 반려동물 상세정보가 없으면
			mav.addObject("Fail", "<script>alert('petInfoDetail 실패')</script>");
			view = "petList";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 반려동물 정보 수정 폼 */
	public ModelAndView petInfoUpdateForm(String pet_no) {
		System.out.println("pet_no=" + pet_no);
		mav = new ModelAndView();
		String view = null;
		Pet pet = new Pet();
		// 동물번호로 반려동물 상세정보 불러오기
		pet = sDao.getPetInfoDetail(pet_no);
		if (pet != null) {// 반려동물 상세정보가 존재하면
			String ptyName = sDao.getPetTypeName(pet.getPty_no());
			pet.setPty_name(ptyName);
			for (int pclNo = 1; pclNo <= 7; pclNo++) {
				pet.setPcl_no(pclNo);
				String getPdtCtt = sDao.getPdtCtt(pet);
				switch (pclNo) {
				case 1:
					pet.setPet_wght(getPdtCtt);
					break;
				case 2:
					pet.setPet_sick(getPdtCtt);
					break;
				case 3:
					pet.setPet_mday(getPdtCtt);
					break;
				case 4:
					pet.setPet_food(getPdtCtt);
					break;
				case 5:
					pet.setPet_rat(getPdtCtt);
					break;
				case 6:
					pet.setPet_tot(getPdtCtt);
					break;
				case 7:
					pet.setPet_etc(getPdtCtt);
				}
			}
			mav.addObject("pet", pet);// 반려동물 상세정보 담기
			view = "petInfoUpdateForm";
		} else {// 반려동물 상세정보가 없으면
			mav.addObject("Fail", "<script>alert('petInfoUpdateForm 실패')</script>");
			view = "petInfoDetail";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 반려동물 정보 수정 */
	public ModelAndView petInfoUpdate(MultipartHttpServletRequest multi) {
		Pet pet = new Pet();
		String ptyNo = multi.getParameter("pty_no");
		String ptyEtc = multi.getParameter("pty_etc");
		// 종류가 기타이면
		if (ptyNo.equals("etc")) {
			System.out.println("if문. pet_type=" + ptyNo);
			System.out.println("if문. pet_type_etc=" + ptyEtc);
			// 동물종류TB에 존재하는 값인지 select한다
			String getPtyNo = sDao.getPetTypeNo(ptyEtc);
			if (getPtyNo == null) {// 기타값이 종류TB에 존재하지 않으면
				System.out.println("[DAO] pet_type select value = null");
				// 동물종류TB에 새로운 데이터를 insert한다
				boolean typeInsertResult = sDao.petTypeInsert(ptyEtc);
				if (typeInsertResult) {// insert성공하면
					System.out.println("[DAO] pet_type insert success");
					// 동물종류번호를 select한다
					getPtyNo = sDao.getPetTypeNo(ptyEtc);
				}
			}
			pet.setPty_no(getPtyNo);
		} // 종류가 기타가 아니면 그대로 pet_type에 set(value가 번호로 넘어온다)
		else
			pet.setPty_no(ptyNo);
		/*--- multi에 포함된 파라미터값을 변수 및 bean에 담는다 ---*/
		pet.setPer_no(multi.getParameter("per_no"));
		pet.setPet_no(multi.getParameter("pet_no"));
		pet.setPet_name(multi.getParameter("pet_name"));
		pet.setPet_vrt(multi.getParameter("pet_vrt"));
		pet.setPet_age(multi.getParameter("pet_age"));
		pet.setPet_sex(multi.getParameter("pet_sex"));
		pet.setPet_ntr(multi.getParameter("pet_ntr"));
		pet.setPet_wght(multi.getParameter("pet_wght"));
		pet.setPet_sick(multi.getParameter("pet_sick"));
		pet.setPet_mday(multi.getParameter("pet_mday"));
		pet.setPet_food(multi.getParameter("pet_food"));
		pet.setPet_rat(multi.getParameter("pet_rat"));
		pet.setPet_tot(multi.getParameter("pet_tot"));
		pet.setPet_etc(multi.getParameter("pet_etc"));
		int check = Integer.parseInt(multi.getParameter("fileCheck"));

		Map<String, String> fMap = new HashMap<String, String>();
		boolean petUpdateResult = false; // pet:PET_TB시너님
		if (check == 1) {// 파일을 첨부했을 때
			FileService upload = new FileService();
			fMap = upload.upload(multi);// 서버에 파일을 업로드, 오리지널파일명·시스템파일명·경로를 Map에 저장
			pet.setPet_photo(fMap.get("sysFileName"));
			System.out.println("ServiceSysFileName=" + pet.getPet_photo());
			pet.setPet_loc(fMap.get("location"));
			System.out.println("ServicePath=" + pet.getPet_loc());
			petUpdateResult = sDao.petAndPhotoUpdate(pet);
			if (petUpdateResult)
				System.out.println("사진 수정 OK 업데이트 성공");
		} else {// 파일을 첨부하지 않았을 때
			System.out.println("pet.getPer_no=" + pet.getPer_no());
			petUpdateResult = sDao.petUpdate(pet);
			if (petUpdateResult)
				System.out.println("사진 수정 NO 업데이트 성공");
		}

		/* 선택입력사항에 값이 있면 기존에 값이 있으면 update 없었으면 insert */
		String getPdt = null;

		pet.setPcl_no(1);
		pet.setPdt_ctt(pet.getPet_wght());
		getPdt = sDao.getPdtCtt(pet);
		if (!pet.getPet_wght().equals("미입력")) {
			if (getPdt == null)
				sDao.pdtInsert(pet);
			else
				sDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				sDao.pdtDelete(pet);
		}
		pet.setPcl_no(2);
		pet.setPdt_ctt(pet.getPet_sick());
		getPdt = sDao.getPdtCtt(pet);
		if (!pet.getPet_sick().equals("미입력")) {
			if (getPdt == null)
				sDao.pdtInsert(pet);
			else
				sDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				sDao.pdtDelete(pet);
		}
		pet.setPcl_no(3);
		pet.setPdt_ctt(pet.getPet_mday());
		getPdt = sDao.getPdtCtt(pet);
		if (!pet.getPet_mday().equals("미입력")) {

			if (getPdt == null)
				sDao.pdtInsert(pet);
			else
				sDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				sDao.pdtDelete(pet);
		}
		pet.setPcl_no(4);
		pet.setPdt_ctt(pet.getPet_food());
		getPdt = sDao.getPdtCtt(pet);
		if (!pet.getPet_food().equals("미입력")) {
			if (getPdt == null)
				sDao.pdtInsert(pet);
			else
				sDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				sDao.pdtDelete(pet);
		}
		pet.setPcl_no(5);
		pet.setPdt_ctt(pet.getPet_rat());
		getPdt = sDao.getPdtCtt(pet);
		if (!pet.getPet_rat().equals("미입력")) {
			if (getPdt == null)
				sDao.pdtInsert(pet);
			else
				sDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				sDao.pdtDelete(pet);
		}
		pet.setPcl_no(6);
		pet.setPdt_ctt(pet.getPet_tot());
		getPdt = sDao.getPdtCtt(pet);
		if (!pet.getPet_tot().equals("미입력")) {
			if (getPdt == null)
				sDao.pdtInsert(pet);
			else
				sDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				sDao.pdtDelete(pet);
		}
		pet.setPcl_no(7);
		pet.setPdt_ctt(pet.getPet_etc());
		getPdt = sDao.getPdtCtt(pet);
		if (!pet.getPet_etc().equals("미입력")) {
			if (getPdt == null)
				sDao.pdtInsert(pet);
			else
				sDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				sDao.pdtDelete(pet);
		}

		/* 해당 뷰페이지로 포워딩 */
		mav = new ModelAndView();
		String view = null;
		if (petUpdateResult) {
			mav.addObject("pet_no", pet.getPet_no());
			view = "petInfoDetail";
		} else {
			mav.addObject("Fail", "<script>alert('petUpdate 실패')</script>");
			view = "petUpdateForm";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 반려동물 삭제 */
	public ModelAndView petDelete(String pet_no) {
		mav = new ModelAndView();
		String view = null;
		System.out.println("Delete pet_no=" + pet_no);
		if (sDao.pdtAllDelete(pet_no)) {
			if (sDao.petDelete(pet_no)) {// 반려동물 정보 삭제에 성공하면
				view = "redirect:petList?userId=respets2018@gmail.com";
			} else {// 반려동물 정보 삭제에 실패하면
				mav.addObject("Fail", "<script>alert('petDelete실패')</script>");
				view = "petInfoDetail";
			}
		} else {// 반려동물 정보 삭제에 실패하면
			mav.addObject("Fail", "<script>alert('pdtDelete실패')</script>");
			view = "petInfoDetail";
		}
		mav.setViewName(view);
		return mav;
	}

	/*--- 관리자 공지사항 ---*/

	/* 공지 리스트 */
	public ModelAndView noticeList(Integer pageNum) {
		mav = new ModelAndView();
		String view = null;
		List<AdminBoard> aboList = null;
		int page_no = (pageNum == null) ? 1 : pageNum;
		System.out.println("pageNum=" + page_no);
		aboList = sDao.getNoticeList(page_no);
		if (aboList != null) {
			mav.addObject("aboList", aboList);
			mav.addObject("paging", getPaging(page_no));
			System.out.println("size=" + aboList.size());
			System.out.println("getNoticeList 성공");
			view = "noticeList";
		} else {
			System.out.println("getNoticeList 실패");
			view = "noticeList";
		}
		mav.setViewName(view);
		return mav;
	}

	private Object getPaging(int pageNum) {
		int maxNum = sDao.getBoardCount(); // 전체 글의 개수
		int listCount = 10; // 페이지당 글의 수
		int pageCount = 5; // 그룹당 페이지 수
		String boardName = "noticeList"; // 게시판이 여러개일 때
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, boardName);
		return paging.makeHtmlPaging();
	}

	/* 게시글 저장 */
	public ModelAndView noticeInsert(String abc_no, String abo_title, String abo_ctt) {
		abo = new AdminBoard();
		System.out.println("abc_no=" + abc_no);
		System.out.println("abo_title=" + abo_title);
		System.out.println("abo_ctt=" + abo_ctt);
		abo.setAbc_no(abc_no);
		abo.setAbo_title(abo_title);
		abo.setAbo_ctt(abo_ctt);
		boolean insertResult = sDao.boardInsert(abo);
		System.out.println("what's the insertResult? " + insertResult);
		mav = new ModelAndView();
		String view = null;
		if (insertResult) {
			System.out.println("noticeInsert 성공!!");
			view = "redirect:noticeList";
		} else {
			System.out.println("noticeInsert 실패 ㅠㅠ");
			view = "redirect:noticeWriteForm";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 게시글 내용 */
	public ModelAndView noticeDetail(String abo_no) {
		mav = new ModelAndView();
		String view = null;
		AdminBoard abo = null;
		abo = sDao.getBoardDetail(abo_no);
		mav.addObject("abo", abo);
		view = "noticeDetail";// jsp
		mav.setViewName(view);
		return mav;
	}

	/* 게시글 수정 폼 */
	public ModelAndView noticeUpdateForm(String abo_no) {
		mav = new ModelAndView();
		String view = null;
		AdminBoard abo = null;
		abo = sDao.getBoardDetail(abo_no);
		mav.addObject("abo", abo);
		view = "noticeUpdateForm";// jsp
		mav.setViewName(view);
		return mav;
	}

	/* 게시글 수정 */
	public ModelAndView noticeUpdate(AdminBoard abo) {
		abo = new AdminBoard();
		boolean updateResult = false;
		updateResult = sDao.boardUpdate(abo);
		System.out.println("updateResult=" + updateResult);
		mav = new ModelAndView();
		String view = null;
		view = "redirect:noticeDetail";
		mav.setViewName(view);
		return mav;
	}

	/* 게시글 삭제 */
	public ModelAndView noticeDelete(String abo_no) {
		System.out.println("abo_no=" + abo_no);
		mav = new ModelAndView();
		boolean deleteResult = sDao.boardDelete(abo_no); // 게시글
		System.out.println("deleteResult=" + deleteResult);
		mav.setViewName("redirect:noticeList");
		return mav;
	}
	
	/* 검색한 게시글 리스트 */
	public ModelAndView noticeListSearch(Integer pageNum, String abc_name, String search) {
		mav = new ModelAndView();
		String view = null;
		int page_no = (pageNum == null) ? 1 : pageNum;
		abo = new AdminBoard();
		abo.setPage_no(page_no);
		abo.setAbc_name(abc_name);
		abo.setSearch(search);
		List<AdminBoard> aboList = null;
		System.out.println("aboList=" + aboList);
		if(abo.getAbc_name().equals("전체") && abo.getSearch()=="") {
			noticeList(pageNum);
		}else if(abo.getAbc_name().equals("전체")) {
			aboList = sDao.getNoticeListAllSearch(abo);
		}else aboList = sDao.getNoticeListCategoriSearch(abo);	
		System.out.println("aboList=" + aboList);
		if (aboList != null) {
			mav.addObject("aboList", aboList);
			mav.addObject("abc_name", abc_name);
			mav.addObject("paging", getPagingSearch(abo));
			System.out.println("size=" + aboList.size());
			System.out.println("getNoticeListSearch 성공");
			view = "noticeList";
		} else {
			System.out.println("getNoticeListSearch 실패");
			view = "noticeList";
		}
		mav.setViewName(view);
		return mav;
	}

	private String getPagingSearch(AdminBoard abo) {
		int maxNum = 0;
		if(abo.getAbc_name().equals("전체")) 
			maxNum = sDao.getBoardCountAllSearch(abo); // 전체 글의 개수
		else maxNum = sDao.getBoardCountCategoriSearch(abo); // 전체 글의 개수
		int listCount = 10; // 페이지당 글의 수
		int pageCount = 5; // 그룹당 페이지 수
		String boardName = "noticeListSearch"; // 게시판이 여러개일 때
		Paging paging = new Paging(maxNum, abo.getPage_no(), listCount, pageCount, boardName);
		return paging.makeHtmlSearchPaging(abo);
	}


	public ModelAndView businessDetailPage(String bus_no, String bct_code) {
		mav = new ModelAndView();
		String view = null;
		HashMap<String, Object> hmap = new HashMap<>();
		List<HashMap<String, Object>> serviceList = new ArrayList<HashMap<String, Object>>();
		System.out.println("bus_no=" + bus_no);
		System.out.println("bct_code=" + bct_code);
		
		//회원번호
		String no = "P1000001";
		//String no = session.getAttribute("no").toString();
		
		// 해시맵에 쿼리스트링과 회원번호를 담는다
		hmap.put("no", no);
		hmap.put("bus_no", bus_no);
		hmap.put("bct_code", bct_code);

		// 기업명을 가져온다
		String bus_name = sDao.getBusName(hmap);
		System.out.println("bus_name=" + bus_name);
		// 업종명을 가져온다
		String bct_name = sDao.getBctName(hmap);
		System.out.println("bct_name=" + bct_name);
		// '기업+업종'의 총 리뷰개수를 가져온다
		String rev_count = sDao.getReviewCount(hmap);
		System.out.println("rev_count=" + rev_count);
		// '기업+업종'의 리뷰평점 평균값을 가져온다
		String rev_avg = sDao.getReviewAvg(hmap);
		System.out.println("rev_avg=" + rev_avg);
		
		//즐겨찾기
		int favorite = 0;
		char code = no.charAt(0);
		String favResult = null;
		if(code=='P') {
			favResult = sDao.getFavorite(hmap);
			if(favResult!=null) favorite = 1;
		}
		
		// 기업대표이미지를 가져온다
		hmap = sDao.getBusinessImage(hmap);
		String bus_img = hmap.get("GLR_LOC").toString() + hmap.get("GLR_FILE").toString();
		System.out.println("bus_img" + bus_img);
		
		// 기업이 제공하는 모든 서비스를 가져온다
		serviceList = sDao.getHaveService(bus_no);
		System.out.println("serviceList=" + serviceList);
		
		mav.addObject("no", no);
		mav.addObject("code", code);
		mav.addObject("bus_no", bus_no);
		mav.addObject("bct_code", bct_code);
		mav.addObject("favorite", favorite);
		mav.addObject("bus_img", bus_img);
		mav.addObject("bus_name", bus_name);
		mav.addObject("bct_name", bct_name);
		mav.addObject("rev_count", rev_count);
		mav.addObject("rev_avg", rev_avg);
		mav.addObject("serviceList", serviceList);
		view = "businessDetailPage";
		mav.setViewName(view);
		return mav;
	}

	public int favoriteChange(HttpServletRequest request) {
		int result = 0;
		HashMap<String, Object> hmap = new HashMap<>();
		hmap.put("per_no", request.getParameter("per_no"));
		hmap.put("bus_no", request.getParameter("bus_no"));		
		String action = request.getParameter("action");
		System.out.println("action="+action);
		if(action.equals("insert")) {
			System.out.println("action="+action);
			System.out.println(hmap.get("per_no").toString());
			System.out.println(hmap.get("bus_no").toString());
			result = sDao.favoriteInsert(hmap);
		}
		if(action.equals("delete")) {
			System.out.println("action="+action);
			System.out.println(hmap.get("per_no").toString());
			System.out.println(hmap.get("bus_no").toString());
			result = sDao.favoriteDelete(hmap);
		}		
		return result;
	}

	public ModelAndView personalCalendar(HttpSession session) {
		mav = new ModelAndView();
		String view = null;
		//회원번호
		//String no = "P1000001";
		String no = session.getAttribute("no").toString();
		List<HashMap<String, Object>> bookingList = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String,Object>> bList = new ArrayList<HashMap<String, Object>>();
		System.out.println("세션확인: " + no);
		
		//회원의 간략한 예약일정을 검색 (예약번호,펫이름,기업명,업종명,방문시간)
		bookingList = sDao.getPerCalendar(no);
		//System.out.println("serviceList=" + bookingList);		
		
		//최종 완성될 JSONObject 선언(전체)
        //JSONObject bookingObject = new JSONObject(); 
        //person의 JSON정보를 담을 Array 선언
        //JSONArray bookingArray = new JSONArray();
        //person의 하나의 예약 정보가 들어갈 JSONObject 선언
        //JSONObject bookingInfo = new JSONObject();
		
		mav.addObject("no", no);
		for(int i=0;i<bookingList.size();i++) {
			HashMap<String,Object> hmap = new HashMap<>();
			System.out.println("bookingList!!!!!!!!!!!!!!!" + bookingList.get(i));
			String bk_no = bookingList.get(i).get("BK_NO").toString();
			String petName = bookingList.get(i).get("PET_NAME").toString();
			String busName = bookingList.get(i).get("BUS_NAME").toString();
			String bctName = bookingList.get(i).get("BCT_NAME").toString();
			if(bctName.equals("병원")) {
				bctName="진료";
			}

			String start = bookingList.get(i).get("VS_START").toString();
			String end = bookingList.get(i).get("VS_END").toString();
			
			//데이터 입력
			hmap.put("title", petName+"-"+busName+"("+bctName+")");
			hmap.put("start", start);
			hmap.put("end", end);
			hmap.put("bk_no", bk_no);
			if(bctName.equals("진료")) {
				hmap.put("className", "bg-warning");
			}else if(bctName.equals("미용")) {
				hmap.put("className", "bg-success");
			}else {
				hmap.put("className", "bg-info");
			}
			bList.add(hmap);
		}
		System.out.println("hmap!!!!!!!!!!!!!!: " + bList);
		Gson gson =  new GsonBuilder().create();
		String json = gson.toJson(bList);
		System.out.println("json!!!!!!!!!!!!!1" + json);
		mav.addObject("e", json);
		//bookingObject.put("e",bookingArray);
		//System.out.println("bookingObject="+bookingObject.toJSONArray(bookingArray));
		System.out.println("확인: " + mav);
		view = "personalCalendar";
		mav.setViewName(view);
		return mav;
	}

}
