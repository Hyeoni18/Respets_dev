package com.teamx.respets.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.Business;
import com.teamx.respets.bean.Personal;
import com.teamx.respets.bean.RandomTB;
import com.teamx.respets.dao.LoginDao;

@Service
public class LoginService {
	ModelAndView mav;
	@Autowired
	private LoginDao lDao;
	@Autowired
	private JavaMailSender mailSender; // 메일보낼시 필요

	// 개인 회원가입 이메일 중복 확인
	public int emailChkSignUp(String email) {
		int result = lDao.emailChkSignUp(email);
		return result;
	} // emailChkSignUp End

	// 개인회원가입
	public ModelAndView personalJoin(MultipartHttpServletRequest multi) {
		mav = new ModelAndView();
		String view = null;
		String email = multi.getParameter("per_email");
		String pw = multi.getParameter("per_pw");
		String name = multi.getParameter("per_name");
		String phone = multi.getParameter("per_phone");
		MultipartFile photo = multi.getFile("photo");
		int check = Integer.parseInt(multi.getParameter("fileCheck"));
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
			personalJoinResult = lDao.personalJoinPhoto(hmap);
			if (personalJoinResult == true) {
				System.out.println("insert 성공");
			} else {
				System.out.println("insert 실패");
			}
		} else {
			personalJoinResult = lDao.personalJoin(hmap);
			if (personalJoinResult == true) {
				System.out.println("사진X insert 성공");
			} else {
				System.out.println("사진X insert 실패");
			}
		}
		mav.addObject("email", email);
		String tomail = email;
		String title = "[Respets] 계정 인증 메일";
		String content = "링크를 클릭해주세요. http://localhost/emailConfirmSuccess?per_email=" + email;
		mailSending(tomail, title, content);
		view = "emailConfirmOffer";
		mav.setViewName(view);
		return mav;
	} // personalJoin End

	// 로그인 (개인, 기업 통합)
	public ModelAndView loginProcess(String email, String pw, HttpServletRequest request) { // loginForm에 email, pw를
		mav = new ModelAndView();
		HashMap<String, Object> hmap = new HashMap<String, Object>();
		String view = null;
		String loginFailed = "alert('로그인에 실패하였습니다.')";
		String noEmail = "alert('계정과 비밀번호를 확인해주세요.')";
		hmap.put("email", email); // input 에서 받아 온 email을 hashMap에 담는다.
		hmap.put("pw", pw);
		hmap = lDao.loginProcess(hmap); // hmap 에 담은 email을 가지고 db 테이블에 가서 select한 정보를 다시 hmap에 담아 가져온다.

		if (hmap != null) {
			String no = (String) hmap.get("NO"); // hmap에 담아 온 회원번호를 가져온다.
			String name = hmap.get("NAME").toString();
			if (no != null) { // 회원 번호가 null이 아니라면
				hmap.put("no", no); // 회원 번호를 다시 hmap에 담는다
				String leave = hmap.get("LEAVE").toString();
				// request.getSession().setAttribute("no", no);
				request.getSession().setAttribute("no", no); // 세션에 회원번호를 담는다.

				HashMap<String, Object> bmap = new HashMap<String, Object>();
				bmap.put("no", no); // bmap에 회원 번호를 담는다.
				bmap = lDao.blackChk(bmap); // bmap에 담긴 회원번호를 가지고 블랙리스트 여부를 검사

				if (leave.equals("O")) {
					String alert = "alert('탈퇴한 회원의 이메일 입니다. 로그인 할 수 없습니다.');";
					mav.addObject("leave", alert);
					request.getSession().invalidate();
					view = "loginForm";
				} else {
					if (bmap != null) {
						Integer out_no = (int) bmap.get("OUT_NO");
						Date blk_time = (Date) bmap.get("BLK_TIME");
						System.out.println(blk_time);

						if (out_no == 1 || out_no == 2) { // 블랙리스트 테이블 경고 번호 확인
							String alert = "alert('이용이 정지된 계정입니다. 로그인 할 수 없습니다.');";
							mav.addObject("alert", alert); // 경고창을 띄우고
							request.getSession().invalidate(); // 세션을 만료시킨다.
							view = "loginForm";
						}
					} else { // 블랙리스트 테이블에서 회원번호가 select되지 안을 때
						if (no.contains("P")) { // 회원 번호에 P가 포함 되어 있으면
							hmap = lDao.chkPerEm(hmap); // hmap에 pw를 담아 정보를 가져온다.
							String perEmChk = (String) hmap.get("PER_EMCHK");
							String loc = hmap.get("PER_LOC").toString();
							String photo = hmap.get("PER_PHOTO").toString();
							request.getSession().setAttribute("name", name);
							request.getSession().setAttribute("loc", loc);
							request.getSession().setAttribute("photo", photo);
							System.out.println("개인회원: " + hmap);

							if (perEmChk.equals("O")) {// 개인회원 인증 여부 확인
								System.out.println("인증된 개인 회원");
								view = "redirect:/";
							} else {
								System.out.println("개인회원 인증X");
								String sendEmail = (String) hmap.get("PER_EMAIL");
								String tomail = sendEmail;
								String title = "[Respets] 계정 인증 메일";
								String content = "링크를 클릭해주세요. http://localhost/emailConfirmSuccess?per_email="
										+ sendEmail;
								mailSending(tomail, title, content);
								mav.addObject("email", sendEmail);
								request.getSession().invalidate();
								view = "emailConfirmOffer";
							} // 개인회원 if end

						} else if (no.contains("B")) {
							hmap = lDao.chkBusEm(hmap);
							String busEmChk = (String) hmap.get("BUS_EMCHK");
							System.out.println("기업회원: " + hmap);

							if (busEmChk.equals("O")) {
								HashMap<String, Object> pMap = new HashMap<>();
								pMap = lDao.getBusinessPhoto(no);
								String loc = pMap.get("GLR_LOC").toString();
								String photo = pMap.get("GLR_FILE").toString();
								String bus_name = lDao.selectBus_name(no);
								request.getSession().setAttribute("name", bus_name);
								request.getSession().setAttribute("loc", loc);
								request.getSession().setAttribute("photo", photo);
								view = "redirect:/";
							} else {
								String sendEmail = (String) hmap.get("BUS_EMAIL");
								String tomail = sendEmail;
								String title = "[Respets] 계정 인증 메일";
								String content = "링크를 클릭해주세요. http://localhost/emailConfirmSuccess?per_email="
										+ sendEmail;
								mailSending(tomail, title, content);
								mav.addObject("email", sendEmail);
								request.getSession().invalidate(); // 세션 만
								view = "emailConfirmOffer";
							}
						} // 기업회원 if end
					}
				} // bmap null end
			} else { // 회원번호가 null 이라면
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

	// 이메일 인증 확인
	public ModelAndView emailConfirmSuccess(HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		String email = request.getParameter("per_email");
		mav.addObject("email", email);
		int result = lDao.emailConfirmSuccess(email);
		view = "emailConfirmSuccess";
		mav.setViewName(view);
		return mav;
	} // emailConfirmSuccess End

	// 로그아웃
	public ModelAndView logout(HttpServletRequest request) {
		mav = new ModelAndView();
		mav.setViewName("redirect:/");
		request.getSession().invalidate();
		return mav;
	} // logout End

	// 아이디 찾기
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
		map = lDao.selectUser(map);

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

	// 이메일로 비밀번호 변경 폼 보내기
	public ModelAndView findMyPw(HttpServletRequest request) {
		mav = new ModelAndView();
		String userId = request.getParameter("email");
		String userType = request.getParameter("type");
		List<Map<String, Object>> list;
		list = lDao.searchRND(userId);
		if (list.size() != 0) {
			lDao.deleteRcode(userId);
		}
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
		int result = lDao.findMyPw(rtb); // 가지고 있는 정보를 가지고 랜덤 테이블 접근.
		if (result != 0) { // insert에 성공 했을 경우
			String tomail = rtb.getRnd_email(); // 메일을 받는 이메일 주소
			String title = "비밀번호 변경 페이지"; // 메일의 제목
			String content = "http://localhost/resetMyPwForm?per_email=" + rtb.getRnd_email() + "&code="
					+ rtb.getRnd_code() + "&type=" + rtb.getRnd_type();
			mailSending(tomail, title, content); // 메일 보내기 메소드로 이동
		}
		String findPw = "<script>alert('이메일로 비밀번호 재설정 링크를 보냈습니다.');</script>";
		mav.addObject("findPw", findPw);
		view = "index";
		mav.setViewName(view);
		return mav;
	}

	// 메일 보내는 메소드
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

	// 신원을 확인하는 메소드, 보낸 랜덤값과 받은 사용자가 맞는지 확인한다.
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
		rtb = lDao.checkRcode(rtb);
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

	// 메일의 유효시간을 확인하는 메소드, minute 으로 계산해준다.
	private long dateCheck(RandomTB rtb) {
		long rand = rtb.getRnd_date().getTime();
		Date curDate = new Date();
		long curDateTime = curDate.getTime();
		long minute = (curDateTime - rand) / 60000;
		return minute;
	}

	// 변경한 비밀번호 값으로 회원정보를 변경한다.
	public ModelAndView updateMyPw(HttpServletRequest request, Personal ps) {
		mav = new ModelAndView();
		String type = request.getParameter("type"); // 개인,기업 구분
		if (type.equals("P")) { // 개인일 경우
			lDao.resetPerPw(ps);
		} else if (type.equals("B")) { // 기업일 경우
			System.out.println(type);
			lDao.resetBusPw(ps);
		}
		lDao.deleteRcode(ps.getPer_email()); // 변경이 완료되면 랜덤값을 지워준다.
		String updateMyPw = "<script>alert('비밀번호가 변경되었습니다.');</script>";
		mav.addObject("updateMyPw", updateMyPw);
		String view = "loginForm";
		mav.setViewName(view);
		return mav;
	}

	// 기업 회원 가입 시 업종 라디오 버튼 생성
	public String selectBusCategory() {
		List<HashMap<String, String>> list = lDao.selectBusCategory();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append("<input type='radio' name='bct_code' class='주력 서비스' value='");
			sb.append(list.get(i).get("BCT_CODE"));
			sb.append("'/>" + list.get(i).get("BCT_NAME") + "</label>");
		} // for End
		return sb.toString();
	} // method End

	// 기업 회원 가입 시 이메일 확인
	public int emailCheck(String email) {
		int result = lDao.emailCheck(email);
		return result;
	} // method End

	// 기업 회원 가입 시 사업자 등록 번호 확인
	public int taxIdCheck(String taxId) {
		int result = lDao.taxIdCheck(taxId);
		return result;
	} // method End

	// 기업 회원 가입
	@Transactional
	public void businessJoin(Business b, MultipartHttpServletRequest request) {
		// 비밀번호 암호화 구현할 것
		lDao.businessInsert(b); // 기업 회원 테이블
		b.setBus_no("B" + String.valueOf(b.getBus_seq()));
		lDao.busJoinSvcInsert(b); // 서비스 테이블
		MultipartFile busLicense = request.getFile("busLicense");
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap = saveFile(request, busLicense, hMap);
		hMap.put("bus_no", b.getBus_no());
		hMap.put("bct_code", b.getBct_code());
		lDao.licenseInsert(hMap); // 사진 테이블에 사업자등록증 사진 넣기
		// 대표 사진 이미지가 있을 경우 대표 사진 넣기
		if (request.getParameter("fileCheck").equals("1")) {
			MultipartFile mainPhoto = request.getFile("mainPhoto");
			hMap = saveFile(request, mainPhoto, hMap);
			lDao.mainPhotoInsert(hMap);
		} else { // 없을 경우 디폴트 이미지로 넣기
			lDao.mainPhotoDefault(hMap);
		} // else End
	} // method End

	// 파일 업로드
	private Map<String, Object> saveFile(MultipartHttpServletRequest request, MultipartFile file,
			Map<String, Object> hMap) {
		String root = request.getSession().getServletContext().getRealPath("/");
		String location = "resources/upload/";
		String path = root + location; // 이미지 저장하는 위치
		File dir = new File(path); 
		if (!dir.isDirectory()) { // 해당 폴더가 없을 때 생성
			dir.mkdir();
		} // if End
		String date = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		// 파일 이름 중복을 없애기 위해 날짜와 UUID 이용
		String saveName = "Respets_" + date + "_" + UUID.randomUUID() + "." + extension;
		try {
			file.transferTo(new File(path, saveName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} // catch End
		hMap.put("location", location); // 주소
		hMap.put("file", saveName); // 파일 이름
		return hMap;
	} // method End
	
}
