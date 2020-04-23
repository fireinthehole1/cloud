package com.learn.spring.cloud.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bill
 * @version 1.0
 * @date 2020/4/23 10:36
 */

public class GlobalExceptionResolver  extends AbstractHandlerExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger("service_log");
    private static final String JSONRESULTCLASSNAME = "com.learn.spring.cloud.core.JsonResult";
    public GlobalExceptionResolver() {
    }
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod)handler;
                if (!JSONRESULTCLASSNAME.equals(handlerMethod.getMethod().getReturnType().getName())) {
                    return new ModelAndView();
                }
            }

            String code = "9999";
            String message = "系统异常";
            if (ex instanceof GlobalException) {
                message = ex.getMessage();
                String state = ((GlobalException)ex).getState();
                if (state != null) {
                    code = state;
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("GlobalExceptionResolver [message]:{}", message);
                } else if (logger.isInfoEnabled()) {
                    logger.info("GlobalExceptionResolver [message]:{}", message);
                }
            } else {
                message = ex.getMessage();
                logger.error("GlobalExceptionResolver [message]:" + message, ex);
            }

            Map<String, Object> attributes = new HashMap(5);
            attributes.put("msg", message);
            attributes.put("code", code);
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            view.setAttributesMap(attributes);
            ModelAndView mv = new ModelAndView();
            mv.setView(view);
            return mv;
        } catch (Exception var11) {
            Exception e = var11;

            try {
                logger.error("ExceptionResolver 解析出错", e);
                response.sendError(400, ex.getMessage());
            } catch (Exception var10) {
                logger.error("Handling of [" + ex.getClass().getName() + "] resulted in Exception", var10);
            }

            return new ModelAndView();
        }
    }
}
