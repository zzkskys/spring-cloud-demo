package com.example.nacoconsumer;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class NacosController {

    private final ProviderClient provider;
    private final StudentProperties studentProperties;


    @GetMapping("/echo/app-name")
    public String echo(
            @RequestParam(defaultValue = "Hello World!") String content
    ) {
        return provider.echo(content);
    }


    @GetMapping("/properties")
    public StudentProperties properties() {
        return studentProperties;
    }

}
