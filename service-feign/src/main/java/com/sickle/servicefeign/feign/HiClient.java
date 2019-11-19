package com.sickle.servicefeign.feign;


import com.sickle.servicefeign.feign.hystrix.HelloRemoteHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author: lianhai
 * Date: 2019-05-10 13:28
 */
@FeignClient(name = "service-hi", fallback = HelloRemoteHystrix.class)
public interface HiClient {

    @GetMapping(value = "/hi")
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
