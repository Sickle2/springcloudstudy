package com.sickle.servicefeign.feign.handler;

import com.sickle.servicefeign.feign.HiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springcloudDemo
 * @description:
 * @author: sickle
 * @create: 2019-08-14 18:51
 **/
@RestController
public class HiHandler {

    @Autowired
    private HiClient hiClient;

    @GetMapping("/hello")
    public String sayHello(@RequestParam("name") String name) {
        return hiClient.sayHiFromClientOne(name);
    }

}
