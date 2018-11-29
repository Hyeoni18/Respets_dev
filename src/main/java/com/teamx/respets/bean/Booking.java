package com.teamx.respets.bean;

import java.util.Date;

public class Booking {
	/* 예약 테이블 */
	private int bk_seq;
	private String bk_no;
	private String per_no;
	private String pet_no;
	private String bus_no;
	private String bct_code;
	private String emp_no;
	private Date bk_time;
	private int bk_pay;
	private String vs_start;
	private String vs_end;
	private String bk_cmt;
	private String bk_chk;
	private String vs_chk;
	private String bk_rejmsg;
	private int menu_no;
	/* 캘린더에서 사용 */
	private String pet_name;
	private String bus_name;
	private String bct_name;
	/* 결제 테이블 */
	private int pm_price;
	private Date pm_time;
	private String pm_way;
	/* 취소 수수료 테이블 */
	private int car_day;
	private int car_crg;
	/* 예약 취소 테이블 */
	private Date can_time;

	public int getBk_seq() {
		return bk_seq;
	}

	public void setBk_seq(int bk_seq) {
		this.bk_seq = bk_seq;
	}

	public String getBk_no() {
		return bk_no;
	}

	public void setBk_no(String bk_no) {
		this.bk_no = bk_no;
	}

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

	public String getBus_no() {
		return bus_no;
	}

	public void setBus_no(String bus_no) {
		this.bus_no = bus_no;
	}

	public String getBct_code() {
		return bct_code;
	}

	public void setBct_code(String bct_code) {
		this.bct_code = bct_code;
	}

	public String getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}

	public Date getBk_time() {
		return bk_time;
	}

	public void setBk_time(Date bk_time) {
		this.bk_time = bk_time;
	}

	public int getBk_pay() {
		return bk_pay;
	}

	public void setBk_pay(int bk_pay) {
		this.bk_pay = bk_pay;
	}

	public String getVs_start() {
		return vs_start;
	}

	public void setVs_start(String vs_start) {
		this.vs_start = vs_start;
	}

	public String getVs_end() {
		return vs_end;
	}

	public void setVs_end(String vs_end) {
		this.vs_end = vs_end;
	}

	public String getBk_cmt() {
		return bk_cmt;
	}

	public void setBk_cmt(String bk_cmt) {
		this.bk_cmt = bk_cmt;
	}

	public String getBk_chk() {
		return bk_chk;
	}

	public void setBk_chk(String bk_chk) {
		this.bk_chk = bk_chk;
	}

	public String getVs_chk() {
		return vs_chk;
	}

	public void setVs_chk(String vs_chk) {
		this.vs_chk = vs_chk;
	}

	public String getBk_rejmsg() {
		return bk_rejmsg;
	}

	public void setBk_rejmsg(String bk_rejmsg) {
		this.bk_rejmsg = bk_rejmsg;
	}

	public int getMenu_no() {
		return menu_no;
	}

	public void setMenu_no(int menu_no) {
		this.menu_no = menu_no;
	}

	public String getPet_name() {
		return pet_name;
	}

	public void setPet_name(String pet_name) {
		this.pet_name = pet_name;
	}

	public String getBus_name() {
		return bus_name;
	}

	public void setBus_name(String bus_name) {
		this.bus_name = bus_name;
	}

	public String getBct_name() {
		return bct_name;
	}

	public void setBct_name(String bct_name) {
		this.bct_name = bct_name;
	}

	public int getPm_price() {
		return pm_price;
	}

	public void setPm_price(int pm_price) {
		this.pm_price = pm_price;
	}

	public Date getPm_time() {
		return pm_time;
	}

	public void setPm_time(Date pm_time) {
		this.pm_time = pm_time;
	}

	public String getPm_way() {
		return pm_way;
	}

	public void setPm_way(String pm_way) {
		this.pm_way = pm_way;
	}

	public int getCar_day() {
		return car_day;
	}

	public void setCar_day(int car_day) {
		this.car_day = car_day;
	}

	public int getCar_crg() {
		return car_crg;
	}

	public void setCar_crg(int car_crg) {
		this.car_crg = car_crg;
	}

	public Date getCan_time() {
		return can_time;
	}

	public void setCan_time(Date can_time) {
		this.can_time = can_time;
	}

} // class End
