package com.tradoon.bookMall.service;

import com.tradoon.bookMall.bo.AdminUserDetails;
import com.tradoon.bookMall.dao.UmsAdminMapper;
import com.tradoon.bookMall.model.UmsAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Service
public class AdminDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UmsAdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

            UmsAdmin admin = adminMapper.selectByNameAndKey(s, null);
            if (Objects.isNull(admin)) {
                throw new RuntimeException("该用户不存在");

            }
            return new AdminUserDetails(admin);
        }
    }

