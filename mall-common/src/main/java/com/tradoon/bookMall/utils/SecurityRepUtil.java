package com.tradoon.bookMall.utils;

import cn.hutool.json.JSONUtil;
import com.tradoon.bookMall.api.CommonResult;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
public class SecurityRepUtil<T> {
    public static <T>void getResp(HttpServletResponse httpServletResponse, T message ) throws IOException {
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().println(JSONUtil.parse(message));
        httpServletResponse.getWriter().flush();

    }

}
