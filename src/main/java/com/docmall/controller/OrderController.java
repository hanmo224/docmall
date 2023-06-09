package com.docmall.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.docmall.domain.MemberVO;
import com.docmall.domain.OrderVO;
import com.docmall.domain.PaymentVO;
import com.docmall.dto.CartListDTO;
import com.docmall.kakaopay.ApproveResponse;
import com.docmall.kakaopay.ReadyResponse;
import com.docmall.service.CartService;
import com.docmall.service.KakaoPayService;
import com.docmall.service.MemberService;
import com.docmall.service.OrderService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/order/*")
public class OrderController {
	
	@Setter(onMethod_ = {@Autowired})
	private OrderService orderService;
	
	// 장바구니 주입작업
	@Setter (onMethod_ = {@Autowired})
	private CartService cartService;
	
	// 회원 정보를 가져오기 위해 주입작업
	@Setter(onMethod_ = {@Autowired})
	private MemberService memberService;
	
	// 카카오페이 주입 작업
	@Setter(onMethod_ = {@Autowired})
	private KakaoPayService kakaoPayService;
	
	// 가맹점 코드 참조
	@Resource(name = "cid")
	private String cid;
	
	// 바로구매(주문정보 입력폼. 장바구니에 담김)
	@GetMapping("/order_info")
	public void order_info(HttpSession session, Model model) {
		
		// 사용자 아이디 확보
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		
		// 장바구니 목록(상품)
		List<CartListDTO> cart_list = cartService.cart_list(mbsp_id);
				
		cart_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		
		// 주문 상품명
		String order_productName = cart_list.get(0).getPro_name() + "외 " + (cart_list.size() - 1) + "건 ";
		model.addAttribute("order_productName", order_productName);
		
		// 주문 목록
		model.addAttribute("cart_list", cart_list);
		
		// 주문 총금액
		model.addAttribute("cart_tot_price", cartService.cart_tot_price(mbsp_id));
		
		// 주문자 정보 -> membercontroller에서 주입을 받아옴
		model.addAttribute("memberVO", memberService.login(mbsp_id));
	}
	
	// 카카오페이 결제 준비 요청 : 
	@ResponseBody // -> ajax 호출을 위한 성격
	@GetMapping("/orderbuy")
	// 가지고 있던 정보가 클라이언트로 넘어간다.
	public ReadyResponse orderkakaoPay(String pay_type, String order_productName, OrderVO o_vo, PaymentVO p_vo, HttpSession session) {
		
		ReadyResponse readyResponse = new ReadyResponse();
		
		// 사용자 아이디 받기
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		
		o_vo.setMbsp_id(mbsp_id); // 아이디 저장.
		p_vo.setMbsp_id(mbsp_id); // 아이디 저장.
		
		log.info("결제방식: " + pay_type);
		log.info("무통장주문정보: " + o_vo);
		log.info("무통장결제정보: " + p_vo);
		
		// 주문정보, 주문상세정보, 결제정보, 장바구니 비우기
		if(pay_type.equals("bank")) {
			
			orderService.order_save(o_vo, p_vo); // 무통장 처리
		}
		
		// 카카오페이 결제 작업
		if(pay_type.equals("kakaopay")) {
			
			p_vo.setPay_user(p_vo.getMbsp_id());
			p_vo.setPay_bank("kakaopay");
			
			log.info("카카오페이 결제 정보: " + p_vo);
			
			orderService.order_save(o_vo, p_vo); // 카카오페이 결제처리
			
			// 결제 준비 요청 성공시 사용 주소
			String approval_url = "http://localhost:8081/order/orderApproval";
			
			// 결제 준비 요청 실패시 사용 주소
			String fail_url = "http://localhost:8081/order/orderCancel";
			
			// 결제 실패시 사용 주소
			String cancel_url = "http://localhost:8081/order/orderFail";
			
			// 결제 준비 요청 작업 및 응답 데이터
			readyResponse = kakaoPayService.payReady(cid, o_vo.getOrd_code(), mbsp_id, order_productName, 1, o_vo.getOrd_price(), 0, approval_url, fail_url, cancel_url);
			
			log.info("결제 준비 요청: " + readyResponse);
			log.info("결제 고유 번호(tid): " + readyResponse.getTid());
			log.info("결제  요청 URL(QR코드): " + readyResponse.getNext_redirect_pc_url());
			
			session.setAttribute("tid", readyResponse.getTid());
			session.setAttribute("ord_code", o_vo.getOrd_code());
		} 
		
		return readyResponse;
	}
	
	// 결제 승인 요청. 카카오페이 API서버에서 다음 주소가 호출이 됨
	@GetMapping("/orderApproval")
	public String orderApproval(String pg_token, HttpSession session) {
		
		String tid = ((String) session.getAttribute("tid"));
		session.removeAttribute("tid");
		
		Long ord_code = (Long) session.getAttribute("ord_cdoe");
		session.removeAttribute("ord_code");
		
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		
		// 결제 승인 요청 작업. 응답 데이터는 사용하지 않음
		ApproveResponse approveResponse = kakaoPayService.payApprove(cid, tid, ord_code, mbsp_id, pg_token);
		
		return "redirect:/order/orderComplete"; // 결제 성공 결과 페이지 주소
	}
	
	// 카카오 페이 결제 완료
	@GetMapping("/orderComplete")
	public String orderComplete() {
		
		return "/order/order_Complete";
	}
	
	// 카카오 페이 결제 취소
	@GetMapping("/orderCancel")
	public void orderCancel() {
		
	}
	
	// 카카오 페이 결제 실패 
	@GetMapping("/orderFail")
	public void orderFail() {
		
	}
}





















