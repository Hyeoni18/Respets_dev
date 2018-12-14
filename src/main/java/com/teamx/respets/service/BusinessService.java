package com.teamx.respets.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

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
			menu = bDao.getMenu(bk_no); //서비스 메뉴 불러오기
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
}
