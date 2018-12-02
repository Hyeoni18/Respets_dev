package com.teamx.respets.bean;

import java.sql.Date;

public class BusinessBoard {
	private int page_no;
	private String bbc_no;
	private String bct_code;
	private String bct_name;
	private String bbc_name;
	private String bbo_title;
	private String bbo_ctt;
	private Date bbo_date;
	public int getPage_no() {
		return page_no;
	}
	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}
	public String getBbc_no() {
		return bbc_no;
	}
	public void setBbc_no(String bbc_no) {
		this.bbc_no = bbc_no;
	}
	public String getBct_code() {
		return bct_code;
	}
	public void setBct_code(String bct_code) {
		this.bct_code = bct_code;
	}
	public String getBct_name() {
		return bct_name;
	}
	public void setBct_name(String bct_name) {
		this.bct_name = bct_name;
	}
	public String getBbc_name() {
		return bbc_name;
	}
	public void setBbc_name(String bbc_name) {
		this.bbc_name = bbc_name;
	}
	public String getBbo_title() {
		return bbo_title;
	}
	public void setBbo_title(String bbo_title) {
		this.bbo_title = bbo_title;
	}
	public String getBbo_ctt() {
		return bbo_ctt;
	}
	public void setBbo_ctt(String bbo_ctt) {
		this.bbo_ctt = bbo_ctt;
	}
	public Date getBbo_date() {
		return bbo_date;
	}
	public void setBbo_date(Date bbo_date) {
		this.bbo_date = bbo_date;
	}
	
}
