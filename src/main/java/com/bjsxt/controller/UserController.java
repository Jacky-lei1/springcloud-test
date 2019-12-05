package com.bjsxt.controller;

import com.bjsxt.pojo.Users;
import com.bjsxt.service.MailService;
import com.bjsxt.service.UserService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lvyelanshan
 * @create 2019-12-05 10:52
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Value("${mq.config.exchange}") //在发送消息时需要知道是哪个交换器发送的消息
    private String exchange;

    //短信路由键
    @Value("${mq.config.queue.msg.rounting.key}")
    private String msgkey;

    //邮件路由键
    @Value("${mq.config.queue.email.rounting.key}")
    private String emailKey;


    @RequestMapping("/login")
    public String UserLogin(Users users,HttpServletRequest request){
        Users users1 = userService.findUser(users);
        if(users1!=null){
            StringBuilder msg = new StringBuilder()
                    .append("手机号:"+users1.getTelphone()+"\n")
                    .append("登陆时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\n")
                    .append("ip地址:"+request.getRemoteHost()+":")
                    .append("端口:"+request.getRemotePort()+"\n");
            this.amqpTemplate.convertAndSend(exchange,msgkey,msg.toString());
            if (users1.getLocked()!=1){
                return "success";
            }else {
                return "activation";
            }
        }else {
            return "failure";
        }
    }

    @RequestMapping("/register")
    @ResponseBody
    public String UserRegister(Users users, HttpServletRequest request, @PathVariable String code){
        String host=null;
        Users user = userService.findUserByname(users.getUsername());
        if(user!=null){
            return "账户已存在";
        }else {
            userService.UserRegister(users); //注册账户
            Users u = userService.findUser(users);//查询账户
            if(request.getLocalAddr().equals("0:0:0:0:0:0:0:1")){
                host="localhost";
            }else {
                host=request.getLocalAddr();
            }
            System.out.println("host地址为:"+host);
            StringBuilder msg = new StringBuilder()
                    .append("邮箱:"+u.getEmail()+"\n")
                    .append("注册时间:"+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())+"\n")
                    .append("激活地址:")
                    .append("http://")
                    .append(host)
                    .append(":")
                    .append(request.getLocalPort())
                    .append("/active")
                    .append("?id=")
                    .append(u.getId());
            //单独的激活链接
            StringBuilder url = new StringBuilder()
                    .append("http://")
                    .append(host)
                    .append(":")
                    .append(request.getLocalPort())
                    .append("/active")
                    .append("?id=")
                    .append(u.getId());
            amqpTemplate.convertAndSend(exchange,emailKey,msg.toString());
            mailService.sendSimpleMail(u.getEmail(),"激活邮件","请点击此链接完成激活"+url);
        }

        return "账户注册成功！请前往注册邮箱激活您的账户";
    }

    @RequestMapping("/active")
    @ResponseBody
    public String active(Integer id) {
        userService.active(id);
        return "激活成功！";
    }

    @RequestMapping("/code")
    public void  code(@PathVariable String telphone){
        System.out.println("获得的电话号码:"+telphone);
        userService.code(telphone);

    }
}
