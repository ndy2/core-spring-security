package io.security.corespringsecurity.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("authenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RequestCache requestCache;
    private final RedirectStrategy redirectStrategy;

    public CustomAuthenticationSuccessHandler() {
        super("/");
        requestCache = new HttpSessionRequestCache();
        redirectStrategy = new DefaultRedirectStrategy();
    }

    /**
     * AccessDenied 로 loginPage 로 redirect 된 경우
     * 원래 가고자 했던 페이지로 redirect 시켜준다.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String redirectUrl = getDefaultTargetUrl();
        if(savedRequest!=null){
             redirectUrl = savedRequest.getRedirectUrl();
        }
        redirectStrategy.sendRedirect(request,response,redirectUrl);
    }
}
