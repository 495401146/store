package com.store.service.impl;

import java.io.InputStream;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.store.dao.CategoryDao;
import com.store.dao.impl.CategoryDaoImpl;
import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.utils.BeanFactory;
import com.store.web.servlet.CategoryServlet;

public class CategoryServiceImpl implements CategoryService {
	CategoryDao cDao = (CategoryDao) BeanFactory.getBean("CategoryDao");
	public List<Category> findAll() throws Exception {
		//��ȡ���������ļ�
		InputStream in = CategoryServlet.class.getClassLoader().getResourceAsStream("ehcache.xml");		
		//ͨ�������ļ���������
		CacheManager manage = CacheManager.create(in);
		Cache cache = manage.getCache("categoryCache");
		//��ȡ��������Ӧ������
		Element element = cache.get("clist");
		List<Category> clist = null;
		//�ж������Ƿ�Ϊ�գ���Ϊ�գ�������ݿ�ȡ��Ȼ����뻺��
		if(element == null)
		{
			//�����ݿ��в�ѯ������Ϣ
			clist = cDao.findAllCategory();
			cache.put(new Element("clist", clist));
			System.out.println("������û������");
		}
		else
		{
			clist = (List<Category>) element.getObjectValue(); 
			System.out.println("�򻺴���ȡ����");
		}
		
		return clist;
	}

}
