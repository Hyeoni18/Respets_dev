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
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Personal;
import com.teamx.respets.dao.HyeonDao;
import com.teamx.respets.dao.IJiyeDao;
import com.teamx.respets.userClass.Paging;

@Service
public class HyeonService {
	private ModelAndView mav;
	@Autowired
	private HyeonDao hyDao;
	@Autowired
	private IJiyeDao jDao;
	@Autowired
	HttpSession session;

	/* 혜연 개인 마이페이지 */
	public ModelAndView myInfo(Personal mb, HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		// 개인정보 select
		mb = hyDao.myInfo(no);
		String view = null;
		if (mb != null) {
			mav.addObject("mb", mb);
			view = "myInfo";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 혜연 개인 비밀전호 확인 */
	public ModelAndView findPw(Personal mb, HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		String no = session.getAttribute("no").toString();
		// 비밀번호 select
		mb = hyDao.myPwCheck(no);
		if (mb != null) {
			mav.addObject("mb", mb);
			view = "myPwUpdateForm";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 혜연 개인 비밀번호 수정 */
	public ModelAndView myPwCheck(HttpSession session, HttpServletRequest request) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		String no = session.getAttribute("no").toString();
		// 개인정보 select
		Personal mb = hyDao.myInfo(no);
		if (mb != null) {
			// 새로 적은 비밀번호 담기
			mb.setPer_pw(request.getParameter("newPw"));
			mb.setPer_no(no);
			// 담은 비밀번호로 update
			int insert = hyDao.myPwUpdate(mb);
			if (insert != 0) {
				mav.addObject("infoSuccess", makeInfoSuccessHtml(mb));
				view = "myInfo";
			} else {
				mav.addObject("flas", makeFlasHtml());
				view = "myPwUpdateForm";
			}
		}
		mav.addObject("mb", mb);
		mav.setViewName(view);
		return mav;
	}

	/* 혜연 정보수정에 개인정보 불러오기 */
	public ModelAndView myInfoUpdateForm(Personal mb, HttpSession session) {
		this.session = session;
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

	public ModelAndView businessInfoUpdate(Business bi, HttpServletRequest request) {
		mav = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		HashMap<String, Object> bmap = new HashMap<>();
		String no = session.getAttribute("no").toString();
		bi.setBus_no(no);
		boolean update = hyDao.businessInfoUpdate(bi);
		if (update) {
			if (request.getParameter("fileCheck").equals("1")) {
				MultipartFile mainPhoto = ((MultipartRequest) request).getFile("mainPhoto");
				map = saveFile((MultipartHttpServletRequest) request, mainPhoto, map);
				map.put("no", no);
				hyDao.mainPhotoUpdate(map);
			} else {
				hyDao.PothoUpdate(no);
			}
			sb.append("<script>alert('" + bi.getBus_email() + "님의 정보가 수정되었습니다.')</script>");
		}
		bmap = hyDao.businessInfo(no);
		System.out.println(bmap.get("BUS_NAME"));
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(bmap);
		System.out.println(json);
		mav.addObject("result", json);
		mav.addObject("success", sb);
		mav.setViewName("businessInfoDetail");
		return mav;
	}

	/* 혜연 개인정보 수정 */
	public ModelAndView myInfoUpdate(Personal mb, HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		String view = null;
		String no = session.getAttribute("no").toString();
		/*int fileCheck = Integer.parseInt(session.getAttribute("fileCheck").toString());*/
		System.out.println(no);
		if (mb != null) {
			mb.setPer_no(no);
			mb.setPer_email(mb.getPer_email());
			mb.setPer_name(mb.getPer_name());
			mb.setPer_phone(mb.getPer_phone());
			mb.setPer_photo(mb.getPer_photo());
			// 담은 정보로 update
			boolean update = hyDao.myInfoUpdate(mb);
			if (update) {
				if (session.getAttribute("fileCheck").equals('1')) {
					MultipartFile mainPhoto = ((MultipartRequest) session).getFile("mainPhoto");
					map = saveFile((MultipartHttpServletRequest) session, mainPhoto, map);
					map.put("no", no);
					hyDao.perPhotoUpdate(map);
				} else {
					hyDao.PothoUpdate(no);
				}
				mav.addObject("mb", mb);
				mav.addObject("infoSuccess", makeInfoSuccessHtml(mb));
				view = "myInfo";
			} else {
				mav.addObject("flas", makeFlasHtml());
				view = "myInfoUpdate";
			}
		}
		mav.setViewName(view);
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
		} // if End
		String date = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
		String extension = mainPhoto.getOriginalFilename()
				.substring(mainPhoto.getOriginalFilename().lastIndexOf(".") + 1);
		String saveName = "Respets_" + date + "_" + UUID.randomUUID() + "." + extension;
		try {
			mainPhoto.transferTo(new File(path, saveName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} // catch End
		map.put("location", location);
		map.put("file", saveName);
		return map;
	}

	/* 혜연 개인 회원탈퇴 이메일,예약내역, 동물종류남기고 지우기 */
	public ModelAndView personalPartDelete(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		String no = session.getAttribute("no").toString();
		/* 회원테이블에 사용안한다고 업데이트 하기 */
		boolean result = hyDao.personalPartDelete(no);
		if (result) {
			view = "loginForm";
		} else {
			mav.addObject("flas", makeFlasHtml());
			view = "myInfo";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 혜연 성공 alert 띄우기 */
	private Object makeInfoSuccessHtml(Personal mb) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>alert('" + mb.getPer_email() + "님의 정보가 수정되었습니다.')</script>");
		return sb.toString();
	}

	/* 혜연 실패 alert 띄우기 */
	private Object makeFlasHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>alert('실패')</script>");
		return sb.toString();
	}

	/* 혜연 예약취소 */
	public ModelAndView myBookingCancel(HttpSession session, HttpServletRequest request) {
		this.session = session;
		mav = new ModelAndView();
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<HashMap<String, Object>> hList = new ArrayList<HashMap<String, Object>>();
		String view = null;
		String bk_no = request.getParameter("bk_no");
		System.out.println("예약번호=" + bk_no);
		// 방문날짜 가져오기
		Date start = hyDao.getBkStart(bk_no);
		System.out.println("방문날=" + start);
		int count = cancleDateCheck(start);
		System.out.println(count);
		map.put("bk_no", bk_no);
		map.put("count", count);
		int result = hyDao.cancelInsert(map);
		if (result != 0) {
			int update = hyDao.bk_chkUpdate(bk_no);
			if (update != 0) {
//				HashMap<String, Object> hmap = new HashMap<>();
//				String no = (String) session.getAttribute("no");
//				StringBuilder sb = new StringBuilder();
//				hmap.put("no", no);
//				hList = jDao.recentMyBookingList(hmap);
//				System.out.println(hList);
//				for (int i = 0; i < hList.size(); i++) {
//					sb.append("<tr><td><a href='./myBookingDetail?" + hList.get(i).get("BK_NO") + "'>"
//							+ hList.get(i).get("BK_NO") + "</a></td>");
//					sb.append("<td>" + hList.get(i).get("BUS_NAME") + "</td>");
//					sb.append("<td>" + hList.get(i).get("PTY_NAME") + "</td>");
//					sb.append("<td>" + hList.get(i).get("PET_NAME") + "</td>");
//					sb.append("<td>" + hList.get(i).get("PER_NAME") + "</td>");
//					sb.append("<td>" + hList.get(i).get("BK_TIME") + "</td>");
//					sb.append("<td>" + hList.get(i).get("VS_START") + "</td>");
//					sb.append("<td name='chk'>" + hList.get(i).get("BK_CHK") + "</td></tr>");
//				} // for End
//				mav.addObject("hList", sb);
				mav.addObject("cancInsertSucess", makeCancInsertSucess());
				mav.addObject(session);
				view = "redirect:/recentMyBookingList";
			} else {
				mav.addObject("flas", makeFlasHtml());
				view = "myBookingCancelPage";
			}
		}
		mav.setViewName(view);
		return mav;
	}

	private int cancleDateCheck(Date booking) {
		Date today = new Date(); // 현재시간
		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(today); // 현재시간을 캘린더화
		Calendar bookingCal = Calendar.getInstance();
		bookingCal.setTime(booking); // 가져온 방문예정일을 캘린더화
		int count = 0; // 현재날짜와 방문예정일의 차이를 나타낼 카운트 변수
		while (!todayCal.after(bookingCal)) { // 현재날짜가 방문예정일을 지나지 않았으면 반복
			if (todayCal != bookingCal) { // 현재날짜가 방문예정일이랑 다르면 count++, 같으면 count하지 않는다.
				count++;
			}
			todayCal.add(Calendar.DATE, 1); // 현재날짜가 방문예정일이 될 때까지 +1일을 해준다.
		}
		if (count != 0) {
			System.out.println("방문예정일 까지 " + count + "일 남았습니다.");
		} else { // 현재날짜가 방문예정일 당일이거나 지난 후면 count가 되지 않는다.
			System.out.println("방문예정일이 지났습니다.");
		}
		return count;
	}

	private Object makeCancInsertSucess() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>alert('예약 취소가 완료되었습니다.')</script>");
		return sb.toString();
	}

	/* 혜연 전체예약목록 */
	public ModelAndView personalAllBookingList(HttpSession session, Integer pageNum) {
		this.session = session;
		mav = new ModelAndView();
		int page_no = (pageNum == null) ? 1 : pageNum;
		StringBuilder sb = new StringBuilder();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String no = session.getAttribute("no").toString();
		System.out.println("개인번호=" + no);
		map.put("no", no);
		map.put("page_no", page_no);
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		allList = hyDao.allBookingList(map);
		if (allList != null) {
			for (int i = 0; i < allList.size(); i++) {
				String bk_no = (String) allList.get(i).get("BK_NO");
				sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a> | "
						+ allList.get(i).get("PTY_NAME") + " | " + allList.get(i).get("PET_NAME") + " | "
						+ allList.get(i).get("PER_NAME") + " | " + allList.get(i).get("BK_TIME") + " | "
						+ allList.get(i).get("VS_START") + " | " + allList.get(i).get("BK_CHK") + "</td></tr>");
			}
			mav.addObject("allList", sb);
			String paging = BookingListPaging(page_no, no);
			mav.addObject("paging", paging);
		}
		mav.setViewName("allBookingList");
		return mav;
	}

	private String BookingListPaging(int page_no, String no) {
		int maxNum = hyDao.contPerBkList(no);
		System.out.println(maxNum);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "personalAllBookingList";
		Paging paging = new Paging(maxNum, page_no, listCount, pageCount, boardName);
		return paging.BookingListPaging(no);
	}

//////////////////////////////////////////////////////////////////////////////////////

	/* 혜연 기업 */

	/* 혜연 기업 오늘일정 */
	public ModelAndView todayScheduleList(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		StringBuilder sb = new StringBuilder();
		String no = session.getAttribute("no").toString();
		ArrayList<HashMap<String, Object>> sMap = hyDao.getSvcPri(no);
		if (sMap.size() != 0) {
			for (int i = 0; i < sMap.size(); i++) {
				String svc = (String) sMap.get(i).get("BCT_NAME");
				String code = (String) sMap.get(i).get("BCT_CODE");
				sb.append("| <input type='radio' name='radio' class='" + code + "' value='" + svc + "'>"+svc);
			}
			mav.addObject("bctList", sb);
			view = "todayScheduleList";
		} else {
			mav.addObject("none", makeNoneHtml());
			view = "todayScheduleList";
		}
		mav.setViewName(view);
		return mav;
	}

	private Object makeNoneHtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<h1>목록이 없습니다.</h1>");
		return sb.toString();
	}

	/* 혜연 예약 상세내역 */
	public ModelAndView myBookingDetail(HttpServletRequest request) {
		mav = new ModelAndView();
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		String view = null;
		String bk_no = request.getQueryString();
		String chk = hyDao.getBk_chk(bk_no);
		HashMap<String, Object> map = new HashMap<String, Object>();
		System.out.println("예약번호=" + bk_no);
		map = hyDao.myBookingDetail(bk_no); // 펫 디테일 리스트로 가져오기
		System.out.println("확인1: " + map);
		ArrayList<HashMap<String, Object>> menu = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();
		String pet_no = map.get("PET_NO").toString();
		menu = hyDao.getMenu(bk_no);
		System.out.println("확인2: " + menu);
		pList = hyDao.getPetList(pet_no);
		System.out.println("pList=" + pList);
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
		System.out.println("json: " + json);
		mav.addObject("result", json);
		mav.addObject("bk_no", bk_no);
		mav.addObject("chk", chk);
		view = "myBookingDetail";
		mav.setViewName(view);
		return mav;
	}

	/* 혜연 방문 클릭시 */
	public int todayScheduleListCheck(String bk_no) {
		System.out.println("예약번호 = " + bk_no);
		int result = hyDao.todayScheduleListCheck(bk_no);
		return result;
	}

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

	/* 노쇼 클릭 시 */
	public int todayScheduleListNoShow(HttpServletRequest request) {
		String per_no = request.getParameter("pno");
		System.out.println("개인=" + per_no);
		int noshow = hyDao.getnoshowCount(per_no);
		System.out.println("노쇼 수=" + noshow);
		int result = 0;
		if (noshow == 0 || noshow == 1 || noshow == 2) { // 노쇼 수가 0이거나 2보다 작거나 같으면
			result = hyDao.noshowInsert(per_no); // 노쇼 레코드 인설트
			System.out.println(result);
		} else if (noshow == 3) { // 노쇼 수가 3개일 시
			int delete = hyDao.noshowDelete(per_no); // 노쇼 레코드 삭제
			int insert = hyDao.warningInsert(per_no); // 경고 레코드 추가
			result = 1;
		}
		return result;
	}

	/* 노쇼 취소 클릭 시 */
	public int todayScheduleListUnNoShow(HttpServletRequest request) {
		String per_no = request.getParameter("pno");
		String timeS = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
		int result = hyDao.todayScheduleListUnNoShow(per_no, timeS);
		return result;
	}

	/* 혜연 전체 예약 */
	public ModelAndView businessBookingList(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		// int pNo = (pageNum == null) ? 1 : pageNum;
		StringBuilder sb = new StringBuilder();
		String no = session.getAttribute("no").toString();
		ArrayList<HashMap<String, Object>> sMap = hyDao.getSvcPri(no);
		if (sMap.size() != 0) {
			for (int i = 0; i < sMap.size(); i++) {
				String svc = (String) sMap.get(i).get("BCT_NAME");
				String code = (String) sMap.get(i).get("BCT_CODE");
				sb.append("  <input type='button' name='button' class='bct' onclick='bctListPaging()' value='" + svc + "'>");
			}
			mav.addObject("bctList", sb);
		}
		/*
		 * Map<String, Object> map = new HashMap<String, Object>(); map.put("no", no);
		 * map.put("page_no", pNo); ArrayList<HashMap<String, Object>> bList = new
		 * ArrayList<HashMap<String, Object>>(); bList = hyDao.businessBookingList(map);
		 * StringBuilder sb2 = new StringBuilder(); for (int i = 0; i < bList.size();
		 * i++) { String bk_no = (String) bList.get(i).get("BK_NO");
		 * sb2.append("<a href='myBookingDetail?no=" + bk_no + "'>" + bk_no + "</a> | "
		 * + bList.get(i).get("PTY_NAME") + " | " + bList.get(i).get("PET_NAME") + " | "
		 * + bList.get(i).get("PER_NAME") + " | " + bList.get(i).get("BCT_NAME") + " | "
		 * + bList.get(i).get("BK_TIME") + " | " + bList.get(i).get("VS_START") +
		 * "<br>"); } mav.addObject("bokList", sb2);
		 */
		// String paging = businessBookingList(pNo, no);
		// mav.addObject("paging", paging);
		mav.setViewName("businessBookingListPage");
		return mav;
	}

	/*private String businessBookingList(int pNo, String no) {
		int maxNum = hyDao.getListCount(no);
		int listCount = 9;
		int pageCount = 5;
		String boardName = "businessBookingList";
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.BookingListButton(no);
	}*/

	/*
	 * private Object makePaging(int pageNum, String no) { int maxNum =
	 * hyDao.getListCount(no); // 전체 글의 개수 int listCount = 10; // 페이지당 글의 수 int
	 * pageCount = 5; // 그룹당 페이지 수 String boardName = "businessBookingListPage"; //
	 * 게시판이 여러개일 때 Paging paging = new Paging(maxNum, pageNum, listCount, pageCount,
	 * boardName); return paging.makeHtmlPaging(); }
	 */

	/* 혜연 서비스 관리 */
	public ModelAndView serviceManagement(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		String no = session.getAttribute("no").toString();
		System.out.println("기업번호 = " + no);
		ArrayList<HashMap<String, Object>> servList = new ArrayList<HashMap<String, Object>>();
		servList = hyDao.serviceManagement(no);
		if (servList != null) {
			mav.addObject("servList", makeServiceListHtml(servList));
			view = "servicePage";
		} else {
			mav.addObject("add", makeAddHtml());
			view = "servicePage";
		}
		mav.setViewName(view);
		return mav;
	}

	private Object makeServiceListHtml(ArrayList<HashMap<String, Object>> servList) {
		StringBuilder sb = new StringBuilder();
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
		return sb.toString();
	}

	/*
	 * public ModelAndView monthSchedule(HttpSession session) { this.session =
	 * session; mav = new ModelAndView(); String view = null; HashMap<String,
	 * Object> map = new HashMap<String, Object>(); String no =
	 * session.getAttribute("no").toString(); System.out.println("번호=" + no);
	 * map.put("no", no); ArrayList<HashMap<String, Object>> servList = new
	 * ArrayList<HashMap<String, Object>>(); servList = hyDao.serviceList(map);
	 * System.out.println(servList); if (servList != null) {
	 * mav.addObject("servList", makeServiceHtml(servList)); view = "monthSchedule";
	 * } else { mav.addObject("add", makeAddHtml()); view = "servicePage"; }
	 * mav.setViewName(view); return mav; }
	 */

	/*
	 * private Object makeServiceHtml(ArrayList<HashMap<String, Object>> servList) {
	 * StringBuilder sb = new StringBuilder(); for (int i = 0; i < servList.size();
	 * i++) { String code = (String) servList.get(i).get("BCT_CODE");
	 * sb.append("<li id='" + code + "'><span id='com'><a href='forward(" + code +
	 * ")'>" + servList.get(i).get("BCT_NAME") + "</a></span></li>"); } return
	 * sb.toString(); }
	 */

	private Object makeAddHtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("서비스를 등록해주세요");
		return sb.toString();
	}

	public ModelAndView businessInfoDetail(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		HashMap<String, Object> bmap = new HashMap<>();
		bmap = hyDao.businessInfo(no);
		System.out.println("bmap" + bmap);
		String view = null;
		if (bmap != null) {
			String glr_file = (String) bmap.get("GLR_FILE");
			String glr_loc = (String) bmap.get("GLR_LOC");
			StringBuilder sb = new StringBuilder();
			sb.append("<img class='card-img-top' src='" + glr_loc + glr_file + "'/>");
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(bmap);
			System.out.println(json);
			mav.addObject("result", json);
			mav.addObject("bmap", bmap);
			mav.addObject("img", sb);
			view = "businessInfoDetail";
		}
		mav.setViewName(view);
		return mav;
	}

	public ModelAndView businessInfoUpdateForm(Map<String, Object> jsonData, HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		mav.addObject("no", no);
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(jsonData);
		mav.addObject("jsonData", json);
		mav.setViewName("businessInfoUpdateForm");
		return mav;
	}

	public ModelAndView businessPartDelete(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		boolean result = hyDao.businessPartDelete(no);
		if (result) {
			session.invalidate();
		}
		mav.setViewName("redirect:/");
		return mav;
	}

	public String bctBookingList(HttpServletRequest request, Integer pageNum) {
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
		if (bList != null) {
			for (int i = 0; i < bList.size(); i++) {
				String bk_no = (String) bList.get(i).get("BK_NO");
				String pno = (String) bList.get(i).get("PER_NO");
				sb.append("<div name='list'><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a> | "
						+ bList.get(i).get("PTY_NAME") + " | " + bList.get(i).get("PET_NAME") + " | "
						+ bList.get(i).get("PER_NAME") + " | " + bList.get(i).get("BCT_NAME") + " | "
						+ bList.get(i).get("BK_TIME") + " | " + bList.get(i).get("VS_START") + "<br/><span class='"
						+ bk_no + "'></span><span id='" + bk_no
						+ "' class='ton'><input type='button' class='com' value='방문' onclick=\"com(\'" + bk_no
						+ "')\" />" + " <input type='button' id='no" + bk_no + "' value='노쇼' onclick=\"noshow(\'"
						+ bk_no + "',\'" + pno + "\')\" />" + " <input type='button' class='unNoshow' id='un" + bk_no
						+ "' value='노쇼취소' onclick=\"unNoshow(\'" + pno + "')\"/></span></div><br>");

			}
		}
		return sb.toString();
	}

	public String todayAllScheduleList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		String no = request.getParameter("no");
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("no", no);
		map.put("timeS", timeS);
		bList = hyDao.todayScheduleList(map);
		for (int i = 0; i < bList.size(); i++) {
			String bk_no = (String) bList.get(i).get("BK_NO");
			String pno = (String) bList.get(i).get("PER_NO");
			sb.append("<div name='list' id='" + bk_no + "'><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a> | "
					+ bList.get(i).get("PTY_NAME") + " | " + bList.get(i).get("PET_NAME") + " | "
					+ bList.get(i).get("PER_NAME") + " | " + bList.get(i).get("BCT_NAME") + " | "
					+ bList.get(i).get("BK_TIME") + " | " + bList.get(i).get("VS_START") + "<br/><span class='" + bk_no
					+ "'></span><span id='" + bk_no
					+ "' class='ton'><input type='button' class='com' value='방문' onclick=\"com(\'" + bk_no + "')\" />"
					+ " <input type='button' id='no" + bk_no + "' value='노쇼' onclick=\"noshow(\'" + bk_no + "',\'" + pno
					+ "\')\" />" + " <input type='button' class='unNoshow' id='un" + bk_no
					+ "' value='노쇼취소' onclick=\"unNoshow(\'" + pno + "')\"/></span></div><br>");

		}
		return sb.toString();
	}

	public String businessAllBookingList(HttpServletRequest request, Integer pageNum) {
		mav = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		int pNo = (pageNum == null) ? 1 : pageNum;
		String no = request.getParameter("no");
		map.put("no", no);
		map.put("page_no", pNo);
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		bList = hyDao.businessBookingList(map);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bList.size(); i++) {
			String bk_no = (String) bList.get(i).get("BK_NO");
			sb.append("<a href='myBookingDetail?no=" + bk_no + "'>" + bk_no + "</a> | " + bList.get(i).get("PTY_NAME")
					+ " | " + bList.get(i).get("PET_NAME") + " | " + bList.get(i).get("PER_NAME") + " | "
					+ bList.get(i).get("BCT_NAME") + " | " + bList.get(i).get("BK_TIME") + " | "
					+ bList.get(i).get("VS_START") + "<br>");
		}
		return sb.toString();
	}

	public String searchAllList(HttpServletRequest request, Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bPerList = new ArrayList<HashMap<String, Object>>();
		int pNo = (pageNum == null) ? 1 : pageNum;
		StringBuilder sb = new StringBuilder();
		String no = request.getParameter("no");
		map.put("no", no);
		map.put("per_name", request.getParameter("per_name"));
		map.put("page_no", pNo);
		bPerList = hyDao.searchAllList(map);
		if (bPerList.size() != 0) {
			for (int i = 0; i < bPerList.size(); i++) {
				String bk_no = (String) bPerList.get(i).get("BK_NO");
				sb.append("<div name='list'><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a> | "
						+ bPerList.get(i).get("PTY_NAME") + " | " + bPerList.get(i).get("PET_NAME") + " | "
						+ bPerList.get(i).get("PER_NAME") + " | " + bPerList.get(i).get("BCT_NAME") + " | "
						+ bPerList.get(i).get("BK_TIME") + " | " + bPerList.get(i).get("VS_START"));
			}
		}
		return sb.toString();
	}

	public String businessAllBctBookingList(HttpServletRequest request, Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		int pNo = (pageNum == null) ? 1 : pageNum;
		String no = request.getParameter("no");
		String bct_name = request.getParameter("bct_name");
		System.out.println("bct_name=" + bct_name);
		map.put("no", no);
		map.put("bct_name", bct_name);
		map.put("page_no", pNo);
		bList = hyDao.AllbctBookingList(map);
		if (bList != null) {
			for (int i = 0; i < bList.size(); i++) {
				String bk_no = (String) bList.get(i).get("BK_NO");
				sb.append("<div name='list'><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a> | "
						+ bList.get(i).get("PTY_NAME") + " | " + bList.get(i).get("PET_NAME") + " | "
						+ bList.get(i).get("PER_NAME") + " | " + bList.get(i).get("BCT_NAME") + " | "
						+ bList.get(i).get("BK_TIME") + " | " + bList.get(i).get("VS_START"));

			}
		}
		return sb.toString();
	}

	public String searchBctAllsList(HttpServletRequest request, Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		int pNo = (pageNum == null) ? 1 : pageNum;
		StringBuilder sb = new StringBuilder();
		String no = request.getParameter("no");
		String bct_name = request.getParameter("bct_name");
		map.put("no", no);
		map.put("bct_name", bct_name);
		map.put("per_name", request.getParameter("per_name"));
		map.put("page_no", pNo);
		bList = hyDao.searchBctAllsList(map);
		if (bList != null) {
			for (int i = 0; i < bList.size(); i++) {
				String bk_no = (String) bList.get(i).get("BK_NO");
				sb.append("<div name='list'><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a> | "
						+ bList.get(i).get("PTY_NAME") + " | " + bList.get(i).get("PET_NAME") + " | "
						+ bList.get(i).get("PER_NAME") + " | " + bList.get(i).get("BCT_NAME") + " | "
						+ bList.get(i).get("BK_TIME") + " | " + bList.get(i).get("VS_START"));
			}
		}
		return sb.toString();
	}

	public String AllPaging(HttpServletRequest request) {
		String no = request.getParameter("bus_no");
		Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
		System.out.println(pageNum);
		int pNo = (pageNum == null) ? 1 : pageNum;
		int maxNum = hyDao.getListCount(no);
		int listCount = 9;
		int pageCount = 5;
		String boardName = "businessBookingList";
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.AllPaging(no);
	}

	public String bctAllPaging(HttpServletRequest request) {
		Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
		String no= request.getParameter("bus_no");
		String bct_name = request.getParameter("bct_name");
		int pNo = (pageNum == null) ? 1 : pageNum;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("bct_name", bct_name);
		int maxNum = hyDao.bctAllPaging(map);
		int listCount = 9;
		int pageCount = 5;
		String boardName = "businessBookingList";
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.bctAllPaging(map);
	}

}
