package com.sharom.wrm.config.interceptor;

import com.sharom.wrm.common.context.LangContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String lang = request.getHeader("X-Lang");

        if (lang == null || lang.isBlank()) {
            lang = "en"; // default
        }

        LangContext.setLang(lang);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        LangContext.clear();
    }
}