package com.store.service.impl;

import java.util.LinkedList;
import java.util.List;

import com.store.dao.ProductDao;
import com.store.dao.impl.ProductDaoImpl;
import com.store.domain.PageBean;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {
	ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
	public List<Product> findHotProduct() throws Exception {
		List<Product> plist = pd.findHotProduct();
		return plist;
	}

	public List<Product> findNewProduct() throws Exception {
		List<Product> plist = pd.findNewProduct();
		return plist;
	}

	public Product getProductById(String pid) throws Exception {
		return pd.getProductById(pid);
	}

	public PageBean<Product> findByPage(String cid, int currPage,int PageSize)
			throws Exception {
		//��ȡtotalcoun
		int totalCount = pd.getTotalCount(cid);
		//��ȡ��ǰҳ���е�list�б�
		List<Product> list = pd.findByPage(PageSize,currPage,cid);
		//����PageBean
		return new PageBean<Product>(PageSize, currPage, totalCount, list);
	}

	public List<Product> findHistory(LinkedList<String> list) throws Exception {
		List<Product> pList = pd.getHistory(list);
		return pList;
	}

}
