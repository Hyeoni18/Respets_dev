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

import com.teamx.respets.bean.Admin;
import com.teamx.respets.bean.Personal;
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

	/* 개인 회원정보 페이지 */
	@RequestMapping(value = "/myInfo")
	public ModelAndView myInfo(HttpSession session) {
		mav = ps.myInfo(session);
		return mav;
	}

	/* 개인 비밀번호 수정 페이지 */
	@RequestMapping(value = "/myPwUpdateForm")
	public ModelAndView myPwUpdateForm(Personal mb) {
		mav = new ModelAndView();
		mav.addObject("mb", mb);
		mav.setViewName("myPwUpdateForm");
		return mav;
	}

	/* 개인 비밀번호 수정 */
	@RequestMapping(value = "/myPwUpdate", produces = "application/text; charset=utf8", method = RequestMethod.POST)
	public ModelAndView myPwUpdate(String newPw, HttpSession session) {
		mav = new ModelAndView();
		int update = ps.myPwUpdate(newPw, session);
		if (update == 1) {
			mav.setViewName("redirect:/myInfo");
		} else {
			mav.addObject("fail", "alert('비밀번호 변경에 실패했습니다.')");
			mav.setViewName("myPwUpdateForm");
		}
		return mav;
	}

	/* 개인정보 수정 페이지 */
	@RequestMapping(value = "/myInfoUpdateForm")
	public ModelAndView myInfoUpdateForm(Personal mb, HttpSession session) {
		mav = ps.myInfoUpdateForm(mb, session);
		return mav;
	}

	/* 개인 정보 수정 */
	@RequestMapping(value = "/myInfoUpdate", method = RequestMethod.POST)
	public ModelAndView myInfoUpdate(MultipartHttpServletRequest request) {
		mav = ps.myInfoUpdate(request);
		mav.setViewName("redirect:/myInfo");
		return mav;
	}

	/* 개인 회원탈퇴 */
	@RequestMapping(value = "/personalPartDelete")
	public ModelAndView personalPartDelete(HttpSession session) {
		mav = new ModelAndView();
		mav = ps.personalPartDelete(session);
		return mav;
	}

	/* 예약 취소안내 페이지 */
	@RequestMapping(value = "/myBookingCancelPage")
	public ModelAndView myBookingCancelPage(HttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		mav.addObject("bk_no", request.getParameter("bk_no"));
		mav.setViewName("myBookingCancelPage");
		return mav;
	}

	/* 예약 취소 */
	@RequestMapping(value = "/myBookingCancel")
	public ModelAndView myBookingCancel(HttpServletRequest request) {
		mav = ps.myBookingCancel(request);
		return mav;
	}

	/* 개인 예약 전체리스트 */
	@RequestMapping(value = "/personalAllBookingList")
	public ModelAndView personalAllBookingList(HttpSession session, Integer pageNum) {
		mav = ps.personalAllBookingList(session, pageNum);
		return mav;
	}

	/* 나의 반려동물 정보(목록) */
	@RequestMapping(value = "/petList", method = RequestMethod.GET)
	public ModelAndView petList(HttpSession session) {
		mav = new ModelAndView();
		mav = ps.petList(session);
		return mav;
	}

	/* 반려동물 등록 폼 */
	@RequestMapping(value = "/petInsertForm", method = RequestMethod.GET)
	public ModelAndView petInsertForm(String per_no) {
		mav = new ModelAndView();
		mav.setViewName("petInsertForm");
		return mav;
	}

	/* 반려동물 등록 */
	@RequestMapping(value = "/petInsert", method = RequestMethod.POST)
	public ModelAndView petInsert(MultipartHttpServletRequest multi) {
		mav = new ModelAndView();
		mav = ps.petInsert(multi);
		return mav;
	}

	/* 반려동물 상세 정보 */
	@RequestMapping(value = "/petInfoDetail", method = RequestMethod.GET)
	public ModelAndView petInfoDetail(String pet_no) {
		mav = new ModelAndView();
		mav = ps.petInfoDetail(pet_no);
		return mav;
	}

	/* 반려동물 정보 수정 폼 */
	@RequestMapping(value = "/petInfoUpdateForm", method = RequestMethod.GET)
	public ModelAndView petInfoUpdateForm(String pet_no) {
		System.out.println(pet_no);
		mav = new ModelAndView();
		mav = ps.petInfoUpdateForm(pet_no);
		return mav;
	}

	/* 반려동물 정보 수정 */
	@RequestMapping(value = "/petInfoUpdate", method = RequestMethod.POST)
	public ModelAndView petInfoUpdate(MultipartHttpServletRequest multi) {
		mav = new ModelAndView();
		mav = ps.petInfoUpdate(multi);
		return mav;
	}

	/* 반려동물 삭제 */
	@RequestMapping(value = "/petDelete", method = RequestMethod.GET)
	public ModelAndView petDelete(String pet_no) {
		mav = new ModelAndView();
		mav = ps.petDelete(pet_no);
		return mav;
	}
	
	/* 개인 캘린더 */
	@RequestMapping (value="/personalCalendar", method=RequestMethod.GET)
	public ModelAndView personalCalendar(HttpSession session) {
		mav = new ModelAndView();
		mav=ps.personalCalendar(session);
		return mav;
	}
	
	// 개인 즐겨찾기 페이지
	@RequestMapping(value = "/likeBusinessList", method = RequestMethod.GET)
	public ModelAndView likeBusinessList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String list = ps.likeBusinessList(request);
		mav.addObject("list", list);
		mav.setViewName("likeBusinessList");
		return mav;
	} // method End

	// 개인 즐겨찾기 삭제
	@RequestMapping(value = "/likeBusinessCancel", method = RequestMethod.GET)
	public ModelAndView likeBusinessCancel(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		ps.likeBusinessCancel(request);
		mav.setViewName("redirect:likeBusinessList");
		return mav;
	} // method End

}
