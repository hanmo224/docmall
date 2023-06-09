package com.docmall.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

// 컨트롤러에서 예외가 발생이되면, 개별컨트롤러에서 예외처리를 하는 것이 아니라 공통예외처리를 하는 클래스
// 개별 컨트롤러에서 예외관련 작업을 한 것은 공통예외처리에서 동작되지 않는다.
// 공토예외처리가 적용될려면, servlet-context.xml에서 설정을 해야 한다. bean등록작업.

@ControllerAdvice
public class CommonExceptionAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
	
	// 모든 예외처리를 담당하는 메서드
	// 공통예외 테스트1   http://localhost:8080/sample2/doA?name=홍길동&age=홍길동
	// 이유?age 파라미터에 숫자값만 사용해야한다. 그러나 홍길동 문자열을 사용하여 예외발생이 되었다.
	// 공통예외 테스트2  http://localhost:8080/sample5/error
	// 이유? 몫을 0으로 나눴을 때 예외발생이 되었다.
	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		
		logger.error("Exception...." + ex.getMessage());
		model.addAttribute("exception", ex);
		logger.error(model.toString());
		
		return "/error/error_page";
	}
	
	// web.xml에서 설정이 필요하다.(중요)
	// 예외종류는 다양하다.
	// 특정 예외가 발생이 되면 예외처리를 담당하는 메서드 - 예>잘못된 url을 호출할 경우.
	// 테스트시 컨트롤러가 타지 않아야 한다.
	// 특정예외 테스트  http://localhost:8080/ex10000  
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException ex) {
		
		return "/error/custom404";
	}
}
