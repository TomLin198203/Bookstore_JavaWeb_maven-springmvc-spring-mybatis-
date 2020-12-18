package com.demo.bookstore.user.web.servlet;

import cn.itcast.commons.CommonUtils;
import com.demo.bookstore.cart.domain.Cart;
import com.demo.bookstore.user.domain.User;
import com.demo.bookstore.user.service.UserException;
import com.demo.bookstore.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.*;

@Controller
@RequestMapping("/UserServlet")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/login")
    public String login(User form,HttpServletRequest request){
        System.out.println("hello userservlet");
        //User form = CommonUtils.toBean(request.getParameterMap(), User.class);
        System.out.println(form);
        try {
            User user=userService.login(form);

            if(user.getState()!=1)
            {
                request.setAttribute("msg","用戶尚未激活,請到email確認確認");
                request.setAttribute("form",form);
                return "jsps/user/login";
            }

            String s=form.getUsername()+",登入成功";
            request.getSession().setAttribute("session_user",user);
            request.getSession().setAttribute("msg",s);
            request.getSession().setAttribute("cart",new Cart());
        } catch (UserException e) {
            //e.printStackTrace();
            request.setAttribute("msg",e.getMessage());
            request.setAttribute("form",form);
            return "jsps/user/login";
        }

        return "index";
    }

    @GetMapping("/quit")
    public String quit(HttpServletRequest req){
        req.getSession().invalidate();
        return "jsps/user/login";
    }

    @PostMapping("/regist")
    public String regist(User form,HttpServletRequest request) throws IOException {
        System.out.println("hello regist");
        //User form = CommonUtils.toBean(request.getParameterMap(), User.class);
        form.setUid(CommonUtils.uuid());
        form.setCode(CommonUtils.uuid()+CommonUtils.uuid());
        form.setState(0);

        Map<String,String> errors=new HashMap<String,String>();
        String username=form.getUsername();
        if(username==null|| username.trim().isEmpty())
            errors.put("username","用戶名不能為空");
        else if(username.length()<3 || username.length()>12)
            errors.put("username","用戶名長度必須在3~12之間");

        String password=form.getPassword();
        if(password==null|| password.trim().isEmpty())
            errors.put("password","密碼不能為空");
        else if(password.length()<3 || password.length()>12)
            errors.put("password","密碼長度必須在3~12之間");

        String email=form.getEmail();
        if(email==null|| email.trim().isEmpty())
            errors.put("email","email不能為空");
        else if(email.matches("\\w+@\\w\\.\\w"))
            errors.put("email","email格式不正確");

        Set<String> keyset = errors.keySet();
        Iterator<String> it = keyset.iterator();
        while (it.hasNext()){
            String key = it.next();
            String value = errors.get(key);
            System.out.println("key=" + key + ",value=" + value);
        }

        if(errors.size()>0){
            request.setAttribute("errors",errors);
            request.setAttribute("form",form);
            return "jsps/user/regist";
        }
        try {
            userService.regist(form);
        } catch (UserException e) {
            //e.printStackTrace();
            System.out.println("e.getMessage():"+e.getMessage());
            request.setAttribute("msg",e.getMessage());
            request.setAttribute("form",form);
            return "jsps/user/regist";
        }

        Properties prop=new Properties();
        InputStream in=this.getClass().getClassLoader().getResourceAsStream("email_template.properties");
        System.out.println(in);
        prop.load(new InputStreamReader(in,"utf-8") );

        String host = prop.getProperty("host");
        String port = prop.getProperty("port");
        String uname = prop.getProperty("uname");
        String pwd = prop.getProperty("pwd");
        String from = prop.getProperty("from");
        String subject = prop.getProperty("subject");
        String content = prop.getProperty("content");

        Properties prop_sendmail=new Properties();
        prop_sendmail.setProperty("mail.smtp.host",host);
        prop_sendmail.setProperty("mail.smtp.auth","true");
        prop_sendmail.setProperty("mail.smtp.starttls.enable", "true");
        prop_sendmail.put("mail.smtp.EnableSSL.enable","true");
        prop_sendmail.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        prop_sendmail.setProperty("mail.smtp.socketFactory.fallback","false");
        prop_sendmail.setProperty("mail.smtp.port",port);

        Authenticator auth=new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname,pwd);
            }
        };

        Session session = Session.getInstance(prop_sendmail, auth);
//2. MimeMessage
        MimeMessage msg=new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(from));
            //msg.setRecipients(MimeMessage.RecipientType.TO,"tm@gmail.com");
            //msg.setRecipients(MimeMessage.RecipientType.TO,"stu@yahoo.com.tw,tm@gmail.com");
            msg.setRecipients(MimeMessage.RecipientType.TO,form.getEmail());

            msg.setSubject(subject);
            content= MessageFormat.format(content,form.getCode());//重要  替換{0}
            System.out.println("content2:"+content);
            msg.setContent(content,"text/html;charset=utf-8");
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        request.setAttribute("msg","恭喜!註冊成功,請到Email激活");
        return "jsps/msg";
    }

    @RequestMapping("/active/{code}")
    public String active(@PathVariable String code, HttpServletRequest request){
        System.out.println("servlet active");
          try {
            userService.active(code);
            request.setAttribute("msg","激活成功");
        } catch (UserException e) {
            //e.printStackTrace();
            request.setAttribute("msg",e.getMessage());
            return "jsps/user/regist";
        }
        return "jsps/msg";
    }
}
