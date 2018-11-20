package com.teamx.respets.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.teamx.respets.bean.Personal;
import com.teamx.respets.bean.RandomTB;
import com.teamx.respets.dao.HyunHwiDao;
import com.teamx.respets.dao.SunnyDao;

@Service
public class HyunHwiService {

	@Autowired
	private HyunHwiDao hDao;
	private ModelAndView mav;
	@Autowired
	private JavaMailSender mailSender; // 메일보낼시 필요
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	private FileOutputStream fos; // 파일등록시 필요
////////////////////////////////////////////////////////////////////////////////////////////////////
	// 이메일, 비밀번호 찾기//
	// 현휘; findMyIdForm에서 가져온 type, per_name, per_phone를 들고 회원테이블로 이동

	public ModelAndView findMyId(Personal mb, HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		char type = request.getParameter("type").charAt(0); // 개인, 기업 의 타입을 담는 변수
		String name = mb.getPer_name(); // bean에서 이름을 빼서 변수에 담아준다.
		String phone = mb.getPer_phone(); // 번호도 담아준다.

		Map<String, Object> map = new HashMap<>();
		// map을 이용해 한번에 3가지의 변수를 dao에 넘겨준다.
		map.put("name", name);
		map.put("phone", phone);
		map.put("type", type);
		map = hDao.selectUser(map);

		if (map != null) {
			String email = (String) map.get("EMAIL");
			String[] word = email.split("@"); // @ 를기준으로 문자열을 잘라 배열에 넣어준다.
			String[] perid = word[0].split(""); // 잘린 문자열 중 메일부분이 아닌 아이디 부분을 다시 잘라준다.
			String findEmail = ""; // 잘린 문자열을 합쳐줄 변수선언.
			for (int i = perid.length - 3; i < perid.length; i++) {
				// 아이디 중 뒷 문자 3개를 찾기 위해 돌리는 반복문.
				perid[i] = "*"; // 찾은 문자들을 치환.
			}
			for (int i = 0; i < perid.length; i++) {
				// 잘랐던 문자들을 하나로 합치기 위해 돌리는 반복문.
				findEmail += perid[i]; // 문자열 합체.
			}
			email = findEmail + "@" + word[1]; // 작업하기 위해 잘랐던 모든 문자열을 합체.
			mav.addObject("showEmail", email); // ***로 치환된 이메일
			mav.addObject("email", (String) map.get("EMAIL")); // 정상 이메일
			mav.addObject("type", type); // 회원 종류
			view = "findMyPwForm";
		} else { // 맞는 정보가 없을 경우.
			String text = "<script>alert('찾으시는 아이디 정보가 없습니다.');</script>";
			mav.addObject("none", text);
			view = "findMyIdForm";
		}
		mav.setViewName(view);
		return mav;
	}

	// 현휘; 찾은 이메일로 비밀번호 변경 폼을 보내주는 메소드
	public ModelAndView findMyPw(HttpServletRequest request) {
		mav = new ModelAndView();
		String userId = request.getParameter("email");
		String userType = request.getParameter("type");
		String view = null;
		String temp = "";
		Random rnd = new Random();
		for (int i = 0; i < 6; i++) {
			int rIndex = rnd.nextInt(3); // 0,1,2 범위 내 랜덤하게 숫자를 부르겠다는 의미.
											// switch 문을 통해 숫자,소문자,대문자를 무작위로 조합하기 위한 작업.
			switch (rIndex) {
			case 0:
				// a-z 소문자, a의 아스키 값은 '97'
				temp += ((char) ((int) (rnd.nextInt(26)) + 97));
				// 알파벳 개수만큼만 숫자를 랜덤으로 뽑아냄
				// 그리고 소문자 첫번째 자리의 아스키 값을 더해준다.
				// 마지막으로 해당 수를 문자열로 변경.
				break;
			case 1:
				// A-Z 대문자, A의 아스키 값은 '65'
				temp += ((char) ((int) (rnd.nextInt(26)) + 65));
				break;
			case 2:
				// 0-9
				temp += ((rnd.nextInt(10)));
				break;
			}
		}
		RandomTB rtb = new RandomTB();
		rtb.setRnd_email(userId);
		rtb.setRnd_code(temp);
		rtb.setRnd_type(userType.charAt(0)); // 회원 번호의 맨 앞자리를 불러옴. 회원 구분을 위해. (P,B,M)
		int result = hDao.findMyPw(rtb); // 가지고 있는 정보를 가지고 랜덤 테이블 접근.
		if (result != 0) { // insert에 성공 했을 경우
			String tomail = rtb.getRnd_email(); // 메일을 받는 이메일 주소
			String title = "비밀번호 변경 페이지"; // 메일의 제목
			String content = "http://localhost:8080/resetMyPwForm?per_email=" + rtb.getRnd_email() + "&code="
					+ rtb.getRnd_code() + "&type=" + rtb.getRnd_type();
			mailSending(tomail, title, content); // 메일 보내기 메소드로 이동
		}
		String findPw = "<script>alert('이메일로 비밀번호 재설정 링크를 보냈습니다.');</script>";
		mav.addObject("findPw", findPw);
		view = "index";
		mav.setViewName(view);
		return mav;
	}

	// 현휘; 메일 보내는 메소드
	// 해당 메소드를 사용하기 위해서는,
	// 메일을 받는사람(tomail), 메일의 제목(title), 메일의 내용(content)을 가지고 넘어와야 한다.
	public void mailSending(String tomail, String title, String content) {
		String setfrom = "respets2018@gmail.com"; // 회원들에게 메일을 보내주는 관리자 메일주소를 적어준다.
		try {
			MimeMessage message = mailSender.createMimeMessage(); // 메세지 만들거야.
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8"); // 근데 그 메세지 인코딩을 "UTF-8"로 할거야.
			// 인코딩이 필요 없다면, (message) 만 담아서 보내 줄 수 있다. 그리고 첨부파일도 보내고 싶을 때는 (message, true)
			// 형식으로도 사용된다. (검색해보기)

			// 작업을 끝낸 메세지 헬퍼에 정보들을 담아준다.
			messageHelper.setFrom(setfrom); // 보내는사람 생략하거나 하면 정상작동을 안함
			messageHelper.setTo(tomail); // 받는사람 이메일
			messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
			messageHelper.setText(content); // 메일 내용

			mailSender.send(message); // 메일 전송.
										// 위에서도 쓰였던 mailSender 는 메이븐에서 가져온 api.
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// 현휘; 신원을 확인하는 메소드, 보낸 랜덤값과 받은 사용자가 맞는지 확인한다.
	public ModelAndView resetMyPwForm(HttpServletRequest request) {
		String view = null;
		mav = new ModelAndView();
		// 메일을 보낼 때 함께 담아서 보내진 email, code, type
		String email = request.getParameter("per_email");
		String code = request.getParameter("code");
		String type = request.getParameter("type");
		RandomTB rtb = new RandomTB();
		rtb.setRnd_email(email);
		rtb.setRnd_code(code);
		// 랜덤 코드가 맞는지 확인한다.
		rtb = hDao.checkRcode(rtb);
		if (rtb != null) { // 신원이 확인되면 유효시간을 확인한다.
			long minute = dateCheck(rtb);
			if (minute < 30) { // 메일이 보내진 지 30분이 되지 않았으면 비밀번호 변경 폼으로 이동.
				mav.addObject("email", email);
				mav.addObject("code", code);
				mav.addObject("type", type);
				view = "resetMyPwForm";
			} else { // 30분이 지났으면 응답시간이 초과됬기에 다시 메일을 보내야 한다.
				String text = "<script>alert('응답시간이 초과되었습니다.');</script>";
				mav.addObject("alert", text);
				view = "index";
			}
		} else { // 랜덤값 자체가 맞지 않으면 존재하지 않는 링크라고 알려준다.
			String text = "<script>alert('존재하지 않는 링크입니다.');</script>";
			mav.addObject("alert", text);
			view = "index";
		}
		mav.setViewName(view);
		return mav;
	}

	// 현휘; 유효시간을 확인하는 메소드, minute 으로 계산해준다.
	private long dateCheck(RandomTB rtb) {
		long rand = rtb.getRnd_date().getTime();
		Date curDate = new Date();
		long curDateTime = curDate.getTime();
		long minute = (curDateTime - rand) / 60000;
		return minute;
	}

	// 현휘; 변경한 비밀번호 값으로 회원정보를 변경한다.
	public ModelAndView updateMyPw(HttpServletRequest request, Personal ps) {
		mav = new ModelAndView();
		String type = request.getParameter("type"); // 개인,기업 구분
		if (type.equals("P")) { // 개인일 경우
			hDao.resetPerPw(ps);
		} else if (type.equals("B")) { // 기업일 경우
			System.out.println(type);
			hDao.resetBusPw(ps);
		}
		hDao.deleteRcode(ps.getPer_email()); // 변경이 완료되면 랜덤값을 지워준다.
		String updateMyPw = "<script>alert('비밀번호가 변경되었습니다.');</script>";
		mav.addObject("updateMyPw", updateMyPw);
		String view = "loginForm";
		mav.setViewName(view);
		return mav;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////	
	// 업종 등록 창 띄우기//
	// 현휘; 기업이 가지고 있는 서비스들을 리스트로 뽑아오는 메소드 (혜연이 메소드 이용시 삭제될 예정)
	public ModelAndView servicePage() {
		mav = new ModelAndView();
		String no = (String) session.getAttribute("no");
		List<Map<String, Object>> list;
		list = hDao.selectBusinessService(no);
		String business = businessListShow(list);
		mav.addObject("servList", business);
		mav.setViewName("servicePage");
		return mav;
	}

	// 검색해온 서비스 리스트 뽑아주는 메소드, 삭제예정 //기업 상세정보를 위해 필요-serviceDetail
	private String businessListShow(List<Map<String, Object>> list) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			String bctcode = (String) list.get(i).get("BCT_CODE");
			String bctname;
			if (bctcode.equals("B")) {
				bctname = "미용";
			} else if (bctcode.equals("M")) {
				bctname = "병원";
			} else {
				bctname = "호텔";
			}
			sb.append("<div class='header'><a href='serviceDetail?bct_code=" + bctcode + "'><div class='con'>"
					+ "<p> 업종 : " + bctname + "</p><br/>" + "</div></a></div>");
		}
		return sb.toString();
	}

	// 현휘; 기업의 업종등록 신청 전, 가지고 있는 업종이 있는지 검색
	public ModelAndView serviceInsertForm() {
		String bus_no = (String) session.getAttribute("no");
		List<Map<String, Object>> list;
		list = hDao.serviceInsertForm(bus_no); // 기업이 가진 서비스 검색
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
		map = hDao.searchBUS(bus_no); // 기업 정보 검색
		String bus_name = (String) map.get("BUS_NAME");
		String bus_phone = (String) map.get("BUS_PHONE");

		String time = timeSelect(); // 기업의 운영시간 선택 select tag
		String lunch = lunchSelect(); // 기업의 점심시간 선택 select tag
		String holiday = holidaySelect(); // 기업의 휴일 선택 select tag

		mav.addObject("cnt", list.size()); // 서비스의 우선순위를 정해주기 위한 카운트
		mav.addObject("bct_code", code);
		mav.addObject("bus_name", bus_name);
		mav.addObject("bus_phone", bus_phone);
		mav.addObject("codeCheck", codeSelectBut);
		mav.addObject("time", time);
		mav.addObject("lunch", lunch);
		mav.addObject("holiday", holiday);
		mav.addObject("medical", medical);
		mav.addObject("beauty", beauty);
		mav.addObject("hotel", hotel);
		mav.setViewName("serviceInsertForm");
		return mav;
	}

	// 현휘; 업종 선택 버튼을 생성 (부트스트랩 사용시 탭으로 변경)
	// 현휘; 업종 등록 할 때 필요한 업종 선택 버튼을 만들어 준다. (부트스트랩 이용시 탭으로 변경)
	private String codeSelectBut() {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<div id='con'><div id='medical_div'>&nbsp;<input type='radio' name='code' value='M' onclick='checkCode(this)'/> 병원 &nbsp;</div>");
		sb.append(
				"<div id='beauty_div'>&nbsp;<input type='radio' name='code' value='B' onclick='checkCode(this)'/> 미용 &nbsp;</div>");
		sb.append(
				"<div id='hotel_div'>&nbsp;<input type='radio' name='code' value='H' onclick='checkCode(this)'/> 호텔 &nbsp;</div></div>");
		sb.append("<br/>");
		return sb.toString();
	}

	// 현휘; 해당 업종에 필요한 메뉴를 체크박스로 만들어 준다.
	private String menuSelect(String code) {
		StringBuilder sb = new StringBuilder();
		List<Map<String, Object>> list;
		list = hDao.selectMENU(code); // 업종이 가지고 있는 메뉴를 검색
		sb.append("제공서비스: ");
		for (int i = 0; i < list.size(); i++) { // 검색된 메뉴들을 체크박스로 생성
			String menu_name = (String) list.get(i).get("MENU_NAME");
			if (menu_name.equals("고양이무마취")) {
				sb.append("<input type='checkbox' name='cat_tag' value='" + menu_name + "'/>&nbsp;" + menu_name);
			} else if (menu_name.equals("가위컷")) {
				sb.append("<input type='checkbox' name='dog_tag' value='" + menu_name + "'/>&nbsp;" + menu_name);
			} else {
				sb.append("<input type='checkbox' name='tag_name' value='" + menu_name + "'/>&nbsp;" + menu_name);
			}
		}
		sb.append("<br/>");
		return sb.toString();
	}

	// 현휘; 해당 업종에 필요한 동물종를 체크박스로 만들어 준다.
	private String animalSelect(String code) {
		StringBuilder sb = new StringBuilder();
		sb.append("-서비스 가능한 동물종류: ");
		if (code.equals("M")) { // 병원에서 제공될 수 있는 동물 종류
			sb.append("<input type='checkbox' name='animal_code' value='소형견'/> 소형견");
			sb.append("<input type='checkbox' name='animal_code' value='중형견'/> 중형견");
			sb.append("<input type='checkbox' name='animal_code' value='대형견'/> 대형견");
			sb.append("<input type='checkbox' name='cat_code' value='고양이'/> 고양이");
			sb.append("<input type='checkbox' name='animal_code' value='소동물'/> 소동물");
			sb.append("<input type='checkbox' name='animal_code' value='파충류'/> 파충류");
			sb.append("<input type='checkbox' name='animal_code' value='가축'/> 가축");
		} else if (code.equals("B")) { // 미용에서 제공될 수 있는 동물 종류
			sb.append("<input type='checkbox' name='animal_code' value='소형견'/> 소형견");
			sb.append("<input type='checkbox' name='animal_code' value='중형견'/> 중형견");
			sb.append("<input type='checkbox' name='animal_code' value='대형견'/> 대형견");
			sb.append("<input type='checkbox' name='cat_code' value='고양이'/> 고양이");
		} else if (code.equals("H")) { // 호텔에서 제공될 수 있는 동물 종류
			sb.append("<input type='checkbox' name='animal_code' value='소형견'/> 소형견");
			sb.append("<input type='checkbox' name='animal_code' value='중형견'/> 중형견");
			sb.append("<input type='checkbox' name='animal_code' value='대형견'/> 대형견");
			sb.append("<input type='checkbox' name='cat_code' value='고양이'/> 고양이");
			sb.append("<input type='checkbox' name='animal_code' value='소동물'/> 소동물");
		}
		return sb.toString();
	}

	// 현휘; 기업이 가지고 있는 업종이면 선택 버튼 삭제
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

	// 현휘; 기업의 운영 시간을 선택 할 수 있는 select tag 생성
	private String timeSelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("<select name=\"am_open\">\n" + "<script>\n" + "for (i = 0; i < 48; i++) {\n" + "var hour = '';\n"
				+ "var min = '00';\n" + "if ((Math.ceil(i / 2)) < 13) {\n" + "if((Math.floor(i / 2)) < 10){\n"
				+ "hour = '0'+(Math.floor(i / 2));\n" + "}\n" + "else {\n" + "hour = (Math.floor(i / 2));\n" + "}\n"
				+ "} else {\n" + "hour = (Math.floor(i / 2));\n" + "}\n" + "if (i % 2 != 0) {\n" + "min = '30';\n"
				+ "}\n" + "document.write('<option value=' + hour + min + '>' + hour +\":\"+ min + '</option>');\n"
				+ "}\n" + "</script>\n" + " </select> ~\n" + " <select name=\"pm_close\">\n" + "<script>\n"
				+ "for (i = 0; i < 48; i++) {\n" + "var hour = '';\n" + "var min = '00';\n"
				+ "if ((Math.ceil(i / 2)) < 13) {\n" + "if((Math.floor(i / 2)) < 10){\n"
				+ "hour = '0'+(Math.floor(i / 2));\n" + "}\n" + "else {\n" + "hour = (Math.floor(i / 2));\n" + "}\n"
				+ "} else {\n" + "hour = (Math.floor(i / 2));\n" + "}\n" + "if (i % 2 != 0) {\n" + "min = '30';\n"
				+ "}\n" + "document.write('<option value=' + hour + min + '>' + hour +\":\"+ min + '</option>');\n"
				+ "}\n" + "</script>\n" + " </select>");
		return sb.toString();
	}

	// 현휘; 기업의 점심 시간을 선택할 수 있는 select tag 생성
	private String lunchSelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("<select name=\"lunch_open\">\n" + "<script>\n" + "for (i = 0; i < 48; i++) {\n" + "var hour = '';\n"
				+ "var min = '00';\n" + "if ((Math.ceil(i / 2)) < 13) {\n" + "if((Math.floor(i / 2)) < 10){\n"
				+ "hour = '0'+(Math.floor(i / 2));\n" + "}\n" + "else {\n" + "hour = (Math.floor(i / 2));\n" + "}\n"
				+ "} else {\n" + "hour = (Math.floor(i / 2));\n" + "}\n" + "if (i % 2 != 0) {\n" + "min = '30';\n"
				+ "}\n" + "document.write('<option value=' + hour + min + '>' + hour +\":\"+ min + '</option>');\n"
				+ "}\n" + "</script>\n" + " </select> ~\n" + " <select name=\"lunch_close\">\n" + "<script>\n"
				+ "for (i = 0; i < 48; i++) {\n" + "var hour = '';\n" + "var min = '00';\n"
				+ "if ((Math.ceil(i / 2)) < 13) {\n" + "if((Math.floor(i / 2)) < 10){\n"
				+ "hour = '0'+(Math.floor(i / 2));\n" + "}\n" + "else {\n" + "hour = (Math.floor(i / 2));\n" + "}\n"
				+ "} else {\n" + "hour = (Math.floor(i / 2));\n" + "}\n" + "if (i % 2 != 0) {\n" + "min = '30';\n"
				+ "}\n" + "document.write('<option value=' + hour + min + '>' + hour +\":\"+ min + '</option>');\n"
				+ "}\n" + "</script>\n" + " </select>");
		return sb.toString();
	}

	// 현휘; 기업의 휴일을 선택할 수 있는 select tag 생성
	private String holidaySelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("<input type=\"checkbox\" name=\"holiday\" value=\"월요일\" /> 월요일 \n"
				+ "<input type=\"checkbox\" name=\"holiday\" value=\"화요일\" /> 화요일 \n"
				+ "<input type=\"checkbox\" name=\"holiday\" value=\"수요일\" /> 수요일 \n"
				+ "<input type=\"checkbox\" name=\"holiday\" value=\"목요일\" /> 목요일 \n"
				+ "<input type=\"checkbox\" name=\"holiday\" value=\"금요일\" /> 금요일 \n"
				+ "<input type=\"checkbox\" name=\"holiday\" value=\"토요일\" /> 토요일 \n"
				+ "<input type=\"checkbox\" name=\"holiday\" value=\"일요일\" /> 일요일 \n"
				+ "<input type=\"checkbox\" name=\"holiday\" value=\"공휴일\" /> 공휴일 \n");
		return sb.toString();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////	
	// 업종 등록//
	// 현휘; 가져온 업종 정보들을 등록하는 메소드
	@Transactional
	public ModelAndView serviceInsert(MultipartHttpServletRequest request) {
		fileWriter(request); // 사진 등록 (사업장사진)
		weekSet(request); // 스케줄 등록 (영업시간,점심시간,고정휴무일)
		checkTag(request); // 제공서비스 등록
		insertPrice(request); // 기업이 지정한 서비스 가격 등록
		addBusinessCode(request); // 업종 등록
		mav.setView(new RedirectView("/servicePage"));
		// mav.setViewName("/servicePage");
		return mav;
	}

	// 현휘; 기업이 등록한 사진을 등록
	private void fileWriter(MultipartHttpServletRequest multi) {
		String no = (String) session.getAttribute("no");
		System.out.println(no);
		uploadFiles(multi, no); // 가져온 정보를 클래스로 보내서 map으로 받아온다.
	}

	public void uploadFiles(MultipartHttpServletRequest multi, String no) {
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
			hDao.insertFile(map);
		}
	}

	// 현휘; 기업이 등록한 업무시간, 점심시간, 휴일을 등록
	private void weekSet(MultipartHttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map = holidayMap(request); // 휴일을 map에 담아오는 메소드
		hDao.serviceInsertBFX(map); // 기업의 고정스케줄 등록

		String bct_code = request.getParameter("bct_code"); // 업종 코드
		System.out.println(bct_code);
		String no = (String) session.getAttribute("no"); // 기업 번호
		String[] holiday = request.getParameterValues("holiday"); // 휴일은 여러개일 수도 있으니까 배열로 받는다.
		String open = request.getParameter("am_open"); // 오픈시간
		String close = request.getParameter("pm_close"); // 닫는시간
		String lunch_open = request.getParameter("lunch_open"); // 점심시작시간
		String lunch_close = request.getParameter("lunch_close"); // 점심끝나는시간
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
			if (flag) { // 휴일이 아니라면,
				map.put("bus_no", no);
				map.put("bct_code", bct_code);
				map.put("date", date);
				map.put("am_open", open);
				map.put("am_close", lunch_open);
				map.put("pm_open", lunch_close);
				map.put("pm_close", close);
				hDao.serviceInsertBSD(map); // 기업의 한달스케줄을 등록한다.
			}
			cal.add(Calendar.DATE, 1); // 날짜를 하루 추가해준다.
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 해당 날짜의 요일을 가져온다.
			flag = true; // 깃발을 다시 true로 바꿔준다.
		}
	}

	// 현휘; 기업이 제공하는 서비스 등록
	private void checkTag(MultipartHttpServletRequest request) {
		String code = request.getParameter("bct_code");
		String no = (String) session.getAttribute("no");
		Map<String, Object> map = new HashMap<>();
		String[] tag = request.getParameterValues("tag_name"); // 체크된 태크네임을 가져온다.
		for (int i = 0; i < tag.length; i++) { // 가져온 태그만큼 반복
			String tag_num = hDao.searchTAG(tag[i]); // 태그테이블에 존재하는지 검사, 존재하면 tag_num을 가져온다.
			if (tag_num == null) { // 없다면
				hDao.insertTAG(tag[i]); // 태그테이블에 해당 서비스 등록
				tag_num = hDao.searchTAG(tag[i]); // 등록한 서비스를 다시 검색하여 tag_num을 가져온다.
			}
			map.put("bus_no", no);
			map.put("bct_code", code);
			map.put("tag_num", tag_num);
			hDao.insertBTG(map); // 기업이 제공하는 서비스들을 해시태그 테이블에 등록
		}
	}

	// 현휘; 제공하는 서비스의 가격 등록
	private void insertPrice(MultipartHttpServletRequest request) {
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
				String sort_o = hDao.selectAnimalCode(sort[i]); // 해당 동물의 코드를 검색
				for (int j = 0; j < pet.length; j++) { // 위에서 저장한 태그 수만큼 반복
					map.put("bct_code", code); // 업종코드와
					map.put("tag", pet[j]); // 서비스종류를 맵에 담아서
					String tag_o = hDao.selectMenuNo(map); // 해당 서비스의 번호를 검색
					map.put("bus_no", no); // 기업번호
					map.put("bct_code", code); // 업종코드
					map.put("tag", tag_o); // 태그번호
					map.put("pet", sort_o); // 동물번호
					if (code.equals("M")) {
						map.put("price", "0");
						hDao.insertPrice(map); // 가격등록
					} else {
						map.put("price", price[cnt]);
						if (price[cnt].equals("X")) {
							System.out.println("제공되지 않는 서비스");
						} else {
							hDao.insertPrice(map); // 가격등록
						}
					}
					cnt++;
				}
			}
			// 강아지에게만 제공되는 가위컷 서비스가 없을 경우
		} else if (sort != null && tag != null) {
			for (int i = 0; i < sort.length; i++) { // 동물 종류 수만큼 반복
				String sort_o = hDao.selectAnimalCode(sort[i]); // 동물코드 검색
				for (int j = 0; j < tag.length; j++) { // 태그 수만큼 반복
					map.put("bct_code", code);
					map.put("tag", tag[j]);
					String tag_o = hDao.selectMenuNo(map); // 해당 서비스 번호 검색
					map.put("bus_no", no);
					map.put("bct_code", code);
					map.put("tag", tag_o);
					map.put("pet", sort_o);
					if (code.equals("M")) {
						map.put("price", "0");
						hDao.insertPrice(map); // 가격등록
					} else {
						map.put("price", price[cnt]);
						if (price[cnt].equals("X")) {
							System.out.println("제공되지 않는 서비스");
						} else {
							hDao.insertPrice(map); // 가격등록
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
			String sort_o = hDao.selectAnimalCode(cat_sort); // 무마취 번호 검색
			for (int j = 0; j < pet.length; j++) { // 가지고 있는 서비스 수만큼 반복
				map.put("bct_code", code);
				map.put("tag", pet[j]);
				String tag_o = hDao.selectMenuNo(map); // 서비스 번호 검색
				map.put("bus_no", no);
				map.put("bct_code", code);
				map.put("tag", tag_o);
				map.put("pet", sort_o);
				if (code.equals("M")) {
					map.put("price", "0");
					hDao.insertPrice(map); // 가격등록
				} else {
					map.put("price", price[cnt]);
					if (price[cnt].equals("X")) {
						System.out.println("제공되지 않는 서비스");
					} else {
						hDao.insertPrice(map); // 가격등록
					}
				}
				cnt++;
			}
			// 고양이에게만 제공되는 무마취 서비스가 없을 경우
		} else if (cat_sort != null && tag != null) {
			String sort_o = hDao.selectAnimalCode(cat_sort); // 동물번호 검색
			for (int j = 0; j < tag.length; j++) { // 서비스 수만큼 반복
				map.put("bct_code", code);
				map.put("tag", tag[j]);
				String tag_o = hDao.selectMenuNo(map); // 서비스 번호 검색
				map.put("bus_no", no);
				map.put("bct_code", code);
				map.put("tag", tag_o);
				map.put("pet", sort_o);
				if (code.equals("M")) {
					map.put("price", "0");
					hDao.insertPrice(map); // 가격등록
				} else {
					map.put("price", price[cnt]);
					if (price[cnt].equals("X")) {
						System.out.println("제공되지 않는 서비스");
					} else {
						hDao.insertPrice(map); // 가격등록
					}
				}
				cnt++;
			}
		}
	} // else if (code.equals("H")) { //업종 종류가 호텔일 경우

	// 현휘; 최종적으로 기업이 제공하는 업종을 등록
	private void addBusinessCode(MultipartHttpServletRequest request) {
		String bus_no = (String) session.getAttribute("no");
		String code = request.getParameter("bct_code");
		int cnt = Integer.parseInt(hDao.countSVC(bus_no));
		Map<String, Object> map = new HashMap<>();
		map.put("bus_no", bus_no);
		map.put("bct_code", code);
		map.put("cnt", cnt+1);
		hDao.addBusinessCode(map); // 업종 등록
	}

////////////////////////////////////////////////////////////////////////////////////////////////////
	// 기업의 상세정보 페이지//
	// 현휘; 기업의 상세정보 페이지를 열기 위한 메소드
	public ModelAndView serviceDetail(HttpServletRequest request) {
		String bus_no = (String) session.getAttribute("no");
		Map<String, Object> map = new HashMap<String, Object>();
		map = hDao.searchBUS(bus_no);
		String bct_code = request.getParameter("bct_code"); // 병원,미용,호텔
		System.out.println(bct_code);
		String bus_name = (String) map.get("BUS_NAME");
		String bus_phone = (String) map.get("BUS_PHONE");
		String bct_name = hDao.searchBCTname(bct_code);
		System.out.println(bct_name);
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);

		map = hDao.searchBSD(map); // 업종 하루 스케줄 검색
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
			map = hDao.searchBFX(map); // 업종 고정 스케줄 검색

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
			list = hDao.selectMenuTag(map); // 제공하는 서비스가 있는지 검색해서 해당 서비스의 번호를 가져온다.
			menu = ""; // 메뉴를 담을 변수
			String menu_no = null;
			for (int i = 0; i < list.size(); i++) {
				menu_no = String.valueOf(list.get(i).get("MENU_NO")); // 해당 메뉴의 번호를 가져와서
				if (menu != "") { // 처음 가져오는 메뉴가 아닐 경우
					menu += "/"; // 경계선을 그어준다.
				}
				menu += hDao.selectMenuName(menu_no); // 메뉴의 이름을 검색하여 변수에 담아준다.
			}

			map.put("bus_no", bus_no);
			map.put("bct_code", bct_code);
			List<Map<String, Object>> alist;
			alist = hDao.selectAnimalTag(map); // 기업이 제공하는 동물종류를 검색하여 번호를 가져온다.
			animal = ""; // 동물종류를 담을 변수
			String animal_no = null;
			for (int i = 0; i < alist.size(); i++) {
				animal_no = String.valueOf(alist.get(i).get("PTY_NO")); // 동물번호를 가져와서
				if (animal != "") { // 처음 가져오는 동물이 아닐 경우
					animal += "/"; // 경계선을 그어준다.
				}
				animal += hDao.selectAnimalName(animal_no); // 동물 이름을 검색하여 변수에 담아준다.
			}
		} else {
			workTime = " 등록이 필요합니다.";
			holiday = " 등록이 필요합니다.";
			menu = " 등록이 필요합니다.";
			animal = " 등록이 필요합니다.";
			firstInsert = "first";
		}
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

	// 기업의 상세정보 수정 페이지//
	// 현휘; 기업의 상세정보 수정 페이지를 열기 위한 메소드 //가격등록창 수정해야해
	public ModelAndView serviceUpdateForm(HttpServletRequest request) {
		String bus_no = (String) session.getAttribute("no");
		String bct_code = request.getParameter("bct_code");
		System.out.println(bct_code);
		String top = serviceTopUpdateForm(bus_no); // 이름, 연락처를 보여주는 메소드
		// String work = Time(bct_code, "business", "work"); //영업시간을 보여주는 메소드
		// String lunch = Time(bct_code, "business", "lunch"); //점심시간을 보여주는 메소드
		// String holiday = Holiday(bct_code, "business"); //휴일을 보여주는 메소드
		// String menu = serviceMenuUpdateForm(bus_no, bct_code); //제공서비스와 이용가능한동물을 보여주는
		// 메소드
		String first = request.getParameter("first");
		String work = null;
		String lunch = null;
		String holiday = null;
		String menu = null;
		String animal = null;
		if (first.equals("first")) {
			work = timeSelect(); // 기업의 운영시간 선택 select tag
			lunch = lunchSelect(); // 기업의 점심시간 선택 select tag
			holiday = holidaySelect(); // 기업의 휴일 선택 select tag
			menu = menuSelect(bct_code);
			animal = animalSelect(bct_code);
			mav.addObject("first", first);

		} else {
			work = serviceWorkUpdateForm(bus_no, bct_code);
			lunch = serviceLunchUpdateForm(bus_no, bct_code);
			holiday = serviceHolidayUpdateForm(bus_no, bct_code);
			menu = serviceMenuUpdateForm(bus_no, bct_code);
		}
		String bct_name = null;
		// 가격등록 창을 가져오기 위한 if문
		if (bct_code.equals("H")) {
			mav.addObject("price", "<input type='button' name='H' onclick='priceBox(this)'/> 가격등록");
			mav.addObject("bct_price", "<div id='H'></div>");
			mav.addObject("cat_price", "<div id='H_price'></div>");
			bct_name = "호텔";
		} else if (bct_code.equals("B")) {
			mav.addObject("price", "<input type='button' name='B' onclick='priceBox(this)'/> 가격등록");
			mav.addObject("bct_price", "<div id='B'></div>");
			mav.addObject("cat_price", "<div id='B_price'></div>");
			bct_name = "미용";
		} else if (bct_code.equals("M")) {
			bct_name = "병원";
		}
		mav.addObject("bct_name", bct_name);
		mav.addObject("bct_code", bct_code); // 업종, 병원,미용,호텔
		mav.addObject("top", top);
		mav.addObject("work", work);
		mav.addObject("lunch", lunch);
		mav.addObject("holiday", holiday);
		mav.addObject("menu", menu);
		mav.addObject("animal", animal);
		mav.setViewName("serviceUpdateForm");
		return mav;
	}

	// 현휘; 기업의 수정페이지에서 이름, 연락처를 보여주는 메소드
	private String serviceTopUpdateForm(String bus_no) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = hDao.searchBUS(bus_no); // 기업번호로 검색해서 기존 정보 불러오기
		String name = (String) map.get("BUS_NAME");
		String phone = (String) map.get("BUS_PHONE");
		StringBuilder sb = new StringBuilder();
		sb.append("-업체명: <input type='text' name='bus_name' value='" + name + "' readonly/><br/>");
		sb.append("-연락처: <input type='text' name='bus_phone' value='" + phone + "' readonly/><br/>");
		return sb.toString();
	}

	// 현휘; 기업의 수정페이지에서 영업시간을 보여주는 메소드
	private String serviceWorkUpdateForm(String bus_no, String bct_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		map = hDao.searchBSD(map); // 기업의 하루 스케줄 검색
		String am_open = (String) map.get("AM_OPEN"); // 오픈시간
		String pm_close = (String) map.get("PM_CLOSE"); // 마감시간

		int am_pre = Integer.parseInt(am_open.substring(0, 2)); // 오픈시간 hour
		int am_las = Integer.parseInt(am_open.substring(2, 4)); // 오픈시간 min
		int pm_pre = Integer.parseInt(pm_close.substring(0, 2)); // 마감시간 hour
		int pm_las = Integer.parseInt(pm_close.substring(2, 4)); // 마감시간 min

		StringBuilder sb = new StringBuilder();
		sb.append("<select name=\"am_open\" id=\"open_time\">\n" + "<script>\n" + "	for (var i = 0; i < 48; i++) {\n" + // 반복문을
																														// 48번
																														// 도는
																														// 이유는
																														// 시간,분
																														// 의
																														// 종류가
																														// 총
																														// 48개이기
																														// 떄문이다.
																														// ex)
																														// 1시면
																														// 01:00,
																														// 01:30
																														// 두가지가
																														// 존재한다.
				"		var hour = '';\n" + // 시간은 무조건 값이 들어가기에 ''로 초기화 해준다.
				"		var	min = '00'\n" + // 분은 밑에 if문을 충족하지 못하면 00분이 들어가야 하기에 00으로 초기화 해준다.
				"		if ((Math.ceil(i / 2)) < 11) {\n" + // i를 2로 나누는 이유는 위에서 시간의 종류를 48개로 지정한 이유와 같다. i는 시를 나타내고 시는
															// 두가지의 종류를 가지고 있다.
				"			if((Math.floor(i / 2)) < 10){\n" + // i가 10보다 작을 경우
				"				hour = '0'+(Math.floor(i / 2));\n" + // 한자리 수가 되기에, 앞에 0을 붙여준다. DB에 입력될때는 01 이런식으로 입력되야
																		// 하기 때문이다.
				"			} else {\n" + "				hour = (Math.floor(i / 2));\n" + // 그런게 아닐 경우 그냥 i만 넣어준다.
				"			}\n" + "		} else {\n" + "			hour = (Math.floor(i / 2));\n" + // 나머지는 한자리 수가 나오지
																										// 않기에 바로 i를
																										// 넣어준다.
				"		}\n" + "		if (i % 2 != 0) {\n" + // 만약 홀수로 나눠진다면
				"			min = '30';\n" + // 분을 30으로 바꿔준다.
				"		}\n" +
				// 만약 가져온 오픈시간이 같다면 해당 값을 selected 해준다.
				"  if ( hour == " + am_pre + " && min == " + am_las
				+ ") { document.write('<option value=' + hour + min + ' selected>' + hour +':'+ min + '</option>'); }    "
				+
				// 나머지는 그냥 가져온다.
				"	else {  document.write('<option value=' + hour + min + '>' + hour +':'+ min + '</option>'); }"
				+ "	}\n" + "</script>\n" + "</select> ~ ");
		sb.append("<select name=\"pm_close\" id=\"close_time\">\n" + "<script>\n"
				+ "	for (var i = 0; i < 48; i++) {\n" + "		var hour = '';\n" + "		var	min = '00'\n"
				+ "		if ((Math.ceil(i / 2)) < 11) {\n" + "			if((Math.floor(i / 2)) < 10){\n"
				+ "				hour = '0'+(Math.floor(i / 2));\n" + "			} else {\n"
				+ "				hour = (Math.floor(i / 2));\n" + "			}\n" + "		} else {\n"
				+ "			hour = (Math.floor(i / 2));\n" + "		}\n" + "		if (i % 2 != 0) {\n"
				+ "			min = '30';\n" + "		}\n" + "  if ( hour == " + pm_pre + " && min == " + pm_las
				+ ") { document.write('<option value=' + hour + min + ' selected>' + hour +':'+ min + '</option>'); }    "
				+ "	else {  document.write('<option value=' + hour + min + '>' + hour +':'+ min + '</option>'); }"
				+ "	}\n" + "</script>\n" + "</select>");
		return sb.toString();
	}

	// 현휘; 기업의 수정페이지에서 점심시간을 보여주는 메소드
	private String serviceLunchUpdateForm(String bus_no, String bct_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		map = hDao.searchBSD(map);
		String am_close = (String) map.get("AM_CLOSE");
		String pm_open = (String) map.get("PM_OPEN");

		int lunch_o_pre = Integer.parseInt(am_close.substring(0, 2));
		int lunch_o_las = Integer.parseInt(am_close.substring(2, 4));
		int lunch_c_pre = Integer.parseInt(pm_open.substring(0, 2));
		int lunch_c_las = Integer.parseInt(pm_open.substring(2, 4));

		StringBuilder sb = new StringBuilder();
		sb.append("<select name=\"lunch_open\" id=\"open_time\">\n" + "<script>\n"
				+ "	for (var i = 0; i < 48; i++) {\n" + "		var hour = '';\n" + "		var	min = '00'\n"
				+ "		if ((Math.ceil(i / 2)) < 11) {\n" + "			if((Math.floor(i / 2)) < 10){\n"
				+ "				hour = '0'+(Math.floor(i / 2));\n" + "			} else {\n"
				+ "				hour = (Math.floor(i / 2));\n" + "			}\n" + "		} else {\n"
				+ "			hour = (Math.floor(i / 2));\n" + "		}\n" + "		if (i % 2 != 0) {\n"
				+ "			min = '30';\n" + "		}\n" + "  if ( hour == " + lunch_o_pre + " && min == " + lunch_o_las
				+ ") { document.write('<option value=' + hour + min + ' selected>' + hour +':'+ min + '</option>'); }    "
				+ "	else {  document.write('<option value=' + hour + min + '>' + hour +':'+ min + '</option>'); }"
				+ "	}\n" + "</script>\n" + "</select> ~ ");
		sb.append("<select name=\"lunch_close\" id=\"close_time\">\n" + "<script>\n"
				+ "	for (var i = 0; i < 48; i++) {\n" + "		var hour = '';\n" + "		var	min = '00'\n"
				+ "		if ((Math.ceil(i / 2)) < 11) {\n" + "			if((Math.floor(i / 2)) < 10){\n"
				+ "				hour = '0'+(Math.floor(i / 2));\n" + "			} else {\n"
				+ "				hour = (Math.floor(i / 2));\n" + "			}\n" + "		} else {\n"
				+ "			hour = (Math.floor(i / 2));\n" + "		}\n" + "		if (i % 2 != 0) {\n"
				+ "			min = '30';\n" + "		}\n" + "  if ( hour == " + lunch_c_pre + " && min == " + lunch_c_las
				+ ") { document.write('<option value=' + hour + min + ' selected>' + hour +':'+ min + '</option>'); }    "
				+ "	else {  document.write('<option value=' + hour + min + '>' + hour +':'+ min + '</option>'); }"
				+ "	}\n" + "</script>\n" + "</select>");
		return sb.toString();
	}

	// 현휘; 기업의 수정페이지에서 휴일을 보여주는 메소드
	private String serviceHolidayUpdateForm(String bus_no, String bct_code) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		map = hDao.searchBFX(map); // 기업의 고정 스케줄을 검색
		Set<String> mapKey = map.keySet(); // 고정 스케줄의 컬럼명을 가져온다.
		Iterator<String> iter = mapKey.iterator(); // 차례로 뽑아오기 위해 iterator를 선언한다.
		while (iter.hasNext()) { // 차례대로 값을 가져온다.
			String key = iter.next(); // 가져온 값을 변수에 넣어준다.
			String value = (String) map.get(key); // 컬럼명이 가지고 있는 값을 변수에 넣어준다.
			if (value.equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				sb.append("<input type='checkbox' name='holiday' value='" + key + "' checked />" + key); // checked 옵션을
																											// 준다.
			} else {
				sb.append("<input type='checkbox' name='holiday' value='" + key + "' />" + key); // 아니면 그냥 만들어준다.
			}
		}
		return sb.toString();
	}

	// 현휘; 기업의 수정페이지에서 제공 서비스&이용 가능한 동물을 보여주는 메소드
	private String serviceMenuUpdateForm(String bus_no, String bct_code) {
		boolean flag = true; // 기업이 제공하는 메뉴인지 아닌지 구분하기 위한 깃발
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		List<Map<String, Object>> list;
		list = hDao.selectMenuTag(map); // 기업이 제공하는 서비스 검색

		List<Map<String, Object>> allList;
		allList = hDao.selectMENU(bct_code); // 모든 메뉴들을 검색

		String menu_no = null; // 모든 메뉴의 번호를 담을 변수
		String menu_name = null; // 모든 메뉴의 이름을 담을 변수
		String select_menu_no = null; // 기업이 제공하는 메뉴의 번호를 담을 변수
		StringBuilder sb = new StringBuilder();
		sb.append("제공서비스: ");
		for (int i = 0; i < allList.size(); i++) { // 모든 메뉴들 개수만큼 반복
			menu_no = String.valueOf(allList.get(i).get("MENU_NO")); // 해당 메뉴의 번호를 변수에 저장
			menu_name = (String) allList.get(i).get("MENU_NAME"); // 해당 메뉴의 이름을 변수에 저장
			for (int j = 0; j < list.size(); j++) { // 기업이 제공하는 서비스 만큼 반복
				select_menu_no = String.valueOf(list.get(j).get("MENU_NO")); // 기업이 제공하는 서비스의 번호를 변수에 저장
				if (menu_no.equals(select_menu_no)) { // 모든 메뉴들 중 기업이 제공하는 메뉴와 같은 것이 있는지 검색
					flag = false; // 기업이 제공한다면 깃발을 false로 바꿔준다.
				}
			}
			if (flag) { // 기업이 제공하지 않는 서비스면
				if(menu_name.equals("가위컷")) {
					sb.append("<input type='checkbox' name='dog_tag' value='" + menu_name + "'/>" + menu_name); // 그냥 만들어주고
				} else if(menu_name.equals("고양이무마취")) {
					sb.append("<input type='checkbox' name='cat_tag' value='" + menu_name + "'/>" + menu_name); // 그냥 만들어주고
				} else {
					sb.append("<input type='checkbox' name='tag_name' value='" + menu_name + "'/>" + menu_name); // 그냥 만들어주고
				}
			} else { // 기업이 제공하는 서비스면
				if(menu_name.equals("가위컷")) {
					sb.append("<input type='checkbox' name='dog_tag' value='" + menu_name + "'checked/>" + menu_name); // 그냥 만들어주고
				} else if(menu_name.equals("고양이무마취")) {
					sb.append("<input type='checkbox' name='cat_tag' value='" + menu_name + "'checked/>" + menu_name); // 그냥 만들어주고
				} else {
					sb.append("<input type='checkbox' name='tag_name' value='" + menu_name + "'checked/>" + menu_name); // 그냥 만들어주고
				}																										// 만들어준다.
			}
			flag = true; // 깃발은 다시 true로 바꿔준다.
		}

		// 밑에 과정을 거치는 이유는 모든 동물 종류 테이블에는 햄스터 같은 종류도 들어있기에, 1차적으로 각 업종에서 제공되는 동물들의 코드만
		// 검색해서 가져와야 한다.
		// 2차적으로는 기업에서 제공하기로 했던 동물종류들은 checked 옵션을 줘야하기에 1차로 검색됐던 결과와 다시 비교를 해줘야 한다.
		sb.append("<br/>");
		sb.append("-서비스 가능한 동물종류: ");
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
		ptyList = hDao.animalSelect(); // 모든 동물 종류 검색
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
		aimlList = hDao.selectAnimalTag(map); // 기업에서 제공하는 동물들의 번호를 담아준다.

		for (int i = 0; i < allAimlList.size(); i++) { // 모든 동물 종류만큼 반복 (햄스터 같은)
			System.out.println(allAimlNoList.get(i));
			String no = allAimlNoList.get(i); // 검색된 동물의 번호
			System.out.println(no);
			System.out.println(allAimlList.get(i));
			String name = allAimlList.get(i); // 각 업종에서 제공되는 동물의 이름이 적힌 리스트
			System.out.println(name);
			for (int j = 0; j < aimlList.size(); j++) { // 기업이 제공하는 동물 종류 수만큼 반복
				String select_aiml_no = String.valueOf(aimlList.get(j).get("PTY_NO")); // 해당 동물의 번호를 담아준다.
				if (no.equals(select_aiml_no)) { // 검색된 동물의 번호와 제공하는 동물의 번호가 같다면
					flag = false; // 깃발을 false 로 바꿔준다.
				}
			}
			if (flag) { // 제공하지 않던 동물 종류라면
				if (name.equals("고양이")) { // 근데 그게 고양이라면
					sb.append("<input type='checkbox' name='cat_code' value='" + name + "'/>" + name); // cat_code를 붙여서
																										// 그냥 가져온다.
				} else { // 둘다 아니라면 animal_code로 그냥 가져온다.
					sb.append("<input type='checkbox' name='animal_code' value='" + name + "'/>" + name);
				}
			} else { // 제공하던 동물 종류라면
				if (name.equals("고양이")) { // 근데 그게 고양이라면
					sb.append("<input type='checkbox' name='cat_code' value='" + name + "' checked/>" + name); // cat_code에
																												// checked
																												// 옵션을
																												// 주고
																												// 가져온다.
				} else { // 고양이가 아니라면
					sb.append("<input type='checkbox' name='animal_code' value='" + name + "' checked/>" + name); // 그냥
																													// animal_code로
																													// checked
																													// 옵션으로
																													// 가져온다.
				}
			}
			flag = true; // 깃발은 다시 true로 바꿔준다.
		}
		sb.append("<br/>");
		return sb.toString();
	}

	// 기업의 상세정보 수정//
	// 현휘; 기업의 상세정보 수정 //가격수정 되는 부분 진행해야해
	public ModelAndView serviceUpdate(MultipartHttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		String bct_code = request.getParameter("bct_code");

		String no = (String) session.getAttribute("no");
		String am_open = request.getParameter("am_open");
		String pm_close = request.getParameter("pm_close");
		String lunch_open = request.getParameter("lunch_open");
		String lunch_close = request.getParameter("lunch_close");
		String first = request.getParameter("first");

		map.put("bus_no", no);
		map.put("bct_code", bct_code);
		map.put("am_open", am_open);
		map.put("pm_close", pm_close);
		map.put("lunch_open", lunch_open);
		map.put("lunch_close", lunch_close);
		if (first.equals("first")) {
			weekSet(request); // 스케줄 등록 (영업시간,점심시간,고정휴무일)
			checkTag(request); // 제공서비스 등록
			insertPrice(request); // 기업이 지정한 서비스 가격 등록
		} else {
			hDao.updateServiceBSD(map); // 기업의 하루 스케줄 수정
			map = holidayMap(request); // 기업의 휴일을 수정하기 위해 정보를 map에 담아오는 메소드
			hDao.updateServiceBFX(map); // 기업의 고정 스케줄 수정
			hDao.deletePRC(map);
			insertPrice(request); // 기업이 지정한 서비스 가격 등록
		}
		fileWriter(request); // 사진 등록 (사업장사진)

		mav.setViewName("test");
		return mav;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////
	// 기업 업종 삭제
	// 현휘; 기업-업종 삭제, 각종 정보들과 직원들 모두 삭제
	@Transactional
	public ModelAndView serviceDelete(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String bus_no = (String) session.getAttribute("no");
		String bct_code = request.getParameter("bct_code");
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		//String svc_no = hDao.searchSVC(map);
		
		List<Map<String, Object>> list;
		list = hDao.selectEMP(map);
		for (int i = 0; i < list.size(); i++) {
			String emp_no = (String) list.get(i).get("EMP_NO");
			hDao.deleteBK(emp_no);
			hDao.deleteESD(emp_no); // 해당 직원의 하루 스케줄 삭제
			hDao.deleteEFX(emp_no); // 해당 직원의 고정 스케줄 삭제
			hDao.deleteEMP(emp_no); // 해당 직원의 상세정보 삭제
		}
		hDao.deletePRC(map); // 해당 업종의 가격삭제
		hDao.deleteBSD(map); // 해당 업종의 하루 스케줄 삭제
		hDao.deleteBFX(map); // 해당 업종의 고정 스케줄 삭제
		hDao.deleteBTG(map); // 해당 업종의 관련태그 삭제
		hDao.deleteGLR(map); // 해당 업종의 사진 삭제
		hDao.deleteSVC(map); // 해당 업종의 정보 삭제
		
		mav.setView(new RedirectView("/servicePage"));
		return mav;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////
	// 직원 리스트
	// 현휘; 기업이 가진 업종의 직원을 불러오기 위해 업종 선택 버튼 생성
	public ModelAndView stepListBut() {
		mav = new ModelAndView();
		List<Map<String, Object>> list;
		StringBuilder sb = new StringBuilder();
		String bus_no = (String) session.getAttribute("no");
		list = hDao.selectSVC(bus_no);
		System.out.println(list);
		sb.append(" ");
		for (int i = 0; i < list.size(); i++) {
			String bct_code = (String) list.get(i).get("BCT_CODE");
			bct_code = hDao.searchBCTname(bct_code);
			sb.append("<input type='radio' name='bct_code' value='" + bct_code + "' onclick='chk(this)'/>" + bct_code);
		}
		mav.addObject("code", sb);
		mav.setViewName("stepList");
		return mav;
	}

	// 현휘; 해당 업종 직원 리스트 불러오기 (ajax)
	public String stepList(HttpServletRequest request) {
		List<Map<String, Object>> list;
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();

		String bus_no = (String) session.getAttribute("no");
		String bct_code = request.getParameter("bct_code");
		System.out.println(bct_code);
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		list = hDao.selectEMP(map);

		for (int i = 0; i < list.size(); i++) {
			String emp_name = (String) list.get(i).get("EMP_NAME");
			String emp_pos = (String) list.get(i).get("EMP_POS");
			String emp_part = (String) list.get(i).get("EMP_PART");
			String emp_photo = (String) list.get(i).get("EMP_PHOTO");
			String emp_loc = (String) list.get(i).get("EMP_LOC");
			sb.append("<div>");
			sb.append("<img src='" + emp_loc  + emp_photo+"'/>");
			sb.append("</div>");
		}
		return sb.toString();
	}

	// 현휘; 직원 등록 페이지로 이동 (
	public ModelAndView stepInsertFormBut() {
		StringBuilder sb = new StringBuilder();
		String bus_no = (String) session.getAttribute("no");
		List<Map<String, Object>> list;
		list = hDao.selectSVC(bus_no);
		sb.append("");
		for (int i = 0; i < list.size(); i++) {
			String bct_code = (String) list.get(i).get("BCT_CODE");
			System.out.println(bct_code);
			String bct_name = hDao.searchBCTname(bct_code);
			sb.append(
					"<input type='radio' class='bct_code' name='bct_code' value='" + bct_code + "' onClick='chk(this)'/> " + bct_name);
		}
		mav.addObject("type", sb.toString());

		String time = timeSelect();
		String lunch = lunchSelect();
		String holiday = holidaySelect();

		mav.addObject("time", time);
		mav.addObject("lunch", lunch);
		mav.addObject("holiday", holiday);
		mav.setViewName("stepInsertForm");
		return mav;
	}

	// 현휘; 해당 업종에 직원 등록하는 페이지 //점심시간 시간이 안나와ㅏ 수정필요
	public String stepInsertForm(HttpServletRequest request) {
		String bct_code = request.getParameter("bct_code");

		String work = checkWorkTime(request);
		String lunch = checkLunchTime(request);
		// String work = Time(bct_code, "step", "work"); //영업시간을 보여주는 메소드
		// String lunch = Time(bct_code, "step", "lunch"); //점심시간을 보여주는 메소드
		String holiday = Holiday(bct_code, "step");
		return work + lunch + holiday;
	}

	// 현휘; 해당 업종에 직원 등록
	public ModelAndView stepInsert(MultipartHttpServletRequest request) {
		// String flag = "emp";
		// fileWriter(request, flag);
		String code = request.getParameter("bct_code");
		System.out.println(code);
		stepInsertEMP(request);
		mav.setView(new RedirectView("/stepList?bct_code="+code));
		return mav;
	}

	// 현휘; 직원 insert (수정)
	@Transactional
	private void stepInsertEMP(MultipartHttpServletRequest request) {
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
		hDao.stepInsert(map);
		int empNo = (int) map.get("EMP_NO");
		System.out.println(empNo);
		empScheduleInsert(request, empNo);
	}

	// 현휘; 직원 스케줄 insert (수정)
	private void empScheduleInsert(MultipartHttpServletRequest request, int empNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("empno", "S" + empNo);
		System.out.println(empNo);
		String open = request.getParameter("am_open");
		String close = request.getParameter("pm_close");
		String time = open + close;

		String lunch_open = request.getParameter("lunch_open");
		String lunch_close = request.getParameter("lunch_close");
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
		int weekSet = hDao.empInsertEFX(map);
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
					map.put("am_open", open);
					map.put("am_close", lunch_open);
					map.put("pm_open", lunch_close);
					map.put("pm_close", close);
					hDao.empInsertESD(map);
				}
				cal.add(Calendar.DATE, 1);
				dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
				flag = true;
			}
		}
	}

	// 현휘; 직원 상세정보 (수정)
	public ModelAndView stepDetail(HttpServletRequest request) {
		String empno = request.getParameter("empno");
		System.out.println(empno);
		String no = request.getParameter("no");
		System.out.println(no);
		Map<String, Object> busiMap = new HashMap<String, Object>();
		Map<String, Object> stepMap = new HashMap<String, Object>();
		StringBuilder text = new StringBuilder();
		stepMap = hDao.searchEMP(empno);
		text.append(makeStepDetail(stepMap));

		busiMap = hDao.holidaySelected(no);
		stepMap = hDao.searchEFX(empno);

		text.append(makeStepWorkTime(empno));
		text.append(makeStepLunchTime(empno));
		text.append(makeStepHoliday(busiMap, stepMap));
		text.append(button());

		mav.addObject("text", text);
		mav.setViewName("stepDetail");

		// String workTime;
		// String lunchTime;

		return mav;
	}

	private String makeStepDetail(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		String bct_code = (String) map.get("BCT_CODE");
		bct_code = hDao.changeCode(bct_code);
		String name = (String) map.get("EMP_NAME");
		String pos = (String) map.get("EMP_POS");
		String part = (String) map.get("EMP_PART");
		String emp_no = (String) map.get("EMP_NO");
		String no = (String) map.get("BUS_NO");
		sb.append("<input type='hidden' name='emp_no' value='" + emp_no + "'/>");
		sb.append("<input type='hidden' name='bus_no' value='" + no + "'/>");
		sb.append("업종 : <input type='text' name='bct_code' value='" + bct_code + "' readonly/><br/>");
		sb.append("프로필사진: <input type='file' name='photo'/><br/>");
		sb.append("이름 : <input type='text' name='emp_name' value='" + name + "'/><br/>");
		sb.append("직급 : <input type='text' name='pos' value='" + pos + "'/><br/>");
		sb.append("담당분야 : <input type='text' name='part' value='" + part + "'/><br/>");
		return sb.toString();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////
	// 중복된 코드를 위한 정리 메소드 모음//
	// 기업의 선택 전 휴일에 관련된 작업을 위한 메소드
	public Map<String, Object> holidayMap(MultipartHttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String code = request.getParameter("bct_code"); // 업종 코드
		String no = (String) session.getAttribute("no"); // 기업 번호

		map.put("bus_no", no);
		map.put("bct_code", code);

		String open = request.getParameter("am_open"); // 오픈시간
		String close = request.getParameter("pm_close"); // 닫는시간
		String time = open + close; // BFX는 8자리니까 둘을 합쳐준다.

		String lunch_open = request.getParameter("lunch_open"); // 점심시작시간
		String lunch_close = request.getParameter("lunch_close"); // 점심끝나는시간
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

	// 기업의 선택 된 휴일에 관련된 작업을 위한 메소드
	public String Holiday(String bct_code, String type) {
		StringBuilder sb = new StringBuilder();
		sb.append("-고정휴무일: ");
		Map<String, Object> map = new HashMap<String, Object>();
		String bus_no = (String) session.getAttribute("no");
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		map = hDao.searchBFX(map); // 기업의 고정 스케줄을 검색
		Set<String> mapKey = map.keySet(); // 고정 스케줄의 컬럼명을 가져온다.
		Iterator<String> iter = mapKey.iterator(); // 차례로 뽑아오기 위해 iterator를 선언한다.
		while (iter.hasNext()) { // 차례대로 값을 가져온다.
			String key = iter.next(); // 가져온 값을 변수에 넣어준다.
			String value = (String) map.get(key); // 컬럼명이 가지고 있는 값을 변수에 넣어준다.
			if (value.equals("XXXXXXXX")) { // XXXXXXXX 라는 값을 가지고 있다면
				if (type.equals("step")) {
					sb.append("<input type='checkbox' name='holiday' value='" + key
							+ "' checked onclick='return false;' />" + key);
				} else {
					sb.append("<input type='checkbox' name='holiday' value='" + key + "' checked />" + key); // checked
																												// 옵션을
																												// 준다.
				}
			} else {
				sb.append("<input type='checkbox' name='holiday' value='" + key + "' />" + key); // 아니면 그냥 만들어준다.
			}
		}
		sb.append("<br/>");
		return sb.toString();
	}

	// 스케줄에 관련된 작업을 위해 필요한 정보들을 map에 담는 메소드
	public String Time(String bct_code, String type, String time) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		String bus_no = (String) session.getAttribute("no");
		System.out.println(bct_code);
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		map = hDao.searchBSD(map); // 기업의 하루 스케줄 검색

		String am_open = (String) map.get("AM_OPEN"); // 오픈시간
		String pm_close = (String) map.get("PM_CLOSE"); // 마감시간
		String am_close = (String) map.get("AM_CLOSE");
		String pm_open = (String) map.get("PM_OPEN");

		int am_pre = 0, am_las = 0, pm_pre = 0, pm_las = 0;
		String pre_name = null;
		String las_name = null;
		if (time.equals("work")) {
			System.out.println("근무");

			am_pre = Integer.parseInt(am_open.substring(0, 2)); // 오픈시간 hour
			am_las = Integer.parseInt(am_open.substring(2, 4)); // 오픈시간 min
			pm_pre = Integer.parseInt(pm_close.substring(0, 2)); // 마감시간 hour
			pm_las = Integer.parseInt(pm_close.substring(2, 4)); // 마감시간 min
			pre_name = "am_open";
			las_name = "pm_close";
			sb.append("-근무시간: ");
		} else {
			System.out.println("점심");
			am_pre = Integer.parseInt(am_close.substring(0, 2));
			am_las = Integer.parseInt(am_close.substring(2, 4));
			pm_pre = Integer.parseInt(pm_open.substring(0, 2));
			pm_las = Integer.parseInt(pm_open.substring(2, 4));
			pre_name = "lunch_open";
			las_name = "lunch_close";
			sb.append("-점심시간: ");
		}

		int i = 0;
		int j = 48;

		String OpenText = "document.write('<option value='+hour+min+'>'+hour+':'+min+'</option>');";
		String OendTextSelect = "document.write('<option value='+hour+min+', selected>'+hour+':'+min+'</option>');";
		String CloseText = "document.write('<option value='+hour+min+'>'+hour+':'+min+'</option>');";
		String CloseTextSelect = "document.write('<option value='+hour+min+', selected>'+hour+':'+min+'</option>');";

		if (type.equals("step")) {
			System.out.println("스텝");
			i = am_pre * 2;
			if (am_las == 30) {
				i += 1;
			}
			j = pm_pre * 2 + 1;
			if (pm_las == 30) {
				j += 1;
			}
			OpenText = "$('#open_time').append($('<option>',{value: hour+min, text:hour+':'+min}));";
			OendTextSelect = "$('#open_time').append($('<option>',{value: hour+min, text:hour+':'+min, selected:true}));";
			CloseText = "$('#close_time').append($('<option>',{value: hour+min, text:hour+':'+min}));";
			CloseTextSelect = "$('#close_time').append($('<option>',{value: hour+min, text:hour+':'+min, selected:true}));";
		}

		sb.append("<select name='" + pre_name + "' id=\"open_time\">\n" + "<script>\n" + "	for (var i = " + i
				+ "; i < " + j + "; i++) {\n" + // 반복문을 48번 도는 이유는 시간,분 의 종류가 총 48개이기 떄문이다. ex) 1시면 01:00, 01:30 두가지가
												// 존재한다.
				"		var hour = '';\n" + // 시간은 무조건 값이 들어가기에 ''로 초기화 해준다.
				"		var	min = '00'\n" + // 분은 밑에 if문을 충족하지 못하면 00분이 들어가야 하기에 00으로 초기화 해준다.
				"		if ((Math.ceil(i / 2)) < 11) {\n" + // i를 2로 나누는 이유는 위에서 시간의 종류를 48개로 지정한 이유와 같다. i는 시를 나타내고 시는
															// 두가지의 종류를 가지고 있다.
				"			if((Math.floor(i / 2)) < 10){\n" + // i가 10보다 작을 경우
				"				hour = '0'+(Math.floor(i / 2));\n" + // 한자리 수가 되기에, 앞에 0을 붙여준다. DB에 입력될때는 01 이런식으로 입력되야
																		// 하기 때문이다.
				"			} else {\n" + "				hour = (Math.floor(i / 2));\n" + // 그런게 아닐 경우 그냥 i만 넣어준다.
				"			}\n" + "		} else {\n" + "			hour = (Math.floor(i / 2));\n" + // 나머지는 한자리 수가 나오지
																										// 않기에 바로 i를
																										// 넣어준다.
				"		}\n" + "		if (i % 2 != 0) {\n" + // 만약 홀수로 나눠진다면
				"			min = '30';\n" + // 분을 30으로 바꿔준다.
				"		}\n" +
				// 만약 가져온 오픈시간이 같다면 해당 값을 selected 해준다.
				"  if ( hour == " + am_pre + " && min == " + am_las + ") { " + OendTextSelect + " }    " +
				// 나머지는 그냥 가져온다.
				"	else {  " + OpenText + " }" + "	}\n" + "</script>\n" + "</select> ~ ");
		sb.append("<select name='" + las_name + "' id=\"close_time\">\n" + "<script>\n" + "	for (var i = " + i
				+ "; i < " + j + "; i++) {\n" + "		var hour = '';\n" + "		var	min = '00'\n"
				+ "		if ((Math.ceil(i / 2)) < 11) {\n" + "			if((Math.floor(i / 2)) < 10){\n"
				+ "				hour = '0'+(Math.floor(i / 2));\n" + "			} else {\n"
				+ "				hour = (Math.floor(i / 2));\n" + "			}\n" + "		} else {\n"
				+ "			hour = (Math.floor(i / 2));\n" + "		}\n" + "		if (i % 2 != 0) {\n"
				+ "			min = '30';\n" + "		}\n" + "  if ( hour == " + pm_pre + " && min == " + pm_las + ") { "
				+ CloseTextSelect + " }    " + "	else {  " + CloseText + " }" + "	}\n" + "</script>\n"
				+ "</select>");
		sb.append("<br/>");
		return sb.toString();
	}

	///////// 대
	private String stepTimeSelect(String no) {
		StringBuilder sb = new StringBuilder();
		String lunch = hDao.searchLunchTime(no);
		String amLunch = lunch.substring(0, 3);
		System.out.println(amLunch);
		String pmLunch = lunch.substring(4, 7);
		System.out.println(pmLunch);
		Map<String, Object> map = new HashMap<String, Object>();
		map = hDao.searchTime(no);
		String amWork = (String) map.get("AM_OPEN");
		String pmWork = (String) map.get("PM_CLOSE");

		sb.append("<select name=\"am_open\">\n" + "<script>\n" + "for (i = 0; i < 48; i++) {\n" + "var hour = '';\n"
				+ "var min = '00';\n" + "if ((Math.ceil(i / 2)) < 13) {\n" + "if((Math.floor(i / 2)) < 10){\n"
				+ "hour = '0'+(Math.floor(i / 2));\n" + "}\n" + "else {\n" + "hour = (Math.floor(i / 2));\n" + "}\n"
				+ "} else {\n" + "hour = (Math.floor(i / 2));\n" + "}\n" + "if (i % 2 != 0) {\n" + "min = '30';\n"
				+ "}\n" + "document.write('<option value=' + hour + min + '>' + hour +\":\"+ min + '</option>');\n"
				+ "}\n" + "</script>\n" + " </select> ~\n" + " <select name=\"pm_close\">\n" + "<script>\n"
				+ "for (i = 0; i < 48; i++) {\n" + "var hour = '';\n" + "var min = '00';\n"
				+ "if ((Math.ceil(i / 2)) < 13) {\n" + "if((Math.floor(i / 2)) < 10){\n"
				+ "hour = '0'+(Math.floor(i / 2));\n" + "}\n" + "else {\n" + "hour = (Math.floor(i / 2));\n" + "}\n"
				+ "} else {\n" + "hour = (Math.floor(i / 2));\n" + "}\n" + "if (i % 2 != 0) {\n" + "min = '30';\n"
				+ "}\n" + "document.write('<option value=' + hour + min + '>' + hour +\":\"+ min + '</option>');\n"
				+ "}\n" + "</script>\n" + " </select>");
		return sb.toString();
	}

	private String holidaySelected(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		Set<String> mapkey = map.keySet();
		Iterator<String> iter = mapkey.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			String value = (String) map.get(key);
			if (value.equals("XXXXXXXX")) {
				sb.append("<input type='checkbox' name='holiday' value='" + key + "' checked onclick='return false;' />"
						+ key);
			} else {
				sb.append("<input type='checkbox' name='holiday' value='" + key + "' />" + key);
			}
		}
		return sb.toString();
	}

	private String stepListShow(List<Map<String, Object>> list, String no) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			String empno = (String) list.get(i).get("EMP_NO");
			String loc = (String) list.get(i).get("EMP_LOC");
			System.out.println(loc);
			String photo = (String) list.get(i).get("EMP_PHOTO");
			System.out.println(photo);
			String name = (String) list.get(i).get("EMP_NAME");
			String pos = (String) list.get(i).get("EMP_POS");
			String part = (String) list.get(i).get("EMP_PART");
			sb.append("<div class='header'><a href='stepDetail?empno=" + empno + "&no=" + no + "'><div class='photo'>"
					+ "<img src='" + loc + photo + "' />" + "</div><br/><div class='con'>" + "<p> 이름 : " + name
					+ "</p><br/>" + "<p> 직급 : " + pos + "</p><br/>" + "<p> 담당분야 : " + part + "</p><br/>"
					+ "</div></a></div>");
		}
		return sb.toString();
	}

	private String button() {
		StringBuilder sb = new StringBuilder();
		sb.append("<input type='button' name='수정완료' value='수정완료' onclick='but(this)' />");
		sb.append("<input type='button' name='삭제' value='삭제' onclick='but(this)' />");
		return sb.toString();
	}

	private String makeStepLunchTime(String empno) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = hDao.searchESD(empno);

		String am_close = (String) map.get("AM_CLOSE");
		String pm_open = (String) map.get("PM_OPEN");
		System.out.println(am_close);
		System.out.println(pm_open);
		int lunch_open_pre = Integer.parseInt(am_close.substring(0, 2));
		int lunch_open_las = Integer.parseInt(am_close.substring(2, 4));
		int lunch_close_pre = Integer.parseInt(pm_open.substring(0, 2));
		int lunch_close_las = Integer.parseInt(pm_open.substring(2, 4));

		int i = lunch_open_pre * 2;
		if (lunch_open_las == 30) {
			i += 1;
		}
		int j = lunch_close_pre * 2 + 1;
		if (lunch_close_las == 30) {
			j += 1;
		}
		System.out.println(i);
		System.out.println(j);
		StringBuilder sb = new StringBuilder();
		sb.append("점심시간: ");
		sb.append("<select name=\"lunch_open\" id=\"lunch_open\">\n" + "<script>\n" + "	for (var i = " + i + "; i < "
				+ j + "; i++) {\n" + "		var hour = '';\n" + "		var	min = '00'\n"
				+ "		if ((Math.ceil(i / 2)) < 13) {\n" + "			if((Math.floor(i / 2)) < 10){\n"
				+ "				hour = '0'+(Math.floor(i / 2));\n" + "			} else {\n"
				+ "				hour = (Math.floor(i / 2));\n" + "			}\n" + "		} else {\n"
				+ "			hour = (Math.floor(i / 2));\n" + "		}\n" + "		if (i % 2 != 0) {\n"
				+ "			min = '30';\n" + "		}\n"
				+ "		 document.write('<option value=' + hour + min + '>' + hour +':'+ min + '</option>');" + "	}\n"
				+ "</script>\n" + "</select> ~ ");
		sb.append("<select name=\"lunch_close\" id=\"lunch_close\">\n" + "<script>\n" + "	for (var i = " + i
				+ "; i < " + j + "; i++) {\n" + "		var hour = '';\n" + "		var	min = '00'\n"
				+ "		if ((Math.ceil(i / 2)) < 13) {\n" + "			if((Math.floor(i / 2)) < 10){\n"
				+ "				hour = '0'+(Math.floor(i / 2));\n" + "			} else {\n"
				+ "				hour = (Math.floor(i / 2));\n" + "			}\n" + "		} else {\n"
				+ "			hour = (Math.floor(i / 2));\n" + "		}\n" + "		if (i % 2 != 0) {\n"
				+ "			min = '30';\n" + "		}\n"
				+ "		 document.write('<option value=' + hour + min + '>' + hour +':'+ min + '</option>');" + "	}\n"
				+ "</script>\n" + "</select>");
		sb.append("<br/>");
		return sb.toString();
	}

	private String makeStepWorkTime(String empno) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(empno);
		map = hDao.searchESD(empno);

		String am_open = (String) map.get("AM_OPEN");
		String pm_close = (String) map.get("PM_CLOSE");

		int am_pre = Integer.parseInt(am_open.substring(0, 2));
		int am_las = Integer.parseInt(am_open.substring(2, 4));
		int pm_pre = Integer.parseInt(pm_close.substring(0, 2));
		int pm_las = Integer.parseInt(pm_close.substring(2, 4));
		int i = am_pre * 2;
		if (am_las == 30) {
			i += 1;
		}
		int j = pm_pre * 2 + 1;
		if (pm_las == 30) {
			j += 1;
		}
		System.out.println(i);
		System.out.println(j);
		StringBuilder sb = new StringBuilder();
		sb.append("근무시간: ");
		sb.append("<select name=\"am_open\" id=\"open_time\">\n" + "<script>\n" + "	for (var i = " + i + "; i < " + j
				+ "; i++) {\n" + "		var hour = '';\n" + "		var	min = '00'\n"
				+ "		if ((Math.ceil(i / 2)) < 13) {\n" + "			if((Math.floor(i / 2)) < 10){\n"
				+ "				hour = '0'+(Math.floor(i / 2));\n" + "			} else {\n"
				+ "				hour = (Math.floor(i / 2));\n" + "			}\n" + "		} else {\n"
				+ "			hour = (Math.floor(i / 2));\n" + "		}\n" + "		if (i % 2 != 0) {\n"
				+ "			min = '30';\n" + "		}\n"
				+ "	   document.write('<option value=' + hour + min + '>' + hour +':'+ min + '</option>');" + "	}\n"
				+ "</script>\n" + "</select> ~ ");
		sb.append("<select name=\"pm_close\" id=\"close_time\">\n" + "<script>\n" + "	for (var i = " + i + "; i < "
				+ j + "; i++) {\n" + "		var hour = '';\n" + "		var	min = '00'\n"
				+ "		if ((Math.ceil(i / 2)) < 13) {\n" + "			if((Math.floor(i / 2)) < 10){\n"
				+ "				hour = '0'+(Math.floor(i / 2));\n" + "			} else {\n"
				+ "				hour = (Math.floor(i / 2));\n" + "			}\n" + "		} else {\n"
				+ "			hour = (Math.floor(i / 2));\n" + "		}\n" + "		if (i % 2 != 0) {\n"
				+ "			min = '30';\n" + "		}\n"
				+ "		 document.write('<option value=' + hour + min + '>' + hour +':'+ min + '</option>');" + "	}\n"
				+ "</script>\n" + "</select>");
		sb.append("<br/>");
		return sb.toString();

	}

	private String makeStepHoliday(Map<String, Object> busiMap, Map<String, Object> stepMap) {
		StringBuilder sb = new StringBuilder();
		sb.append("고정휴무일 : ");
		Set<String> busiKey = busiMap.keySet();
		Set<String> stepKey = stepMap.keySet();
		Iterator<String> siter = stepKey.iterator();
		boolean flag = true;
		int i = 1;
		while (siter.hasNext()) {
			String key = siter.next();
			String value = (String) stepMap.get(key);
			if (value.equals("XXXXXXXX")) {
				Iterator<String> biter = busiKey.iterator();
				while (biter.hasNext()) {
					String bkey = biter.next();
					String bvalue = (String) busiMap.get(bkey);
					System.out.println(bkey);
					System.out.println(bvalue);
					if (key.equals(bkey) && bvalue.equals("XXXXXXXX")) {
						sb.append("<script></script>");
						sb.append("<input type='checkbox' name='holiday' value='" + key
								+ "' checked onclick='return false;' />" + key);
						System.out.println("불가능한X값이야");
						flag = false;
					} else {
						System.out.println(bkey + " : 지나가는 값 ");
					}
				}
				if (flag) {
					sb.append("<input type='checkbox' name='holiday' value='" + key + "' checked />" + key);
					System.out.println("변화가능한X값이야 ");
				}
				flag = true;
			} else {
				sb.append("<input type='checkbox' name='holiday' value='" + key + "' />" + key);
			}
		}
		sb.append("<br/>");
		return sb.toString();
	}

	public String changeCode(HttpServletRequest request) {
		String no = request.getParameter("busNo");
		System.out.println(no);
		String code = request.getParameter("bctCode");
		System.out.println(code);
		String work = checkWorkTime(request);
		String lunch = checkLunchTime(request);
		mav.addObject("work", work);
		mav.addObject("lunch", lunch);
		mav.setViewName("checkCode");
		return work;
	}

	private String checkLunchTime(HttpServletRequest request) {
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

		map = hDao.searchBSD(map);

		String am_close = (String) map.get("AM_CLOSE");
		String pm_open = (String) map.get("PM_OPEN");

		int lunch_open_pre = Integer.parseInt(am_close.substring(0, 2));
		int lunch_open_las = Integer.parseInt(am_close.substring(2, 4));
		int lunch_close_pre = Integer.parseInt(pm_open.substring(0, 2));
		int lunch_close_las = Integer.parseInt(pm_open.substring(2, 4));

		int i = lunch_open_pre * 2;
		if (lunch_open_las == 30) {
			i += 1;
		}
		int j = lunch_close_pre * 2 + 1;
		if (lunch_close_las == 30) {
			j += 1;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("-점심시간: ");
		sb.append("<select name=\"lunch_open\" id=\"lunch_open\">\n" + "<script>\n" + "	for (var i = " + i + "; i < "
				+ j + "; i++) {\n" + "		var hour = '';\n" + "		var	min = '00'\n"
				+ "		if ((Math.ceil(i / 2)) < 13) {\n" + "			if((Math.floor(i / 2)) < 10){\n"
				+ "				hour = '0'+(Math.floor(i / 2));\n" + "			} else {\n"
				+ "				hour = (Math.floor(i / 2));\n" + "			}\n" + "		} else {\n"
				+ "			hour = (Math.floor(i / 2));\n" + "		}\n" + "		if (i % 2 != 0) {\n"
				+ "			min = '30';\n" + "		}\n"
				+ "		$('#lunch_open').append($('<option>',{ value: hour+min, text: hour+':'+min }));\n" + "	}\n"
				+ "</script>\n" + "</select> ~ ");
		sb.append("<select name=\"lunch_close\" id=\"lunch_close\">\n" + "<script>\n" + "	for (var i = " + i
				+ "; i < " + j + "; i++) {\n" + "		var hour = '';\n" + "		var	min = '00'\n"
				+ "		if ((Math.ceil(i / 2)) < 13) {\n" + "			if((Math.floor(i / 2)) < 10){\n"
				+ "				hour = '0'+(Math.floor(i / 2));\n" + "			} else {\n"
				+ "				hour = (Math.floor(i / 2));\n" + "			}\n" + "		} else {\n"
				+ "			hour = (Math.floor(i / 2));\n" + "		}\n" + "		if (i % 2 != 0) {\n"
				+ "			min = '30';\n" + "		}\n"
				+ "		$('#lunch_close').append($('<option>',{ value: hour+min, text: hour+':'+min }));\n" + "	}\n"
				+ "</script>\n" + "</select>");
		sb.append("<br/>");
		return sb.toString();
	}

	private String checkWorkTime(HttpServletRequest request) {
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

		map = hDao.searchBSD(map);
		String am_open = (String) map.get("AM_OPEN");
		String pm_close = (String) map.get("PM_CLOSE");

		int am_pre = Integer.parseInt(am_open.substring(0, 2));
		int am_las = Integer.parseInt(am_open.substring(2, 4));
		int pm_pre = Integer.parseInt(pm_close.substring(0, 2));
		int pm_las = Integer.parseInt(pm_close.substring(2, 4));
		int i = am_pre * 2;
		if (am_las == 30) {
			i += 1;
		}
		int j = pm_pre * 2 + 1;
		if (pm_las == 30) {
			j += 1;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("-근무시간: ");
		sb.append("<select name=\"am_open\" id=\"open_time\">\n" + "<script>\n" + "	for (var i = " + i + "; i < " + j
				+ "; i++) {\n" + "		var hour = '';\n" + "		var	min = '00'\n"
				+ "		if ((Math.ceil(i / 2)) < 13) {\n" + "			if((Math.floor(i / 2)) < 10){\n"
				+ "				hour = '0'+(Math.floor(i / 2));\n" + "			} else {\n"
				+ "				hour = (Math.floor(i / 2));\n" + "			}\n" + "		} else {\n"
				+ "			hour = (Math.floor(i / 2));\n" + "		}\n" + "		if (i % 2 != 0) {\n"
				+ "			min = '30';\n" + "		}\n"
				+ "		$('#open_time').append($('<option>',{ value: hour+min, text: hour+':'+min }));\n" + "	}\n"
				+ "</script>\n" + "</select> ~ ");
		sb.append("<select name=\"pm_close\" id=\"close_time\">\n" + "<script>\n" + "	for (var i = " + i + "; i < "
				+ j + "; i++) {\n" + "		var hour = '';\n" + "		var	min = '00'\n"
				+ "		if ((Math.ceil(i / 2)) < 13) {\n" + "			if((Math.floor(i / 2)) < 10){\n"
				+ "				hour = '0'+(Math.floor(i / 2));\n" + "			} else {\n"
				+ "				hour = (Math.floor(i / 2));\n" + "			}\n" + "		} else {\n"
				+ "			hour = (Math.floor(i / 2));\n" + "		}\n" + "		if (i % 2 != 0) {\n"
				+ "			min = '30';\n" + "		}\n"
				+ "		$('#close_time').append($('<option>',{ value: hour+min, text: hour+':'+min }));\n" + "	}\n"
				+ "</script>\n" + "</select>");
		sb.append("<br/>");
		return sb.toString();
	}

	public ModelAndView stepUpdate(MultipartHttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String emp_no = request.getParameter("emp_no");
			String code = request.getParameter("bct_code");
			String name = request.getParameter("emp_name");
			String pos = request.getParameter("pos");
			String part = request.getParameter("part");
			String[] holiday = request.getParameterValues("holiday");
			String am = request.getParameter("am_open");
			String pm = request.getParameter("pm_close");
			String lunch_o = request.getParameter("lunch_open");
			String lunch_c = request.getParameter("lunch_close");
			MultipartFile file = request.getFile("photo");
			byte fileData[] = file.getBytes();
			String ori = file.getOriginalFilename();
			String uploadPath = request.getSession().getServletContext().getRealPath("resources/upload/");
			map.put("emp_no", emp_no);
			map.put("emp_name", name);
			map.put("emp_pos", pos);
			map.put("emp_part", part);

			if (ori.length() == 0) {
				System.out.println("사진이 없습니다.");
				hDao.updateEMP(map);
			} else {
				System.out.println(ori);
				map.put("emp_photo", ori);
				map.put("emp_loc", uploadPath);
				hDao.updateEMPPhoto(map);
			}

			System.out.println(code); // 기업코드
			System.out.println(pm); // work pm
			System.out.println(lunch_o); // lunch o
			System.out.println(lunch_c); // lunch c
			System.out.println(file);
			map.put("emp_no", emp_no);
			System.out.println(emp_no);
			map.put("am_open", am);
			System.out.println(am);
			map.put("am_close", lunch_o);
			System.out.println(lunch_o);
			map.put("pm_open", lunch_c);
			System.out.println(lunch_c);
			map.put("pm_close", pm);
			System.out.println(pm);
			hDao.updateESD(map);
			System.out.println("시간입력 끝");
			String time = am + pm;
			String lunch = lunch_o + lunch_c;
			System.out.println(time + ".." + lunch);
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
				if (holiday[i].equals("월")) {
					map.put("efx_mon", "XXXXXXXX");
					num[i] = 2;
				} else if (holiday[i].equals("화")) {
					map.put("efx_tue", "XXXXXXXX");
					num[i] = 3;
				} else if (holiday[i].equals("수")) {
					map.put("efx_wed", "XXXXXXXX");
					num[i] = 4;
				} else if (holiday[i].equals("목")) {
					map.put("efx_thu", "XXXXXXXX");
					num[i] = 5;
				} else if (holiday[i].equals("금")) {
					map.put("efx_fri", "XXXXXXXX");
					num[i] = 6;
				} else if (holiday[i].equals("토")) {
					map.put("efx_sat", "XXXXXXXX");
					num[i] = 7;
				} else if (holiday[i].equals("일")) {
					map.put("efx_sun", "XXXXXXXX");
					num[i] = 1;
				} else if (holiday[i].equals("공휴일")) {
					map.put("efx_hld", "XXXXXXXX");
				}
			}
			map.put("efx_lch", lunch);
			hDao.updateEFX(map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String empno = request.getParameter("emp_no");
		map = hDao.searchEMP(empno);
		String no = (String) map.get("BUS_NO");
		// 업데이트 문만 ㅆㅓ주고 직원정보 emp, esd, efx 만 수정해주면 돼.
		mav.setViewName("stepList");
		return mav;
	}

	public ModelAndView stepDelete(MultipartHttpServletRequest request) {
		String emp_no = request.getParameter("emp_no");
		System.out.println(emp_no);
		hDao.deleteESD(emp_no);
		hDao.deleteEFX(emp_no);
		hDao.deleteEMP(emp_no);
		// 딜리트 문만 써주면 돼. emp, esd, efs 다 삭제해줘야해.
		// mav.setViewName("stepList");
		// return mav;
		ModelAndView mav = new ModelAndView();
		RedirectView rv = new RedirectView();
		String url = "/stepList.do";
		rv.setExposeModelAttributes(false);
		rv.setUrl(url);
		mav.setView(rv);
		return mav;
	}

	public ModelAndView personalBlacklist() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> yList;
		// 경고 리스트 (한달정지)
		yList = hDao.yellowList();
		String bList = blackList(yList);
		mav.addObject("bList", bList);
		mav.setViewName("personalBlackListPage");
		// 경고번호, 2-경고 3-정지에 따른 값 따로 불러오기
		// 불러올 때 해제 버튼 만들어서 가져오기.
		return mav;
	}

	private String blackList(List<Map<String, Object>> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			String per_no = (String) list.get(i).get("PER_NO");
			BigDecimal out_no = (BigDecimal) list.get(i).get("OUT_NO");
			Timestamp blk_time = (Timestamp) list.get(i).get("BLK_TIME");
			map = hDao.searchPER(per_no);
			String per_name = (String) map.get("PER_NAME");
			String pty_name = (String) map.get("PTY_NAME");
			String pet_name = (String) map.get("PET_NAME");
			BigDecimal per_noshow = (BigDecimal) map.get("PER_NOSHOW");
			System.out.println(per_no);
			System.out.println(per_name);
			System.out.println(pty_name);
			System.out.println(pet_name);
			System.out.println(blk_time);
			System.out.println(per_noshow);
			System.out.println(out_no);
			sb.append("<div>");
			sb.append("<p>" + per_no + "|" + per_name + "|" + pty_name + "|" + pet_name + "|" + blk_time + "|"
					+ per_noshow + "|" + out_no + "</p>");
			sb.append("<input type='button' name='해제' value='해제'/>");
			sb.append("</div>");
			// 회원번호 클릭하면 모달박스 뜨는 거 구현하기
			// 라디오 버튼으로 버튼 값에 따라 경고, 정지 페이지 다르게 나오게 하기.(Ajax)
			//
		}
		return sb.toString();
	}

	public ModelAndView blackList(HttpServletRequest request) {
		String black = request.getParameter("black");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> bList = null;
		if (black.equals("yellow")) {
			bList = hDao.searchYellowBLK();
		} else if (black.equals("red")) {
			bList = hDao.searchRedBLK();
		}
		String List = makeBlackList(bList);
		mav.addObject("List", List);
		mav.setViewName("blackList");
		return mav;
	}

	private String makeBlackList(List<Map<String, Object>> bList) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bList.size(); i++) {
			String per_no = (String) bList.get(i).get("PER_NO");
			String per_name = (String) bList.get(i).get("PER_NAME");
			String blk_time = (String) bList.get(i).get("BLK_TIME");
			String out_no = (String) bList.get(i).get("OUT_NO");
			sb.append("<p><a href='customerInfoDetail?per_no=" + per_no + "'>");
			sb.append(per_no + "</a>||" + per_name + "||" + blk_time + "||" + out_no + "</p>");
			sb.append("<button> 해제 </button>");
		}
		return sb.toString();
	}

	public ModelAndView businessList(HttpServletRequest request) {
		mav = new ModelAndView();
		StringBuilder sb = new StringBuilder();
		String bct_code = request.getParameter("bct_code");
		// String bus_addr = request.getParameter("bus_addr");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list;
		list = hDao.searchBTG(bct_code);
		for (int i = 0; i < list.size(); i++) {
			String bus_no = (String) list.get(i).get("BUS_NO");
			map.put("bus_no", bus_no);
			// map.put("bus_addr", bus_addr);
			// map = hDao.searchBUSmap(map);
			map = hDao.searchBUS(bus_no);
			String bus_name = (String) map.get("BUS_NAME");
			String bus_addr = (String) map.get("BUS_ADDR");
			sb.append("<div id='busiList'>");
			sb.append("<a href='businessInfo?bus_no=" + bus_no + "&bct_code=" + bct_code + "'>");
			sb.append("<br/> 이름: " + bus_name + "<br/> 주소: " + bus_addr);
			sb.append("</a></div>");
		}
		sb.append("");
		System.out.println(sb.toString());
		mav.addObject("busiList", sb.toString());
		mav.setViewName("businessList");
		return mav;
	}

	public ModelAndView weekCal() {
		mav = new ModelAndView();
		String bus_no = (String) session.getAttribute("no");
		List<Map<String, Object>> list;
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		map.put("bus_no", bus_no);
		map.put("bct_code", 'M');
		list = hDao.selectEMP(map);
		sb.append("<input type='hidden' name='bct_code' value='" + list.get(0).get("BCT_CODE") + "'/>");
		for (int i = 0; i < list.size(); i++) {
			sb.append("<a href='javascript:;' class='step' value='" + list.get(i).get("EMP_NO") + "'>"
					+ list.get(i).get("EMP_NAME") + "/</a>");
		}
		mav.addObject("weekCal", sb.toString());
		mav.addObject("test", "okkkk");
		mav.setViewName("weekCal");
		return mav;
	}

	public ModelAndView stepCal(HttpServletRequest request) {
		mav = new ModelAndView();
		String emp_no = request.getParameter("emp_no");
		String bus_no = (String) session.getAttribute("no");
		String bct_code = request.getParameter("bct_code");
		System.out.println(emp_no);
		System.out.println(bus_no);
		System.out.println(bct_code);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		map = hDao.searchBSD(map);
		String am_open = (String) map.get("AM_OPEN");
		String pm_close = (String) map.get("PM_CLOSE");

		String am_pre = am_open.substring(0, 2);
		String am_las = am_open.substring(2, 4);
		String pm_pre = pm_close.substring(0, 2);
		String pm_las = pm_close.substring(2, 4);

		mav.addObject("am", am_pre + ":" + am_las + ":00");
		mav.addObject("pm", pm_pre + ":" + pm_las + ":00");
		mav.setViewName("stepCal");
		return mav;
	}

	public String searchPrice(HttpServletRequest request) {
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
		String tag_no = hDao.searchMENUNO(map);
		System.out.println(tag_no);
		String ani_no = hDao.searchPTYNO(ani_name);
		System.out.println(ani_no);
		map.put("tag_no", tag_no);
		map.put("ani_no", ani_no);
		map.put("bus_no", bus_no);
		map.put("bct_code", bct_code);
		String price = hDao.searchPRC(map);
		if(price == null) {
			price = "0";
		}
		System.out.println(price);
		return price;
	}

}
