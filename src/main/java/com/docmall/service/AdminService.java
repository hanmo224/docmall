package com.docmall.service;

import com.docmall.domain.AdminVO;
import com.docmall.domain.ProductVO;

public interface AdminService {

	// 관리자 로그인
	AdminVO admin_ok(String admin_id);
	
	// 관리자 접속시간 업데이트
	void now_visit(String admin_id);
	
}
