package com.manoel.embalagem.infrastructure.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WithMockJwtSecurityContextFactory implements WithSecurityContextFactory<WithMockJwtAuth> {

    @Override
    public SecurityContext createSecurityContext(WithMockJwtAuth annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        var authorities = Arrays.stream(annotation.roles())
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        var principal = annotation.username();
        var auth = new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);

        context.setAuthentication(auth);
        return context;
    }
}
