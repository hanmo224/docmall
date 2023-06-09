package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.CartVO;
import com.docmall.dto.CartListDTO;
import com.docmall.mapper.CartMapper;

import lombok.Setter;

@Service
public class CartServiceImpl implements CartService{

	@Setter(onMethod_ = {@Autowired})
	private CartMapper cartMapper;

	@Override
	public int cart_add(CartVO vo) {
		// TODO Auto-generated method stub
		return cartMapper.cart_add(vo);
	}

	@Override
	public List<CartListDTO> cart_list(String mbsp_id) {
		// TODO Auto-generated method stub
		return cartMapper.cart_list(mbsp_id);
	}

	@Override
	public int cart_amount_change(Long cart_code, int cart_amount) {
		// TODO Auto-generated method stub
		return cartMapper.cart_amount_change(cart_code, cart_amount);
	}

	@Override
	public int cart_delete(Long cart_code) {
		// TODO Auto-generated method stub
		return cartMapper.cart_delete(cart_code);
	}

	@Override
	public int cart_tot_price(String mbsp_id) {
		// TODO Auto-generated method stub
		return cartMapper.cart_tot_price(mbsp_id);
	}

	@Override
	public void cart_empty(String mbsp_id) {
		// TODO Auto-generated method stub
		cartMapper.cart_empty(mbsp_id);
	}
	
}














