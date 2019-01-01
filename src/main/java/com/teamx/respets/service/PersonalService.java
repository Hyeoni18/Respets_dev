package com.teamx.respets.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamx.respets.bean.Personal;
import com.teamx.respets.bean.Pet;
import com.teamx.respets.dao.PersonalDao;
import com.teamx.respets.userClass.Paging;

@Service
public class PersonalService {
	ModelAndView mav;
	@Autowired
	private PersonalDao pDao;
	
	// 개인 최근 예약 목록
	public ModelAndView recentMyBookingList(HttpSession session, Integer pageNum) {
		mav = new ModelAndView();
		String view = null;
		String no = (String) session.getAttribute("no");
		int pNo = (pageNum == null) ? 1 : pageNum;
		HashMap<String, Object> hmap = new HashMap<>();
		List<HashMap<String, Object>> hList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		hmap.put("pageNum", pNo);
		hmap.put("no", no);
		hList = pDao.recentMyBookingList(hmap);
		for (int i = 0; i < hList.size(); i++) {
			sb.append("<tr><td><a href='./myBookingDetail?" + hList.get(i).get("BK_NO") + "'>"
					+ hList.get(i).get("BK_NO") + "</a></td>");
			sb.append("<td>" + hList.get(i).get("BUS_NAME") + "</td>");
			sb.append("<td>" + hList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + hList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + hList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + hList.get(i).get("BK_TIME") + "</td>");
			sb.append("<td>" + hList.get(i).get("VS_START") + "</td>");
			if (hList.get(i).get("BK_CHK").equals("승인")) {
				sb.append("<td class='text-success'>" + hList.get(i).get("BK_CHK") + "</td></tr>");
			} else if (hList.get(i).get("BK_CHK").equals("거절")) {
				sb.append("<td class='text-danger'>" + hList.get(i).get("BK_CHK") + "</td></tr>");
			} else if (hList.get(i).get("BK_CHK").equals("취소")) {
				sb.append("<td class='text-warning'>" + hList.get(i).get("BK_CHK") + "</td></tr>");
			} else {
				sb.append("<td class='text-info'>" + hList.get(i).get("BK_CHK") + "</td></tr>");
			}
		} // for End
		mav.addObject("hList", sb);
		mav.addObject("paging", getPagingRecent(pNo, session));
		view = "recentMyBookingList";
		mav.setViewName(view);
		return mav;
	}// recentMyBookingList End
	
	//개인 최근예약목록 페이징
	private String getPagingRecent(int pageNum, HttpSession session) { // 현재 페이지 번호
		String no = session.getAttribute("no").toString();
		int maxNum = pDao.recentMyBookingListCount(no); // 전체 글의 개수
		int listCount = 10; // 페이지당 글의 수
		int pageCount = 5; // 그룹당 페이지 수 [1] [2] [3] [4] [5] ▶ [6] [7]....
		String boardName = "recentMyBookingList"; // 게시판이 여러 개일 때 쓴다.
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, boardName);
		return paging.makeHtmlPaging();
	} // method End
	
	// 개인예약상세정보
	public ModelAndView myBookingDetail(HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		String bk_no = request.getQueryString();
		HashMap<String, Object> hmap = new HashMap<>();
		hmap = pDao.myBookingDetail(bk_no);
		String pet_no = hmap.get("PET_NO").toString();

		List<HashMap<String, Object>> mList = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> tList = new ArrayList<HashMap<String, Object>>();
		mList = pDao.getMenu(bk_no);
		tList = pDao.getPetDetail(pet_no);
		StringBuilder sb = new StringBuilder();

		// 메뉴
		for (int i = 0; i < mList.size(); i++) {
			if (mList.size() - i == 1) {
				sb.append("<span>" + mList.get(i).get("MENU_NAME") + "</span>");
			} else {
				sb.append("<span>" + mList.get(i).get("MENU_NAME") + ", </span>");
			}
			mav.addObject("mList", sb);
		} // for End

		// 반려동물상세
		for (int i = 0; i < tList.size(); i++) {
			System.out.println(tList.get(i).get("PCL_NAME") + " " + tList.get(i).get("PDT_CTT"));
			sb.append("<tr><td>" + tList.get(i).get("PCL_NAME") + "</td><td>" + tList.get(i).get("PDT_CTT")
					+ "</td></tr>");
			mav.addObject("tList", sb);
		} // for End

		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(hmap);
		mav.addObject("result", json);
		view = "myBookingDetail";
		mav.setViewName(view);
		return mav;
	} // myBookingDetail End

	/* 개인 회원정보 페이지 */
	public ModelAndView myInfo(HttpSession session) {
		mav = new ModelAndView();
		String view = null;
		Personal mb = new Personal();
		String no = session.getAttribute("no").toString();
		mb = pDao.myInfo(no);
		if (mb != null) {
			mav.addObject("mb", mb);
			view = "myInfo";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 개인 현제 비번과 받아온 비번 비교 ajax */
	public int myPwCheck(String now, HttpServletRequest request) {
		mav = new ModelAndView();
		Personal mb = new Personal();
		mb.setPer_no(request.getSession().getAttribute("no").toString());
		mb.setPer_pw(now);
		int result = pDao.myPwCheck(mb);
		return result;
	}

	/* 개인 비밀번호 수정 */
	public int myPwUpdate(String newPw, HttpSession session) {
		mav = new ModelAndView();
		Personal mb = new Personal();
		mb.setPer_no(session.getAttribute("no").toString());
		mb.setPer_pw(newPw);
		int update = pDao.myPwUpdate(mb);
		return update;
	}

	/* 개인정보 수정 페이지 */
	public ModelAndView myInfoUpdateForm(Personal mb, HttpSession session) {
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		mb = pDao.myInfo(no);
		String view = null;
		if (mb != null) {
			mav.addObject("mb", mb);
			view = "myInfoUpdateForm";
		} else {
			view = "myInfo";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 개인 정보 수정 */
	public ModelAndView myInfoUpdate(MultipartHttpServletRequest request) {
		mav = new ModelAndView();
		Personal p = new Personal();
		String no = request.getSession().getAttribute("no").toString();
		p.setPer_no(no);
		p.setPer_phone(request.getParameter("per_phone"));
		if (request.getParameter("fileCheck").equals("1")) {
			MultipartFile photo = request.getFile("mainPhoto");
			Map<String, Object> hMap = new HashMap<String, Object>();
			hMap = saveFile(request, photo, hMap);
			p.setPer_loc(hMap.get("per_loc").toString());
			p.setPer_photo(hMap.get("per_photo").toString());
			pDao.perPhotoUpdate(p);
			request.getSession().setAttribute("loc", p.getPer_loc());
			request.getSession().setAttribute("photo", p.getPer_photo());
		} else {
			pDao.perNoPhotoUpdate(p);
		}
		p = pDao.myInfo(no);
		mav.addObject("mb", p);
		return mav;
	}

	private Map<String, Object> saveFile(MultipartHttpServletRequest request, MultipartFile mainPhoto,
			Map<String, Object> map) {
		String root = request.getSession().getServletContext().getRealPath("/");
		String location = "resources/upload/";
		String path = root + location;
		File dir = new File(path);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String date = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
		String extension = mainPhoto.getOriginalFilename().substring(mainPhoto.getOriginalFilename().lastIndexOf(".") + 1);
		String saveName = "Respets_" + date + "_" + UUID.randomUUID() + "." + extension;
		try {
			mainPhoto.transferTo(new File(path, saveName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		map.put("per_loc", location);
		map.put("per_photo", saveName);
		return map;
	}

	/* 개인 회원탈퇴 */
	public ModelAndView personalPartDelete(HttpSession session) {
		mav = new ModelAndView();
		StringBuffer sb = new StringBuffer();
		String view = null;
		String no = session.getAttribute("no").toString();
		boolean result = pDao.personalPartDelete(no);
		if (result) {
			view = "loginForm";
		} else {
			sb.append("<script>alert('실패')</script>");
			mav.addObject("flas", sb);
			view = "myInfo";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 예약 취소 */
	public ModelAndView myBookingCancel(HttpServletRequest request) {
		mav = new ModelAndView();
		StringBuffer sb = new StringBuffer();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String view = null;
		String bk_no = request.getParameter("bk_no");
		Date start = pDao.getBkStart(bk_no);
		int count = cancleDateCheck(start);
		map.put("bk_no", bk_no);
		map.put("count", count);
		int result = pDao.cancelInsert(map);
		if (result != 0) {
			int update = pDao.bk_chkUpdate(bk_no);
			if (update != 0) {
				sb.append("<script>alert('예약 취소가 완료되었습니다.')</script>");
				mav.addObject("cancInsertSucess", sb);
				view = "redirect:/recentMyBookingList";
			} else {
				sb.append("<script>alert('실패')</script>");
				mav.addObject("flas", sb);
				view = "myBookingCancelPage";
			}
		}
		mav.setViewName(view);
		return mav;
	}

	private int cancleDateCheck(Date booking) {
		Date today = new Date();
		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(today);
		Calendar bookingCal = Calendar.getInstance();
		bookingCal.setTime(booking);
		int count = 0;
		while (!todayCal.after(bookingCal)) {
			if (todayCal != bookingCal) {
				count++;
			}
			todayCal.add(Calendar.DATE, 1);
		}
		if (count != 0) {
			System.out.println("방문예정일 까지 " + count + "일 남았습니다.");
		} else {
			System.out.println("방문예정일이 지났습니다.");
		}
		return count;
	}

	/* 개인 예약 전체리스트 */
	public ModelAndView personalAllBookingList(HttpSession session, Integer pageNum) {
		mav = new ModelAndView();
		int page_no = (pageNum == null) ? 1 : pageNum;
		StringBuilder sb = new StringBuilder();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String no = session.getAttribute("no").toString();
		map.put("no", no);
		map.put("page_no", page_no);
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		allList = pDao.allBookingList(map);
		if (allList != null) {
			for (int i = 0; i < allList.size(); i++) {
				String bk_no = (String) allList.get(i).get("BK_NO");
				sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td><td>"
						+ allList.get(i).get("BUS_NAME") + "</td><td>" + allList.get(i).get("PTY_NAME") + "</td><td>"
						+ allList.get(i).get("PET_NAME") + "</td><td>" + allList.get(i).get("PER_NAME") + "</td><td>"
						+ allList.get(i).get("BK_TIME") + "</td><td>" + allList.get(i).get("VS_START") + "</td>");
				if (allList.get(i).get("BK_CHK").equals("승인")) {
					sb.append("<td class='text-success'>" + allList.get(i).get("BK_CHK") + "</td></tr>");
				} else if (allList.get(i).get("BK_CHK").equals("거절")) {
					sb.append("<td class='text-danger'>" + allList.get(i).get("BK_CHK") + "</td></tr>");
				} else if (allList.get(i).get("BK_CHK").equals("취소")) {
					sb.append("<td class='text-warning'>" + allList.get(i).get("BK_CHK") + "</td></tr>");
				} else {
					sb.append("<td class='text-info'>" + allList.get(i).get("BK_CHK") + "</td></tr>");
				}
			}
			mav.addObject("allList", sb);
			String paging = BookingListPaging(page_no, no);
			mav.addObject("paging", paging);
		}
		mav.setViewName("allBookingList");
		return mav;
	}

	/* 개인 예약 전체리스트 페이징 */
	private String BookingListPaging(int page_no, String no) {
		int maxNum = pDao.contPerBkList(no);
		System.out.println(maxNum);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "personalAllBookingList";
		Paging paging = new Paging(maxNum, page_no, listCount, pageCount, boardName);
		return paging.BookingListPaging(no);
	}
	
	/* 메뉴 나의 동물 정보 클릭 시 실행 */
	public ModelAndView petList(HttpSession session) { // userId는 이메일
		ArrayList<Pet> petList = new ArrayList<Pet>();
		mav = new ModelAndView();
		String view = null;
		petList = null;
		String no = session.getAttribute("no").toString();
		char code = no.charAt(0);
		if (code == 'P') {
			// 검색한 회원번호로 반려동물 리스트 불러오기
			petList = pDao.getPetList(no);
			mav.addObject("per_no", no);// 회원번호 담기
			if (petList.size() > 0) {// 리스트가 존재하면
				mav.addObject("petList", petList);// 반려동물 리스트 담기
			}
			view = "petList";
			mav.setViewName(view);
		} else {
			view = "lock-screen-bus";
			mav.setViewName(view);
		}
		return mav;
	}

	/* 반려동물 등록 폼에서 [등록 완료] 버튼 클릭 시 실행 */
	public ModelAndView petInsert(MultipartHttpServletRequest multi) {
		Pet pet = new Pet();
		String ptyNo = multi.getParameter("pty_no");
		String ptyEtc = multi.getParameter("pty_etc");
		// 종류가 기타이면
		if (ptyNo.equals("etc")) {
			// 동물종류TB에 존재하는 값인지 select한다
			String getPtyNo = pDao.getPetTypeNo(ptyEtc);
			if (getPtyNo == null) {// 기타값이 종류TB에 존재하지 않으면
				// 동물종류TB에 새로운 데이터를 insert한다
				boolean typeInsertResult = pDao.petTypeInsert(ptyEtc);
				if (typeInsertResult) {// insert성공하면
					// 동물종류번호를 select한다
					getPtyNo = pDao.getPetTypeNo(ptyEtc);
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

		Map<String, String> fMap = new HashMap<String, String>();
		boolean petInsertResult = false; // pet:PET_TB시너님
		if (check == 1) {// 파일을 첨부했을 때
			FileService upload = new FileService();
			fMap = upload.upload(multi);// 서버에 파일을 업로드, 오리지널파일명·시스템파일명·경로를 Map에 저장
			pet.setPet_photo(fMap.get("sysFileName"));
			pet.setPet_loc(fMap.get("location"));
			petInsertResult = pDao.petAndPhotoInsert(pet);
			if (petInsertResult)
				System.out.println("사진 첨부 OK 인서트 성공");
		} else {// 파일을 첨부하지 않았을 때
			petInsertResult = pDao.petInsert(pet);
			if (petInsertResult)
				System.out.println("사진 첨부 NO 인서트 성공");
		}
		/* 선택입력사항에 값이 있면 insert */
		if (!pet.getPet_wght().equals("미입력")) {
			pet.setPcl_no(1);
			pet.setPdt_ctt(pet.getPet_wght());
			pDao.pdtInsert(pet);
		}
		if (!pet.getPet_sick().equals("미입력")) {
			pet.setPcl_no(2);
			pet.setPdt_ctt(pet.getPet_sick());
			pDao.pdtInsert(pet);
		}
		if (!pet.getPet_mday().equals("미입력")) {
			pet.setPcl_no(3);
			pet.setPdt_ctt(pet.getPet_mday());
			pDao.pdtInsert(pet);
		}
		if (!pet.getPet_food().equals("미입력")) {
			pet.setPcl_no(4);
			pet.setPdt_ctt(pet.getPet_food());
			pDao.pdtInsert(pet);
		}
		if (!pet.getPet_rat().equals("미입력")) {
			pet.setPcl_no(5);
			pet.setPdt_ctt(pet.getPet_rat());
			pDao.pdtInsert(pet);
		}
		if (!pet.getPet_tot().equals("미입력")) {
			pet.setPcl_no(6);
			pet.setPdt_ctt(pet.getPet_tot());
			pDao.pdtInsert(pet);
		}
		if (!pet.getPet_etc().equals("미입력")) {
			pet.setPcl_no(7);
			pet.setPdt_ctt(pet.getPet_etc());
			pDao.pdtInsert(pet);
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
		pet = pDao.getPetInfoDetail(pet_no);
		if (pet != null) {// 반려동물 상세정보가 존재하면
			String ptyName = pDao.getPetTypeName(pet.getPty_no());
			pet.setPty_name(ptyName);
			for (int pclNo = 1; pclNo <= 7; pclNo++) {
				pet.setPcl_no(pclNo);
				String getPdtCtt = pDao.getPdtCtt(pet);
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
		mav = new ModelAndView();
		String view = null;
		Pet pet = new Pet();
		// 동물번호로 반려동물 상세정보 불러오기
		pet = pDao.getPetInfoDetail(pet_no);
		if (pet != null) {// 반려동물 상세정보가 존재하면
			String ptyName = pDao.getPetTypeName(pet.getPty_no());
			pet.setPty_name(ptyName);
			for (int pclNo = 1; pclNo <= 7; pclNo++) {
				pet.setPcl_no(pclNo);
				String getPdtCtt = pDao.getPdtCtt(pet);
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
			// 동물종류TB에 존재하는 값인지 select한다
			String getPtyNo = pDao.getPetTypeNo(ptyEtc);
			if (getPtyNo == null) {// 기타값이 종류TB에 존재하지 않으면
				// 동물종류TB에 새로운 데이터를 insert한다
				boolean typeInsertResult = pDao.petTypeInsert(ptyEtc);
				if (typeInsertResult) {// insert성공하면
					// 동물종류번호를 select한다
					getPtyNo = pDao.getPetTypeNo(ptyEtc);
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
			pet.setPet_loc(fMap.get("location"));
			petUpdateResult = pDao.petAndPhotoUpdate(pet);
			if (petUpdateResult)
				System.out.println("사진 수정 OK 업데이트 성공");
		} else {// 파일을 첨부하지 않았을 때
			System.out.println("pet.getPer_no=" + pet.getPer_no());
			petUpdateResult = pDao.petUpdate(pet);
			if (petUpdateResult)
				System.out.println("사진 수정 NO 업데이트 성공");
		}

		/* 선택입력사항에 값이 있면 기존에 값이 있으면 update 없었으면 insert */
		String getPdt = null;

		pet.setPcl_no(1);
		pet.setPdt_ctt(pet.getPet_wght());
		getPdt = pDao.getPdtCtt(pet);
		if (!pet.getPet_wght().equals("미입력")) {
			if (getPdt == null)
				pDao.pdtInsert(pet);
			else
				pDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				pDao.pdtDelete(pet);
		}
		pet.setPcl_no(2);
		pet.setPdt_ctt(pet.getPet_sick());
		getPdt = pDao.getPdtCtt(pet);
		if (!pet.getPet_sick().equals("미입력")) {
			if (getPdt == null)
				pDao.pdtInsert(pet);
			else
				pDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				pDao.pdtDelete(pet);
		}
		pet.setPcl_no(3);
		pet.setPdt_ctt(pet.getPet_mday());
		getPdt = pDao.getPdtCtt(pet);
		if (!pet.getPet_mday().equals("미입력")) {

			if (getPdt == null)
				pDao.pdtInsert(pet);
			else
				pDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				pDao.pdtDelete(pet);
		}
		pet.setPcl_no(4);
		pet.setPdt_ctt(pet.getPet_food());
		getPdt = pDao.getPdtCtt(pet);
		if (!pet.getPet_food().equals("미입력")) {
			if (getPdt == null)
				pDao.pdtInsert(pet);
			else
				pDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				pDao.pdtDelete(pet);
		}
		pet.setPcl_no(5);
		pet.setPdt_ctt(pet.getPet_rat());
		getPdt = pDao.getPdtCtt(pet);
		if (!pet.getPet_rat().equals("미입력")) {
			if (getPdt == null)
				pDao.pdtInsert(pet);
			else
				pDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				pDao.pdtDelete(pet);
		}
		pet.setPcl_no(6);
		pet.setPdt_ctt(pet.getPet_tot());
		getPdt = pDao.getPdtCtt(pet);
		if (!pet.getPet_tot().equals("미입력")) {
			if (getPdt == null)
				pDao.pdtInsert(pet);
			else
				pDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				pDao.pdtDelete(pet);
		}
		pet.setPcl_no(7);
		pet.setPdt_ctt(pet.getPet_etc());
		getPdt = pDao.getPdtCtt(pet);
		if (!pet.getPet_etc().equals("미입력")) {
			if (getPdt == null)
				pDao.pdtInsert(pet);
			else
				pDao.pdtUpdate(pet);
		} else {
			if (getPdt != null)
				pDao.pdtDelete(pet);
		}

		/* 해당 뷰페이지로 포워딩 */
		mav = new ModelAndView();
		String view = null;
		if (petUpdateResult) {
			mav.addObject("pet_no", pet.getPet_no());
			view = "redirect:petInfoDetail";
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
		List<HashMap<String, Object>> pdt = pDao.getPdt(pet_no);
		if (pdt.size() != 0 || pdt.size() < 0) {
			if (pDao.pdtAllDelete(pet_no)) {
				if (pDao.petDelete(pet_no)) {// 반려동물 정보 삭제에 성공하면
					view = "redirect:petList";
				} else {// 반려동물 정보 삭제에 실패하면
					mav.addObject("Fail", "<script>alert('petDelete실패')</script>");
					view = "petInfoDetail";
				}
			} else {// 반려동물 정보 삭제에 실패하면
				mav.addObject("Fail", "<script>alert('pdtAllDelete실패')</script>");
				view = "petInfoDetail";
			}
		} else {
			if (pDao.petDelete(pet_no)) {// 반려동물 정보 삭제에 성공하면
				view = "redirect:petList";
			} else {// 반려동물 정보 삭제에 실패하면
				mav.addObject("Fail", "<script>alert('petDelete실패')</script>");
				view = "petInfoDetail";
			}
		}
		mav.setViewName(view);
		return mav;
	}
	
	/* 개인 캘린더 */
	@DateTimeFormat(iso=ISO.DATE)
	public ModelAndView personalCalendar(HttpSession session) {
		mav = new ModelAndView();
		String view = null;
		// 회원번호
		String no = session.getAttribute("no").toString();
		List<HashMap<String, Object>> bookingList = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		// 회원의 간략한 예약일정을 검색 (예약번호,펫이름,기업명,업종명,방문시간)
		bookingList = pDao.getPerCalendar(no);

		mav.addObject("no", no);
		for (int i = 0; i < bookingList.size(); i++) {
			HashMap<String, Object> hmap = new HashMap<>();
			String bk_no = bookingList.get(i).get("BK_NO").toString();
			String petName = bookingList.get(i).get("PET_NAME").toString();
			String busName = bookingList.get(i).get("BUS_NAME").toString();
			String bctName = bookingList.get(i).get("BCT_NAME").toString();
			String bus_addr = bookingList.get(i).get("BUS_ADDR").toString();
			String bus_addr2 = bookingList.get(i).get("BUS_ADDR2").toString();
			
			if (bctName.equals("병원")) {
				bctName = "진료";
			}
			
			String start = bookingList.get(i).get("VS_START").toString();
			String end = bookingList.get(i).get("VS_END").toString();
			// 데이터 입력
			hmap.put("title", petName + ": " + busName + "[" + bctName + "]");
			hmap.put("start", start);
			hmap.put("end", end);
			hmap.put("bk_no", bk_no);
			hmap.put("pet_name", petName);
			hmap.put("bus_name", busName);
			hmap.put("bct_name", bctName);
			hmap.put("bus_addr", bus_addr);
			hmap.put("bus_addr2", bus_addr2);
			
			if (bctName.equals("진료")) {
				hmap.put("className", "bg-warning");
			} else if (bctName.equals("미용")) {
				hmap.put("className", "bg-success");
			} else {
				hmap.put("className", "bg-info");
			}
			bList.add(hmap);
		}
		//json		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(bList);
		mav.addObject("e", json);
		view = "personalCalendar";
		mav.setViewName(view);
		return mav;
	}
	
	// 개인 즐겨찾기 페이지
	public String likeBusinessList(HttpServletRequest request) {
		String per_no = request.getSession().getAttribute("no").toString();
		List<HashMap<String, String>> list = pDao.likeBusinessSelect(per_no);
		String sb = makeLikeBusListHtml(list);
		return sb;
	} // method End

	// 개인 즐겨찾기 페이지 Html 생성
	private String makeLikeBusListHtml(List<HashMap<String, String>> list) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append("<div class='col-lg-4' style='padding:0;'>");
			sb.append("<div class='card d-block' style='text-align: center;margin-bottom:20px;'>");
			sb.append("<a href='businessDetailPage?bus_no=" + list.get(i).get("BUS_NO") + "&bct_code=");
			sb.append(list.get(i).get("BCT_CODE") + "'><img class='rounded-circle img-thumbnail' id='petProfile' src='");
			sb.append(list.get(i).get("GLR_LOC") + list.get(i).get("GLR_FILE") + "'>");
			sb.append("</a><div class='card-body'><h5 class='card-title'>");
			sb.append(list.get(i).get("BUS_NAME") + "</h5><br/>");
			sb.append("<a class='btn btn-outline-danger btn-rounded' href='./likeBusinessCancel?bus_no=");
			sb.append(list.get(i).get("BUS_NO") + "' onclick='return check();'>삭제</a></div>");
			sb.append("</div></div>");
		} // for End
		return sb.toString();
	} // method End

	// 개인 즐겨찾기 삭제
	public void likeBusinessCancel(HttpServletRequest request) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("per_no", request.getSession().getAttribute("no").toString());
		hMap.put("bus_no", request.getParameter("bus_no"));
		pDao.likeBusinessDelete(hMap);
	} // method End
}
