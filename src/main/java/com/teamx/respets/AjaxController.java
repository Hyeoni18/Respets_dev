package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamx.respets.bean.Business;
import com.teamx.respets.dao.BusinessDao;
import com.teamx.respets.dao.MainDao;
import com.teamx.respets.service.BusinessService;
import com.teamx.respets.service.LoginService;
import com.teamx.respets.service.MainService;
import com.teamx.respets.service.PersonalService;

@RestController
public class AjaxController {
	@Autowired
	private PersonalService ps;
	@Autowired
	private BusinessService bs;
	@Autowired
	private MainService ms;
	@Autowired
	private BusinessDao bDao;
	@Autowired
	private LoginService ls;

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

	// 주소 선택 후 예약 기업 리스트 페이지에서 태그 선택시 리스트 페이지 (ajax)
	@RequestMapping(value = "/tagSelectList", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String tagSelectList(HttpServletRequest request) {
		String text = ms.tagSelectList(request);
		return text;
	}

	// 주소 선택 후 예약 기업 리스트 페이지에서 태그 선택시 페이징 (ajax)
	@RequestMapping(value = "/tagSelectListAddr", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String tagSelectListAddr(HttpServletRequest request) {
		String text = ms.tagSelectListAddr(request);
		return text;
	}

	// 버튼 선택 후 예약 기업 리스트 페이지에서 태그 선택시 페이징 (ajax)
	@RequestMapping(value = "/butTagSelectListPaging", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String butTagSelectListPaging(HttpServletRequest request, Integer pageNum) {
		String text = ms.butTagSelectListPaging(request, pageNum);
		return text;
	}

	// 버튼 선택 후 예약 기업 리스트 페이지에서 태그 선택시 리스트 페이지 (ajax)
	@RequestMapping(value = "/butTagSelectList", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String butTagSelectList(HttpServletRequest request, Integer pageNum) {
		String text = ms.butTagSelectList(request, pageNum);
		return text;
	}

	// 해당 업종의 직원 리스트
	@RequestMapping(value = "/stepList", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String stepList(HttpServletRequest request, HttpSession session) {
		String text = bs.stepList(request, session);
		return text;
	}

	// 직원 추가 페이지, 업종에 따라 시간과 휴일이 달라짐 (ajax)
	@RequestMapping(value = "/stepInsertForm", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String stepInsertForm(HttpServletRequest request, HttpSession session) {
		String text = bs.stepInsertForm(request, session);
		return text;
	}

	// 업종 수정시 가격검사
	@RequestMapping(value = "/searchPrice", method = RequestMethod.POST)
	@ResponseBody
	public String searchPrice(HttpServletRequest request, HttpSession session) {
		String text = bs.searchPrice(request, session);
		return text;
	}

	/* 기업 상세 페이지 '즐겨찾기' 클릭 */
	@RequestMapping(value = "/favoriteChange", method = RequestMethod.POST)
	@ResponseBody
	public int favoriteChange(HttpServletRequest request) {
		System.out.println("Controller favoriteChange");
		int data = bs.favoriteChange(request);
		return data;
	}

	// 회원 가입 이메일 확인
	@RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
	public int emailCheck(String email) {
		int result = ls.emailCheck(email);
		return result;
	} // method End

	// 기업 회원 가입 사업자 등록 번호 확인
	@RequestMapping(value = "/taxIdCheck", method = RequestMethod.POST)
	public int taxIdCheck(String taxId) {
		int result = ls.taxIdCheck(taxId);
		return result;
	} // method End

	// 예약 페이지 직원 시간 확인
	@RequestMapping(value = "/timeSelect", method = RequestMethod.POST, produces = "application/text;charset=utf8")
	public String timeSelect(HttpServletRequest request) {
		String timeList = ms.timeSelect(request);
		return timeList;
	} // method End

	// 예약 확정
	@RequestMapping(value = "/bookingAccept", method = RequestMethod.POST)
	public void bookingAccept(String bk_no) {
		bDao.bookingAccept(bk_no);
	} // method End

	// 예약 거절
	@RequestMapping(value = "/bookingReject", method = RequestMethod.POST)
	public void bookingReject(String bk_no) {
		bDao.bookingReject(bk_no);
	} // method End

	@RequestMapping(value = "/nowPwCheck", method = RequestMethod.POST)
	public int nowPwCheck(Business b, HttpServletRequest request) {
		int result = ms.nowPwCheck(b, request);
		return result;
	}

	/* 전체예약 페이징 ajax */
	@RequestMapping(value = "/AllPaging", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody String AllPaging(HttpServletRequest request) {
		String text = bs.AllPaging(request);
		return text;
	}

	/* 서비스별 예약 페이징 ajax */
	@RequestMapping(value = "/bctAllPaging", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody String bctAllPaging(HttpServletRequest request) {
		String text = bs.bctAllPaging(request);
		return text;
	}

	/* 전체 예약에서의 검색 페이징 ajax */
	@RequestMapping(value = "/searchAllListPaging", produces = "application/text; charset=utf8")
	public @ResponseBody String searchAllListPaging(HttpServletRequest request) {
		String text = bs.searchAllListPaging(request);
		return text;
	}

	/* 서비스별 예약에서의 검색 페이징 ajax */
	@RequestMapping(value = "/searchBctAllsListPaging", produces = "application/text; charset=utf8")
	public @ResponseBody String searchBctAllsListPaging(HttpServletRequest request) {
		String text = bs.searchBctAllsListPaging(request);
		return text;
	}
}
