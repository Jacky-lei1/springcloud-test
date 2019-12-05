package com.bjsxt.dao;

import com.bjsxt.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lvyelanshan
 * @create 2019-12-05 10:56
 */
public interface UserDao extends JpaRepository<Users,Integer> {

    /**
     * 根据姓名查找
     * @param name
     * @return
     */
    Users findUsersByUsername(String name);
}
