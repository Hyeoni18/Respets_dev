package com.teamx.respets;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.stream.JsonToken;
import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Personal;
import com.teamx.respets.service.HyeonService;

@Controller
public class HyeonController {
	@Autowired
	private HyeonService hy;

	ModelAndView mav;

	/* 혜연 */
	@RequestMapping(value = "/myInfo")
	public ModelAndView myInfo(Personal mb, HttpSession session) {
		System.out.println("회원번호" + session.getAttribute("no"));
		mav = hy.myInfo(mb, session);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/myPwUpdateForm")
	public ModelAndView myPwUpdateForm(Personal mb, HttpSession session) {
		mav = hy.findPw(mb, session);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/myPwCheck", method = RequestMethod.POST)
	public ModelAndView myPwCheck(HttpSession session, HttpServletRequest request) {
		mav = new ModelAndView();
		mav = hy.myPwCheck(session, request);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/myInfoUpdateForm")
	public ModelAndView myInfoUpdateForm(Personal mb, HttpSession session) {
		mav = hy.myInfoUpdateForm(mb, session);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/myInfoUpdate", method = RequestMethod.POST)
	public ModelAndView myInfoUpdate(Personal mb, HttpSession session) {
		System.out.println(mb.getPer_email());
		mav = hy.myInfoUpdate(mb, session);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/personalPartDelete")
	public ModelAndView personalPartDelete(HttpSession session) {
		mav = new ModelAndView();
		mav = hy.personalPartDelete(session);
		return mav;
	}

	/* 예약취소페이지 전환 */
	@RequestMapping(value = "/myBookingCancelPage")
	public ModelAndView myBookingCancelPage(HttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		mav.addObject("bk_no", request.getParameter("bk_no"));
		mav.setViewName("myBookingCancelPage");
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/myBookingCancel")
	public ModelAndView myBookingCancel(HttpSession session, HttpServletRequest request) {
		System.out.println(session.getAttribute("no"));
		System.out.println(request.getParameter("bk_no"));
		mav = hy.myBookingCancel(session, request);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/personalAllBookingList")
	public ModelAndView personalAllBookingList(HttpSession session) {
		mav = hy.personalAllBookingList(session);
		return mav;
	}

	////////////////////////////////////////////////////////////////////////////////////////

	/* 혜연 */
	@RequestMapping(value = "/myPage", method = RequestMethod.GET)
	public ModelAndView myPage() {
		mav = new ModelAndView();
		mav.setViewName("myPage");
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/todayScheduleList")
	public ModelAndView todayScheduleList(HttpSession session) {
		mav = hy.todayScheduleList(session);
		return mav;
	}

	/* 혜연 예약 상세내역 */
	@RequestMapping(value = "/myBookingDetail")
	public ModelAndView myBookingDetail(HttpServletRequest request) {
		mav = hy.myBookingDetail(request);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/businessBookingList")
	public ModelAndView businessBookingList(HttpSession session, Integer pageNum) {
		mav = hy.businessBookingList(session, pageNum);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/serviceManagement")
	public ModelAndView serviceManagement(HttpSession session) {
		mav = hy.serviceManagement(session);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/businessInfoDetail")
	public ModelAndView businessInfoDetail(HttpSession session) {
		mav = hy.businessInfoDetail(session);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/businessInfoUpdateForm")
	public ModelAndView businessInfoUpdateForm(HttpSession session) {
		mav.addObject("no", session.getAttribute("no"));
		mav.setViewName("businessInfoUpdateForm");
		return mav;
	}
	
	/* 혜연 */
	@RequestMapping(value = "/businessInfoUpdate")
	public ModelAndView businessInfoUpdate(Business bi, HttpSession session) {
		mav = hy.businessInfoUpdate(bi, session);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/businessPartDelete")
	public ModelAndView businessPartDelete(HttpSession session) {
		mav = hy.businessPartDelete(session);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/montest", method = RequestMethod.GET)
	public ModelAndView montest() {
		mav = new ModelAndView();
		mav.setViewName("montest");
		return mav;
	}

	/* 혜연 */
	/* 기업중 업종별로 쉬는날 불러오기 */
	@RequestMapping(value = "/monthSchedule")
	public ModelAndView monthSchedule(HttpSession session) {
		mav = hy.monthSchedule(session);
		return mav;
	}

	////////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/unconfirmStep")
	public ModelAndView unconfirmStep(HttpSession session) {
		mav = hy.unconfirmStep(session);
		return mav;
	}

	////////////////////////////////////////////////////////////////////////////////////

	/* 혜연 방문 클릭시 */
	@RequestMapping(value = "/todayScheduleListCheck", method = RequestMethod.POST)
	public @ResponseBody int todayScheduleListCheck(String bk_no) {
		int result = hy.todayScheduleListCheck(bk_no);
		return result;
	}
	
	@RequestMapping(value = "/todayAllScheduleList", produces = "application/text; charset=utf8")
	public @ResponseBody String todayAllScheduleList(HttpServletRequest request) {
		String text = hy.todayAllScheduleList(request);
		return text;
	}
	
	@RequestMapping(value = "/bctBookingList", produces = "application/text; charset=utf8")
	public @ResponseBody String bctBookingList(HttpServletRequest request) {
		String text = hy.bctBookingList(request);
		return text;
	}

	@RequestMapping(value = "/todayScheduleListNoShow", method = RequestMethod.POST)
	public @ResponseBody int todayScheduleListNoShow(HashMap<String, String> map) {
		System.out.println(map.get("com"));
		System.out.println(map.get("no"));
		System.out.println(map.get("pno"));
		int result = hy.todayScheduleListNoShow(map);
		return result;
	}

}
