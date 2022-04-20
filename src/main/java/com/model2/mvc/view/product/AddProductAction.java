package com.model2.mvc.view.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class AddProductAction extends Action {

	@Override
	public String execute( HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		System.out.println("AddProductAction start");
		
		ProductVO product = new ProductVO();
		product.setProdName(request.getParameter("prodName"));
		System.out.println(request.getParameter("prodName"));
		
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManufDay(request.getParameter("manufDay"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setImgFile(request.getParameter("imgFile"));
		
		ProductService service = new ProductServiceImpl();
		service.addProduct(product);
		request.setAttribute("product", product);
		
				
		System.out.println(product);
		System.out.println("AddProductAction end");
		
		
		
		if(FileUpload.isMultipartContent(request)) {
			
			String temDir = 
					"C:\\workspace\\01.Model2MVCShop(stu)\\src\\main\\webapp\\images\\uploadFiles\\";
			//String temDir2 = "/uploadFiles/";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			// setSizeThreshold�� ũ�⸦ ����� �Ǹ� ������ ��ġ�� �ӽ÷� �����Ѵ�. 
			fileUpload.setSizeThreshold(1024*1024*10);
			// �ִ� 1�ް����� ���ε� ����(1024*1024*100) <- 100MB
			fileUpload.setSizeThreshold(1024*100); // �ѹ��� 100k ������ �޸𸮿� ����
			
			if (request.getContentLength()<fileUpload.getFileSizeMax()) {
				
				StringTokenizer token = null;
				
				//parseRequest()�� FileItem�� �����ϰ� �ִ� List Type�� return�Ѵ�. 
				List fileItemList = fileUpload.parseRequest(request);
				int Size = fileItemList.size();	// html page���� ���� ������ ������ ���Ѵ�.
				for(int i = 0; i<Size; i++) {
					FileItem fileItem = (FileItem) fileItemList.get(i);
					// isFormField()�� ���ؼ� ������������ �Ķ�������� �����Ѵ�. �ĸ����Ͷ��	true
					if(fileItem.isFormField()) {
						if(fileItem.getFieldName().equals("manufDay")) {
							token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
							String manufDay = token.nextToken() + token.nextToken() + token.nextToken();
							product.setManufDay(manufDay);
						}
						else if(fileItem.getFieldName().equals("prodName"))
							product.setProdName(fileItem.getString("euc-kr"));
						else if(fileItem.getFieldName().equals("prodDetail"))
							product.setProdDetail(fileItem.getString("euc-kr"));
						else if(fileItem.getFieldName().equals("price"))
							product.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
					}else { // ���������̸�...
						
						if(fileItem.getSize()>0) {	//������ �����ϴ� if
							int idx = fileItem.getName().lastIndexOf("\\");
							//getName()�� ��θ� �� �������� ������ lastIndexOf�� �߶󳽴�
							if(idx == -1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx+1);
							product.setImgFile(fileName);
							try {
								File uploadedFile = new File(temDir, fileName);
								fileItem.write(uploadedFile);
							}catch(IOException e) {
								System.out.println(e);
							}
						}else {
							product.setImgFile("../../images/empty.GIF");
						}
					}// else end
				}// for end
				
				ProductServiceImpl serviceImpl = new ProductServiceImpl();
				serviceImpl.addProduct(product);
				request.setAttribute("product", product);
			
			}else {
				// upload�ϴ� ������ setSizeMax���� ū ���
				int overSize = (request.getContentLength()/1000000);
				System.out.println("<script>alert('������ ũ��� 1MB���� �Դϴ�. �ø��� ���Ͽ뷮��" + overSize + "MB�Դϴ�');");
				System.out.println("history.back();</script>");
			}
		}else {
			System.out.println("���ڵ� Ÿ���� multipart/form-data�� �ƴմϴ�.");
		}
		
		return "forward:/product/getProduct.jsp";
	}
	
	
}
