package com.tradoon.bookMall;

import com.tradoon.bookMall.dao.UmsAdminMapper;
import com.tradoon.bookMall.model.UmsAdmin;
import org.apache.ibatis.annotations.Mapper;
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
@SpringBootTest
@RunWith(SpringRunner.class)
public class MbfTest {
    @Autowired
    UmsAdminMapper umsAdminMapper;
    @Test
    public void testAdminMapper(){

        System.out.println(umsAdminMapper);
        umsAdminMapper.insertAdmin(null);
        UmsAdmin test = umsAdminMapper.selectByNameAndKey("test", null);
        System.out.println(test);
    }
}
