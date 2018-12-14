package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
	
	/* 기업정보 수정 */
	@RequestMapping(value = "/businessInfoUpdate", method = RequestMethod.POST)
	public ModelAndView businessInfoUpdate(MultipartHttpServletRequest request) {
		mav = bs.businessInfoUpdate(request);
		return mav;
	}

	/* 기업 메뉴페이지 */
	@RequestMapping(value = "/businessButtonPage", method = RequestMethod.GET)
	public ModelAndView businessButtonPage() {
		mav = new ModelAndView();
		mav.setViewName("businessButtonPage");
		return mav;
	}

	/* 기업 서비스 버튼 불러오기 */
	@RequestMapping(value = "/todayScheduleList")
	public ModelAndView todayScheduleList(HttpSession session) {
		mav = bs.todayScheduleList(session);
		return mav;
	}

	/* 업종 종류 불러오기(전체예약페이지) */
	@RequestMapping(value = "/businessBookingList")
	public ModelAndView businessBookingList(HttpSession session) {
		mav = bs.businessBookingList(session);
		return mav;
	}

	/* 서비스 종류 불러오기(서비스페이지) */
	@RequestMapping(value = "/serviceManagement")
	public ModelAndView serviceManagement(HttpSession session) {
		mav = bs.serviceManagement(session);
		return mav;
	}

	/* 기업 정보 페이지 */
	@RequestMapping(value = "/businessInfoDetail")
	public ModelAndView businessInfoDetail(HttpSession session) {
		mav = bs.businessInfoDetail(session);
		return mav;
	}

	/* 기업 회원탈퇴 */
	@RequestMapping(value = "/businessPartDelete")
	public ModelAndView businessPartDelete(HttpSession session) {
		mav = bs.businessPartDelete(session);
		return mav;
	}

}
