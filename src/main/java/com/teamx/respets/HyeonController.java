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
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
		mav = hy.myInfoUpdate(mb, session);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/businessInfoUpdate")
	public ModelAndView businessInfoUpdate(Business bi, HttpServletRequest request) {
		mav = hy.businessInfoUpdate(bi, request);
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
		System.out.println(request.getParameter("bk_no"));
		mav = hy.myBookingCancel(session, request);
		return mav;
	}

	/* 혜연 */
	@RequestMapping(value = "/personalAllBookingList")
	public ModelAndView personalAllBookingList(HttpSession session, Integer pageNum) {
		mav = hy.personalAllBookingList(session, pageNum);
		return mav;
	}

	////////////////////////////////////////////////////////////////////////////////////////

	/* 혜연 */
	@RequestMapping(value = "/businessButtonPage", method = RequestMethod.GET)
	public ModelAndView businessButtonPage() {
		mav = new ModelAndView();
		mav.setViewName("businessButtonPage");
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
	@RequestMapping(value = "/businessPartDelete")
	public ModelAndView businessPartDelete(HttpSession session) {
		mav = hy.businessPartDelete(session);
		return mav;
	}

	/* 혜연 */
	/*
	 * @RequestMapping(value = "/montest", method = RequestMethod.GET) public
	 * ModelAndView montest() { mav = new ModelAndView();
	 * mav.setViewName("montest"); return mav; }
	 */

	/* 혜연 */
	/* 기업중 업종별로 쉬는날 불러오기 */
	/*
	 * @RequestMapping(value = "/monthSchedule") public ModelAndView
	 * monthSchedule(HttpSession session) { mav = hy.monthSchedule(session); return
	 * mav; }
	 */

	////////////////////////////////////////////////////////////////////////////////////

	/*
	 * @RequestMapping(value = "/unconfirmStep") public ModelAndView
	 * unconfirmStep(HttpSession session) { mav = hy.unconfirmStep(session); return
	 * mav; }
	 */

	////////////////////////////////////////////////////////////////////////////////////
	/* 혜연 방문 클릭시 */
	@RequestMapping(value = "/todayScheduleListCheck", produces = "application/text; charset=utf8")
	public @ResponseBody String todayScheduleListCheck(HttpServletRequest request) {
		String result = hy.todayScheduleListCheck(request);
		return result;
	}

	@RequestMapping(value = "/todayScheduleListCancel", produces = "application/text; charset=utf8")
	public @ResponseBody String todayScheduleListCancel(HttpServletRequest request) {
		String result = hy.todayScheduleListCancel(request);
		return result;
	}

	@RequestMapping(value = "/vs_chkOkList", produces = "application/text; charset=utf8")
	public @ResponseBody String vs_chkOkList(HttpServletRequest request) {
		String text = hy.vs_chkOkList(request);
		return text;
	}

	@RequestMapping(value = "/todayScheduleListNoShow", method = RequestMethod.POST)
	public @ResponseBody int todayScheduleListNoShow(HttpServletRequest request) {
		int result = hy.todayScheduleListNoShow(request);
		return result;
	}

	@RequestMapping(value = "/todayScheduleListUnNoShow", method = RequestMethod.POST)
	public @ResponseBody int todayScheduleListUnNoShow(HttpServletRequest request) {
		int result = hy.todayScheduleListUnNoShow(request);
		return result;
	}
	

	@RequestMapping(value = "/todayAllScheduleList", produces = "application/text; charset=utf8")
	public @ResponseBody String todayAllScheduleList(HttpServletRequest request) {
		String text = hy.todayAllScheduleList(request);
		return text;
	}
	
	@RequestMapping(value = "/todayAllScheduleListOk", produces = "application/text; charset=utf8")
	public @ResponseBody String todayAllScheduleListOk(HttpServletRequest request) {
		String text = hy.todayAllScheduleListOk(request);
		return text;
	}

	@RequestMapping(value = "/bctBookingList", produces = "application/text; charset=utf8")
	public @ResponseBody String bctBookingList(HttpServletRequest request) {
		String text = hy.bctBookingList(request);
		return text;
	} 

	@RequestMapping(value = "/bctBookingListOk", produces = "application/text; charset=utf8")
	public @ResponseBody String bctBookingListOk(HttpServletRequest request) {
		String text = hy.bctBookingListOk(request);
		return text;
	}
	
	@RequestMapping(value = "/bctBookingListCheck", produces = "application/text; charset=utf8")
	public @ResponseBody String bctBookingListCheck(HttpServletRequest request) {
		String result = hy.bctBookingListCheck(request);
		return result;
	}
	
	@RequestMapping(value = "/bctBookingListCancel", produces = "application/text; charset=utf8")
	public @ResponseBody String bctBookingListCancel(HttpServletRequest request) {
		String text = hy.bctBookingListCancel(request);
		return text;
	}
	
	@RequestMapping(value = "/businessAllBookingList", method=RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody String businessAllBookingList(HttpServletRequest request, Integer pageNum) {
		String text = hy.businessAllBookingList(request, pageNum);
		return text;
	}
	
	@RequestMapping(value = "/AllPaging", method=RequestMethod.POST,produces = "application/text; charset=utf8")
	public @ResponseBody String AllPaging(HttpServletRequest request ,Integer pageNum) {
		String text = hy.AllPaging(request, pageNum);
		System.out.println("확인@@@@@@@" + text);
		return text;
	}
	
	@RequestMapping(value = "/bctAllPaging", method=RequestMethod.POST,produces = "application/text; charset=utf8")
	@ResponseBody public String bctAllPaging(HttpServletRequest request, Integer pageNum) {
		String text = hy.bctAllPaging(request, pageNum);
		return text;
	}

	@RequestMapping(value = "/searchAllList", produces = "application/text; charset=utf8")
	public @ResponseBody String searchAllList(HttpServletRequest request, Integer pageNum) {
		String text = hy.searchAllList(request, pageNum);
		return text;
	}

	@RequestMapping(value = "/businessAllBctBookingList", produces = "application/text; charset=utf8")
	public @ResponseBody String businessAllBctBookingList(HttpServletRequest request, Integer pageNum) {
		String text = hy.businessAllBctBookingList(request, pageNum);
		return text;
	}

	@RequestMapping(value = "/searchBctAllsList", produces = "application/text; charset=utf8")
	public @ResponseBody String searchBctAllsList(HttpServletRequest request, Integer pageNum) {
		String text = hy.searchBctAllsList(request, pageNum);
		return text;
	}
}
