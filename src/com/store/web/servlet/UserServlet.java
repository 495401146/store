package com.store.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.constant.Constant;
import com.store.domain.User;
import com.store.service.UserService;
import com.store.service.impl.UserServiceImpl;
import com.store.utils.DateConvert;
import com.store.utils.MD5Utils;
import com.store.utils.UUIDUtils;
import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.sun.org.apache.commons.beanutils.ConvertUtils;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

/***
 * ���û���ص�servlet
 * @author 49540
 *
 */
public class UserServlet extends BaseServlet {
	private UserService s = new UserServiceImpl();
	private static final long serialVersionUID = 1L;

	public String add(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("userServlet��add����ִ����");
		return null;
	}
	/***
	 * ��ת��ע�����
	 * @param req
	 * @param resp
	 * @return String
	 */
	public String registerUI(HttpServletRequest request, HttpServletResponse response)
	{
		return "/jsp/register.jsp";
	}
	/***
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String register(HttpServletRequest request, HttpServletResponse response)
	{
		String ImageCode = (String) request.getSession().getAttribute("msg");
		System.out.println(ImageCode);
		System.out.println(request.getParameter("ImageCode"));
		if(ImageCode == null)
		{
			request.setAttribute("msg","�����ˡ�����");
			return "/jsp/msg.jsp";
		}
		else if(ImageCode.equalsIgnoreCase(request.getParameter("ImageCode")))
		{
			request.setAttribute("msg","������֤��������");
			return "/jsp/msg.jsp";
		}
		try {
			//1.��װ����
			User user = new User();
			ConvertUtils.register(new DateConvert(), Date.class);
			System.out.println(request.getParameterMap());
			BeanUtils.populate(user, request.getParameterMap());
			user.setUid(UUIDUtils.getId());
			user.setCode(UUIDUtils.getCode());
			user.setPassword(MD5Utils.md5(user.getPassword()));
			//2.����service���ע��
			s.register(user);
			//3.���ҳ������ת��
			request.setAttribute("msg","�û�ע���ѳɹ����Ͽ�ȥ���伤���");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "/jsp/msg.jsp";
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String code = request.getParameter("code");
		User user = s.active(code);
		if(user==null)
		{
			request.setAttribute("msg", "�����¼���");
		}else{
			request.setAttribute("msg", "�����Ѽ���ɹ�");
		}
		
		return "/jsp/msg.jsp";
	}
	/**
	 * ��ת����¼ҳ��
	 * @param request
	 * @param response
	 * @return
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response)
	{
		return "/jsp/login.jsp";
	}
	
	/**
	 * �û���¼
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//��ȡ�û���������
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username+","+password);
		//���û�������md5���ܵ������������ݿ���жԱ�
		User user = s.login(username,MD5Utils.md5(password));
		System.out.println(user);
		//���Ϊ�գ�����ת
		if(user == null)
		{
			request.setAttribute("msg", "�û������������");
			return "/jsp/login.jsp";
		}
		if(Constant.USER_IS_ACTIVE != user.getState())
		{
			request.setAttribute("msg", "δ����Ͽ�ȥ�����");
			return "/jsp/login.jsp";
		}
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath()+"/");
		
		return null;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		response.sendRedirect(request.getContextPath()+"/");
		return null;
	}
	
	public String checkUsernameIsExist(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String username = request.getParameter("username");
		User user = s.checkUsernameIsExist(username);
		PrintWriter writer = response.getWriter();
		if(user == null)
		{
			writer.print(0);
		}
		else
		{
			writer.print(1);
		}
		
		return null;
	}
}
