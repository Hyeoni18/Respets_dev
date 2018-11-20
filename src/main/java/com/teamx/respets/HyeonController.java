package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Personal;
import com.teamx.respets.service.HyeonService;

//@Controller
@RestController
public class HyeonController {
	@Autowired
	private HyeonService hy;

	ModelAndView mav;

	/* 혜연 */
	@RequestMapping(value = "/myInfo")
	public ModelAndView myInfo(Personal mb, HttpSession session) {
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
		mav = new ModelAndView();
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
	public ModelAndView myBookingCancelPage() {
		mav = new ModelAndView();
		mav.setViewName("myBookingCancelPage");
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/myBookingCancel")
	public ModelAndView myBookingCancel(HttpSession session) {
		mav = hy.myBookingCancel(session);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/allBookingList")
	public ModelAndView allBookingList(HttpSession session) {
		mav = hy.allBookingList(session);
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
	public ModelAndView myBookingDetail(HttpSession session, HttpServletRequest request) {
		System.out.println("예약번호=" + request.getParameter("no"));
		session.setAttribute("no", request.getParameter("no"));
		System.out.println(session);
		mav = hy.myBookingDetail(session);
		return mav;
	}
	
	/* 혜연 */
	@RequestMapping(value = "/businessBookingList")
	public ModelAndView businessBookingList(HttpSession session) {
		mav = hy.businessBookingList(session);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/serviceManagement")
	public ModelAndView serviceManagement(HttpSession session) {
		mav = hy.serviceManagement(session);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/businessInfoDetail", method = RequestMethod.POST)
	public ModelAndView businessInfoDetail(Business bi, HttpServletRequest request) {
		mav = hy.businessInfoDetail(bi, request);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/businessInfoUpdateForm")
	public ModelAndView businessInfoUpdateForm(Business bi, HttpServletRequest request) {
		mav = hy.businessInfoUpdateForm(bi, request);
		return mav;
	}

	@RequestMapping(value = "/apps-calendar")
	public ModelAndView appscalendar() {
		mav = new ModelAndView();
		mav.setViewName("apps-calendar");
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
	@RequestMapping(value = "/todayScheduleListCheck")
	public int todayScheduleListCheck(String no) {
		System.out.println("방문=" + no);
		int result = hy.todayScheduleListCheck(no);
		return result;
	}

	/* 혜연 예약취소 클릭시 */
	@RequestMapping(value = "/todayScheduleCancell")
	public int todayScheduleCancell(String no) {
		int result = hy.todayScheduleCancell(no);
		return result;
	}

	/* 혜연 노쇼 클릭시 */
	@RequestMapping(value = "/todayScheduleListNoShow")
	public int todayScheduleListNoShow(String bkno) {
		System.out.println(bkno);
		int result = hy.todayScheduleListNoShow(bkno);
		return result;
	}

}
