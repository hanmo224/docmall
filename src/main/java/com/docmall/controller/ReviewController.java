package com.docmall.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.domain.MemberVO;
import com.docmall.domain.ReviewVO;
import com.docmall.dto.Criteria;
import com.docmall.dto.PageDTO;
import com.docmall.service.ReviewService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

// pro_detail.jsp에서 ReviewController를 사용함.

@Log4j
@RequestMapping("/review/*")
@RestController
public class ReviewController {

	@Setter(onMethod_ = {@Autowired})
	private ReviewService reviewService;
	
	// 리뷰
	// json 형식으로 쓰고 싶으면 responsebody 사용
	@GetMapping("/list/{pro_num}/{page}")
	public ResponseEntity<Map<String, Object>> // 상품후기 목록, 페이지
			review_list(@PathVariable("pro_num") int pro_num, @PathVariable("page") int page) {
		
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		Criteria cri = new Criteria();
		cri.setPageNum(page);
		
		//1)상품후기목록
		List<ReviewVO> list = reviewService.review_list(cri, pro_num);
		map.put("list", list);
		
		//2)페이징정보
		PageDTO pageMaker = new PageDTO(reviewService.review_count(pro_num), cri);
		map.put("pageMaker", pageMaker);
		
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		
		return entity;
		
	}
	
	// 상품 후기 작성(저장). 클라이언트에서 json으로 데이터가 전송되면 @RequestBody 사용되어, ReviewVO vo 변환된다.
	@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReviewVO vo, HttpSession session) {
		
		log.info("상품후기 :" + vo);
		
		ResponseEntity<String> entity = null;
		
		// 사용자 가져오기
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		
		// db연동 작업
		reviewService.create(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 상품후기 수정
	// restcontroller에선 수정 할땐 put, patch
	@PatchMapping(value = "/modify", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReviewVO vo) {
		
		ResponseEntity<String> entity = null;
		
		//db연동
		reviewService.modify(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 상품후기 삭제
	@DeleteMapping(value = "/delete/{rew_num}")
	public ResponseEntity<String> delete(@PathVariable("rew_num") Long rew_num) {
		
		ResponseEntity<String> entity = null;
		
		reviewService.delete(rew_num);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
}













