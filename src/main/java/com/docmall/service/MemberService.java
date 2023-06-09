package com.docmall.service;

import com.docmall.domain.MemberVO;

public interface MemberService {

	// 아이디 중복 체크
	String idCheck(String mbsp_id);
	
	//회원가입
	void join(MemberVO vo);
	
	//로그인
	MemberVO login(String mbsp_id);
	
	//회원수정
	void modify(MemberVO vo);
	
	// 비밀번호 변경
	void pwchange(String mbsp_id, String new_mbsp_password);
	
	//현재 로그인한 시간 업데이트
	void now_visit(String mbsp_id);
	
	// 회원 탈퇴
	void delete(String mbsp_id);
}
