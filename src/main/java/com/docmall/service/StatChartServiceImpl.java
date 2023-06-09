package com.docmall.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.dto.StatChartDTO;
import com.docmall.mapper.StatChartMapper;

import lombok.Setter;

@Service
public class StatChartServiceImpl implements StatChartService{

	@Setter(onMethod_ = {@Autowired})
	private StatChartMapper statChartMapper;

	@Override
	public List<StatChartDTO> firstCategoryOrderPrice() {
		// TODO Auto-generated method stub
		return statChartMapper.firstCategoryOrderPrice();
	}

	@Override
	public JSONObject firstCategoryChart() {
		
		/*
		 google chart api 데이터 구조.
		 {"rows":
		 	[{"c":[{"v":"TOP"},
		 	{"v":1100000}]}],
		 	
		 "cols":
		 	 [											// JsonArray 객체
		 	  {"label":"1차 카테고리 이름","type":"string"},  // JsonObject 객체 
		 	  {"label":"주문금액","type":"number"}         // JsonObject 객체
		 	 ]
		 	}
		 	
		 JSONObject 클래스 : JSON데이터를 생성하는 기능
		 JSONObject obj = new JSONObject();
		 obj.put("TOP", "5000");
		 obj.put("Pants", "15000");
		 
		 결과 : {"TOP" : 5000, "Pants" : 15000}
		 
		 JSONArray 클래스 : JSON데이터를 배열로 생성하는 기능
		 */
		
		// DB에서 가져온 리턴 타입
		List<Map<String, Object>> list = statChartMapper.firstCategoryChart();
		
		JSONObject data = new JSONObject(); // JSON데이터 전체적인 관점에서 사용
		
		// 배열구조 [JSON Data]
		/*
		  [                                            // JsonArray 객체
		 	{"label":"1차 카테고리 이름","type":"string"},   // JsonObject 객체 
		 	{"label":"주문금액","type":"number"}		   // JsonObject 객체
		  ]
		 */
		JSONArray title = new JSONArray();
		
		// {"label":"1차 카테고리 이름","type":"string"}
		JSONObject col1 = new JSONObject(); // rows : [배열]
		col1.put("label", "1차 카테고리 이름");
		col1.put("type", "string");
		
		// {"label":"주문금액","type":"number"}
		JSONObject col2 = new JSONObject(); // cols : [배열]
		col2.put("label", "주문금액");
		col2.put("type", "number");
		
		title.add(col1);
		title.add(col2);
		
		/*
		 "cols":
		 	 [											// JsonArray 객체
		 	  {"label":"1차 카테고리 이름","type":"string"},  // JsonObject 객체 
		 	  {"label":"주문금액","type":"number"}         // JsonObject 객체
		 	 ]
		 	}
		 */
		
		data.put("cols", title); // cols 작업 완료
		
		/*
		 "rows":
		 	[{"c":[{"v":"TOP"}, {"v":1100000}]}]
		 */
		
		// List<Map<String, Object>> list
		JSONArray body = new JSONArray();
		for(Map<String, Object> map : list) {
			
			JSONArray row = new JSONArray();
			
			// for문 첫번쨰 {"v":"TOP"} 두번쨰 {"v" : "카테고리"}
			JSONObject categoryName = new JSONObject();
			categoryName.put("v", map.get("categoryname"));
			
			// for문 첫번쨰 {"v":1100000} 두번쨰 {"v" : 가격}
			JSONObject orderprice = new JSONObject();
			orderprice.put("v", map.get("orderprice"));
			
			// 배열로 추가
			/*
			 for문 첫번쨰 [{"v":"TOP"}, {"v":1100000}]
			 for문 두번쨰 [{"v":"카테고리"}, {"v":가격}]
			 */
			row.add(categoryName);
			row.add(orderprice);
			
			JSONObject cell = new JSONObject();
			cell.put("c", row);
			
			body.add(cell); 
		}
		
		data.put("rows", body); // rows 작업 완료
		
		return data;
	}
}











