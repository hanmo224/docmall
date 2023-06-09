package com.docmall.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CartVO {

	private Long cart_code;
	private Integer pro_num;
	private String mbsp_id;
	private int cart_amount;
}
