package com.bjsxt.service.Impl;

import com.bjsxt.dao.UserDao;
import com.bjsxt.pojo.Users;
import com.bjsxt.service.UserService;
import com.bjsxt.sms.SmsSingleSender;
import com.bjsxt.sms.SmsSingleSenderResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author lvyelanshan
 * @create 2019-12-05 10:54
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 查询所有的对象
     * @param users
     * @return
     */
    @Override
    public Users findUser(Users users) {
        Optional<Users> user = userDao.findOne(Example.of(users)); //将查询条件封装成模板对象
        if(user.isPresent()){ //如果存在
            System.out.println("查询到的结果："+user.get());
            return user.get();
        }else { //不存在则返回空
            return null;
        }
    }

    /**
     * 注册用户
     * @param users
     */
    @Override
    public void UserRegister(Users users) {
        try {
            users.setLocked(1);
            System.out.println("注册的用户信息:"+users);
            userDao.save(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据姓名查找
     * @param name
     * @return
     */

    @Override
    public Users findUserByname(String name) {
        Users usersByUsername = userDao.findUsersByUsername(name);
        System.out.println("根据姓名查找:"+usersByUsername);
        return usersByUsername;
    }


    /**
     * 激活邮箱
     */
    @Override
    public void active(Integer id) {
        Optional<Users> result = userDao.findById(id);
            Users user = result.get();
            user.setLocked(0);
            userDao.save(user);
    }

    /**
     * 发送验证码
     * @param phoneNumber
     */
    @Override
    public void code(String phoneNumber) {
        try {
            //请根据实际 accesskey 和 secretkey 进行开发，以下只作为演示 sdk 使用
            String accesskey = "5de8fd8defb9a377ad62336b";
            String secretkey = "f4497efeb7644e47892fa47573df22f2";

            //初始化单发
            SmsSingleSender singleSender = new SmsSingleSender(accesskey, secretkey);
            SmsSingleSenderResult singleSenderResult;

            String random=(int)((Math.random()*9+1)*100000)+""; //随机生成6位验证码
            //普通单发,注意前面必须为【】符号包含，置于头或者尾部。
            singleSenderResult = singleSender.send(0, "86", phoneNumber, "【狗强科技】尊敬的刘再强架构师：您的验证码为："+random+"，工作人员不会索取，请勿泄漏。", "", "");
            System.out.println(singleSenderResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
