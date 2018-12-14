package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamx.respets.service.BusinessService;
import com.teamx.respets.service.PersonalService;

@RestController
public class AjaxController {
	@Autowired
	private PersonalService ps;
	@Autowired
	private BusinessService bs;

	/* 개인 현제 비번과 받아온 비번 비교 ajax */
	@RequestMapping(value = "/myPwCheck", method = RequestMethod.POST)
	public @ResponseBody int myPwCheck(String now, HttpServletRequest request) {
		int result = ps.myPwCheck(now, request);
		return result;
	}

	/* 오늘 예약 방문 확인 ajax */
	@RequestMapping(value = "/todayScheduleListCheck", produces = "application/text; charset=utf8")
	public @ResponseBody String todayScheduleListCheck(HttpServletRequest request) {
		String result = bs.todayScheduleListCheck(request);
		return result;
	}

	/* 오늘 예약 방문취소 ajax */
	@RequestMapping(value = "/todayScheduleListCancel", produces = "application/text; charset=utf8")
	public @ResponseBody String todayScheduleListCancel(HttpServletRequest request) {
		String result = bs.todayScheduleListCancel(request);
		return result;
	}

	/* 방문완료 예약리스트 ajax */
	@RequestMapping(value = "/vs_chkOkList", produces = "application/text; charset=utf8")
	public @ResponseBody String vs_chkOkList(HttpServletRequest request) {
		String text = bs.vs_chkOkList(request);
		return text;
	}

	/* 오늘 예약 일정 전체 ajax */
	@RequestMapping(value = "/todayAllScheduleList", produces = "application/text; charset=utf8")
	public @ResponseBody String todayAllScheduleList(HttpServletRequest request) {
		String text = bs.todayAllScheduleList(request);
		return text;
	}

	/* 오늘 방문 확인된 예약 리스트 ajax */
	@RequestMapping(value = "/todayAllScheduleListOk", produces = "application/text; charset=utf8")
	public @ResponseBody String todayAllScheduleListOk(HttpServletRequest request) {
		String text = bs.todayAllScheduleListOk(request);
		return text;
	}

	/* 서비스별 전체예약 리스트 ajax */
	@RequestMapping(value = "/bctBookingList", produces = "application/text; charset=utf8")
	public @ResponseBody String bctBookingList(HttpServletRequest request) {
		String text = bs.bctBookingList(request);
		return text;
	}

	/* 서비스별 방문확인 된 전체예약 리스트 ajax */
	@RequestMapping(value = "/bctBookingListOk", produces = "application/text; charset=utf8")
	public @ResponseBody String bctBookingListOk(HttpServletRequest request) {
		String text = bs.bctBookingListOk(request);
		return text;
	}

	/* 서비스별 예약 리스트 ajax */
	@RequestMapping(value = "/bctBookingListCheck", produces = "application/text; charset=utf8")
	public @ResponseBody String bctBookingListCheck(HttpServletRequest request) {
		String result = bs.bctBookingListCheck(request);
		return result;
	}

	/* 서비스별 예약 방문 취소 ajax */
	@RequestMapping(value = "/bctBookingListCancel", produces = "application/text; charset=utf8")
	public @ResponseBody String bctBookingListCancel(HttpServletRequest request) {
		String text = bs.bctBookingListCancel(request);
		return text;
	}

	/* 기업 전체예약 리스트 ajax */
	@RequestMapping(value = "/businessAllBookingList", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody String businessAllBookingList(HttpServletRequest request) {
		String text = bs.businessAllBookingList(request);
		return text;
	}

	/* 전체 예약에서의 검색 ajax */
	@RequestMapping(value = "/searchAllList", produces = "application/text; charset=utf8")
	public @ResponseBody String searchAllList(HttpServletRequest request) {
		String text = bs.searchAllList(request);
		return text;
	}

	/* 서비스별 전체 예약 리스트 ajax */
	@RequestMapping(value = "/businessAllBctBookingList", produces = "application/text; charset=utf8")
	public @ResponseBody String businessAllBctBookingList(HttpServletRequest request) {
		String text = bs.businessAllBctBookingList(request);
		return text;
	}

	/* 서비스별 예약에서의 검색 ajax */
	@RequestMapping(value = "/searchBctAllsList", produces = "application/text; charset=utf8")
	public @ResponseBody String searchBctAllsList(HttpServletRequest request) {
		String text = bs.searchBctAllsList(request);
		return text;
	}

}
