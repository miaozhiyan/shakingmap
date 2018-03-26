package com.douyu.supermap.shakingmap.dao;

import com.douyu.supermap.shakingmap.common.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RoleRepository extends PagingAndSortingRepository<Role,Long> {
    List<Role> findRolesByUserId(Long userId);
}
