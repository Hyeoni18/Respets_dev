package com.teamx.respets.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Gallery;
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
		if (request.getSession().getAttribute("no") != null) {
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

	/* 기업 서비스 버튼 불러오기 */
	public ModelAndView todayScheduleList(HttpSession session) {
		mav = new ModelAndView();
		String view = null;
		StringBuilder sb = new StringBuilder();
		String no = session.getAttribute("no").toString();
		ArrayList<HashMap<String, Object>> sMap = bDao.getSvcPri(no);
		if (sMap.size() != 0) {
			for (int i = 0; i < sMap.size(); i++) {
				String svc = (String) sMap.get(i).get("BCT_NAME");
				String code = (String) sMap.get(i).get("BCT_CODE");
				sb.append("&emsp;&emsp; <input type='radio' name='radio' onchange='test();' class='" + code
						+ "' value='" + svc + "'>" + svc);
			}
			mav.addObject("bctList", sb);
			view = "todayScheduleList";
		} else {
			sb.append("<h1>목록이 없습니다.</h1>");
			mav.addObject("none", sb);
			view = "todayScheduleList";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 예약 상세피이지 */
	public ModelAndView myBookingDetail(HttpServletRequest request) {
		mav = new ModelAndView();
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		String view = null;
		String bk_no = request.getQueryString();
		String chk = bDao.getBk_chk(bk_no);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map = bDao.myBookingDetail(bk_no); // 예약 리스트로 가져오기
		ArrayList<HashMap<String, Object>> menu = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();
		String pet_no = map.get("PET_NO").toString();
		menu = bDao.getMenu(bk_no); // 서비스 메뉴 불러오기
		pList = bDao.getPetList(pet_no); // 펫 디테일 리스트로 가져오기
		if (map != null) {
			for (int i = 0; i < menu.size(); i++) {
				if (menu.size() - i == 1) {
					sb2.append("<span>" + menu.get(i).get("MENU_NAME") + "</span>");
				} else {
					sb2.append("<span>" + menu.get(i).get("MENU_NAME") + ", </span>");
				}
				mav.addObject("mList", sb2);
			}
			for (int i = 0; i < pList.size(); i++) {
				if (pList.size() - i == 1) {
					sb.append("<tr><td>" + pList.get(i).get("PCL_NAME") + "</td><td>" + pList.get(i).get("PDT_CTT")
							+ "</td></tr>");
				}
				mav.addObject("pList", sb);
			}
		}
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(map);
		mav.addObject("result", json);
		mav.addObject("bk_no", bk_no);
		mav.addObject("chk", chk);
		view = "myBookingDetail";
		mav.setViewName(view);
		return mav;
	}

	/* 오늘 예약 방문 확인 ajax */
	public String todayScheduleListCheck(HttpServletRequest request) {
		String bk_no = request.getParameter("bk_no");
		bDao.todayScheduleListCheck(bk_no);
		String changeList = todayAllScheduleList(request);
		return changeList;
	}

	/* 오늘 예약 방문취소 ajax */
	public String todayScheduleListCancel(HttpServletRequest request) {
		String bk_no = request.getParameter("bk_no");
		bDao.todayScheduleListCancel(bk_no);
		String changeList = todayAllScheduleList(request);
		return changeList;
	}

	/* 서비스별 예약 리스트 ajax */
	public String bctBookingListCheck(HttpServletRequest request) {
		String bk_no = request.getParameter("bk_no");
		bDao.todayScheduleListCheck(bk_no);
		String changeList = bctBookingList(request);
		return changeList;
	}

	/* 서비스별 예약 방문 취소 ajax */
	public String bctBookingListCancel(HttpServletRequest request) {
		String bk_no = request.getParameter("bk_no");
		bDao.todayScheduleListCancel(bk_no);
		String changeList = bctBookingList(request);
		return changeList;
	}

	/* 방문완료 예약리스트 ajax */
	public String vs_chkOkList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("bus_no", request.getParameter("bus_no"));
		map.put("bk_no", request.getParameter("bk_no"));
		map.put("timeS", timeS);
		ArrayList<HashMap<String, Object>> okList = bDao.vs_chkOkList(map);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < okList.size(); i++) {
			String bk_no = (String) okList.get(i).get("BK_NO");
			sb.append("<div name='list' id='" + bk_no + "'><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a> | "
					+ okList.get(i).get("PTY_NAME") + " | " + okList.get(i).get("PET_NAME") + " | "
					+ okList.get(i).get("PER_NAME") + " | " + okList.get(i).get("BCT_NAME") + " | "
					+ okList.get(i).get("BK_TIME") + " | " + okList.get(i).get("VS_START") + "<br/> 방문완료 </div><br>");

		}
		return sb.toString();
	}

	/* 업종 종류 불러오기(전체예약페이지) */
	public ModelAndView businessBookingList(HttpSession session) {
		mav = new ModelAndView();
		StringBuilder sb = new StringBuilder();
		String no = session.getAttribute("no").toString();
		ArrayList<HashMap<String, Object>> sMap = bDao.getSvcPri(no);
		if (sMap.size() != 0) {
			for (int i = 0; i < sMap.size(); i++) {
				String svc = (String) sMap.get(i).get("BCT_NAME");
				sb.append("<option>" + svc + "</option>");
			}
			mav.addObject("bctList", sb);
		}
		mav.setViewName("businessBookingListPage");
		return mav;
	}

	/* 서비스 종류 불러오기(서비스페이지) */
	public ModelAndView serviceManagement(HttpSession session) {
		mav = new ModelAndView();
		StringBuilder sb = new StringBuilder();
		String view = null;
		String no = session.getAttribute("no").toString();
		ArrayList<HashMap<String, Object>> servList = new ArrayList<HashMap<String, Object>>();
		servList = bDao.serviceManagement(no);
		if (servList != null) {
			for (int i = 0; i < servList.size(); i++) {
				String bct_code = (String) servList.get(i).get("BCT_CODE");
				String code = null;
				if (bct_code.equals("M")) {
					code = "mdi mdi-hospital";
				} else if (bct_code.equals("B")) {
					code = "mdi mdi-content-cut";
				} else if (bct_code.equals("H")) {
					code = "mdi mdi-hotel";
				}
				sb.append("<div class='col-md-4'><div class='card card-pricing'><div class='card-body text-center'>");
				sb.append("<a href='serviceDetail?bct_code=" + bct_code + "'><i class='card-pricing-icon " + code
						+ "'></i></a><br/><br/>" + "<h2 class='card-pricing-price'>" + servList.get(i).get("BCT_NAME")
						+ "</h2>");
				sb.append("</div></div></div>");
			}
			mav.addObject("servList", sb);
		} else {
			sb.append("서비스를 등록해주세요");
			mav.addObject("add", sb);
		}
		view = "servicePage";
		mav.setViewName(view);
		return mav;
	}

	/* 기업 정보 페이지 */
	public ModelAndView businessInfoDetail(HttpSession session) {
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		HashMap<String, Object> bmap = new HashMap<>();
		bmap = bDao.businessInfo(no);
		String view = null;
		if (bmap != null) {
			String glr_file = (String) bmap.get("GLR_FILE");
			String glr_loc = (String) bmap.get("GLR_LOC");
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(bmap);
			mav.addObject("result", json);
			mav.addObject("bmap", bmap);
			mav.addObject("glr_loc", glr_loc);
			mav.addObject("glr_file", glr_file);
			view = "businessInfoDetail";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 기업정보 수정 */
	public ModelAndView businessInfoUpdate(MultipartHttpServletRequest request) {
		mav = new ModelAndView();
		HashMap<String, Object> bmap = new HashMap<String, Object>();
		Business b = new Business();
		String no = request.getSession().getAttribute("no").toString();
		Gallery gy = new Gallery();
		if (request.getParameter("fileCheck").equals("1")) {
			b.setBus_no(no);
			b.setBus_phone(request.getParameter("bus_phone"));
			b.setBus_post(request.getParameter("bus_post"));
			b.setBus_addr(request.getParameter("bus_addr"));
			b.setBus_addr2(request.getParameter("bus_addr2"));
			bDao.businessInfoUpdate(b);
			MultipartFile photo = request.getFile("mainPhoto");
			Map<String, Object> hMap = new HashMap<String, Object>();
			hMap = bussaveFile(request, photo, hMap);
			gy.setBus_no(request.getSession().getAttribute("no").toString());
			gy.setGlr_loc(hMap.get("glr_loc").toString());
			gy.setGlr_file(hMap.get("glr_file").toString());
			bDao.mainPhotoUpdate(gy);
		}
		bmap = bDao.businessInfo(no);
		String glr_file = (String) bmap.get("GLR_FILE");
		String glr_loc = (String) bmap.get("GLR_LOC");
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<img id='perProfile' style='width: 150px; height: 150px; margin-top: 15px; margin-left: 20px;' class='rounded-circle img-thumbnail' src='"
						+ glr_loc + glr_file + "'/>");
		mav.addObject("bmap", bmap);
		mav.addObject("img", sb);
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(bmap);
		mav.addObject("result", json);
		request.getSession().setAttribute("loc", glr_loc);
		request.getSession().setAttribute("photo", glr_file);
		mav.setViewName("redirect:/businessInfoDetail");
		return mav;
	}

	private Map<String, Object> bussaveFile(MultipartHttpServletRequest request, MultipartFile photo,
			Map<String, Object> hMap) {
		String root = request.getSession().getServletContext().getRealPath("/");
		String location = "resources/upload/";
		String path = root + location;
		File dir = new File(path);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		String date = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
		String extension = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
		String saveName = "Respets_" + date + "_" + UUID.randomUUID() + "." + extension;
		try {
			photo.transferTo(new File(path, saveName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		hMap.put("glr_loc", location);
		hMap.put("glr_file", saveName);
		return hMap;
	}

	/* 기업 회원탈퇴 */
	public ModelAndView businessPartDelete(HttpSession session) {
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		boolean result = bDao.businessPartDelete(no);
		if (result) {
			session.invalidate();
		}
		mav.setViewName("redirect:/");
		return mav;
	}

	/* 서비스별 전체예약 리스트 ajax */
	public String bctBookingList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		String no = request.getParameter("no");
		String bct_name = request.getParameter("bct_name");
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("no", no);
		map.put("timeS", timeS);
		map.put("bct_name", bct_name);
		bList = bDao.bctBookingList(map);
		sb.append(
				"<table class='table table-centered mb-0' style='text-align:center;'><thead><tr><th width='13%'>예약 번호</th><th width='13%'>동물 종류</th><th width='13%'>동물 이름</th><th width='13%'>예약자명</th><th width='13%'>서비스 종류</th><th>방문 일시</th><th width='13%'>방문 상태</th></tr></thead>");
		for (int i = 0; i < bList.size(); i++) {
			String bk_no = (String) bList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td>");
			sb.append("<td>" + bList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("VS_START") + "</td>");
			sb.append("<td><input type='button' class='btn btn-outline-success' value='방문' onclick=\"com(\'" + bk_no
					+ "')\" /></td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/* 서비스별 방문확인 된 전체예약 리스트 ajax */
	public String bctBookingListOk(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> okList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		String no = request.getParameter("no");
		String bct_name = request.getParameter("bct_name");
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("no", no);
		map.put("timeS", timeS);
		map.put("bct_name", bct_name);
		okList = bDao.bctBookingListOk(map);
		sb.append(
				"<table class='table table-centered mb-0' style='text-align:center;'><thead><tr><th width='13%'>예약 번호</th><th width='13%'>동물 종류</th><th width='13%'>동물 이름</th><th width='13%'>예약자명</th><th width='13%'>서비스 종류</th><th>방문 일시</th><th width='13%'>방문 상태</th></tr></thead>");
		for (int i = 0; i < okList.size(); i++) {
			String bk_no = (String) okList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td>");
			sb.append("<td>" + okList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("VS_START") + "</td>");
			sb.append("<td><button type='button' class='btn btn-outline-danger' onclick='cancelCheck(\"" + bk_no
					+ "\")'> 취소 </button></td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/* 오늘 예약 일정 전체 ajax */
	public String todayAllScheduleList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		String no = request.getParameter("no");
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("no", no);
		map.put("timeS", timeS);
		bList = bDao.todayScheduleList(map);
		sb.append(
				"<table class='table table-centered mb-0' style='text-align:center;'><thead><tr><th width='13%'>예약 번호</th><th width='13%'>동물 종류</th><th width='13%'>동물 이름</th><th width='13%'>예약자명</th><th width='13%'>서비스 종류</th><th>방문 일시</th><th width='13%'>방문 상태</th></tr></thead>");
		for (int i = 0; i < bList.size(); i++) {
			String bk_no = (String) bList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td>");
			sb.append("<td>" + bList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("VS_START") + "</td>");
			sb.append("<td><input type='button' class='btn btn-outline-success' value='방문' onclick=\"com(\'" + bk_no
					+ "')\" /></td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/* 오늘 방문 확인된 예약 리스트 ajax */
	public String todayAllScheduleListOk(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> okList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		String no = request.getParameter("no");
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("no", no);
		map.put("timeS", timeS);
		okList = bDao.todayScheduleListOk(map);
		sb.append(
				"<table class='table table-centered mb-0' style='text-align:center;'><thead><tr><th width='13%'>예약 번호</th><th width='13%'>동물 종류</th><th width='13%'>동물 이름</th><th width='13%'>예약자명</th><th width='13%'>서비스 종류</th><th>방문 일시</th><th width='13%'>방문 상태</th></tr></thead>");
		for (int i = 0; i < okList.size(); i++) {
			String bk_no = (String) okList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td>");
			sb.append("<td>" + okList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("VS_START") + "</td>");
			sb.append("<td><button type='button' class='btn btn-outline-danger' onclick='cancelCheck(\"" + bk_no
					+ "\")'> 취소 </button></td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/* 기업 전체예약 리스트 ajax */
	public String businessAllBookingList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String no = (String) request.getSession().getAttribute("no");
		System.out.println("session" + no);
		map.put("no", no);
		map.put("pageNum", pNo);
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		bList = bDao.businessBookingList(map);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bList.size(); i++) {
			String bk_no = (String) bList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td><td>"
					+ bList.get(i).get("PTY_NAME") + "</td><td>" + bList.get(i).get("PET_NAME") + "</td><td>"
					+ bList.get(i).get("PER_NAME") + "</td><td>" + bList.get(i).get("BCT_NAME") + "</td><td>"
					+ bList.get(i).get("VS_START") + "</td></tr>");
		}
		return sb.toString();
	}

	/* 전체 예약에서의 검색 ajax */
	public String searchAllList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bPerList = new ArrayList<HashMap<String, Object>>();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String no = request.getSession().getAttribute("no").toString();
		String search = request.getParameter("search");
		StringBuilder sb = new StringBuilder();
		map.put("no", no);
		map.put("per_name", search);
		map.put("pageNum", pNo);
		bPerList = bDao.searchAllList(map);
		if (bPerList.size() != 0) {
			for (int i = 0; i < bPerList.size(); i++) {
				String bk_no = (String) bPerList.get(i).get("BK_NO");
				sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td><td>"
						+ bPerList.get(i).get("PTY_NAME") + "</td><td>" + bPerList.get(i).get("PET_NAME") + "</td><td>"
						+ bPerList.get(i).get("PER_NAME") + "</td><td>" + bPerList.get(i).get("BCT_NAME") + "</td><td>"
						+ bPerList.get(i).get("VS_START") + "</td></tr>");
			}
		}
		return sb.toString();
	}

	/* 전체 예약에서의 검색 페이징 ajax */
	public String searchAllListPaging(HttpServletRequest request) {
		String no = request.getSession().getAttribute("no").toString();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String search = request.getParameter("search");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("per_name", search);
		int maxNum = bDao.searchAllListPaging(map);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "businessBookingList"; // 사용안함
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.searchAllList(search);
	}

	/* 서비스별 전체 예약 리스트 ajax */
	public String businessAllBctBookingList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String no = request.getSession().getAttribute("no").toString();
		String bct_name = request.getParameter("bct_name");
		map.put("no", no);
		map.put("bct_name", bct_name);
		map.put("pageNum", pNo);
		bList = bDao.AllbctBookingList(map);
		if (bList != null) {
			for (int i = 0; i < bList.size(); i++) {
				String bk_no = (String) bList.get(i).get("BK_NO");
				sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td><td>"
						+ bList.get(i).get("PTY_NAME") + "</td><td>" + bList.get(i).get("PET_NAME") + "</td><td>"
						+ bList.get(i).get("PER_NAME") + "</td><td>" + bList.get(i).get("BCT_NAME") + "</td><td>"
						+ bList.get(i).get("VS_START") + "</td></tr>");

			}
		}
		return sb.toString();
	}

	/* 서비스별 예약에서의 검색 ajax */
	public String searchBctAllsList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String search = request.getParameter("search");
		String bct_name = request.getParameter("bct_name");
		StringBuilder sb = new StringBuilder();
		String no = request.getSession().getAttribute("no").toString();
		map.put("no", no);
		map.put("bct_name", bct_name);
		map.put("per_name", search);
		map.put("pageNum", pNo);
		bList = bDao.searchBctAllsList(map);
		if (bList != null) {
			for (int i = 0; i < bList.size(); i++) {
				String bk_no = (String) bList.get(i).get("BK_NO");
				sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td><td>"
						+ bList.get(i).get("PTY_NAME") + "</td><td>" + bList.get(i).get("PET_NAME") + "</td><td>"
						+ bList.get(i).get("PER_NAME") + "</td><td>" + bList.get(i).get("BCT_NAME") + "</td><td>"
						+ bList.get(i).get("VS_START") + "</td></tr>");
			}
		}
		return sb.toString();
	}

	/* 서비스별 예약에서의 검색 페이징 ajax */
	public String searchBctAllsListPaging(HttpServletRequest request) {
		String no = request.getSession().getAttribute("no").toString();
		String bct_name = request.getParameter("bct_name");
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String search = request.getParameter("search");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("bct_name", bct_name);
		map.put("per_name", search);
		int maxNum = bDao.searchBctAllsListPaging(map);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "businessBookingList"; // 사용안함
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.searchBctAllsList(bct_name, search);
	}

	/* 전체예약 페이징 ajax */
	public String AllPaging(HttpServletRequest request) {
		String no = request.getSession().getAttribute("no").toString();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		int maxNum = bDao.getListCount(no);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "businessBookingList"; // 사용안함
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.AllPaging();
	}

	/* 서비스별 예약 페이징 ajax */
	public String bctAllPaging(HttpServletRequest request) {
		String no = request.getSession().getAttribute("no").toString();
		String bct_name = request.getParameter("bct_name");
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("bct_name", bct_name);
		int maxNum = bDao.bctAllPaging(map);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "businessBookingList"; // 사용안함
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.bctAllPaging(bct_name);
	}

	// 기업의 업종등록 신청 전, 가지고 있는 업종이 있는지 검색
	public ModelAndView serviceInsertForm(HttpSession session) {
		mav = new ModelAndView();
		String bus_no = (String) session.getAttribute("no");
		List<Map<String, Object>> list;
		list = bDao.serviceInsertForm(bus_no); // 기업이 가진 서비스 검색
		String codeSelectBut = codeSelectBut(); // 업종 선택 버튼 생성

		// 각 업종들에게 필요한 메뉴를 검색하여 생성해준다.
		String medical = menuSelect("M");
		String beauty = menuSelect("B");
		String hotel = menuSelect("H");

		String code = null; // 업종 코드를 담을 변수
		String codeCheck = null; // 가지고 있는 라디오 버튼을 지운 문장 담을 변수

		if (list.size() != 0) { // 업종을 하나라도 가지고 있을 경우,
			for (int i = 0; i < list.size(); i++) {
				code = (String) list.get(i).get("BCT_CODE");
				codeCheck = codeCheck(code); // 기업이 가지고 있는 업종 선택 버튼을 지워준다.
				codeSelectBut += codeCheck; // 만들어진 StringBuilder를 모두 합쳐준다.

			}
		}
		// 기업의 이름과 전화번호는 모든 업종이 동일하기에 입력된 값을 가져와서 담아준다.
		Map<String, Object> map = new HashMap<String, Object>();
		map = bDao.searchBUS(bus_no); // 기업 정보 검색
		String bus_name = (String) map.get("BUS_NAME");
		String bus_phone = (String) map.get("BUS_PHONE");

		// String time = timeSelect(); // 기업의 운영시간 선택 select tag
		// String lunch = lunchSelect(); // 기업의 점심시간 선택 select tag
		// String holiday = holidaySelect(); // 기업의 휴일 선택 select tag

		mav.addObject("cnt", list.size()); // 서비스의 우선순위를 정해주기 위한 카운트
		mav.addObject("bct_code", code);
		mav.addObject("bus_name", bus_name);
		mav.addObject("bus_phone", bus_phone);
		mav.addObject("codeCheck", codeSelectBut);
		// mav.addObject("time", time);
		// mav.addObject("lunch", lunch);
		// mav.addObject("holiday", holiday);
		mav.addObject("medical", medical);
		mav.addObject("beauty", beauty);
		mav.addObject("hotel", hotel);
		mav.setViewName("serviceInsertForm");
		return mav;
	}

	// 업종 등록 할 때 필요한 업종 선택 버튼을 만들어 준다.
	private String codeSelectBut() {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<li class='nav-item' id='medical_div'><a href='javascript:void(0)' onclick='checkCode(\"M\")' data-toggle='tab' aria-expanded='false' class='nav-link rounded-0' > 병원 </a></li>");
		sb.append(
				"<li class='nav-item' id='beauty_div'><a href='javascript:void(0)' onclick='checkCode(\"B\")' data-toggle='tab' aria-expanded='false' class='nav-link rounded-0' > 미용 </a></li>");
		sb.append(
				"<li class='nav-item' id='hotel_div'><a href='javascript:void(0)' onclick='checkCode(\"H\")' data-toggle='tab' aria-expanded='false' class='nav-link rounded-0' > 호텔 </a></li>");
		sb.append("<br/>");
		return sb.toString();
	}

	// 해당 업종에 필요한 메뉴를 체크박스로 만들어 준다.
	private String menuSelect(String code) {
		StringBuilder sb = new StringBuilder();
		List<Map<String, Object>> list;
		list = bDao.selectMENU(code); // 업종이 가지고 있는 메뉴를 검색
		for (int i = 0; i < list.size(); i++) { // 검색된 메뉴들을 체크박스로 생성
			sb.append("<div class='custom-control custom-checkbox'>");
			String menu_name = (String) list.get(i).get("MENU_NAME");
			if (menu_name.equals("고양이무마취")) {
				sb.append("<input type='checkbox' name='cat_tag' class='custom-control-input' value='" + menu_name
						+ "' id='" + menu_name + "_" + code + "'/> <label class='custom-control-label' for='"
						+ menu_name + "_" + code + "'>" + menu_name + " </label>&nbsp;");
			} else if (menu_name.equals("가위컷")) {
				sb.append("<input type='checkbox' name='dog_tag' class='custom-control-input' value='" + menu_name
						+ "' id='" + menu_name + "_" + code + "'/> <label class='custom-control-label' for='"
						+ menu_name + "_" + code + "'>" + menu_name + " </label>&nbsp;");
			} else {
				sb.append("<input type='checkbox' name='tag_name' class='custom-control-input' value='" + menu_name
						+ "' id='" + menu_name + "_" + code + "'/> <label class='custom-control-label' for='"
						+ menu_name + "_" + code + "'>" + menu_name + " </label>&nbsp;");
			}
			sb.append("</div>");
		}
		return sb.toString();
	}

	// 기업이 가지고 있는 업종이면 선택 버튼 삭제
	private String codeCheck(String code) {
		StringBuilder sb = new StringBuilder();
		if (code.equals("M")) {
			sb.append("<script> $(medical_div).hide(); </script>");
		} else if (code.equals("B")) {
			sb.append("<script> $(beauty_div).hide(); </script>");
		} else if (code.equals("H")) {
			sb.append("<script> $(hotel_div).hide(); </script>");
		}
		return sb.toString();
	}

	// 가져온 업종 정보들을 등록하는 메소드
	@Transactional
	public ModelAndView serviceInsert(MultipartHttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		fileWriter(request, session); // 사진 등록 (사업장사진)
		weekSet(request, session); // 스케줄 등록 (영업시간,점심시간,고정휴무일)
		checkTag(request, session); // 제공서비스 등록
		insertPrice(request, session); // 기업이 지정한 서비스 가격 등록
		addBusinessCode(request, session); // 업종 등록
		mav.setView(new RedirectView("/serviceManagement"));
		// mav.setViewName("/servicePage");
		return mav;
	}

	// 기업이 등록한 사진을 등록
	private void fileWriter(MultipartHttpServletRequest multi, HttpSession session) {
		String no = (String) session.getAttribute("no");
		System.out.println("upload Start");
		// 1.이클립스의 물리적 저장경로 찾기
		String root = multi.getSession().getServletContext().getRealPath("/");
		System.out.println("root=" + root);
		String location = "resources/upload/";
		String path = root + location;/* resource는 webapp/resources에 저장 */
		System.out.println("path=" + path);
		// 2.폴더 생성을 꼭 할것(clean했을 때 폴더 지워짐 방지)
		File dir = new File(path);
		if (!dir.isDirectory()) { // upload폴더 없다면
			dir.mkdir(); // upload폴더 생성
		}
		// 3.파일을 가져오기-일태그 이름들 반환
		List<MultipartFile> name = multi.getFiles("bgl_ori");
		System.out.println(name);
		for (int i = 0; i < name.size(); i++) {
			String fileTagName = name.get(i).getName();
			System.out.println("fileTagNext=" + fileTagName);
			// 파일 메모리에 저장
			MultipartFile mf = name.get(i); // 실제파일
			String oriFileName = mf.getOriginalFilename(); // a.txt
			System.out.println("oriFileName=" + oriFileName);
			// 4.시스템파일이름 생성 a.txt-->112323242424.txt
			String sysFileName = System.currentTimeMillis() + "."
					+ oriFileName.substring(oriFileName.lastIndexOf(".") + 1);
			/* 인덱스(점)을 기준으로 앞에 있는 문자열을 현재 시간을 기준으로한 밀리세컨드로 대체한다 */
			System.out.println("sysFileName=" + sysFileName);
			// 5.메모리->실제 파일 업로드
			try {
				/* 실제로 파일을 업로드하는 메소드 */
				mf.transferTo(new File(path + sysFileName));
			} catch (IOException e) {
				e.printStackTrace();
			}

			String bct_code = multi.getParameter("bct_code");
			System.out.println(bct_code);
			System.out.println(sysFileName);
			System.out.println(location);
			System.out.println(no);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bus_no", no);
			map.put("bct_code", bct_code);
			map.put("number", "3");
			map.put("file", sysFileName);
			map.put("path", location);
			bDao.insertFile(map);
		}
	}

	// 기업이 등록한 업무시간, 점심시간, 휴일을 등록
	private void weekSet(MultipartHttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		map = holidayMap(request, session); // 휴일을 map에 담아오는 메소드
		bDao.serviceInsertBFX(map); // 기업의 고정스케줄 등록
		String bct_code = request.getParameter("bct_code"); // 업종 코드
		String no = (String) session.getAttribute("no"); // 기업 번호
		String[] holiday = request.getParameterValues("holiday"); // 휴일은 여러개일 수도 있으니까 배열로 받는다.
		String work_o = request.getParameter("work_o");
		String work_c = request.getParameter("work_c");
		String lunch_o = request.getParameter("lunch_o");
		String lunch_c = request.getParameter("lunch_c");
		String am_open = work_o.split(":")[0] + work_o.split(":")[1];
		String pm_close = work_c.split(":")[0] + work_c.split(":")[1];
		String lunch_open = lunch_o.split(":")[0] + lunch_o.split(":")[1];
		String lunch_close = lunch_c.split(":")[0] + lunch_c.split(":")[1];
		int[] num = new int[holiday.length]; // BSD에 영업시간을 담을 때, 휴일로 지정한 날을 제외시키기 위해 요일을 1~7로 담아준다.
		for (int i = 0; i < holiday.length; i++) {
			if (holiday[i].equals("월요일")) {
				num[i] = 2;
			} else if (holiday[i].equals("화요일")) {
				num[i] = 3;
			} else if (holiday[i].equals("수요일")) {
				num[i] = 4;
			} else if (holiday[i].equals("목요일")) {
				num[i] = 5;
			} else if (holiday[i].equals("금요일")) {
				num[i] = 6;
			} else if (holiday[i].equals("토요일")) {
				num[i] = 7;
			} else if (holiday[i].equals("일요일")) {
				num[i] = 1;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 날짜형식 정해주기
		Calendar cal = Calendar.getInstance(); // 현재 시간을 가져온다.
		boolean flag = true; // 휴일이 아니면 입력하기 위한 깃발
		String date = null; // 스케줄 날짜를 담을 변수
		int dayOfWeek = 0; // 요일을 담을 변수
		for (int i = 0; i < 30; i++) { // 30일까지의 스케줄만 제공하기에 범위는 30
			date = sdf.format(cal.getTime()); // 현재시간을 내가 지정한 포맷으로 변경한다.
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 해달 날짜의 요일을 가져온다.
			for (int j = 0; j < num.length; j++) { // 위에서 담아온 휴일만큼 반복
				if (dayOfWeek == num[j]) { // 지정된 휴일인지 확인
					flag = false; // 맞으면 깃밧을 false로 바꿔준다.
				}
			}
			System.out.println(date);
			if (flag) { // 휴일이 아니라면,
				map.put("bus_no", no);
				map.put("bct_code", bct_code);
				map.put("date", date);
				map.put("am_open", am_open);
				map.put("am_close", lunch_open);
				map.put("pm_open", lunch_close);
				map.put("pm_close", pm_close);
				bDao.serviceInsertBSD(map); // 기업의 한달스케줄을 등록한다.
			}
			cal.add(Calendar.DATE, 1); // 날짜를 하루 추가해준다.
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 해당 날짜의 요일을 가져온다.
			flag = true; // 깃발을 다시 true로 바꿔준다.
		}
	}

	// 기업의 선택 전 휴일에 관련된 작업을 위한 메소드
	public Map<String, Object> holidayMap(MultipartHttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String code = request.getParameter("bct_code"); // 업종 코드
		String no = (String) session.getAttribute("no"); // 기업 번호

		map.put("bus_no", no);
		map.put("bct_code", code);

		String work_o = request.getParameter("work_o");
		String work_c = request.getParameter("work_c");
		String lunch_o = request.getParameter("lunch_o");
		String lunch_c = request.getParameter("lunch_c");

		String am_open = work_o.split(":")[0] + work_o.split(":")[1];
		String pm_close = work_c.split(":")[0] + work_c.split(":")[1];
		String lunch_open = lunch_o.split(":")[0] + lunch_o.split(":")[1];
		String lunch_close = lunch_c.split(":")[0] + lunch_c.split(":")[1];

		String time = am_open + pm_close; // BFX는 8자리니까 둘을 합쳐준다.
		String lunch = lunch_open + lunch_close;

		String[] holiday = request.getParameterValues("holiday"); // 휴일은 여러개일 수도 있으니까 배열로 받는다.
		map.put("bfx_mon", time);
		map.put("bfx_tue", time);
		map.put("bfx_wed", time);
		map.put("bfx_thu", time);
		map.put("bfx_fri", time);
		map.put("bfx_sat", time);
		map.put("bfx_sun", time);
		map.put("bfx_hld", time);
		int[] num = new int[holiday.length]; // BSD에 영업시간을 담을 때, 휴일로 지정한 날을 제외시키기 위한 변수
		// num에 요일을 1~7로 담아준다.
		for (int i = 0; i < holiday.length; i++) {
			if (holiday[i].equals("월요일")) {
				map.put("bfx_mon", "XXXXXXXX");
				num[i] = 2;
			} else if (holiday[i].equals("화요일")) {
				map.put("bfx_tue", "XXXXXXXX");
				num[i] = 3;
			} else if (holiday[i].equals("수요일")) {
				map.put("bfx_wed", "XXXXXXXX");
				num[i] = 4;
			} else if (holiday[i].equals("목요일")) {
				map.put("bfx_thu", "XXXXXXXX");
				num[i] = 5;
			} else if (holiday[i].equals("금요일")) {
				map.put("bfx_fri", "XXXXXXXX");
				num[i] = 6;
			} else if (holiday[i].equals("토요일")) {
				map.put("bfx_sat", "XXXXXXXX");
				num[i] = 7;
			} else if (holiday[i].equals("일요일")) {
				map.put("bfx_sun", "XXXXXXXX");
				num[i] = 1;
			} else if (holiday[i].equals("공휴일")) {
				map.put("bfx_hld", "XXXXXXXX");
			}
		}
		map.put("bfx_lch", lunch);
		return map;
	}

	// 기업이 제공하는 서비스 등록
	private void checkTag(MultipartHttpServletRequest request, HttpSession session) {
		String code = request.getParameter("bct_code");
		String no = (String) session.getAttribute("no");
		Map<String, Object> map;
		String[] tag = request.getParameterValues("tag_name"); // 체크된 태크네임을 가져온다.
		String cat_tag = request.getParameter("cat_tag");
		String dog_tag = request.getParameter("dog_tag");
		String tag_num = null;
		if (cat_tag != null) {
			map = new HashMap<>();
			String cat_num = bDao.searchTAG(cat_tag);
			if (cat_num == null) {
				bDao.insertTAG(cat_tag);
				cat_num = bDao.searchTAG(cat_tag);
			}
			map.put("bus_no", no);
			map.put("bct_code", code);
			map.put("tag_num", cat_num);
			bDao.insertBTG(map);
		}
		if (dog_tag != null) {
			map = new HashMap<>();
			String dog_num = bDao.searchTAG(dog_tag);
			if (dog_num == null) {
				bDao.insertTAG(dog_tag);
				dog_num = bDao.searchTAG(dog_tag);
			}
			map.put("bus_no", no);
			map.put("bct_code", code);
			map.put("tag_num", dog_num);
			bDao.insertBTG(map);
		}
		if (tag != null) {
			map = new HashMap<>();
			for (int i = 0; i < tag.length; i++) { // 가져온 태그만큼 반복
				tag_num = bDao.searchTAG(tag[i]); // 태그테이블에 존재하는지 검사, 존재하면 tag_num을 가져온다.
				if (tag_num == null) { // 없다면
					bDao.insertTAG(tag[i]); // 태그테이블에 해당 서비스 등록
					tag_num = bDao.searchTAG(tag[i]); // 등록한 서비스를 다시 검색하여 tag_num을 가져온다.
				}
				map.put("bus_no", no);
				map.put("bct_code", code);
				map.put("tag_num", tag_num);
				bDao.insertBTG(map); // 기업이 제공하는 서비스들을 해시태그 테이블에 등록
			}
		}
	}

	// 제공하는 서비스의 가격 등록
	private void insertPrice(MultipartHttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String no = (String) session.getAttribute("no"); // 기업번호
		String code = request.getParameter("bct_code"); // 업종코드
		String[] price = request.getParameterValues("price"); // 가격
		String[] tag = request.getParameterValues("tag_name"); // 서비스종류
		String dog_tag = request.getParameter("dog_tag"); // 강아지 태그(가위컷)
		String cat_tag = request.getParameter("cat_tag"); // 고양이 태그(무마취)
		String[] sort = request.getParameterValues("animal_code"); // 동물종류
		String cat_sort = request.getParameter("cat_code"); // 고양이종류
		String pet[];
		int cnt = 0; // 가격의 순서를 가늠하기 위한 카운트 변수
		// if (code.equals("B")) { //업종코드가 미용일 경우
		// 강아지에게만 제공되는 가위컷 서비스가 있을 경우
		if (dog_tag != null && sort != null) {
			pet = new String[1]; // 강아지 태그를 넣어줄 공간을 하나 만들어준다.
			if (tag != null) { // 서비스종류가 널값이 아닐경우
				pet = new String[tag.length + 1]; // 강아지태그+서비스태그를 넣어줄 공간을 함께 만들어준다.
				for (int i = 0; i < tag.length; i++) { // 서비스 태그들을 넣어준다.
					pet[i + 1] = tag[i]; // +1을 한 이유는 강아지 태그를 제일 먼저 넣어주기 위해서이다.
				}
			}
			pet[0] = dog_tag; //
			for (int i = 0; i < sort.length; i++) { // 동물 종류만큼 반복
				String sort_o = bDao.selectAnimalCode(sort[i]); // 해당 동물의 코드를 검색
				for (int j = 0; j < pet.length; j++) { // 위에서 저장한 태그 수만큼 반복
					map.put("bct_code", code); // 업종코드와
					map.put("tag", pet[j]); // 서비스종류를 맵에 담아서
					String tag_o = bDao.selectMenuNo(map); // 해당 서비스의 번호를 검색
					map.put("bus_no", no); // 기업번호
					map.put("bct_code", code); // 업종코드
					map.put("tag", tag_o); // 태그번호
					map.put("pet", sort_o); // 동물번호
					if (code.equals("M")) {
						map.put("price", "0");
						bDao.insertPrice(map); // 가격등록
					} else {
						map.put("price", price[cnt]);
						if (price[cnt].equals("X")) {
							System.out.println("제공되지 않는 서비스");
						} else {
							bDao.insertPrice(map); // 가격등록
						}
					}
					cnt++;
				}
			}
			// 강아지에게만 제공되는 가위컷 서비스가 없을 경우
		} else if (sort != null && tag != null) {
			for (int i = 0; i < sort.length; i++) { // 동물 종류 수만큼 반복
				String sort_o = bDao.selectAnimalCode(sort[i]); // 동물코드 검색
				for (int j = 0; j < tag.length; j++) { // 태그 수만큼 반복
					map.put("bct_code", code);
					map.put("tag", tag[j]);
					String tag_o = bDao.selectMenuNo(map); // 해당 서비스 번호 검색
					map.put("bus_no", no);
					map.put("bct_code", code);
					map.put("tag", tag_o);
					map.put("pet", sort_o);
					if (code.equals("M")) {
						map.put("price", "0");
						bDao.insertPrice(map); // 가격등록
					} else {
						map.put("price", price[cnt]);
						if (price[cnt].equals("X")) {
							System.out.println("제공되지 않는 서비스");
						} else {
							bDao.insertPrice(map); // 가격등록
						}
					}
					cnt++;
				}
			}
		}
		// 고양이에게만 제공되는 무마취 서비스가 있을 경우
		if (cat_tag != null && cat_sort != null) {
			pet = new String[1]; // 고양이 태그를 넣어준 공간을 하나 만들어준다.
			if (tag != null) { // 다른 서비스 태그도 존재할 경우
				pet = new String[tag.length + 1]; // 고양이태그+서비스태그 공간을 만들어준다.
				for (int i = 0; i < tag.length; i++) { // 태그수 만큼 반복
					pet[i + 1] = tag[i]; // 서비스들을 배열에 담아준다.
				}
			}
			pet[0] = cat_tag; // 첫번째는 무마취를 넣어준다.
			String sort_o = bDao.selectAnimalCode(cat_sort); // 무마취 번호 검색
			for (int j = 0; j < pet.length; j++) { // 가지고 있는 서비스 수만큼 반복
				map.put("bct_code", code);
				map.put("tag", pet[j]);
				String tag_o = bDao.selectMenuNo(map); // 서비스 번호 검색
				map.put("bus_no", no);
				map.put("bct_code", code);
				map.put("tag", tag_o);
				map.put("pet", sort_o);
				if (code.equals("M")) {
					map.put("price", "0");
					bDao.insertPrice(map); // 가격등록
				} else {
					map.put("price", price[cnt]);
					if (price[cnt].equals("X")) {
						System.out.println("제공되지 않는 서비스");
					} else {
						bDao.insertPrice(map); // 가격등록
					}
				}
				cnt++;
			}
			// 고양이에게만 제공되는 무마취 서비스가 없을 경우
		} else if (cat_sort != null && tag != null) {
			String sort_o = bDao.selectAnimalCode(cat_sort); // 동물번호 검색
			for (int j = 0; j < tag.length; j++) { // 서비스 수만큼 반복
				map.put("bct_code", code);
				map.put("tag", tag[j]);
				String tag_o = bDao.selectMenuNo(map); // 서비스 번호 검색
				map.put("bus_no", no);
				map.put("bct_code", code);
				map.put("tag", tag_o);
				map.put("pet", sort_o);
				if (code.equals("M")) {
					map.put("price", "0");
					bDao.insertPrice(map); // 가격등록
				} else {
					map.put("price", price[cnt]);
					if (price[cnt].equals("X")) {
						System.out.println("제공되지 않는 서비스");
					} else {
						bDao.insertPrice(map); // 가격등록
					}
				}
				cnt++;
			}
		}
	} // else if (code.equals("H")) { //업종 종류가 호텔일 경우

	// 최종적으로 기업이 제공하는 업종을 등록
	private void addBusinessCode(MultipartHttpServletRequest request, HttpSession session) {
		String bus_no = (String) session.getAttribute("no");
		String code = request.getParameter("bct_code");
		int cnt = Integer.parseInt(bDao.countSVC(bus_no));
		Map<String, Object> map = new HashMap<>();
		map.put("bus_no", bus_no);
		map.put("bct_code", code);
		map.put("cnt", cnt + 1);
		bDao.addBusinessCode(map); // 업종 등록
	}

	// 기업의 상세정보 페이지를 열기 위한 메소드
	public ModelAndView serviceDetail(HttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		String bus_no = (String) session.getAttribute("no");
		Map<String, Object> map = new HashMap<String, Object>();
		map = bDao.searchBUS(bus_no);
		String bct_code = request.getParameter("bct_code"); // 병원,미용,호텔
		System.out.println(bct_code);
		String bus_name = (String) map.get("BUS_NAME");
		String bus_phone = (String) map.get("BUS_PHONE");
		String bct_name = bDao.searchBCTname(bct_code);
		System.out.println(bct_name);
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);

		map = bDao.searchBSD(map); // 업종 하루 스케줄 검색
		String workTime = null;
		String holiday = null;
		String menu = null;
		String animal = null;
		String firstInsert = null;
		if (map != null) {
			String am_open = (String) map.get("AM_OPEN"); // 오픈시간
			String pm_close = (String) map.get("PM_CLOSE"); // 마감시간

			String am_pre = am_open.substring(0, 2); // 오픈시간 hour
			String am_las = am_open.substring(2, 4); // 오픈시간 min
			String pm_pre = pm_close.substring(0, 2); // 마감시간 hour
			String pm_las = pm_close.substring(2, 4); // 마갑시간 min

			workTime = am_pre + ":" + am_las + " ~ " + pm_pre + ":" + pm_las; // 00:00~00:00로 표현

			map.put("bus_no", bus_no);
			map.put("bct_code", bct_code);
			map = bDao.searchBFX(map); // 업종 고정 스케줄 검색

			holiday = ""; // 휴일을 담을 변수
			Set<String> mapkey = map.keySet(); // 검색된 키 값을 가져온다. (컬럼)
			Iterator<String> iter = mapkey.iterator(); // 처음부터 뽑아주기 위해 iterator로 선언한다.
			while (iter.hasNext()) { // 하나씩 값을 가져온다.
				String key = iter.next(); // 가져온 값을 변수에 담아준다.
				String value = (String) map.get(key); // 가져온 키에 적혀있던 값을 변수에 담아준다.
				if (value.equals("XXXXXXXX")) { // 해당 값이 XXXXXXXX 일 때,
					if (holiday != "") { // 휴일을 처음 담는 경우가 아닐 때는
						holiday += "/"; // 경계선을 그어준다.
					}
					holiday += key; // 변수에 컬럼명을 담아준다. (ex.월요일)
				}
			}
			map.put("bus_no", bus_no);
			map.put("bct_code", bct_code);
			List<Map<String, Object>> list;
			list = bDao.selectMenuTag(map); // 제공하는 서비스가 있는지 검색해서 해당 서비스의 번호를 가져온다.
			menu = ""; // 메뉴를 담을 변수
			String menu_no = null;
			for (int i = 0; i < list.size(); i++) {
				menu_no = String.valueOf(list.get(i).get("MENU_NO")); // 해당 메뉴의 번호를 가져와서
				if (menu != "") { // 처음 가져오는 메뉴가 아닐 경우
					menu += "/"; // 경계선을 그어준다.
				}
				menu += bDao.selectMenuName(menu_no); // 메뉴의 이름을 검색하여 변수에 담아준다.
			}

			map.put("bus_no", bus_no);
			map.put("bct_code", bct_code);
			List<Map<String, Object>> alist;
			alist = bDao.selectAnimalTag(map); // 기업이 제공하는 동물종류를 검색하여 번호를 가져온다.
			animal = ""; // 동물종류를 담을 변수
			String animal_no = null;
			for (int i = 0; i < alist.size(); i++) {
				animal_no = String.valueOf(alist.get(i).get("PTY_NO")); // 동물번호를 가져와서
				if (animal != "") { // 처음 가져오는 동물이 아닐 경우
					animal += "/"; // 경계선을 그어준다.
				}
				animal += bDao.selectAnimalName(animal_no); // 동물 이름을 검색하여 변수에 담아준다.
			}
		} else {
			workTime = " 등록이 필요합니다.";
			holiday = " 등록이 필요합니다.";
			menu = " 등록이 필요합니다.";
			animal = " 등록이 필요합니다.";
			firstInsert = "first";
		}
		System.out.println(bct_code);
		System.out.println(bct_name);
		mav.addObject("bct_name", bct_name);
		mav.addObject("bct_code", bct_code);
		mav.addObject("name", bus_name);
		mav.addObject("phone", bus_phone);
		mav.addObject("work", workTime);
		mav.addObject("holiday", holiday);
		mav.addObject("menu", menu);
		mav.addObject("animal", animal);
		mav.addObject("first", firstInsert);
		mav.setViewName("serviceDetail");
		return mav;
	}

	// 기업의 상세정보 수정 페이지를 열기 위한 메소드
	public ModelAndView serviceUpdateForm(HttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		String bus_no = (String) session.getAttribute("no");
		String bct_code = request.getParameter("bct_code");
		System.out.println(bct_code);
		String top = serviceTopUpdateForm(bus_no); // 이름, 연락처를 보여주는 메소드
		String first = request.getParameter("first");
		String work = null;
		String lunch = null;
		String holiday = null;
		String menu = null;
		String animal = null;
		String work_o = null;
		String work_c = null;
		String lunch_o = null;
		String lunch_c = null;
		if (first.equals("first")) {
			holiday = holidaySelect(); // 기업의 휴일 선택 select tag
			menu = menuSelect(bct_code);
			animal = animalSelect(bct_code);
			mav.addObject("first", first);

		} else {
			work = serviceWorkUpdateForm(bus_no, bct_code);
			work_o = work.split(",")[0];
			work_c = work.split(",")[1];
			lunch = serviceLunchUpdateForm(bus_no, bct_code);
			lunch_o = lunch.split(",")[0];
			lunch_c = lunch.split(",")[1];
			holiday = serviceHolidayUpdateForm(bus_no, bct_code);
			menu = serviceMenuUpdateForm(bus_no, bct_code);
		}
		String bct_name = null;
		// 가격등록 창을 가져오기 위한 if문
		if (bct_code.equals("H")) {
			mav.addObject("price",
					"<button type='button' class='btn btn-outline-secondary' name='H' onclick='priceBox(this)'> 가격등록</button>&nbsp");
			mav.addObject("bct_price", "<div class='form-group mb-2'><div class='row'><div id='H'></div></div></div>");
			mav.addObject("cat_price",
					"<div class='form-group mb-2'><div class='row'><div id='H_price'></div></div></div>");
			bct_name = "호텔";
		} else if (bct_code.equals("B")) {
			mav.addObject("price",
					"<button type='button' class='btn btn-outline-secondary' name='B' onclick='priceBox(this)'> 가격등록</button>&nbsp");
			mav.addObject("bct_price", "<div class='form-group mb-2'><div class='row'><div id='B'></div></div></div>");
			mav.addObject("cat_price",
					"<div class='form-group mb-2'><div class='row'><div id='B_price'></div></div></div>");
			bct_name = "미용";
		} else if (bct_code.equals("M")) {
			mav.addObject("medi_submit", "<button class='btn btn-outline-secondary'>수정하기</button>");
			bct_name = "병원";
		}
		mav.addObject("bct_name", bct_name);
		mav.addObject("bct_code", bct_code); // 업종, 병원,미용,호텔
		mav.addObject("top", top);
		mav.addObject("work_o", work_o);
		mav.addObject("work_c", work_c);
		mav.addObject("lunch_o", lunch_o);
		mav.addObject("lunch_c", lunch_c);
		mav.addObject("holiday", holiday);
		mav.addObject("menu", menu);
		mav.addObject("animal", animal);
		mav.setViewName("serviceUpdateForm");
		return mav;
	}

	// 기업의 수정페이지에서 이름, 연락처를 보여주는 메소드
	private String serviceTopUpdateForm(String bus_no) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = bDao.searchBUS(bus_no); // 기업번호로 검색해서 기존 정보 불러오기
		String name = (String) map.get("BUS_NAME");
		String phone = (String) map.get("BUS_PHONE");
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='form-group mb-3'>");
		sb.append("<label for='simpleinput'>업체명</label>");
		sb.append("<input type='text' name='bus_name' value='" + name + "' class='form-control' readonly/><br/>");
		sb.append("</div>");
		sb.append("<div class='form-group mb-3'>");
		sb.append("<label for='simpleinput'>연락처</label>");
		sb.append("<input type='text' name='bus_phone' value='" + phone + "' class='form-control' readonly/><br/>");
		sb.append("</div>");
		return sb.toString();
	}

	// 기업의 휴일을 선택할 수 있는 select tag 생성
	private String holidaySelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='custom-control custom-checkbox'>");
		sb.append(
				"<input type=\"checkbox\" class='custom-control-input' name=\"holiday\" value=\"월요일\" id='월요일'/> <label class='custom-control-label' for='월요일'>월요일</label>&nbsp;");
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'>");
		sb.append(
				"<input type=\"checkbox\" class='custom-control-input' name=\"holiday\" value=\"화요일\" id='화요일'/> <label class='custom-control-label' for='화요일'>화요일</label>&nbsp;");
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'>");
		sb.append(
				"<input type=\"checkbox\" class='custom-control-input' name=\"holiday\" value=\"수요일\" id='수요일'/> <label class='custom-control-label' for='수요일'>수요일</label>&nbsp;");
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'>");
		sb.append(
				"<input type=\"checkbox\" class='custom-control-input' name=\"holiday\" value=\"목요일\" id='목요일'/> <label class='custom-control-label' for='목요일'>목요일</label>&nbsp;");
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'>");
		sb.append(
				"<input type=\"checkbox\" class='custom-control-input' name=\"holiday\" value=\"금요일\" id='금요일'/> <label class='custom-control-label' for='금요일'>금요일</label>&nbsp;");
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'>");
		sb.append(
				"<input type=\"checkbox\" class='custom-control-input' name=\"holiday\" value=\"토요일\" id='토요일'/> <label class='custom-control-label' for='토요일'>토요일</label>&nbsp;");
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'>");
		sb.append(
				"<input type=\"checkbox\" class='custom-control-input' name=\"holiday\" value=\"일요일\" id='일요일'/> <label class='custom-control-label' for='일요일'>일요일</label>&nbsp;");
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'>");
		sb.append(
				"<input type=\"checkbox\" class='custom-control-input' name=\"holiday\" value=\"공휴일\" id='공휴일'/> <label class='custom-control-label' for='공휴일'>공휴일</label>&nbsp;");
		sb.append("</div>");
		return sb.toString();
	}

	// 해당 업종에 필요한 동물종를 체크박스로 만들어 준다.
	private String animalSelect(String code) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='form-group mb-3'>");
		sb.append("<label for='simpleinput'>서비스 가능한 동물종류</label><br/>");
		if (code.equals("M")) { // 병원에서 제공될 수 있는 동물 종류
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='animal_code' value='소형견' id='소형견'/> <label class='custom-control-label' for='소형견'>소형견</label>&nbsp;");
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='animal_code' value='중형견' id='중형견'/> <label class='custom-control-label' for='중형견'> 중형견</label>&nbsp;");
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='animal_code' value='대형견' id='대형견'/> <label class='custom-control-label' for='대형견'> 대형견</label>&nbsp;");
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='cat_code' value='고양이' id='고양이' /> <label class='custom-control-label' for='고양이'> 고양이</label>&nbsp;");
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='animal_code' value='소동물' id='소동물'/> <label class='custom-control-label' for='소동물'> 소동물</label>&nbsp;");
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='animal_code' value='파충류' id='파충류'/> <label class='custom-control-label' for='파충류'> 파충류</label>&nbsp;");
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='animal_code' value='가축' id='가축'/> <label class='custom-control-label' for='가축'> 가축</label>&nbsp;");
			sb.append("</div>");
		} else if (code.equals("B")) { // 미용에서 제공될 수 있는 동물 종류
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='animal_code' value='소형견' id='소형견_b'/> <label class='custom-control-label' for='소형견_b'> 소형견</label>&nbsp;");
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='animal_code' value='중형견' id='중형견_b'/> <label class='custom-control-label' for='중형견_b'> 중형견</label>&nbsp;");
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='animal_code' value='대형견' id='대형견_b'/> <label class='custom-control-label' for='대형견_b'> 대형견</label>&nbsp;");
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='cat_code' value='고양이' id='고양이_b'/> <label class='custom-control-label' for='고양이_b'> 고양이</label>&nbsp;");
			sb.append("</div>");
		} else if (code.equals("H")) { // 호텔에서 제공될 수 있는 동물 종류
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='animal_code' value='소형견' id='소형견_h'/> <label class='custom-control-label' for='소형견_h'> 소형견</label>&nbsp;");
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='animal_code' value='중형견' id='중형견_h'/> <label class='custom-control-label' for='중형견_h'> 중형견</label>&nbsp;");
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='animal_code' value='대형견' id='대형견_h'/> <label class='custom-control-label' for='대형견_h'> 대형견</label>&nbsp;");
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='cat_code' value='고양이' id='고양이_h'/> <label class='custom-control-label' for='고양이_h'> 고양이</label>&nbsp;");
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='animal_code' value='소동물' id='소동물_h'/> <label class='custom-control-label' for='소동물_h'> 소동물</label>&nbsp;");
			sb.append("</div>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	// 기업의 수정페이지에서 영업시간을 보여주는 메소드
	private String serviceWorkUpdateForm(String bus_no, String bct_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		map = bDao.searchBSD(map); // 기업의 하루 스케줄 검색
		String am_open = (String) map.get("AM_OPEN"); // 오픈시간
		String pm_close = (String) map.get("PM_CLOSE"); // 마감시간

		String am_pre = am_open.substring(0, 2); // 오픈시간 hour
		String am_las = am_open.substring(2, 4); // 오픈시간 min
		String pm_pre = pm_close.substring(0, 2); // 마감시간 hour
		String pm_las = pm_close.substring(2, 4); // 마감시간 min

		am_open = am_pre + ":" + am_las;
		pm_close = pm_pre + ":" + pm_las;

		return am_open + "," + pm_close;
	}

	// 기업의 수정페이지에서 점심시간을 보여주는 메소드
	private String serviceLunchUpdateForm(String bus_no, String bct_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		map = bDao.searchBSD(map);
		String am_close = (String) map.get("AM_CLOSE");
		String pm_open = (String) map.get("PM_OPEN");

		String lunch_o_pre = am_close.substring(0, 2);
		String lunch_o_las = am_close.substring(2, 4);
		String lunch_c_pre = pm_open.substring(0, 2);
		String lunch_c_las = pm_open.substring(2, 4);

		String lunch = lunch_o_pre + ":" + lunch_o_las + "," + lunch_c_pre + ":" + lunch_c_las;

		return lunch;
	}

	// 기업의 수정페이지에서 휴일을 보여주는 메소드
	private String serviceHolidayUpdateForm(String bus_no, String bct_code) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		map = bDao.searchBFX(map);
		// 기업의 고정 스케줄을 검색
		if (map != null) {
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("월요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='월요일' id='월요일' checked /> <label class='custom-control-label' for='월요일'>월요일</label>");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='월요일' id='월요일'/> <label class='custom-control-label' for='월요일'>월요일</label>");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("화요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='화요일' id='화요일' checked /> <label class='custom-control-label' for='화요일'>화요일</label>");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='화요일' id='화요일'/> <label class='custom-control-label' for='화요일'>화요일</label>");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("수요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='수요일' id='수요일' checked /> <label class='custom-control-label' for='수요일'>수요일</label>");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='수요일' id='수요일'/> <label class='custom-control-label' for='수요일'>수요일</label>");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("목요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='목요일' id='목요일' checked /> <label class='custom-control-label' for='목요일'>목요일</label>");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='목요일' id='목요일'/> <label class='custom-control-label' for='목요일'>목요일</label>");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("금요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='금요일' id='금요일' checked /> <label class='custom-control-label' for='금요일'>금요일</label>");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='금요일' id='금요일'/> <label class='custom-control-label' for='금요일'>금요일</label>");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("토요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='토요일' id='토요일' checked /> <label class='custom-control-label' for='토요일'>토요일</label>");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='토요일' id='토요일'/> <label class='custom-control-label' for='토요일'>토요일</label>");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("일요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='일요일' id='일요일' checked /> <label class='custom-control-label' for='일요일'>일요일</label>");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='일요일' id='일요일'/> <label class='custom-control-label' for='일요일'>일요일</label>");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("공휴일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='공휴일' id='공휴일' checked /> <label class='custom-control-label' for='공휴일'>공휴일</label>");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='공휴일' id='공휴일'/> <label class='custom-control-label' for='공휴일'>공휴일</label>");
			}
			sb.append("</div>");
		}
		return sb.toString();
	}

	// 기업의 수정페이지에서 제공 서비스&이용 가능한 동물을 보여주는 메소드
	private String serviceMenuUpdateForm(String bus_no, String bct_code) {
		boolean flag = true; // 기업이 제공하는 메뉴인지 아닌지 구분하기 위한 깃발
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		List<Map<String, Object>> list;
		list = bDao.selectMenuTag(map); // 기업이 제공하는 서비스 검색
		List<Map<String, Object>> allList;
		allList = bDao.selectMENU(bct_code); // 모든 메뉴들을 검색
		String menu_no = null; // 모든 메뉴의 번호를 담을 변수
		String menu_name = null; // 모든 메뉴의 이름을 담을 변수
		String select_menu_no = null; // 기업이 제공하는 메뉴의 번호를 담을 변수
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < allList.size(); i++) { // 모든 메뉴들 개수만큼 반복
			menu_no = String.valueOf(allList.get(i).get("MENU_NO")); // 해당 메뉴의 번호를 변수에 저장
			menu_name = (String) allList.get(i).get("MENU_NAME"); // 해당 메뉴의 이름을 변수에 저장
			for (int j = 0; j < list.size(); j++) { // 기업이 제공하는 서비스 만큼 반복
				select_menu_no = String.valueOf(list.get(j).get("MENU_NO")); // 기업이 제공하는 서비스의 번호를 변수에 저장
				if (menu_no.equals(select_menu_no)) { // 모든 메뉴들 중 기업이 제공하는 메뉴와 같은 것이 있는지 검색
					flag = false; // 기업이 제공한다면 깃발을 false로 바꿔준다.
				}
			}
			sb.append("<div class='custom-control custom-checkbox'>");
			if (flag) { // 기업이 제공하지 않는 서비스면
				if (menu_name.equals("가위컷")) {
					sb.append("<input type='checkbox' class='custom-control-input' name='dog_tag' value='" + menu_name
							+ "' id='" + menu_name + "'/> <label class='custom-control-label' for='" + menu_name + "'>"
							+ menu_name + " </label>&nbsp;"); // 그냥
				} else if (menu_name.equals("고양이무마취")) {
					sb.append("<input type='checkbox' class='custom-control-input' name='cat_tag' value='" + menu_name
							+ "' id='" + menu_name + "'/> <label class='custom-control-label' for='" + menu_name + "'>"
							+ menu_name + " </label>&nbsp;"); // 그냥
				} else {
					sb.append("<input type='checkbox' class='custom-control-input' name='tag_name' value='" + menu_name
							+ "' id='" + menu_name + "'/> <label class='custom-control-label' for='" + menu_name + "'>"
							+ menu_name + " </label>&nbsp;"); // 그냥
				}
			} else { // 기업이 제공하는 서비스면
				if (menu_name.equals("가위컷")) {
					sb.append("<input type='checkbox' class='custom-control-input' name='dog_tag' value='" + menu_name
							+ "' id='" + menu_name + "' checked/> <label class='custom-control-label' for='" + menu_name
							+ "'>" + menu_name + " </label>&nbsp;"); // 그냥
				} else if (menu_name.equals("고양이무마취")) {
					sb.append("<input type='checkbox' class='custom-control-input' name='cat_tag' value='" + menu_name
							+ "' id='" + menu_name + "'checked/> <label class='custom-control-label' for='" + menu_name
							+ "'>" + menu_name + " </label>&nbsp;"); // 그냥
				} else {
					sb.append("<input type='checkbox' class='custom-control-input' name='tag_name' value='" + menu_name
							+ "' id='" + menu_name + "' checked/> <label class='custom-control-label' for='" + menu_name
							+ "'>" + menu_name + " </label>&nbsp;"); // 그냥
				} // 만들어준다.
			}
			sb.append("</div>");
			flag = true; // 깃발은 다시 true로 바꿔준다.
		}
		sb.append("</div>");
		sb.append("<div class='form-group mb-3'>");
		sb.append("<label for='simpleinput'>서비스 가능한 동물종류</label><br/>");
		// 밑에 과정을 거치는 이유는 모든 동물 종류 테이블에는 햄스터 같은 종류도 들어있기에, 1차적으로 각 업종에서 제공되는 동물들의 코드만
		// 검색해서 가져와야 한다.
		// 2차적으로는 기업에서 제공하기로 했던 동물종류들은 checked 옵션을 줘야하기에 1차로 검색됐던 결과와 다시 비교를 해줘야 한다.
		List<String> allAimlList = new ArrayList<String>(); // 동물의 종류를 담아줄 리스트 선언
		if (bct_code.equals("M")) { // 병원에서 제공될 수 있는 동물 종류
			allAimlList.add("소형견");
			allAimlList.add("중형견");
			allAimlList.add("대형견");
			allAimlList.add("고양이");
			allAimlList.add("소동물");
			allAimlList.add("파충류");
			allAimlList.add("가축");
		} else if (bct_code.equals("B")) { // 미용에서 제공될 수 있는 동물 종류
			allAimlList.add("소형견");
			allAimlList.add("중형견");
			allAimlList.add("대형견");
			allAimlList.add("고양이");
		} else if (bct_code.equals("H")) { // 호텔에서 제공될 수 있는 동물 종류
			allAimlList.add("소형견");
			allAimlList.add("중형견");
			allAimlList.add("대형견");
			allAimlList.add("고양이");
			allAimlList.add("소동물");
		}

		List<String> allAimlNoList = new ArrayList<String>(); // 업종에서 제공되는 동물의 번호를 담을 리스트 선언
		List<Map<String, Object>> ptyList;
		ptyList = bDao.animalSelect(); // 모든 동물 종류 검색
		for (int k = 0; k < ptyList.size(); k++) { // 검색된 수 만큼 반복
			String pty_no = String.valueOf(ptyList.get(k).get("PTY_NO")); // 검색된 동물의 번호를 가져온다.
			String pty_name = (String) ptyList.get(k).get("PTY_NAME"); // 검색된 동물의 이름을 가져온다.
			for (int l = 0; l < allAimlList.size(); l++) { // 각 업종들이 제공하는 동물종류만큼 반복
				if (pty_name.equals(allAimlList.get(l))) { // 업종들이 제공하는 동물종류가 모든 동물 종류 중 하나와 같다면
					allAimlNoList.add(pty_no); // 해당 동물의 번호를 담아준다.
				}
			}
		}
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		List<Map<String, Object>> aimlList; // 기업이 제공하는 동물종류를 담을 리스트 선언
		aimlList = bDao.selectAnimalTag(map); // 기업에서 제공하는 동물들의 번호를 담아준다.
		for (int i = 0; i < allAimlList.size(); i++) { // 모든 동물 종류만큼 반복 (햄스터 같은)
			String no = allAimlNoList.get(i); // 검색된 동물의 번호
			String name = allAimlList.get(i); // 각 업종에서 제공되는 동물의 이름이 적힌 리스트
			for (int j = 0; j < aimlList.size(); j++) { // 기업이 제공하는 동물 종류 수만큼 반복
				String select_aiml_no = String.valueOf(aimlList.get(j).get("PTY_NO")); // 해당 동물의 번호를 담아준다.
				if (no.equals(select_aiml_no)) { // 검색된 동물의 번호와 제공하는 동물의 번호가 같다면
					flag = false; // 깃발을 false 로 바꿔준다.
				}
			}
			sb.append("<div class='custom-control custom-checkbox'>");
			if (flag) { // 제공하지 않던 동물 종류라면
				if (name.equals("고양이")) { // 근데 그게 고양이라면
					sb.append("<input type='checkbox' class='custom-control-input' name='cat_code' value='" + name
							+ "' id='" + name + "'/> <label class='custom-control-label' for='" + name + "'>" + name
							+ "</label>&nbsp;"); // cat_code를 붙여서
					// 그냥 가져온다.
				} else { // 둘다 아니라면 animal_code로 그냥 가져온다.
					sb.append("<input type='checkbox' class='custom-control-input' name='animal_code' value='" + name
							+ "' id='" + name + "'/> <label class='custom-control-label' for='" + name + "'>" + name
							+ "</label>&nbsp;");
				}
			} else { // 제공하던 동물 종류라면
				if (name.equals("고양이")) { // 근데 그게 고양이라면
					sb.append("<input type='checkbox' class='custom-control-input' name='cat_code' value='" + name
							+ "' id='" + name + "' checked/> <label class='custom-control-label' for='" + name + "'>"
							+ name + "</label>&nbsp;"); // cat_code에
				} else { // 고양이가 아니라면
					sb.append("<input type='checkbox' class='custom-control-input' name='animal_code' value='" + name
							+ "' id='" + name + "' checked/> <label class='custom-control-label' for='" + name + "'>"
							+ name + "</label>&nbsp;"); // 그냥
				}
			}
			sb.append("</div>");
			flag = true; // 깃발은 다시 true로 바꿔준다.
		}
		sb.append("<br/>");
		return sb.toString();
	}

	// 기업의 상세정보 수정
	@Transactional
	public ModelAndView serviceUpdate(MultipartHttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();

		String bct_code = request.getParameter("bct_code");

		String no = (String) session.getAttribute("no");
		String work_o = request.getParameter("work_o");
		String work_c = request.getParameter("work_c");
		String lunch_o = request.getParameter("lunch_o");
		String lunch_c = request.getParameter("lunch_c");

		String am_open = work_o.split(":")[0] + work_o.split(":")[1];
		String pm_close = work_c.split(":")[0] + work_c.split(":")[1];
		String lunch_open = lunch_o.split(":")[0] + lunch_o.split(":")[1];
		String lunch_close = lunch_c.split(":")[0] + lunch_c.split(":")[1];
		String first = request.getParameter("first");

		map.put("bus_no", no);
		map.put("bct_code", bct_code);
		map.put("am_open", am_open);
		map.put("pm_close", pm_close);
		map.put("lunch_open", lunch_open);
		map.put("lunch_close", lunch_close);
		if (first.equals("first")) {
			weekSet(request, session); // 스케줄 등록 (영업시간,점심시간,고정휴무일)
			checkTag(request, session); // 제공서비스 등록
			insertPrice(request, session); // 기업이 지정한 서비스 가격 등록
		} else {
			bDao.updateServiceBSD(map); // 기업의 하루 스케줄 수정
			map = holidayMap(request, session); // 기업의 휴일을 수정하기 위해 정보를 map에 담아오는 메소드
			bDao.updateServiceBFX(map); // 기업의 고정 스케줄 수정
			if (bct_code.equals("M")) {
				bDao.deletePRC(map);
				insertPrice(request, session);
			} else {
				String[] price = request.getParameterValues("price");
				if (price != null) {
					bDao.deletePRC(map);
					insertPrice(request, session); // 기업이 지정한 서비스 가격 등록
				}
			}
			bDao.deleteBTG(map);
			checkTag(request, session);
		}
		fileWriter(request, session); // 사진 등록 (사업장사진)
		mav.setView(new RedirectView("/serviceManagement"));
		return mav;
	}

	// 기업-업종 삭제, 각종 정보들과 직원들 모두 삭제
	@Transactional
	public ModelAndView serviceDelete(HttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		String bus_no = (String) session.getAttribute("no");
		String bct_code = request.getParameter("bct_code");
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		String svc_no = bDao.searchSVC(map);
		String text = null;
		if (svc_no.equals("1")) {
			text = "<script>alert('주 업종은 삭제할 수 없습니다.');</script>";
			mav.addObject("text", text);
		} else {
			System.out.println("삭제");
			List<Map<String, Object>> list;
			list = bDao.selectEMP(map);
			for (int i = 0; i < list.size(); i++) {
				String emp_no = (String) list.get(i).get("EMP_NO");
				bDao.deleteBK(emp_no);
				bDao.deleteESD(emp_no); // 해당 직원의 하루 스케줄 삭제
				bDao.deleteEFX(emp_no); // 해당 직원의 고정 스케줄 삭제
				bDao.deleteEMP(emp_no); // 해당 직원의 상세정보 삭제
			}
			bDao.deletePRC(map); // 해당 업종의 가격삭제
			bDao.deleteBSD(map); // 해당 업종의 하루 스케줄 삭제
			bDao.deleteBFX(map); // 해당 업종의 고정 스케줄 삭제
			bDao.deleteBTG(map); // 해당 업종의 관련태그 삭제
			bDao.deleteGLR(map); // 해당 업종의 사진 삭제
			bDao.deleteSVC(map); // 해당 업종의 정보 삭제
		}
		mav.setView(new RedirectView("/serviceManagement"));
		return mav;
	}

	// 기업이 가진 업종의 직원을 불러오기 위해 업종 선택 버튼 생성
	public ModelAndView stepListBut(HttpSession session) {
		mav = new ModelAndView();
		List<Map<String, Object>> list;
		StringBuilder sb = new StringBuilder();
		String bus_no = (String) session.getAttribute("no");
		list = bDao.selectSVC(bus_no);
		System.out.println(list);
		sb.append("<ul class=\"nav nav-pills bg-light nav-justified mb-3\">");
		for (int i = 0; i < list.size(); i++) {
			String bct_code = (String) list.get(i).get("BCT_CODE");
			String bct_name = bDao.searchBCTname(bct_code);
			sb.append("<li class='nav-item'><a href='javascript:void(0)' onclick='chk(\"" + bct_code
					+ "\")' data-toggle='tab' aria-expanded='false' class='nav-link rounded-0'>");
			if (bct_code == "M") {
				sb.append("<i class=\"mdi mdi-hospital font-18\"></i>");
			}
			if (bct_code == "B") {
				sb.append("<i class=\"mdi mdi-content-cut font-18\"></i>");
			}
			if (bct_code == "H") {
				sb.append("<i class=\"mdi mdi-home font-18\"></i>");
			}
			sb.append("<span class=\"d-none d-lg-block\">" + bct_name + "</span>" + "</a></li>");
		}
		sb.append("</div>");
		mav.addObject("code", sb.toString());
		mav.setViewName("stepList");
		return mav;
	}

	// 직원 등록 페이지로 이동
	public ModelAndView stepInsertFormBut(HttpSession session) {
		mav = new ModelAndView();
		StringBuilder sb = new StringBuilder();
		String bus_no = (String) session.getAttribute("no");
		List<Map<String, Object>> list;
		list = bDao.selectSVC(bus_no);
		sb.append("");
		sb.append("<ul class='nav nav-pills bg-light nav-justified mb-3'>");
		for (int i = 0; i < list.size(); i++) {
			String bct_code = (String) list.get(i).get("BCT_CODE");
			System.out.println(bct_code);
			String bct_name = bDao.searchBCTname(bct_code);
			sb.append("<li class='nav-item'>&nbsp;<a href='javascript:void(0)' onclick='chk(\"" + bct_code
					+ "\")' data-toggle='tab' aria-expanded='false' class='nav-link rounded-0'> " + bct_name
					+ " &nbsp;</a></li>");
		}
		sb.append("</ul>");
		mav.addObject("type", sb.toString());

		// String time = timeSelect();
		// String lunch = lunchSelect();
		String holiday = holidaySelect();

		// mav.addObject("time", time);
		// mav.addObject("lunch", lunch);
		mav.addObject("holiday", holiday);
		mav.setViewName("stepInsertForm");
		return mav;
	}

	// 해당 업종에 직원 등록
	@Transactional
	public ModelAndView stepInsert(MultipartHttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		// String flag = "emp";
		// fileWriter(request, flag);
		String code = request.getParameter("bct_code");
		System.out.println(code);
		stepInsertEMP(request, session);
		System.out.println("등록완료");
		mav.setView(new RedirectView("/stepListBut"));
		return mav;
	}

	// 직원 등록
	@Transactional
	private void stepInsertEMP(MultipartHttpServletRequest request, HttpSession session) {
		String no = (String) session.getAttribute("no");
		String code = request.getParameter("bct_code");
		String name = request.getParameter("emp_name");
		String pos = request.getParameter("emp_pos");
		String part = request.getParameter("emp_part");

		Map<String, Object> map = new HashMap<String, Object>();
		String root = request.getSession().getServletContext().getRealPath("/");
		String location = "resources/upload/";
		String path = root + location;/* resource는 webapp/resources에 저장 */
		File dir = new File(path);
		if (!dir.isDirectory()) { // upload폴더 없다면
			dir.mkdir(); // upload폴더 생성
		}
		MultipartFile photo = request.getFile("emp_photo");
		MultipartFile license = request.getFile("emp_license");

		String photooriFileName = photo.getOriginalFilename(); // a.txt
		String licesnseoriFileName = photo.getOriginalFilename(); // a.txt

		String photosysFileName = System.currentTimeMillis() + "."
				+ photooriFileName.substring(photooriFileName.lastIndexOf(".") + 1);
		String licensesysFileName = System.currentTimeMillis() + "."
				+ licesnseoriFileName.substring(licesnseoriFileName.lastIndexOf(".") + 1);
		try {
			photo.transferTo(new File(path + photosysFileName));
			license.transferTo(new File(path + licensesysFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("bus_no", no);
		map.put("bct_code", code);
		map.put("name", name);
		map.put("pos", pos);
		map.put("part", part);
		map.put("photo", photosysFileName);
		map.put("path", location);
		map.put("license", licensesysFileName);
		map.put("lcloc", location);
		bDao.stepInsert(map);
		int empNo = (int) map.get("EMP_NO");
		System.out.println(empNo);
		empScheduleInsert(request, empNo);
	}

	// 직원 스케줄 등록
	private void empScheduleInsert(MultipartHttpServletRequest request, int empNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("emp_no", "S" + empNo);
		System.out.println(empNo);

		String work_o = request.getParameter("work_o");
		String work_c = request.getParameter("work_c");
		String am_open = work_o.split(":")[0] + work_o.split(":")[1];
		String pm_close = work_c.split(":")[0] + work_c.split(":")[1];
		String time = am_open + pm_close;

		String lunch_o = request.getParameter("lunch_o");
		String lunch_c = request.getParameter("lunch_c");
		String lunch_open = lunch_o.split(":")[0] + lunch_o.split(":")[1];
		String lunch_close = lunch_c.split(":")[0] + lunch_c.split(":")[1];
		String lunch = lunch_open + lunch_close;

		String[] holiday = request.getParameterValues("holiday");
		map.put("efx_mon", time);
		map.put("efx_tue", time);
		map.put("efx_wed", time);
		map.put("efx_thu", time);
		map.put("efx_fri", time);
		map.put("efx_sat", time);
		map.put("efx_sun", time);
		map.put("efx_hld", time);
		int[] num = new int[holiday.length];
		for (int i = 0; i < holiday.length; i++) {
			if (holiday[i].equals("월요일")) {
				map.put("efx_mon", "XXXXXXXX");
				num[i] = 2;
			} else if (holiday[i].equals("화요일")) {
				map.put("efx_tue", "XXXXXXXX");
				num[i] = 3;
			} else if (holiday[i].equals("수요일")) {
				map.put("efx_wed", "XXXXXXXX");
				num[i] = 4;
			} else if (holiday[i].equals("목요일")) {
				map.put("efx_thu", "XXXXXXXX");
				num[i] = 5;
			} else if (holiday[i].equals("금요일")) {
				map.put("efx_fri", "XXXXXXXX");
				num[i] = 6;
			} else if (holiday[i].equals("토요일")) {
				map.put("efx_sat", "XXXXXXXX");
				num[i] = 7;
			} else if (holiday[i].equals("일요일")) {
				map.put("efx_sun", "XXXXXXXX");
				num[i] = 1;
			} else if (holiday[i].equals("공휴일")) {
				map.put("efx_hld", "XXXXXXXX");
			}
		}
		map.put("efx_lch", lunch);
		int weekSet = bDao.empInsertEFX(map);
		if (weekSet != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar cal = Calendar.getInstance();
			boolean flag = true;
			String date = null;
			int dayOfWeek = 0;
			for (int i = 0; i < 30; i++) {
				date = sdf.format(cal.getTime());
				dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
				for (int j = 0; j < num.length; j++) {
					if (dayOfWeek == num[j]) {
						flag = false;
					}
				}
				if (flag) {
					map.put("date", date);
					map.put("am_open", am_open);
					map.put("am_close", lunch_open);
					map.put("pm_open", lunch_close);
					map.put("pm_close", pm_close);
					bDao.empInsertESD(map);
				}
				cal.add(Calendar.DATE, 1);
				dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
				flag = true;
			}
		}
	}

	// 직원 상세정보
	@Transactional
	public ModelAndView stepDetail(HttpServletRequest request, HttpSession session) {
		mav = new ModelAndView();
		String emp_no = request.getParameter("emp_no");
		System.out.println(emp_no);
		String bus_no = (String) session.getAttribute("no");
		System.out.println(bus_no);
		Map<String, Object> busiMap = new HashMap<String, Object>();
		Map<String, Object> stepMap = new HashMap<String, Object>();
		StringBuilder text = new StringBuilder();
		stepMap = bDao.searchEMP(emp_no);
		text.append(makeStepDetail(stepMap));
		String bct_code = (String) stepMap.get("BCT_CODE");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		busiMap = bDao.holidaySelected(map);
		stepMap = bDao.searchEFX(emp_no);

		text.append(makeStepWorkTime(emp_no, bus_no, bct_code));
		text.append(makeStepLunchTime(emp_no, bus_no, bct_code));
		text.append(makeStepHoliday(busiMap, stepMap));
		text.append(button());

		mav.addObject("text", text);
		mav.setViewName("stepDetail");

		return mav;
	}

	// 직원 상세정보 페이지 구성
	private String makeStepDetail(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		String bct_code = (String) map.get("BCT_CODE");
		String bct_name = bDao.searchBCTname(bct_code);
		String emp_name = (String) map.get("EMP_NAME");
		String emp_pos = (String) map.get("EMP_POS");
		String emp_part = (String) map.get("EMP_PART");
		String emp_no = (String) map.get("EMP_NO");
		String bus_no = (String) map.get("BUS_NO");
		sb.append("<input type='hidden' name='emp_no' value='" + emp_no + "'/>");
		sb.append("<input type='hidden' name='bus_no' value='" + bus_no + "'/>");
		sb.append("<div class='col-lg-6'>");
		sb.append("<div class='form-group mb-3'>");
		sb.append("<label for='simpleinput'>업종</label>");
		sb.append("<input type='text' class='form-control' name='bct_code' value='" + bct_name + "' readonly/><br/>");
		sb.append("</div>");
		sb.append("<div class='form-group mb-3'>");
		sb.append("<label for='example-fileinput' style='margin-right:15px;'>프로필 사진</label>");
		sb.append("<input type='file' name='emp_photo'/><br/>");
		sb.append("</div>");
		sb.append("<div class='form-group mb-3'>");
		sb.append("<label for='simpleinput'>이름</label>");
		sb.append("<input type='text' class='form-control' name='emp_name' value='" + emp_name + "'/><br/>");
		sb.append("</div>");
		sb.append("<div class='form-group mb-3'>");
		sb.append("<label for='simpleinput'>직급</label>");
		sb.append("<input type='text' class='form-control' name='emp_pos' value='" + emp_pos + "'/><br/>");
		sb.append("</div>");
		sb.append("<div class='form-group mb-3'>");
		sb.append("<label for='simpleinput'>담당분야</label>");
		sb.append("<input type='text' class='form-control' name='emp_part' value='" + emp_part + "'/><br/>");
		sb.append("</div>");
		sb.append("</div>");
		return sb.toString();
	}

	// 직원 수정페이지에서 버튼 구성
	private String button() {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<br/><input type='button' class='btn btn-outline-secondary' name='수정완료' value='수정완료' onclick='but(this)' />");
		sb.append(
				"&nbsp;&nbsp;&nbsp;<input type='button' class='btn btn-outline-danger' name='삭제' value='삭제' onclick='but(this)' />");
		return sb.toString();
	}

	// 직원 점심시간 표시
	private String makeStepLunchTime(String emp_no, String bus_no, String bct_code) {
		Map<String, Object> bmap = new HashMap<String, Object>();
		bmap.put("bus_no", bus_no);
		bmap.put("bct_code", bct_code);
		bmap = bDao.searchBSD(bmap);
		String am_close_b = (String) bmap.get("AM_CLOSE");
		String pm_open_b = (String) bmap.get("PM_OPEN");

		String lunch_open_pre_b = am_close_b.substring(0, 2);
		String lunch_open_las_b = am_close_b.substring(2, 4);
		String lunch_close_pre_b = pm_open_b.substring(0, 2);
		String lunch_close_las_b = pm_open_b.substring(2, 4);

		am_close_b = lunch_open_pre_b + ":" + lunch_open_las_b;
		pm_open_b = lunch_close_pre_b + ":" + lunch_close_las_b;

		Map<String, Object> map = new HashMap<String, Object>();
		map = bDao.searchESD(emp_no);

		String am_close_e = (String) map.get("AM_CLOSE");
		String pm_open_e = (String) map.get("PM_OPEN");
		String lunch_open_pre_e = am_close_e.substring(0, 2);
		String lunch_open_las_e = am_close_e.substring(2, 4);
		String lunch_close_pre_e = pm_open_e.substring(0, 2);
		String lunch_close_las_e = pm_open_e.substring(2, 4);

		am_close_e = lunch_open_pre_e + ":" + lunch_open_las_e;
		pm_open_e = lunch_close_pre_e + ":" + lunch_close_las_e;

		StringBuilder sb = new StringBuilder();
		sb.append("<div class='form-group mb-3'><label for='simpleinput'>직원 점심 시작시간</label> "
				+ "<input type='time' class='form-control' name='lunch_o' id='lunch_open' value='" + am_close_e
				+ "' step='1800' min='" + am_close_b + "' max='" + pm_open_b + "' required></div>");
		sb.append("<div class='form-group mb-3'><label for='simpleinput'>직원 점심 마감시간</label> "
				+ "<input type='time' class='form-control' name='lunch_c' id='lunch_close' value='" + pm_open_e
				+ "' step='1800' min='" + am_close_b + "' max='" + pm_open_b + "' required></div>");
		sb.append("<p> 점심시간은 " + am_close_b + " ~ " + pm_open_b + " 입니다.");
		return sb.toString();
	}

	// 직원 업무시간 표시
	private String makeStepWorkTime(String emp_no, String bus_no, String bct_code) {
		Map<String, Object> bmap = new HashMap<String, Object>();
		bmap.put("bus_no", bus_no);
		bmap.put("bct_code", bct_code);
		bmap = bDao.searchBSD(bmap);
		String am_open_b = (String) bmap.get("AM_OPEN");
		String pm_close_b = (String) bmap.get("PM_CLOSE");

		String am_pre_b = am_open_b.substring(0, 2);
		String am_las_b = am_open_b.substring(2, 4);
		String pm_pre_b = pm_close_b.substring(0, 2);
		String pm_las_b = pm_close_b.substring(2, 4);

		am_open_b = am_pre_b + ":" + am_las_b;
		pm_close_b = pm_pre_b + ":" + pm_las_b;

		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(emp_no);
		map = bDao.searchESD(emp_no);

		String am_open_e = (String) map.get("AM_OPEN");
		String pm_close_e = (String) map.get("PM_CLOSE");

		String am_pre_e = am_open_e.substring(0, 2);
		String am_las_e = am_open_e.substring(2, 4);
		String pm_pre_e = pm_close_e.substring(0, 2);
		String pm_las_e = pm_close_e.substring(2, 4);

		am_open_e = am_pre_e + ":" + am_las_e;
		pm_close_e = pm_pre_e + ":" + pm_las_e;

		StringBuilder sb = new StringBuilder();
		sb.append("<div class='col-lg-6'>");
		sb.append("<div class='form-group mb-3'><label for='simpleinput'>직원 출근시간</label> "
				+ "<input type='time' class='form-control' name='work_o' id='am_open' value='" + am_open_e
				+ "' step='1800' min='" + am_open_b + "' max='" + pm_close_b + "' required></div>");
		sb.append("<div class='form-group mb-3'><label for='simpleinput'>직원 퇴근시간</label> "
				+ "<input type='time' class='form-control' name='work_c' id='pm_close' value='" + pm_close_e
				+ "' step='1800' min='" + am_open_b + "' max='" + pm_close_b + "' required></div>");
		sb.append("<p> 영업시간은 " + am_open_b + " ~ " + pm_close_b + " 입니다.");
		return sb.toString();
	}

	// 직원 휴무일 표시
	private String makeStepHoliday(Map<String, Object> busiMap, Map<String, Object> stepMap) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='form-group mb-3'>");
		sb.append("<label for='simpleinput'>고정휴무일</label><br/>");
		boolean flag = true;
		sb.append("<div class='custom-control custom-checkbox'> ");
		if (stepMap.get("월요일").equals("XXXXXXXX")) {
			if (busiMap.get("월요일").equals("XXXXXXXX")) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='월요일' id='월요일'checked onclick='return false;' /> <label class='custom-control-label' for='월요일'>월요일</label>&nbsp;");
				System.out.println("불가능한X값이야");
				flag = false;
			} else {
				System.out.println("월요일은 지나가는 값 ");
			}
			if (flag) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='월요일' id='월요일' checked /> <label class='custom-control-label' for='월요일'>월요일</label>&nbsp;");
				System.out.println("변화가능한X값이야 ");
			}
			flag = true;
		} else {
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='holiday' value='월요일' id='월요일'/> <label class='custom-control-label' for='월요일'>월요일</label>&nbsp;");
		}
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'> ");
		if (stepMap.get("화요일").equals("XXXXXXXX")) {
			if (busiMap.get("화요일").equals("XXXXXXXX")) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='화요일' id='화요일'checked onclick='return false;' /> <label class='custom-control-label' for='화요일'>화요일</label>&nbsp;");
				flag = false;
			}
			if (flag) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='화요일' id='화요일' checked /> <label class='custom-control-label' for='화요일'>화요일</label>&nbsp;");
			}
			flag = true;
		} else {
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='holiday' value='화요일' id='화요일'/> <label class='custom-control-label' for='화요일'>화요일</label>&nbsp;");
		}
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'> ");
		if (stepMap.get("수요일").equals("XXXXXXXX")) {
			if (busiMap.get("수요일").equals("XXXXXXXX")) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='수요일' id='수요일'checked onclick='return false;' /> <label class='custom-control-label' for='수요일'>수요일</label>&nbsp;");
				flag = false;
			}
			if (flag) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='수요일' id='수요일' checked /> <label class='custom-control-label' for='수요일'>수요일</label>&nbsp;");
			}
			flag = true;
		} else {
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='holiday' value='수요일' id='수요일'/> <label class='custom-control-label' for='수요일'>수요일</label>&nbsp;");
		}
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'> ");
		if (stepMap.get("목요일").equals("XXXXXXXX")) {
			if (busiMap.get("목요일").equals("XXXXXXXX")) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='목요일' id='목요일'checked onclick='return false;' /> <label class='custom-control-label' for='목요일'>목요일</label>&nbsp;");
				flag = false;
			}
			if (flag) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='목요일' id='목요일' checked /> <label class='custom-control-label' for='목요일'>목요일</label>&nbsp;");
			}
			flag = true;
		} else {
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='holiday' value='목요일' id='목요일'/> <label class='custom-control-label' for='목요일'>목요일</label>&nbsp;");
		}
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'> ");
		if (stepMap.get("금요일").equals("XXXXXXXX")) {
			if (busiMap.get("금요일").equals("XXXXXXXX")) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='금요일' id='금요일'checked onclick='return false;' /> <label class='custom-control-label' for='금요일'>금요일</label>&nbsp;");
				flag = false;
			}
			if (flag) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='금요일' id='금요일' checked /> <label class='custom-control-label' for='금요일'>금요일</label>&nbsp;");
			}
			flag = true;
		} else {
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='holiday' value='금요일' id='금요일'/> <label class='custom-control-label' for='금요일'>금요일</label>&nbsp;");
		}
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'> ");
		if (stepMap.get("토요일").equals("XXXXXXXX")) {
			if (busiMap.get("토요일").equals("XXXXXXXX")) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='토요일' id='토요일'checked onclick='return false;' /> <label class='custom-control-label' for='토요일'>토요일</label>&nbsp;");
				flag = false;
			}
			if (flag) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='토요일' id='토요일' checked /> <label class='custom-control-label' for='토요일'>토요일</label>&nbsp;");
			}
			flag = true;
		} else {
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='holiday' value='토요일' id='토요일'/> <label class='custom-control-label' for='토요일'>토요일</label>&nbsp;");
		}
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'> ");
		if (stepMap.get("일요일").equals("XXXXXXXX")) {
			if (busiMap.get("일요일").equals("XXXXXXXX")) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='일요일' id='일요일'checked onclick='return false;' /> <label class='custom-control-label' for='일요일'>일요일</label>&nbsp;");
				flag = false;
			}
			if (flag) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='일요일' id='일요일' checked /> <label class='custom-control-label' for='일요일'>일요일</label>&nbsp;");
			}
			flag = true;
		} else {
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='holiday' value='일요일' id='일요일'/> <label class='custom-control-label' for='일요일'>일요일</label>&nbsp;");
		}
		sb.append("</div>");
		sb.append("<div class='custom-control custom-checkbox'> ");
		if (stepMap.get("공휴일").equals("XXXXXXXX")) {
			if (busiMap.get("공휴일").equals("XXXXXXXX")) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='공휴일' id='공휴일'checked onclick='return false;' /> <label class='custom-control-label' for='공휴일'>공휴일</label>&nbsp;");
				flag = false;
			}
			if (flag) {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='공휴일' id='공휴일' checked /> <label class='custom-control-label' for='공휴일'>공휴일</label>&nbsp;");
			}
			flag = true;
		} else {
			sb.append(
					"<input type='checkbox' class='custom-control-input' name='holiday' value='공휴일' id='공휴일'/> <label class='custom-control-label' for='공휴일'>공휴일</label>&nbsp;");
		}
		sb.append("</div>");
		sb.append("</div>"); // col-6 마감
		return sb.toString();
	}

	// 직원 수정
	@Transactional
	public ModelAndView stepUpdate(MultipartHttpServletRequest request) {
		mav = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		String emp_no = request.getParameter("emp_no");
		String bct_code = request.getParameter("bct_code");
		String emp_name = request.getParameter("emp_name");
		String emp_pos = request.getParameter("emp_pos");
		String emp_part = request.getParameter("emp_part");
		String[] holiday = request.getParameterValues("holiday");

		String work_o = request.getParameter("work_o");
		String work_c = request.getParameter("work_c");
		String lunch_o = request.getParameter("lunch_o");
		String lunch_c = request.getParameter("lunch_c");
		String am_open = work_o.split(":")[0] + work_o.split(":")[1];
		String pm_close = work_c.split(":")[0] + work_c.split(":")[1];
		String lunch_open = lunch_o.split(":")[0] + lunch_o.split(":")[1];
		String lunch_close = lunch_c.split(":")[0] + lunch_c.split(":")[1];

		map = bDao.searchEMP(emp_no);

		map.put("emp_no", emp_no);
		map.put("emp_name", emp_name);
		map.put("emp_pos", emp_pos);
		map.put("emp_part", emp_part);

		String root = request.getSession().getServletContext().getRealPath("/");
		String location = "resources/upload/";
		String path = root + location;/* resource는 webapp/resources에 저장 */
		File dir = new File(path);
		if (!dir.isDirectory()) { // upload폴더 없다면
			dir.mkdir(); // upload폴더 생성
		}
		MultipartFile photo = request.getFile("emp_photo");
		String photooriFileName = photo.getOriginalFilename(); // a.txt
		System.out.println(photooriFileName);
		if (photooriFileName.equals("")) {
			System.out.println("사진이 없습니다.");
			bDao.updateEMP(map);
		} else {
			System.out.println("머야");
			String photosysFileName = System.currentTimeMillis() + "."
					+ photooriFileName.substring(photooriFileName.lastIndexOf(".") + 1);
			try {
				photo.transferTo(new File(path + photosysFileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			map.put("emp_photo", photosysFileName);
			map.put("emp_loc", location);
			bDao.updateEMPPhoto(map);
		}

		map.put("emp_no", emp_no);
		map.put("am_open", am_open);
		map.put("am_close", lunch_open);
		map.put("pm_open", lunch_close);
		map.put("pm_close", pm_close);
		bDao.updateESD(map);
		System.out.println("시간입력 끝");
		String time = am_open + pm_close;
		String lunch = lunch_open + lunch_close;
		map.put("efx_mon", time);
		map.put("efx_tue", time);
		map.put("efx_wed", time);
		map.put("efx_thu", time);
		map.put("efx_fri", time);
		map.put("efx_sat", time);
		map.put("efx_sun", time);
		map.put("efx_hld", time);
		int[] num = new int[holiday.length];
		for (int i = 0; i < holiday.length; i++) {
			System.out.println(holiday[i]);
			if (holiday[i].equals("월요일")) {
				map.put("efx_mon", "XXXXXXXX");
				num[i] = 2;
			} else if (holiday[i].equals("화요일")) {
				map.put("efx_tue", "XXXXXXXX");
				num[i] = 3;
			} else if (holiday[i].equals("수요일")) {
				map.put("efx_wed", "XXXXXXXX");
				num[i] = 4;
			} else if (holiday[i].equals("목요일")) {
				map.put("efx_thu", "XXXXXXXX");
				num[i] = 5;
			} else if (holiday[i].equals("금요일")) {
				map.put("efx_fri", "XXXXXXXX");
				num[i] = 6;
			} else if (holiday[i].equals("토요일")) {
				map.put("efx_sat", "XXXXXXXX");
				num[i] = 7;
			} else if (holiday[i].equals("일요일")) {
				map.put("efx_sun", "XXXXXXXX");
				num[i] = 1;
			} else if (holiday[i].equals("공휴일")) {
				map.put("efx_hld", "XXXXXXXX");
			}
		}
		map.put("efx_lch", lunch);
		bDao.updateEFX(map);
		mav.setView(new RedirectView("/stepListBut"));
		return mav;
	}

	// 직원 삭제
	@Transactional
	public ModelAndView stepDelete(MultipartHttpServletRequest request) {
		mav = new ModelAndView();
		String emp_no = request.getParameter("emp_no");
		System.out.println(emp_no);
		bDao.deleteESD(emp_no);
		bDao.deleteEFX(emp_no);
		bDao.deleteEMP(emp_no);
		mav.setView(new RedirectView("/stepListBut"));
		return mav;
	}

	// 해당 업종 직원 리스트 불러오기 (ajax)
	public String stepList(HttpServletRequest request, HttpSession session) {
		List<Map<String, Object>> list;
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		String bus_no = (String) session.getAttribute("no");
		String bct_code = request.getParameter("bct_code");
		System.out.println(bct_code);
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		list = bDao.selectEMP(map);
		if (list != null) {
			sb.append("<div class='row'>");
			sb.append("<div class='col-12'>");
			sb.append("<div class='card-deck-wrapper'>");
			sb.append("<div class='card-deck'>");

			for (int i = 0; i < list.size(); i++) {
				String emp_no = (String) list.get(i).get("EMP_NO");
				String emp_name = (String) list.get(i).get("EMP_NAME");
				String emp_pos = (String) list.get(i).get("EMP_POS");
				String emp_photo = (String) list.get(i).get("EMP_PHOTO");
				String emp_loc = (String) list.get(i).get("EMP_LOC");
				sb.append("<div class='col-lg-4'>");
				sb.append("<div class='card d-block' style='text-align: center; margin-bottom:20px;'>");
				sb.append("<a href='stepDetail?emp_no=" + emp_no + "'>");
				sb.append("<img class='rounded-circle img-thumbnail' src='" + emp_loc + emp_photo
						+ "' style='width: 150px;height: 150px;margin-top: 20px;'/>");
				sb.append("</a>");
				sb.append("<div class='card-body'>");
				sb.append("<h5 class='card-title'>" + emp_name + "</h5>");
				sb.append("<p class='card-text'> 직급: " + emp_pos + "</p>");
				sb.append("</div>");
				sb.append("</div>");
				sb.append("</div>");
			}
			sb.append("</div>");
			sb.append("</div>");
			sb.append("</div>");
			sb.append("</div>");
		} else if (list == null) {
			sb.append("<div class=\"alert alert-info\" role=\"alert\" style='margin-top:2rem;margin-bottom:0;'>"
					+ "<i class=\"dripicons-information mr-2\"></i> 직원을 등록해주셔야 <strong>예약</strong>"
					+ "을&nbsp;받을 수 있습니다!</div>");
		}
		return sb.toString();
	}

	// 해당 업종에 직원 등록하는 페이지
	public String stepInsertForm(HttpServletRequest request, HttpSession session) {
		String bct_code = request.getParameter("bct_code");
		String work = checkWorkTime(request, session);
		String lunch = checkLunchTime(request, session);
		String holiday = Holiday(bct_code, "step", session);
		String hidden_code = "<input type='hidden' name='bct_code' value='" + bct_code + "'/>";
		return work + lunch + holiday + hidden_code;
	}

	// 직원 업무시간 확인 메소드
	private String checkWorkTime(HttpServletRequest request, HttpSession session) {
		String no = (String) session.getAttribute("no");
		String code = request.getParameter("bct_code");
		System.out.println(code);
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(code);
		if (code.equals("병원")) {
			code = "M";
		} else if (code.equals("미용")) {
			code = "B";
		} else if (code.equals("호텔")) {
			code = "H";
		}
		map.put("bus_no", no);
		map.put("bct_code", code);

		map = bDao.searchBSD(map);
		String am_open = (String) map.get("AM_OPEN");
		String pm_close = (String) map.get("PM_CLOSE");

		String am_pre = am_open.substring(0, 2); // 오픈시간 hour
		String am_las = am_open.substring(2, 4); // 오픈시간 min
		String pm_pre = pm_close.substring(0, 2); // 마감시간 hour
		String pm_las = pm_close.substring(2, 4); // 마감시간 min

		am_open = am_pre + ":" + am_las;
		pm_close = pm_pre + ":" + pm_las;

		StringBuilder sb = new StringBuilder();
		sb.append("<div class='form-group mb-3'><label for='simpleinput'>영업 오픈시간</label> "
				+ "<input type='time' class='form-control' name='work_o' id='am_open' value='" + am_open
				+ "' step='1800' min='" + am_open + "' max='" + pm_close + "' required></div>");
		sb.append("<div class='form-group mb-3'><label for='simpleinput'>영업 마감시간</label> "
				+ "<input type='time' class='form-control' name='work_c' id='pm_close' value='" + pm_close
				+ "' step='1800' min='" + am_open + "' max='" + pm_close + "' required></div>");

		return sb.toString();
	}

	// 직원 점심시간 확인 메소드
	private String checkLunchTime(HttpServletRequest request, HttpSession session) {
		String no = (String) session.getAttribute("no");
		String code = request.getParameter("bct_code");
		Map<String, Object> map = new HashMap<String, Object>();
		if (code.equals("병원")) {
			code = "M";
		} else if (code.equals("미용")) {
			code = "B";
		} else if (code.equals("호텔")) {
			code = "H";
		}
		map.put("bus_no", no);
		map.put("bct_code", code);

		map = bDao.searchBSD(map);

		String am_close = (String) map.get("AM_CLOSE");
		String pm_open = (String) map.get("PM_OPEN");

		String lunch_open_pre = am_close.substring(0, 2); // 오픈시간 hour
		String lunch_open_las = am_close.substring(2, 4); // 오픈시간 min
		String lunch_close_pre = pm_open.substring(0, 2); // 마감시간 hour
		String lunch_close_las = pm_open.substring(2, 4); // 마감시간 min

		am_close = lunch_open_pre + ":" + lunch_open_las;
		pm_open = lunch_close_pre + ":" + lunch_close_las;

		StringBuilder sb = new StringBuilder();
		sb.append("<div class='form-group mb-3'><label for='simpleinput'>점심 시작시간</label> "
				+ "<input type='time' class='form-control' name='lunch_o' id='lunch_open' value='" + am_close
				+ "' step='1800' min='" + am_close + "' max='" + pm_open + "' required></div>");
		sb.append("<div class='form-group mb-3'><label for='simpleinput'>점심 마감시간</label> "
				+ "<input type='time' class='form-control' name='lunch_c' id='lunch_close' value='" + pm_open
				+ "' step='1800' min='" + am_close + "' max='" + pm_open + "' required></div>");

		return sb.toString();
	}

	// 직원 고정스케줄 확인 메소드
	public String Holiday(String bct_code, String type, HttpSession session) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		String bus_no = (String) session.getAttribute("no");
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		map = bDao.searchBFX(map); // 기업의 고정 스케줄을 검색
		sb.append("<div class=\"form-group mb-3\">");
		sb.append("<label for=\"simpleinput\">고정휴무일</label><br />");
		if (map != null) {
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("월요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='월요일' id='월요일' checked onclick='return false;'/> <label class='custom-control-label' for='월요일'>월요일</label>&nbsp;");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='월요일' id='월요일' /> <label class='custom-control-label' for='월요일'>월요일</label>&nbsp;");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("화요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='화요일' id='화요일' checked onclick='return false;'/> <label class='custom-control-label' for='화요일'>화요일</label>&nbsp;");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='화요일' id='화요일' /> <label class='custom-control-label' for='화요일'>화요일</label>&nbsp;");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("수요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='수요일' id='수요일' checked onclick='return false;'/> <label class='custom-control-label' for='수요일'>수요일</label>&nbsp;");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='수요일' id='수요일' /> <label class='custom-control-label' for='수요일'>수요일</label>&nbsp;");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("목요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='목요일' id='목요일' checked onclick='return false;'/> <label class='custom-control-label' for='목요일'>목요일</label>&nbsp;");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='목요일' id='목요일' /> <label class='custom-control-label' for='목요일'>목요일</label>&nbsp;");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("금요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='금요일' id='금요일' checked onclick='return false;'/> <label class='custom-control-label' for='금요일'>금요일</label>&nbsp;");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='금요일' id='금요일' /> <label class='custom-control-label' for='금요일'>금요일</label>&nbsp;");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("토요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='토요일' id='토요일' checked onclick='return false;'/> <label class='custom-control-label' for='토요일'>토요일</label>&nbsp;");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='토요일' id='토요일' /> <label class='custom-control-label' for='토요일'>토요일</label>&nbsp;");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("일요일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='일요일' id='일요일' checked onclick='return false;'/> <label class='custom-control-label' for='일요일'>일요일</label>&nbsp;");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='일요일' id='일요일' /> <label class='custom-control-label' for='일요일'>일요일</label>&nbsp;");
			}
			sb.append("</div>");
			sb.append("<div class='custom-control custom-checkbox'>");
			if (map.get("공휴일").equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='공휴일' id='공휴일' checked onclick='return false;'/> <label class='custom-control-label' for='공휴일'>공휴일</label>");
			} else {
				sb.append(
						"<input type='checkbox' class='custom-control-input' name='holiday' value='공휴일' id='공휴일' /> <label class='custom-control-label' for='공휴일'>공휴일</label>&nbsp;");
			}
			sb.append("</div>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	// 업종 수정시 가격 검사 메소드
	public String searchPrice(HttpServletRequest request, HttpSession session) {
		String tag_name = request.getParameter("tag_name");
		String ani_name = request.getParameter("ani_name");
		String bus_no = (String) session.getAttribute("no");
		String bct_code = request.getParameter("bct_code");
		System.out.println(bus_no);
		System.out.println(tag_name);
		System.out.println(ani_name);
		System.out.println(bct_code);
		System.out.println("왜 코드 안나와 ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tag_name", tag_name);
		map.put("bct_code", bct_code);
		String tag_no = bDao.searchMENUNO(map);
		System.out.println(tag_no);
		String ani_no = bDao.searchPTYNO(ani_name);
		System.out.println(ani_no);
		map.put("tag_no", tag_no);
		map.put("ani_no", ani_no);
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		String price = bDao.searchPRC(map);
		if (price == null) {
			price = "0";
		}
		System.out.println(price);
		return price;
	}
	
	/* 기업 상세페이지 - 즐겨찾기 추가 삭제 */
	public int favoriteChange(HttpServletRequest request) {
		int result = 0;
		HashMap<String, Object> hmap = new HashMap<>();
		hmap.put("per_no", request.getParameter("per_no"));
		hmap.put("bus_no", request.getParameter("bus_no"));
		String action = request.getParameter("action");
		if (action.equals("insert")) {
			result = bDao.favoriteInsert(hmap);
		}
		if (action.equals("delete")) {
			result = bDao.favoriteDelete(hmap);
		}
		return result;
	}
	
	public ModelAndView businessDetailPage(HttpSession session, HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		HashMap<String, Object> hmap = new HashMap<>();
		List<HashMap<String, Object>> serviceList = new ArrayList<HashMap<String, Object>>();

		String bus_no = request.getParameter("bus_no");
		String bct_code = request.getParameter("bct_code");
		String bsd_date = request.getParameter("bsd_date");

		// 해시맵에 쿼리스트링과 회원번호를 담는다
		hmap.put("bus_no", bus_no);
		hmap.put("bct_code", bct_code);

		String no = null;

		// 회원번호
		if (session.getAttribute("no") != null) {
			no = session.getAttribute("no").toString();
			hmap.put("no", no);

			// 즐겨찾기
			int favorite = 0;
			char code = no.charAt(0);
			String favResult = null;
			if (code == 'P') {
				favResult = bDao.getFavorite(hmap);
				if (favResult != null)
					favorite = 1;
			}

			mav.addObject("no", no);
			mav.addObject("code", code);
			mav.addObject("favorite", favorite);

		}

		// 기업명을 가져온다
		String bus_name = bDao.getBusName(hmap);
		// 업종명을 가져온다
		String bct_name = bDao.getBctName(hmap);
		// 기업대표이미지를 가져온다
		hmap = bDao.getBusinessImage(hmap);
		String bus_img = hmap.get("GLR_LOC").toString() + hmap.get("GLR_FILE").toString();

		// 기업이 제공하는 모든 서비스를 가져온다
		serviceList = bDao.getHaveService(bus_no);
		mav.addObject("bus_no", bus_no);
		mav.addObject("bsd_date", bsd_date);
		mav.addObject("bct_code", bct_code);
		mav.addObject("bus_img", bus_img);
		mav.addObject("bus_name", bus_name);
		mav.addObject("bct_name", bct_name);
		mav.addObject("serviceList", serviceList);
		view = "businessDetailPage";
		mav.setViewName(view);
		return mav;
	}
	
	public int nowPwCheck(Business b, HttpServletRequest request) {
		b.setBus_no(request.getSession().getAttribute("no").toString());
		int result = bDao.nowPwCheck(b);
		return result;
	}

	public void businessPwUpdate(Business b, HttpServletRequest request) {
		b.setBus_no(request.getSession().getAttribute("no").toString());
		bDao.businessPwUpdate(b);
	}

	public ModelAndView businessInfoUpdateForm(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Business b = new Business();
		b.setBus_no(request.getSession().getAttribute("no").toString());
		b = bDao.businessInfoUpdateForm(b);
		mav.addObject("b", b);
		return mav;
	}
	
	// 새로운 예약 목록
	public ModelAndView newScheduleList(HttpServletRequest request) {
		String bus_no = request.getSession().getAttribute("no").toString();
		List<HashMap<String, String>> list = bDao.selectBooking(bus_no);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append("<tr><td><a href='myBookingDetail?" + list.get(i).get("BK_NO") + "'>" + list.get(i).get("BK_NO"));
			sb.append("</a></td><td>" + list.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + list.get(i).get("PET_NAME") + "</td><td>" + list.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + list.get(i).get("BCT_NAME") + "</td><td>" + list.get(i).get("VS_START") + "</td>");
			sb.append("<td><span id='" + list.get(i).get("BK_NO") + "'>");
			sb.append("<input type='button' class='btn-outline-success' value='확정' name='" + list.get(i).get("BK_NO")
					+ "' />&nbsp;");
			sb.append("<input type='button' class='btn-outline-danger' value='거절' name='" + list.get(i).get("BK_NO")
					+ "' /></span></td></tr>");
		} // for End
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", sb.toString());
		return mav;
	} // method End
	
}
