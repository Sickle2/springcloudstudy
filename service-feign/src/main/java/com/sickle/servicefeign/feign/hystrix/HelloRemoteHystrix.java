package com.sickle.servicefeign.feign.hystrix;

import com.sickle.servicefeign.feign.HiClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: springcloudDemo
 * @description:
 * @author: sickle
 * @create: 2019-11-19 15:52
 **/
@Component
public class HelloRemoteHystrix  implements HiClient {
    @Override
    public String sayHiFromClientOne(@RequestParam(value = "name") String name) {
        return "Hello World  !!!";
    }
}
