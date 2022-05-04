package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO{

    public PurchaseDAO(){
    }

    public void insertPurchase(PurchaseVO purchase) throws Exception {
       
    	System.out.println("insertPurchaseDAO start");
        
    	Connection con = DBUtil.getConnection();
       
    	String sql = "INSERT INTO transaction VALUES (seq_product_prod_no.nextval,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
        stmt.setString(2, purchase.getBuyer().getUserId());
        stmt.setString(3, purchase.getPaymentOption());
        stmt.setString(4, purchase.getReceiverName());
        stmt.setString(5, purchase.getReceiverPhone());
        stmt.setString(6, purchase.getDlvyAddr());
        stmt.setString(7, purchase.getDlvyRequest());
        stmt.setString(8, purchase.getTranCode());
        stmt.setDate(9, purchase.getOrderDate());
        stmt.setString(10, purchase.getDlvyDate());
        stmt.executeUpdate();
        
        System.out.println(sql);
       
        stmt.close();
        con.close();
       
        
        System.out.println("insertproductDAO end");
        
        	
		
    }
    
    public void updatePurchase(PurchaseVO purchase) throws Exception {

    	System.out.println("updatePurchase start");
    	
		Connection con = DBUtil.getConnection();
		String sql = "UPDATE transaction SET buyer_id=?, payment_option=?, receiver_name=?, receiver_phone=?, demailaddr=?, dlvy_request=?, dlvy_date=? WHERE tran_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, purchase.getBuyer().getUserId());
		pStmt.setString(2, purchase.getPaymentOption());
		pStmt.setString(3, purchase.getReceiverName());
		pStmt.setString(4, purchase.getReceiverPhone());
		pStmt.setString(5, purchase.getDlvyAddr());
		pStmt.setString(6, purchase.getDlvyRequest());
		pStmt.setString(7, purchase.getDlvyDate());
		pStmt.setInt(8, purchase.getTranNo());
		pStmt.executeUpdate();
		
		System.out.println(sql);
		System.out.println("updatePurchase end");
		
		pStmt.close();
		con.close();
	}
    
    public PurchaseVO findPurchase(int tranNo) throws Exception{
    	
    	System.out.println("findPurchsae start");
    	
    	Connection con = DBUtil.getConnection();
    	
    	String sql = "SELECT * FROM transaction WHERE tran_no";
    	
    	System.out.println(sql);
    	PreparedStatement stmt = con.prepareStatement(sql);
    	stmt.setInt(1, tranNo);
    	
    	System.out.println(tranNo);
    	
    	ResultSet rs = stmt.executeQuery();
    	System.out.println(rs);
    	
    	PurchaseVO purchase = new PurchaseVO();
    	
    	while(rs.next()) {
    		purchase = new PurchaseVO();
    		purchase.setTranNo(rs.getInt("tran_no"));
    		purchase.getPurchaseProd().setProdNo(rs.getInt("prod_no"));
    		purchase.getBuyer().setUserId(rs.getString("buyer_id"));
    		purchase.setPaymentOption(rs.getString("payment_option"));
    		purchase.setReceiverName(rs.getString("receiver_name"));
    		purchase.setReceiverPhone(rs.getString("receiver_phone"));
    		purchase.setDlvyAddr(rs.getString("dlvy_addr"));
    		purchase.setDlvyRequest(rs.getString("dlvy_request"));
    		purchase.setTranCode(rs.getString("tran_status_code"));
    		purchase.setOrderDate(rs.getDate("order_data"));
    		purchase.setDlvyDate(rs.getString("dlvy_date"));
    	}
    	
    	System.out.println(purchase);
    	rs.close();
    	stmt.close();
    	con.close();
    	
    	System.out.println("findPurchase end");
    	return purchase;
    }
    
    public  HashMap<String, Object> getPurchaseList(SearchVO searchVO, String userID) throws Exception {
	
    	Connection con = DBUtil.getConnection();
 
    	String sql =  "SELECT tran_no, prod_no, buyer_id, receiver_name, receiver_phone, tran_status_code FROM transaction where buyer_id ='"+userID+"'"; 
    		
    			/*SELECT tran_no, prod_no, buyer_id, receiver_name, receiver_phone, tran_status_code FROM transaction"
				+ "FROM transaction";
    	
    	sql += "where buyer_id";
    			*/
    	
    	
    	
		System.out.println("PurchaseDAO:: " +sql);

		PreparedStatement stmt = con.prepareStatement(sql,
															ResultSet.TYPE_SCROLL_INSENSITIVE,
															ResultSet.CONCUR_UPDATABLE);
		
		ResultSet rs = stmt.executeQuery();
		
		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));
		System.out.println(" DAO map : " + map);
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
			
		System.out.println(searchVO);
				
					
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
			PurchaseVO purchase = new PurchaseVO();
			ProductVO product = new ProductVO();
			UserVO user = new UserVO();
			
			purchase.setPurchaseProd(product);
			purchase.setBuyer(user);
			
			purchase.setTranNo(rs.getInt("tran_no"));
			product.setProdNo(Integer.parseInt(rs.getString("prod_no")));
			purchase.getBuyer().setUserId(rs.getString("buyer_id"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			/*
			 * purchase.setDlvyAddr(rs.getString("demailaddr"));
			 * purchase.setDlvyRequest(rs.getString("dlvy_request"));
			 * purchase.setDlvyDate(rs.getString("dlvy_date"));
			 * purchase.setOrderDate(rs.getDate("order_data"));
			 * purchase.setPaymentOption(rs.getString("payment_option"));
			 */
			
			System.out.println("DAO VO check:" + purchase);
			list.add(purchase);
			if (!rs.next())
				break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		
		System.out.println("map().size() : "+ map.size());
		
		rs.close();
		stmt.close();
		con.close();
		
		System.out.println("DAO map : "+map);
		return map;
    }
    
    public  Map<String, Object> getSaleList(SearchVO searchVO) throws Exception {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	Connection con = DBUtil.getConnection();
    	
    	String sql = "SELECT * FROM transaction ";
    	
    	int totalCount = this.getTotalCount(sql);
    	System.out.println("PurcDAO :: totalCount  :: " + totalCount);
    	
    	System.out.println("PurchaseDAO:: " +sql);

		PreparedStatement stmt = 
				con.prepareStatement(	sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
											            ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();
		
		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

	
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
			
		System.out.println(searchVO);
				
					
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		while(rs.next()){
			
			PurchaseVO purchase = new PurchaseVO();
			ProductVO product = new ProductVO();
			UserVO user = new UserVO();
			purchase.setTranNo(rs.getInt("tran_no"));
			product.setProdNo(Integer.parseInt(rs.getString("prod_no")));
			purchase.setPurchaseProd(product);
			user.setUserId(rs.getString("buyer_id"));
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDlvyAddr(rs.getString("demailaddr"));
			purchase.setDlvyRequest(rs.getString("dlvy_request"));
			purchase.setDlvyDate(rs.getString("dlvy_date"));
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			
			System.out.println("DAO VO check:" + purchase);
			list.add(purchase);
		}
		
			map.put("list", list);
	
			rs.close();
			stmt.close();
			con.close();
			System.out.println("DAO map : "+map);
			return map;
    }
    
    public void updateTranCode(PurchaseVO purchase) throws Exception {
		
    	System.out.println("###updateTranCode START###");		
    			Connection con = DBUtil.getConnection();

    			String sql = "UPDATE transaction SET tran_status_code=?"
    						+ "WHERE prod_no=?";
    			System.out.println("update sql : "+sql);
    			PreparedStatement stmt = con.prepareStatement(sql);

    			stmt.setString(1, purchase.getTranCode());
    			stmt.setInt(2, purchase.getPurchaseProd().getProdNo());
    			ResultSet rs = stmt.executeQuery();

    			rs.close();
    			stmt.close();
    			con.close();
    	System.out.println("###updateTranCode END###");
    }
    
    private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	

	

}  
