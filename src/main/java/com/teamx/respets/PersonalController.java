package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.Admin;
import com.teamx.respets.service.PersonalService;
@Controller
public class PersonalController {
	ModelAndView mav;
	@Autowired
	private PersonalService ps;
	
	// 개인마이페이지
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
		mav = ps.recentMyBookingList(session, pageNum);
		return mav;
	}
	
	// 개인예약디테일
	@RequestMapping(value = "/myBookingDetail", method = RequestMethod.GET)
	public ModelAndView recentMyBookingList(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = ps.myBookingDetail(request);
		return mav;
	}

}
