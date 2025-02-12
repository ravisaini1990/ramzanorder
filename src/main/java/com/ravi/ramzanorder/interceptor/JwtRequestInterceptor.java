package com.ravi.ramzanorder.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public @NonNull ClientHttpResponse intercept(
            @NonNull HttpRequest request,
            @NonNull byte[] body,
            @NonNull ClientHttpRequestExecution execution
    ) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            request.getHeaders().setBearerAuth(jwtAuth.getToken().getTokenValue());
        }

        return execution.execute(request, body);
    }
}
