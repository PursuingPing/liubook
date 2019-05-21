package com.classbooking.web.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.classbooking.web.domain.User;
import com.classbooking.web.util.RedisUtil;
import nl.bitwalker.useragentutils.UserAgent;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service("tokenServiceImpl")
public class TokenServiceImpl {

    @Autowired
    private RedisUtil redisUtil;

    public TokenServiceImpl(){
        init();
    }

    private void init(){
        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        //redisUtil = (RedisUtil) applicationContext.getBean("redisUtil");

    }

    /**
     * 生成token 设备+加密的用户名+时间+6位随机数
     * @param userAgent
     * @param userEmail
     * @return
     */
    public String generateToken(String userAgent,String userEmail){
        StringBuilder token = new StringBuilder();

        //token.append("token:");

        UserAgent userAgent1 = UserAgent.parseUserAgentString(userAgent);
        if(userAgent1.getOperatingSystem().isMobileDevice()){
            token.append("MOBILE-");
        }else{
            token.append("PC-");
        }
        //加密用户邮件
        token.append(DigestUtils.md5Hex(userEmail) + "-");

        //加时间
        token.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "-");

        //加6为随机数
        token.append(new Random().nextInt(999999 - 111111 +1) + 111111);
        System.out.println("token =>" + token.toString());
        return token.toString();
    }

    public void saveToken(String token, User user){

        if(token.startsWith("PC")){
            redisUtil.setex(token, JSONObject.toJSONString(user),2*60*60);
        }else {
            redisUtil.set(token,JSONObject.toJSONString(user));
        }
    }

}
