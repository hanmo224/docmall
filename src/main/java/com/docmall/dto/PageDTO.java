package com.docmall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDTO {

	private int startPage;	// 시작페이지번호
	private int endPage; // 끝페이지번호
	
	// [이전] [다음] 표시여부
	private boolean prev, next;
	
	// 게시판테이블의 총 데이터 개수
	private int total;
	
	//페이징정보(pageNum, amount)와 검색필드(type, keyword)
	private Criteria cri;
	
	// total : 데이타 총 개수, pageNum : 페이지번호, amount : 출력 건수
	public PageDTO(int total, Criteria cri) {
		
		this.cri = cri;
		this.total = total;
		
		int pageSize = 10; // 블럭당 출력할 페이지 개수.  1		2	3	4	5	6	7	8	9	10	
		// endPage필드값을 구하기위한 용도.
		int endPageInfo = pageSize - 1;
		
		// 1	2	3	4	5	6	7	8	9	10	
		
		// pageNum = 1   this.endPage = (int) Math.ceil(1 / (double) 10) * 10; 
		// pageNum = 10   (int) Math.ceil(10 / (double) 10) * 10; 
		
		this.endPage =  (int) Math.ceil(cri.getPageNum() / (double) pageSize) * pageSize;  
		this.startPage = this.endPage - endPageInfo;
		
		// 게시판테이블의 실제데이타 개수에 따른 마지막 페이지수를 구해야 한다.
		int realEnd = (int) Math.ceil((total * 1.0) / cri.getAmount());   //   Math.ceil(13.0 / 10) = 2.0
		
		// 실제페이지 수 <= (논리)마지막페이지 수
		if(realEnd <= this.endPage) {
			this.endPage = realEnd; // 테이블 데이타개수에 따른 실제페이지수 
		}
		
		this.prev = this.startPage > 1; // [다음] 블럭에 표시할 데이타가 존재한다는 의미.
		this.next = this.endPage < realEnd; // 실제페이지수가 더크면 다음 블럭에 표시할 데이타가 존재하는 의미이므로
	}
}
