package com.model2.mvc.service.purchase;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public interface PurchaseService {
	
	public void addPurchase(PurchaseVO purchase) throws Exception;
	
	public PurchaseVO getPurchase(int tranNo) throws Exception;
	
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String UserID) throws Exception;
	
	public HashMap<String, Object> getSaleList(SearchVO searchVO) throws Exception;
	
	public void updatePurchase(PurchaseVO purchase) throws Exception;
	
	public void updateTranCode(PurchaseVO purchase) throws Exception;

}
