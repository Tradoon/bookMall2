package com.tradoon.bookMall.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.RedisPreKey;
import com.tradoon.bookMall.bo.AdminUserDetails;
import com.tradoon.bookMall.dao.UmsAdminRoleRelationDao;
import com.tradoon.bookMall.dao.UmsRoleDao;
import com.tradoon.bookMall.dao.umsAdminRoleRelationMapper;
import com.tradoon.bookMall.dto.UmsRoleRelationDto;
import com.tradoon.bookMall.dto.UpdateAdminPasswordParam;
import com.tradoon.bookMall.exception.TokenException;
import com.tradoon.bookMall.model.UmsMenu;
import com.tradoon.bookMall.model.UmsResource;
import com.tradoon.bookMall.model.UmsRole;
import com.tradoon.bookMall.service.RedisService;
import com.tradoon.bookMall.service.UmsAdminService;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.api.ResultCode;
import com.tradoon.bookMall.dao.UmsAdminMapper;
import com.tradoon.bookMall.model.UmsAdmin;
import com.tradoon.bookMall.utils.JwtUtil;
import com.tradoon.bookMall.utils.SnowflakeConfig;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * author:tradoon
 * desciption:
 * date:
 */
@Service
@Slf4j
public class UmsAdminServiceImpl implements UmsAdminService {
    @Autowired
    SnowflakeConfig snowflakeConfig;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UmsRoleDao umsRoleDao;

    @Autowired
    RedisService redis;

//    @Autowired
//    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UmsAdminRoleRelationDao umsAdminRoleRelationDao;

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
    public CommonResult<Map<String, Object>> getAdminInfo() {
        AdminUserDetails adminInfo = (AdminUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        Map<String,Object> data=new HashMap<>();
        UmsAdmin umsAdmin = adminInfo.getUmsAdmin();
        data.put("username",umsAdmin.getUsername());
//
        data.put("icon",umsAdmin.getIcon());
//        menu 和roles
        
        if(umsAdmin.getId()!=null){
            List<UmsMenu> menuList = umsRoleDao.getMenuList(umsAdmin.getId());
            if(!menuList.isEmpty())
            data.put("menu",menuList);
            List<UmsRole> roleList = umsRoleDao.getRoleList(umsAdmin.getId());
            if(!roleList.isEmpty()){
                List<String> roles=roleList.stream().map(tmpData->tmpData.getName()).collect(Collectors.toList());
                data.put("roles",roles);
            }
        }


        return CommonResult.success(data);
    }

    @Override
    public CommonResult refreshToken(String token) {
        String subject=null;

            if(StringUtils.isNotBlank(token))

                try {
                subject = JwtUtil.parseJWT(token).getSubject();
                } catch (Exception e) {
                    log.info("token 解析异常："+e.getMessage());
                }

            else {
                AdminUserDetails principal =
                        (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if(!Objects.isNull(principal)){
                    subject= String.valueOf(principal.getUmsAdmin().getId());
                }else{
                    log.info("token ：securityContext获取principle对象为空");
                }
            }
      if(!Objects.isNull(subject)) {
          String redisKey = RedisPreKey.ADMIN_PREKEY.getPreKey() + subject;
          AdminUserDetails adminUserDetails = (AdminUserDetails) redis.get(redisKey);
          if (Objects.isNull(adminUserDetails)) {
              return CommonResult.failed("用户已注销");
          }
          String newToken = JwtUtil.createJWT(subject);
          HashMap<String, String> map = new HashMap<>();
          map.put("token", newToken);
          return CommonResult.success(map);
      }
      return CommonResult.failed("token刷新失败");
    }

    @Override
    public CommonResult<CommonPage<UmsAdmin>> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<Object> pageHelper = PageHelper.startPage(pageNum, pageSize);
        String key;
        if(StringUtils.isNotBlank(keyword)){
            key=keyword;
        }
        List<UmsAdmin> umsAdmins = adminMapper.selectByKeyWord(keyword);
        PageInfo<UmsAdmin> pageInfo = new PageInfo<>(umsAdmins);
        pageInfo.setPageNum(pageHelper.getPageNum());
        pageInfo.setPageSize(pageHelper.getPageSize());
        pageInfo.setPages(pageHelper.getPages());
        pageInfo.setTotal(pageHelper.getTotal());
        return CommonResult.success(new CommonPage<>(pageInfo));
    }

    @Override
    public CommonResult updatePassword(UpdateAdminPasswordParam udAdmiPwd) {

        UmsAdmin umsAdmin = adminMapper.selectByNameAndKey(udAdmiPwd.getUsername(), null);
        String newWord=passwordEncoder.encode(udAdmiPwd.getNewPassword());
        // 旧密码是否正确
        if(!passwordEncoder.matches(udAdmiPwd.getOldPassword(),umsAdmin.getPassword())){
            return CommonResult.failed(ResultCode.PASSWORDERRO.getCode(), ResultCode.PASSWORDERRO.getMessage());
        }
        //新旧密码是否相同
        if(passwordEncoder.matches(udAdmiPwd.getOldPassword(),newWord)){
            return CommonResult.failed(ResultCode.PASSWORDERRO.getCode(),ResultCode.PASSWORDREPEAT.getMessage());
        }
        //新密码更新
        UmsAdmin admin=new UmsAdmin();
        if(StringUtils.isNotBlank(udAdmiPwd.getUsername())){
            admin.setUsername(udAdmiPwd.getUsername());
        }
        if(StringUtils.isNotBlank(udAdmiPwd.getNewPassword())){
            admin.setPassword(newWord);
        }
        int updateCol=adminMapper.updateByPrimaryKeySelective(admin);
        return CommonResult.success(updateCol,"更改成功");
    }

    @Override
    public CommonResult changeStatus(Long id, Integer status) {
        UmsAdmin admin=new UmsAdmin();
        if(id!=null){
            admin.setId(id);
        }
        if(status!=null){
            admin.setStatus(status);
        }
        int colNum = adminMapper.updateByPrimaryKeySelective(admin);

        return CommonResult.success(colNum);
    }

    @Override
    public CommonResult updateRole(Long adminId, List<Long> roleIds) {
        //todo 将物理删除该为逻辑删除

        // 刪除原有的角色关系
        if(adminId!=null&&roleIds.size()!=0){
            umsAdminRoleRelationDao.deleteByExample(adminId);

            //增加的角色关系
            List<UmsRoleRelationDto> roleDtoList=new ArrayList<>();
            for(Long roleId:roleIds){
                UmsRoleRelationDto umsRoleRelationDto = new UmsRoleRelationDto();
                umsRoleRelationDto.setRoleId(roleId);
                umsRoleRelationDto.setId(snowflakeConfig.snowFlackId());
                umsRoleRelationDto.setAdminId(adminId);
                roleDtoList.add(umsRoleRelationDto);
            }
            if(!roleDtoList.isEmpty())
            umsAdminRoleRelationDao.insertList(roleDtoList);

        }
        // 以及securityContext中的权限信息
        List<UmsResource> resourceList = umsAdminRoleRelationDao.getResourceList(adminId);
        List<GrantedAuthority>   authorities=
                resourceList.stream().map(data->new SimpleGrantedAuthority(data.getValue())).collect(Collectors.toList());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AdminUserDetails principal = (AdminUserDetails) auth.getPrincipal();
        principal.setResourceList(resourceList);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, auth.getCredentials(), authorities);
       SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //redis 中的用户信息更改
        redis.update(RedisPreKey.ADMIN_ROLE_RESOURCE.getPreKey()+adminId, resourceList);
        redis.update(RedisPreKey.ADMIN_PREKEY.getPreKey()+principal.getUmsAdmin().getId(),principal);

        return CommonResult.success(redis.get(RedisPreKey.ADMIN_PREKEY.getPreKey()+principal.getUmsAdmin().getId()));
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
