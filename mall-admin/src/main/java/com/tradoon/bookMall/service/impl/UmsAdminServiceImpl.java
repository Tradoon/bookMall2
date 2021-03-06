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
                //todo ??????????????????????????????/???????????????????????????
                //?????????????????????????????????return null;
                //???????????????????????????????
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
//    ????????????
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
        //????????????redis??????????????????
        String redisKey= RedisPreKey.ADMIN_PREKEY.getPreKey();
        redis.set(redisKey+principal.getUmsAdmin().getId(),principal);
        tokenMap.put("token",jwt);

        return CommonResult.success(tokenMap);


//        ??????token??????????????????jwt

//    ????????????
//        ???????????????
    }

    @Override
    public CommonResult logout() {
        // ??????securityContextHolder ??????
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        AdminUserDetails principal = (AdminUserDetails) authentication.getPrincipal();
        //??????user id
        String key = RedisPreKey.ADMIN_PREKEY.getPreKey() + principal.getUmsAdmin().getId();
        if(StringUtils.isBlank(key)){
            log.info("???securityContext????????????????????????");
            throw  new TokenException("?????????????????????");
        }
        //???redis????????????id?????????
        redis.del(key);
        return CommonResult.success("????????????");

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
//        menu ???roles
        
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
                    log.info("token ???????????????"+e.getMessage());
                }

            else {
                AdminUserDetails principal =
                        (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if(!Objects.isNull(principal)){
                    subject= String.valueOf(principal.getUmsAdmin().getId());
                }else{
                    log.info("token ???securityContext??????principle????????????");
                }
            }
      if(!Objects.isNull(subject)) {
          String redisKey = RedisPreKey.ADMIN_PREKEY.getPreKey() + subject;
          AdminUserDetails adminUserDetails = (AdminUserDetails) redis.get(redisKey);
          if (Objects.isNull(adminUserDetails)) {
              return CommonResult.failed("???????????????");
          }
          String newToken = JwtUtil.createJWT(subject);
          HashMap<String, String> map = new HashMap<>();
          map.put("token", newToken);
          return CommonResult.success(map);
      }
      return CommonResult.failed("token????????????");
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
        // ?????????????????????
        if(!passwordEncoder.matches(udAdmiPwd.getOldPassword(),umsAdmin.getPassword())){
            return CommonResult.failed(ResultCode.PASSWORDERRO.getCode(), ResultCode.PASSWORDERRO.getMessage());
        }
        //????????????????????????
        if(passwordEncoder.matches(udAdmiPwd.getOldPassword(),newWord)){
            return CommonResult.failed(ResultCode.PASSWORDERRO.getCode(),ResultCode.PASSWORDREPEAT.getMessage());
        }
        //???????????????
        UmsAdmin admin=new UmsAdmin();
        if(StringUtils.isNotBlank(udAdmiPwd.getUsername())){
            admin.setUsername(udAdmiPwd.getUsername());
        }
        if(StringUtils.isNotBlank(udAdmiPwd.getNewPassword())){
            admin.setPassword(newWord);
        }
        int updateCol=adminMapper.updateByPrimaryKeySelective(admin);
        return CommonResult.success(updateCol,"????????????");
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
        //todo ?????????????????????????????????

        // ???????????????????????????
        if(adminId!=null&&roleIds.size()!=0){
            umsAdminRoleRelationDao.deleteByExample(adminId);

            //?????????????????????
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
        // ??????securityContext??????????????????
        List<UmsResource> resourceList = umsAdminRoleRelationDao.getResourceList(adminId);
        List<GrantedAuthority>   authorities=
                resourceList.stream().map(data->new SimpleGrantedAuthority(data.getValue())).collect(Collectors.toList());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //todo ???????????????????????????????????????????????????????????????????????????SecurityCONTEXT?????????????????????????????????
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);

       SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        UmsAdmin umsAdmin = adminMapper.selectByPrimaryKey(adminId);
        AdminUserDetails adminUserDetails = new AdminUserDetails(umsAdmin, resourceList);
        //redis ????????????????????????
        redis.update(RedisPreKey.ADMIN_ROLE_RESOURCE.getPreKey()+adminId, resourceList);
        redis.update(RedisPreKey.ADMIN_PREKEY.getPreKey()+adminId,adminUserDetails);

        return CommonResult.success(redis.get(RedisPreKey.ADMIN_PREKEY.getPreKey()+adminId));
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
            //todo ???????????????????????????admin????????????id???????????????????????????set
            int num = adminMapper.updateByPrimaryKeySelective(admin);
            return ;
        }
    }

    //?????????????????????????????????????????????

    //???????????????????????????/????????????
    //???????????????????????????/status??????0
    //??????????????????

}
