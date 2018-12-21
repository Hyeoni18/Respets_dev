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

import com.teamx.respets.bean.Business;
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

	// 개인회원가입(insert)
	@RequestMapping(value = "/personalJoin", method = RequestMethod.POST)
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

	// 아이디 찾기 페이지로 이동
	@RequestMapping(value = "/findMyIdForm", method = RequestMethod.GET)
	public ModelAndView findMyIdForm() {
		mav = new ModelAndView();
		mav.setViewName("findMyIdForm");
		return mav;
	}

	// 넘어온 type, per_name, per_phone로 아이디를 찾으러 감
	@RequestMapping(value = "/findMyId", method = RequestMethod.POST)
	public ModelAndView findMyId(Personal mb, HttpServletRequest request) {
		mav = new ModelAndView();
		mav = ls.findMyId(mb, request);
		return mav;
	}

	// 찾은 이메일의 비밀번호를 찾기 위해 변경 폼을 메일로 보내주는 작업 (email, type)
	@RequestMapping(value = "/findMyPw", method = RequestMethod.POST)
	public ModelAndView findMyPw(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = ls.findMyPw(request);
		return mav;
	}

	// 메일에 적힌 url, 신원을 확인 후 비밀번호 변경 폼으로 이동
	@RequestMapping(value = "/resetMyPwForm", method = RequestMethod.GET)
	public ModelAndView resetMyPwForm(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = ls.resetMyPwForm(request);
		return mav;
	}

	// 변경한 비밀번호 값으로 정보를 변경하러 감
	@RequestMapping(value = "/updateMyPw", method = RequestMethod.POST)
	public ModelAndView updateMyPw(HttpServletRequest request, Personal ps) {
		mav = new ModelAndView();
		mav = ls.updateMyPw(request, ps);
		return mav;
	}
	
	// 기업 회원 가입 동의 폼
	@RequestMapping(value = "/businessJoinAgreement", method = RequestMethod.GET)
	public ModelAndView businessJoinAgreement() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("businessJoinAgreement");
		return mav;
	} // method End

	// 기업 회원 가입 폼
	@RequestMapping(value = "/businessJoinForm", method = RequestMethod.GET)
	public ModelAndView businessJoinForm() {
		ModelAndView mav = new ModelAndView();
		String sb = ls.selectBusCategory();
		mav.addObject("input", sb);
		mav.setViewName("businessJoinForm");
		return mav;
	} // method End

	// 기업 회원 가입
	@RequestMapping(value = "/businessJoin", method = RequestMethod.POST)
	public ModelAndView businessJoin(Business b, MultipartHttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		ls.businessJoin(b, request);
		mav.setViewName("index");
		return mav;
	} // method End
}
