package com.bjsxt;

import com.bjsxt.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringcloudRabbitmqJapThymeleafApplicationTests {

    /**
     * 注入发送邮件的接口
     */
    @Autowired
    private MailService mailService;

    /**
     * 测试发送文本邮件
     */
    @Test
    public void sendmail() {
        mailService.sendSimpleMail("2089634588@qq.com","主题：你好普通邮件","内容：第一封邮件");
    }
    /**
     * 测试发送html邮件
     */
    @Test
    public void sendmailHtml(){
        mailService.sendHtmlMail("2089634588@qq.com","主题：你好html邮件","<h1>内容：第一封html邮件</h1>");
    }

}
