package com.example.nacoconsumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RefreshScope
@AllArgsConstructor
public class ScheduleService implements ApplicationListener<RefreshScopeRefreshedEvent> {

    private final StudentProperties studentProperties;

    @Scheduled(cron = "#{studentProperties.cron}")
    public void hello() {
        log.info("你好 {}",studentProperties.getName());
    }

    @Override
    public void onApplicationEvent(RefreshScopeRefreshedEvent event) {
        hello();
    }
}
