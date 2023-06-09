package com.docmall.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.CartVO;
import com.docmall.dto.CartListDTO;

public interface CartService {

	// 장바구니 담기
	int cart_add(CartVO vo);
	
	// 장바구니 리스트
	List<CartListDTO> cart_list(String mbsp_id);
	
	// 장바구니 수량 변경
	int cart_amount_change (Long cart_code, int cart_amount);
	
	// 장바구니 삭제
	int cart_delete(Long cart_code);
	
	// 장바구니 총금액
	int cart_tot_price(String mbsp_id);
	
	// 장바구니 비우기
	void cart_empty(String mbsp_id);
}











