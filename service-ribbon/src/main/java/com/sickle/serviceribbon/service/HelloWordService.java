package com.sickle.serviceribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @program: springcloudDemo
 * @description:
 * @author: sickle
 * @create: 2019-08-14 17:51
 **/
@Service
public class HelloWordService {

    @Autowired
    private RestTemplate restTemplate;

    public String HelloWordService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name=" + name, String.class);
    }
}
