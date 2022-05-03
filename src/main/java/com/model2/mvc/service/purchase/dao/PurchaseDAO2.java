package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;


public class PurchaseDAO {
	///Field
	///Constructor
	public PurchaseDAO(){
	}
	
	///Method
	public void insertPurchase(Purchase purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction "
				+ "VALUES (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,'111',sysdate,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
//		stmt.setString(8, purchaseVO.getTranCode()); 배송코드 만들면 수정
		stmt.setString(8, purchaseVO.getDivyDate());

		stmt.executeUpdate();
		
		stmt.close();
		con.close();
	}

	public Purchase findPurchase(int tranNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();
		System.out.println("DAO sql : "+sql);
		
		Purchase purc = new Purchase();		
		Product prod = new Product();
		User user = new User();
		purc.setPurchaseProd(prod);
		purc.setBuyer(user);		
System.out.println("dao purc : "+purc);
		
		while (rs.next()) {
			purc.setTranNo(rs.getInt("tran_no"));
			purc.getPurchaseProd().setProdNo(rs.getInt("prod_no"));
			purc.getBuyer().setUserId(rs.getString("buyer_id"));
			purc.setPaymentOption(rs.getString("payment_option"));
			purc.setReceiverName(rs.getString("receiver_name"));
			purc.setReceiverPhone(rs.getString("receiver_phone"));
			purc.setDivyAddr(rs.getString("divy_addr"));
			purc.setDivyRequest(rs.getString("divy_request"));
			purc.setTranCode(rs.getString("tran_status_code"));
			purc.setOrderDate(rs.getDate("order_data"));
			purc.setDivyDate(rs.getString("divy_date"));
		}
		rs.close();
		stmt.close();
		con.close();
System.out.println("DAO purc : "+purc);
		return purc;
	}

	public Map<String,Object> getPurchaseList(Search search, String userId) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
System.out.println("DAO map : "+map);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT tran_no, prod_no, buyer_id, receiver_name, receiver_phone, tran_status_code "
				+ "FROM transaction";
		
//		if (search.getSearchCondition() != null) {
//			if (search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) {
//				sql += " WHERE prod_no='" + search.getSearchKeyword()
//			+"'OR prod_no LIKE '%"+search.getSearchKeyword()+"%'";
//			} else if (search.getSearchCondition().equals("1") &&  !search.getSearchKeyword().equals("")) {
//				sql += " WHERE prod_name='" + search.getSearchKeyword()
//			+ "'OR prod_name LIKE '%"+search.getSearchKeyword()+"%'";  //검색조건유지 like%%
//			} else if (search.getSearchCondition().equals("2") &&  !search.getSearchKeyword().equals("")) {
//				sql += " WHERE price='" + search.getSearchKeyword()
//			+ "'OR price LIKE '%"+search.getSearchKeyword()+"%'";
//			} 
//		}
//		sql += " ORDER BY prod_no";
		
		
		System.out.println("PurcDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurcDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println("Purc DAO search : "+search);
		List<Purchase> list = new ArrayList<Purchase>();
		
		while(rs.next()){
			Purchase purc = new Purchase();
			Product prod = new Product();
			User user = new User();
			purc.setPurchaseProd(prod);
			purc.setBuyer(user);	
			
			purc.setTranNo(rs.getInt("tran_no"));
			purc.getPurchaseProd().setProdNo(rs.getInt("prod_no"));
			purc.getBuyer().setUserId(rs.getString("buyer_id"));
			purc.setReceiverName(rs.getString("receiver_name"));
			purc.setReceiverPhone(rs.getString("receiver_phone"));
			purc.setTranCode(rs.getString("tran_status_code"));					
			list.add(purc);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();
System.out.println("DAO map : "+map);
		return map;
	}

public Map<String,Object> getSaleList(Search search) throws Exception {
		
	Map<String , Object>  map = new HashMap<String, Object>();
System.out.println("DAO map : "+map);
	
	Connection con = DBUtil.getConnection();
	
	String sql = "SELECT tran_no, prod_no, buyer_id, receiver_name, receiver_phone, tran_status_code "
			+ "FROM transaction";
	
//	if (search.getSearchCondition() != null) {
//		if (search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) {
//			sql += " WHERE prod_no='" + search.getSearchKeyword()
//		+"'OR prod_no LIKE '%"+search.getSearchKeyword()+"%'";
//		} else if (search.getSearchCondition().equals("1") &&  !search.getSearchKeyword().equals("")) {
//			sql += " WHERE prod_name='" + search.getSearchKeyword()
//		+ "'OR prod_name LIKE '%"+search.getSearchKeyword()+"%'";  //검색조건유지 like%%
//		} else if (search.getSearchCondition().equals("2") &&  !search.getSearchKeyword().equals("")) {
//			sql += " WHERE price='" + search.getSearchKeyword()
//		+ "'OR price LIKE '%"+search.getSearchKeyword()+"%'";
//		} 
//	}
//	sql += " ORDER BY prod_no";
	
	
	System.out.println("PurcDAO::Original SQL :: " + sql);
	
	//==> TotalCount GET
	int totalCount = this.getTotalCount(sql);
	System.out.println("PurcDAO :: totalCount  :: " + totalCount);
	
	//==> CurrentPage 게시물만 받도록 Query 다시구성
	sql = makeCurrentPageSql(sql, search);
	PreparedStatement pStmt = con.prepareStatement(sql);
	ResultSet rs = pStmt.executeQuery();

	System.out.println("Purc DAO search : "+search);
	List<Purchase> list = new ArrayList<Purchase>();
	
	while(rs.next()){
		Purchase purc = new Purchase();
		Product prod = new Product();
		User user = new User();
		purc.setPurchaseProd(prod);
		purc.setBuyer(user);	
		
		purc.setTranNo(rs.getInt("tran_no"));
		purc.getPurchaseProd().setProdNo(rs.getInt("prod_no"));
		purc.getBuyer().setUserId(rs.getString("buyer_id"));
		purc.setReceiverName(rs.getString("receiver_name"));
		purc.setReceiverPhone(rs.getString("receiver_phone"));
		purc.setTranCode(rs.getString("tran_status_code"));					
		list.add(purc);
	}
	
	//==> totalCount 정보 저장
	map.put("totalCount", new Integer(totalCount));
	//==> currentPage 의 게시물 정보 갖는 List 저장
	map.put("list", list);

	rs.close();
	pStmt.close();
	con.close();
System.out.println("DAO map : "+map);
	return map;
}
	
	public void updatePurchase(Purchase purc) throws Exception {
System.out.println("###updatePurcahse START###");		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET payment_option=?,receiver_name=?,receiver_phone=?,divy_addr=?,divy_request=?,divy_date=? WHERE tran_no=?";
System.out.println("update sql : "+sql);
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purc.getPaymentOption());
		stmt.setString(2, purc.getReceiverName());
		stmt.setString(3, purc.getReceiverPhone());
		stmt.setString(4, purc.getDivyAddr());
		stmt.setString(5, purc.getDivyRequest());
		stmt.setString(6, purc.getDivyDate());
		stmt.setInt(7, purc.getTranNo());
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
System.out.println("###updatePurcahse END###");		

	}
	
	public void updateTranCode(Purchase purc) throws Exception {
		
System.out.println("###updateTranCode START###");		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET tran_status_code=?"
					+ "WHERE prod_no=?";
		System.out.println("update sql : "+sql);
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, purc.getTranCode());
		stmt.setInt(2, purc.getPurchaseProd().getProdNo());
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
	
//	 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("PurcDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}
