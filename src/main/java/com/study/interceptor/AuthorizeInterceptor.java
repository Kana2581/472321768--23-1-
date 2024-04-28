package com.study.interceptor;

import com.study.entity.UserInformation;
import com.study.service.UserInformationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.HandlerInterceptor;


public class AuthorizeInterceptor implements HandlerInterceptor {
    @Resource
    UserInformationService userInformationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User user = (User)authentication.getPrincipal();
        String username = user.getUsername();
        UserInformation account = userInformationService.returnUserInformation(Integer.valueOf(username));
        request.getSession().setAttribute("account", account);

        return true;
    }
}