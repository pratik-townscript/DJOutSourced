package com.townscript.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class WebHandlerInterceptor extends HandlerInterceptorAdapter {
	private long startTime = 0L;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("processing: " + request.getRequestURI() + " Handler: " + handler.toString());
        startTime = System.currentTimeMillis();
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        System.out.println("processed: " + request.getRequestURI() + " Handler: " + handler.toString());
        long responseTime = System.currentTimeMillis() - startTime;
        System.out.println(String.format("responseTime: %d ms", responseTime));
    }
}
