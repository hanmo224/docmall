package com.docmall.service;

import java.util.List;

import com.docmall.domain.AdOrderDetailVO;
import com.docmall.domain.OrderVO;
import com.docmall.dto.Criteria;

public interface AdOrderService {

	// 주문 목록
	List<OrderVO> orderList(Criteria cri, String sDate, String eDate);
		
	// 목록 데이터 개수
	int getTotalCount(Criteria cri, String sDate, String eDate);
	
	// 주문상세 보기
	List<AdOrderDetailVO> orderDetail(Long ord_code);
	
	// 주문상세 주문상품 삭제
	void orderDetailProdcutDelete(Long ord_code, Integer pro_num);
}











