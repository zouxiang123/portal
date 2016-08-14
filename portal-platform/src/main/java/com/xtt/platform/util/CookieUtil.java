package com.xtt.platform.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @ClassName: CookieUtils
 * @author:  [Tik] 
 * @CreateDate: [2013-7-9 下午1:56:03]   
 * @UpdateUser: [Tik]   
 * @UpdateDate: [2013-7-9 下午1:56:03]   
 * @UpdateRemark: [说明本次修改内容]
 * @Description:  [TODO(Cookie操作工具类)]
 * @version: [V1.0]
 */
public class CookieUtil {

	/**
	 * 
	 * @Title: [setCookie]
	 * @author:  [Tik] 
	 * @CreateDate: [2013-7-9 下午1:56:34]   
	 * @UpdateUser: [Tik]   
	 * @UpdateDate: [2013-7-9 下午1:56:34]   
	 * @UpdateRemark: [说明本次修改内容]
	 * @Description:  [TODO(设置 Cookie（生成时间为1天）)]
	 * @version: [V1.0]
	 * @param response
	 * @param name 名称
	 * @param value 值
	 */
	public static void setCookie(HttpServletResponse response, String name, String value) {
		setCookie(response, name, value, 60*60*24);
	}
	
	/**
	 * 
	 * @Title: [setCookie]
	 * @author:  [Tik] 
	 * @CreateDate: [2013-7-9 下午1:57:09]   
	 * @UpdateUser: [Tik]   
	 * @UpdateDate: [2013-7-9 下午1:57:09]   
	 * @UpdateRemark: [说明本次修改内容]
	 * @Description:  [TODO(设置Cookie)]
	 * @version: [V1.0]
	 * @param response
	 * @param name 名称
	 * @param value 值
	 * @param maxAge 生存时间
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		try {
			cookie.setValue(URLEncoder.encode(value, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.addCookie(cookie);
	}

	/**
	 * 
	 * @Title: [getCookie]
	 * @author:  [Tik] 
	 * @CreateDate: [2013-7-9 下午1:58:30]   
	 * @UpdateUser: [Tik]   
	 * @UpdateDate: [2013-7-9 下午1:58:30]   
	 * @UpdateRemark: [说明本次修改内容]
	 * @Description:  [TODO(Cookie值获取)]
	 * @version: [V1.0]
	 * @param request
	 * @param name key值
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		return getCookie(request, null, name, false);
	}

	/**
	 * 
	 * @Title: [getCookie]
	 * @author:  [Tik] 
	 * @CreateDate: [2013-7-9 下午1:59:11]   
	 * @UpdateUser: [Tik]   
	 * @UpdateDate: [2013-7-9 下午1:59:11]   
	 * @UpdateRemark: [说明本次修改内容]
	 * @Description:  [TODO(获取指定的Cookie值，并删除)]
	 * @version: [V1.0]
	 * @param request
	 * @param response
	 * @param name 名称
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		return getCookie(request, response, name, true);
	}

	/**
	 * 
	 * @Title: [getCookie]
	 * @author:  [Tik] 
	 * @CreateDate: [2013-7-9 下午2:00:45]   
	 * @UpdateUser: [Tik]   
	 * @UpdateDate: [2013-7-9 下午2:00:45]   
	 * @UpdateRemark: [说明本次修改内容]
	 * @Description:  [TODO(获得指定Cookie的值,根据操作是否移除)]
	 * @version: [V1.0]
	 * @param request
	 * @param response
	 * @param name key值
	 * @param isRemove 是否移除
	 * @return
	 */
	
	private static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemove) {
		String value = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					try {
						value = URLDecoder.decode(cookie.getValue(), "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (isRemove) {
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
		}
		return value;
	}
}
