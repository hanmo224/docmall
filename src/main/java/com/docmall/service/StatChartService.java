package com.docmall.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.docmall.dto.StatChartDTO;

public interface StatChartService {

	public List<StatChartDTO> firstCategoryOrderPrice();
	
	public JSONObject firstCategoryChart();
}










