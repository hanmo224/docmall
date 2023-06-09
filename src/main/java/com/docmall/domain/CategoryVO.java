package com.docmall.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryVO {
	
	private Integer cat_code; // PK 참조되는 것이라 Integer 또는 Long
	private int	cat_prtcode;
	private String cat_name;
}
