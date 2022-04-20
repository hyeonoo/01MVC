package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();		
		UserVO userId = (UserVO) session.getAttribute("user");
		System.out.println("userId : "+userId);

		int prodNo=Integer.parseInt(request.getParameter("prodNo"));		
		ProductService prodservice = new ProductServiceImpl();
		ProductVO product = prodservice.getProduct(prodNo);
		System.out.println("prodNo : "+prodNo);

		PurchaseVO purchase=new PurchaseVO();
		purchase.setPurchaseProd(product);
		purchase.setBuyer(userId);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDlvyAddr(request.getParameter("dlvyAddr"));
		purchase.setDlvyRequest(request.getParameter("dlvyRequest"));
		purchase.setDlvyDate(request.getParameter("dlvyDate"));

		
		System.out.println("AddPurchaseAction purchase : "+purchase);
		
		PurchaseService service=new PurchaseServiceImpl();
		service.addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchase.jsp"; 
	}
}
