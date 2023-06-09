package com.docmall.service;

import com.docmall.domain.OrderVO;
import com.docmall.domain.PaymentVO;

public interface OrderService {

	// 주문정보
	void order_save(OrderVO o_vo, PaymentVO p_vo);

}


















