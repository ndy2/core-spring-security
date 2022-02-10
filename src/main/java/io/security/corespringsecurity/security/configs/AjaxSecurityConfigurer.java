package io.security.corespringsecurity.security.configs;

import io.security.corespringsecurity.security.filter.AjaxAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class AjaxSecurityConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractAuthenticationFilterConfigurer<H, AjaxSecurityConfigurer<H>, AjaxAuthenticationFilter> {

    private AuthenticationManager authenticationManager;

    public AjaxSecurityConfigurer() {
        super(new AjaxAuthenticationFilter(), null);
    }

    @Override
    public void configure(H http) throws Exception {
        configureAuthenticationManager(http);
        configureSessionAuthenticationStrategy(http);
        configureRememberMeService(http);

        http.setSharedObject(AjaxAuthenticationFilter.class,getAuthenticationFilter());
        http.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private void configureAuthenticationManager(H http) {
        if(authenticationManager == null){
            authenticationManager = http.getSharedObject(AuthenticationManager.class);
        }
        getAuthenticationFilter().setAuthenticationManager(authenticationManager);
    }

    public AjaxSecurityConfigurer<H> setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        return this;
    }

    private void configureRememberMeService(H http) {
        RememberMeServices rememberMeServices = http
                .getSharedObject(RememberMeServices.class);
        if (rememberMeServices != null) {
            getAuthenticationFilter().setRememberMeServices(rememberMeServices);
        }
    }

    private void configureSessionAuthenticationStrategy(H http) {
        SessionAuthenticationStrategy sessionAuthenticationStrategy = http
                .getSharedObject(SessionAuthenticationStrategy.class);
        if (sessionAuthenticationStrategy != null) {
            getAuthenticationFilter().setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        }
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }
}
