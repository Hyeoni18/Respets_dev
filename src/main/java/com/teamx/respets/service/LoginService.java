package com.teamx.respets.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.Personal;
import com.teamx.respets.dao.LoginDao;

@Service
public class LoginService {
	ModelAndView mav;
	@Autowired
	private HyunHwiService hhs;
	@Autowired
	private LoginDao lDao;
	
	//개인 회원가입 이메일 중복 확인
	public int emailChkSignUp(String email) {
		int result =lDao.emailChkSignUp(email);
		return result;
	} // emailChkSignUp End
	
	//개인회원가입
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
		hhs.mailSending(tomail, title, content);
		view = "emailConfirmOffer";
		mav.setViewName(view);
		return mav;
	} // personalJoin End
	
	//로그인 (개인, 기업 통합)
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
								hhs.mailSending(tomail, title, content);
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
								hhs.mailSending(tomail, title, content);
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
	
	//이메일 인증 확인
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
	
	//로그아웃
	public ModelAndView logout(HttpServletRequest request) {
		mav = new ModelAndView();
		mav.setViewName("redirect:/");
		request.getSession().invalidate();
		return mav;
	} // logout End


}
