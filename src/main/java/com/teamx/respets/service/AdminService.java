package com.teamx.respets.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.dao.AdminDao;

@Component
public class AdminService {
	ModelAndView mav;
	@Autowired
	private AdminDao aDao;
	
	
	//관리자 로그인
	public ModelAndView adminLogin(String adm_no, String adm_pw, HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		HashMap<String, Object> hmap = new HashMap<>();
		hmap.put("adm_no", adm_no);
		hmap.put("adm_pw", adm_pw);
		hmap = aDao.adminLogin(hmap);
		if (hmap != null) {
			String no = (String) hmap.get("ADM_NO");
			request.getSession().setAttribute("no", no);
			view = "redirect:/unconfirmBusiness";
		} else {
			view = "adminLoginForm";
		}
		mav.setViewName(view);
		return mav;
	} // adminLogin End
	
	// 미인증 기업 목록
	public ModelAndView unconfirmBusiness(HttpSession session) {
		mav = new ModelAndView();
		String view = null;
		StringBuilder sb = new StringBuilder();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<>();
		bList = aDao.getBusinessList();
		for (int i = 0; i < bList.size(); i++) {
			sb.append("<tr><td><a href='./chkLicense?bus_no=" + bList.get(i).get("BUS_NO") + "&bct_code="
					+ bList.get(i).get("BCT_CODE") + "'>" + bList.get(i).get("BUS_NO") + "</a></td>");
			sb.append("<td>" + bList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("BUS_EMAIL") + "</td>");
			sb.append("<td>" + bList.get(i).get("BUS_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("BUS_TIME") + "</td>");

			mav.addObject("bList", sb);
		}
		view = "unconfirmBusiness";
		mav.setViewName(view);
		return mav;
	} // unconfirmBusiness end

	// 미인증기업정보 확인
	public ModelAndView chkLicense(HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		String bus_no = request.getParameter("bus_no");
		String bct_code = request.getParameter("bct_code");
		HashMap<String, Object> bMap = new HashMap<>();
		bMap.put("bus_no", bus_no);
		bMap.put("bct_code", bct_code);
		if (bus_no != null && bct_code != null) {
			bMap = aDao.getBusinessInfo(bMap);
			System.out.println(bMap);
			String bct_name = bMap.get("BCT_NAME").toString();
			String bus_name = bMap.get("BUS_NAME").toString();
			String bus_ceo = bMap.get("BUS_CEO").toString();
			String bus_phone = bMap.get("BUS_PHONE").toString();
			String bus_email = bMap.get("BUS_EMAIL").toString();
			String bus_addr = bMap.get("BUS_ADDR").toString();
			String bus_addr2 = bMap.get("BUS_ADDR2").toString();
			String bus_lcno = bMap.get("BUS_LCNO").toString();
			String glr_loc = bMap.get("GLR_LOC").toString();
			String glr_file = bMap.get("GLR_FILE").toString();

			mav.addObject("bus_no", bus_no);
			mav.addObject("bct_name", bct_name);
			mav.addObject("bus_name", bus_name);
			mav.addObject("bus_phone", bus_phone);
			mav.addObject("bus_email", bus_email);
			mav.addObject("glr_loc", glr_loc);
			mav.addObject("glr_file", glr_file);
			mav.addObject("bus_ceo", bus_ceo);
			mav.addObject("bus_addr", bus_addr);
			mav.addObject("bus_addr2", bus_addr2);
			mav.addObject("bus_lcno", bus_lcno);
		}
		view = "chkLicense";
		mav.setViewName(view);
		return mav;
	} // chkLicense end

	// 미인증 기업 인증
	public ModelAndView confirmLicense(HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		String bus_no = request.getQueryString();
		if (bus_no != null) {
			int result = aDao.confirmLicense(bus_no);
			String alert = "alert('승인이 완료되었습니다.')";
			mav.addObject("alert", alert);
			view = "redirect:unconfirmBusiness";
		} else {
			view = "chkLicense";
		}
		mav.setViewName(view);
		return mav;
	} // confirmLicense end

}
