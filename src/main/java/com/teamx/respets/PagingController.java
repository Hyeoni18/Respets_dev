package com.teamx.respets;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamx.respets.service.BusinessService;
import com.teamx.respets.service.PersonalService;

@Controller
public class PagingController {
	@Autowired
	private PersonalService ps;
	@Autowired
	private BusinessService bs;
	
	/* 전체예약 페이징 ajax */
	@RequestMapping(value = "/AllPaging", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody String AllPaging(HttpServletRequest request) {
		String text = bs.AllPaging(request);
		return text;
	}
	
	/* 서비스별 예약 페이징 ajax */
	@RequestMapping(value = "/bctAllPaging", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody String bctAllPaging(HttpServletRequest request) {
		String text = bs.bctAllPaging(request);
		return text;
	}

	/* 전체 예약에서의 검색 페이징 ajax */
	@RequestMapping(value = "/searchAllListPaging", produces = "application/text; charset=utf8")
	public @ResponseBody String searchAllListPaging(HttpServletRequest request) {
		String text = bs.searchAllListPaging(request);
		return text;
	}

	/* 서비스별 예약에서의 검색 페이징 ajax */
	@RequestMapping(value = "/searchBctAllsListPaging", produces = "application/text; charset=utf8")
	public @ResponseBody String searchBctAllsListPaging(HttpServletRequest request) {
		String text = bs.searchBctAllsListPaging(request);
		return text;
	}

}
