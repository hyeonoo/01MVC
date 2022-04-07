package com.model2.mvc.service.product.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;


public class ProductServiceImpl implements ProductService{
	
	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
		System.out.println("ImplProductServiceDAO start");
		
		productDAO = new ProductDAO();
		
		System.out.println("ImplProductServiceDAO end");
	}
	
	public void addProduct(ProductVO product) throws Exception{
		System.out.println("ImplAddProduct start");
		
		productDAO.insertProduct(product);
		
		System.out.println("ImplAddProduct and");
	}
	
	public ProductVO getProduct(int prodNo) throws Exception{
		System.out.println("ImplVOGetProduct start");
		
		return productDAO.findProduct(prodNo);
		
		
	}
	
	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception{
		System.out.println("ImplHashGetProductList start");
		
		return productDAO.getProductList(searchVO);
	}
	
	public void updateProduct(ProductVO product) throws Exception{
		System.out.println("ImplIpdateProduct start");
		
		productDAO.updateProduct(product);
		
		System.out.println("ImplIpdateProduct end");
	}

	
	

}
