package com.tradoom.bookMall.service.impl;

import com.tradoom.bookMall.service.AdminService;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.api.ResultCode;
import com.tradoon.bookMall.dao.UmsAdminMapper;
import com.tradoon.bookMall.model.UmsAdmin;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * author:
 * desciption:
 * date:
 */
@Service
public class AdminServiceImpl implements AdminService {
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
            adminMapper.insertAdmin(user);
        }
        return CommonResult.success(user);
    }

    @Override
    public CommonResult<UmsAdmin> login(UmsAdmin user) {
//        if(StringUtils.isNotBlank(user.getUsername())&&StringUtils.isNotBlank(user.getPassword())){
//            UmsAdmin umsAdmin = adminMapper.selectByNameAndKey(user.getUsername(), null);
//            if(umsAdmin==null)
//                //用户名不存在
//                return CommonResult.failed(ResultCode.NOADMIN.getCode(), ResultCode.NOADMIN.getMessage());
//            if(!passwordEncoder.matches(user.getPassword(),umsAdmin.getPassword())){
//                //密码错误
//                return CommonResult.failed(ResultCode.PASSWORDERRO.getCode(), ResultCode.PASSWORDERRO.getMessage());
//
//            }else{
//               // todo 改成token
//                return CommonResult.success(umsAdmin);
//                //  new UsernamePasswordAuthenticationToken(umsAdmin,null,)
//               }
//        }
        return CommonResult.failed();
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
