package com.docmall.dto;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {

	private int pageNum;	// 선택한 페이지번호  1	2	3	4	5 
	private int amount;		// 페이지마다 출력할 게시물 개수.. 예)5개, 10, 15, 20 
	
	
	// 검색기능에 사용할 필드.  목록에서 검색기능을 사용하지 않을 경우 type, keyword필드는 null 이 됨.
	private String type;	// 검색종류(6개): 제목(T), 내용(C), 작성자(W), 제목+내용(TC), 제목+작성자(TW), 제목+내용+작성자(TCW)
	private String keyword;  // 검색어
	
	// 처음 이주소를 요청시 /board/list 아래 생성자가 호출된다. 
	public Criteria() {
		this(1, 10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type == null? new String[] {} : type.split("");  // "T", "C"
	}
	
	public String getListLink() {
		
		// UriComponentsBuilder : Uri를 생성하는 기능을 제공.
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.getAmount())
				.queryParam("type", this.getType())
				.queryParam("keyword", this.getKeyword());
		
		return builder.toUriString();  // ?pageNum=3&amount=10&type=T&keyword=검색어
	}
}
