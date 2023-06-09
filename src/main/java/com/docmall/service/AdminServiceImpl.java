package com.docmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.AdminVO;
import com.docmall.mapper.AdminMapper;

import lombok.Setter;

@Service
public class AdminServiceImpl implements AdminService {

	@Setter(onMethod_ = {@Autowired}) // 1.8버전에서만 _ 사용
	private AdminMapper adminMapper;

	@Override
	public AdminVO admin_ok(String admin_id) {
		// TODO Auto-generated method stub
		return adminMapper.admin_ok(admin_id);
	}

	@Override
	public void now_visit(String admin_id) {
		// TODO Auto-generated method stub
		adminMapper.now_visit(admin_id);
	}
}
