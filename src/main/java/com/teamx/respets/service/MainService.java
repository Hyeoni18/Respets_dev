package com.teamx.respets.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.dao.MainDao;
import com.teamx.respets.userClass.Paging;

@Service
public class MainService {
	ModelAndView mav;
	@Autowired
	private MainDao mDao;
	
	// 기업상세페이지 하단 업체 기본 정보
	public ModelAndView businessBasicInfo(HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		String bus_no = request.getParameter("bus_no"); // 쿼리 스트링으로 받아 온 기업회원 번호
		String bct_code = request.getParameter("bct_code"); // 쿼리 스트링으로 받아 온 업종 코드
		HashMap<String, Object> hmap = new HashMap<>();
		HashMap<String, Object> tMap = new HashMap<>(); // 태그 셀렉트
		List<HashMap<String, Object>> tList = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		hmap.put("bus_no", bus_no);
		hmap.put("bct_code", bct_code);
		hmap = mDao.businessBasiceInfo(hmap);

		// 기본정보
		String bus_name = hmap.get("BUS_NAME").toString();
		String bct_name = hmap.get("BCT_NAME").toString();
		String bus_post = hmap.get("BUS_POST").toString();
		String bus_addr = hmap.get("BUS_ADDR").toString();
		String bus_addr2 = hmap.get("BUS_ADDR2").toString();
		// 스케줄
		String mon = hmap.get("BFX_MON").toString();
		String tue = hmap.get("BFX_TUE").toString();
		String wed = hmap.get("BFX_WED").toString();
		String thu = hmap.get("BFX_THU").toString();
		String fri = hmap.get("BFX_FRI").toString();
		String sat = hmap.get("BFX_SAT").toString();
		String sun = hmap.get("BFX_SUN").toString();
		String hld = hmap.get("BFX_HLD").toString();
		String lch = hmap.get("BFX_LCH").toString();

		mav.addObject("bus_name", bus_name);
		mav.addObject("bct_name", bct_name);
		mav.addObject("bus_post", bus_post);
		mav.addObject("bus_addr", bus_addr);
		mav.addObject("bus_addr2", bus_addr2);
		mav.addObject("mon", mon);
		mav.addObject("tue", tue);
		mav.addObject("wed", wed);
		mav.addObject("thu", thu);
		mav.addObject("fri", fri);
		mav.addObject("sat", sat);
		mav.addObject("sun", sun);
		mav.addObject("hld", hld);
		mav.addObject("lch", lch);

		// 태그 샐렉트
		if (hmap != null) {
			tMap.put("bus_no", bus_no);
			tMap.put("bct_code", bct_code);
			tList = mDao.selectTag(tMap);
			for (int i = 0; i < tList.size(); i++) {
				if (tList.size() - i == 1) {
					sb.append("<span>" + tList.get(i).get("TAG_NAME") + "</span>");
				} else {
					sb.append("<span>" + tList.get(i).get("TAG_NAME") + ", </span>");
				}
			} // for end
			mav.addObject("tList", sb);
		} // hmap if End
		mav.setViewName(view);
		view = "businessBasicInfo";
		return mav;
	}// businessBasicInfo End

	// 기업 상세 페이지 갤러리
	public ModelAndView businessGallery(HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		String bus_no = request.getParameter("bus_no");
		String bct_code = request.getParameter("bct_code");
		HashMap<String, Object> hmap = new HashMap<>();
		List<HashMap<String, Object>> gList = new ArrayList<HashMap<String, Object>>();
		hmap.put("bus_no", bus_no);
		hmap.put("bct_code", bct_code);
		gList = mDao.businessGallery(hmap);
		StringBuilder sb = new StringBuilder();
		if (gList.size() > 0) {
			for (int i = 0; i < gList.size(); i++) {
				sb.append("<img style='height:100%; weight:100%;' " + "class='card-img-top' src='"
						+ gList.get(i).get("GLR_LOC") + gList.get(i).get("GLR_FILE") + "' />");
			}
		} else {
			sb.append("<p> 상세사진이 없습니다. </p>");
		}
		mav.addObject("gList", sb);
		view = "businessGallery";
		mav.setViewName(view);
		return mav;
	}// businessGallery
	
	// 기업상세페이지 기업 공지
	public ModelAndView businessDetailNoticeList(HttpServletRequest request, Integer pageNum) {
		mav = new ModelAndView();
		String view = null;
		String bus_no = request.getParameter("bus_no");
		String bct_code = request.getParameter("bct_code");
		HashMap<String, Object> hmap = new HashMap<>();
		int pNo = (pageNum == null) ? 1 : pageNum;
		hmap.put("bus_no", bus_no);
		hmap.put("bct_code", bct_code);
		hmap.put("pageNum", pNo);
		List<HashMap<String, Object>> nList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		nList = mDao.businessDetailNoticeList(hmap);
		for (int i = 0; i < nList.size(); i++) {
			sb.append("<tr><td>" + nList.get(i).get("BBO_NO") + "</td>");
			sb.append("<td>" + nList.get(i).get("BBC_NAME") + "</td>");
			/*sb.append("<td><a href='./businessNoticeDetail?" + nList.get(i).get("BBO_NO") + "'>"
					+ nList.get(i).get("BBO_TITLE") + "</a></td>");*/
			sb.append("<td><a href='#' data-toggle='modal' data-target='#B" + nList.get(i).get("BBO_NO") + "'>"
					+ nList.get(i).get("BBO_TITLE") + "</a></td>");
			sb.append("<div id=\"B"+nList.get(i).get("BBO_NO")+"\" class=\"modal fade\" tabindex=\"-1\"\r\n" + 
					"															role=\"dialog\" aria-labelledby=\"myModalLabel\"\r\n" + 
					"															aria-hidden=\"true\">\r\n" + 
					"															<div class=\"modal-dialog modal-dialog-centered\">\r\n" + 
					"																<div class=\"modal-content\">\r\n" + 
					"																	<div class=\"modal-header\">\r\n" + 
					"																		\r\n" + 
					"																		<h4 class=\"modal-title\" id=\"myModalLabel\">"+nList.get(i).get("BBO_TITLE")+"</h4>\r\n" + 
					"																		<div class=\"badge badge-secondary\" style=\"margin-top:5px;margin-left:10px\">\r\n");
			
			if(nList.get(i).get("BBC_NAME").equals("공지사항")) sb.append("공지사항");
			if(nList.get(i).get("BBC_NAME").equals("이벤트")) sb.append("이벤트");
			
			sb.append("																			</div>\r\n" + 
					"																		<button type=\"button\" class=\"close\"\r\n" + 
					"																			data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\r\n" + 
					"																	</div>\r\n" + 
					"																	<div class=\"modal-body\">\r\n" + 
					"																		<p>"+nList.get(i).get("BBO_CTT")+"</p>\r\n" + 
					"																	</div>\r\n" + 
					"																</div>\r\n" + 
					"															</div>\r\n" +  
					"														</div>");
			sb.append("<td>" + nList.get(i).get("BBO_DATE") + "</td></tr>");
		}
		mav.addObject("nList", sb);
		mav.addObject("paging", getDetailPaging(pNo, request));
		view = "businessDetailNoticeList";
		mav.setViewName(view);
		return mav;
	} // businessDetailNoticeList end

	// 기업상세페이지 공지사항 페이징
	private String getDetailPaging(int pageNum, HttpServletRequest request) { // 현재 페이지 번호
		HashMap<String, Object> hmap = new HashMap<>();
		String bus_no = request.getParameter("bus_no");
		String bct_code = request.getParameter("bct_code");
		hmap.put("bus_no", bus_no);
		hmap.put("bct_code", bct_code);
		int maxNum = mDao.getBusinessNoticeDetailCount(hmap); // 전체 글의 개수
		int listCount = 10; // 페이지당 글의 수
		int pageCount = 5; // 그룹당 페이지 수 [1] [2] [3] [4] [5] ▶ [6] [7]....
		String boardName = "businessDetailNoticeList"; // 게시판이 여러 개일 때 쓴다.
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, boardName);
		return paging.makeHtmlPaging();
	} // getDetailPaging End


}
