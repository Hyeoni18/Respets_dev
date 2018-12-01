package com.teamx.respets;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.Admin;
import com.teamx.respets.bean.Personal;
import com.teamx.respets.service.JiyeService;

@Controller
public class JiyeController {
	@Autowired
	private JiyeService js;
	ModelAndView mav;

	// 로그인
	@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	public ModelAndView loginForm() {
		mav = new ModelAndView();
		mav.setViewName("loginForm");
		return mav;
	}

	// 회원 종료 선택
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
		mav = js.personalJoin(multi);
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

	// 로그인 처리시 개인, 기업 서비스 클래스 나누지 않고 personalManagement 클래스에서 한번에 처리
	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public ModelAndView loginProcess(String email, String pw, HttpServletRequest request) {
		mav = new ModelAndView();
		mav = js.loginProcess(email, pw);
		return mav;
	}

	// 이메일 인증 완료
	@RequestMapping(value = "/emailConfirmSuccess", method = RequestMethod.GET)
	public ModelAndView emailConfirmSuccess(Personal mb, HttpServletRequest request) throws MessagingException {
		mav = new ModelAndView();
		System.out.println("-----------컨트롤러 진입----------");
		mav = js.emailConfirmSuccess(request);
		return mav;
	}

	// 관리자 로그인 폼
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminLoginForm() {
		mav = new ModelAndView();
		mav.setViewName("adminLoginForm");
		return mav;
	}

	// 관리자 로그인
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public ModelAndView adminLogin(String adm_no, String adm_pw) {
		mav = new ModelAndView();
		mav = js.adminLogin(adm_no, adm_pw);
		return mav;
	}

	// 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ModelAndView logout() {
		mav = js.logout();
		return mav;
	} // method End

	// 마이페이지
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView myPage(Admin adm) {
		mav = new ModelAndView();
		mav.setViewName("dashboard");
		return mav;
	}

	// 최근 예약 목록
	@RequestMapping(value = "/recentMyBookingList", method = RequestMethod.GET)
	public ModelAndView recentMyBookingList(HttpSession session, Integer pageNum) {
		mav = new ModelAndView();
		mav = js.recentMyBookingList(session, pageNum);
		return mav;
	}

	// 개인예약디테일
	/*@RequestMapping(value = "/myBookingDetail", method = RequestMethod.GET)
	public ModelAndView recentMyBookingList(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = js.myBookingDetail(request);
		return mav;
	}*/
	
	//기업 공지사항 리스트
	@RequestMapping (value="/businessNoticeList", method=RequestMethod.GET)
	public ModelAndView businessNoticeList(HttpSession session, Integer pageNum) {
		mav = new ModelAndView();
		mav = js.businessNoticeList(session, pageNum);
		return mav;
	}
	
	@RequestMapping (value="/businessNoticeDetail")
	public ModelAndView businessNoticeDetail(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = js.businessNoticeDetail(request);
		return mav;
	}
	
	@RequestMapping (value="/searchBusinessNotice")
	public ModelAndView searchBusinessNotice(HttpSession session, String select, String search, Integer pageNum) {
		mav = new ModelAndView();
		mav = js.searchBusinessNotice(session, select, search, pageNum);
		return mav;
	}
	
	@RequestMapping (value="/businessNoticeWriteForm")
	public ModelAndView businessNoticeWriteForm() {
		mav = new ModelAndView();
		mav.setViewName("businessNoticeWriteForm");
		return mav;
	}
	
	@RequestMapping (value="/businessNoticeInsert")
	public ModelAndView businessNoticeInsert
	(HttpSession session, String bct_code, int bbc_no, String bbo_title, String bbo_ctt) {
		mav = new ModelAndView();
		mav = js.businessNoticeInsert(session, bct_code, bbc_no, bbo_title, bbo_ctt);
		return mav;
	}

	
	@RequestMapping (value="/businessNoticeUpdate")
	public ModelAndView businessNoticeUpdate(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = js.businessNoticeUpdate(request);
		return mav;
	}
	
	@RequestMapping (value="/businessNoticeUpdateForm", method=RequestMethod.GET)
	public ModelAndView businessNoticeUpdateForm(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = js.businessNoticeUpdateForm(request);
		return mav;
	}

	@RequestMapping (value="/businessNoticeDelete", method=RequestMethod.GET)
	public ModelAndView businessNoticeDelete(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = js.businessNoticeDelete(request);
		return mav;
	}
	
	@RequestMapping (value="/businessBasicInfo", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView businessBasicInfo(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = js.businessBasicInfo(request);
		return mav;
	}
	
	@RequestMapping (value="/businessGallery", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView businessGallery(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = js.businessGallery(request);
		return mav;
	}
	
	@RequestMapping (value="/businessDetailNoticeList", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView businessDetailNoticeList(HttpServletRequest request, Integer pageNum) {
		mav = new ModelAndView();
		mav = js.businessDetailNoticeList(request, pageNum);
		return mav;
	}
	@RequestMapping(value = "/adminPage", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		mav = new ModelAndView();
		mav.setViewName("adminPage");
		return mav;
	}
	
	@RequestMapping (value="/unconfirmBusiness", method=RequestMethod.GET)
	public ModelAndView businessConfirm(HttpSession session) {
		mav = new ModelAndView();
		mav = js.unconfirmBusiness(session);
		return mav;
	}
	
	@RequestMapping (value="/chkLicense", method=RequestMethod.GET)
	public ModelAndView chkLicense(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = js.chkLicense(request);
		return mav;
	}
	
	@RequestMapping (value="/confirmLicense", method=RequestMethod.GET)
	public ModelAndView confirmLicense(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = js.confirmLicense(request);
		return mav;
	}

}
