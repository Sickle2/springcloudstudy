package com.sickle.serviceribbon.handler;

import com.sickle.serviceribbon.service.HelloWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springcloudDemo
 * @description:
 * @author: sickle
 * @create: 2019-08-14 17:54
 **/
@RestController
public class HelloHandler {

    @Autowired
    private HelloWordService helloWordService;

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        return helloWordService.HelloWordService(name);
    }
}
