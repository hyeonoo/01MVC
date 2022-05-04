
package com.model2.mvc.view.purchase;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ListPurchaseAction extends Action
{

    public ListPurchaseAction(){
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
    	System.out.println("ListPurchaseAction start");
       
    	SearchVO searchVO = new SearchVO();
    	HttpSession session = request.getSession();		
		UserVO user = (UserVO) session.getAttribute("user");
		String userId = user.getUserId();
		System.out.println("List userId : "+userId);
    	
        
    	int page = 1;
        
        if(request.getParameter("page") != null) 
        	page = Integer.parseInt(request.getParameter("page"));
       
        searchVO.setPage(page);
        searchVO.setSearchCondition(request.getParameter("searchCondition"));
        searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
       
        String pageUnit = getServletContext().getInitParameter("pageSize");
        searchVO.setPageUnit(Integer.parseInt(pageUnit));
       
        PurchaseService service = new PurchaseServiceImpl();
        HashMap<String,Object> map = service.getPurchaseList(searchVO, userId);
        
        request.setAttribute("map", map);
        request.setAttribute("searchVO", searchVO);
        request.setAttribute("userID", userId);
        
        
        System.out.println("ListPurchaseAction end");
        
        return "forward:/purchase/listPurchase.jsp";
    }
}
