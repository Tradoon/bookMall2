package com.tradoon.bookMall.service.impl;

import com.tradoon.bookMall.api.RedisPreKey;
import com.tradoon.bookMall.bo.AdminUserDetails;
import com.tradoon.bookMall.exception.TokenException;
import com.tradoon.bookMall.service.RedisService;
import com.tradoon.bookMall.service.UmsAdminService;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.api.ResultCode;
import com.tradoon.bookMall.dao.UmsAdminMapper;
import com.tradoon.bookMall.model.UmsAdmin;
import com.tradoon.bookMall.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * author:tradoon
 * desciption:
 * date:
 */
@Service
@Slf4j
public class UmsAdminServiceImpl implements UmsAdminService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisService redis;

//    @Autowired
//    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UmsAdminMapper adminMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public CommonResult<UmsAdmin> register(UmsAdmin user) {
        user.setCreateTime(new Date());
        user.setLoginTime(new Date());
        if(StringUtils.isNotBlank(user.getUsername())){
            UmsAdmin example = adminMapper.selectByNameAndKey(user.getUsername(), null);
            if(example!=null){
                //todo 封装类来判断对象为空/对象每个属性都为空
                //抛出用户名已经存在的错return null;
                //前端拿这个对象去干嘛?
                return CommonResult.failed(ResultCode.NameRepeated.getCode(),
                        ResultCode.NameRepeated.getMessage());
            }
            String pwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(pwd);
            user.setLoginTime(new Date());
            user.setCreateTime(new Date());
            adminMapper.insertAdmin(user);
        }
        return CommonResult.success(user);
    }

    @Override
    public CommonResult login(UmsAdmin user) {
//    登录成功
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            CommonResult.failed(ResultCode.PASSWORDERRO.getCode(),ResultCode.PASSWORDERRO.getMessage());
        }
        AdminUserDetails principal = (AdminUserDetails) authenticate.getPrincipal();
        String jwt = JwtUtil.createJWT(String.valueOf(principal.getUmsAdmin().getId()));
//        String jwt=jwtTokenUtil.generateToken(principal);
        HashMap<String, String> tokenMap = new HashMap<>();
        //信息存入redis，啥时候取呢
        String redisKey= RedisPreKey.ADMIN_PREKEY.getPreKey();
        redis.set(redisKey+principal.getUmsAdmin().getId(),principal);
        tokenMap.put("token",jwt);

        return CommonResult.success(tokenMap);


//        获取token进行计算返回jwt

//    登录失败
//        对应的信息
    }

    @Override
    public CommonResult logout() {
        // 获取securityContextHolder 的值
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        AdminUserDetails principal = (AdminUserDetails) authentication.getPrincipal();
        //获取user id
        String key = RedisPreKey.ADMIN_PREKEY.getPreKey() + principal.getUmsAdmin().getId();
        if(StringUtils.isBlank(key)){
            log.info("从securityContext中获取的数据为空");
            throw  new TokenException("用户无相关信息");
        }
        //从redis中删除该id的数据
        redis.del(key);
        return CommonResult.success("登出成功");

    }

    @Override
    public CommonResult<UmsAdmin> getItem(Long id) {
        if(id!=null){
            UmsAdmin admin = adminMapper.selectByPrimaryKey(id);
            return CommonResult.success(admin);
        }
        return CommonResult.failed();
    }

    @Override
    public void update(Long id,UmsAdmin admin) {
        if (admin != null&&id!=null) {
            admin.setId(id);
            //todo 前端是不是传过来的admin里面没有id，有的话根本没必要set
            int num = adminMapper.updateByPrimaryKeySelective(admin);
            return ;
        }
    }

    //应户名或者姓名分页获取用户列表

    //修改指定用户的密码/重置密码
    //删除只当用户的信息/status置为0
    //修改账号状态

}
