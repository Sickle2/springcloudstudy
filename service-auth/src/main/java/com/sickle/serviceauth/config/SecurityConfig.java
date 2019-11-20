package com.sickle.serviceauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @program: springcloudDemo
 * @description:
 * @author: sickle
 * @create: 2019-11-20 13:27
 **/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/css/**", "/index").permitAll()
                                .antMatchers("/user/**").hasRole("USER"))
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .failureUrl("/login-error"));
//                /*与之匹配的请求/CSS/*和/索引完全无障碍*/
//                .antMatchers("/css/**", "/index").permitAll()
//                /*与之匹配的请求/用户/*要求对用户进行身份验证，并且必须与用户角色*/
//                .antMatchers("/user/**").hasRole("USER")
//                .and().formLogin()
//                /*基于表单的身份验证是通过自定义登录页和故障url启用的。*/
//                .loginPage("/login").failureUrl("/login-error");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .inMemoryAuthentication()
                .withUser("user")
                .password("{noop}password")
                .roles("USER");
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder.inMemoryAuthentication().withUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER"));
//    }
}
