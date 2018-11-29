package com.teamx.respets.bean;

import java.sql.Date;

public class AdminBoard {
	private int page_no;
	private String search;
	private String abc_no;
	private String abc_name;
	private String abo_no;
	private String abo_title;
	private String abo_ctt;
	private Date abo_date;
	private Date abo_date_string;

	public int getPage_no() {
		return page_no;
	}

	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getAbc_no() {
		return abc_no;
	}

	public void setAbc_no(String abc_no) {
		this.abc_no = abc_no;
	}

	public String getAbc_name() {
		return abc_name;
	}

	public void setAbc_name(String abc_name) {
		this.abc_name = abc_name;
	}

	public String getAbo_no() {
		return abo_no;
	}

	public void setAbo_no(String abo_no) {
		this.abo_no = abo_no;
	}

	public String getAbo_title() {
		return abo_title;
	}

	public void setAbo_title(String abo_title) {
		this.abo_title = abo_title;
	}

	public String getAbo_ctt() {
		return abo_ctt;
	}

	public void setAbo_ctt(String abo_ctt) {
		this.abo_ctt = abo_ctt;
	}

	public Date getAbo_date() {
		return abo_date;
	}

	public void setAbo_date(Date abo_date) {
		this.abo_date = abo_date;
	}

	public Date getAbo_date_string() {
		return abo_date_string;
	}

	public void setAbo_date_string(Date abo_date_string) {
		this.abo_date_string = abo_date_string;
	}

}
