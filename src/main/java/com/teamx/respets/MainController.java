package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.Booking;
import com.teamx.respets.service.MainService;

@Controller
public class MainController {

	@Autowired
	private MainService ms;

	// 메인 페이지
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = ms.index();
		mav.setViewName("index");
		return mav;
	} // method End

	// 기업 상세페이지 기본정보
	@RequestMapping(value = "/businessBasicInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView businessBasicInfo(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav = ms.businessBasicInfo(request);
		return mav;
	}

	// 기업 상세페이지 갤러리
	@RequestMapping(value = "/businessGallery", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView businessGallery(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav = ms.businessGallery(request);
		return mav;
	}

	// 기업 상세페이지 공지사항 리스트
	@RequestMapping(value = "/businessDetailNoticeList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView businessDetailNoticeList(HttpServletRequest request, Integer pageNum) {
		ModelAndView mav = new ModelAndView();
		mav = ms.businessDetailNoticeList(request, pageNum);
		return mav;
	}

	// 주소선택 후 기업리스트 페이지 (예약)
	@RequestMapping(value = "/searchList", method = RequestMethod.GET)
	public ModelAndView searchList(HttpServletRequest request, Integer pageNum) {
		System.out.println("search check");
		ModelAndView mav = new ModelAndView();
		mav = ms.searchList(request, pageNum); // 업종코드,날짜,지역
		System.out.println("end");
		return mav;
	} // method End

	// 업종버튼선택 후 기업리스트 페이지 (예약)
	@RequestMapping(value = "/businessList", method = RequestMethod.GET)
	public ModelAndView businessList(HttpServletRequest request, Integer pageNum) {
		ModelAndView mav = new ModelAndView();
		mav = ms.businessList(request, pageNum); // 업종코드
		return mav;
	}

	// 예약 페이지
	@RequestMapping(value = "/bookingForm", method = RequestMethod.GET)
	public ModelAndView bookingForm(HttpServletRequest request) {
		ModelAndView mav = ms.bookingForm(request);
		mav.setViewName("bookingForm");
		return mav;
	} // method End

	// 예약
	@RequestMapping(value = "/booking", method = RequestMethod.POST)
	public ModelAndView booking(Booking bk, HttpServletRequest request) {
		ModelAndView mav = ms.insertBooking(bk, request);
		mav.setViewName("bookingSuccess");
		return mav;
	} // method End

}
