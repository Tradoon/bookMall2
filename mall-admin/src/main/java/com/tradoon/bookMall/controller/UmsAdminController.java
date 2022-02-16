package com.tradoon.bookMall.controller;

import com.tradoon.bookMall.service.UmsAdminService;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.model.UmsAdmin;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

/**
 * author:tradoon
 * desciption:用户注册/登录 相关接口
 * date:
 */
@RestController
@RequestMapping("/admin")
public class UmsAdminController {


    @Autowired
    UmsAdminService adminService;

    @ApiOperation("测试security")
    @GetMapping("/test")
    public String testSecurity(){
        return "hello";
    }

    @ApiOperation(value ="用户注册",httpMethod = "POST")
    @PostMapping("/register")
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin user){

        return  adminService.register(user);
    }

    @ApiOperation(value = "用户登录",httpMethod = "POST")
    @PostMapping("/login")
    public CommonResult login(@RequestBody UmsAdmin user){
        return adminService.login(user);
}

    @ApiOperation(value = "获取指定用户的信息",httpMethod = "GET")
    @GetMapping("/{id}")
    public CommonResult<UmsAdmin> getItem(@PathVariable Long id){
        return adminService.getItem(id);
}

    @ApiOperation(value = "修改指定用户的信息",httpMethod = "POST")
    @PostMapping("/update/{id}")
    //todo 既有PathVarible 又有requestBody 不符合常理
    public void update(@PathVariable Long id,@RequestBody UmsAdmin admin){
        adminService.update(id,admin);
        return ;
    }

}
