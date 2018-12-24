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

import com.teamx.respets.bean.Business;
import com.teamx.respets.service.BusinessService;

@Controller
public class BusinessController {
	ModelAndView mav;
	@Autowired
	private BusinessService bs;

	// 기업 공지사항 리스트
	@RequestMapping(value = "/businessNoticeList", method = RequestMethod.GET)
	public ModelAndView businessNoticeList(HttpSession session, Integer pageNum) {
		mav = new ModelAndView();
		mav = bs.businessNoticeList(session, pageNum);
		return mav;
	}

	// 기업 공지사항 디테일
	@RequestMapping(value = "/businessNoticeDetail")
	public ModelAndView businessNoticeDetail(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = bs.businessNoticeDetail(request);
		return mav;
	}

	// 기업 공지사항 리스트(검)
	@RequestMapping(value = "/searchBusinessNotice")
	public ModelAndView searchBusinessNotice(HttpSession session, String select, String search, Integer pageNum) {
		mav = new ModelAndView();
		mav = bs.searchBusinessNotice(session, select, search, pageNum);
		return mav;
	}

	// 기업 공지사항 글쓰기
	@RequestMapping(value = "/businessNoticeWriteForm")
	public ModelAndView businessNoticeWriteForm() {
		mav = new ModelAndView();
		mav.setViewName("businessNoticeWriteForm");
		return mav;
	}

	// 기업 공지사항 글쓰기 등록
	@RequestMapping(value = "/businessNoticeInsert")
	public ModelAndView businessNoticeInsert(HttpSession session, String bct_code, int bbc_no, String bbo_title,
			String bbo_ctt) {
		mav = new ModelAndView();
		mav = bs.businessNoticeInsert(session, bct_code, bbc_no, bbo_title, bbo_ctt);
		return mav;
	}

	// 기업 공지사항 수정
	@RequestMapping(value = "/businessNoticeUpdate")
	public ModelAndView businessNoticeUpdate(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = bs.businessNoticeUpdate(request);
		return mav;
	}

	// 기업 공지사항 수정 폼(기존 정보 불러오기)
	@RequestMapping(value = "/businessNoticeUpdateForm", method = RequestMethod.GET)
	public ModelAndView businessNoticeUpdateForm(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = bs.businessNoticeUpdateForm(request);
		return mav;
	}

	// 기업 공지사항 지우
	@RequestMapping(value = "/businessNoticeDelete", method = RequestMethod.GET)
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

	// 기업의 업종등록 신청 페이지로 이동하기 전에 기업이 가진 서비스 검색-업종등록
	@RequestMapping(value = "/serviceInsertForm", method = RequestMethod.GET)
	public ModelAndView serviceInsertForm(HttpSession session) {
		mav = new ModelAndView();
		mav = bs.serviceInsertForm(session);
		return mav;
	}

	// 추가할 업종들의 정보를 가지고 등록하러 이동 -업종등록
	@RequestMapping(value = "/serviceInsert", method = RequestMethod.POST)
	public ModelAndView serviceInsert(MultipartHttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		mav = bs.serviceInsert(request, session);
		return mav;
	}

	// 기업의 업종의 자세한 정보를 보기위한 메소드 -업종정보
	@RequestMapping(value = "/serviceDetail", method = RequestMethod.GET)
	public ModelAndView serviceDetail(HttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		mav = bs.serviceDetail(request, session);
		// mav.setViewName("serviceDetail");
		return mav;
	}

	// 기업의 상세정보 페이지에서 수정폼으로 이동 -업종정보
	@RequestMapping(value = "/serviceUpdateForm", method = RequestMethod.POST)
	public ModelAndView serviceUpdateForm(HttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		mav = bs.serviceUpdateForm(request, session);
		return mav;
	}

	// 기업의 상세정보 수정
	@RequestMapping(value = "/serviceUpdate", method = RequestMethod.POST)
	public ModelAndView serviceUpdate(MultipartHttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		mav = bs.serviceUpdate(request, session);
		return mav;
	}

	// 기업의 업종 삭제
	@RequestMapping(value = "/serviceDelete", method = RequestMethod.POST)
	public ModelAndView serviceDelete(HttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		mav = bs.serviceDelete(request, session);
		return mav;
	}

	// 직원 리스트 불러오기 전 업종 선택 버튼 생성
	@RequestMapping(value = "/stepListBut", method = RequestMethod.GET)
	public ModelAndView stepListBut(HttpSession session) {
		mav = new ModelAndView();
		mav = bs.stepListBut(session);
		return mav;
	}

	// 직원 추가 페이지, 이동하기 전에 기업이 가진 업종을 검사
	@RequestMapping(value = "/stepInsertFormBut", method = RequestMethod.GET)
	public ModelAndView stepInsertFormBut(HttpSession session) {
		mav = new ModelAndView();
		mav = bs.stepInsertFormBut(session);
		return mav;
	}

	// 직원 추가
	@RequestMapping(value = "/stepInsert", method = RequestMethod.POST)
	public ModelAndView stepInsert(MultipartHttpServletRequest request,HttpSession session) {
		mav = new ModelAndView();
		mav = bs.stepInsert(request,session);
		return mav;
	}

	// 직원 상세정보
	@RequestMapping(value = "/stepDetail", method = RequestMethod.GET)
	public ModelAndView stepDetail(HttpServletRequest request,HttpSession session) {
		mav = new ModelAndView();
		mav = bs.stepDetail(request,session);
		return mav;
	}

	// 직원 상세정보 수정
	@RequestMapping(value = "/stepUpdate", method = RequestMethod.POST)
	public ModelAndView stepUpdate(MultipartHttpServletRequest request) {
		mav = new ModelAndView();
		mav = bs.stepUpdate(request);
		return mav;
	}

	// 직원 삭제
	@RequestMapping(value = "/stepDelete", method = RequestMethod.POST)
	public ModelAndView stepDelete(MultipartHttpServletRequest request) {
		mav = new ModelAndView();
		mav = bs.stepDelete(request);
		return mav;
	}
	
	/* 기업리스트에서 기업 클릭 시 - 기업 상세 페이지 */
	@RequestMapping (value="/businessDetailPage", method=RequestMethod.GET)
	public ModelAndView businessDetailPage(HttpSession session, HttpServletRequest request) {
		mav = new ModelAndView();
		mav=bs.businessDetailPage(session, request);
		return mav;
	}
	
	// 서진 : 마이페이지
	@RequestMapping(value = "/myPage", method = RequestMethod.GET)
	public ModelAndView myPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (request.getSession().getAttribute("no").toString().substring(0, 1).equals("P")) {
			mav.setViewName("redirect:/recentMyBookingList");
		} else if(request.getSession().getAttribute("no").toString().substring(0, 1).equals("B")) {
			mav.setViewName("redirect:/todayScheduleList");
		} else {
			mav.setViewName("redirect:/unconfirmBusiness");
		} // else End
		return mav;
	} // method End

	@RequestMapping(value = "/businessPwUpdateForm", method = RequestMethod.GET)
	public ModelAndView businessPwUpdateForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("businessPwUpdateForm");
		return mav;
	} // method End
	
	@RequestMapping(value = "/businessPwUpdate", method = RequestMethod.POST)
	public ModelAndView businessPwUpdate(Business b, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		bs.businessPwUpdate(b, request);
		mav.setViewName("redirect:/businessInfoDetail");
		return mav;
	} // method End
	
	@RequestMapping(value = "/businessInfoUpdateForm", method = RequestMethod.GET)
	public ModelAndView businessPwUpdate(HttpServletRequest request) {
		ModelAndView mav = bs.businessInfoUpdateForm(request);
		mav.setViewName("businessInfoUpdateForm");
		return mav;
	} // method End
	
	// 기업 새로운 예약
	@RequestMapping(value = "/newScheduleList", method = RequestMethod.GET)
	public ModelAndView newScheduleList(HttpServletRequest request) {
		ModelAndView mav = bs.newScheduleList(request);
		mav.setViewName("newScheduleList");
		return mav;
	} // method End
	
	
}
