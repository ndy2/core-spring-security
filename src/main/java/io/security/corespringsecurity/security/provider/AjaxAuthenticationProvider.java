package io.security.corespringsecurity.security.provider;

import io.security.corespringsecurity.security.details.FormWebAuthenticationDetails;
import io.security.corespringsecurity.security.service.AccountContext;
import io.security.corespringsecurity.security.token.AjaxAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("ajaxAuthenticationProvider")
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AjaxAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getPrincipal() == null ? "NONE_PROVIDED" : authentication.getName();
        AccountContext accountContext = (AccountContext)userDetailsService.loadUserByUsername(username);

        String presentedPassword = authentication.getCredentials().toString();
        if(!passwordEncoder.matches(presentedPassword, accountContext.getPassword())){
            throw new BadCredentialsException("비밀 번호를 확인하세요");
        }

        return createSuccessAuthentication(authentication, accountContext);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AjaxAuthenticationToken.class.isAssignableFrom(authentication);
    }


    private Authentication createSuccessAuthentication(Authentication authentication, AccountContext accountContext) {
        return new AjaxAuthenticationToken(authentication.getPrincipal(),authentication.getPrincipal(),accountContext.getAuthorities());
    }

}
