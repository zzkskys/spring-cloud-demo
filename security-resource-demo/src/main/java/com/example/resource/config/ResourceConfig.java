package com.example.resource.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Created Date : 2022/02/26
 *
 * @author zzk
 */
@Configuration
@EnableResourceServer
@AllArgsConstructor
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "res1";

    private final TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .resourceId(RESOURCE_ID)
                .tokenStore(tokenStore)
//                .tokenServices(tokenServices())
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin().disable()

                .authorizeRequests(it -> {
//                    it.anyRequest().permitAll();
                    it.antMatchers("/**").access("#oauth2.hasScope('all')");
                })
                .sessionManagement(management -> {
                    management.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });
    }

    @Bean
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices services = new RemoteTokenServices();
        services.setCheckTokenEndpointUrl("http://localhost:9000/oauth/check_token");
        services.setClientId("c1");
        services.setClientSecret("secret");

        return services;
    }

}
