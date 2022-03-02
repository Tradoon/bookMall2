package com.tradoon.bookMall.component;

import cn.hutool.json.JSONUtil;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.utils.SecurityRepUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        SecurityRepUtil.getResp(httpServletResponse,CommonResult.unauthorized(e.getMessage()));
    }
}
