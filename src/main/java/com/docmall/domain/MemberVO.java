package com.docmall.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//lombok이 getter, setter, toString을 자동으로 생성해준다.
@Getter
@Setter
@ToString
public class MemberVO {

	private String mbsp_id;
	private String mbsp_name;
	private String mbsp_email;
	private String mbsp_password;
	private String mbsp_zipcode;
	private String mbsp_addr;
	private String mbsp_deaddr;
	private String mbsp_phone;
	private String mbsp_nick;
	private String mbsp_receive;
	private int	mbsp_point;
	private Date mbsp_lastlogin;
	private Date mbsp_datesub;
	private Date mbsp_updatedate;
}
