package com.store.web.servlet;

import java.io.IOException;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.Request;

import com.store.domain.PageBean;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.service.impl.ProductServiceImpl;
import com.store.utils.BeanFactory;
import com.store.utils.CookUtils;

public class ProductServlet extends BaseServlet {
	ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getProductById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//=��ȡ��Ʒ���
		String pid = request.getParameter("pid");
		Product p = null;
		//�����ݿ��в����Ʒ��Ϣ
		try {
			p = ps.getProductById(pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("p", p);
		//����cookie�ж�
		Cookie cookie = CookUtils.getCookieByName("history", request.getCookies());
		cookie = setCookieHistory(cookie,p,request);
		response.addCookie(cookie);
		return "/jsp/product_info.jsp";
	}
	/***
	 * ����Cookie����
	 * @param cookie
	 * @param p
	 * @param request
	 */
	private Cookie setCookieHistory(Cookie cookie, Product p, HttpServletRequest request) {
		if(cookie == null)
		{
			cookie = new Cookie("history",p.getPid());
		}
		else
		{
			String values = cookie.getValue();
			System.out.println("cookie�е�ֵ��"+values);
			LinkedList<String> list =new LinkedList<String>();
			for(String i:values.split(","))
			{
				list.addLast(i);
			}
			System.out.println("ȡ����list"+list);
			//�ж�list�Ƿ��������Ʒ
			if(list.contains(p.getPid()))
			{
				list.remove(p.getPid());
				list.addFirst(p.getPid());
			}
			else
			{
				//�ж�list�Ƿ���ڵ���3
				if(list.size()>=3)
				{
					list.removeLast();
					list.addFirst(p.getPid());
				}
				else{
					list.addFirst(p.getPid());
				}
			}
			StringBuilder sb = new StringBuilder(); 
			//��listתString
			for(int i = 0;i<list.size();i++)
			{
				sb.append(list.get(i));
				if(i+1!=list.size())
					sb.append(",");
			}
			System.out.println("ת���ɵ�list"+sb.toString());
			cookie = new Cookie("history",sb.toString());
			System.out.println("�����cookie"+cookie.getValue());
		}
		cookie.setMaxAge(Integer.MAX_VALUE);
		cookie.setPath(request.getContextPath()+"/");
		return cookie;
	}

	public String findByPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//�ж�cookie
		Cookie cookie = CookUtils.getCookieByName("history", request.getCookies());
		String[] str = cookie.getValue().split(",");
		LinkedList<String> list = new  LinkedList<String>();
		for(int i = 0;i<str.length;i++)
		{
			list.addLast(str[i]);
		}
		
		//����serviceѰ�������ʷ
		List<Product> plist = ps.findHistory(list);
		//1.��ȡcid,currPage

		String cid = request.getParameter("cid");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		//����service�з�������ȡ���ص�pageBean
		PageBean<Product> bean = null;
		int pageSize = 12;
		try {
			bean = ps.findByPage(cid,currPage,pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��pageBean����request��
		request.setAttribute("pb", bean);
		request.setAttribute("plist", plist);
		System.out.println(bean);
		return "/jsp/product_list.jsp";
	}
}
