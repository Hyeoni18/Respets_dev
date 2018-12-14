package com.teamx.respets;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.Personal;
import com.teamx.respets.service.LoginService;
import com.teamx.respets.service.PersonalService;

@Controller
public class LoginController {
	@Autowired
	private LoginService ls;
	ModelAndView mav;

	// 로그인
	@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	public ModelAndView loginForm() {
		mav = new ModelAndView();
		mav.setViewName("loginForm");
		return mav;
	}
	
	// 회원 종류 선택
	@RequestMapping(value = "/joinChoiceForm", method = RequestMethod.GET)
	public ModelAndView joinChoiceForm() {
		mav = new ModelAndView();
		mav.setViewName("joinChoiceForm");
		return mav;
	}
	
	// 개인회원약관동의
	@RequestMapping(value = "/personalJoinAgreement", method = RequestMethod.GET)
	public ModelAndView personalJoinAgreement() {
		mav = new ModelAndView();
		mav.setViewName("personalJoinAgreement");
		return mav;
	}
	
	// 개인회원가입폼
	@RequestMapping(value = "/personalJoinForm", method = RequestMethod.GET)
	public ModelAndView personalJoinForm(Personal mb) {
		mav = new ModelAndView();
		mav.setViewName("personalJoinForm");
		return mav;
	}	
	
	//개인회원가입(insert)
	@RequestMapping (value="/personalJoin", method=RequestMethod.POST)
	public ModelAndView personalJoin(MultipartHttpServletRequest multi) throws MessagingException {
		mav = new ModelAndView();
		System.out.println(multi.getParameter("per_email"));
		System.out.println("multi=" + multi.getFileNames());
		mav = ls.personalJoin(multi);
		return mav;
	}
	
	// 인증 이메일
	@RequestMapping(value = "/emailConfirmOffer", method = RequestMethod.GET)
	public ModelAndView confirmPerEmail(Personal mb, HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {
		mav = new ModelAndView();
		System.out.println("emailConfirmOffer");
		// mav =mm.SendConfirmPerEmail(mb, request);
		mav.setViewName("emailConfirmSuccess");
		return mav;
	}
	
	// 로그인
	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public ModelAndView loginProcess(String email, String pw, HttpServletRequest request) {
		mav = new ModelAndView();
		mav = ls.loginProcess(email, pw, request);
		return mav;
	}
	
	// 이메일 인증 완료
	@RequestMapping(value = "/emailConfirmSuccess", method = RequestMethod.GET)
	public ModelAndView emailConfirmSuccess(Personal mb, HttpServletRequest request) throws MessagingException {
		mav = new ModelAndView();
		System.out.println("-----------컨트롤러 진입----------");
		mav = ls.emailConfirmSuccess(request);
		return mav;
	}
	
	// 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ModelAndView logout(HttpServletRequest request) {
		mav = ls.logout(request);
		return mav;
	} // method End

}
