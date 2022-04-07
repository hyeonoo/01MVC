
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
public class PurchaseDAO
{

    public PurchaseDAO(){
    }

    public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
        System.out.println("insertPurchaseDAO start");
        Connection con = DBUtil.getConnection();
        String sql = "INSERT INTO transaction VALUES (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, purchaseVO.getPaymentOption());
        stmt.setString(2, purchaseVO.getReceiverName());
        stmt.setString(3, purchaseVO.getReceiverPhone());
        stmt.setString(4, purchaseVO.getDlvyAddr());
        stmt.setString(5, purchaseVO.getDlvyRequest());
        stmt.executeUpdate();
        System.out.println(sql);
        con.close();
        System.out.println("insertproductDAO end");
    }

}  
