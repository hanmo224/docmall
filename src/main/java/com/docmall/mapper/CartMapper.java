package com.docmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.CartVO;
import com.docmall.dto.CartListDTO;

public interface CartMapper {

	// 장바구니 담기
	int cart_add(CartVO vo); // 인서트, 업데이트, 딜리트는 int 사용가능 void만 줘도 상관은 없음
	
	// 장바구니 리스트
	List<CartListDTO> cart_list(String mbsp_id);
	
	// 장바구니 수량 변경
	int cart_amount_change(@Param("cart_code") Long cart_code, @Param("cart_amount") int cart_amount);
	
	// 장바구니 삭제
	int cart_delete(Long cart_code);
	
	// 장바구니 총금액
	int cart_tot_price(String mbsp_id);
	
	// 장바구니 비우기
	void cart_empty(String mbsp_id);
}












