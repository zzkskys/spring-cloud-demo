package com.example.securityserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created Date : 2022/02/17
 *
 * @author zzk
 */
@Configuration
public class TokenConfig {


    /**
     * JWT令牌密钥
     */
    private static final String JWT_SINGLE_KEY = "test";

    /**
     * 令牌存储在内存中
     */
    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter converter) {
        return new JwtTokenStore(converter);
    }


    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(JWT_SINGLE_KEY);
        return converter;
    }

    /**
     * 令牌管理服务
     */
    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices(
            ClientDetailsService clientDetailsService,
            TokenStore tokenStore,
            JwtAccessTokenConverter accessTokenConverter,
            JwtTokenEnhancer enhancer
    ) {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore);
        services.setAccessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(2)); //令牌默认有效期 : 2小时
        services.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(3)); //刷新令牌默认有效期 : 3天

        //设置令牌增加服务
        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(enhancer, accessTokenConverter));
        services.setTokenEnhancer(chain);

        return services;
    }


    /**
     * 颁发与存储令牌授权码服务类
     */
    @Bean
    public AuthorizationCodeServices codeServices() {
        return new InMemoryAuthorizationCodeServices();
    }
}
