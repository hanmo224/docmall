package com.docmall.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductVO {

	// pro_num, cat_code, pro_name, pro_price, pro_discount, pro_publisher, pro_content, pro_up_folder, pro_img, pro_amount, pro_buy, pro_date, pro_updatedate
	
	private Integer	pro_num;
	private int 	cat_code;
	private String 	pro_name;
	private int		pro_price;
	private int		pro_discount;
	private	String	pro_publisher;
	private String	pro_content;
	private	String	pro_up_folder;
	private String	pro_img; // 상품이미지 파일명을 저장하기 위한 목적
	private	int		pro_amount;
	private String	pro_buy;
	private Date	pro_date;
	private Date	pro_updatedate;
	
	//파일 업로드 필드(상품이미지)
	// <input type="file" class="form-control" id="uploadFile" name="uploadFile">
	private MultipartFile uploadFile;
}
