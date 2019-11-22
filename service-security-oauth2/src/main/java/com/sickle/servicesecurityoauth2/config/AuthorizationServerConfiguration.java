package com.sickle.servicesecurityoauth2.config;

import com.sickle.servicesecurityoauth2.exception.MyWebResponseExceptionTranslator;
import com.sickle.servicesecurityoauth2.helper.RedisTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.Arrays;

/**
 * @program: springcloudDemo
 * @description: oauth2 https://projects.spring.io/spring-security-oauth/docs/oauth2.html
 * @author: sickle
 * @create: 2019-11-21 10:52
 **/
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private MyTokenEnhancer tokenEnhancer;
    @Autowired
    private MyWebResponseExceptionTranslator myWebResponseExceptionTranslator;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    @Qualifier("tokenStoreBean")
    private TokenStore tokenStore;


    AuthenticationManager authenticationManager;
    KeyPair keyPair;
    boolean jwtEnabled;

    public AuthorizationServerConfiguration(
            AuthenticationConfiguration authenticationConfiguration,
            KeyPair keyPair,
            @Value("${security.oauth2.authorizationserver.jwt.enabled:true}") boolean jwtEnabled) throws Exception {

        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.keyPair = keyPair;
        this.jwtEnabled = jwtEnabled;
    }

    /**
     * ClientDetailsServiceConfigurer：用来配置客户端详情服务（ClientDetailsService）
     * 客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     *
     * @param clients
     * @return
     * @author: sickle
     * @Date: 2019/11/21 2:32 下午
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //把数据放在缓存里
        clients.inMemory()
                .withClient("reader")
                .authorizedGrantTypes("password")
                .secret("{noop}secret")
                .scopes("message:read")
                .accessTokenValiditySeconds(600_000_000)
                .refreshTokenValiditySeconds(600_000_000)
                .and()
                .withClient("writer")
                .authorities("writer")
                .authorizedGrantTypes("client_credentials", "password", "refresh_token")
                .secret("{noop}secret")
                .scopes("message:write")
                .accessTokenValiditySeconds(600_000_000)
                .and()
                .withClient("noscopes")
                .authorizedGrantTypes("password")
                .secret("{noop}secret")
                .scopes("none")
                .accessTokenValiditySeconds(600_000_000);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, accessTokenConverter()));

        endpoints
                //自定义异常处理
                .exceptionTranslator(myWebResponseExceptionTranslator)
                .authenticationManager(this.authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter())
                .userDetailsService(user -> {
//                    UserDo user = userService.getTenantUser(email);
//                    if (user == null) {
//                        throw new CustomOauthException(CustomOauthException.Type.EmailNotFound, "UsernameNotFoundException");
//                    }
//                    List<SimpleGrantedAuthority> authorities = userService.getRoles(user.getTenant().getId(), email);
                    return User.withUsername("user").password("password").authorities("USER").build();
                }).tokenEnhancer(tokenEnhancerChain);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
        security.checkTokenAccess("permitAll()");
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        //自定义非对称加密
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
        converter.setKeyPair(this.keyPair);
        converter.setAccessTokenConverter(new DefaultAccessTokenConverter());
        return converter;
//        官方配置
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setKeyPair(this.keyPair);
//
//        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
//        accessTokenConverter.setUserTokenConverter(new SubjectAttributeUserTokenConverter());
//        converter.setAccessTokenConverter(accessTokenConverter);
//
//        return converter;
    }

    @Bean
    public TokenStore tokenStoreBean() {
        return new RedisTokenHelper(redisConnectionFactory);
    }
}
