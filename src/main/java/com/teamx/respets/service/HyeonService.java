package com.teamx.respets.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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


}
