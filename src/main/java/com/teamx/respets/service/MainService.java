package com.teamx.respets.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
	            String bfx = mDao.searchBFX((String) list.get(i).get("BUS_NO"));
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
			sb.append(
					/*"<span>&nbsp;&nbsp;</span><a class='btn btn-outline-secondary' href='javascript:void(0)' onclick=\"butTagSelectList('"
							+ bct_code + "','" + tag_no + "','" + pNo + "')\">" + tag_name + "</a>"
					+*/"<a href='javascript:void(0)' onclick=\"butTagSelectList('" + 
							bct_code + "','" + tag_no + "','" + pNo + "')\" class='btn btn-outline-light btn-rounded' style='margin-right:10px;'>"+tag_name+"</a>");
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
					sb.append("<img class='card-img-top img-fluid' src='" + glr_loc + glr_file + "' style='width:100%;height:13rem;'/>");
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
}
