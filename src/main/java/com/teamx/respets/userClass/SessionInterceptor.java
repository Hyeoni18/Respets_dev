package com.teamx.respets.userClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getSession().getAttribute("no") == null) {
			response.sendRedirect("./");
			return false;
		} // if End
		return true; // 꼭 써줘야 한다.
	} // method End

//	// 서블릿 지나서 서비스 클래스 가기 전에 인터셉트 한 후 작업 (잘 쓰이지 않는다.)
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		System.out.println("postHandle");
//		modelAndView.addObject("msg", "postHandle!!");
//	} // method End

} // class End