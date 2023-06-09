package com.docmall.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.dto.Criteria;
import com.docmall.dto.PageDTO;
import com.docmall.service.ProductService;
import com.docmall.util.FileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/product/*")
@Controller
public class ProductController {

	@Setter(onMethod_ = {@Autowired})
	private ProductService productService;
	
	@Resource(name = "uploadPath") // servlet-context.xml 참조.
	private String uploadPath;  // C:\\dev\\upload\\pds\\
	
	@ResponseBody // jackson-databind 라이브러리 필수
	@GetMapping("/subCategory/{cat_code}") // 주소의 값을 쓰고 싶으면 {}. product/subCategory/1 안쓰면 쿼리스트링 주소씀
	public ResponseEntity<List<CategoryVO>> subCategory(@PathVariable("cat_code") Integer cat_code) {
		
		ResponseEntity<List<CategoryVO>> entity = null;
		
		// 2차 카테고리를 보내줘야 하므로 success 안씀. 쓰이는 경우는 update, delete
		entity = new ResponseEntity<List<CategoryVO>>
			(productService.subCategoryList(cat_code), HttpStatus.OK);
		
		return entity;
	}
	
	@GetMapping("/pro_list/{cat_code}/{cat_name}")
	// 메소드를 void로 주면 jsp명이 되므로 String을 줌
	public String pro_list(@ModelAttribute("cri") Criteria cri, 
						   @PathVariable("cat_code") Integer cat_code, // 2차 카테고리 코드로 들어옴
						   @PathVariable("cat_name") String cat_name,
						   Model model) {
		
		// 카테고리 코드
		model.addAttribute("cat_code", cat_code);
		model.addAttribute("cat_name", cat_name);
		
		List<ProductVO> pro_list = productService.pro_list(cat_code, cri);
		
		pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		
		model.addAttribute("pro_list", pro_list);
		
		int count = productService.pro_count(cat_code, cri);
		PageDTO pageDTO = new PageDTO(count, cri);
		
		model.addAttribute("pageMaker", pageDTO);
		
		return "/product/pro_list";
	}
	
	// 이미지 보여주기
	@ResponseBody
	@GetMapping("/displayImage")
	public ResponseEntity<byte[]> displayFile(String folderName, String fileName) throws IOException {
		
		// uploadPath : 들어있는 경로 C:\\dev\\upload\\pds\\
		return FileUtils.getFile(uploadPath + folderName, fileName);
	}
	
	// 상품 상세 보기
	@GetMapping("/pro_detail")
	public void pro_detail(Integer pro_num, @ModelAttribute("cri") Criteria cri, Model model) {
		
		ProductVO vo = productService.pro_detail(pro_num);
		vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		
		model.addAttribute("productVO", vo);
	}
	
}


































