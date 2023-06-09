package com.docmall.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.domain.MemberVO;
import com.docmall.dto.LoginDTO;
import com.docmall.service.MemberService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/member/*")
public class MemberController {

	@Setter(onMethod_ = {@Autowired})
	private MemberService memberService;
	
	//스프링시큐리티 암호화 클래스 객체 주입. spring-security.xml의 bcryptPasswordEncoder bean객체 주입
	@Setter(onMethod_ = {@Autowired})
	private PasswordEncoder passwordEncoder;
	
	// 아이디 중복체크(ajax사용)
	@ResponseBody
	@GetMapping("/idCheck")
	public ResponseEntity<String> idCheck(String mbsp_id) {
		
		log.info("아이디: " + mbsp_id);
		
		ResponseEntity<String> entity = null;
		
		//idcheck 작업
		String isUse = "";
		if(memberService.idCheck(mbsp_id) != null) {
			isUse = "no";
		}else {
			isUse = "yes";
		}
		
		entity = new ResponseEntity<String>(isUse, HttpStatus.OK);
		
		return entity;
	}
	
	// 회원 가입 폼
	@GetMapping("/join")
	public void join() {
		log.info("회원가입 폼");
	}
	
	// 회원 저장
	@PostMapping("/join")
	public String join(MemberVO vo) {
		
		log.info("회원정보: " + vo); // 암호화 되지 않은 상태
		
		// 평문 텍스트인 비밀번호를 스프링시큐리티 암호화클래스를 이용하여, 암호화시킴.
		vo.setMbsp_password(passwordEncoder.encode(vo.getMbsp_password()));
		
		log.info("회원정보: " + vo); // 암호화 된 상태
		
		memberService.join(vo);
		
		return "redirect:/member/login";
	}
	
	// 로그인 폼
	@GetMapping("/login")
	public void login() {
		log.info("로그인 폼");
	}
	
	// 로그인 주소
	@PostMapping("/login")
	public String login(LoginDTO dto, HttpSession session, RedirectAttributes rttr) {
		
		log.info("로그인 정보: " + dto);
		
		MemberVO vo = memberService.login(dto.getMbsp_id());
		
		String url = "";
		String msg = "";
		
		// 로그인 검사. 아이디만 제공
		if(vo != null) {
			// 사용자 입력 비번, 암호화된 비번
			if(passwordEncoder.matches(dto.getMbsp_password(), vo.getMbsp_password())) {
				session.setAttribute("loginStatus", vo); // 로그인한 사용자의 회원정보가 세션형태로 저장
				
				//로그인 시간 업데이트
				memberService.now_visit(dto.getMbsp_id());
				
				// 강제로 로그인 되기 이전 매핑주소와 정보 존재 유무 작업
				String targetUrl = (String) session.getAttribute("dest");
				
				// 로그인이 만료되고 나서 재로그인을 하면 전 매핑주소로 간다.
				url = (targetUrl != null) ? targetUrl : "/";
				
				if(targetUrl != null) {
					session.removeAttribute("dest");
				}
			
			}else {
				//  비번이 틀린경우
				url = "/member/login";
				msg = "failPW";
			}
		}else {
			// 아이디가 틀린경우
			url = "/member/login";
			msg = "failID";
		}
		
		rttr.addFlashAttribute("msg", msg); // jsp에서 msg 이름을 사용가능
		
		return "redirect:" + url;
	}
	
	// 로그아웃 기능
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 사용자 세션관련정보가 모두 소멸.
		
		return "redirect:/";
	}
	
	// 수정 폼
	@GetMapping(value = {"/modify", "/mypage"})
	public void modify(Model model, HttpSession session) {
		
		//세션객체로부터 사용자아이디 확보.
		// Object형태로 저장된 것을 다시 MemberVO형태로 받아옴
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		
		// 로그인에서 사용한 것을 재사용 한 것
		model.addAttribute("memberVO", memberService.login(mbsp_id));
		
	}
	
	// 수정 정보 전송
	@PostMapping("/modify")
	public String modify(MemberVO vo, HttpSession session, RedirectAttributes rttr) {
		
		String url = "";
		String msg = "";
		
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
				
		// 암호화된 비밀번호를 db에서 읽어옴.
		String db_mbsp_password = ((MemberVO) memberService.login(mbsp_id)).getMbsp_password();
		
		if(passwordEncoder.matches(vo.getMbsp_password(), db_mbsp_password)) {
			// 회원정보수정작업
			memberService.modify(vo);
			
			msg = "modify";
			url = "/";
		}else {
			msg = "failPW";
			url = "/member/modify";
		}
			
		rttr.addFlashAttribute("msg", msg);
		
		return "redirect:" + url;
	}
	
	// 비밀번호 변경
	@ResponseBody
	@PostMapping("/pwchange")
	public ResponseEntity<String> pwchange(String old_mbsp_password, String new_mbsp_password, HttpSession session) {
		
		ResponseEntity<String> entity = null;
		String body = "";
		
		//기존 비번확인및 신규비번 변경작업
		log.info("기존비번: " + old_mbsp_password); // 1234
		log.info("신규비번: " + new_mbsp_password); // 4321
		
		// mbsp_id 뽑아오기
		MemberVO vo = (MemberVO) session.getAttribute("loginStatus");
		
		// 세션에 존재하고 있는 암호화된 비밀번호.  비번 1234 의 암호화
		//String db_mbsp_password = vo.getMbsp_password();
		
		// mbsp_id 뽑아오기
		String mbsp_id = vo.getMbsp_id();
		
		// 암호화된 비밀번호를 db에서 읽어옴.
		String db_mbsp_password = ((MemberVO) memberService.login(mbsp_id)).getMbsp_password();
			
		if(passwordEncoder.matches(old_mbsp_password, db_mbsp_password)) {
			//신규비밀번호를 암호화처리.  pwchange()메서드
			String enc_mbsp_password = passwordEncoder.encode(new_mbsp_password);
			memberService.pwchange(mbsp_id, enc_mbsp_password);
			
			body = "success";
		}else {
			
			body = "failPW";
		}
		
		entity = new ResponseEntity<String>(body, HttpStatus.OK);
		
		return entity;
	}
	
	// 회원 탈퇴
	@ResponseBody
	@PostMapping("/delete")
	public ResponseEntity<String> delete(String mbsp_password, HttpSession session) {
		ResponseEntity<String> entity = null;
		String body = "";
		
		// 사용자 아이디 가져오기
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();

		// 암호화된 비밀번호를 db에서 읽어옴.
		String db_mbsp_password = ((MemberVO) memberService.login(mbsp_id)).getMbsp_password();
			
		if(passwordEncoder.matches(mbsp_password, db_mbsp_password)) {
			// 회원탈퇴작업
			memberService.delete(mbsp_id);
			session.invalidate(); // 세션 초기화
			
			body = "success";
		}else {
			
			body = "failPW";
		}
		
		entity = new ResponseEntity<String>(body, HttpStatus.OK);
		
		
		return entity;
	}
}
