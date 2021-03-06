package com.tradoon.bookMall.config;

import com.tradoon.bookMall.component.RestAuthenticationEntryPoint;
import com.tradoon.bookMall.component.RestfulAccessDeniedHandler;
import com.tradoon.bookMall.filter.JwtAuthenticationTokenFilter;
import com.tradoon.bookMall.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtAuthenticationTokenFilter jwtFilter;
    @Autowired
    RestAuthenticationEntryPoint entryPoint;
    @Autowired
    RestfulAccessDeniedHandler deniedHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/admin/login").permitAll()
                .antMatchers("/admin/register").permitAll()
                //方便测试接口先对所有的接口屏蔽security
                .antMatchers("/**/*").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        //自定义认证失败/授权失败的返回
        http.exceptionHandling().
                authenticationEntryPoint(entryPoint).
                accessDeniedHandler(deniedHandler);
        //配置过滤器的位置
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        //开启跨域访问
        http.cors();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getEncode(){
    return new BCryptPasswordEncoder();
}
//    @Bean
//    public JwtTokenUtil getTokenUtil(){
//        return new JwtTokenUtil();
//    }
}
