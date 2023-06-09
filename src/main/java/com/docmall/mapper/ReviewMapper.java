package com.docmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.ReviewVO;
import com.docmall.dto.Criteria;

public interface ReviewMapper {
	
	// 리뷰 목록 및 페이징
	List<ReviewVO> review_list(@Param("cri") Criteria cri, @Param("pro_num") int pro_num);
	
	// 상품 데이터 개수
	int review_count(int pro_num);
	
	// 상품 후기 작성(저장)
	void create(ReviewVO vo);
	
	// 상품 후기 수정
	void modify(ReviewVO vo);
	
	// 상품 후기 삭제
	void delete(Long rew_num);
}





















