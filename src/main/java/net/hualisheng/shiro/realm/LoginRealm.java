package net.hualisheng.shiro.realm;

import net.hualisheng.shiro.service.ShiroService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

public class LoginRealm extends AuthorizingRealm {
    private ShiroService service;

    public void setService(ShiroService service) {
        this.service = service;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username= (String) principalCollection.fromRealm(getName()).iterator().next();
        if (username!=null){
//            setAuthenticationCache() v v
            List<String> list = service.getPermissionByUsername(username);
            if (list!=null&&!list.isEmpty()){
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                for (String s : list) {
                    info.addRole(s);
                }
                return info;
            }
        }
        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();
        if (username!=null&&"".equals(username)){
            String password = service.getPasswordByUserName(username);
            if (password!=null){
                return new SimpleAuthenticationInfo(username,password,getName());
            }
        }
        return null;
    }
}
