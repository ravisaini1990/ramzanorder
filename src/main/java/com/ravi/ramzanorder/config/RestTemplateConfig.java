package com.ravi.ramzanorder.config;

import com.ravi.ramzanorder.interceptor.JwtRequestInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfig {

    // Add Interceptor to add Authorization header automatically
    //to fix this after eureka server registry -java.net.UnknownHostException: RAMZANAUTHSERVICE
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(JwtRequestInterceptor jwtRequestInterceptor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(jwtRequestInterceptor);
        return restTemplate;
    }
}
