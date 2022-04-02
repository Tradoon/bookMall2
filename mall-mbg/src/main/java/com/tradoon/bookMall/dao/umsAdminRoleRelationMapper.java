package com.tradoon.bookMall.dao;

/**
 * author:tradoon
 * desciption:用户-角色处理
 * date:2022/ / /
 */

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 先闲置不用，尽量合并到UmsAdminRoleRelationDao中
 */
@Mapper
public interface umsAdminRoleRelationMapper {
    int  deleteByExample(@Param("adminId") Long adminId);
}
