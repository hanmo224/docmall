package com.docmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.AdOrderDetailVO;
import com.docmall.domain.OrderVO;
import com.docmall.dto.Criteria;

public interface AdOrderMapper {

	// 주문 목록
	List<OrderVO> orderList(@Param("cri") Criteria cri, @Param("sDate") String sDate, @Param("eDate") String eDate);
	
	// 목록 데이터 개수
	int getTotalCount(@Param("cri") Criteria cri, @Param("sDate") String sDate, @Param("eDate") String eDate);
	
	// 주문상세 보기
	List<AdOrderDetailVO> orderDetail(Long ord_code);
	
	// 주문상세 주문상품 삭제
	void orderDetailProdcutDelete(@Param("ord_code") Long ord_code, @Param("pro_num") Integer pro_num);
}











