package com.example.nacoconsumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("nacos-provider")
public interface ProviderClient {

    @GetMapping("/echo/{str}")
    String echo(@PathVariable("str") String str);

}
