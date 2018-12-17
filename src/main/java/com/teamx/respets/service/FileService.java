package com.teamx.respets.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class FileService {
	
	@Autowired
	HttpSession session;
	
	public Map<String, String> upload(MultipartHttpServletRequest multi) {
		System.out.println("upload Start");		
		//1.이클립스의 물리적 저장경로 찾기
		String root=multi.getSession().getServletContext().getRealPath("/");
		System.out.println("root="+root);
		String location = "resources/upload/";
		String path = root+location;/*resource는 webapp/resources에 저장*/			
		System.out.println("path="+path);
		//2.폴더 생성을 꼭 할것(clean했을 때 폴더 지워짐 방지)
		File dir=new File(path);
		if(!dir.isDirectory()){  //upload폴더 없다면
			dir.mkdir();  //upload폴더 생성
		}
		//3.파일을 가져오기-일태그 이름들 반환
		Iterator<String> files=multi.getFileNames(); //파일업로드 2개이상일때
		Map<String,String> fMap=new HashMap<String, String>();
		while(files.hasNext()){
			String fileTagName=files.next();
			System.out.println("fileTagNext="+fileTagName);
			//파일 메모리에 저장
			MultipartFile mf=multi.getFile(fileTagName); //실제파일
			String oriFileName=mf.getOriginalFilename();  //a.txt
			System.out.println("oriFileName="+oriFileName);
			fMap.put("oriFileName", oriFileName);
			//4.시스템파일이름 생성  a.txt-->112323242424.txt
			String sysFileName=System.currentTimeMillis()+"."
					+oriFileName.substring(oriFileName.lastIndexOf(".")+1);
			/*인덱스(점)을 기준으로 앞에 있는 문자열을 현재 시간을 기준으로한 밀리세컨드로 대체한다*/
			System.out.println("sysFileName="+sysFileName);
			fMap.put("sysFileName", sysFileName);
			fMap.put("location", location);
			//5.메모리->실제 파일 업로드
			try {
				/*실제로 파일을 업로드하는 메소드*/
				mf.transferTo(new File(path+sysFileName));				
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fMap;
	}
}
