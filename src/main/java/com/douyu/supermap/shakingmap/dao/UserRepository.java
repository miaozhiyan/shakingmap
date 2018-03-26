package com.douyu.supermap.shakingmap.dao;

import com.douyu.supermap.shakingmap.common.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findByAccount(String account);
}
