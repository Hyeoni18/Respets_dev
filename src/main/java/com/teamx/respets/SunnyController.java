package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.AdminBoard;
import com.teamx.respets.service.SunnyService;

@Controller
public class SunnyController {
	@Autowired
	private SunnyService ss;
	@Autowired
	HttpSession session;
	
	ModelAndView mav;
	
	
	/*----- Pet -----*/
	
	/*진선*/
	/*@RequestMapping (value="/petList", method=RequestMethod.GET)
	public ModelAndView petList(HttpServletRequest request) {
		mav = new ModelAndView();
		mav=ss.petList(request);
		return mav;
	}*/
	
	/*진선*/
	@RequestMapping (value="/petList", method=RequestMethod.GET)
	public ModelAndView petList(String per_no) {
		mav = new ModelAndView();
		mav=ss.petList(per_no);
		return mav;
	}
	
	/*진선*/
	@RequestMapping (value="/petInsertForm", method=RequestMethod.GET)
	public ModelAndView petInsertForm(String per_no) {
		mav = new ModelAndView();
		mav.setViewName("petInsertForm");
		return mav;
	}
	
	/*진선*/
	@RequestMapping (value="/petInsert", method=RequestMethod.POST)
	public ModelAndView petInsert(MultipartHttpServletRequest multi) {		
		mav = new ModelAndView();
		mav=ss.petInsert(multi);
		return mav;
	}	
	
	/*진선*/
	@RequestMapping (value="/petInfoDetail", method=RequestMethod.GET)
	public ModelAndView petInfoDetail(String pet_no) {
		mav = new ModelAndView();
		mav=ss.petInfoDetail(pet_no);
		return mav;
	}

	/*진선*/
	@RequestMapping (value="/petInfoUpdateForm", method=RequestMethod.GET)
	public ModelAndView petInfoUpdateForm(String pet_no) {
		System.out.println(pet_no);
		mav = new ModelAndView();
		mav=ss.petInfoUpdateForm(pet_no);
		return mav;
	}
	
	/*진선*/
	@RequestMapping (value="/petInfoUpdate", method=RequestMethod.POST)
	public ModelAndView petInfoUpdate(MultipartHttpServletRequest multi) {
		mav = new ModelAndView();
		mav=ss.petInfoUpdate(multi);
		return mav;
	}
	
	/*진선*/
	@RequestMapping (value="/petDelete", method=RequestMethod.GET)
	public ModelAndView petDelete(String pet_no) {
		mav = new ModelAndView();
		mav=ss.petDelete(pet_no);
		return mav;
	}
	
	
	
	
	/*----- AdminBoard -----*/
	
	
	/*
	@RequestMapping (value="/noticeList", method=RequestMethod.GET)
	public ModelAndView noticeList(Integer pageNum) {
		System.out.println(pageNum);
		mav = new ModelAndView();
		mav=ss.noticeList(pageNum);
		return mav;
	}*/
	
	/*진선*/
	@RequestMapping (value="/noticeList", method=RequestMethod.GET)
	public ModelAndView noticeList(Integer pageNum) {		
		mav = new ModelAndView();
		mav=ss.noticeList(pageNum);
		return mav;
	}
	
	/*진선*/
	@RequestMapping (value="/noticeListSearch", method=RequestMethod.GET)
	public ModelAndView noticeListSearch(Integer pageNum, String abc_name, String search) {		
		mav = new ModelAndView();
		mav=ss.noticeListSearch(pageNum,abc_name,search);
		return mav;
	}

	/*진선*/
	@RequestMapping (value="/noticeListBusiness", method=RequestMethod.GET)
	public ModelAndView noticeListBusiness(Integer pageNum) {
		mav = new ModelAndView();
		mav=ss.noticeListBusiness(pageNum);
		return mav;
	}
	
	/*진선*/
	@RequestMapping (value="/noticeListPersonal", method=RequestMethod.GET)
	public ModelAndView noticeListPersonal(Integer pageNum) {
		mav = new ModelAndView();
		mav=ss.noticeListPersonal(pageNum);
		return mav;
	}
	
	/*진선*/
	@RequestMapping (value="/noticeWriteForm", method=RequestMethod.GET)
	public ModelAndView noticeWriteForm() {
		mav = new ModelAndView();
		mav.setViewName("noticeWriteForm");
		return mav;
	}
	
	/*진선*/
	@RequestMapping (value="/noticeInsert", method=RequestMethod.GET)
	public ModelAndView noticeInsert(String abc_no, String abo_title, String abo_ctt) {
		mav = new ModelAndView();
		mav=ss.noticeInsert(abc_no, abo_title, abo_ctt);
		return mav;
	}
	
	/*진선*/
	@RequestMapping (value="/noticeDetail", method=RequestMethod.GET)
	public ModelAndView noticeDetail(String abo_no) {
		mav = new ModelAndView();
		mav=ss.noticeDetail(abo_no);
		return mav;
	}
	
	/*진선*/
	@RequestMapping (value="/noticeUpdateForm", method=RequestMethod.GET)
	public ModelAndView noticeUpdateForm(String abo_no) {
		mav = new ModelAndView();
		mav=ss.noticeUpdateForm(abo_no);
		return mav;
	}
	
	/*진선*/
	@RequestMapping (value="/noticeUpdate", method=RequestMethod.GET)
	public ModelAndView noticeUpdate(AdminBoard abo) {
		mav = new ModelAndView();
		mav=ss.noticeUpdate(abo);
		return mav;
	}
	
	/*진선*/
	@RequestMapping (value="/noticeDelete", method=RequestMethod.GET)
	public ModelAndView noticeDelete(String abo_no) {
		mav = new ModelAndView();
		mav=ss.noticeDelete(abo_no);
		return mav;
	}

	
	
	
	/*---- Business Detail ----*/
	
	/*기업 상세 페이지*/
	@RequestMapping (value="/businessDetailPage", method=RequestMethod.GET)
	public ModelAndView businessDetailPage(String bus_no, String bct_code) {
		mav = new ModelAndView();
		mav=ss.businessDetailPage(bus_no,bct_code);
		return mav;
	}
	
	/*Ajax : 즐겨찾기 클릭*/
	@RequestMapping(value = "/favoriteChange", method=RequestMethod.POST)
	@ResponseBody public int favoriteChange(HttpServletRequest request) {
		System.out.println("Controller favoriteChange");
		int data = ss.favoriteChange(request);
		return data;
	}

	
	
	/*---- Personal Calendar ----*/
	
	/*개인 캘린더*/
	@RequestMapping (value="/personalCalendar", method=RequestMethod.GET)
	public ModelAndView personalCalendar(HttpSession session) {
		mav = new ModelAndView();
		mav=ss.personalCalendar(session);
		return mav;
	}
}
