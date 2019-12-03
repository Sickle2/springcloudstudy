package com.sickle.zipkinserver.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springcloudDemo
 * @description:
 * @author: sickle
 * @create: 2019-08-14 17:40
 **/
@RestController
public class SayHelloWorldHandler {

    @Value("${server.port}")
    String port;

    @GetMapping("/hi")
    public String home(@RequestParam String name) throws InterruptedException {
        //模拟返回超时
//        Thread.sleep(1000000);
        return "hello world! " + name + ",i am from port:" + port;
    }
}
