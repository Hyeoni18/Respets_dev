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
import com.teamx.respets.dao.BusinessDao;
import com.teamx.respets.userClass.Paging;

@Service
public class BusinessService {
	ModelAndView mav;
	@Autowired
	private BusinessDao bDao;
	
	// 기업 공지사항 리스트
		public ModelAndView businessNoticeList(HttpSession session, Integer pageNum) {
			mav = new ModelAndView();
			String view = null;
			String no = session.getAttribute("no").toString();
			System.out.println("no=" + no);
			HashMap<String, Object> hmap = new HashMap<>();
			int pNo = (pageNum == null) ? 1 : pageNum;
			hmap.put("no", no);
			hmap.put("pageNum", pNo);
			List<HashMap<String, Object>> nList = new ArrayList<HashMap<String, Object>>();
			StringBuilder sb = new StringBuilder();
			nList = bDao.businessNotice(hmap);
			System.out.println(nList);
			for (int i = 0; i < nList.size(); i++) {
				sb.append("<tr><td>" + nList.get(i).get("BBO_NO") + "</td>");
				sb.append("<td>" + nList.get(i).get("BCT_NAME") + "</td>");
				sb.append("<td>" + nList.get(i).get("BBC_NAME") + "</td>");
				sb.append("<td><a href='businessNoticeDetail?" + nList.get(i).get("BBO_NO") + "'>"
						+ nList.get(i).get("BBO_TITLE") + "</a></td>");
				sb.append("<td>" + nList.get(i).get("BBO_DATE") + "</td>");
				sb.append("<td class='table-action'><a href='./businessNoticeUpdateForm?" + nList.get(i).get("BBO_NO")
						+ "'class='action-icon'>");
				sb.append("<i class='mdi mdi-pencil'></i></a>");
				sb.append("<a href='./businessNoticeDelete?" + nList.get(i).get("BBO_NO")
						+ "'class='action-icon' onclick='return deleteChk(this);'>");
				sb.append("<i class='mdi mdi-delete'></i></a></td></tr>");
			}
			mav.addObject("nList", sb);
			mav.addObject("paging", getPaging(pNo, session));
			view = "businessNoticeList";
			mav.setViewName(view);
			return mav;
		} // businessNotice end
		
		// 기업 공지사항 리스트 페이징
		private String getPaging(int pageNum, HttpSession session) { // 현재 페이지 번호
			String no = session.getAttribute("no").toString();
			System.out.println("getPaging=" + no);
			int maxNum = bDao.getBusinessNoticeCount(no); // 전체 글의 개수
			int listCount = 10; // 페이지당 글의 수
			int pageCount = 5; // 그룹당 페이지 수 [1] [2] [3] [4] [5] ▶ [6] [7]....
			String boardName = "businessNoticeList"; // 게시판이 여러 개일 때 쓴다.
			Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, boardName);
			return paging.makeHtmlPaging();
		} // method End

		// 기업 공지사항 리스트 카운트
		private String getSearchBusinessNoticeCount(HttpSession session, String select, String search, int pageNum) {
			HashMap<String, Object> hmap = new HashMap<>();
			String no = session.getAttribute("no").toString();
			hmap.put("no", no);
			hmap.put("select", select);
			hmap.put("search", search);
			System.out.println("hmap= " + hmap);
			int maxNum = 0;
			if (select.equals("전체"))
				maxNum = bDao.getSearchBusinessAllNoticeCount(hmap); // 전체 글의 개수
			else
				maxNum = bDao.getSearchBusinessNoticeCount(hmap); // 전체 글의 개수
			int listCount = 10; // 페이지당 글의 수
			int pageCount = 5; // 그룹당 페이지 수 [1] [2] [3] [4] [5] ▶ [6] [7]....
			String boardName = "searchBusinessNotice"; // 게시판이 여러 개일 때 쓴다.
			Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, boardName);
			return paging.makeHtmlSearchPaging(hmap);
		} // getSearchBusinessNoticeCount End
		
		// 기업공지사항상세
		public ModelAndView businessNoticeDetail(HttpServletRequest request) {
			mav = new ModelAndView();
			String view = null;
			String no = request.getQueryString(); // 리스트에서 클릭하여 받아 온 게시글 번호
			String session = null;
			if(request.getSession().getAttribute("no")!=null) {
				session = request.getSession().getAttribute("no").toString(); 
				mav.addObject("session", session);
			} // if end
			
			HashMap<String, Object> hmap = new HashMap<>();
			hmap.put("bbo_no", no);
			System.out.println(hmap);
			hmap = bDao.noticeDetailPage(hmap);
			System.out.println("hmap=" + hmap);
			String bus_no = hmap.get("BUS_NO").toString();
			String bct_name = hmap.get("BCT_NAME").toString();
			String bbc_name = hmap.get("BBC_NAME").toString();
			String bbo_title = hmap.get("BBO_TITLE").toString();
			String bbo_ctt = hmap.get("BBO_CTT").toString();
			String bbo_date = hmap.get("BBO_DATE").toString();
			mav.addObject("bus_no", bus_no);
			mav.addObject("bbo_no", no);
			mav.addObject("bct_name", bct_name);
			mav.addObject("bbc_name", bbc_name);
			mav.addObject("bbo_title", bbo_title);
			mav.addObject("bbo_ctt", bbo_ctt);
			mav.addObject("bbo_date", bbo_date);
			view = "businessNoticeDetail";
			mav.setViewName(view);
			return mav;
		} // noticeDetailPage end

		// 기업공지사항검색
		public ModelAndView searchBusinessNotice(HttpSession session, String select, String search, Integer pageNum) {
			mav = new ModelAndView();
			String view = null;
			String no = session.getAttribute("no").toString();
			HashMap<String, Object> hmap = new HashMap<>();
			int pNo = (pageNum == null) ? 1 : pageNum;
			hmap.put("no", no);
			hmap.put("pageNum", pNo);
			hmap.put("select", select);
			hmap.put("search", search);
			List<HashMap<String, Object>> nList = new ArrayList<HashMap<String, Object>>();
			StringBuilder sb = new StringBuilder();
			
			if (search.equals("전체") && search == "") {
				businessNoticeList(session, pageNum);
			} else if (select.equals("전체")) {
				nList = bDao.searchBusinessAllNotice(hmap);
			} else {
				nList = bDao.searchBusinessNotice(hmap);
				System.out.println("searh nList=" + nList);
			}
			
			if (nList.size() < 1) {
				sb.append("<tr><td colspan='6' style='text-align: center'>검색한 내용이 없습니다</td></tr>");
			}
			for (int i = 0; i < nList.size(); i++) {
				sb.append("<tr><td>" + nList.get(i).get("BBO_NO") + "</td>");
				sb.append("<td>" + nList.get(i).get("BCT_NAME") + "</td>");
				sb.append("<td>" + nList.get(i).get("BBC_NAME") + "</td>");
				sb.append("<td><a href='./noticeDetailPage?" + nList.get(i).get("BBO_NO") + "'>"
						+ nList.get(i).get("BBO_TITLE") + "</a></td>");
				sb.append("<td>" + nList.get(i).get("BBO_DATE") + "</td>");
				sb.append("<td class='table-action'><a href='./businessNoticeUpdateForm?" + nList.get(i).get("BBO_NO")
						+ "'class='action-icon'>");
				sb.append("<i class='mdi mdi-pencil'></i></a>");
				sb.append("<a href='./businessNoticeDelete?" + nList.get(i).get("BBO_NO")
						+ "'class='action-icon' onclick='return deleteChk(this);'>");
				sb.append("<i class='mdi mdi-delete'></i></a></td></tr>");
			}
			mav.addObject("searchNotifications", "<div class='alert alert-primary' role='alert' style='text-align:center'>"
					+ "<strong>'" + search + "'</strong>에 대한 검색 결과입니다</div>");
			mav.addObject("nList", sb);
			mav.addObject("paging", getSearchBusinessNoticeCount(session, select, search, pNo));
			view = "businessNoticeList";
			mav.setViewName(view);
			return mav;
		} // searchBusinessNotice End

		// 기업공지사항등록
		public ModelAndView businessNoticeInsert(HttpSession session, String bct_code, int bbc_no, String bbo_title,
				String bbo_ctt) {
			mav = new ModelAndView();
			String no = session.getAttribute("no").toString();
			System.out.println(no);
			String view = null;
			HashMap<String, Object> hmap = new HashMap<>();
			hmap.put("no", no);
			hmap.put("bct_code", bct_code);
			hmap.put("bbc_no", bbc_no);
			hmap.put("bbo_title", bbo_title);
			hmap.put("bbo_ctt", bbo_ctt);
			System.out.println("check? " + hmap);
			int result = bDao.businessNoticeInsert(hmap);
			System.out.println("what's the result? " + result);
			if (result != 0) {
				view = "redirect:businessNoticeList";
			} else {
				view = "businessNoticeWriteForm";
			}
			mav.setViewName(view);
			return mav;
		} // businessNoticeInsert End

		// 기업공지사항수정폼
		public ModelAndView businessNoticeUpdateForm(HttpServletRequest request) {
			mav = new ModelAndView();
			String view = null;
			String no = request.getQueryString();
			System.out.println(no);
			HashMap<String, Object> hmap = new HashMap<>();
			hmap = bDao.businessNoticeUpdateForm(no);
			if (hmap == null) {
				view = "businessNoticeDetail";
			} else {
				Gson gson = new GsonBuilder().create();
				String json = gson.toJson(hmap);
				mav.addObject("result", json);
				view = "businessNoticeUpdateForm";
			}
			mav.setViewName(view);
			return mav;
		} // businessNoticeUpdateForm end

		// 기업공지사항수정
		public ModelAndView businessNoticeUpdate(HttpServletRequest request) {
			mav = new ModelAndView();
			String view = null;
			int bbo_no = Integer.parseInt(request.getParameter("bbo_no"));
			int bbc_no = Integer.parseInt(request.getParameter("bbc_no"));
			String bct_code = request.getParameter("bct_code");
			String bbo_title = request.getParameter("bbo_title");
			String bbo_ctt = request.getParameter("bbo_ctt");
			HashMap<String, Object> hmap = new HashMap<>();
			hmap.put("bbo_no", bbo_no);
			hmap.put("bbc_no", bbc_no);
			hmap.put("bct_code", bct_code);
			hmap.put("bbo_title", bbo_title);
			hmap.put("bbo_ctt", bbo_ctt);
			int result = bDao.businessNoticeUpdate(hmap);
			if (result != 0) {
				System.out.println("수정성공");
				request.setAttribute("bbo_no", bbo_no);
				view = "redirect:businessNoticeDetail?" + bbo_no;
			} else {
				System.out.println("수정 실패");
				String alert = "alert('수정을 실패했습니다.');";
				mav.addObject("alert", alert);
				view = "redirect:businessNoticeUpdateForm";
			}
			mav.setViewName(view);

			return mav;
		} // businessNoticeUpdate End

		// 기업공지사항 삭제
		public ModelAndView businessNoticeDelete(HttpServletRequest request) {
			mav = new ModelAndView();
			String view = null;
			String alert = null;
			int result = 0;
			if (request.getParameter("bbo_no") != null) {
				Integer bbo_no = Integer.parseInt(request.getParameter("bbo_no"));
				result = bDao.businessNoticeDelete(bbo_no);
				if (result != 0) {
					view = "redirect:businessNoticeList";
				} else {
					view = "redirect:businessNoticeDetail?" + bbo_no;
					mav.addObject("alert", alert);
				}

			} else {
				Integer bbo_no = Integer.parseInt(request.getQueryString());
				result = bDao.businessNoticeDelete(bbo_no);
				if (result != 0) {
					view = "redirect:businessNoticeList";
				} else {
					view = "businessNoticeList";
					alert = "alert(삭제를 실패하였습니다.);";
					mav.addObject("alert", alert);
				}
			}
			mav.setViewName(view);
			return mav;
		} // businessNoticeDelete End

}
