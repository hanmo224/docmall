package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.dto.Criteria;
import com.docmall.mapper.AdProductMapper;

import lombok.Setter;

@Service
public class AdProductServiceImpl implements AdProductService {

	@Setter(onMethod_ = {@Autowired})
	private AdProductMapper adProductMapper;

	@Override
	public List<CategoryVO> getCategoryList() {
		// TODO Auto-generated method stub
		return adProductMapper.getCategoryList();
	}

	@Override
	public List<CategoryVO> subCategoryList(Integer cat_code) {
		// TODO Auto-generated method stub
		return adProductMapper.subCategoryList(cat_code);
	}

	@Override
	public void pro_insert(ProductVO vo) {
		// TODO Auto-generated method stub
		adProductMapper.pro_insert(vo);
	}

	@Override
	public List<ProductVO> getListWithPaging(Criteria cri, Integer cat_code) {
		// TODO Auto-generated method stub
		return adProductMapper.getListWithPaging(cri, cat_code);
	}

	@Override
	public int getTotalCount(Criteria cri, Integer cat_code) {
		// TODO Auto-generated method stub
		return adProductMapper.getTotalCount(cri, cat_code);
	}

	@Override
	public ProductVO modify(Integer pro_num) {
		// TODO Auto-generated method stub
		return adProductMapper.modify(pro_num);
	}

	@Override
	public CategoryVO get(int cat_code) {
		// TODO Auto-generated method stub
		return adProductMapper.get(cat_code);
	}

	@Override
	public void update(ProductVO vo) {
		// TODO Auto-generated method stub
		adProductMapper.update(vo);
	}

	@Override
	public void delete(Integer pro_num) {
		// TODO Auto-generated method stub
		adProductMapper.delete(pro_num);
	}
	
	@Override
	public void pro_checked_modify(List<ProductVO> pro_list) {
		// TODO Auto-generated method stub
		adProductMapper.pro_checked_modify(pro_list);
	}
	
//	@Override
//	public void pro_checked_modify2(Integer pro_num, int pro_price, String pro_buy) {
//		// TODO Auto-generated method stub
//		adProductMapper.pro_checked_modify2(pro_num, pro_price, pro_buy);
//	}
	
	@Override
	public void pro_checked_delete(List<Integer> pro_num_arr) {
		// TODO Auto-generated method stub
		adProductMapper.pro_checked_delete(pro_num_arr);
	}

}


















