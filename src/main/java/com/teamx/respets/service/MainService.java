package com.teamx.respets.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.AdminBoard;
import com.teamx.respets.bean.Booking;
import com.teamx.respets.bean.Business;
import com.teamx.respets.dao.MainDao;
import com.teamx.respets.userClass.Paging;

@Service
public class MainService {

	@Autowired
	private MainDao mDao;
	ModelAndView mav;

	// 메인 페이지 서비스 종류, 공지사항 갖고 오기
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		// 서비스 종류 갖고 오기
		List<HashMap<String, String>> list = mDao.selectBusCategory();
		StringBuilder sb = new StringBuilder();
		// option 태그 생성
		for (int i = 0; i < list.size(); i++) {
			sb.append("<option value='" + list.get(i).get("BCT_CODE") + "'>");
			sb.append(list.get(i).get("BCT_NAME") + "</option>");
		} // for End
		mav.addObject("bct", sb);
		// 공지사항 갖고 오기
		List<AdminBoard> aboList = mDao.selectBoardList();
		mav.addObject("list", aboList);
		return mav;
	} // method End

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
			/*
			 * sb.append("<td><a href='./businessNoticeDetail?" + nList.get(i).get("BBO_NO")
			 * + "'>" + nList.get(i).get("BBO_TITLE") + "</a></td>");
			 */
			sb.append("<td><a href='#' data-toggle='modal' data-target='#B" + nList.get(i).get("BBO_NO") + "'>"
					+ nList.get(i).get("BBO_TITLE") + "</a></td>");
			sb.append("<div id=\"B" + nList.get(i).get("BBO_NO") + "\" class=\"modal fade\" tabindex=\"-1\"\r\n"
					+ "															role=\"dialog\" aria-labelledby=\"myModalLabel\"\r\n"
					+ "															aria-hidden=\"true\">\r\n"
					+ "															<div class=\"modal-dialog modal-dialog-centered\">\r\n"
					+ "																<div class=\"modal-content\">\r\n"
					+ "																	<div class=\"modal-header\">\r\n"
					+ "																		\r\n"
					+ "																		<h4 class=\"modal-title\" id=\"myModalLabel\">"
					+ nList.get(i).get("BBO_TITLE") + "</h4>\r\n"
					+ "																		<div class=\"badge badge-secondary\" style=\"margin-top:5px;margin-left:10px\">\r\n");

			if (nList.get(i).get("BBC_NAME").equals("공지사항"))
				sb.append("공지사항");
			if (nList.get(i).get("BBC_NAME").equals("이벤트"))
				sb.append("이벤트");

			sb.append("																			</div>\r\n"
					+ "																		<button type=\"button\" class=\"close\"\r\n"
					+ "																			data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\r\n"
					+ "																	</div>\r\n"
					+ "																	<div class=\"modal-body\">\r\n"
					+ "																		<p>"
					+ nList.get(i).get("BBO_CTT") + "</p>\r\n"
					+ "																	</div>\r\n"
					+ "																</div>\r\n"
					+ "															</div>\r\n"
					+ "														</div>");
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

	// 주소선택 후 기업리스트 첫번째 메소드
	public ModelAndView searchList(HttpServletRequest request, Integer pageNum) {
		System.out.println("주소입력후 첫메소드 첫번째,");
		mav = new ModelAndView();
		String bct_code = request.getParameter("bct_code");
		String city = request.getParameter("city");
		String four = null;
		if (city.length() == 4) {
			four = city.substring(0, 1);
			four += city.substring(2, 3);
			city = four;
		}
		System.out.println(city);
		String bk_date = request.getParameter("bk_date");
		String year = bk_date.substring(8, 10);
		String day = bk_date.substring(3, 5);
		String month = bk_date.substring(0, 2);
		String beday = year + month + day;
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		DateFormat outputFormat = new SimpleDateFormat("yy/MM/dd", Locale.KOREA);
		Date inputDate = null;
		String outputDate = null;
		try {
			inputDate = format.parse(beday);
			outputDate = outputFormat.format(inputDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int pNo = (pageNum == null) ? 1 : pageNum; // 페이지넘버
		System.out.println(outputDate);
		String tagList = tagList(outputDate, bct_code, city, 1);
		String busiList = busiList(outputDate, bct_code, city, pNo);
		String paging = busiListPaging(pNo, outputDate, bct_code, city);
		mav.addObject("busiList", busiList);
		mav.addObject("tagList", tagList);
		mav.addObject("paging", paging);
		mav.setViewName("businessList");
		return mav;
	}

	// 주소선택 후 기업리스트 두번째 메소드 (페이지 구성)
	private String busiList(String outputDate, String bct_code, String city, int pNo) {
		System.out.println("주소입력기업리스트불러오기 두번째,");
		StringBuilder sb = new StringBuilder();
		List<Map<String, Object>> list;
		Map<String, Object> mapo = new HashMap<String, Object>();
		mapo.put("bct_code", bct_code);
		mapo.put("bsd_date", outputDate);
		mapo.put("bus_addr", city);
		mapo.put("pageNum", pNo);
		System.out.println(outputDate);
		list = mDao.searchBUSaddr(mapo);
		Map<String, Object> map = new HashMap<String, Object>();
		if (list.size() != 0) {
			int count = 0;
			boolean flag = true;
			for (int i = 0; i < list.size(); i++) {
				String bus_no = (String) list.get(i).get("BUS_NO");
				System.out.println(bus_no);
				String bus_name = (String) list.get(i).get("BUS_NAME");
				String bus_addr = (String) list.get(i).get("BUS_ADDR");
				map = new HashMap<String, Object>();
				map.put("bus_no", bus_no);
				map.put("gct_no", "2");
				map = mDao.selectGallery(map);
				if (map != null) {
					if (count == 0 || count % 3 == 0) {
						sb.append("<div class='row'>");
						sb.append("<div class='col-12'>");
						sb.append("<div class='card-deck-wrapper'>");
						sb.append("<div class='card-deck'>");
						flag = false;
					}
					String glr_file = (String) map.get("GLR_FILE");
					String glr_loc = (String) map.get("GLR_LOC");
					sb.append("<div class='card d-block'>");
					sb.append("<a href='businessDetailPage?bus_no=" + bus_no + "&bct_code=" + bct_code + "&bsd_date="
							+ mapo.get("bsd_date") + "'>");
					sb.append("<img class='card-img-top img-fluid' src='" + glr_loc + glr_file + "'/>");
					sb.append("<div class='card-body'>");
					sb.append("<h5 class='card-title'> " + bus_name + "</h1>");
					sb.append("<h5 class='card-text'> 주소: " + bus_addr + "</h2>");
					sb.append("</div>");
					sb.append("</a>");
					sb.append("</div>");
					if (count % 3 == 2) {
						sb.append("</div>");
						sb.append("</div>");
						sb.append("</div>");
						sb.append("</div>");
						flag = true;
					}
					count++;
				}

			}
			if (count % 3 < 3 && count % 3 != 0) {
				for (int i = 0; i < 3 - count % 3; i++) {
					sb.append("<div class='card d-block'>");
					sb.append("<div class='card-body'>");
					sb.append("</div></div>");
				}
			}
			if (flag != true) {
				sb.append("</div>");
				sb.append("</div>");
				sb.append("</div>");
				sb.append("</div>");
			}
		}
		return sb.toString();
	}

	// 주소선택 후 기업리스트 세번째 메소드 (페이징 표현)
	private String busiListPaging(int pNo, String outputDate, String bct_code, String city) {
		System.out.println("주소입력기업리스트불러오기, 페이징 부분");
		System.out.println(bct_code);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bus_addr", city);
		map.put("bsd_date", outputDate);
		map.put("bct_code", bct_code);
		int maxNum = mDao.countBusiList(map);
		int listCount = 9;
		int pageCount = 5;
		String boardName = "searchList";
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.businessListPaging(map);
	}

	// 주소선택 후 기업리스트 네번째 메소드 (태그 불러오기)
	private String tagList(String outputDate, String bct_code, String city, int pNo) {
		System.out.println("주소입력태그불러오기 세번째,");
		StringBuilder sb = new StringBuilder();
		List<Map<String, Object>> list;
		list = mDao.selectTAG(bct_code);
		pNo = 1;
		for (int i = 0; i < list.size(); i++) {
			String tag_no = (String) list.get(i).get("TAG_NO");
			String tag_name = mDao.changeTAG(tag_no);
			sb.append(
					"<span>&nbsp;&nbsp;</span><a class='btn btn-link btn-rounded' href='javascript:void(0)' onclick=\"businessList('"
							+ bct_code + "','" + tag_no + "','" + outputDate + "','" + city + "','" + pNo + "')\">"
							+ tag_name + "</a>");
		}
		return sb.toString();
	}

	// 업종버튼선택 후 기업리스트 첫번째 메소드 (페이지 구성)
	public ModelAndView businessList(HttpServletRequest request, Integer pageNum) {
		System.out.println("버튼누르면 첫 메소드 첫번째,");
		System.out.println("들어오는ㄴ지?");
		int pNo = (pageNum == null) ? 1 : pageNum;
		mav = new ModelAndView();
		List<Map<String, Object>> list;
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		String bct_code = request.getParameter("bct_code");
		map.put("bct_code", bct_code);
		map.put("pageNum", pNo);
		list = mDao.selectSVCcode(map);
		if (list.size() != 0) {
			sb.append("<div class='row'>");
			sb.append("<div class='col-12'>");
			sb.append("<div class='card-deck-wrapper'>");
			sb.append("<div class='card-deck'>");
			for (int i = 0; i < list.size(); i++) {
				map = new HashMap<String, Object>();
				map.put("bct_code", bct_code);
				map.put("bus_no", (String) list.get(i).get("BUS_NO"));
				String bfx = mDao.searchBFX(map);
				if (bfx != null) {
					String bus_no = (String) list.get(i).get("BUS_NO");
					String bus_name = (String) list.get(i).get("BUS_NAME");
					String bus_addr = (String) list.get(i).get("BUS_ADDR");
					map = new HashMap<String, Object>();
					map.put("bus_no", bus_no);
					map.put("gct_no", "2");
					map = mDao.selectGallery(map);
					if (map != null) {
						String glr_file = (String) map.get("GLR_FILE");
						String glr_loc = (String) map.get("GLR_LOC");

						sb.append("<div class='col-lg-4'>");
						sb.append("<div class='card d-block' style='margin: 0 0 20px 0;'>");
						sb.append("<a href='businessDetailPage?bus_no=" + bus_no + "&bct_code=" + bct_code + "'>");
						sb.append("<img class='card-img-top img-fluid' src='" + glr_loc + glr_file
								+ "' style='width:100%;height:13rem;'/>");
						sb.append("<div class='card-body'>");
						sb.append("<h5 class='card-title'> " + bus_name + "</h1>");
						sb.append("<h5 class='card-text'> 주소: " + bus_addr + "</h2>");
						sb.append("</div>");
						sb.append("</a>");
						sb.append("</div>");
						sb.append("</div>");

					}
				}
			}
			sb.append("</div>");
			sb.append("</div>");
			sb.append("</div>");
			sb.append("</div>");
			mav.addObject("busiList", sb.toString());
		}
		String tag = butTagList(bct_code, 1);
		mav.addObject("tagList", tag);
		String paging = busiListCodePaging(pNo, bct_code);
		mav.addObject("paging", paging);
		mav.setViewName("businessList");
		return mav;
	}

	// 업종버튼선택 후 기업리스트 두번째 메소드 (태그 불러오기)
	private String butTagList(String bct_code, int pNo) {
		System.out.println("버튼태그리스트,함수로 보내기 두번째,");
		StringBuilder sb = new StringBuilder();
		List<Map<String, Object>> list;
		list = mDao.selectTAG(bct_code);
		for (int i = 0; i < list.size(); i++) {
			String tag_no = (String) list.get(i).get("TAG_NO");
			String tag_name = mDao.changeTAG(tag_no);
			sb.append(/*
						 * "<span>&nbsp;&nbsp;</span><a class='btn btn-outline-secondary' href='javascript:void(0)' onclick=\"butTagSelectList('"
						 * + bct_code + "','" + tag_no + "','" + pNo + "')\">" + tag_name + "</a>" +
						 */"<a href='javascript:void(0)' onclick=\"butTagSelectList('" + bct_code + "','" + tag_no
					+ "','" + pNo + "')\" class='btn btn-outline-light btn-rounded' style='margin-right:10px;'>"
					+ tag_name + "</a>");
		}
		return sb.toString();
	}

	// 업종버튼선택 후 기업리스트 세번째 메소드 (페이징 표현)
	private String busiListCodePaging(int pNo, String bct_code) {
		System.out.println("버튼클릭기업리스트불러오기, 페이징 부분");
		System.out.println(bct_code);
		int maxNum = mDao.countBusiButList(bct_code);
		int listCount = 9;
		int pageCount = 5;
		String boardName = "businessList";
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.businessListButPaging(bct_code);
	}

	// 주소선택 후 기업리스트에서 태그선택시 첫번째 메소드 (페이지 구성)
	public String tagSelectList(HttpServletRequest request) {
		String bct_code = request.getParameter("bct_code");
		String tag_no = request.getParameter("tag_no");
		String bsd_date = request.getParameter("bsd_date");
		String bus_addr = request.getParameter("bus_addr");
		Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int pNo = (pageNum == null) ? 1 : pageNum; // 아마 필요없으껄
		StringBuilder sb = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list;
		map.put("bct_code", bct_code);
		map.put("bsd_date", bsd_date);
		map.put("bus_addr", bus_addr);
		map.put("pageNum", pNo);
		map.put("tag_no", tag_no);
		list = mDao.searchBUSaddrTag(map);
		map = new HashMap<String, Object>();
		if (list.size() != 0) {
			int count = 0;
			boolean flag = true;
			for (int i = 0; i < list.size(); i++) {
				String bus_no = (String) list.get(i).get("BUS_NO");
				System.out.println(bus_no);
				String bus_name = (String) list.get(i).get("BUS_NAME");
				bus_addr = (String) list.get(i).get("BUS_ADDR");
				map = new HashMap<String, Object>();
				map.put("bus_no", bus_no);
				map.put("gct_no", "2");
				map = mDao.selectGallery(map);
				if (map != null) {
					if (count == 0 || count % 3 == 0) {
						sb.append("<div class='row'>");
						sb.append("<div class='col-12'>");
						sb.append("<div class='card-deck-wrapper'>");
						sb.append("<div class='card-deck'>");
						flag = false;
					}
					String glr_file = (String) map.get("GLR_FILE");
					String glr_loc = (String) map.get("GLR_LOC");
					sb.append("<div class='card d-block'>");
					sb.append("<a href='businessDetailPage?bus_no=" + bus_no + "&bct_code=" + bct_code + "'>");
					sb.append("<img class='card-img-top img-fluid' src='" + glr_loc + glr_file + "'/>");
					sb.append("<div class='card-body'>");
					sb.append("<h5 class='card-title'> " + bus_name + "</h1>");
					sb.append("<h5 class='card-text'> 주소: " + bus_addr + "</h2>");
					sb.append("</div>");
					sb.append("</a>");
					sb.append("</div>");
					if (count % 3 == 2) {
						sb.append("</div>");
						sb.append("</div>");
						sb.append("</div>");
						sb.append("</div>");
						flag = true;
					}
					count++;
				}

			}
			if (count % 3 < 3 && count % 3 != 0) {
				for (int i = 0; i < 3 - count % 3; i++) {
					sb.append("<div class='card d-block'>");
					sb.append("<div class='card-body'>");
					sb.append("</div></div>");
				}
			}
			if (flag != true) {
				sb.append("</div>");
				sb.append("</div>");
				sb.append("</div>");
				sb.append("</div>");
			}
		}
		return sb.toString();
	}

	// 주소선택 후 기업리스트에서 태그선택시 두번째 메소드 (페이징 표현)
	public String tagSelectListAddr(HttpServletRequest request) {
		String bct_code = request.getParameter("bct_code");
		String tag_no = request.getParameter("tag_no");
		String bsd_date = request.getParameter("bsd_date");
		String bus_addr = request.getParameter("bus_addr");
		Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int pNo = (pageNum == null) ? 1 : pageNum;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bct_code", bct_code);
		map.put("tag_no", tag_no);
		map.put("bsd_date", bsd_date);
		map.put("bus_addr", bus_addr);
		int maxNum = mDao.countSearchBUSaddr(map);
		int listCount = 9;
		int pageCount = 5;
		String boardName = "businessList";
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.businessListTagPaging(map);
	}

	// 업종버튼선택 후 기업리스트에서 태그선택시 첫번쨰 메소드 (페이지 구성)
	public String butTagSelectList(HttpServletRequest request, Integer pageNum) {
		System.out.println("버튼태그셀렉리스트, 리스트 띄워주기, 세번째 ");
		String bct_code = request.getParameter("bct_code");
		String tag_no = request.getParameter("tag_no");
		int pNo = (pageNum == null) ? 1 : pageNum;
		StringBuilder sb = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list;
		map.put("bct_code", bct_code);
		map.put("pageNum", pNo);
		map.put("tag_no", tag_no);
		list = mDao.butTagSelectList(map);
		if (list.size() != 0) {
			sb.append("<div class='row'>");
			sb.append("<div class='col-12'>");
			sb.append("<div class='card-deck-wrapper'>");
			sb.append("<div class='card-deck'>");
			for (int i = 0; i < list.size(); i++) {
				String bus_no = (String) list.get(i).get("BUS_NO");
				String bus_name = (String) list.get(i).get("BUS_NAME");
				String bus_addr = (String) list.get(i).get("BUS_ADDR");
				map = new HashMap<String, Object>();
				map.put("bus_no", bus_no);
				map.put("gct_no", "2");
				map = mDao.selectGallery(map);
				if (map != null) {
					String glr_file = (String) map.get("GLR_FILE");
					String glr_loc = (String) map.get("GLR_LOC");
					sb.append("<div class='col-lg-4'>");
					sb.append("<div class='card d-block' style='margin: 0 0 20px 0;'>");
					sb.append("<a href='businessDetailPage?bus_no=" + bus_no + "&bct_code=" + bct_code + "'>");
					sb.append("<img class='card-img-top img-fluid' src='" + glr_loc + glr_file
							+ "' style='width:100%;height:13rem;'/>");
					sb.append("<div class='card-body'>");
					sb.append("<h5 class='card-title'> " + bus_name + "</h1>");
					sb.append("<h5 class='card-text'> 주소: " + bus_addr + "</h2>");
					sb.append("</div>");
					sb.append("</a>");
					sb.append("</div>");
					sb.append("</div>");
				}
			}
			sb.append("</div>");
			sb.append("</div>");
			sb.append("</div>");
			sb.append("</div>");
		}
		return sb.toString();
	}

	// 업종버튼선택 후 기업리스트에서 태그선택시 두번째 메소드 (페이징표현)
	public String butTagSelectListPaging(HttpServletRequest request, Integer pageNum) {
		System.out.println("버튼클릭기업리스트불러오기, 페이징 부분");
		String bct_code = request.getParameter("bct_code");
		String tag_no = request.getParameter("tag_no");
		int pNo = (pageNum == null) ? 1 : pageNum;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bct_code", bct_code);
		map.put("tag_no", tag_no);
		int maxNum = mDao.countButTagSelectList(map);
		int listCount = 9;
		int pageCount = 5;
		String boardName = "businessList"; /// 필요없어
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.butTagSelectListPaging(map);
	}

	public int nowPwCheck(Business b, HttpServletRequest request) {
		b.setBus_no(request.getSession().getAttribute("no").toString());
		int result = mDao.nowPwCheck(b);
		return result;
	}

	// 예약 페이지
	public ModelAndView bookingForm(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, String> hMap = new HashMap<String, String>();

		// 쿼리스트링을 HashMap에 저장
		if (request.getQueryString() != null) {
			String queryString = request.getQueryString();
			// &을 기준으로 나눠줌
			String[] parameters = queryString.split("&");
			// =을 기준으로 앞에 있는 건 key, 뒤에 있는 건 value
			for (String parameter : parameters) {
				String name = parameter.split("=")[0];
				String value = parameter.split("=")[1];
				hMap.put(name, value);
			} // for End
		} // if End

		// 세션에서 예약하는 개인 회원 번호 갖고 오기
		hMap.put("per_no", request.getSession().getAttribute("no").toString());
		Map<String, String> petMap = new HashMap<String, String>();

		// 쿼리스트링에 반려동물 정보가 없을 경우
		if (hMap.get("pet_no") == null) {
			// Dao에서 제일 첫 번째 반려동물을 기본 정보로 보여준다.
			petMap = mDao.selectFirstPet(hMap);
			// 개인 회원이 등록한 반려동물이 없을 경우
			if (petMap == null) {
				mav.addObject("noPet", "noPet");
				return mav;
			} // if End
		} else {
			// 아닐 경우 쿼리스트링에 있는 반려동물(선택한 반려동물)을 기본 정보로 보여준다.
			petMap = mDao.firstPet(hMap);
		} // if End

		// 현재 예약하는 반려동물 번호를 다른 HashMap에 저장
		hMap.put("pet_no", petMap.get("PET_NO"));

		// 메인 페이지에서 검색으로 왔을 경우 date가 있지만, 없을 경우 null 오류가 뜨지 않게 noDate를 넣어준다.
		if (hMap.get("date") == null) {
			hMap.put("date", "noDate");
		} // if End

		// 예약 선택한(사진이 뜨는) 반려동물 정보 html 만들기
		StringBuilder pet = new StringBuilder();
		pet.append("<img class='card-img-top' src='" + petMap.get("PET_LOC") + petMap.get("PET_PHOTO"));
		pet.append("' style='height:300px; weight:300px;' />");
		pet.append("<div class='card-body'>");
		pet.append("<p style='text-align:center;'>이름: " + petMap.get("PET_NAME") + "</p>");
		pet.append("<input type='hidden' name='bus_no' value='" + hMap.get("bus_no") + "' />");
		pet.append("<input type='hidden' name='bct_code' value='" + hMap.get("bct_code") + "' />");
		pet.append("<input type='hidden' name='pet_no' value='" + petMap.get("PET_NO") + "' />");
		pet.append("</div>");

		// 선택한 반려동물을 제외한 나머지 반려동물 리스트 html 만들기 (클릭 시 해당 반려동물로 예약 페이지 전환)
		List<HashMap<String, String>> petList = mDao.selectPetList(petMap);
		pet.append(
				"<hr/><div style='text-align: center'><p class='text-success' style='text-align: center' >다른 반려동물 선택</p><hr/></div>");
		// 여러 마리일 경우를 위해 for문
		for (int i = 0; i < petList.size(); i++) {
			pet.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='./bookingForm?bus_no="
					+ hMap.get("bus_no"));
			pet.append("&bct_code=" + hMap.get("bct_code"));
			pet.append("&date=" + hMap.get("date") + "&pet_no=" + petList.get(i).get("PET_NO"));
			pet.append("'> " + petList.get(i).get("PET_NAME") + "</a><br/>");
		} // for End
		mav.addObject("petList", pet);

		// DB에서 해당 기업의 서비스 선택 갖고 오기
		List<HashMap<String, String>> svcList = mDao.selectSvcList(hMap);

		// 서비스 선택 체크박스 html 만들기
		StringBuilder svc = new StringBuilder();
		svc.append("<hr/><h5 class='text-success'>서비스 선택</h5>");
		for (int i = 0; i < svcList.size(); i++) {
			svc.append("<input type='checkbox' name='menu_no' value='");
			svc.append(String.valueOf(svcList.get(i).get("MENU_NO")) + "' />");
			svc.append(svcList.get(i).get("MENU_NAME"));
			// 가격이 있는 서비스의 경우 가격을 붙이기 위해 if문
			if (!svcList.get(i).get("PRICE").equals("0")) {
				svc.append(" (" + svcList.get(i).get("PRICE") + " 원)");
			} // if End
			svc.append("<br/>");
			svc.append("<input type='hidden' name='" + String.valueOf(svcList.get(i).get("MENU_NO")));
			svc.append("' value='" + svcList.get(i).get("PRICE") + "' />");
		} // for End
		mav.addObject("svcList", svc);

		// 해당 기업의 직원 리스트를 갖고 오기
		List<HashMap<String, String>> empList = mDao.selectEmpList(hMap);

		// 직원 선택 radio 버튼 html 만들기
		StringBuilder emp = new StringBuilder();
		emp.append("<br/><br/<br/><hr/><h5 class='text-success'>직원 선택</h5>");
		for (int i = 0; i < empList.size(); i++) {
			emp.append("<input type='radio' name='emp_no' value='");
			emp.append(empList.get(i).get("EMP_NO") + "' /> ");
			emp.append(empList.get(i).get("EMP_NAME") + " " + empList.get(i).get("EMP_POS"));
			emp.append(" (" + empList.get(i).get("EMP_PART") + ")<br/>");
		} // for End
		mav.addObject("empList", emp);

		Date date = null;
		// 만약 hMap(쿼리스트링을 담은 HashMap)에 date가 null이 아니거나, "noDate"가 아닐 경우 = 검색을 통해 정해진 날짜가 있을 경우
		if (hMap.get("date") != null && !hMap.get("date").equals("noDate")) {
			try {
				date = new SimpleDateFormat("yy/MM/dd").parse(hMap.get("date"));
			} catch (ParseException e) {
				e.printStackTrace();
			} // catch End
		} else { // 날짜 값이 없을 경우 오늘로
			date = new Date();
		} // else End
		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(date); // 위에서 정한 날짜로 set
		StringBuilder dayList = new StringBuilder();
		dayList.append("<tr>");
		
		// 정해진 날짜에서 일주일 선택지를 만들기 위한 for문
		for (int i = 0; i < 7; i++) { // 일주일이라서 7번 돈다
			// 요일 갖고 오기 (해당 날짜의 요일을 int로 반환한다)
			int day = cal.get(Calendar.DAY_OF_WEEK);
			// radio value 값을 위한 yyMMdd 포맷
			SimpleDateFormat valSdf = new SimpleDateFormat("yyMMdd"); 
			SimpleDateFormat sdf = null; // 사용자에게 보여주기 위한 포맷
			if (day == 1) {
				sdf = new SimpleDateFormat("MM월 dd일 일요일");
			} else if (day == 2) {
				sdf = new SimpleDateFormat("MM월 dd일 월요일");
			} else if (day == 3) {
				sdf = new SimpleDateFormat("MM월 dd일 화요일");
			} else if (day == 4) {
				sdf = new SimpleDateFormat("MM월 dd일 수요일");
			} else if (day == 5) {
				sdf = new SimpleDateFormat("MM월 dd일 목요일");
			} else if (day == 6) {
				sdf = new SimpleDateFormat("MM월 dd일 금요일");
			} else if (day == 7) {
				sdf = new SimpleDateFormat("MM월 dd일 토요일");
			} // else if End
			String strDate = sdf.format(cal.getTime()); //yyMMdd 포맷
			String valDate = valSdf.format(cal.getTime()); // MM월 dd일 x요일 포맷
			dayList.append("<td><input type='radio' name='day' value='" + valDate + "' />");
			dayList.append(strDate + "</td>");
			cal.add(Calendar.DAY_OF_YEAR, 1); // 하루 추가
		} // for End
		dayList.append("</tr>");
		mav.addObject("dayList", dayList);
		return mav;
	} // method End

	// 예약 페이지 시간
	public String timeSelect(HttpServletRequest request) {
		
		HashMap<String, String> hMap = new HashMap<String, String>();
		
		// 선택된 날짜와 직원 번호
		hMap.put("date", request.getParameter("date"));
		hMap.put("emp_no", request.getParameter("emp"));
		
		// 선택된 직원의 선택된 날짜 스케줄 갖고 오기
		HashMap<String, String> timeMap = mDao.selectEmpTime(hMap);
		String timeList = "";
		
		// 다른 예약이 된 시간 DB에서 갖고 오기
		List<HashMap<String, String>> noTimeList = mDao.selectNoTime(hMap);
		
		if (timeMap == null) { // DB에 해당 날짜의 스케줄이 없을 때
			timeList = "직원 휴무일입니다.";
		} else { // DB에 시간 있을 때
			
			int amOpenHour = Integer.parseInt(timeMap.get("AM_OPEN").substring(0, 2));
			int amOpenMin = Integer.parseInt(timeMap.get("AM_OPEN").substring(2, 4));
			int amCloseHour = Integer.parseInt(timeMap.get("AM_CLOSE").substring(0, 2));
			int amCloseMin = Integer.parseInt(timeMap.get("AM_CLOSE").substring(2, 4));
			int pmOpenHour = Integer.parseInt(timeMap.get("PM_OPEN").substring(0, 2));
			int pmOpenMin = Integer.parseInt(timeMap.get("PM_OPEN").substring(2, 4));
			int pmCloseHour = Integer.parseInt(timeMap.get("PM_CLOSE").substring(0, 2));
			int pmCloseMin = Integer.parseInt(timeMap.get("PM_CLOSE").substring(2, 4));
			
			// 오늘일 때
			if (hMap.get("date").equals(new SimpleDateFormat("yyMMdd").format(new Date()))) {
				
				// 오늘 현재 시각
				String todayTime = new SimpleDateFormat("HHmm").format(new Date());
				int todayHour = Integer.parseInt(todayTime.substring(0, 2));
				int todayMin = Integer.parseInt(todayTime.substring(2, 4));
				String strHour = "";
				String strMin = "";
				
				/* 현재 시각보다 1시간 30분 이후부터 예약이 가능하도록 구현 */
				
				// 현재 시각 '분'이 30분보다 미만일 때
				if (todayMin < 30) {
					strMin = "30"; // 30분으로
				} else { // 30분보다 이상일 때
					strMin = "00"; // 00분으로 반올림 후
					todayHour++; // 시간에 1 추가
				} // else End
				todayHour++; // 1시간 추가
				
				// 현재 시각이 10시 미만일 때
				if (todayHour < 10) {
					strHour = "0" + todayHour; // 앞에 0을 붙인다
				} else { // 10시 이상일 때는 그냥 숫자 그대로
					strHour = String.valueOf(todayHour);
				} // else End
				
				// 현재 시각에서 1시간 30분을 반올림 한 시각
				todayTime = strHour + strMin;
				
				// 현재 시각이 업체 영업 시작 시간보다 전일 때
				if (Integer.parseInt(todayTime) < Integer.parseInt(timeMap.get("AM_OPEN"))) {
					String amTimeList = timeHtml(amOpenHour, amOpenMin, amCloseHour, amCloseMin, noTimeList);
					String pmTimeList = timeHtml(pmOpenHour, pmOpenMin, pmCloseHour, pmCloseMin, noTimeList);
					timeList = amTimeList + pmTimeList;
				// 현재 시각이 업체 영업 시작 시간보단 지났지만, 점심 시간 시작 전일 때
				} else if (Integer.parseInt(todayTime) < Integer.parseInt(timeMap.get("AM_CLOSE"))) {
					amOpenHour = Integer.parseInt(todayTime.substring(0, 2));
					amOpenMin = Integer.parseInt(todayTime.substring(2, 4));
					String amTimeList = timeHtml(amOpenHour, amOpenMin, amCloseHour, amCloseMin, noTimeList);
					String pmTimeList = timeHtml(pmOpenHour, pmOpenMin, pmCloseHour, pmCloseMin, noTimeList);
					timeList = amTimeList + pmTimeList;
				// 현재 시각이 업체 점심 시간일 때
				} else if (Integer.parseInt(todayTime) < Integer.parseInt(timeMap.get("PM_OPEN"))) {
					timeList = timeHtml(pmOpenHour, pmOpenMin, pmCloseHour, pmCloseMin, noTimeList);
				// 현재 시각이 업체 점심 시간 이후지만, 영업 종료 시간 전일 때
				} else if (Integer.parseInt(todayTime) < Integer.parseInt(timeMap.get("PM_CLOSE"))) {
					pmOpenHour = Integer.parseInt(todayTime.substring(0, 2));
					pmOpenMin = Integer.parseInt(todayTime.substring(2, 4));
					timeList = timeHtml(pmOpenHour, pmOpenMin, pmCloseHour, pmCloseMin, noTimeList);
				// 현재 시각이 영업 종류 시간 후일 때
				} else {
					timeList = "예약 가능한 시간이 없습니다.";
				} // else End
				
			} else { // 오늘 아닐 때
				String amTimeList = timeHtml(amOpenHour, amOpenMin, amCloseHour, amCloseMin, noTimeList);
				String pmTimeList = timeHtml(pmOpenHour, pmOpenMin, pmCloseHour, pmCloseMin, noTimeList);
				timeList = amTimeList + pmTimeList;
			} // else End
			
		} // else End
		return timeList;
	} // method End

	// 시간 만드는 메소드
	private String timeHtml(int openHour, int openMin, int closeHour, int closeMin,
			List<HashMap<String, String>> noTimeList) {
		
		// for문 돌려야 하는 횟수 정하기
		int length = 0;
		if (openMin == closeMin) { // 시작 시각 '분'과 끝 시각 '분'이 같을 때 (00분=00분, 30분=30분)
			length = (closeHour - openHour) * 2; // 30분 단위기 때문에 '시' 차이의 2배
		} else if (openMin > closeMin) { // 시작 시각 분이 더 클 때 (30분 > 00분) 
			length = (closeHour - openHour) * 2 - 1; // 2배를 해준 후 -1 해준다. (30분 뺀다)
		} else if (openMin < closeMin) { // 끝 시각 분이 더 클 때 (00분 < 30분)
			length = (closeHour - openHour) * 2 + 1; // 2배를 해준 후 +1 해준다. (30분 더한다)
		} // if End
		
		StringBuilder timeList = new StringBuilder();
		timeList.append("<tr>");
		String time = "";
		
		for (int i = 1; i <= length; i++) {
			
			
			if (openHour == 0) { // 00시일 때
				time += "00";
			} else if (openHour < 10) { // 10시 미만일 때
				time = "0" + openHour;
			} else { // 10시 이상일 때
				time += openHour;
			} // else End
			
			if (openMin == 0) { // 00분일 때
				time += "00";
			} else { // 30분일 때
				time += openMin;
			} // else End
			
			timeList.append("<td><input type='radio' name='time' value='" + time);
			
			// 이미 예약된 시간과 동일하면 disabled 속성
			for (int j = 0; j < noTimeList.size(); j++) {
				if (noTimeList.get(j).get("VS_START").equals(time)) {
					timeList.append("' disabled='disabled");
				} // if End
			} // for End
			
			timeList.append("' />" + time.substring(0, 2) + ":" + time.substring(2, 4) + "</td>");
			if (i % 5 == 0) { // 시간 5개마다 줄내림
				timeList.append("</tr><tr>");
			} // if End
			
			// 반복문 위해 30분 증가
			if (openMin == 30) {
				openHour += 1;
				openMin = 0;
			} else {
				openMin += 30;
			} // else End
			time = ""; // 초기화
		} // for End
		timeList.append("</tr>");
		return timeList.toString();
	} // method End

	// 예약 메소드
	@Transactional
	public ModelAndView insertBooking(Booking bk, HttpServletRequest request) {
		bk.setPer_no(request.getSession().getAttribute("no").toString());
		bk.setVs_start(request.getParameter("day") + request.getParameter("time"));
		String[] menu_no = request.getParameterValues("menu_no");
		int sum = 0;
		for (int i = 0; i < menu_no.length; i++) {
			sum += Integer.parseInt(request.getParameter(menu_no[i]));
		} // for End
		bk.setBk_pay(sum);
		mDao.insertBooking(bk);
		bk.setBk_no("K" + String.valueOf(bk.getBk_seq()));
		for (int i = 0; i < menu_no.length; i++) {
			bk.setMenu_no(Integer.parseInt(menu_no[i]));
			mDao.insertBkMenu(bk);
		} // for End
		HashMap<String, String> hMap = mDao.bookingSuccess(bk);
		ModelAndView mav = new ModelAndView();
		mav.addObject("bus_name", hMap.get("BUS_NAME"));
		mav.addObject("vs_start", hMap.get("VS_START"));
		mav.addObject("bct_name", hMap.get("BCT_NAME"));
		return mav;
	} // method End

}
