package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.UmsMenu;
import com.tradoon.bookMall.model.UmsRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface UmsRoleDao {
    /**
     * 获取adminId 对应的menu菜单
     * @param adminId
     * @return
     */
    List<UmsMenu> getMenuList(@Param("adminId")Long adminId);

    List<UmsRole> getRoleList(@Param("adminId")Long adminId);
}
