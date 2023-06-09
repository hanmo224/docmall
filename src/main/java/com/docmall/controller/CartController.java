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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.docmall.domain.CartVO;
import com.docmall.domain.MemberVO;
import com.docmall.dto.CartListDTO;
import com.docmall.service.CartService;
import com.docmall.util.FileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/cart/*")
public class CartController {

	@Setter (onMethod_ = {@Autowired})
	private CartService cartService;
	
	@Resource(name = "uploadPath") // servlet-context.xml 참조.
	private String uploadPath;  // C:\\dev\\upload\\pds\\
	
	// 장바구니 담기
	@ResponseBody
	@PostMapping("/cart_add")
	public ResponseEntity<String> cart_add(CartVO vo, HttpSession session) {
		
		ResponseEntity<String> entity = null;
		
		// 사용자 아이디 확보
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		
		if(cartService.cart_add(vo) == 1) {
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}else {
			entity = new ResponseEntity<String>("fail", HttpStatus.OK);
		}
		
		return entity;
	}
	
	// 바로구매시 장바구니 담기작업
	@GetMapping("/direct_cart_add")
	public String direct_cart_add(CartVO vo, HttpSession session) {
		
		// 사용자 아이디 확보
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		
		cartService.cart_add(vo);
		
		return "redirect:/order/order_info";
	}
	
	// 장바구니 리스트. 페이징 기능을 추가 하고 싶다면 @ModelAttribute와 쿼리 수정하기
	@GetMapping("/cart_list")
	public void cart_list(HttpSession session, Model model) {
		
		// 사용자 아이디 확보
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		
		List<CartListDTO> cart_list = cartService.cart_list(mbsp_id);
		
		cart_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		
		model.addAttribute("cart_list", cart_list);
		
		// 장바구니 총금액
		if(cart_list.size() != 0) { // 리스트가 int이기 때문에 null이 들어가면 오류가 나타나므로 조건 추가
			model.addAttribute("cart_tot_price", cartService.cart_tot_price(mbsp_id));
		}
	}
	
	// 이미지 보여주기
	@ResponseBody
	@GetMapping("/displayImage")
	public ResponseEntity<byte[]> displayFile(String folderName, String fileName) throws IOException {
			
		// uploadPath : 들어있는 경로 C:\\dev\\upload\\pds\\
		return FileUtils.getFile(uploadPath + folderName, fileName);
	}
	
	// 장바구니 수량 변경
	@ResponseBody
	// @PostMapping("/cart_amount_change")
	@GetMapping("/cart_amount_change")
	public ResponseEntity<String> cart_amount_change(Long cart_code, int cart_amount) {
		
		ResponseEntity<String> entity = null;
		
		if(cartService.cart_amount_change(cart_code, cart_amount) == 1) {
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} else {
			entity = new ResponseEntity<String>("fail", HttpStatus.OK);
		}
		
		return entity;
	}
	
	// 장바구니 삭제
	@ResponseBody
	@PostMapping("/cart_delete")
	public ResponseEntity<String> cart_delete(Long cart_code) {
		
		ResponseEntity<String> entity = null;
		
		if(cartService.cart_delete(cart_code) == 1) {
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} else {
			entity = new ResponseEntity<String>("fail", HttpStatus.OK);
		}
		
		return entity;
	}
	
	// 장바구니 비우기
	@GetMapping("/cart_empty")
	public String cart_empty(HttpSession session) {
		
		// 사용자 아이디 확보
	    String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		
	    cartService.cart_empty(mbsp_id);
	    
		return "redirect:/cart/cart_list";
	}
}

















