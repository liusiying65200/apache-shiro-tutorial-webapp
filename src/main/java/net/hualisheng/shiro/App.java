package net.hualisheng.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("H:\\code\\apache-shiro-tutorial-webapp\\src\\main\\webapp\\WEB-INF\\shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("root", "123456");
        token.setRememberMe(true);
        try {
            subject.login(token);
            if (subject.hasRole("admin")){
                logger.error("你拥有admin角色");
            }else {
                logger.error("你没有拥有admin角色");
            }
            if (subject.hasRole("test")){
                logger.error("你拥有test角色");
            }else {
                logger.error("你没有拥有test角色");
            }
            if (subject.isPermitted("perm1")){
                logger.error("你拥有perm1权限");
            }else {
                logger.error("你没有拥有perm1权限");
            }
            if (subject.isPermitted("perm2")){
                logger.error("你拥有perm2权限");
            }else{
                logger.error("你没有拥有perm2权限");
            }
            subject.logout();
            logger.error("用户"+token.getPrincipal()+"已经成功退出");
        }catch (UnknownAccountException uae){
            logger.info(token.getPrincipal()+"账户不存在");
        } catch (IncorrectCredentialsException ice) {
            logger.info(token.getPrincipal()+"密码不正确");
        } catch (LockedAccountException lock) {
            logger.info(token.getPrincipal() + "用户账户被锁定");
        } catch (AuthenticationException authenticationException) {
            logger.info("无法识别的错误");
        }

    }
}
