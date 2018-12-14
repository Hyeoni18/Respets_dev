package com.teamx.respets.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Gallery;
import com.teamx.respets.bean.Personal;
import com.teamx.respets.dao.HyeonDao;
import com.teamx.respets.userClass.Paging;

@Service
public class HyeonService {
	private ModelAndView mav;
	@Autowired
	private HyeonDao hyDao;

	/* 개인 회원정보 페이지 */
	public ModelAndView myInfo(HttpSession session) {
		mav = new ModelAndView();
		String view = null;
		Personal mb = new Personal();
		String no = session.getAttribute("no").toString();
		mb = hyDao.myInfo(no);
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
		int result = hyDao.myPwCheck(mb);
		return result;
	}

	/* 개인 비밀번호 수정 */
	public int myPwUpdate(String newPw, HttpSession session) {
		mav = new ModelAndView();
		Personal mb = new Personal();
		mb.setPer_no(session.getAttribute("no").toString());
		mb.setPer_pw(newPw);
		int update = hyDao.myPwUpdate(mb);
		return update;
	}

	/* 개인정보 수정 페이지 */
	public ModelAndView myInfoUpdateForm(Personal mb, HttpSession session) {
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		mb = hyDao.myInfo(no);
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
			hyDao.perPhotoUpdate(p);
			request.getSession().setAttribute("loc", p.getPer_loc());
			request.getSession().setAttribute("photo", p.getPer_photo());
		} else {
			hyDao.perNoPhotoUpdate(p);
		}
		p = hyDao.myInfo(no);
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
		boolean result = hyDao.personalPartDelete(no);
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
		Date start = hyDao.getBkStart(bk_no);
		int count = cancleDateCheck(start);
		map.put("bk_no", bk_no);
		map.put("count", count);
		int result = hyDao.cancelInsert(map);
		if (result != 0) {
			int update = hyDao.bk_chkUpdate(bk_no);
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
		allList = hyDao.allBookingList(map);
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
		int maxNum = hyDao.contPerBkList(no);
		System.out.println(maxNum);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "personalAllBookingList";
		Paging paging = new Paging(maxNum, page_no, listCount, pageCount, boardName);
		return paging.BookingListPaging(no);
	}

	/* 기업 서비스 버튼 불러오기 */
	public ModelAndView todayScheduleList(HttpSession session) {
		mav = new ModelAndView();
		String view = null;
		StringBuilder sb = new StringBuilder();
		String no = session.getAttribute("no").toString();
		ArrayList<HashMap<String, Object>> sMap = hyDao.getSvcPri(no);
		if (sMap.size() != 0) {
			for (int i = 0; i < sMap.size(); i++) {
				String svc = (String) sMap.get(i).get("BCT_NAME");
				String code = (String) sMap.get(i).get("BCT_CODE");
				sb.append("&emsp;&emsp; <input type='radio' name='radio' onchange='test();' class='" + code
						+ "' value='" + svc + "'>" + svc);
			}
			mav.addObject("bctList", sb);
			view = "todayScheduleList";
		} else {
			sb.append("<h1>목록이 없습니다.</h1>");
			mav.addObject("none", sb);
			view = "todayScheduleList";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 예약 상세피이지 */
	public ModelAndView myBookingDetail(HttpServletRequest request) {
		mav = new ModelAndView();
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		String view = null;
		String bk_no = request.getQueryString();
		String chk = hyDao.getBk_chk(bk_no);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map = hyDao.myBookingDetail(bk_no); // 예약 리스트로 가져오기
		ArrayList<HashMap<String, Object>> menu = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();
		String pet_no = map.get("PET_NO").toString();
		menu = hyDao.getMenu(bk_no); //서비스 메뉴 불러오기
		pList = hyDao.getPetList(pet_no); // 펫 디테일 리스트로 가져오기
		if (map != null) {
			for (int i = 0; i < menu.size(); i++) {
				if (menu.size() - i == 1) {
					sb2.append("<span>" + menu.get(i).get("MENU_NAME") + "</span>");
				} else {
					sb2.append("<span>" + menu.get(i).get("MENU_NAME") + ", </span>");
				}
				mav.addObject("mList", sb2);
			}
			for (int i = 0; i < pList.size(); i++) {
				if (pList.size() - i == 1) {
					sb.append("<tr><td>" + pList.get(i).get("PCL_NAME") + "</td><td>" + pList.get(i).get("PDT_CTT")
							+ "</td></tr>");
				}
				mav.addObject("pList", sb);
			}
		}
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(map);
		mav.addObject("result", json);
		mav.addObject("bk_no", bk_no);
		mav.addObject("chk", chk);
		view = "myBookingDetail";
		mav.setViewName(view);
		return mav;
	}

	/* 오늘 예약 방문 확인 ajax */
	public String todayScheduleListCheck(HttpServletRequest request) {
		String bk_no = request.getParameter("bk_no");
		hyDao.todayScheduleListCheck(bk_no);
		String changeList = todayAllScheduleList(request);
		return changeList;
	}

	/* 오늘 예약 방문취소 ajax */
	public String todayScheduleListCancel(HttpServletRequest request) {
		String bk_no = request.getParameter("bk_no");
		hyDao.todayScheduleListCancel(bk_no);
		String changeList = todayAllScheduleList(request);
		return changeList;
	}

	/* 서비스별 예약 리스트 ajax */
	public String bctBookingListCheck(HttpServletRequest request) {
		String bk_no = request.getParameter("bk_no");
		hyDao.todayScheduleListCheck(bk_no);
		String changeList = bctBookingList(request);
		return changeList;
	}

	/* 서비스별 예약 방문 취소 ajax */
	public String bctBookingListCancel(HttpServletRequest request) {
		String bk_no = request.getParameter("bk_no");
		hyDao.todayScheduleListCancel(bk_no);
		String changeList = bctBookingList(request);
		return changeList;
	}

	/* 방문완료 예약리스트 ajax */
	public String vs_chkOkList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("bus_no", request.getParameter("bus_no"));
		map.put("bk_no", request.getParameter("bk_no"));
		map.put("timeS", timeS);
		ArrayList<HashMap<String, Object>> okList = hyDao.vs_chkOkList(map);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < okList.size(); i++) {
			String bk_no = (String) okList.get(i).get("BK_NO");
			sb.append("<div name='list' id='" + bk_no + "'><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a> | "
					+ okList.get(i).get("PTY_NAME") + " | " + okList.get(i).get("PET_NAME") + " | "
					+ okList.get(i).get("PER_NAME") + " | " + okList.get(i).get("BCT_NAME") + " | "
					+ okList.get(i).get("BK_TIME") + " | " + okList.get(i).get("VS_START") + "<br/> 방문완료 </div><br>");

		}
		return sb.toString();
	}

	/* 업종 종류 불러오기(전체예약페이지) */
	public ModelAndView businessBookingList(HttpSession session) {
		mav = new ModelAndView();
		StringBuilder sb = new StringBuilder();
		String no = session.getAttribute("no").toString();
		ArrayList<HashMap<String, Object>> sMap = hyDao.getSvcPri(no);
		if (sMap.size() != 0) {
			for (int i = 0; i < sMap.size(); i++) {
				String svc = (String) sMap.get(i).get("BCT_NAME");
				sb.append("<option>" + svc + "</option>");
			}
			mav.addObject("bctList", sb);
		}
		mav.setViewName("businessBookingListPage");
		return mav;
	}

	/* 서비스 종류 불러오기(서비스페이지) */
	public ModelAndView serviceManagement(HttpSession session) {
		mav = new ModelAndView();
		StringBuilder sb = new StringBuilder();
		String view = null;
		String no = session.getAttribute("no").toString();
		ArrayList<HashMap<String, Object>> servList = new ArrayList<HashMap<String, Object>>();
		servList = hyDao.serviceManagement(no);
		if (servList != null) {
			for (int i = 0; i < servList.size(); i++) {
				String bct_code = (String) servList.get(i).get("BCT_CODE");
				String code = null;
				if (bct_code.equals("M")) {
					code = "mdi mdi-hospital";
				} else if (bct_code.equals("B")) {
					code = "mdi mdi-content-cut";
				} else if (bct_code.equals("H")) {
					code = "mdi mdi-hotel";
				}
				sb.append("<div class='col-md-4'><div class='card card-pricing'><div class='card-body text-center'>");
				sb.append("<a href='serviceDetail?bct_code=" + bct_code + "'><i class='card-pricing-icon " + code
						+ "'></i></a><br/><br/>" + "<h2 class='card-pricing-price'>" + servList.get(i).get("BCT_NAME")
						+ "</h2>");
				sb.append("</div></div></div>");
			}
			mav.addObject("servList", sb);
		} else {
			sb.append("서비스를 등록해주세요");
			mav.addObject("add", sb);
		}
		view = "servicePage";
		mav.setViewName(view);
		return mav;
	}

	/* 기업 정보 페이지 */
	public ModelAndView businessInfoDetail(HttpSession session) {
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		HashMap<String, Object> bmap = new HashMap<>();
		bmap = hyDao.businessInfo(no);
		String view = null;
		if (bmap != null) {
			String glr_file = (String) bmap.get("GLR_FILE");
			String glr_loc = (String) bmap.get("GLR_LOC");
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(bmap);
			mav.addObject("result", json);
			mav.addObject("bmap", bmap);
			mav.addObject("glr_loc", glr_loc);
			mav.addObject("glr_file", glr_file);
			view = "businessInfoDetail";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 기업정보 수정 */
	public ModelAndView businessInfoUpdate(MultipartHttpServletRequest request) {
		mav = new ModelAndView();
		HashMap<String, Object> bmap = new HashMap<String, Object>();
		Business b = new Business();
		String no = request.getSession().getAttribute("no").toString();
		Gallery gy = new Gallery();
		if (request.getParameter("fileCheck").equals("1")) {
			b.setBus_no(no);
			b.setBus_phone(request.getParameter("bus_phone"));
			b.setBus_post(request.getParameter("bus_post"));
			b.setBus_addr(request.getParameter("bus_addr"));
			b.setBus_addr2(request.getParameter("bus_addr2"));
			hyDao.businessInfoUpdate(b);
			MultipartFile photo = request.getFile("mainPhoto");
			Map<String, Object> hMap = new HashMap<String, Object>();
			hMap = bussaveFile(request, photo, hMap);
			gy.setBus_no(request.getSession().getAttribute("no").toString());
			gy.setGlr_loc(hMap.get("glr_loc").toString());
			gy.setGlr_file(hMap.get("glr_file").toString());
			hyDao.mainPhotoUpdate(gy);
		}
		bmap = hyDao.businessInfo(no);
		String glr_file = (String) bmap.get("GLR_FILE");
		String glr_loc = (String) bmap.get("GLR_LOC");
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<img id='perProfile' style='width: 150px; height: 150px; margin-top: 15px; margin-left: 20px;' class='rounded-circle img-thumbnail' src='"
						+ glr_loc + glr_file + "'/>");
		mav.addObject("bmap", bmap);
		mav.addObject("img", sb);
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(bmap);
		mav.addObject("result", json);
		request.getSession().setAttribute("loc", glr_loc);
		request.getSession().setAttribute("photo", glr_file);
		mav.setViewName("redirect:/businessInfoDetail");
		return mav;
	}

	private Map<String, Object> bussaveFile(MultipartHttpServletRequest request, MultipartFile photo,
			Map<String, Object> hMap) {
		String root = request.getSession().getServletContext().getRealPath("/");
		String location = "resources/upload/";
		String path = root + location;
		File dir = new File(path);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String date = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
		String extension = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
		String saveName = "Respets_" + date + "_" + UUID.randomUUID() + "." + extension;
		try {
			photo.transferTo(new File(path, saveName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		hMap.put("glr_loc", location);
		hMap.put("glr_file", saveName);
		return hMap;
	}

	/* 기업 회원탈퇴 */
	public ModelAndView businessPartDelete(HttpSession session) {
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		boolean result = hyDao.businessPartDelete(no);
		if (result) {
			session.invalidate();
		}
		mav.setViewName("redirect:/");
		return mav;
	}

	/* 서비스별 전체예약 리스트 ajax */
	public String bctBookingList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		String no = request.getParameter("no");
		String bct_name = request.getParameter("bct_name");
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("no", no);
		map.put("timeS", timeS);
		map.put("bct_name", bct_name);
		bList = hyDao.bctBookingList(map);
		sb.append(
				"<table class='table table-centered mb-0' style='text-align:center;'><thead><tr><th width='13%'>예약 번호</th><th width='13%'>동물 종류</th><th width='13%'>동물 이름</th><th width='13%'>예약자명</th><th width='13%'>서비스 종류</th><th>방문 일시</th><th width='13%'>방문 상태</th></tr></thead>");
		for (int i = 0; i < bList.size(); i++) {
			String bk_no = (String) bList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td>");
			sb.append("<td>" + bList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("VS_START") + "</td>");
			sb.append("<td><input type='button' class='btn btn-outline-success' value='방문' onclick=\"com(\'" + bk_no
					+ "')\" /></td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/* 서비스별 방문확인 된 전체예약 리스트 ajax */
	public String bctBookingListOk(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> okList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		String no = request.getParameter("no");
		String bct_name = request.getParameter("bct_name");
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("no", no);
		map.put("timeS", timeS);
		map.put("bct_name", bct_name);
		okList = hyDao.bctBookingListOk(map);
		sb.append(
				"<table class='table table-centered mb-0' style='text-align:center;'><thead><tr><th width='13%'>예약 번호</th><th width='13%'>동물 종류</th><th width='13%'>동물 이름</th><th width='13%'>예약자명</th><th width='13%'>서비스 종류</th><th>방문 일시</th><th width='13%'>방문 상태</th></tr></thead>");
		for (int i = 0; i < okList.size(); i++) {
			String bk_no = (String) okList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td>");
			sb.append("<td>" + okList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("VS_START") + "</td>");
			sb.append("<td><button type='button' class='btn btn-outline-danger' onclick='cancelCheck(\"" + bk_no
					+ "\")'> 취소 </button></td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/* 오늘 예약 일정 전체 ajax */
	public String todayAllScheduleList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		String no = request.getParameter("no");
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("no", no);
		map.put("timeS", timeS);
		bList = hyDao.todayScheduleList(map);
		sb.append(
				"<table class='table table-centered mb-0' style='text-align:center;'><thead><tr><th width='13%'>예약 번호</th><th width='13%'>동물 종류</th><th width='13%'>동물 이름</th><th width='13%'>예약자명</th><th width='13%'>서비스 종류</th><th>방문 일시</th><th width='13%'>방문 상태</th></tr></thead>");
		for (int i = 0; i < bList.size(); i++) {
			String bk_no = (String) bList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td>");
			sb.append("<td>" + bList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("VS_START") + "</td>");
			sb.append("<td><input type='button' class='btn btn-outline-success' value='방문' onclick=\"com(\'" + bk_no
					+ "')\" /></td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/* 오늘 방문 확인된 예약 리스트 ajax */
	public String todayAllScheduleListOk(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> okList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		String no = request.getParameter("no");
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("no", no);
		map.put("timeS", timeS);
		okList = hyDao.todayScheduleListOk(map);
		sb.append(
				"<table class='table table-centered mb-0' style='text-align:center;'><thead><tr><th width='13%'>예약 번호</th><th width='13%'>동물 종류</th><th width='13%'>동물 이름</th><th width='13%'>예약자명</th><th width='13%'>서비스 종류</th><th>방문 일시</th><th width='13%'>방문 상태</th></tr></thead>");
		for (int i = 0; i < okList.size(); i++) {
			String bk_no = (String) okList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td>");
			sb.append("<td>" + okList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("VS_START") + "</td>");
			sb.append("<td><button type='button' class='btn btn-outline-danger' onclick='cancelCheck(\"" + bk_no
					+ "\")'> 취소 </button></td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/* 기업 전체예약 리스트 ajax */
	public String businessAllBookingList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String no = (String) request.getSession().getAttribute("no");
		System.out.println("session" + no);
		map.put("no", no);
		map.put("pageNum", pNo);
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		bList = hyDao.businessBookingList(map);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bList.size(); i++) {
			String bk_no = (String) bList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td><td>"
					+ bList.get(i).get("PTY_NAME") + "</td><td>" + bList.get(i).get("PET_NAME") + "</td><td>"
					+ bList.get(i).get("PER_NAME") + "</td><td>" + bList.get(i).get("BCT_NAME") + "</td><td>"
					+ bList.get(i).get("VS_START") + "</td></tr>");
		}
		return sb.toString();
	}

	/* 전체 예약에서의 검색 ajax */
	public String searchAllList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bPerList = new ArrayList<HashMap<String, Object>>();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String no = request.getSession().getAttribute("no").toString();
		String search = request.getParameter("search");
		StringBuilder sb = new StringBuilder();
		map.put("no", no);
		map.put("per_name", search);
		map.put("pageNum", pNo);
		bPerList = hyDao.searchAllList(map);
		if (bPerList.size() != 0) {
			for (int i = 0; i < bPerList.size(); i++) {
				String bk_no = (String) bPerList.get(i).get("BK_NO");
				sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td><td>"
						+ bPerList.get(i).get("PTY_NAME") + "</td><td>" + bPerList.get(i).get("PET_NAME") + "</td><td>"
						+ bPerList.get(i).get("PER_NAME") + "</td><td>" + bPerList.get(i).get("BCT_NAME") + "</td><td>"
						+ bPerList.get(i).get("VS_START") + "</td></tr>");
			}
		}
		return sb.toString();
	}

	/* 전체 예약에서의 검색 페이징 ajax */
	public String searchAllListPaging(HttpServletRequest request) {
		String no = request.getSession().getAttribute("no").toString();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String search = request.getParameter("search");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("per_name", search);
		int maxNum = hyDao.searchAllListPaging(map);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "businessBookingList"; // 사용안함
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.searchAllList(search);
	}

	/* 서비스별 전체 예약 리스트 ajax */
	public String businessAllBctBookingList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String no = request.getSession().getAttribute("no").toString();
		String bct_name = request.getParameter("bct_name");
		map.put("no", no);
		map.put("bct_name", bct_name);
		map.put("pageNum", pNo);
		bList = hyDao.AllbctBookingList(map);
		if (bList != null) {
			for (int i = 0; i < bList.size(); i++) {
				String bk_no = (String) bList.get(i).get("BK_NO");
				sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td><td>"
						+ bList.get(i).get("PTY_NAME") + "</td><td>" + bList.get(i).get("PET_NAME") + "</td><td>"
						+ bList.get(i).get("PER_NAME") + "</td><td>" + bList.get(i).get("BCT_NAME") + "</td><td>"
						+ bList.get(i).get("VS_START") + "</td></tr>");

			}
		}
		return sb.toString();
	}

	/* 서비스별 예약에서의 검색 ajax */
	public String searchBctAllsList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String search = request.getParameter("search");
		String bct_name = request.getParameter("bct_name");
		StringBuilder sb = new StringBuilder();
		String no = request.getSession().getAttribute("no").toString();
		map.put("no", no);
		map.put("bct_name", bct_name);
		map.put("per_name", search);
		map.put("pageNum", pNo);
		bList = hyDao.searchBctAllsList(map);
		if (bList != null) {
			for (int i = 0; i < bList.size(); i++) {
				String bk_no = (String) bList.get(i).get("BK_NO");
				sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td><td>"
						+ bList.get(i).get("PTY_NAME") + "</td><td>" + bList.get(i).get("PET_NAME") + "</td><td>"
						+ bList.get(i).get("PER_NAME") + "</td><td>" + bList.get(i).get("BCT_NAME") + "</td><td>"
						+ bList.get(i).get("VS_START") + "</td></tr>");
			}
		}
		return sb.toString();
	}

	/* 서비스별 예약에서의 검색 페이징 ajax */
	public String searchBctAllsListPaging(HttpServletRequest request) {
		String no = request.getSession().getAttribute("no").toString();
		String bct_name = request.getParameter("bct_name");
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String search = request.getParameter("search");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("bct_name", bct_name);
		map.put("per_name", search);
		int maxNum = hyDao.searchBctAllsListPaging(map);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "businessBookingList"; // 사용안함
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.searchBctAllsList(bct_name, search);
	}

	/* 전체예약 페이징 ajax */
	public String AllPaging(HttpServletRequest request) {
		String no = request.getSession().getAttribute("no").toString();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		int maxNum = hyDao.getListCount(no);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "businessBookingList"; // 사용안함
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.AllPaging();
	}

	/* 서비스별 예약 페이징 ajax */
	public String bctAllPaging(HttpServletRequest request) {
		String no = request.getSession().getAttribute("no").toString();
		String bct_name = request.getParameter("bct_name");
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("bct_name", bct_name);
		int maxNum = hyDao.bctAllPaging(map);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "businessBookingList"; // 사용안함
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.bctAllPaging(bct_name);
	}

}
