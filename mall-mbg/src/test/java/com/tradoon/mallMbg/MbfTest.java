package com.tradoon.mallMbg;

import com.tradoon.bookMall.MbgApplication;
import com.tradoon.bookMall.dao.UmsAdminMapper;
import com.tradoon.bookMall.dao.UmsAdminRoleRelationDao;
import com.tradoon.bookMall.model.UmsAdmin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@SpringBootTest(classes = MbgApplication.class)
@RunWith(SpringRunner.class)
public class MbfTest {
    @Autowired
    UmsAdminMapper umsAdminMapper;
    @Autowired
    UmsAdminRoleRelationDao umsRole;

    @Test
    public void getResouce(){
        System.out.println(umsRole.getResourceList(1L));
    }

    @Test
    public void testAdminMapper(){

        System.out.println(umsAdminMapper);
        umsAdminMapper.insertAdmin(null);
        UmsAdmin test = umsAdminMapper.selectByNameAndKey("test", null);
        System.out.println(test);
    }
}
