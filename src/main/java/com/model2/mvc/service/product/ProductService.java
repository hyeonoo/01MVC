package com.model2.mvc.service.product;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.vo.ProductVO;

public interface ProductService {

	public void addProduct(ProductVO product) throws Exception;
	
	public ProductVO getProduct(int prodNo) throws Exception;
	
	
	public HashMap<String, Object> getProductList(SearchVO search) throws Exception;
	
	public void updateProduct(ProductVO product) throws Exception;
	
	
}

