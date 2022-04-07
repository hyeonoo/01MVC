
package com.model2.mvc.view.purchase;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import java.io.PrintStream;
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
        com.model2.mvc.service.product.vo.ProductVO productVO = service.getProduct(prodNo);
        request.setAttribute("productVO", productVO);
        return "forward:/purchase/addPurchaseView.jsp";
    }
}
