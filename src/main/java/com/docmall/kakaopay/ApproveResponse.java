package com.docmall.kakaopay;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
// 2) 결제 승인 요청시 넘어오는 응답데이터(두번째 요청)
public class ApproveResponse {

	private String aid;
	private String tid;
	private String cid;
	private String sid;
	private String partner_order_id;
	private String partner_user_id;
	private String payment_method_type;
	private Amount amount;
	private String item_name;
	private String item_code;
	private int quantity;
	private Date created_at;
	private Date approved_at;
	private String payload;
}
