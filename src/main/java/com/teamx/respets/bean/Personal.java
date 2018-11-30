package com.teamx.respets.bean;

//@Alias("member")
public class Personal {	
	//개인회원 
	private String per_no;
	private String per_email;
	private String per_pw;
	private String per_name;
	private String per_phone;
	private String per_photo;
	private String per_loc;
	
	public String getPer_email() {
		return per_email;
	}
	public void setPer_email(String per_email) {
		this.per_email = per_email;
	}
	public String getPer_pw() {
		return per_pw;
	}
	public void setPer_pw(String per_pw) {
		this.per_pw = per_pw;
	}
	public String getPer_name() {
		return per_name;
	}
	public void setPer_name(String per_name) {
		this.per_name = per_name;
	}
	public String getPer_phone() {
		return per_phone;
	}
	public void setPer_phone(String per_phone) {
		this.per_phone = per_phone;
	}
	public String getPer_loc() {
		return per_loc;
	}
	public void setPer_loc(String per_loc) {
		this.per_loc = per_loc;
	}
	public String getPer_no() {
		return per_no;
	}
	public void setPer_no(String per_no) {
		this.per_no = per_no;
	}
	public String getPer_photo() {
		return per_photo;
	}
	public void setPer_photo(String per_photo) {
		this.per_photo = per_photo;
	}
}
