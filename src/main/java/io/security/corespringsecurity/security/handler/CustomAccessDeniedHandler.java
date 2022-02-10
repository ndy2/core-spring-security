package io.security.corespringsecurity.security.handler;


import io.security.corespringsecurity.domain.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("accessDeniedHandler")
@PropertySource("classpath:url.properties")
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final String errorPage;

    public CustomAccessDeniedHandler(@Value("${deniedPage}") String errorPage) {
        this.errorPage = errorPage;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        String deniedUrl = errorPage + "?exception"+ exception.getClass().getSimpleName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        request.setAttribute("username", username);
        request.setAttribute("exceptionMessage", exception.getMessage());

        request.getRequestDispatcher(deniedUrl).forward(request,response);
    }
}
