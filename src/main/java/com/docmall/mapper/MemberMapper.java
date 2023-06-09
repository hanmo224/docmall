package com.docmall.mapper;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.MemberVO;

public interface MemberMapper {

	//아이디 중복체크
	String idCheck(String mbsp_id);
	
	//회원가입
	void join(MemberVO vo);
	
	// 로그인, 회원수정폼(재사용)
	MemberVO login(String mbsp_id); // 비밀번호는 암호화 되었기 때문에 아이디로 비교
	
	// 회원정보  수정. modify, insert, delete는 void로 함
	void modify(MemberVO vo);
	
	//비밀번호 변경
	void pwchange(@Param("mbsp_id") String mbsp_id, @Param("new_mbsp_password") String new_mbsp_password);
	
	//현재 로그인한 시간 업데이트
	void now_visit(String mbsp_id);
	
	//회원탈퇴. 비번이 암호화 되어있서 id를 씀
	void delete(String mbsp_id);
}
