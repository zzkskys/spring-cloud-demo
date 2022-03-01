package com.example.resource;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created Date : 2022/03/01
 *
 * @author zzk
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        return "你好 , " + authentication.getPrincipal();
    }
}
