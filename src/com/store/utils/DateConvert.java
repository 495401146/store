package com.store.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.commons.beanutils.Converter;

public class DateConvert implements Converter {

	/***
	 * ��һ������ClassҪת�ɵ�����
	 * �ڶ�������Objectҳ���ϴ����ֵ
	 */
	public Object convert(Class arg0, Object arg1) {
		//��objectת��date
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Date date =  format.parse((String)arg1);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
