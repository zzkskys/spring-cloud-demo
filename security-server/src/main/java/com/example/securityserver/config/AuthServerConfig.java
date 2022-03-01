package com.example.securityserver.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

/**
 * Created Date : 2022/02/17
 *
 * @author zzk
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {


    private final AuthenticationManager authenticationManager;
    private final AuthorizationCodeServices authorizationCodeServices;

    private final ClientDetailsService clientDetailsService;
    private final TokenStore tokenStore;

    private final JwtAccessTokenConverter accessTokenConverter;

    /**
     * 配置客户端支持的服务
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                //为了演示方便,使用内存模式，后续可存储在数据库中
                .inMemory()
                //客户端ID
                .withClient("c1")
                //客户端密钥
                .secret(new BCryptPasswordEncoder().encode("secret"))
                //资源列表
                .resourceIds("res1")
                //允许的授权类型
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
                //允许的授权范围
                .scopes("all")
                //是否自动批准
                .autoApprove(true)
                //回调地址
                .redirectUris("http://www.baidu.com");
    }

    /**
     * 配置令牌访问端点和令牌服务
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager) //用户名密码模式需要
                .authorizationCodeServices(authorizationCodeServices) //授权码模式需要
                .tokenServices(tokenServices()) //令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);//允许 post 提交
    }

    /**
     * 令牌访问端点的安全策略
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()") // 提供公钥密钥端点
                .checkTokenAccess("permitAll()") //token 检查
                .allowFormAuthenticationForClients(); //允许表单认证
    }


    /**
     * 令牌管理服务
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore);
        services.setAccessTokenValiditySeconds(7200); //令牌默认有效期 : 2小时
        services.setRefreshTokenValiditySeconds(259200); //刷新令牌默认有效期 : 3天

        //设置令牌增加服务
        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        services.setTokenEnhancer(chain);

        return services;
    }

}
