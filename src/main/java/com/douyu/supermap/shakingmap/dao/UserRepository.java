package com.douyu.supermap.shakingmap.dao;

import com.douyu.supermap.shakingmap.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findByAccount(String account);

    @Modifying
    @Query("update User set avatar = :avatarUrl where id = :uid")
    int updateAvatarByUid(@Param("avatarUrl") String avatarUrl,@Param("uid")Long uid);

    Page<User> findAll(Specification<User> spec, Pageable pageable);

    User findUserByTel(String tel);
}

