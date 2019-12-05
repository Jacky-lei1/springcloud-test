package com.bjsxt.service;

import com.bjsxt.pojo.Users;

/**
 * @author lvyelanshan
 * @create 2019-12-05 10:53
 */
public interface UserService {
     Users findUser(Users users);

     void UserRegister(Users users);

     Users findUserByname(String name);

     void active(Integer id);

     void code(String phone);

}
