package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.service.BusinessService;

@Controller
public class BusinessController {
	ModelAndView mav;
	@Autowired
	private BusinessService bs;
	
	//기업 공지사항 리스트
	@RequestMapping (value="/businessNoticeList", method=RequestMethod.GET)
	public ModelAndView businessNoticeList(HttpSession session, Integer pageNum) {
		mav = new ModelAndView();
		mav = bs.businessNoticeList(session, pageNum);
		return mav;
	}
	
	//기업 공지사항 디테일
	@RequestMapping (value="/businessNoticeDetail")
	public ModelAndView businessNoticeDetail(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = bs.businessNoticeDetail(request);
		return mav;
	}
	
	//기업 공지사항 리스트(검)
	@RequestMapping (value="/searchBusinessNotice")
	public ModelAndView searchBusinessNotice(HttpSession session, String select, String search, Integer pageNum) {
		mav = new ModelAndView();
		mav = bs.searchBusinessNotice(session, select, search, pageNum);
		return mav;
	}
	
	//기업 공지사항 글쓰기
	@RequestMapping (value="/businessNoticeWriteForm")
	public ModelAndView businessNoticeWriteForm() {
		mav = new ModelAndView();
		mav.setViewName("businessNoticeWriteForm");
		return mav;
	}
	
	//기업 공지사항 글쓰기 등록
	@RequestMapping (value="/businessNoticeInsert")
	public ModelAndView businessNoticeInsert
	(HttpSession session, String bct_code, int bbc_no, String bbo_title, String bbo_ctt) {
		mav = new ModelAndView();
		mav = bs.businessNoticeInsert(session, bct_code, bbc_no, bbo_title, bbo_ctt);
		return mav;
	}

	//기업 공지사항 수정
	@RequestMapping (value="/businessNoticeUpdate")
	public ModelAndView businessNoticeUpdate(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = bs.businessNoticeUpdate(request);
		return mav;
	}
	
	//기업 공지사항 수정 폼(기존 정보 불러오기)
	@RequestMapping (value="/businessNoticeUpdateForm", method=RequestMethod.GET)
	public ModelAndView businessNoticeUpdateForm(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = bs.businessNoticeUpdateForm(request);
		return mav;
	}
	
	//기업 공지사항 지우
	@RequestMapping (value="/businessNoticeDelete", method=RequestMethod.GET)
	public ModelAndView businessNoticeDelete(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = bs.businessNoticeDelete(request);
		return mav;
	}

}
