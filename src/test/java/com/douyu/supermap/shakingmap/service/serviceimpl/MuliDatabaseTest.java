package com.douyu.supermap.shakingmap.service.serviceimpl;

import com.douyu.superman.shakingmap.parent.ShakingmapApplicationTests;
import com.douyu.supermap.shakingmap.config.mongodb.PrimaryMongoObject;
import com.douyu.supermap.shakingmap.config.mongodb.PrimaryRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MuliDatabaseTest extends ShakingmapApplicationTests {

    @Autowired
    private PrimaryRepository primaryRepository;

    @Test
    public void TestSave() {

        System.out.println("************************************************************");
        System.out.println("测试开始");
        System.out.println("************************************************************");

        this.primaryRepository
                .save(new PrimaryMongoObject(null, "第一个库的对象"));
    }
}


