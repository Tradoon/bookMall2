package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.UmsResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface UmsAdminRoleRelationDao {
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);

}
