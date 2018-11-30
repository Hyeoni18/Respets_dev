package com.teamx.respets.bean;

import java.util.Date;

public class RandomTB {
	private String rnd_email;
	private String rnd_code;
	private char rnd_type;
	private Date rnd_date;
	public String getRnd_email() {
		return rnd_email;
	}
	public void setRnd_email(String rnd_email) {
		this.rnd_email = rnd_email;
	}
	public Date getRnd_date() {
		return rnd_date;
	}
	public void setRnd_date(Date rnd_date) {
		this.rnd_date = rnd_date;
	}
	public String getRnd_code() {
		return rnd_code;
	}
	public void setRnd_code(String rnd_code) {
		this.rnd_code = rnd_code;
	}
	public char getRnd_type() {
		return rnd_type;
	}
	public void setRnd_type(char rnd_type) {
		this.rnd_type = rnd_type;
	}
}
