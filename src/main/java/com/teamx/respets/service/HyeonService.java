package com.teamx.respets.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Personal;
import com.teamx.respets.dao.HyeonDao;
import com.teamx.respets.userClass.Paging;

@Service
public class HyeonService {
	private ModelAndView mav;
	@Autowired
	private HyeonDao hyDao;
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

	/* 혜연 개인정보 수정 */
	public ModelAndView myInfoUpdate(Personal mb, HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		String no = session.getAttribute("no").toString();
		System.out.println(no);
		if (mb != null) {
			// 가져온 정보를 담기
			mb.setPer_no(no);
			mb.setPer_email(mb.getPer_email());
			mb.setPer_name(mb.getPer_name());
			mb.setPer_phone(mb.getPer_phone());
			mb.setPer_photo(mb.getPer_photo());
			// 담은 정보로 update
			boolean update = hyDao.myInfoUpdate(mb);
			if (update) {
				System.out.println(mb.getPer_email());
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

	public ModelAndView businessInfoUpdate(Business bi, HttpSession session) {
		this.session = session;
		String no = session.getAttribute("no").toString();
		if (bi != null) {
			bi.setBus_no(no);
			bi.setBus_name(bi.getBus_name());
			bi.setBus_ceo(bi.getBus_ceo());
			bi.setBus_phone(bi.getBus_phone());
			bi.setBus_post(bi.getBus_addr());
			bi.setBus_addr2(bi.getBus_addr2());
			boolean update = hyDao.businessInfoUpdate(bi);
		}
		return null;
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
		String view = null;
		String bk_no = request.getParameter("bk_no");
		System.out.println("예약번호=" + bk_no);
		// 방문날짜 가져오기
		Date start = hyDao.getBkStart(bk_no);
		System.out.println("방문날=" + start);
		int count = cancleDateCheck(start);
		System.out.println(count);
		// 현재날짜(취소날짜)
//		String timeS = new SimpleDateFormat("yy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
//		System.out.println("취소날=" + timeS);
//		// date 형식의 객체를 원하는 방식으로 출력하게 해준다
//		SimpleDateFormat day = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
//		Date startDay = null;
//		Date nowDay = null;
//		try {
//			startDay = day.parse(start); // day.format(start);
//			System.out.println(startDay);
//			nowDay = day.parse(timeS); // day.format(timeS);
//			System.out.println(nowDay);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		// 날짜간 차이 구하기
//		long cancel = startDay.getTime() - nowDay.getTime();
//		int cancDay = (int) cancel / (24 * 60 * 60 * 1000);
//		System.out.println(cancDay);
//		/*
//		 * if (cancDay >= 1 || cancDay <= 3) { // 취소테이블에 insert 하기 int cancelDay =
//		 * hyDao.cancelInsert(no, timeS, cancDay); if (cancelDay != 0) {
//		 * mav.addObject("cancInsertSucess", makeCancInsertSucess()); view =
//		 * "recentMyBookingList"; } else { mav.addObject("flas", makeFlasHtml()); view =
//		 * "myBookingCancelPage"; } }
//		 */
		mav.setViewName("allBookingList"); //전체예약목록으로돌아가기 근데 왜안되지 분명 필요한 값은 세션하난데. 이상해 그치?
		return mav;
	}
	
	private int cancleDateCheck(Date booking) {
		Date today = new Date(); //현재시간 
		Calendar todayCal = Calendar.getInstance ();  
		todayCal.setTime(today); //현재시간을 캘린더화 
		Calendar bookingCal = Calendar.getInstance();
		bookingCal.setTime(booking); //가져온 방문예정일을 캘린더화 
		int count = 0; //현재날짜와 방문예정일의 차이를 나타낼 카운트 변수 
		while(!todayCal.after(bookingCal)) { //현재날짜가 방문예정일을 지나지 않았으면 반복 
			if(todayCal!=bookingCal) { //현재날짜가 방문예정일이랑 다르면 count++, 같으면 count하지 않는다.
				count++;
			}
			todayCal.add(Calendar.DATE, 1); //현재날짜가 방문예정일이 될 때까지 +1일을 해준다.
		}
		if(count!=0) { 
		System.out.println("방문예정일 까지 "+count+"일 남았습니다.");
		} else { //현재날짜가 방문예정일 당일이거나 지난 후면 count가 되지 않는다. 
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
	public ModelAndView allBookingList(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		StringBuilder sb = new StringBuilder();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String no = session.getAttribute("no").toString();
		System.out.println("개인번호=" + no);
		map.put("no", no);
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		// 예약 리스트 select
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
			view = "allBookingList";
		} else {
			mav.addObject("none", makeNoneHtml());
			view = "allBookingList";
		}
		mav.setViewName(view);
		return mav;
	}

//////////////////////////////////////////////////////////////////////////////////////

	/* 혜연 기업 */

	/* 혜연 기업 오늘일정 */
	public ModelAndView todayScheduleList(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		StringBuilder sb = new StringBuilder();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String no = session.getAttribute("no").toString();
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("no", no);
		map.put("timeS", timeS);
		System.out.println(timeS);
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		bList = hyDao.todayScheduleList(map);
		if (bList.size() != 0) {
			
			sb.append("<input type='radio' id='M' value='병원'>");
			for (int i = 0; i < bList.size(); i++) {
				String bk_no = (String) bList.get(i).get("BK_NO");
				String pno = (String) bList.get(i).get("PER_NO");
				sb.append("<div name='list'><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a> | "
						+ bList.get(i).get("PTY_NAME") + " | " + bList.get(i).get("PET_NAME") + " | "
						+ bList.get(i).get("PER_NAME") + " | " + bList.get(i).get("BK_TIME") + " | "
						+ bList.get(i).get("VS_START")
						+ "<br/><span class='but'></span><span class='ton'><input type='button' class='com' value='방문' onclick=\"com(this,\'"
						+ bk_no + "')\" />" + " <input type='button' class='noshow' value='노쇼' onclick=\"noshow(this,\'"
						+ bk_no + "',\'" + pno + "\')\" />"
						+ " <input type='button' class='unNoshow' value='노쇼취소' onclick=\"unNoshow(this,\'" + bk_no
						+ "',\'" + pno + "')\"/></span></div><br>");

			}
			mav.addObject("toSdList", sb);
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
		String view = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String bk_no = request.getQueryString();
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
					sb.append("<span>" + menu.get(i).get("MENU_NAME") + "</span>");
				} else {
					sb.append("<span>" + menu.get(i).get("MENU_NAME") + ", </span>");
				}
				mav.addObject("mList", sb);
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
		view = "myBookingDetail";
		mav.setViewName(view);
		return mav;
	}

	/* 혜연 방문 클릭시 */
	public int todayScheduleListCheck(HttpServletRequest request) {
		String bk_no = request.getParameter("bk_no");
		System.out.println("예약번호 = " + bk_no);
		int result = hyDao.todayScheduleListCheck(bk_no);
		return result;
	}

	/* 혜연 전체 예약 */
	public ModelAndView businessBookingList(HttpSession session, Integer pageNum) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		int page_no = (pageNum == null) ? 1 : pageNum;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String no = session.getAttribute("no").toString();
		System.out.println(no);
		map.put("no", no);
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		bList = hyDao.businessBookingList(map);
		if (bList != null) {
			mav.addObject("toSdList", makedListHtml(bList));
			mav.addObject("paging", makePaging(page_no, no));
			mav.addObject("no", no);
			view = "businessBookingListPage";
		} else {
			mav.addObject("none", makeNoneHtml());
			view = "businessBookingListPage";
		}
		mav.setViewName(view);
		return mav;
	}

	private Object makePaging(int pageNum, String no) {
		int maxNum = hyDao.getListCount(no); // 전체 글의 개수
		int listCount = 10; // 페이지당 글의 수
		int pageCount = 5; // 그룹당 페이지 수
		String boardName = "businessBookingListPage"; // 게시판이 여러개일 때
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, boardName);
		return paging.makeHtmlPaging();
	}

	private Object makedListHtml(ArrayList<HashMap<String, Object>> bList) {
		StringBuilder sb = new StringBuilder();
		String no = null;
		for (int i = 0; i < bList.size(); i++) {
			no = (String) bList.get(i).get("BK_NO");
			sb.append("<div name='list'><a href='myBookingDetail?no=" + no + "'>" + no + "</a> | "
					+ bList.get(i).get("PTY_NAME") + " | " + bList.get(i).get("PET_NAME") + " | "
					+ bList.get(i).get("PER_NAME") + " | " + bList.get(i).get("BK_TIME") + " | "
					+ bList.get(i).get("VS_START") + "</div><br>");
		}
		return sb.toString();
	}

	/* 혜연 서비스 관리 */
	public ModelAndView serviceManagement(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String no = session.getAttribute("no").toString();
		System.out.println(no);
		map.put("no", no);
		ArrayList<HashMap<String, Object>> servList = new ArrayList<HashMap<String, Object>>();
		servList = hyDao.serviceManagement(map);
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
			String no = (String) servList.get(i).get("BUS_NO");
			sb.append(
					"<div id='servDiv' style='border: 2px solid black'><div id='servPhoto' style='border: 1px solid black'><input type='image' value='"
							+ servList.get(i).get("GLR_FILE") + "' onclick='forward(this)?no=" + no
							+ "'></div><div style='border: 1px solid black' algin='center'>"
							+ servList.get(i).get("BCT_NAME") + "</div></div><br>");
		}
		return sb.toString();
	}

	public ModelAndView monthSchedule(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String no = session.getAttribute("no").toString();
		System.out.println("번호=" + no);
		map.put("no", no);
		ArrayList<HashMap<String, Object>> servList = new ArrayList<HashMap<String, Object>>();
		servList = hyDao.serviceList(map);
		System.out.println(servList);
		if (servList != null) {
			mav.addObject("servList", makeServiceHtml(servList));
			view = "monthSchedule";
		} else {
			mav.addObject("add", makeAddHtml());
			view = "servicePage";
		}
		mav.setViewName(view);
		return mav;
	}

	private Object makeServiceHtml(ArrayList<HashMap<String, Object>> servList) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < servList.size(); i++) {
			String code = (String) servList.get(i).get("BCT_CODE");
			sb.append("<li id='" + code + "'><span id='com'><a href='forward(" + code + ")'>"
					+ servList.get(i).get("BCT_NAME") + "</a></span></li>");
		}
		return sb.toString();
	}

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
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(bmap);
			System.out.println(json);
			mav.addObject("result", json);
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

	public ModelAndView unconfirmStep(HttpSession session) {
		this.session = session;

		return null;
	}

	public int todayScheduleListNoShow(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return 0;
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

}
