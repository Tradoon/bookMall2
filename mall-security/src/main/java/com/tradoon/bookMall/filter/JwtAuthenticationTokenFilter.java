package com.tradoon.bookMall.filter;

import com.tradoon.bookMall.bo.AdminUserDetails;
import com.tradoon.bookMall.exception.TokenException;
import com.tradoon.bookMall.service.RedisService;
import com.tradoon.bookMall.utils.JwtUtil;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * author:tradoon
 * desciption:jwt 过滤器
 * date:2022/ / /
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    RedisService redis;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = httpServletRequest.getHeader("token");
        if(StringUtils.isBlank(token)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //解析token
        String subject;
        try {
             subject = JwtUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            logger.info("token 解析失败:"+e.getMessage());
            throw  new TokenException("token 解析失败");
        }

        //通过token 信息从数据库中拿到对应的用户信息
        AdminUserDetails adminPrinciple = (AdminUserDetails)redis.get("admin:id:" + subject);
        if(Objects.isNull(adminPrinciple)){
            logger.info("redis 中根据token获取的对象为空");
            throw new TokenException("用户未登录");
        }
        //将拿到的信息存入securityContext中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(adminPrinciple, null, adminPrinciple.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
