package com.xtt.platform.framework.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.xtt.platform.util.Constants;
import com.xtt.platform.util.lang.StringUtil;

/**
 * 
 * @ClassName: RdpFilter
 * @author: Tik
 * @CreateDate: 2014-4-2 下午5:00:16
 * @UpdateRemark: 说明本次修改内容
 * @Description: 未登陆用户是否有访问权限
 * @version: V1.0
 */
public class RdpFilter implements Filter {

    private static final String FILTERED_REQUEST = "@@session_context_filtered_request";

    // 不需要登录即可访问的URI资源
    private static final String[] INHERENT_ESCAPE_URIS = { "/help.html", "/index.html", "/login.html" };

    // 执行过滤
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 保证该过滤器在一次请求中只被调用一次
        if (request != null && request.getAttribute(FILTERED_REQUEST) != null) {
            chain.doFilter(request, response);
        } else {
            // 设置过滤标识，防止一次请求多次过滤
            request.setAttribute(FILTERED_REQUEST, Boolean.TRUE);
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            Object userContext = httpRequest.getSession().getAttribute(Constants.USER_CONTEXT);
            // 用户未登录, 且当前URI资源需要登录才能访问
            if (userContext == null && !isURILogin(httpRequest.getRequestURI(), httpRequest)) {
                String toUrl = httpRequest.getRequestURL().toString();
                if (!StringUtil.isEmpty(httpRequest.getQueryString())) {
                    toUrl += "?" + httpRequest.getQueryString();
                }
                // 将用户的请求URL保存在session中，用于登录成功之后，跳到目标URL
                httpRequest.getSession().setAttribute(Constants.LOGIN_TO_URL, toUrl);
                // 转发到登录页面
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            chain.doFilter(request, response);
        }
    }

    // 当前URI资源是否需要登录才能访问
    private boolean isURILogin(String requestURI, HttpServletRequest request) {

        if (request.getContextPath().equalsIgnoreCase(requestURI) || (request.getContextPath() + "/").equalsIgnoreCase(requestURI)) {
            return true;
        }

        for (String uri : INHERENT_ESCAPE_URIS) {
            if (requestURI != null && requestURI.indexOf(uri) >= 0) {
                return true;
            }
        }
        return false;
    }

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }
}
