package com.t226.tools;

import com.sun.mail.util.MailSSLSocketFactory;
import redis.clients.jedis.Jedis;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class Mail {
    public static int mail(String qq) throws GeneralSecurityException {
        // 收件人电子邮箱
        String to = qq;


        // 发件人电子邮箱
        String from = "2735633182@qq.com";

        // 设置邮件服务器
        properties.setProperty("mail.smtp.
        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器smtp.qq.com

        // 获取系统属性
        Properties properties = System.getProperties();
host", host);

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("2735633182@qq.com", "ngognuoriiypddcj"); //发件人邮件用户名、密码
            }
        });
        int result = (int) (Math.random() * 9000 + 999);
        Jedis jedis = new Jedis("106.12.129.166", 6379);
        jedis.auth("1234");
        jedis.sadd(qq, result + "");
        jedis.expire(qq, 70);
        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject("自助租房系统绑定邮箱验证码：");

            // 设置消息体
            message.setText("验证码为：" + result + "，感谢你使用自助租房系统，请填写完整完成验证");

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....from runoob.com");
            return 1;
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return 0;
    }
}
