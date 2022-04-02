package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.dto.UmsRoleRelationDto;
import com.tradoon.bookMall.model.UmsResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:tradoon
 * desciption: 用户-角色-资源的处理
 * date:2022/ / /
 */

@Mapper
public interface UmsAdminRoleRelationDao {
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);

    /**
     * 删除adminId 的角色关系
     * @param adminId
     * @return
     */
    int  deleteByExample(@Param("adminId") Long adminId);

    int insertList(@Param("roleList") List<UmsRoleRelationDto> roleRelations);

}
