package com.store.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {
    
    
	private static final long serialVersionUID = 1L;
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	try {
	    		//��ȡ����
	       	 Class<? extends BaseServlet> clazz = this.getClass();
	       	//��ȡ���󷽷�
	       	String m = request.getParameter("method");
	       	if(m==null)
	       	{
	       		m="index";
	       	}
	       	System.out.println(m);
	       	//��ȡ�����Ӧ�ķ�������
			Method method = clazz.getMethod(m,HttpServletRequest.class,HttpServletResponse.class);
			//���÷���������ֵΪת��·��
			String path = (String) method.invoke(this,request,response);
			//�ж�s�Ƿ�Ϊ�գ�Ϊ�գ��򲻴�
			if(path!=null)
			{
				request.getRequestDispatcher(path).forward(request, response);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
    }
	    public String index(HttpServletRequest request,HttpServletResponse response) throws Exception
	    {
	    	return null;
	    }

}
