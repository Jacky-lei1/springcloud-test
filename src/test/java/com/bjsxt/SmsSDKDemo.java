package com.bjsxt;

import com.bjsxt.sms.SmsSingleSender;
import com.bjsxt.sms.SmsSingleSenderResult;


public class SmsSDKDemo {
    public static void main(String[] args) {
        try {
            //请根据实际 accesskey 和 secretkey 进行开发，以下只作为演示 sdk 使用
            String accesskey = "5de8fd8defb9a377ad62336b";
            String secretkey = "f4497efeb7644e47892fa47573df22f2";
            //手机号码
            String phoneNumber = "15892055179";
            //初始化单发
            SmsSingleSender singleSender = new SmsSingleSender(accesskey, secretkey);
            SmsSingleSenderResult singleSenderResult;

            String random=(int)((Math.random()*9+1)*100000)+""; //随机生成6位验证码
            //普通单发,注意前面必须为【】符号包含，置于头或者尾部。
            singleSenderResult = singleSender.send(0, "86", phoneNumber, "【狗强科技】尊敬的用户：您的验证码为："+random+"，工作人员不会索取，请勿泄漏。", "", "");


           // singleSenderResult = singleSender.sendWithParam("86", phoneNumber,1,list,"","","");
            System.out.println(singleSenderResult);


            //语音验证码发送
            //SmsVoiceVerifyCodeSender smsVoiceVerifyCodeSender = new SmsVoiceVerifyCodeSender(accesskey,secretkey);
            //SmsVoiceVerifyCodeSenderResult smsVoiceVerifyCodeSenderResult = smsVoiceVerifyCodeSender.send("86",phoneNumber, "444144",2,"");
            //System.out.println(smsVoiceVerifyCodeSenderResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
