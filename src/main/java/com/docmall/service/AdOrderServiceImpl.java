package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.AdOrderDetailVO;
import com.docmall.domain.OrderVO;
import com.docmall.dto.Criteria;
import com.docmall.mapper.AdOrderMapper;

import lombok.Setter;

@Service
public class AdOrderServiceImpl implements AdOrderService{

	@Setter(onMethod_ = {@Autowired})
	private AdOrderMapper adOrderMapper;

	@Override
	public List<OrderVO> orderList(Criteria cri, String sDate, String eDate) {
		// TODO Auto-generated method stub
		return adOrderMapper.orderList(cri, sDate, eDate);
	}

	@Override
	public int getTotalCount(Criteria cri, String sDate, String eDate) {
		// TODO Auto-generated method stub
		return adOrderMapper.getTotalCount(cri, sDate, eDate);
	}

	@Override
	public List<AdOrderDetailVO> orderDetail(Long ord_code) {
		// TODO Auto-generated method stub
		return adOrderMapper.orderDetail(ord_code);
	}

	@Override
	public void orderDetailProdcutDelete(Long ord_code, Integer pro_num) {
		// TODO Auto-generated method stub
		adOrderMapper.orderDetailProdcutDelete(ord_code, pro_num);
	}

}




















