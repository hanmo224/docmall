package com.docmall.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

//사용자로 부터 로그인 정보를 받기 위한 용도. 안만들어도 됨
public class LoginDTO {

	private String mbsp_id;
	private String mbsp_password;
}
