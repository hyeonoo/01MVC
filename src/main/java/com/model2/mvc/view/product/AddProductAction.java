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
			// setSizeThreshold의 크기를 벗어나게 되면 지정한 위치에 임시로 저장한다. 
			fileUpload.setSizeThreshold(1024*1024*10);
			// 최대 1메가까지 업로드 가능(1024*1024*100) <- 100MB
			fileUpload.setSizeThreshold(1024*100); // 한번에 100k 까지는 메모리에 저장
			
			if (request.getContentLength()<fileUpload.getFileSizeMax()) {
				
				StringTokenizer token = null;
				
				//parseRequest()는 FileItem을 포함하고 있는 List Type을 return한다. 
				List fileItemList = fileUpload.parseRequest(request);
				int Size = fileItemList.size();	// html page에서 받은 값들의 개수를 구한다.
				for(int i = 0; i<Size; i++) {
					FileItem fileItem = (FileItem) fileItemList.get(i);
					// isFormField()를 통해서 파일형식인지 파라미터인지 구분한다. 파리미터라면	true
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
					}else { // 파일형식이면...
						
						if(fileItem.getSize()>0) {	//파일을 저장하는 if
							int idx = fileItem.getName().lastIndexOf("\\");
							//getName()은 경로를 다 가져오기 때문에 lastIndexOf로 잘라낸다
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
				// upload하는 파일이 setSizeMax보다 큰 경우
				int overSize = (request.getContentLength()/1000000);
				System.out.println("<script>alert('파일의 크기는 1MB까지 입니다. 올리신 파일용량은" + overSize + "MB입니다');");
				System.out.println("history.back();</script>");
			}
		}else {
			System.out.println("인코딩 타입이 multipart/form-data가 아닙니다.");
		}
		
		return "forward:/product/getProduct.jsp";
	}
	
	
}
