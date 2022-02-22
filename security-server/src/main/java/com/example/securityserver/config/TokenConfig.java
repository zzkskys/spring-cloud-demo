package com.example.securityserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Created Date : 2022/02/17
 *
 * @author zzk
 */
@Configuration
public class TokenConfig {
    /**
     * 令牌存储在内存中
     */
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    /**
     * 令牌管理服务
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices(
            TokenStore tokenStore,
            ClientDetailsService clientDetailsService

    ) {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore);
        services.setAccessTokenValiditySeconds(7200); //令牌默认有效期 : 2小时
        services.setRefreshTokenValiditySeconds(259200); //刷新令牌默认有效期 : 3天
        return services;
    }

    @Bean
    public AuthorizationCodeServices codeServices() {
        return new InMemoryAuthorizationCodeServices();
    }
}
