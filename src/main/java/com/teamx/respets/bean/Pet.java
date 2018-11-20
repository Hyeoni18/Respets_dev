package com.teamx.respets.bean;

//반려동물 Bean
public class Pet {
	/* 개인회원 테이블(PER) */
	private String per_no;	//개인회원번호
	
	/*---동물 테이블(PET)---*/
	private String pet_no;	//번호
	private String pet_vrt;	//품종 (variety)
	private String pet_name;//이름
	private String pet_age;	//나이
	private String pet_sex;	//성별
	private String pet_ntr;	//중성화여부 (neutralization)
	private String pet_photo;//사진
	private String pet_loc;	//사진경로 (location)
	
	/*---동물 컬럼 테이블(PCL)/동물 상세 테이블(PDT)---*/
	private String pet_wght;//몸무게 (weight), 컬럼번호1
	private String pet_sick;//질병, 컬럼번호2
	private String pet_mday;//만난날, 컬럼번호3
	private String pet_food;//주의해야할 음식, 컬럼번호4
	private String pet_rat;	//식사 횟수 및 배급량 (ration), 컬럼번호5
	private String pet_tot;	//배변훈련 (toilet training), 컬럼번호6
	private String pet_etc;	//특이사항, 컬럼번호7
	private int pcl_no;	//컬럼번호
	private String pdt_ctt;	//디테일내용
	
	/*---동물 종류 테이블(PTY)---*/
	private String pty_no;	//종류번호
	private String pty_name;//종류명
	
	public String getPer_no() {
		return per_no;
	}
	public void setPer_no(String per_no) {
		this.per_no = per_no;
	}
	public String getPet_no() {
		return pet_no;
	}
	public void setPet_no(String pet_no) {
		this.pet_no = pet_no;
	}
	public String getPet_vrt() {
		return pet_vrt;
	}
	public void setPet_vrt(String pet_vrt) {
		this.pet_vrt = pet_vrt;
	}
	public String getPet_name() {
		return pet_name;
	}
	public void setPet_name(String pet_name) {
		this.pet_name = pet_name;
	}
	public String getPet_age() {
		return pet_age;
	}
	public void setPet_age(String pet_age) {
		this.pet_age = pet_age;
	}
	public String getPet_sex() {
		return pet_sex;
	}
	public void setPet_sex(String pet_sex) {
		this.pet_sex = pet_sex;
	}
	public String getPet_ntr() {
		return pet_ntr;
	}
	public void setPet_ntr(String pet_ntr) {
		this.pet_ntr = pet_ntr;
	}
	public String getPet_photo() {
		return pet_photo;
	}
	public void setPet_photo(String pet_photo) {
		this.pet_photo = pet_photo;
	}
	public String getPet_loc() {
		return pet_loc;
	}
	public void setPet_loc(String pet_loc) {
		this.pet_loc = pet_loc;
	}
	public String getPet_wght() {
		return pet_wght;
	}
	public void setPet_wght(String pet_wght) {
		this.pet_wght = pet_wght;
	}
	public String getPet_sick() {
		return pet_sick;
	}
	public void setPet_sick(String pet_sick) {
		this.pet_sick = pet_sick;
	}
	public String getPet_mday() {
		return pet_mday;
	}
	public void setPet_mday(String pet_mday) {
		this.pet_mday = pet_mday;
	}
	public String getPet_food() {
		return pet_food;
	}
	public void setPet_food(String pet_food) {
		this.pet_food = pet_food;
	}
	public String getPet_rat() {
		return pet_rat;
	}
	public void setPet_rat(String pet_rat) {
		this.pet_rat = pet_rat;
	}
	public String getPet_tot() {
		return pet_tot;
	}
	public void setPet_tot(String pet_tot) {
		this.pet_tot = pet_tot;
	}
	public String getPet_etc() {
		return pet_etc;
	}
	public void setPet_etc(String pet_etc) {
		this.pet_etc = pet_etc;
	}
	public int getPcl_no() {
		return pcl_no;
	}
	public void setPcl_no(int pcl_no) {
		this.pcl_no = pcl_no;
	}
	public String getPdt_ctt() {
		return pdt_ctt;
	}
	public void setPdt_ctt(String pdt_ctt) {
		this.pdt_ctt = pdt_ctt;
	}
	public String getPty_no() {
		return pty_no;
	}
	public void setPty_no(String pty_no) {
		this.pty_no = pty_no;
	}
	public String getPty_name() {
		return pty_name;
	}
	public void setPty_name(String pty_name) {
		this.pty_name = pty_name;
	}

}// class End
