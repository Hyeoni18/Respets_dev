package com.teamx.respets.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamx.respets.dao.IJiyeDao;
import com.teamx.respets.userClass.Paging;

@Service
public class JiyeService {
	@Autowired
	private IJiyeDao jDao;
	private ModelAndView mav;
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	@Autowired
	private HyunHwiService hhs;

	// 지예 이메일 중복 확인
	public int emailChkSignUp(String email) {
		int result = jDao.emailChkSignUp(email);
		return result;
	} // emailChkSignUp End

	// 지예 개인회원가입
	public ModelAndView personalJoin(MultipartHttpServletRequest multi) {
		mav = new ModelAndView();
		String view = null;
		String email = multi.getParameter("per_email");
		String pw = multi.getParameter("per_pw");
		String name = multi.getParameter("per_name");
		String phone = multi.getParameter("per_phone");
		MultipartFile photo = multi.getFile("photo");
		int check = Integer.parseInt(multi.getParameter("fileCheck"));
		System.out.println("check=" + email);
		Map<String, Object> hmap = new HashMap<>();
		hmap.put("per_email", email);
		hmap.put("per_pw", pw);
		hmap.put("per_name", name);
		hmap.put("per_phone", phone);
		boolean personalJoinResult = false;
		if (check == 1) {
			FileService upload = new FileService();
			Map<String, String> fmap = new HashMap<>();
			fmap = upload.upload(multi);// 서버에 파일을 업로드, 오리지널파일명·시스템파일명·경로를 Map에 저장
			String sysFileName = fmap.get("sysFileName");
			String location = fmap.get("location");
			hmap.put("per_photo", sysFileName);
			hmap.put("per_loc", location);
			System.out.println("check hmap=" + hmap);
			personalJoinResult = jDao.personalJoinPhoto(hmap);
			if (personalJoinResult == true) {
				System.out.println("insert 성공");
			} else {
				System.out.println("insert 실패");
			}
		} else {
			personalJoinResult = jDao.personalJoin(hmap);
			if (personalJoinResult == true) {
				System.out.println("사진없이 insert 성공");
			} else {
				System.out.println("사진없이 insert 실패");
			}
		}
		mav.addObject("email", email);
		String tomail = email;
		String title = "[Respets] 계정 인증 메일";
		String content = "링크를 클릭해주세요. http://localhost/emailConfirmSuccess?per_email=" + email;
		hhs.mailSending(tomail, title, content);
		view = "emailConfirmOffer";
		mav.setViewName(view);
		return mav;
	} // personalJoin End

	// 지예 로그인 (개인, 기업 통합)
	public ModelAndView loginProcess(String email, String pw) { // loginForm에 email, pw를 String 으로 받아옴 (빈 없음)
		mav = new ModelAndView();
		HashMap<String, Object> hmap = new HashMap<String, Object>();
		String view = null;
		String loginFailed = "alert('로그인에 실패하였습니다.')";
		String noEmail = "alert('계정과 비밀번호를 확인해주세요.')";
		hmap.put("email", email); // input 에서 받아 온 email을 hashMap에 담는다.
		hmap.put("pw", pw);
		hmap = jDao.loginProcess(hmap); // hmap 에 담은 email을 가지고 db 테이블에 가서 select한 정보를 다시 hmap에 담아 가져온다.
		System.out.println("loginProcess: " + hmap);

		if (hmap != null) {
			String no = (String) hmap.get("NO"); // hmap에 담아 온 회원번호를 가져온다.
			if (no != null) { // 회원 번호가 null이 아니라면
				hmap.put("no", no); // 회원 번호를 다시 hmap에 담는다
				String name = hmap.get("NAME").toString();
				String leave = hmap.get("LEAVE").toString();
				// request.getSession().setAttribute("no", no);
				session.setAttribute("no", no); // 세션에 회원번호를 담는다.
				session.setAttribute("name", name);

				HashMap<String, Object> bmap = new HashMap<String, Object>();
				bmap.put("no", no); // bmap에 회원 번호를 담는다.
				bmap = jDao.blackChk(bmap); // bmap에 담긴 회원번호를 가지고 블랙리스트 여부를 검사
				System.out.println("블랙리스트: " + bmap);
				
				if(leave.equals("O")) {
					String alert = "alert('탈퇴한 회원의 이메일 입니다. 로그인 할 수 없습니다.');";
					mav.addObject("leave", alert);
					session.invalidate();
					view="loginForm";
				}else {
					if(bmap != null) {
					Integer out_no = (int) bmap.get("OUT_NO");
					Date blk_time = (Date) bmap.get("BLK_TIME");
					System.out.println(blk_time);
				
						if(out_no == 1 || out_no== 2) { // 블랙리스트 테이블 경고 번호 확인
							String alert = "alert('이용이 정지된 계정입니다. 로그인 할 수 없습니다.');";
							mav.addObject("alert", alert); // 경고창을 띄우고
							session.invalidate(); // 세션을 만료시킨다.
							view = "loginForm";
						}
					} else { // 블랙리스트 테이블에서 회원번호가 select되지 안을 때
						if (no.contains("P")) { // 회원 번호에 P가 포함 되어 있으면
							hmap = jDao.chkPerEm(hmap); // hmap에 pw를 담아 정보를 가져온다.
							String perEmChk = (String) hmap.get("PER_EMCHK");
							String loc = hmap.get("PER_LOC").toString();
							String photo = hmap.get("PER_PHOTO").toString();
							session.setAttribute("loc", loc);
							session.setAttribute("photo", photo);
							System.out.println("개인회원: " + hmap);

							if (perEmChk.equals("O")) {// 개인회원 인증 여부 확인
								System.out.println("인증된 개인 회원");
								view = "index";
							} else {
								System.out.println("개인회원 인증X");
								String sendEmail = (String) hmap.get("PER_EMAIL");
								String tomail = sendEmail;
								String title = "[Respets] 계정 인증 메일";
								String content = "링크를 클릭해주세요. http://localhost/emailConfirmSuccess?per_email=" + sendEmail;
								hhs.mailSending(tomail, title, content);
								mav.addObject("email", sendEmail);
								session.invalidate();
								System.out.println(session);
								view = "emailConfirmOffer";
							} // 개인회원 if end

						} else if (no.contains("B")) {
							hmap = jDao.chkBusEm(hmap);
							String busEmChk = (String) hmap.get("BUS_EMCHK");
							System.out.println("기업회원: " + hmap);

							if (busEmChk.equals("O")) {
								//String name = (String) hmap.get("BUS_NAME");
								//mav.addObject("name", name);
								System.out.println("기업회원 인증");
								view = "index";
							} else {
								System.out.println("기업회원 인증X");
								String sendEmail = (String) hmap.get("BUS_EMAIL");
								String tomail = sendEmail;
								String title = "[Respets] 계정 인증 메일";
								String content = "링크를 클릭해주세요. http://localhost/emailConfirmSuccess?per_email=" + sendEmail;
								hhs.mailSending(tomail, title, content);
								mav.addObject("email", sendEmail);
								session.invalidate(); // 세션 만
								view = "emailConfirmOffer";
						}
					} // 기업회원 if end
				}
				}// bmap null end
			}else { // 회원번호가 null 이라면
				System.out.println("로그인에 실패");
				mav.addObject("loginFailed", loginFailed);
				view = "loginForm";
			} // no null if end
		} else {
			System.out.println("아이디 없음");
			mav.addObject("noEmail", noEmail);
			view = "loginForm";
		} // email null if end
		mav.setViewName(view);
		return mav;
	} // loginProcess End

	// 지예 메일
	public ModelAndView emailConfirmSuccess(HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		String email = request.getParameter("per_email");
		System.out.println("email: " + email);
		mav.addObject("email", email);
		int result = jDao.emailConfirmSuccess(email);
		System.out.println(result);
		view = "emailConfirmSuccess";
		mav.setViewName(view);
		return mav;
	} // emailConfirmSuccess End

	// 지예 관리자 로그인
	public ModelAndView adminLogin(String adm_no, String adm_pw) {
		mav = new ModelAndView();
		String view = null;
		HashMap<String, Object> hmap = new HashMap<>();
		hmap.put("adm_no", adm_no);
		hmap.put("adm_pw", adm_pw);
		hmap = jDao.adminLogin(hmap);
		System.out.println("확인!!!!!!!" + hmap);
		if (hmap != null) {
			String no = (String) hmap.get("ADM_NO");
			session.setAttribute("no", no);
			view = "adminPage";
		} else {
			view = "adminLoginForm";
		}
		mav.setViewName(view);
		return mav;
	} // adminLogin End

	// 지예 로그아웃
	public ModelAndView logout() {
		mav = new ModelAndView();
		session.invalidate();
		mav.setViewName("redirect:/");
		return mav;
	} // logout End

	// 지예 최근 예약 목록
	public ModelAndView recentMyBookingList(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		String no = (String) session.getAttribute("no");
		HashMap<String, Object> hmap = new HashMap<>();
		List<HashMap<String, Object>> hList = new ArrayList<HashMap<String, Object>>();
		StringBuilder sb = new StringBuilder();
		hmap.put("no", no);
		hList = jDao.recentMyBookingList(hmap);
		System.out.println(hList);
		for (int i = 0; i < hList.size(); i++) {
			sb.append("<tr><td><a href='./myBookingDetail?" + hList.get(i).get("BK_NO") + "'>"
					+ hList.get(i).get("BK_NO") + "</a></td>");
			sb.append("<td>" + hList.get(i).get("BUS_NAME") + "</td>");
			sb.append("<td>" + hList.get(i).get("PTY_NAME") + "</td>");
			sb.append("<td>" + hList.get(i).get("PET_NAME") + "</td>");
			sb.append("<td>" + hList.get(i).get("PER_NAME") + "</td>");
			sb.append("<td>" + hList.get(i).get("BK_TIME") + "</td>");
			sb.append("<td>" + hList.get(i).get("VS_START") + "</td>");
			sb.append("<td name='chk'>" + hList.get(i).get("BK_CHK") + "</td></tr>");
		} // for End
		mav.addObject("hList", sb);
		view = "recentMyBookingList";
		return mav;
	}// recentMyBookingList End

	// 개인예약상세정보
	public ModelAndView myBookingDetail(HttpServletRequest request) {
		this.request = request;
		mav = new ModelAndView();
		String view = null;
		String bk_no = request.getQueryString();
		System.out.println("디테일 bk_no=" + bk_no);
		HashMap<String, Object> hmap = new HashMap<>();
		hmap = jDao.myBookingDetail(bk_no);
		String pet_no = hmap.get("PET_NO").toString();

		List<HashMap<String, Object>> mList = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> tList = new ArrayList<HashMap<String, Object>>();
		mList = jDao.getMenu(bk_no);
		tList = jDao.getPetDetail(pet_no);
		System.out.println("tList" + tList);
		System.out.println("mList=" + mList);
		System.out.println("해시맵 너 오니? " + hmap);
		StringBuilder sb = new StringBuilder();

		// 메뉴
		for (int i = 0; i < mList.size(); i++) {
			if (mList.size() - i == 1) {
				sb.append("<span>" + mList.get(i).get("MENU_NAME") + "</span>");
			} else {
				sb.append("<span>" + mList.get(i).get("MENU_NAME") + ", </span>");
			}
			mav.addObject("mList", sb);
		} //for End

		// 반려동물상세
		for (int i = 0; i < tList.size(); i++) {
			System.out.println(tList.get(i).get("PCL_NAME") + " " + tList.get(i).get("PDT_CTT"));
			sb.append("<tr><td>" + tList.get(i).get("PCL_NAME") + "</td><td>" + tList.get(i).get("PDT_CTT")
					+ "</td></tr>");
			mav.addObject("tList", sb);
		}//for End

		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(hmap);
		mav.addObject("result", json);
		view = "myBookingDetail";
		mav.setViewName(view);
		return mav;
	} // myBookingDetail End

	// 기업 공지사항 리스트
	public ModelAndView businessNoticeList(HttpSession session, Integer pageNum) {
		this.session = session;
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
		nList = jDao.businessNotice(hmap);
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

	// 페이징
	private String getPaging(int pageNum, HttpSession session) { // 현재 페이지 번호
		String no = session.getAttribute("no").toString();
		System.out.println("getPaging=" + no);
		int maxNum = jDao.getBusinessNoticeCount(no); // 전체 글의 개수
		int listCount = 10; // 페이지당 글의 수
		int pageCount = 5; // 그룹당 페이지 수 [1] [2] [3] [4] [5] ▶ [6] [7]....
		String boardName = "businessNoticeList"; // 게시판이 여러 개일 때 쓴다.
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, boardName);
		return paging.makeHtmlPaging();
	} // method End


	// 기업공지사항갯수확인
	private String getSearchBusinessNoticeCount(HttpSession session, String select, String search, int pageNum) {
		this.session = session;
		HashMap<String, Object> hmap = new HashMap<>();
		String no = session.getAttribute("no").toString();
		hmap.put("no", no);
		hmap.put("select", select);
		hmap.put("search", search);
		System.out.println("hmap= " + hmap);
		int maxNum = 0;
		if(select.equals("전체")) maxNum = jDao.getSearchBusinessAllNoticeCount(hmap); // 전체 글의 개수
		else maxNum = jDao.getSearchBusinessNoticeCount(hmap); // 전체 글의 개수
		int listCount = 10; // 페이지당 글의 수
		int pageCount = 5; // 그룹당 페이지 수 [1] [2] [3] [4] [5] ▶ [6] [7]....
		String boardName = "searchBusinessNotice"; // 게시판이 여러 개일 때 쓴다.
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, boardName);
		return paging.makeHtmlSearchPaging(hmap);
	} // method End

	// 기업공지사항상세
	public ModelAndView businessNoticeDetail(HttpServletRequest request) {
		this.request = request;
		mav = new ModelAndView();
		String view = null;
		String no = request.getQueryString(); // 리스트에서 클릭하여 받아 온 게시글 번호
		HashMap<String, Object> hmap = new HashMap<>();
		hmap.put("bbo_no", no);
		System.out.println(hmap);
		hmap = jDao.noticeDetailPage(hmap);
		System.out.println("hmap=" + hmap);
		String bct_name = hmap.get("BCT_NAME").toString();
		String bbc_name = hmap.get("BBC_NAME").toString();
		String bbo_title = hmap.get("BBO_TITLE").toString();
		String bbo_ctt = hmap.get("BBO_CTT").toString();
		String bbo_date = hmap.get("BBO_DATE").toString();
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
		this.session = session;
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
		if(search.equals("전체") && search=="") {
			businessNoticeList(session, pageNum);
		}else if(select.equals("전체")) {
			nList = jDao.searchBusinessAllNotice(hmap);
		}else nList = jDao.searchBusinessNotice(hmap);
		System.out.println("searh nList="+nList);
		if(nList.size()<1) {
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
				+ "<strong>'"+search+"'</strong>에 대한 검색 결과입니다</div>");
		mav.addObject("nList", sb);
		mav.addObject("paging", getSearchBusinessNoticeCount(session, select, search, pNo));
		view = "businessNoticeList";
		mav.setViewName(view);
		return mav;
	} // searchBusinessNotice End

	// 기업공지사항등록
	public ModelAndView businessNoticeInsert(HttpSession session, String bct_code, int bbc_no, String bbo_title,
			String bbo_ctt) {
		this.session = session;
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
		int result = jDao.businessNoticeInsert(hmap);
		System.out.println("what's the result? " + result);
		if (result != 0) {
			System.out.println("글쓰기 성공~");
			view = "redirect:businessNoticeList";
		} else {
			System.out.println("글쓰기 실패 ㅠㅠ");
			view = "businessNoticeWriteForm";
		}
		mav.setViewName(view);
		return mav;
	} // businessNoticeInsert End

	// 기업공지사항수정폼
	public ModelAndView businessNoticeUpdateForm(HttpServletRequest request) {
		this.request = request;
		mav = new ModelAndView();
		String view = null;
		String no = request.getQueryString();
		System.out.println(no);
		HashMap<String, Object> hmap = new HashMap<>();
		hmap = jDao.businessNoticeUpdateForm(no);
		System.out.println("잘 가져 오니? " + hmap);
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
		this.request = request;
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
		int result = jDao.businessNoticeUpdate(hmap);
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
		this.request = request;
		mav = new ModelAndView();
		String view = null;
		String alert = null;
		int result = 0;
		if (request.getParameter("bbo_no") != null) {
			Integer bbo_no = Integer.parseInt(request.getParameter("bbo_no"));
			result = jDao.businessNoticeDelete(bbo_no);
			System.out.println("삭제 결과는? " + result);
			if (result != 0) {
				System.out.println("두구두구두구두");
				view = "redirect:businessNoticeList";
			} else {
				System.out.println("삭제 실패~ ");
				view = "redirect:businessNoticeDetail?" + bbo_no;
				alert = "alert(삭제를 실패하였습니다.);";
				mav.addObject("alert", alert);
			}

		} else {
			Integer bbo_no = Integer.parseInt(request.getQueryString());
			System.out.println("확인=" + bbo_no);
			result = jDao.businessNoticeDelete(bbo_no);
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

	// 기업상세페이지 하단 업체 기본 정보
	public ModelAndView businessBasicInfo(HttpServletRequest request) {
		this.request = request;
		mav = new ModelAndView();
		String view = null;
		String bus_no = request.getParameter("bus_no");
		String bct_code = request.getParameter("bct_code");
		//String bus_no = "B1000001";
		//String bct_code = "B";
		HashMap<String, Object> hmap = new HashMap<>();
		HashMap<String, Object> tMap = new HashMap<>(); // 태그 셀렉트
		List<HashMap<String, Object>> tList = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		hmap.put("bus_no", bus_no);
		hmap.put("bct_code", bct_code);
		hmap = jDao.businessBasiceInfo(hmap);
		System.out.println("Do i get the " + hmap + "?");

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

		if (hmap != null) {
			tMap.put("bus_no", bus_no);
			tMap.put("bct_code", bct_code);
			tList = jDao.selectTag(tMap);
			System.out.println("show me the tag's List=" + tList);
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

	public ModelAndView businessGallery(HttpServletRequest request) {
		this.request = request;
		mav = new ModelAndView();
		String view = null;
		String bus_no = request.getParameter("bk_no");
		String bct_code = request.getParameter("bct_code");
		//String bus_no = "B1000097";
		//String bct_code = "M";
		HashMap<String, Object> hmap = new HashMap<>();
		List<HashMap<String, Object>> gList = new ArrayList<HashMap<String, Object>>();
		hmap.put("bus_no", bus_no);
		hmap.put("bct_code", bct_code);
		gList = jDao.businessGallery(hmap);
		StringBuilder sb = new StringBuilder();
		System.out.println("gList=" + gList);
		for (int i = 0; i < gList.size(); i++) {
			sb.append("<div class='photo' float:left;'><img style='height:200px; weight:200px;' class='card-img-top' src='" + gList.get(i).get("GLR_LOC") + gList.get(i).get("GLR_FILE") + "' /></div>");
		}
		mav.addObject("gList", sb);
		view = "businessGallery";
		mav.setViewName(view);
		return mav;
	}// businessGallery

	public ModelAndView unconfirmBusiness(HttpSession session) {
		this.session = session;
		mav = new ModelAndView();
		String view = null;
		StringBuilder sb = new StringBuilder();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<>();
		bList = jDao.getBusinessList();
		System.out.println("bList=" + bList);
		for (int i = 0; i < bList.size(); i++) {
			sb.append("<tr><td><a href='./chkLicense?bus_no=" + bList.get(i).get("BUS_NO") + "&bct_code="
					+ bList.get(i).get("BCT_CODE") + "'>" + bList.get(i).get("BUS_NO") + "</a></td>");
			sb.append("<td>" + bList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("BUS_EMAIL") + "</td>");
			sb.append("<td>" + bList.get(i).get("BUS_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("BUS_CEO") + "</td>");
			sb.append("<td>" + bList.get(i).get("BUS_LCNO") + "</td>");
			sb.append("<td>" + bList.get(i).get("BUS_PHONE") + "</td>");
			sb.append("<td>" + bList.get(i).get("BUS_ADDR") + " " + bList.get(i).get("BUS_ADDR2") + "</td>");
			sb.append("<td>" + bList.get(i).get("BUS_TIME") + "</td>");

			mav.addObject("bList", sb);
		}
		view = "unconfirmBusiness";
		mav.setViewName(view);
		return mav;
	} // unconfirmBusiness end

	public ModelAndView chkLicense(HttpServletRequest request) {
		this.request = request;
		mav = new ModelAndView();
		String view = null;
		String bus_no = request.getParameter("bus_no");
		String bct_code = request.getParameter("bct_code");
		HashMap<String, Object> bMap = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		bMap.put("bus_no", bus_no);
		bMap.put("bct_code", bct_code);
		if (bus_no != null && bct_code != null) {
			bMap = jDao.getBusinessInfo(bMap);
			System.out.println(bMap);
			String bct_name = bMap.get("BCT_NAME").toString();
			String bus_name = bMap.get("BUS_NAME").toString();
			String bus_phone = bMap.get("BUS_PHONE").toString();
			String bus_email = bMap.get("BUS_EMAIL").toString();
			String glr_loc = bMap.get("GLR_LOC").toString();
			String glr_file = bMap.get("GLR_FILE").toString();
			mav.addObject("bus_no", bus_no);
			mav.addObject("bct_name", bct_name);
			mav.addObject("bus_name", bus_name);
			mav.addObject("bus_phone", bus_phone);
			mav.addObject("bus_email", bus_email);
			mav.addObject("glr_loc", glr_loc);
			mav.addObject("glr_file", glr_file);
		}
		view = "chkLicense";
		mav.setViewName(view);
		return mav;
	} // chkLicense end

	public ModelAndView confirmLicense(HttpServletRequest request) {
		this.request = request;
		mav = new ModelAndView();
		String view = null;
		String bus_no = request.getQueryString();
		if (bus_no != null) {
			int result = jDao.confirmLicense(bus_no);
			System.out.println("확인!!!!" + result);
			String alert = "alert('승인이 완료되었습니다.')";
			mav.addObject("alert", alert);
			view = "redirect:unconfirmBusiness";
		} else {
			view = "chkLicense";
		}
		mav.setViewName(view);
		return mav;
	} // confirmLicense end

}// Class End
