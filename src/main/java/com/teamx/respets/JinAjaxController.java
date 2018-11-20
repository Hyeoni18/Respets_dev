package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.teamx.respets.service.JinService;

@RestController
public class JinAjaxController {

	@Autowired
	private JinService jinSvc;

	// 서진 : 기업 회원 가입 이메일 확인
	@RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
	public int emailCheck(String email) {
		int result = jinSvc.emailCheck(email);
		return result;
	} // method End

	// 서진 : 기업 회원 가입 사업자 등록 번호 확인
	@RequestMapping(value = "/taxIdCheck", method = RequestMethod.POST)
	public int taxIdCheck(String taxId) {
		int result = jinSvc.taxIdCheck(taxId);
		return result;
	} // method End
	
	// 서진 : 예약 페이지 직원 시간 확인
	@RequestMapping(value = "/timeSelect", method = RequestMethod.POST, produces = "application/text;charset=utf8")
	public String timeSelect(HttpServletRequest request) {
		String timeList = jinSvc.timeSelect(request);
		return timeList;
	} // method End

} // class End