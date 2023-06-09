package com.docmall.domain;

import lombok.Data;

@Data
public class AdOrderDetailVO {

	private Long ord_code;
	private Integer pro_num;
	private int dt_amount;
	private int dt_price;
	private String pro_name;
	private String pro_img;
	private String pro_up_folder;
}
