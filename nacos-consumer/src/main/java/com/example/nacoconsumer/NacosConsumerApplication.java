package com.example.nacoconsumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class NacosConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApplication.class, args);
    }

    @RestController
    public static class NacosController {

        private final String appName;
        private final ServerProvider provider;

        public NacosController(
                @Value("${spring.application.name}") String appName,
                ServerProvider provider
        ) {
            this.appName = appName;
            this.provider = provider;
        }

        @GetMapping("/echo/app-name")
        public String echo() {
            return provider.echo(appName);
        }

    }

    @FeignClient("nacos-provider")
    public interface ServerProvider {

        @GetMapping("/echo/{str}")
        String echo(@PathVariable("str") String str);

    }

}
