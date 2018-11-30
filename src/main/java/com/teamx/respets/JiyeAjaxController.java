package com.teamx.respets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.teamx.respets.service.JiyeService;

@RestController
public class JiyeAjaxController {
	@Autowired
	private JiyeService js;
	
	@RequestMapping(value = "/emailChkSignUp", method = RequestMethod.POST)
	public int emailChkSignUp(String email) {
		int result = js.emailChkSignUp(email);
		return result;
	}

}
