package com.teamx.respets.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Personal;
import com.teamx.respets.dao.HyeonDao;

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

	/* 혜연 개인 회원탈퇴 이메일,예약내역, 동물종류남기고 지우기 */
	public ModelAndView personalPartDelete(HttpSession session) {
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
	public ModelAndView myBookingCancel(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		String no = session.getAttribute("no").toString();
		System.out.println("예약번호=" + no);
		// 방문날짜 가져오기
		String start = hyDao.getBkStart(no);
		System.out.println("방문날=" + start);
		// 현재날짜(취소날짜)
		String timeS = new SimpleDateFormat("yy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		System.out.println("취소날=" + timeS);
		// date 형식의 객체를 원하는 방식으로 출력하게 해준다
		SimpleDateFormat day = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		Date startDay = null;
		Date nowDay = null;
		try {
			startDay = day.parse(start); // day.format(start);
			System.out.println(startDay);
			nowDay = day.parse(timeS); // day.format(timeS);
			System.out.println(nowDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 날짜간 차이 구하기
		long cancel = startDay.getTime() - nowDay.getTime();
		int cancDay = (int) cancel / (24 * 60 * 60 * 1000);
		System.out.println(cancDay);
		/*
		 * if (cancDay >= 1 || cancDay <= 3) { // 취소테이블에 insert 하기 int cancelDay =
		 * hyDao.cancelInsert(no, timeS, cancDay); if (cancelDay != 0) {
		 * mav.addObject("cancInsertSucess", makeCancInsertSucess()); view =
		 * "recentMyBookingList"; } else { mav.addObject("flas", makeFlasHtml()); view =
		 * "myBookingCancelPage"; } }
		 */
		mav.setViewName(view);
		return mav;
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
		HashMap<String, Object> map = new HashMap<String, Object>();
		String no = session.getAttribute("no").toString();
		System.out.println("개인번호=" + no);
		map.put("no", no);
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		// 예약 리스트 select
		allList = hyDao.allBookingList(map);
		if (allList != null) {
			mav.addObject("allList", makeAllListHtml(allList));
			view = "allBookingList";
		} else {
			mav.addObject("none", makeNoneHtml());
			view = "allBookingList";
		}
		mav.setViewName(view);
		return mav;
	}

	private Object makeAllListHtml(ArrayList<HashMap<String, Object>> allList) {
		StringBuilder sb = new StringBuilder();
		String no = null;
		for (int i = 0; i < allList.size(); i++) {
			no = (String) allList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?no=" + no + "'>" + no + "</a> | "
					+ allList.get(i).get("PTY_NAME") + " | " + allList.get(i).get("PET_NAME") + " | "
					+ allList.get(i).get("PER_NAME") + " | " + allList.get(i).get("BK_TIME") + " | "
					+ allList.get(i).get("VS_START") + " | " + allList.get(i).get("BK_CHK") + "</td></tr>");
		}
		return sb.toString();
	}

//////////////////////////////////////////////////////////////////////////////////////

	/* 혜연 기업 */
	/* 혜연 기업 마이페이지 */
	/*
	 * public ModelAndView myPage(HttpServletRequest request) { mav = new
	 * ModelAndView(); String view = null; String email =
	 * request.getParameter("email"); String pw = request.getParameter("pw"); String
	 * no = hyDao.myPage(email, pw); if (no != null) { mav.addObject("no", no); view
	 * = "topbar"; } else { view = "loginForm"; } mav.setViewName(view); return mav;
	 * }
	 */

	/* 혜연 기업 오늘일정 */
	public ModelAndView todayScheduleList(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String no = session.getAttribute("no").toString();
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("no", no);
		map.put("timeS", timeS);
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		// 오늘 일정 select
		bList = hyDao.todayScheduleList(map);
		if (bList.size() != 0) {
			mav.addObject("toSdList", makeToSdListHtml(bList));
			mav.addObject("no", no);
			view = "todayScheduleList";
		} else {
			mav.addObject("none", makeNoneHtml());
			view = "todayScheduleList";
		}
		mav.setViewName(view);
		return mav;

	}

	/* 목록 띄우기 */
	private Object makeToSdListHtml(ArrayList<HashMap<String, Object>> bList) {
		StringBuilder sb = new StringBuilder();
		String no = null;
		for (int i = 0; i < bList.size(); i++) {
			no = (String) bList.get(i).get("BK_NO");
			sb.append("<div name='list'><a href='myBookingDetail?no=" + no + "'>" + no + "</a> | "
					+ bList.get(i).get("PTY_NAME") + " | " + bList.get(i).get("PET_NAME") + " | "
					+ bList.get(i).get("PER_NAME") + " | " + bList.get(i).get("BK_TIME") + " | "
					+ bList.get(i).get("VS_START")
					+ "<br/><span id='but'></span><input type='button' name='com' value='방문' onclick='forward(this)' />"
					+ " <input type='button' name='com' value='예약취소' onclick='forward(this)' />"
					+ " <input type='button' name='com' value='노쇼' onclick='forward(this)' />"
					+ " <input type='button' name='com' value='노쇼취소' onclick='forward(this?no" + no + ")'/></div><br>");
		}
		return sb.toString();
	}

	private Object makeNoneHtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<h1>목록이 없습니다.</h1>");
		return sb.toString();
	}

	/* 혜연 예약 상세내역 */
	public ModelAndView myBookingDetail(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String no = session.getAttribute("no").toString();
		System.out.println("예약번호=" + no);
		map.put("no", no);
		ArrayList<HashMap<String, Object>> detail = new ArrayList<HashMap<String, Object>>();
		detail = hyDao.myBookingDetail(map);
		if (detail != null) {
			mav.addObject("dt", detail);
			mav.addObject("no", no);
			view = "myBookingDetail";
		} else {
			mav.addObject("none", makeNoneHtml());
			view = "myBookingDetail";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 혜연 방문 클릭시 */
	public int todayScheduleListCheck(String no) {
		System.out.println("방문2=" + no);
		int result = hyDao.todayScheduleListCheck(no);
		return result;
	}

	/* 혜연 예약취소 클릭시 */
	public int todayScheduleCancell(String no) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* 혜연 노쇼 클릭시 */
	public int todayScheduleListNoShow(String bkno) {
		int result = hyDao.todayScheduleListNoShow(bkno);
		System.out.println(result);
		return result;
	}

	/* 혜연 전체 예약 */
	public ModelAndView businessBookingList(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String no = session.getAttribute("no").toString();
		System.out.println(no);
		map.put("no", no);
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		bList = hyDao.businessBookingList(map);
		if (bList != null) {
			mav.addObject("toSdList", makedListHtml(bList));
			mav.addObject("no", no);
			view = "businessBookingListPage";
		} else {
			mav.addObject("none", makeNoneHtml());
			view = "businessBookingListPage";
		}
		mav.setViewName(view);
		return mav;
	}

	private Object makedListHtml(ArrayList<HashMap<String, Object>> bList) {
		StringBuilder sb = new StringBuilder();
		String no = null;
		for (int i = 0; i < bList.size(); i++) {
			no = (String) bList.get(i).get("BK_NO");
			sb.append("<div name='list'><a href='myBookingDetail?no=" + no + "'>" + no + "</a> | "
					+ bList.get(i).get("PTY_NAME") + " | " + bList.get(i).get("PET_NAME") + " | "
					+ bList.get(i).get("PER_NAME") + " | " + bList.get(i).get("BK_TIME") + " | "
					+ bList.get(i).get("VS_START") + "</div>");
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

	private Object makeAddHtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("서비스를 등록해주세요");
		return sb.toString();
	}

	private Object makeServiceListHtml(ArrayList<HashMap<String, Object>> servList) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < servList.size(); i++) {
			String no = (String) servList.get(i).get("BUS_NO");
			System.out.println("2=" + no);
			if (no.length() <= 3) {
				System.out.println("3=" + no);
				sb.append("<div id='servDiv'><div id='servPhoto'><input type='image' value='"
						+ servList.get(i).get("GLR_FILE") + "' onclick='forward(this)?no=" + no + "'></div><div>"
						+ servList.get(i).get("BCT_NAME") + "</div></div>");
			}
		}
		return sb.toString();
	}

	/* 혜연 */
	public ModelAndView businessInfoDetail(Business bi, HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		bi.setBus_no(request.getParameter("no"));
		System.out.println(bi.getBus_no());
		// bi = hyDao.serviceManagement(bi);
		if (bi != null) {
			mav.addObject("bi", bi);
			view = "businessInfoDetail";
		} else {
			view = "servicePage";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 혜연 */
	public ModelAndView businessInfoUpdateForm(Business bi, HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		bi.setBus_no(request.getParameter("no"));
		System.out.println(bi.getBus_no());
		// bi = hyDao.myPage(bi);
		if (bi != null) {
			view = "";
		}
		return null;
	}

	public ModelAndView unconfirmStep(HttpSession session) {
		this.session = session;

		return null;
	}

}
