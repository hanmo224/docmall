package com.docmall.kakaopay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
//2) 결제 승인 요청시 넘어오는 응답데이터의 하위 결제 금액 정보(두번째 요청)
// ApproveRespnse의 필드 수준으로 들어간다.
public class Amount {

	private int total; // 전체 결제 금액
	private int tax_free; // 비과세 금액
	private int vat; // 부가세 금액
	private int point; // 사용한 포인트 금액
	private int discount; // 할인 금액
}
