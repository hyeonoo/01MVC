package com.model2.mvc.service.purchase.impl;


import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import java.util.HashMap;


public class PurchaseServiceImpl implements PurchaseService{

	private ProductDAO productDAO;
    private PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl(){
        System.out.println("ImplPurchaseServiceImpl start");
        purchaseDAO = new PurchaseDAO();
        System.out.println("ImplPurchaseServiceImpl end");
    }

    public void addPurcase(PurchaseDAO purchaseDao) throws Exception{
       
    }

    public purchaseVO getPurchase(int i) throws Exception{
        
    }

    public HashMap getPurchaseList(SearchVO searchVO) throws Exception{
        System.out.println("ImplHashGetPurchaseList start");
        return purchaseDAO.getPurchaseList(searchVO);
    }

    public void updatePurchase(PurchaseVO purchasevo) throws Exception{
        
    }

    public void updateTranCode(PurchaseVO purchasevo) throws Exception {
        
    }	
    public void addPurchase(PurchaseVO purchasevo) throws Exception{
        
    }
    
    public HashMap getSaleList(PurchaseVO purchasevo) throws Exception{
       
    }

    public void updateTranCode(PurchaseVO purchasevo) throws Exception{
        
    }
    public void updatePurchase(PurchaseVO purchasevo) throws Exception{
        
    }

    public volatile PurchaseVO getPurchase(int i) throws Exception{
        return (PurchaseVO)getPurchase(i);
    }

 
}
