package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.UmsAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface UmsAdminMapper {
    /**
     * 插入用户信息
     * @param user
     * @return 操作行数
     */

    int insertAdmin(UmsAdmin user);

    /**
     * 根据用户名或者密码进行查询用户信息
     * @param username
     * @param password
     * @return
     */
    UmsAdmin selectByNameAndKey(@Param("username")String username, @Param("password")String password);

    UmsAdmin selectByPrimaryKey(@Param("id")Long id);

    int updateByPrimaryKeySelective(@Param("admin")UmsAdmin admin);

    List<UmsAdmin> selectByKeyWord(@Param("keyword") String keyword);
}
