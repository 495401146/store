package com.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Category;
import com.store.domain.Product;
import com.store.service.CategoryService;
import com.store.service.ProductService;
import com.store.service.impl.CategoryServiceImpl;
import com.store.service.impl.ProductServiceImpl;
import com.store.utils.BeanFactory;


public class IndexServlet extends BaseServlet {

	ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//�����ݿ��в�ѯ������Ʒ��������Ʒ
		List<Product> hotlist = ps.findHotProduct();
		List<Product> newlist = ps.findNewProduct();
		System.out.println(hotlist);
		System.out.println(newlist);
		//��������Ʒ��������Ʒ����request����
		request.setAttribute("hotlist", hotlist);
		request.setAttribute("newlist",newlist);
		return "/jsp/index.jsp";
	}
   

}
