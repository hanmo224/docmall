package com.docmall.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.docmall.service.StatChartService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/admin/chart/*")
@Controller
public class StatChartController {

	@Setter(onMethod_ = {@Autowired})
	private StatChartService statChartService;
	
	// 2차원 배열 데이터
	@GetMapping("/overall")
	public void overall(Model model) { // 모델을 사용한다는 것은 jsp에 모델 이름으로 전달 하고 자 할려고 함이다.
		
		/*
		List<StatChartDTO> firstCategoryOrderPrice = stateChartService.firstCategoryOrderPrice();
		
		String firstCategoryData = "[";
		
		firstCategoryData += "['1차 카테고리별', '주문금액'], ";
		
		for(StatChartDTO dto : firstCategoryOrderPrice) {
			// ['Bob', 35000]
			firstCategoryData += "['" + dto.getCategoryname() + "', " + dto.getOrderprice() + "]";
			firstCategoryData += ",";
		}
		
		// ,의 위치를 뒤에서부터 찾아서 없애준 값이 들어가므로 +를 사용 안한다.(사용하면 중복값이 출력)
		firstCategoryData = firstCategoryData.substring(0, firstCategoryData.lastIndexOf(","));
		firstCategoryData += "]";
		
		log.info("차트데이터: " + firstCategoryData);
		
		model.addAttribute("firstCategoryData", firstCategoryData);
		*/
	}
	
	// JSON데이터
	@ResponseBody
	@GetMapping("/firstCategoryOrderPrice")
	public ResponseEntity<JSONObject> firstCategoryOrderPrice() {
		
		ResponseEntity<JSONObject> entity = null;
		
		entity = new ResponseEntity<JSONObject>(statChartService.firstCategoryChart(), HttpStatus.OK);
		
		return entity;
	}
}






















