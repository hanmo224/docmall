package com.docmall.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.dto.EmailDTO;
import com.docmall.service.EmailService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@RequestMapping("/email/*")
public class EmailController {

	@Setter(onMethod_ = {@Autowired})
	private EmailService emailService;
	
	//메일인증코드 요청주소.
	@GetMapping("/send") // 1)EmailDTO클래스의 기본생성자 호출, 사용자의 EMail주소는 receiverMail필드에 저장
	public ResponseEntity<String> send(EmailDTO dto, HttpSession session) {
		
		log.info("메일정보: " + dto);
		
		ResponseEntity<String> entity = null;
		
		//인증코드 생성. 6자리
		String authCode = "";
		for(int i=0; i<6; i++) {
			authCode += String.valueOf((int)(Math.random() * 10));
		}
		
		log.info("인증코드: " + authCode);
		
		// 사용자가 인증코드를 입력시 비교하기위하여, 발급해준 인증코드를 세션형태로 저장해두어야 한다.
		session.setAttribute("authCode", authCode);
		
		//인증코드 메일발송
		try {
			emailService.sendMail(dto, authCode);
			entity = new ResponseEntity<String>("success", HttpStatus.OK); // 200
		}catch(Exception ex) {
			ex.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
		}
	
		
		
		return entity;
	}
	
	// 인증코드 확인
	@GetMapping("/confirmAuthcode")
	public ResponseEntity<String> confirmAuthCode(String authCode, HttpSession session) {
		
		ResponseEntity<String> entity = null;
		
		String confirm_authCode = (String) session.getAttribute("authCode");
		
		if(authCode.equals(confirm_authCode)) {
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
			session.removeAttribute("authCode");
		}else {
			entity = new ResponseEntity<String>("fail", HttpStatus.OK);
		}
		
		return entity;
	}
	
	
}
