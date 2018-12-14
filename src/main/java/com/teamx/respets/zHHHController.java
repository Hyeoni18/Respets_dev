package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.Personal;
import com.teamx.respets.service.HyunHwiService;

@Controller
public class zHHHController {
	@Autowired
	private HyunHwiService hhs;
	ModelAndView mav;
	
	//메인-주소선택 후 기업리스트 페이지 (예약)
	@RequestMapping(value = "/searchList", method = RequestMethod.GET)
	public ModelAndView searchList(HttpServletRequest request, Integer pageNum) {
		System.out.println("search check");
		mav = new ModelAndView();
		mav = hhs.searchList(request, pageNum); //업종코드,날짜,지역 
		System.out.println("end");
		return mav;
	} // method End

	//메인-업종버튼선택 후 기업리스트 페이지 (예약)
	@RequestMapping(value = "/businessList", method=RequestMethod.GET)
	public ModelAndView businessList(HttpServletRequest request, Integer pageNum) {
		mav = new ModelAndView();
		mav = hhs.businessList(request, pageNum); //업종코드 
		return mav;
	}
	
	//주소 선택 후 예약 기업 리스트 페이지에서 태그 선택시 리스트 페이지 (ajax)
	@RequestMapping(value = "/tagSelectList", method=RequestMethod.POST,produces = "application/text; charset=utf8")
	@ResponseBody public String tagSelectList(HttpServletRequest request) {
		String text = hhs.tagSelectList(request);
		return text;
	}
	
	//주소 선택 후 예약 기업 리스트 페이지에서 태그 선택시 페이징 (ajax)
	@RequestMapping(value = "/tagSelectListAddr", method=RequestMethod.POST,produces = "application/text; charset=utf8")
	@ResponseBody public String tagSelectListAddr(HttpServletRequest request) {
		String text = hhs.tagSelectListAddr(request);
		return text;
	}

	//버튼 선택 후 예약 기업 리스트 페이지에서 태그 선택시 페이징 (ajax)
	@RequestMapping(value = "/butTagSelectListPaging", method=RequestMethod.POST,produces = "application/text; charset=utf8")
	@ResponseBody public String butTagSelectListPaging(HttpServletRequest request, Integer pageNum) {
		String text = hhs.butTagSelectListPaging(request, pageNum);
		return text;
	}
	
	//버튼 선택 후 예약 기업 리스트 페이지에서 태그 선택시 리스트 페이지 (ajax)
	@RequestMapping(value = "/butTagSelectList", method = RequestMethod.POST,produces = "application/text; charset=utf8")
	@ResponseBody public String butTagSelectList(HttpServletRequest request, Integer pageNum) {
		String text = hhs.butTagSelectList(request, pageNum);
		return text;
	}
//	
//	@RequestMapping(value = "/test", method = RequestMethod.GET)
//	public ModelAndView test() {
//		mav = new ModelAndView();
//		mav.setViewName("test");
//		return mav;
//	}
//	
	//현휘; loginForm에서 아이디찾기 버튼을 클릭하면 작동하는 url, 아이디 찾기 페이지로 이동 
	@RequestMapping(value = "/findMyIdForm", method = RequestMethod.GET)
	public ModelAndView findMyIdForm() {
		mav = new ModelAndView();
		mav.setViewName("findMyIdForm");
		return mav;
	}

	//현휘; 넘어온 type, per_name, per_phone로 아이디를 찾으러 감
	@RequestMapping (value="/findMyId", method=RequestMethod.POST)
	public ModelAndView findMyId(Personal mb,HttpServletRequest request) {
		mav = new ModelAndView();
		mav=hhs.findMyId(mb,request);
		return mav;
	}
	
	//현휘; 찾은 이메일의 비밀번호를 찾기 위해 변경 폼을 메일로 보내주는 작업 (email, type)
	@RequestMapping (value="/findMyPw", method=RequestMethod.POST)
	public ModelAndView findMyPw(HttpServletRequest request) {
		mav = new ModelAndView();
		mav=hhs.findMyPw(request);
		return mav;
	}
	
	//현휘; 메일에 적힌 url, 신원을 확인 후 비밀번호 변경 폼으로 이동
	@RequestMapping (value="/resetMyPwForm", method=RequestMethod.GET)
	public ModelAndView resetMyPwForm(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = hhs.resetMyPwForm(request);
		return mav;
	}

	//현휘; 변경한 비밀번호 값으로 정보를 변경하러 감
	@RequestMapping (value="/updateMyPw", method=RequestMethod.POST)
	public ModelAndView updateMyPw(HttpServletRequest request, Personal ps) {
		mav = new ModelAndView();
		mav = hhs.updateMyPw(request, ps); 
		return mav;
	}

	//현휘; 기업의 업종등록 신청 페이지로 이동하기 전에 기업이 가진 서비스 검색-업종등록 
	@RequestMapping (value="/serviceInsertForm", method=RequestMethod.GET)
	public ModelAndView serviceInsertForm() {
		mav = new ModelAndView();
		mav = hhs.serviceInsertForm();
		return mav;
	}
	
	//현휘; 추가할 업종들의 정보를 가지고 등록하러 이동 -업종등록
	@RequestMapping (value="/serviceInsert", method=RequestMethod.POST)
	public ModelAndView serviceInsert(MultipartHttpServletRequest request) {
		mav = new ModelAndView();
		mav = hhs.serviceInsert(request); 
		return mav;
	}
	
	//현휘; 기업의 업종의 자세한 정보를 보기위한 메소드 -업종정보
	@RequestMapping(value = "/serviceDetail", method=RequestMethod.GET)
	public ModelAndView serviceDetail(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = hhs.serviceDetail(request);
		//mav.setViewName("serviceDetail");
		return mav;
	}
	
	//현휘; 기업의 상세정보 페이지에서 수정폼으로 이동 -업종정보 
	@RequestMapping(value = "/serviceUpdateForm", method=RequestMethod.POST)
	public ModelAndView serviceUpdateForm(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = hhs.serviceUpdateForm(request);
		return mav;
	}
	
	//현휘; 기업의 상세정보 수정
	@RequestMapping(value = "/serviceUpdate", method=RequestMethod.POST)
	public ModelAndView serviceUpdate(MultipartHttpServletRequest request) {
		mav = new ModelAndView();
		mav = hhs.serviceUpdate(request);
		return mav;
	}
	
	//현휘; 기업의 업종 삭제
	@RequestMapping(value = "/serviceDelete", method=RequestMethod.POST)
	public ModelAndView serviceDelete(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = hhs.serviceDelete(request);
		return mav;
	}
	
	//현휘; 직원 리스트 불러오기 전 업종 선택 버튼 생성
	@RequestMapping(value = "/stepListBut", method=RequestMethod.GET)
	public ModelAndView stepListBut() {
		mav = new ModelAndView();
		mav = hhs.stepListBut();
		return mav;
	}
	
	//현휘; 해당 업종의 직원 리스트 
	@RequestMapping(value = "/stepList", method=RequestMethod.POST,produces = "application/text; charset=utf8")
	@ResponseBody public String stepList(HttpServletRequest request) {
		String text = hhs.stepList(request);
		return text;
	}
	
	//현휘; 직원 추가 페이지, 이동하기 전에 기업이 가진 업종을 검사
	@RequestMapping(value = "/stepInsertFormBut", method=RequestMethod.GET)
	public ModelAndView stepInsertFormBut() {
		mav = new ModelAndView();
		mav = hhs.stepInsertFormBut();
		return mav;
	} 
	
	//현휘; 직원 추가 페이지, 업종에 따라 시간과 휴일이 달라짐 (ajax)
	@RequestMapping(value = "/stepInsertForm", method=RequestMethod.POST,produces = "application/text; charset=utf8")
	@ResponseBody public String stepInsertForm(HttpServletRequest request) {
		String text = hhs.stepInsertForm(request);
		return text;
	}
	
	//현휘; 직원 추가
	@RequestMapping(value = "/stepInsert", method=RequestMethod.POST)
	public ModelAndView stepInsert(MultipartHttpServletRequest request) {
		mav = new ModelAndView();
		mav = hhs.stepInsert(request);
		return mav;
	}

	//현휘; 직원 상세정보 (수정)
	@RequestMapping(value = "/stepDetail", method=RequestMethod.GET)
	public ModelAndView stepDetail(HttpServletRequest request) {
		mav = new ModelAndView();
		mav = hhs.stepDetail(request);
		return mav;
	}
	
	//현휘; 직원 상세정보 수정 (수정)
	@RequestMapping(value = "/stepUpdate", method=RequestMethod.POST)
	public ModelAndView stepUpdate(MultipartHttpServletRequest request) {
		mav = new ModelAndView();
		mav = hhs.stepUpdate(request);
		return mav;
	}
	
	//현휘; 직원 삭제 (수정)
	@RequestMapping(value = "/stepDelete", method=RequestMethod.POST)
	public ModelAndView stepDelete(MultipartHttpServletRequest request) {
		mav = new ModelAndView();
		mav = hhs.stepDelete(request);
		return mav;
	}
//	
//	@RequestMapping(value = "/changeCode", method=RequestMethod.POST)
//	@ResponseBody public String changeCode(HttpServletRequest request) {
//		String text = hhs.changeCode(request);
//		return text;
//	}
//	
//	@RequestMapping(value = "/personalBlackListPage", method=RequestMethod.GET)
//	public ModelAndView personalBlackListPage() {
//		//mav = hhs.personalBlacklist();
//		mav = new ModelAndView();
//		mav.setViewName("personalBlackListPage");
//		return mav;
//	}
//	@RequestMapping(value = "/customerInfoDetail", method=RequestMethod.GET)
//	public ModelAndView customerInfoDetail() {
//		//mav = hhs.personalBlacklist();
//		mav = new ModelAndView();
//		mav.setViewName("customerInfoDetail");
//		return mav; 
//	}
//
//	@RequestMapping(value = "/businessInfo", method=RequestMethod.GET)
//	public ModelAndView businessInfo(HttpServletRequest request) {
//		System.out.println(request.getParameter("bus_no"));
//		System.out.println(request.getParameter("bct_code"));
//		//mav = hhs.businessInfo(request);
//		mav = new ModelAndView();
//		return mav;
//	}

	//업종 수정시 가격검사 
	@RequestMapping(value = "/searchPrice", method=RequestMethod.POST)
	@ResponseBody public String searchPrice(HttpServletRequest request) {
		String text = hhs.searchPrice(request);
		return text;
	}
}
