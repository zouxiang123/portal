package com.xtt.platform.framework.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.xtt.platform.util.Constants;

/**
 * 
 * @ClassName: BaseController
 * @author: Tik
 * @CreateDate: 2014-4-2 下午4:33:11
 * @Description: spring MVC基类控制
 * @version: V1.0
 */
public class SpringSuperController {

    protected static final String ERROR_MSG_KEY = "errorMsg";
    protected Logger logger = LoggerFactory.getLogger(SpringSuperController.class);

    // 获取保存在Session中的用户对象
    protected Object getSessionUser(HttpServletRequest request) {
        return request.getSession().getAttribute(Constants.USER_CONTEXT);
    }

    // 将用户对象保存到Session中
    protected void setSessionUser(HttpServletRequest request, Object obj) {
        request.getSession().setAttribute(Constants.USER_CONTEXT, obj);
    }

    // 获取基于应用程序的url绝对路径
    public final String getAppbaseUrl(HttpServletRequest request, String url) {
        Assert.hasLength(url, "url不能为空");
        Assert.isTrue(url.startsWith("/"), "必须以/打头");
        return request.getContextPath() + url;
    }

}