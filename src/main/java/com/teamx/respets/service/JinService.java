package com.teamx.respets.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.Booking;
import com.teamx.respets.bean.Business;
import com.teamx.respets.dao.JinDao;

@Service
public class JinService {

	@Autowired
	private JinDao jinDao;

	// 서진 : 기업 회원 가입 시 업종 라디오 버튼 생성
	public String selectBusCategory() {
		List<HashMap<String, String>> list = jinDao.selectBusCategory();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append("<input type='radio' name='bct_code' class='주력 서비스' value='");
			sb.append(list.get(i).get("BCT_CODE"));
			sb.append("'/>" + list.get(i).get("BCT_NAME") + " ");
		} // for End
		return sb.toString();
	} // method End

	// 서진 : 기업 회원 가입 시 이메일 확인
	public int emailCheck(String email) {
		int result = jinDao.emailCheck(email);
		return result;
	} // method End

	// 서진 : 기업 회원 가입 시 사업자 등록 번호 확인
	public int taxIdCheck(String taxId) {
		int result = jinDao.taxIdCheck(taxId);
		return result;
	} // method End

	// 서진 : 기업 회원 가입
	@Transactional
	public void businessJoin(Business b, MultipartHttpServletRequest request) {
		// 비밀번호 암호화 구현
		jinDao.businessInsert(b);
		b.setBus_no("B" + String.valueOf(b.getBus_seq()));
		jinDao.busJoinSvcInsert(b);
		MultipartFile busLicense = request.getFile("busLicense");
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap = saveFile(request, busLicense, hMap);
		hMap.put("bus_no", b.getBus_no());
		hMap.put("bct_code", b.getBct_code());
		jinDao.licenseInsert(hMap);
		if (request.getParameter("fileCheck").equals("1")) {
			MultipartFile mainPhoto = request.getFile("mainPhoto");
			hMap = saveFile(request, mainPhoto, hMap);
			jinDao.mainPhotoInsert(hMap);
		} else {
			jinDao.mainPhotoDefault(hMap);
		} // else End
	} // method End

	// 서진 : 파일 업로드
	private Map<String, Object> saveFile(MultipartHttpServletRequest request, MultipartFile file,
			Map<String, Object> hMap) {
		String root = request.getSession().getServletContext().getRealPath("/");
		String location = "resources/upload/";
		String path = root + location;
		File dir = new File(path);
		if (!dir.isDirectory()) {
			dir.mkdir();
		} // if End
		String date = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		String saveName = "Respets_" + date + "_" + UUID.randomUUID() + "." + extension;
		try {
			file.transferTo(new File(path, saveName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} // catch End
		hMap.put("location", location);
		hMap.put("file", saveName);
		return hMap;
	} // method End

	// 서진 : 개인 즐겨찾기 페이지
	public String likeBusinessList(HttpServletRequest request) {
		String per_no = request.getSession().getAttribute("no").toString();
		List<HashMap<String, String>> list = jinDao.likeBusinessSelect(per_no);
		String sb = makeLikeBusListHtml(list);
		return sb;
	} // method End

	// 서진 : 개인 즐겨찾기 페이지 Html 생성
	private String makeLikeBusListHtml(List<HashMap<String, String>> list) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append("<div class='card d-block'>");
			sb.append("<img style='height:300px; weight:300px;' class='card-img-top' src='" + list.get(i).get("GLR_LOC") + list.get(i).get("GLR_FILE") + "' />");
			sb.append("<div class='card-body'>");
			sb.append("<span><b class='card-title'>" + list.get(i).get("BUS_NAME")+"</b></span><br/>");
			sb.append("<a class='btn btn-outline-danger btn-rounded' href='./likeBusinessCancel?bus_no=" + list.get(i).get("BUS_NO") + "'>삭제</a></div>");
			sb.append("</div>");
		} // for End
		return sb.toString();
	} // method End

	// 서진 : 개인 즐겨찾기 삭제
	public void likeBusinessCancel(HttpServletRequest request) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("per_no", request.getSession().getAttribute("no").toString());
		hMap.put("bus_no", request.getParameter("bus_no"));
		jinDao.likeBusinessDelete(hMap);
	} // method End

	// 서진 : 메인 페이지 서비스 Select
	public String indexBusCategory() {
		List<HashMap<String, String>> list = jinDao.selectBusCategory();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append("<option value='" + list.get(i).get("BCT_CODE") + "'>");
			sb.append(list.get(i).get("BCT_NAME") + "</option>");
		} // for End
		return sb.toString();
	} // method End

	// 서진 : 예약 페이지
	public ModelAndView bookingForm(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, String> hMap = new HashMap<String, String>();
		if (request.getQueryString() != null) {
			String queryString = request.getQueryString();
			String[] parameters = queryString.split("&");
			for (String parameter : parameters) {
				String name = parameter.split("=")[0];
				String value = parameter.split("=")[1];
				hMap.put(name, value);
			} // for End
		} // if End

		/* 나중에 삭제할 것 */
		hMap.put("bus_no", "B1000063");
		hMap.put("bct_code", "M");

		hMap.put("per_no", request.getSession().getAttribute("no").toString());
		Map<String, String> petMap = new HashMap<String, String>();
		if (hMap.get("pet_no") == null) {
			petMap = jinDao.selectFirstPet(hMap);
		} else {
			petMap = jinDao.firstPet(hMap);
		} // if End
		hMap.put("pet_no", petMap.get("PET_NO"));
		if (hMap.get("date") == null) {
			hMap.put("date", "noDate");
		} // if End
		StringBuilder pet = new StringBuilder();
		pet.append("<p>예약할 동물</p>");
		pet.append("<div><img src='" + petMap.get("PET_LOC") + petMap.get("PET_PHOTO"));
		pet.append("' style='width: 100%; height: auto;' />");
		pet.append("<p>" + petMap.get("PET_NAME") + "</p></div>");
		pet.append("<input type='hidden' name='bus_no' value='" + hMap.get("bus_no") + "' />");
		pet.append("<input type='hidden' name='bct_code' value='" + hMap.get("bct_code") + "' />");
		pet.append("<input type='hidden' name='pet_no' value='" + petMap.get("PET_NO") + "' />");
		List<HashMap<String, String>> petList = jinDao.selectPetList(petMap);
		pet.append("<p>다른 동물 선택</p>");
		for (int i = 0; i < petList.size(); i++) {
			pet.append("<a href='./bookingForm?bus_no=" + hMap.get("bus_no"));
			pet.append("&bct_code=" + hMap.get("bct_code"));
			pet.append("&date=" + hMap.get("date") + "&pet_no=" + petList.get(i).get("PET_NO"));
			pet.append("'>" + petList.get(i).get("PET_NAME") + "</a><br/>");
		} // for End
		mav.addObject("petList", pet);
		List<HashMap<String, String>> svcList = jinDao.selectSvcList(hMap);
		StringBuilder svc = new StringBuilder();
		svc.append("<p>서비스 선택</p>");
		for (int i = 0; i < svcList.size(); i++) {
			svc.append("<input type='checkbox' name='menu_no' value='");
			svc.append(String.valueOf(svcList.get(i).get("MENU_NO")) + "' />");
			svc.append(svcList.get(i).get("MENU_NAME"));
			if (!svcList.get(i).get("PRICE").equals("0")) {
				svc.append(" (" + svcList.get(i).get("PRICE") + " 원)");
			} // if End
			svc.append("<br/>");
			svc.append("<input type='hidden' name='" + String.valueOf(svcList.get(i).get("MENU_NO")));
			svc.append("' value='" + svcList.get(i).get("PRICE") + "' />");
		} // for End
		mav.addObject("svcList", svc);
		List<HashMap<String, String>> empList = jinDao.selectEmpList(hMap);
		StringBuilder emp = new StringBuilder();
		emp.append("<p>직원 선택</p>");
		for (int i = 0; i < empList.size(); i++) {
			emp.append("<input type='radio' name='emp_no' value='");
			emp.append(empList.get(i).get("EMP_NO") + "' /> ");
			emp.append(empList.get(i).get("EMP_NAME") + " " + empList.get(i).get("EMP_POS"));
			emp.append(" (" + empList.get(i).get("EMP_PART") + ")<br/>");
		} // for End
		mav.addObject("empList", emp);
		Date date = null;
		if (hMap.get("date") != null && !hMap.get("date").equals("noDate")) {
			try {
				date = new SimpleDateFormat("yy/MM/dd").parse(hMap.get("date"));
			} catch (ParseException e) {
				e.printStackTrace();
			} // catch End
		} else {
			date = new Date();
		} // else End
		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(date);
		StringBuilder dayList = new StringBuilder();
		dayList.append("<tr>");
		for (int i = 0; i < 7; i++) {
			int day = cal.get(Calendar.DAY_OF_WEEK);
			SimpleDateFormat valSdf = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf = null;
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
			String strDate = sdf.format(cal.getTime());
			String valDate = valSdf.format(cal.getTime());
			dayList.append("<td><input type='radio' name='day' value='" + valDate + "' />");
			dayList.append(strDate + "</td>");
			cal.add(Calendar.DAY_OF_YEAR, 1);
		} // for End
		dayList.append("</tr>");
		mav.addObject("dayList", dayList);
		return mav;
	} // method End

	// 서진 : 예약 페이지 시간
	public String timeSelect(HttpServletRequest request) {
		HashMap<String, String> hMap = new HashMap<String, String>();
		hMap.put("date", request.getParameter("date"));
		hMap.put("emp_no", request.getParameter("emp"));
		HashMap<String, String> timeMap = jinDao.selectEmpTime(hMap);
		String timeList = "";
		List<HashMap<String, String>> noTimeList = jinDao.selectNoTime(hMap);
		if (timeMap == null) { // DB에 시간 없을 때
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
			if (hMap.get("date").equals(new SimpleDateFormat("yyMMdd").format(new Date()))) { // 오늘일 때
				String todayTime = new SimpleDateFormat("HHmm").format(new Date());
				int todayHour = Integer.parseInt(todayTime.substring(0, 2));
				int todayMin = Integer.parseInt(todayTime.substring(2, 4));
				String strHour = "";
				String strMin = "";
				if (todayMin < 30) {
					strMin = "30";
				} else {
					strMin = "00";
					todayHour++;
				} // else End
				todayHour++;
				if (todayHour < 10) {
					strHour = "0" + todayHour;
				} else {
					strHour = String.valueOf(todayHour);
				} // else End
				todayTime = strHour + strMin;
				// 비교를 위한 변수
				if (Integer.parseInt(todayTime) < Integer.parseInt(timeMap.get("AM_OPEN"))) {
					String amTimeList = timeHtml(amOpenHour, amOpenMin, amCloseHour, amCloseMin, noTimeList);
					String pmTimeList = timeHtml(pmOpenHour, pmOpenMin, pmCloseHour, pmCloseMin, noTimeList);
					timeList = amTimeList + pmTimeList;
				} else if (Integer.parseInt(todayTime) < Integer.parseInt(timeMap.get("AM_CLOSE"))) {
					amOpenHour = Integer.parseInt(todayTime.substring(0, 2));
					amOpenMin = Integer.parseInt(todayTime.substring(2, 4));
					String amTimeList = timeHtml(amOpenHour, amOpenMin, amCloseHour, amCloseMin, noTimeList);
					String pmTimeList = timeHtml(pmOpenHour, pmOpenMin, pmCloseHour, pmCloseMin, noTimeList);
					timeList = amTimeList + pmTimeList;
				} else if (Integer.parseInt(todayTime) < Integer.parseInt(timeMap.get("PM_OPEN"))) {
					timeList = timeHtml(pmOpenHour, pmOpenMin, pmCloseHour, pmCloseMin, noTimeList);
				} else if (Integer.parseInt(todayTime) < Integer.parseInt(timeMap.get("PM_CLOSE"))) {
					pmOpenHour = Integer.parseInt(todayTime.substring(0, 2));
					pmOpenMin = Integer.parseInt(todayTime.substring(2, 4));
					timeList = timeHtml(pmOpenHour, pmOpenMin, pmCloseHour, pmCloseMin, noTimeList);
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

	// 서진 : 시간 만드는 메소드
	private String timeHtml(int openHour, int openMin, int closeHour, int closeMin,
			List<HashMap<String, String>> noTimeList) {
		int length = 0;
		if (openMin == closeMin) {
			length = (closeHour - openHour) * 2;
		} else if (openMin > closeMin) {
			length = (closeHour - openHour) * 2 - 1;
		} else if (openMin < closeMin) {
			length = (closeHour - openHour) * 2 + 1;
		} // if End
		StringBuilder timeList = new StringBuilder();
		timeList.append("<tr>");
		String time = "";
		for (int i = 1; i <= length; i++) {
			if (openHour == 0) {
				time += "00";
			} else if (openHour < 10) {
				time = "0" + openHour;
			} else {
				time += openHour;
			} // else End
			if (openMin == 0) {
				time += "00";
			} else {
				time += openMin;
			} // else End
			System.out.println("time" + time);
			timeList.append("<td><input type='radio' name='time' value='" + time);
			for (int j = 0; j < noTimeList.size(); j++) {
				if (noTimeList.get(j).get("VS_START").equals(time)) {
					timeList.append("' disabled='disabled");
				} // if End
			} // for End
			timeList.append("' />" + time.substring(0, 2) + ":" + time.substring(2, 4) + "</td>");
			if (i % 5 == 0) {
				timeList.append("</tr><tr>");
			} // if End
			if (openMin == 30) {
				openHour += 1;
				openMin = 0;
			} else {
				openMin += 30;
			} // else End
			time = "";
		} // for End
		timeList.append("</tr>");
		return timeList.toString();
	} // method End

	// 서진 : 예약 메소드
	@Transactional
	public void insertBooking(Booking bk, HttpServletRequest request) {
		bk.setPer_no(request.getSession().getAttribute("no").toString());
		bk.setVs_start(request.getParameter("day") + request.getParameter("time"));
		String[] menu_no = request.getParameterValues("menu_no");
		int sum = 0;
		for (int i = 0; i < menu_no.length; i++) {
			sum += Integer.parseInt(menu_no[i]);
		} // for End
		bk.setBk_pay(sum);
		jinDao.insertBooking(bk);
		bk.setBk_no("K" + String.valueOf(bk.getBk_seq()));
		for (int i = 0; i < menu_no.length; i++) {
			bk.setMenu_no(Integer.parseInt(menu_no[i]));
			jinDao.insertBkMenu(bk);
		} // for End
	} // method End

} // class End
