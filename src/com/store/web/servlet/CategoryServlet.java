package com.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.utils.JsonUtil;
import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.service.impl.CategoryServiceImpl;
import com.store.utils.BeanFactory;

public class CategoryServlet extends BaseServlet {
    CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			//�����ݿ��в�ѯ������Ϣ
			List<Category> clist = null;
			try {
				clist = cs.findAll();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//��������Ϣתjson
			String json = JsonUtil.list2json(clist);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(json);
			
			//ȥ���ݿ��ѯ������Ʒ��������Ʒ�������Ƿ���request���У�����ת��
			return null;
		}
	}

