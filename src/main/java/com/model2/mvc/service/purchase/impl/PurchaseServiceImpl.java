package com.model2.mvc.service.purchase.impl;


import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

import java.util.HashMap;


public class PurchaseServiceImpl implements PurchaseService{

	
	private PurchaseDAO purchaseDAO;
	private ProductDAO productDAO;
	
	
	public PurchaseServiceImpl(){
        System.out.println("ImplPurchaseServiceImpl start");
        purchaseDAO = new PurchaseDAO();
        productDAO=new ProductDAO();
        System.out.println("ImplPurchaseServiceImpl end");
    }

    public void addPurchase(PurchaseVO purchase) throws Exception{
       System.out.println("ImplAddPurchase start");
       
       purchaseDAO.insertPurchase(purchase);
       
       System.out.println("ImplAddPurchase end");
       
       
    }
    
    public HashMap<String,Object> getPurchaseList(SearchVO searchVO) throws Exception {
		
    	System.out.println("ImplHashGetPurchaseList start");
    	
    	return purchaseDAO.getPurchaseList(searchVO);
	}


	
	public PurchaseVO getPurchase(int tranNo) throws Exception{
		
		System.out.println("ImplGetPurchase start");
		
		return purchaseDAO.findPurchase(tranNo);
	}
	
		
	
	
	
	public HashMap<String, Object> getSaleList(SearchVO searchVO) throws Exception{
		
		System.out.println("getSaleList start");
		
		return purchaseDAO.getPurchaseList(searchVO);
	}
	
	public void updatePurchase(PurchaseVO purchase) throws Exception{
		
		System.out.println("ImplUpdatePurchase start");
		
		purchaseDAO.updatePurchase(purchase);
		 
		System.out.println("ImplUpdatePurchase end");
	}
	
	public void updateTranCode(PurchaseVO purchase) throws Exception;

 
}
