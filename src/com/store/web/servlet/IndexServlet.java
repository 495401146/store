package com.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class IndexServlet extends BaseServlet {


	public String index(HttpServletRequest request, HttpServletResponse response) {
		//ȥ���ݿ��ѯ������Ʒ��������Ʒ�������Ƿ���request���У�����ת��
		return "/jsp/index.jsp";
	}
   

}
