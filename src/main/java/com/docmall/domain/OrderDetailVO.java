package com.docmall.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderDetailVO {

	private Long ord_code;
	private int pro_num;
	private int dt_amount;
	private int dt_price;
}
