package com.store.utils;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * ���÷���ʵ�ֽ����
 * @author �γ���
 *
 */
public class BeanFactory {
	public static Object getBean(String id)
	{
		Object obj = null;
		try {
			//��ȡxml�ĵ�����
			Document doc = new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
			//��ȡָ��id��Ԫ��
			Element element = (Element) doc.selectSingleNode("//bean[@id='"+id+"']");
			//��ȡ��Ӧclass���Ե�ֵ
			String value = element.attributeValue("class");
			System.out.println(value);
			//���÷���õ��˶���
			obj = Class.forName(value).newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}
