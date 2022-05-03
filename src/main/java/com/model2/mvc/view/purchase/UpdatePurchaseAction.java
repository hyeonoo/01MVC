package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdatePurchaseAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		HttpSession session = request.getSession();
		UserVO user = (UserVO)session.getAttribute("user");
		
		System.out.println(user.getUserId());
		
		PurchaseVO purchase = new PurchaseVO();
		purchase.setTranNo(tranNo);
		purchase.setBuyer(user);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDlvyAddr(request.getParameter("receiverAddr"));
		purchase.setDlvyRequest(request.getParameter("receiverRequest"));
		purchase.setDlvyDate(request.getParameter("divyDate"));
		purchase.setTranNo(tranNo);
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updatePurchase(purchase);

		return "redirect:/getPurchase.do?tranNo=" +tranNo;
	}

}
