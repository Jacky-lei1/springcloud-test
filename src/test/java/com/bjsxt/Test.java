package com.bjsxt;

/**
 * @author lvyelanshan
 * @create 2019-12-05 21:38
 */
public class Test {
    public static void main(String[] args) {
        smsCode();
    }
    public static String smsCode(){
        String random=(int)((Math.random()*9+1)*100000)+"";
        System.out.println("创建的验证码为："+random);
        return random;
    }
}
