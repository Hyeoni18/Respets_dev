package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.service.MainService;

@Controller
public class MainController {
	ModelAndView mav;
	@Autowired
	private MainService ms;
	
	//기업 상세페이지 기본정보
	@RequestMapping (value="/businessBasicInfo", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView businessBasicInfo(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = ms.businessBasicInfo(request);
		return mav;
	}
	
	//기업 상세페이지 갤러리
	@RequestMapping (value="/businessGallery", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView businessGallery(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = ms.businessGallery(request);
		return mav;
	}
	
	//기업 상세페이지 공지사항 리스트
	@RequestMapping (value="/businessDetailNoticeList", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView businessDetailNoticeList(HttpServletRequest request, Integer pageNum) {
		mav = new ModelAndView();
		mav = ms.businessDetailNoticeList(request, pageNum);
		return mav;
	}

}
