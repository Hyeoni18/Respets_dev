package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.Business;
import com.teamx.respets.service.JinService;

@Controller
public class JinController {

	@Autowired
	private JinService jinSvc;

	// 서진 : 메인 페이지
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		String sb = jinSvc.indexBusCategory();
		mav.addObject("bct", sb);
		mav.setViewName("index");
		return mav;
	} // method End

	// 서진 : 기업 회원 가입 동의 폼
	@RequestMapping(value = "/businessJoinAgreement", method = RequestMethod.GET)
	public ModelAndView businessJoinAgreement() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("businessJoinAgreement");
		return mav;
	} // method End

	// 서진 : 기업 회원 가입 폼
	@RequestMapping(value = "/businessJoinForm", method = RequestMethod.GET)
	public ModelAndView businessJoinForm() {
		ModelAndView mav = new ModelAndView();
		String sb = jinSvc.selectBusCategory();
		mav.addObject("input", sb);
		mav.setViewName("businessJoinForm");
		return mav;
	} // method End

	// 서진 : 기업 회원 가입
	@RequestMapping(value = "/businessJoin", method = RequestMethod.POST)
	public ModelAndView businessJoin(Business b, MultipartHttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		jinSvc.businessJoin(b, request);
		mav.setViewName("index");
		return mav;
	} // method End

	// 서진 : 개인 즐겨찾기 페이지
	@RequestMapping(value = "/likeBusinessList", method = RequestMethod.GET)
	public ModelAndView likeBusinessList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String list = jinSvc.likeBusinessList(request);
		mav.addObject("list", list);
		mav.setViewName("likeBusinessList");
		return mav;
	} // method End

	// 서진 : 개인 즐겨찾기 삭제
	@RequestMapping(value = "/likeBusinessCancel", method = RequestMethod.GET)
	public ModelAndView likeBusinessCancel(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		jinSvc.likeBusinessCancel(request);
		mav.setViewName("redirect:likeBusinessList");
		return mav;
	} // method End

	// 서진 : 예약 페이지
	@RequestMapping(value = "/bookingForm", method = RequestMethod.GET)
	public ModelAndView bookingForm(HttpServletRequest request) {
		ModelAndView mav = jinSvc.bookingForm(request);
		mav.setViewName("bookingForm");
		return mav;
	} // method End

} // class End
