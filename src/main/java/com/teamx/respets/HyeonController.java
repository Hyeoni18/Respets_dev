package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.Personal;
import com.teamx.respets.service.HyeonService;

@Controller
public class HyeonController {
	@Autowired
	private HyeonService hy;

	ModelAndView mav;

	/* 개인 */

	/* 개인 회원정보 페이지 */
	@RequestMapping(value = "/myInfo")
	public ModelAndView myInfo(HttpSession session) {
		mav = hy.myInfo(session);
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
		int update = hy.myPwUpdate(newPw, session);
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
		mav = hy.myInfoUpdateForm(mb, session);
		return mav;
	}

	/* 개인 정보 수정 */
	@RequestMapping(value = "/myInfoUpdate", method = RequestMethod.POST)
	public ModelAndView myInfoUpdate(MultipartHttpServletRequest request) {
		mav = hy.myInfoUpdate(request);
		mav.setViewName("redirect:/myInfo");
		return mav;
	}

	/* 개인 회원탈퇴 */
	@RequestMapping(value = "/personalPartDelete")
	public ModelAndView personalPartDelete(HttpSession session) {
		mav = new ModelAndView();
		mav = hy.personalPartDelete(session);
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
		mav = hy.myBookingCancel(request);
		return mav;
	}

	/* 개인 예약 전체리스트 */
	@RequestMapping(value = "/personalAllBookingList")
	public ModelAndView personalAllBookingList(HttpSession session, Integer pageNum) {
		mav = hy.personalAllBookingList(session, pageNum);
		return mav;
	}

	/* 기업 */

	/* 기업정보 수정 */
	@RequestMapping(value = "/businessInfoUpdate", method = RequestMethod.POST)
	public ModelAndView businessInfoUpdate(MultipartHttpServletRequest request) {
		mav = hy.businessInfoUpdate(request);
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
		mav = hy.todayScheduleList(session);
		return mav;
	}


//	/* 혜연 예약 상세내역 */
//	@RequestMapping(value = "/myBookingDetail")
//	public ModelAndView myBookingDetail(HttpServletRequest request) {
//		mav = hy.myBookingDetail(request);
//		return mav;
//	}

	/* 업종 종류 불러오기(전체예약페이지) */
	@RequestMapping(value = "/businessBookingList")
	public ModelAndView businessBookingList(HttpSession session) {
		mav = hy.businessBookingList(session);
		return mav;
	}

	/* 서비스 종류 불러오기(서비스페이지) */
	@RequestMapping(value = "/serviceManagement")
	public ModelAndView serviceManagement(HttpSession session) {
		mav = hy.serviceManagement(session);
		return mav;
	}

	/* 기업 정보 페이지 */
	@RequestMapping(value = "/businessInfoDetail")
	public ModelAndView businessInfoDetail(HttpSession session) {
		mav = hy.businessInfoDetail(session);
		return mav;
	}

	/* 기업 회원탈퇴 */
	@RequestMapping(value = "/businessPartDelete")
	public ModelAndView businessPartDelete(HttpSession session) {
		mav = hy.businessPartDelete(session);
		return mav;
	}

	/* ajax */

	/* 개인 현제 비번과 받아온 비번 비교 ajax */
	@RequestMapping(value = "/myPwCheck", method = RequestMethod.POST)
	public @ResponseBody int myPwCheck(String now, HttpServletRequest request) {
		int result = hy.myPwCheck(now, request);
		return result;
	}

	/* 오늘 예약 방문 확인 ajax */
	@RequestMapping(value = "/todayScheduleListCheck", produces = "application/text; charset=utf8")
	public @ResponseBody String todayScheduleListCheck(HttpServletRequest request) {
		String result = hy.todayScheduleListCheck(request);
		return result;
	}

	/* 오늘 예약 방문취소 ajax */
	@RequestMapping(value = "/todayScheduleListCancel", produces = "application/text; charset=utf8")
	public @ResponseBody String todayScheduleListCancel(HttpServletRequest request) {
		String result = hy.todayScheduleListCancel(request);
		return result;
	}

	/* 방문완료 예약리스트 ajax */
	@RequestMapping(value = "/vs_chkOkList", produces = "application/text; charset=utf8")
	public @ResponseBody String vs_chkOkList(HttpServletRequest request) {
		String text = hy.vs_chkOkList(request);
		return text;
	}

	/* 오늘 예약 일정 전체 ajax */
	@RequestMapping(value = "/todayAllScheduleList", produces = "application/text; charset=utf8")
	public @ResponseBody String todayAllScheduleList(HttpServletRequest request) {
		String text = hy.todayAllScheduleList(request);
		return text;
	}

	/* 오늘 방문 확인된 예약 리스트 ajax */
	@RequestMapping(value = "/todayAllScheduleListOk", produces = "application/text; charset=utf8")
	public @ResponseBody String todayAllScheduleListOk(HttpServletRequest request) {
		String text = hy.todayAllScheduleListOk(request);
		return text;
	}

	/* 서비스별 전체예약 리스트 ajax */
	@RequestMapping(value = "/bctBookingList", produces = "application/text; charset=utf8")
	public @ResponseBody String bctBookingList(HttpServletRequest request) {
		String text = hy.bctBookingList(request);
		return text;
	}

	/* 서비스별 방문확인 된 전체예약 리스트 ajax */
	@RequestMapping(value = "/bctBookingListOk", produces = "application/text; charset=utf8")
	public @ResponseBody String bctBookingListOk(HttpServletRequest request) {
		String text = hy.bctBookingListOk(request);
		return text;
	}

	/* 서비스별 예약 리스트 ajax */
	@RequestMapping(value = "/bctBookingListCheck", produces = "application/text; charset=utf8")
	public @ResponseBody String bctBookingListCheck(HttpServletRequest request) {
		String result = hy.bctBookingListCheck(request);
		return result;
	}

	/* 서비스별 예약 방문 취소 ajax */
	@RequestMapping(value = "/bctBookingListCancel", produces = "application/text; charset=utf8")
	public @ResponseBody String bctBookingListCancel(HttpServletRequest request) {
		String text = hy.bctBookingListCancel(request);
		return text;
	}

	/* 기업 전체예약 리스트 ajax */
	@RequestMapping(value = "/businessAllBookingList", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody String businessAllBookingList(HttpServletRequest request) {
		String text = hy.businessAllBookingList(request);
		return text;
	}

	/* 전체예약 페이징 ajax */
	@RequestMapping(value = "/AllPaging", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody String AllPaging(HttpServletRequest request) {
		String text = hy.AllPaging(request);
		return text;
	}

	/* 서비스별 예약 페이징 ajax */
	@RequestMapping(value = "/bctAllPaging", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody String bctAllPaging(HttpServletRequest request) {
		String text = hy.bctAllPaging(request);
		return text;
	}

	/* 전체 예약에서의 검색 ajax */
	@RequestMapping(value = "/searchAllList", produces = "application/text; charset=utf8")
	public @ResponseBody String searchAllList(HttpServletRequest request) {
		String text = hy.searchAllList(request);
		return text;
	}

	/* 전체 예약에서의 검색 페이징 ajax */
	@RequestMapping(value = "/searchAllListPaging", produces = "application/text; charset=utf8")
	public @ResponseBody String searchAllListPaging(HttpServletRequest request) {
		String text = hy.searchAllListPaging(request);
		return text;
	}

	/* 서비스별 전체 예약 리스트 ajax */
	@RequestMapping(value = "/businessAllBctBookingList", produces = "application/text; charset=utf8")
	public @ResponseBody String businessAllBctBookingList(HttpServletRequest request) {
		String text = hy.businessAllBctBookingList(request);
		return text;
	}

	/* 서비스별 예약에서의 검색 ajax */
	@RequestMapping(value = "/searchBctAllsList", produces = "application/text; charset=utf8")
	public @ResponseBody String searchBctAllsList(HttpServletRequest request) {
		String text = hy.searchBctAllsList(request);
		return text;
	}

	/* 서비스별 예약에서의 검색 페이징 ajax */
	@RequestMapping(value = "/searchBctAllsListPaging", produces = "application/text; charset=utf8")
	public @ResponseBody String searchBctAllsListPaging(HttpServletRequest request) {
		String text = hy.searchBctAllsListPaging(request);
		return text;
	}
}
