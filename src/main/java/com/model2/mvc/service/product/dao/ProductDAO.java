package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;


public class ProductDAO {
	
	public ProductDAO() {
	}

	public void insertProduct(ProductVO product) throws Exception{
		
		System.out.println("insertproductDAO start");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO PRODUCT VALUES (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManufDay().replace("-", ""));
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getImgFile());
	
		stmt.executeUpdate();
		
		System.out.println(sql);
		
		con.close();
		
		System.out.println("insertproductDAO end");
	}
	
	public ProductVO findProduct(int prodNo) throws Exception {
		
		System.out.println("findProduct start");
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM PRODUCT WHERE PROD_NO=? ";
		System.out.println(sql);
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		System.out.println(prodNo);
		
		ResultSet rs = stmt.executeQuery();
		System.out.println(rs);

		ProductVO product = null;
		while (rs.next()) {
			product = new ProductVO();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManufDay(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setImgFile(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
		}
		
		System.out.println(sql);
		
		con.close();
		
		System.out.println("findProduct end");
		return product;
		
	}

	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		System.out.println("getP.DAOMap start");
		

		String sql = "SELECT * FROM product ";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " WHERE prod_no='" + searchVO.getSearchKeyword()
						+ "'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " WHERE prod_name='" + searchVO.getSearchKeyword()
						+ "'";
			} else if (searchVO.getSearchCondition().equals("2")) {
				sql += " WHERE price='" + searchVO.getSearchKeyword()
						+ "'";
			}	
		}
		sql += " ORDER BY prod_no";
		
		PreparedStatement stmt = 
				con.prepareStatement(	sql,
															ResultSet.TYPE_SCROLL_INSENSITIVE,
															ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery();

			rs.last();
			int total = rs.getRow();
			System.out.println("로우의 수:" + total);

			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("count", new Integer(total));

			rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
			System.out.println("searchVO.getPage():" + searchVO.getPage());
			System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
		
			ArrayList<ProductVO> list = new ArrayList<ProductVO>();
			if (total > 0) {
				for (int i = 0; i < searchVO.getPageUnit(); i++) {
					ProductVO product = new ProductVO();
					product.setProdNo(rs.getInt("prod_no"));
					product.setProdName(rs.getString("prod_name"));
					product.setProdDetail(rs.getString("prod_detail"));
					product.setManufDay(rs.getString("manufacture_day"));
					product.setPrice(rs.getInt("price"));
					System.out.println(rs.getInt("price"));
					product.setRegDate(rs.getDate("reg_date"));
				
					list.add(product);
					if (!rs.next())
						break;
				}
			}
		
			System.out.println("list.size() : "+ list.size());
			map.put("list", list);
			System.out.println("map().size() : "+ map.size());

			con.close();
				
			return map;
	}
	
	

	public void updateProduct(ProductVO product) throws Exception {
		
		System.out.println("updateProduct start");
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE PRODUCT SET prod_name=?, prod_detail=?, manufacture_day=?, Price=?, image_file=? WHERE prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManufDay());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getImgFile());
		stmt.setInt(6, product.getProdNo());
		
		
		stmt.executeUpdate();
		
		System.out.println("updateProduct end");
		con.close();
	}
	
	
}
