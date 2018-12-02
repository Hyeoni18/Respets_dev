package com.teamx.respets.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Gallery;
import com.teamx.respets.bean.Personal;
import com.teamx.respets.dao.HyeonDao;
import com.teamx.respets.userClass.Paging;

@Service
public class HyeonService {
	private ModelAndView mav;
	@Autowired
	private HyeonDao hyDao;
	@Autowired
	HttpSession session;

	/* 혜연 개인 마이페이지 */
	public ModelAndView myInfo(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		Personal mb = new Personal();
		String no = session.getAttribute("no").toString();
		// 개인정보 select
		mb = hyDao.myInfo(no);
		if (mb != null) {
			mav.addObject("mb", mb);
			view = "myInfo";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 혜연 개인 비밀번호 수정 */
	public int myPwCheck(String now, HttpServletRequest request) {
		mav = new ModelAndView();
		Personal mb = new Personal();
		System.out.println(request.getSession().getAttribute("no"));
		mb.setPer_no(request.getSession().getAttribute("no").toString());
		System.out.println(now);
		mb.setPer_pw(now);
		int result = hyDao.myPwCheck(mb);
		return result;
	}

	public int myPwUpdate(String newPw) {
		mav = new ModelAndView();
		Personal mb = new Personal();
		mb.setPer_no(session.getAttribute("no").toString());
		mb.setPer_pw(newPw);
		int update = hyDao.myPwUpdate(mb);
		return update;
	}

	/* 혜연 정보수정에 개인정보 불러오기 */
	public ModelAndView myInfoUpdateForm(Personal mb, HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		mb = hyDao.myInfo(no);
		String view = null;
		if (mb != null) {
			mav.addObject("mb", mb);
			view = "myInfoUpdateForm";
		} else {
			view = "myInfo";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 혜연 개인정보 수정 */
	public ModelAndView myInfoUpdate(MultipartHttpServletRequest request) {
		// 파일은 request에 담겨서 온다.
		mav = new ModelAndView();
		Personal p = new Personal();
		String no = request.getSession().getAttribute("no").toString();
		// MultipartFile을 선언해서 request.getFile에서 받은 파일을 담아준다.
		// request를 * Multipart * Request로 선언한다.
		p.setPer_no(no);
		p.setPer_phone(request.getParameter("per_phone")); // 폼에서 받은 연락처
		if (request.getParameter("fileCheck").equals("1")) {
			MultipartFile photo = request.getFile("mainPhoto");
			// saveFile 메소드에서 해시맵을 사용하고 받기 위해 생성하고 파라미터로 보내준다.
			Map<String, Object> hMap = new HashMap<String, Object>();
			// request는 path 주소를 위해, photo는 실제 저장할 파일, hMap은 주소와 파일 이름 반환을 위
			hMap = saveFile(request, photo, hMap); // 파일 저장 메소드 소환
			p.setPer_loc(hMap.get("per_loc").toString());
			p.setPer_photo(hMap.get("per_photo").toString());
			System.out.println("연락처 수정 확인:" + request.getParameter("per_phone"));
			System.out.println(p.getPer_no());
			System.out.println(p.getPer_photo());
			hyDao.perPhotoUpdate(p);
		} else {
			hyDao.perNoPhotoUpdate(p);
		}
		p = hyDao.myInfo(no);
		mav.addObject("mb", p);
		return mav;
	}

	private Map<String, Object> saveFile(MultipartHttpServletRequest request, MultipartFile mainPhoto,
			Map<String, Object> map) {
		// 프로젝트가 실제 존재하는 주소(RealPath)를 가져온다. (언제든지 리퀘스트만 있다면 뽑을 수 있음)
		String root = request.getSession().getServletContext().getRealPath("/");
		// DB에 저장할 값. 위의 root는 상시로 뽑을 수 있기 때문에 저장 X.
		String location = "resources/upload/";
		// 그래서 주소는 파일을 저장할 실제 주소는 root + location
		String path = root + location;

		File dir = new File(path); // File이라는 객체를 선언. 주소값을 가지고.
		if (!dir.isDirectory()) { // 파일(폴더)이 존재하지 않으면
			dir.mkdir(); // 폴더 생성
		} // if End
			// 파일 이름을 바꾸기 위한 것들 (파일이 같은 이름일 때 덮어씌어지지않기 위해 서버파일이름을 따로 지정)
		String date = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
		// 혹시 모를 이름 중복을 위해서 오늘 날짜를 181201..이런 식으로 생성
		String extension = mainPhoto.getOriginalFilename() // hyeon.jpg
				.substring(mainPhoto.getOriginalFilename().lastIndexOf(".") + 1);
		// 확장자 갖고 오기 jpg, png, jpeg
		// 업로드된 사진의 원래 이름에서 .을 기준으로 한글자 뒤부터 끝까지 저장
		String saveName = "Respets_" + date + "_" + UUID.randomUUID() + "." + extension;
		// db에 저장할 파일의 서버 이름
		try {
			mainPhoto.transferTo(new File(path, saveName));
			// 이게 실제로 파일 저장하는 소스
			// new File로 주소와, 이름을 가진 빈 껍데기 파일을 생성해줌.
			// 실제 파일.transferTo 메소드를 이용해 빈 껍데기 파일에 실제 파일을 넣어준다.
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} // catch End
		map.put("per_loc", location); // 디비에 저장할 경로 담기
		map.put("per_photo", saveName); // 디비에 저장할 파일 이름 담기
		return map; // 경로와 이름 리턴
	}

	/* 혜연 개인 회원탈퇴 이메일,예약내역, 동물종류남기고 지우기 */
	public ModelAndView personalPartDelete(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		String no = session.getAttribute("no").toString();
		/* 회원테이블에 사용안한다고 업데이트 하기 */
		boolean result = hyDao.personalPartDelete(no);
		if (result) {
			view = "loginForm";
		} else {
			mav.addObject("flas", makeFlasHtml());
			view = "myInfo";
		}
		mav.setViewName(view);
		return mav;
	}

	/* 혜연 실패 alert 띄우기 */
	private Object makeFlasHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>alert('실패')</script>");
		return sb.toString();
	}

	/* 혜연 예약취소 */
	public ModelAndView myBookingCancel(HttpSession session, HttpServletRequest request) {
		this.session = session;
		mav = new ModelAndView();
		HashMap<String, Object> map = new HashMap<String, Object>();
		// List<HashMap<String, Object>> hList = new ArrayList<HashMap<String,
		// Object>>();
		String view = null;
		String bk_no = request.getParameter("bk_no");
		System.out.println("예약번호=" + bk_no);
		// 방문날짜 가져오기
		Date start = hyDao.getBkStart(bk_no);
		System.out.println("방문날=" + start);
		int count = cancleDateCheck(start);
		System.out.println(count);
		map.put("bk_no", bk_no);
		map.put("count", count);
		int result = hyDao.cancelInsert(map);
		if (result != 0) {
			int update = hyDao.bk_chkUpdate(bk_no);
			if (update != 0) {
//				HashMap<String, Object> hmap = new HashMap<>();
//				String no = (String) session.getAttribute("no");
//				StringBuilder sb = new StringBuilder();
//				hmap.put("no", no);
//				hList = jDao.recentMyBookingList(hmap);
//				System.out.println(hList);
//				for (int i = 0; i < hList.size(); i++) {
//					sb.append("<tr><td><a href='./myBookingDetail?" + hList.get(i).get("BK_NO") + "'>"
//							+ hList.get(i).get("BK_NO") + "</a></td>");
//					sb.append("<td>" + hList.get(i).get("BUS_NAME") + "</td>");
//					sb.append("<td>" + hList.get(i).get("PTY_NAME") + "</td>");
//					sb.append("<td>" + hList.get(i).get("PET_NAME") + "</td>");
//					sb.append("<td>" + hList.get(i).get("PER_NAME") + "</td>");
//					sb.append("<td>" + hList.get(i).get("BK_TIME") + "</td>");
//					sb.append("<td>" + hList.get(i).get("VS_START") + "</td>");
//					sb.append("<td name='chk'>" + hList.get(i).get("BK_CHK") + "</td></tr>");
//				} // for End
//				mav.addObject("hList", sb);
				mav.addObject("cancInsertSucess", makeCancInsertSucess());
				mav.addObject(session);
				view = "redirect:/recentMyBookingList";
			} else {
				mav.addObject("flas", makeFlasHtml());
				view = "myBookingCancelPage";
			}
		}
		mav.setViewName(view);
		return mav;
	}

	private int cancleDateCheck(Date booking) {
		Date today = new Date(); // 현재시간
		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(today); // 현재시간을 캘린더화
		Calendar bookingCal = Calendar.getInstance();
		bookingCal.setTime(booking); // 가져온 방문예정일을 캘린더화
		int count = 0; // 현재날짜와 방문예정일의 차이를 나타낼 카운트 변수
		while (!todayCal.after(bookingCal)) { // 현재날짜가 방문예정일을 지나지 않았으면 반복
			if (todayCal != bookingCal) { // 현재날짜가 방문예정일이랑 다르면 count++, 같으면 count하지 않는다.
				count++;
			}
			todayCal.add(Calendar.DATE, 1); // 현재날짜가 방문예정일이 될 때까지 +1일을 해준다.
		}
		if (count != 0) {
			System.out.println("방문예정일 까지 " + count + "일 남았습니다.");
		} else { // 현재날짜가 방문예정일 당일이거나 지난 후면 count가 되지 않는다.
			System.out.println("방문예정일이 지났습니다.");
		}
		return count;
	}

	private Object makeCancInsertSucess() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>alert('예약 취소가 완료되었습니다.')</script>");
		return sb.toString();
	}

	/* 혜연 전체예약목록 */
	public ModelAndView personalAllBookingList(HttpSession session, Integer pageNum) {
		this.session = session;
		mav = new ModelAndView();
		int page_no = (pageNum == null) ? 1 : pageNum;
		StringBuilder sb = new StringBuilder();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String no = session.getAttribute("no").toString();
		System.out.println("개인번호=" + no);
		map.put("no", no);
		map.put("page_no", page_no);
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		allList = hyDao.allBookingList(map);
		if (allList != null) {
			for (int i = 0; i < allList.size(); i++) {
				String bk_no = (String) allList.get(i).get("BK_NO");
				sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td><td>"
						+ allList.get(i).get("BUS_NAME") + "</td><td>" + allList.get(i).get("PTY_NAME") + "</td><td>"
						+ allList.get(i).get("PET_NAME") + "</td><td>" + allList.get(i).get("PER_NAME") + "</td><td>"
						+ allList.get(i).get("BK_TIME") + "</td><td>" + allList.get(i).get("VS_START") + "</td>");
				if (allList.get(i).get("BK_CHK").equals("승인")) {
					sb.append("<td class='text-success'>" + allList.get(i).get("BK_CHK") + "</td></tr>");
				} else if (allList.get(i).get("BK_CHK").equals("거절")) {
					sb.append("<td class='text-danger'>" + allList.get(i).get("BK_CHK") + "</td></tr>");
				} else if (allList.get(i).get("BK_CHK").equals("취소")) {
					sb.append("<td class='text-warning'>" + allList.get(i).get("BK_CHK") + "</td></tr>");
				} else {
					sb.append("<td class='text-info'>" + allList.get(i).get("BK_CHK") + "</td></tr>");
				}
			}
			mav.addObject("allList", sb);
			String paging = BookingListPaging(page_no, no);
			mav.addObject("paging", paging);
		}
		mav.setViewName("allBookingList");
		return mav;
	}

	private String BookingListPaging(int page_no, String no) {
		int maxNum = hyDao.contPerBkList(no);
		System.out.println(maxNum);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "personalAllBookingList";
		Paging paging = new Paging(maxNum, page_no, listCount, pageCount, boardName);
		return paging.BookingListPaging(no);
	}

//////////////////////////////////////////////////////////////////////////////////////

	/* 혜연 기업 */

	/* 혜연 기업 오늘일정 */
	public ModelAndView todayScheduleList(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		StringBuilder sb = new StringBuilder();
		String no = session.getAttribute("no").toString();
		ArrayList<HashMap<String, Object>> sMap = hyDao.getSvcPri(no);
		if (sMap.size() != 0) {
			for (int i = 0; i < sMap.size(); i++) {
				String svc = (String) sMap.get(i).get("BCT_NAME");
				String code = (String) sMap.get(i).get("BCT_CODE");
				sb.append("&emsp;&emsp; <input type='radio' name='radio' class='" + code + "' value='" + svc + "'>"
						+ svc);
			}
			mav.addObject("bctList", sb);
			view = "todayScheduleList";
		} else {
			mav.addObject("none", makeNoneHtml());
			view = "todayScheduleList";
		}
		mav.setViewName(view);
		return mav;
	}

	private Object makeNoneHtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<h1>목록이 없습니다.</h1>");
		return sb.toString();
	}

	/* 혜연 예약 상세내역 */
	public ModelAndView myBookingDetail(HttpServletRequest request) {
		mav = new ModelAndView();
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		String view = null;
		String bk_no = request.getQueryString();
		String chk = hyDao.getBk_chk(bk_no);
		HashMap<String, Object> map = new HashMap<String, Object>();
		System.out.println("예약번호=" + bk_no);
		map = hyDao.myBookingDetail(bk_no); // 펫 디테일 리스트로 가져오기
		System.out.println("확인1: " + map);
		ArrayList<HashMap<String, Object>> menu = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> pList = new ArrayList<HashMap<String, Object>>();
		String pet_no = map.get("PET_NO").toString();
		menu = hyDao.getMenu(bk_no);
		System.out.println("확인2: " + menu);
		pList = hyDao.getPetList(pet_no);
		System.out.println("pList=" + pList);
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
		System.out.println("json: " + json);
		mav.addObject("result", json);
		mav.addObject("bk_no", bk_no);
		mav.addObject("chk", chk);
		view = "myBookingDetail";
		mav.setViewName(view);
		return mav;
	}

	/* 혜연 방문 클릭시 */
	public String todayScheduleListCheck(HttpServletRequest request) {
		System.out.println("예약번호 = " + request);
		String bk_no = request.getParameter("bk_no");
		hyDao.todayScheduleListCheck(bk_no);
		String changeList = todayAllScheduleList(request);
		return changeList;
	}

	public String todayScheduleListCancel(HttpServletRequest request) {
		String bk_no = request.getParameter("bk_no");
		System.out.println("예약번호 = " + bk_no);
		hyDao.todayScheduleListCancel(bk_no);
		String changeList = todayAllScheduleList(request);
		return changeList;
	}

	public String bctBookingListCheck(HttpServletRequest request) {
		System.out.println("예약번호 = " + request);
		String bk_no = request.getParameter("bk_no");
		hyDao.todayScheduleListCheck(bk_no);
		String changeList = bctBookingList(request);
		return changeList;
	}

	public String bctBookingListCancel(HttpServletRequest request) {
		String bk_no = request.getParameter("bk_no");
		System.out.println("예약번호 = " + bk_no);
		hyDao.todayScheduleListCancel(bk_no);
		String changeList = bctBookingList(request);
		return changeList;
	}

	public String vs_chkOkList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("bus_no", request.getParameter("bus_no"));
		map.put("bk_no", request.getParameter("bk_no"));
		map.put("timeS", timeS);
		ArrayList<HashMap<String, Object>> okList = hyDao.vs_chkOkList(map);
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

	/* 노쇼 클릭 시 */
	public int todayScheduleListNoShow(HttpServletRequest request) {
		String per_no = request.getParameter("pno");
		System.out.println("개인=" + per_no);
		int noshow = hyDao.getnoshowCount(per_no);
		System.out.println("노쇼 수=" + noshow);
		int result = 0;
		if (noshow == 0 || noshow == 1 || noshow == 2) { // 노쇼 수가 0이거나 2보다 작거나 같으면
			result = hyDao.noshowInsert(per_no); // 노쇼 레코드 인설트
			System.out.println(result);
		} else if (noshow == 3) { // 노쇼 수가 3개일 시
			int delete = hyDao.noshowDelete(per_no); // 노쇼 레코드 삭제
			int insert = hyDao.warningInsert(per_no); // 경고 레코드 추가
			result = 1;
		}
		return result;
	}

	/* 노쇼 취소 클릭 시 */
	public int todayScheduleListUnNoShow(HttpServletRequest request) {
		String per_no = request.getParameter("pno");
		String timeS = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
		int result = hyDao.todayScheduleListUnNoShow(per_no, timeS);
		return result;
	}

	/* 혜연 전체 예약 */
	public ModelAndView businessBookingList(HttpSession session, Integer pageNum) {
		this.session = session;
		mav = new ModelAndView();
		int pNo = (pageNum == null) ? 1 : pageNum;
		StringBuilder sb = new StringBuilder();
		String no = session.getAttribute("no").toString();
		ArrayList<HashMap<String, Object>> sMap = hyDao.getSvcPri(no);
		if (sMap.size() != 0) {
			for (int i = 0; i < sMap.size(); i++) {
				String svc = (String) sMap.get(i).get("BCT_NAME");
				String code = (String) sMap.get(i).get("BCT_CODE");
				sb.append("<option>" + svc + "</option>");
			}
			mav.addObject("bctList", sb);
		}
		mav.setViewName("businessBookingListPage");
		return mav;
	}

	/* 혜연 서비스 관리 */
	public ModelAndView serviceManagement(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		String no = session.getAttribute("no").toString();
		System.out.println("기업번호 = " + no);
		ArrayList<HashMap<String, Object>> servList = new ArrayList<HashMap<String, Object>>();
		servList = hyDao.serviceManagement(no);
		if (servList != null) {
			mav.addObject("servList", makeServiceListHtml(servList));
			view = "servicePage";
		} else {
			mav.addObject("add", makeAddHtml());
			view = "servicePage";
		}
		mav.setViewName(view);
		return mav;
	}

	private Object makeServiceListHtml(ArrayList<HashMap<String, Object>> servList) {
		StringBuilder sb = new StringBuilder();
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
		return sb.toString();
	}

	/*
	 * public ModelAndView monthSchedule(HttpSession session) { this.session =
	 * session; mav = new ModelAndView(); String view = null; HashMap<String,
	 * Object> map = new HashMap<String, Object>(); String no =
	 * session.getAttribute("no").toString(); System.out.println("번호=" + no);
	 * map.put("no", no); ArrayList<HashMap<String, Object>> servList = new
	 * ArrayList<HashMap<String, Object>>(); servList = hyDao.serviceList(map);
	 * System.out.println(servList); if (servList != null) {
	 * mav.addObject("servList", makeServiceHtml(servList)); view = "monthSchedule";
	 * } else { mav.addObject("add", makeAddHtml()); view = "servicePage"; }
	 * mav.setViewName(view); return mav; }
	 */

	/*
	 * private Object makeServiceHtml(ArrayList<HashMap<String, Object>> servList) {
	 * StringBuilder sb = new StringBuilder(); for (int i = 0; i < servList.size();
	 * i++) { String code = (String) servList.get(i).get("BCT_CODE");
	 * sb.append("<li id='" + code + "'><span id='com'><a href='forward(" + code +
	 * ")'>" + servList.get(i).get("BCT_NAME") + "</a></span></li>"); } return
	 * sb.toString(); }
	 */

	private Object makeAddHtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("서비스를 등록해주세요");
		return sb.toString();
	}

	public ModelAndView businessInfoDetail(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		HashMap<String, Object> bmap = new HashMap<>();
		bmap = hyDao.businessInfo(no);
		System.out.println("bmap" + bmap);
		String view = null;
		if (bmap != null) {
			String glr_file = (String) bmap.get("GLR_FILE");
			String glr_loc = (String) bmap.get("GLR_LOC");
			StringBuilder sb = new StringBuilder();
			sb.append("<img class='card-img-top' src='" + glr_loc + glr_file + "'/>");
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(bmap);
			System.out.println(json);
			mav.addObject("result", json);
			mav.addObject("bmap", bmap);
			mav.addObject("img", sb);
			view = "businessInfoDetail";
		}
		mav.setViewName(view);
		return mav;
	}

	public ModelAndView businessInfoUpdateForm(Map<String, Object> jsonData, HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		mav.addObject("no", no);
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(jsonData);
		mav.addObject("jsonData", json);
		mav.setViewName("businessInfoUpdateForm");
		return mav;
	}

	public ModelAndView businessInfoUpdate(MultipartHttpServletRequest request) {
		mav = new ModelAndView();
		System.out.println("기업수정서비스확인");
		HashMap<String, Object> bmap = new HashMap<String, Object>();
		Business bi = new Business();
		String no = request.getSession().getAttribute("no").toString();
		Gallery gy = new Gallery();

		if (request.getParameter("fileCheck").equals("1")) {
			bi.setBus_no(no);
			bi.setBus_name(request.getParameter("bus_name"));
			bi.setBus_phone(request.getParameter("bus_phone"));
			System.out.println("기업이름=" + request.getParameter("bus_name"));
			System.out.println("기업이름=" + request.getParameter("bus_phone"));
			hyDao.businessInfoUpdate(bi);
			MultipartFile photo = request.getFile("mainPhoto");
			// saveFile 메소드에서 해시맵을 사용하고 받기 위해 생성하고 파라미터로 보내준다.
			Map<String, Object> hMap = new HashMap<String, Object>();
			// request는 path 주소를 위해, photo는 실제 저장할 파일, hMap은 주소와 파일 이름 반환을 위
			hMap = bussaveFile(request, photo, hMap); // 파일 저장 메소드 소환
			gy.setBus_no(request.getSession().getAttribute("no").toString());
			gy.setGlr_loc(hMap.get("glr_loc").toString());
			gy.setGlr_file(hMap.get("glr_file").toString());
			hyDao.mainPhotoUpdate(gy);
		} /*
			 * else { hyDao.PhotoUpdate(gy); }
			 */
		bmap = hyDao.businessInfo(no);
		System.out.println(bmap.get("BUS_NAME"));
		String glr_file = (String) bmap.get("GLR_FILE");
		String glr_loc = (String) bmap.get("GLR_LOC");
		StringBuilder sb = new StringBuilder();
		sb.append("<img class='card-img-top' src='" + glr_loc + glr_file + "'/>");
		mav.addObject("bmap", bmap);
		mav.addObject("img", sb);
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(bmap);
		System.out.println(json);
		mav.addObject("result", json);
		mav.setViewName("businessInfoDetail");
		return mav;
	}

	private Map<String, Object> bussaveFile(MultipartHttpServletRequest request, MultipartFile photo,
			Map<String, Object> hMap) {
		// 프로젝트가 실제 존재하는 주소(RealPath)를 가져온다. (언제든지 리퀘스트만 있다면 뽑을 수 있음)
		String root = request.getSession().getServletContext().getRealPath("/");
		// DB에 저장할 값. 위의 root는 상시로 뽑을 수 있기 때문에 저장 X.
		String location = "resources/upload/";
		// 그래서 주소는 파일을 저장할 실제 주소는 root + location
		String path = root + location;
		File dir = new File(path); // File이라는 객체를 선언. 주소값을 가지고.
		if (!dir.isDirectory()) { // 파일(폴더)이 존재하지 않으면
			dir.mkdir(); // 폴더 생성
		} // if End
			// 파일 이름을 바꾸기 위한 것들 (파일이 같은 이름일 때 덮어씌어지지않기 위해 서버파일이름을 따로 지정)
		String date = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
		// 혹시 모를 이름 중복을 위해서 오늘 날짜를 181201..이런 식으로 생성
		String extension = photo.getOriginalFilename() // hyeon.jpg
				.substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
		// 확장자 갖고 오기 jpg, png, jpeg
		// 업로드된 사진의 원래 이름에서 .을 기준으로 한글자 뒤부터 끝까지 저장
		String saveName = "Respets_" + date + "_" + UUID.randomUUID() + "." + extension;
		// db에 저장할 파일의 서버 이름
		try {
			photo.transferTo(new File(path, saveName));
			// 이게 실제로 파일 저장하는 소스
			// new File로 주소와, 이름을 가진 빈 껍데기 파일을 생성해줌.
			// 실제 파일.transferTo 메소드를 이용해 빈 껍데기 파일에 실제 파일을 넣어준다.
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} // catch End
		hMap.put("glr_loc", location); // 디비에 저장할 경로 담기
		hMap.put("glr_file", saveName); // 디비에 저장할 파일 이름 담기
		return hMap; // 경로와 이름 리턴
	}

	public ModelAndView businessPartDelete(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String no = session.getAttribute("no").toString();
		boolean result = hyDao.businessPartDelete(no);
		if (result) {
			session.invalidate();
		}
		mav.setViewName("redirect:/");
		return mav;
	}

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
		bList = hyDao.bctBookingList(map);
		sb.append(
				"<table class='table table-centered mb-0' style='text-align:center;'><thead><tr><th> 예약번호 </th><th> 동물종류 </th><th> 동물이름 </th><th> 예약자명 </th><th> 서비스종류 </th><th> 예약일시 </th><th> 방문일시 </th><th> 방문표시 </th></tr></thead>");
		for (int i = 0; i < bList.size(); i++) {
			String bk_no = (String) bList.get(i).get("BK_NO");
			String pno = (String) bList.get(i).get("PER_NO");

			sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td>");
			sb.append("<td>" + bList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("BK_TIME") + "</td>");
			sb.append("<td>" + bList.get(i).get("VS_START") + "</td>");
			sb.append("<td><input type='button' class='btn btn-outline-success' value='방문' onclick=\"com(\'" + bk_no
					+ "')\" /></td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

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
		okList = hyDao.bctBookingListOk(map);
		sb.append(
				"<table class='table table-centered mb-0' style='text-align:center;'><thead><tr><th> 예약번호 </th><th> 동물종류 </th><th> 동물이름 </th><th> 예약자명 </th><th> 서비스종류 </th><th> 예약일시 </th><th> 방문일시 </th><th> 방문표시 </th></tr></thead>");
		for (int i = 0; i < okList.size(); i++) {
			String bk_no = (String) okList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td>");
			sb.append("<td>" + okList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("BK_TIME") + "</td>");
			sb.append("<td>" + okList.get(i).get("VS_START") + "</td>");
			sb.append("<td><button type='button' class='btn btn-outline-danger' onclick='cancelCheck(\"" + bk_no
					+ "\")'> 취소 </button></td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	public String todayAllScheduleList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		String no = request.getParameter("no");
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("no", no);
		map.put("timeS", timeS);
		bList = hyDao.todayScheduleList(map);
		sb.append(
				"<table class='table table-centered mb-0' style='text-align:center;'><thead><tr><th>예약 번호</th><th>동물 종류</th><th>동물 이름</th><th>예약자명</th><th>서비스 종류</th><th>방문 일시</th><th>방문 상태</th></tr></thead>");
		for (int i = 0; i < bList.size(); i++) {
			String bk_no = (String) bList.get(i).get("BK_NO");
			String pno = (String) bList.get(i).get("PER_NO");

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

	public String todayAllScheduleListOk(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> okList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		String no = request.getParameter("no");
		String timeS = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime());
		map.put("no", no);
		map.put("timeS", timeS);
		okList = hyDao.todayScheduleListOk(map);
		sb.append(
				"<table class='table table-centered mb-0' style='text-align:center;'><thead><tr><th> 예약번호 </th><th> 동물종류 </th><th> 동물이름 </th><th> 예약자명 </th><th> 서비스종류 </th><th> 예약일시 </th><th> 방문일시 </th><th> 방문표시 </th></tr></thead>");
		for (int i = 0; i < okList.size(); i++) {
			String bk_no = (String) okList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td>");
			sb.append("<td>" + okList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + okList.get(i).get("BK_TIME") + "</td>");
			sb.append("<td>" + okList.get(i).get("VS_START") + "</td>");
			sb.append("<td><button type='button' class='btn btn-outline-danger' onclick='cancelCheck(\"" + bk_no
					+ "\")'> 취소 </button></td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	public String businessAllBookingList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String no = (String) session.getAttribute("no");
		System.out.println(no);
		System.out.println(pNo);
		map.put("no", no);
		map.put("pageNum", pNo);
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		bList = hyDao.businessBookingList(map);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bList.size(); i++) {
			String bk_no = (String) bList.get(i).get("BK_NO");
			sb.append("<tr><td><a href='myBookingDetail?no=" + bk_no + "'>" + bk_no + "</a></td><td>"
					+ bList.get(i).get("PTY_NAME") + "</td><td>" + bList.get(i).get("PET_NAME") + "</td><td>"
					+ bList.get(i).get("PER_NAME") + "</td><td>" + bList.get(i).get("BCT_NAME") + "</td><td>"
					+ bList.get(i).get("BK_TIME") + "</td><td>" + bList.get(i).get("VS_START") + "</td></tr>");
		}
		return sb.toString();
	}

	public String searchAllList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bPerList = new ArrayList<HashMap<String, Object>>();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String no = (String) session.getAttribute("no");
		String search = request.getParameter("search");
		StringBuilder sb = new StringBuilder();
		map.put("no", no);
		map.put("per_name", search);
		map.put("pageNum", pNo);
		bPerList = hyDao.searchAllList(map);
		if (bPerList.size() != 0) {
			for (int i = 0; i < bPerList.size(); i++) {
				String bk_no = (String) bPerList.get(i).get("BK_NO");
				sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td><td>"
						+ bPerList.get(i).get("PTY_NAME") + "</td><td>" + bPerList.get(i).get("PET_NAME") + "</td><td>"
						+ bPerList.get(i).get("PER_NAME") + "</td><td>" + bPerList.get(i).get("BCT_NAME") + "</td><td>"
						+ bPerList.get(i).get("BK_TIME") + "</td><td>" + bPerList.get(i).get("VS_START")
						+ "</td></tr>");
			}
		}
		return sb.toString();
	}

	public String searchAllListPaging(HttpServletRequest request) {
		String no = (String) session.getAttribute("no");
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String search = request.getParameter("search");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("per_name", search);
		System.out.println(no);
		System.out.println(search);
		int maxNum = hyDao.searchAllListPaging(map);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "businessBookingList"; // 사용안함
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.searchAllList(search);
	}

	public String businessAllBctBookingList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String no = (String) session.getAttribute("no");
		String bct_name = request.getParameter("bct_name");
		map.put("no", no);
		map.put("bct_name", bct_name);
		map.put("pageNum", pNo);
		bList = hyDao.AllbctBookingList(map);
		if (bList != null) {
			for (int i = 0; i < bList.size(); i++) {
				String bk_no = (String) bList.get(i).get("BK_NO");
				sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td><td>"
						+ bList.get(i).get("PTY_NAME") + "</td><td>" + bList.get(i).get("PET_NAME") + "</td><td>"
						+ bList.get(i).get("PER_NAME") + "</td><td>" + bList.get(i).get("BCT_NAME") + "</td><td>"
						+ bList.get(i).get("BK_TIME") + "</td><td>" + bList.get(i).get("VS_START") + "</td></tr>");

			}
		}
		return sb.toString();
	}

	public String searchBctAllsList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<HashMap<String, Object>>();
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String search = request.getParameter("search");
		String bct_name = request.getParameter("bct_name");
		StringBuilder sb = new StringBuilder();
		String no = (String) session.getAttribute("no");
		map.put("no", no);
		map.put("bct_name", bct_name);
		map.put("per_name", search);
		map.put("pageNum", pNo);
		bList = hyDao.searchBctAllsList(map);
		if (bList != null) {
			for (int i = 0; i < bList.size(); i++) {
				String bk_no = (String) bList.get(i).get("BK_NO");
				sb.append("<tr><td><a href='myBookingDetail?" + bk_no + "'>" + bk_no + "</a></td><td>"
						+ bList.get(i).get("PTY_NAME") + "</td><td>" + bList.get(i).get("PET_NAME") + "</td><td>"
						+ bList.get(i).get("PER_NAME") + "</td><td>" + bList.get(i).get("BCT_NAME") + "</td><td>"
						+ bList.get(i).get("BK_TIME") + "</td><td>" + bList.get(i).get("VS_START") + "</td></tr>");
			}
		}
		return sb.toString();
	}

	public String searchBctAllsListPaging(HttpServletRequest request) {
		String no = (String) session.getAttribute("no");
		String bct_name = request.getParameter("bct_name");
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		String search = request.getParameter("search");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("bct_name", bct_name);
		map.put("per_name", search);
		int maxNum = hyDao.searchBctAllsListPaging(map);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "businessBookingList"; // 사용안함
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.searchBctAllsList(bct_name, search);
	}

	public String AllPaging(HttpServletRequest request) {
		/* public String AllPaging(String searcht, Integer pageNum) { */
		String no = (String) session.getAttribute("no");
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		int maxNum = hyDao.getListCount(no);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "businessBookingList"; // 사용안함
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.AllPaging();
	}

	public String bctAllPaging(HttpServletRequest request) {
		String no = (String) session.getAttribute("no");
		String bct_name = request.getParameter("bct_name");
		int pNo = Integer.parseInt(request.getParameter("pageNum"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("bct_name", bct_name);
		int maxNum = hyDao.bctAllPaging(map);
		int listCount = 10;
		int pageCount = 5;
		String boardName = "businessBookingList"; // 사용안함
		Paging paging = new Paging(maxNum, pNo, listCount, pageCount, boardName);
		return paging.bctAllPaging(bct_name);
	}

}
