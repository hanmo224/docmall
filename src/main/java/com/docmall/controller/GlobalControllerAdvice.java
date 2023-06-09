package com.docmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.docmall.domain.CategoryVO;
import com.docmall.service.ProductService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

// com.docmall.controller 패키지안의 컨트롤러에서 설정된 매핑주소가 클라이언트로 부터 요청을 받으면
// 해당 컨트롤러의 메소드가 동작하기전에 이 클래스가 먼저 동작이 진행이 된다.
// 컨트롤러 기능을 가지고 있는 클래스들을 관리. 지금 작성하고 있는 매핑주소가 먼저 요청됨
@Log4j
@ControllerAdvice(basePackages = {"com.docmall.controller"})
public class GlobalControllerAdvice {

	@Setter(onMethod_ = {@Autowired})
	private ProductService productService;
	
	@ModelAttribute // 없으면 메소드가 호출이 안됨
	public void categoryList(Model model) {
		
		log.info("1차 카테고리 목록");
		
		List<CategoryVO> cateList  = productService.getCategoryList();
		model.addAttribute("cateList", cateList);
	}
}








































