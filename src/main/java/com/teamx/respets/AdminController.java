package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.AdminBoard;
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
	
	/* 공지사항 목록 */
	@RequestMapping (value="/noticeList", method=RequestMethod.GET)
	public ModelAndView noticeList(Integer pageNum) {
		//페이징을 위한 페이지번호(pageNum)를 매개변수로 가져온다
		mav = new ModelAndView();
		mav=as.noticeList(pageNum);
		return mav;
	}
	
	/* 검색 시 공지사항 목록 */
	@RequestMapping (value="/noticeListSearch", method=RequestMethod.GET)
	public ModelAndView noticeListSearch(Integer pageNum, String abc_name, String search) {	
		//페이징을 위한 페이지번호(pageNum)와 검색을 위한 카테고리명(abc_name),검색내용(search)를 매개변수로 가져온다
		mav = new ModelAndView();
		mav=as.noticeListSearch(pageNum, abc_name, search);
		return mav;
	}
	
	/* 공지사항 등록 폼 */
	@RequestMapping (value="/noticeWriteForm", method=RequestMethod.GET)
	public ModelAndView noticeWriteForm() {
		mav = new ModelAndView();		
		mav.setViewName("noticeWriteForm");//noticeWriteForm.jsp로 이동
		return mav;
	}
	
	/* 공지사항 등록 */
	@RequestMapping (value="/noticeInsert", method=RequestMethod.GET)
	public ModelAndView noticeInsert(HttpServletRequest request) {
		//insert할 내용이 모두 담긴 request를 매개변수로 가져온다
		mav = new ModelAndView();
		mav=as.noticeInsert(request);
		return mav;
	}
	
	/* 공지사항 상세 */
	@RequestMapping (value="/noticeDetail", method=RequestMethod.GET)
	public ModelAndView noticeDetail(String abo_no) {
		//선택한 게시물의 상세내용을 출력하기 위해 글번호(abo_no)를 매개변수로 가져온다
		mav = new ModelAndView();
		mav=as.noticeDetail(abo_no);
		return mav;
	}
	
	/* 공지사항 수정 폼 */
	@RequestMapping (value="/noticeUpdateForm", method=RequestMethod.GET)
	public ModelAndView noticeUpdateForm(String abo_no) {
		//수정 폼에 선택한 게시물의 상세내용을 출력하기 위해 글번호(abo_no)를 매개변수로 가져온다
		mav = new ModelAndView();
		mav=as.noticeUpdateForm(abo_no);
		return mav;
	}
	
	/* 공지사항 수정 */
	@RequestMapping (value="/noticeUpdate", method=RequestMethod.GET)
	public ModelAndView noticeUpdate(HttpServletRequest request) {
		//update할 내용이 모두 담긴 request를 매개변수로 가져온다
		mav = new ModelAndView();
		mav=as.noticeUpdate(request);
		return mav;
	}
	
	/* 공지사항 삭제 */
	@RequestMapping (value="/noticeDelete", method=RequestMethod.GET)
	public ModelAndView noticeDelete(String abo_no) {
		//선택한 게시물을 삭제하기 위해 글번호(abo_no)를 매개변수로 가져온다
		mav = new ModelAndView();
		mav=as.noticeDelete(abo_no);
		return mav;
	}
	
}
