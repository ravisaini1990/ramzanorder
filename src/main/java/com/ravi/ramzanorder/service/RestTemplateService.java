package com.ravi.ramzanorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateService<T> {

    @Autowired
    private RestTemplate restTemplate;


    public T getMappedObject(String url) {
        return restTemplate.exchange(url,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<T>() {
                }).getBody();
    }
}