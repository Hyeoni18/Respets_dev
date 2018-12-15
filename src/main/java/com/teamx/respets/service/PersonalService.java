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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamx.respets.bean.Personal;
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
}
