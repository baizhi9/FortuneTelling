package com.baizhi.realm;

import com.baizhi.model.User;
import com.baizhi.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String principal = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //正常情况下需要从数据库获取当前登陆用户的role和permission信息，这里为了方便固定传入
        simpleAuthorizationInfo.addRole("user");
        simpleAuthorizationInfo.addStringPermission("user:update:*");
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String) token.getPrincipal();

        User user = userService.getUserByName(username);
        if(!ObjectUtils.isEmpty(user)){
            //用户名; 密码; 注册用户时用于md5加密密码的随机盐（存放在数据库中的salt字段）; realm的name
            //返回这些信息之后交给shiro校验密码
            return new SimpleAuthenticationInfo(user.getName(), user.getPwd(), ByteSource.Util.bytes(user.getSalt()), this.getName());
        }
        return null;
    }
}