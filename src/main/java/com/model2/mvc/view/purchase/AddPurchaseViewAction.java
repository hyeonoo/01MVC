
package com.model2.mvc.view.purchase;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddPurchaseViewAction extends Action
{

    public AddPurchaseViewAction() {
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	
    	int prodNo = Integer.parseInt(request.getParameter("prod_no"));
        System.out.println(prodNo);
        ProductService service = new ProductServiceImpl();
        ProductVO product = service.getProduct(prodNo);
        request.setAttribute("product", product);
        return "forward:/purchase/addPurchaseView.jsp";
    }
}
