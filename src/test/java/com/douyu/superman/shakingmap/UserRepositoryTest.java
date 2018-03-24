package com.douyu.superman.shakingmap;

import com.douyu.superman.shakingmap.parent.ShakingmapApplicationTests;
import com.douyu.supermap.shakingmap.common.entity.User;
import com.douyu.supermap.shakingmap.dao.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTest extends ShakingmapApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testfindOne(){
        User user = userRepository.findOne(1L);
        System.out.println(user);
    }
}
