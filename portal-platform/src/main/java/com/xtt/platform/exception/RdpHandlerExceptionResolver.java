package com.xtt.platform.exception;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.google.common.base.Objects;
import com.xtt.platform.util.CommonUtil;

/**
 * 
 * @ClassName: RdpHandlerExceptionResolver
 * @author: Tik
 * @CreateDate: 2014-4-4 下午10:26:48
 * @UpdateRemark: 说明本次修改内容
 * @Description: 控制层异常处理
 * @version: V1.0
 */
public class RdpHandlerExceptionResolver extends SimpleMappingExceptionResolver {

	Logger log = LoggerFactory.getLogger(RdpHandlerExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		// String viewName = determineViewName(ex, request);
		String url = "error/500";
		// if (ex instanceof FileNotFoundException) {
		// return new ModelAndView();
		// }
		if (isAjaxRequest(request)) {
			response.setContentType("application/json");
			try {
				PrintWriter out = response.getWriter();
				if (ex instanceof JsonResponseException) {
					JsonResponseException jsonEx = (JsonResponseException) ex;
					response.setStatus(jsonEx.getStatus());
					out.print(ex.getMessage());
					out.close();
					writeLog(ex);
					return new ModelAndView();
				}
				if (ex instanceof BindException) {
					BindException bindException = (BindException) ex;
					response.setStatus(400);
					BindingResult result = bindException.getBindingResult();
					if (result.hasErrors())
						out.print(result.getFieldError().getDefaultMessage());

					out.close();
					writeLog(ex);
					return new ModelAndView();
				}
				writeLog(ex);
				response.setStatus(500);
				return new ModelAndView();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			ModelAndView errorModel = new ModelAndView("error/500");
			if (response.getStatus() == 404) {
				errorModel.setViewName("error/404");
			}
			errorModel.addObject("message", CommonUtil.getExceptionMessage(ex));
			return errorModel;
		}
		writeLog(ex);
		return new ModelAndView(url);
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		return Objects.equal(request.getHeader("X-Requested-With"), "XMLHttpRequest");
	}

	private void writeLog(Exception ex) {
		log.error("catch error:", ex);
	}
}
