package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.service.AdminService;
import com.teamx.respets.service.LoginService;

@Controller
public class AdminController {
	ModelAndView mav;
	@Autowired
	private AdminService as;

	// 관리자 로그인 폼
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminLoginForm() {
		mav = new ModelAndView();
		mav.setViewName("adminLoginForm");
		return mav;
	}
	
	// 관리자 로그인
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public ModelAndView adminLogin(String adm_no, String adm_pw, HttpServletRequest request) {
		mav = new ModelAndView();
		mav = as.adminLogin(adm_no, adm_pw, request);
		return mav;
	}
	
	//관리자 페이지
	@RequestMapping(value = "/adminPage", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		mav = new ModelAndView();
		mav.setViewName("adminPage");
		return mav;
	}
	
	//미인증 기업리스트
	@RequestMapping (value="/unconfirmBusiness", method=RequestMethod.GET)
	public ModelAndView businessConfirm(HttpSession session) {
		mav = new ModelAndView();
		mav = as.unconfirmBusiness(session);
		return mav;
	}
	
	//기업 미인증 정보 확인
	@RequestMapping (value="/chkLicense", method=RequestMethod.GET)
	public ModelAndView chkLicense(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = as.chkLicense(request);
		return mav;
	}
	
	//기업 미인증 기업 인증
	@RequestMapping (value="/confirmLicense", method=RequestMethod.GET)
	public ModelAndView confirmLicense(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = as.confirmLicense(request);
		return mav;
	}
}
