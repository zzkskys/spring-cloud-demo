package com.example.nacoconsumer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("student")
@Data
public class StudentProperties {

    private String name = "张三";

    private int age = 18;

    /**
     * cron 表达式，默认每秒执行一次
     */
    private String cron = "* * * * * ?";
}
