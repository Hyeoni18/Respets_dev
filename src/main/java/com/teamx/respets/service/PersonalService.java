package com.teamx.respets.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamx.respets.dao.PersonalDao;
import com.teamx.respets.userClass.Paging;

@Service
public class PersonalService {
	ModelAndView mav;
	@Autowired
	private PersonalDao pDao;
	
	// 개인 최근 예약 목록
	public ModelAndView recentMyBookingList(HttpSession session, Integer pageNum) {
		mav = new ModelAndView();
		String view = null;
		String no = (String) session.getAttribute("no");
		int pNo = (pageNum == null) ? 1 : pageNum;
		HashMap<String, Object> hmap = new HashMap<>();
		List<HashMap<String, Object>> hList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		hmap.put("pageNum", pNo);
		hmap.put("no", no);
		hList = pDao.recentMyBookingList(hmap);
		for (int i = 0; i < hList.size(); i++) {
			sb.append("<tr><td><a href='./myBookingDetail?" + hList.get(i).get("BK_NO") + "'>"
					+ hList.get(i).get("BK_NO") + "</a></td>");
			sb.append("<td>" + hList.get(i).get("BUS_NAME") + "</td>");
			sb.append("<td>" + hList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + hList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + hList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + hList.get(i).get("BK_TIME") + "</td>");
			sb.append("<td>" + hList.get(i).get("VS_START") + "</td>");
			if (hList.get(i).get("BK_CHK").equals("승인")) {
				sb.append("<td class='text-success'>" + hList.get(i).get("BK_CHK") + "</td></tr>");
			} else if (hList.get(i).get("BK_CHK").equals("거절")) {
				sb.append("<td class='text-danger'>" + hList.get(i).get("BK_CHK") + "</td></tr>");
			} else if (hList.get(i).get("BK_CHK").equals("취소")) {
				sb.append("<td class='text-warning'>" + hList.get(i).get("BK_CHK") + "</td></tr>");
			} else {
				sb.append("<td class='text-info'>" + hList.get(i).get("BK_CHK") + "</td></tr>");
			}
		} // for End
		mav.addObject("hList", sb);
		mav.addObject("paging", getPagingRecent(pNo, session));
		view = "recentMyBookingList";
		mav.setViewName(view);
		return mav;
	}// recentMyBookingList End
	
	//개인 최근예약목록 페이징
	private String getPagingRecent(int pageNum, HttpSession session) { // 현재 페이지 번호
		String no = session.getAttribute("no").toString();
		int maxNum = pDao.recentMyBookingListCount(no); // 전체 글의 개수
		int listCount = 10; // 페이지당 글의 수
		int pageCount = 5; // 그룹당 페이지 수 [1] [2] [3] [4] [5] ▶ [6] [7]....
		String boardName = "recentMyBookingList"; // 게시판이 여러 개일 때 쓴다.
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, boardName);
		return paging.makeHtmlPaging();
	} // method End
	
	// 개인예약상세정보
	public ModelAndView myBookingDetail(HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		String bk_no = request.getQueryString();
		HashMap<String, Object> hmap = new HashMap<>();
		hmap = pDao.myBookingDetail(bk_no);
		String pet_no = hmap.get("PET_NO").toString();

		List<HashMap<String, Object>> mList = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> tList = new ArrayList<HashMap<String, Object>>();
		mList = pDao.getMenu(bk_no);
		tList = pDao.getPetDetail(pet_no);
		StringBuilder sb = new StringBuilder();

		// 메뉴
		for (int i = 0; i < mList.size(); i++) {
			if (mList.size() - i == 1) {
				sb.append("<span>" + mList.get(i).get("MENU_NAME") + "</span>");
			} else {
				sb.append("<span>" + mList.get(i).get("MENU_NAME") + ", </span>");
			}
			mav.addObject("mList", sb);
		} // for End

		// 반려동물상세
		for (int i = 0; i < tList.size(); i++) {
			System.out.println(tList.get(i).get("PCL_NAME") + " " + tList.get(i).get("PDT_CTT"));
			sb.append("<tr><td>" + tList.get(i).get("PCL_NAME") + "</td><td>" + tList.get(i).get("PDT_CTT")
					+ "</td></tr>");
			mav.addObject("tList", sb);
		} // for End

		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(hmap);
		mav.addObject("result", json);
		view = "myBookingDetail";
		mav.setViewName(view);
		return mav;
	} // myBookingDetail End

}
