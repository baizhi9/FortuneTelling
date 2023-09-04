package com.baizhi.controller;

import com.baizhi.model.User;
import com.baizhi.service.UserService;
import com.baizhi.utils.FateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;


@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    //调用第三方算命API
    @GetMapping("/fate")
    public String getFate(String year, String month, String day) {
        //余额不足时发送异常消息
        User currentUser = getCurrentUser();
        Integer currentBalance = currentUser.getBalance();
        if (currentBalance <= 0) {
            return "余额不足，请及时充值！";
        }
        String result = FateUtils.getHuangLi(year, month, day);
        //根据当前登录用户的id，生成对应存放结果的文件
        Integer id = currentUser.getId();
        try (PrintWriter pw = new PrintWriter(new FileWriter("C:\\Users\\86180\\Desktop\\杂七杂八的\\项目\\springboot\\spring-boot-shiro-master\\src\\main\\java\\com\\baizhi\\logs\\res" + id, true))) {
            pw.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //如果都没有异常，就扣费并返回结果
        currentUser.setBalance(currentBalance - 1);
        userService.updateUser(currentUser);
        System.out.println("算命成功！");
        return result;
    }


    //查看历史记录
    @RequestMapping("/history")
    public String getHistory() {
        int cnt = 1;
        StringBuilder sb = new StringBuilder();
        //根据当前登录用户的id，从对应文件取出结果
        User currentUser = getCurrentUser();
        Integer id = currentUser.getId();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\86180\\Desktop\\杂七杂八的\\项目\\springboot\\spring-boot-shiro-master\\src\\main\\java\\com\\baizhi\\logs\\res" + id))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append("第").append(cnt).append("条记录：").append(line).append(System.lineSeparator()).append(System.lineSeparator());
                cnt++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    //显示余额
    @RequestMapping("/balance")
    public Integer getBalance() {
        //根据当前登录用户，从数据库中取出余额的值
        User currentUser = getCurrentUser();
        return currentUser.getBalance();
    }


    //充值
    @RequestMapping("/recharge")
    public String charge(Integer money) {
        //根据当前登录用户，获取余额相关信息
        User currentUser = getCurrentUser();
        Integer modified = currentUser.getBalance() + money;
        currentUser.setBalance(modified);
        userService.updateUser(currentUser);
        System.out.println("充值成功！");
        return "充值成功！";
    }


    @RequestMapping("/login")
    public String login(String name, String pwd) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(name, pwd));
            System.out.println("登录成功！");
            return "登录成功！";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("登录失败！");
            return "登录失败！";
        }
    }


    @RequestMapping("register")
    public String register(String name, String pwd) {
        User user = new User();
        user.setName(name);
        user.setPwd(pwd);
        userService.save(user);
        System.out.println("注册成功！");
        return "注册成功！";
    }


    @RequestMapping("/logout")
    public String logout() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        System.out.println("退出成功！");
        return "成功退出";
    }


    //获取当前登录用户
    public User getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        Object principal = currentUser.getPrincipal();
        String username = principal.toString();
        return userService.getUserByName(username);
    }
}
