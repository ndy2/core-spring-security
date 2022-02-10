package io.security.corespringsecurity.security.provider;

import io.security.corespringsecurity.security.details.FormWebAuthenticationDetails;
import io.security.corespringsecurity.security.service.AccountContext;
import io.security.corespringsecurity.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("authenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
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

        FormWebAuthenticationDetails details = (FormWebAuthenticationDetails) authentication.getDetails();
        String secretKey = details.getSecretKey();
        if(!"secret".equals(secretKey)){
            throw new InsufficientAuthenticationException("Secret Key Not Provided");
        }

        return createSuccessAuthentication(authentication, accountContext);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


    private Authentication createSuccessAuthentication(Authentication authentication, AccountContext accountContext) {
        return new UsernamePasswordAuthenticationToken(accountContext,authentication.getCredentials(),accountContext.getAuthorities());
    }
}
