package net.hualisheng.shiro.service;

import net.hualisheng.shiro.dao.ShiroDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class ShiroService {
    private static Logger logger= LoggerFactory.getLogger(ShiroService.class);
    private ShiroDao shiroDao;

    public void setShiroDao(ShiroDao shiroDao) {
        this.shiroDao = shiroDao;
    }

    /**
     * 登陆
     */
    public void login(String username,String password){
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {

                subject.login(token);
                if (subject.hasRole("admin")){
                    logger.info(subject.getPrincipal()+"拥有admin角色");
                }else {
                    logger.info(subject.getPrincipal()+"没有admin角色");
                }

            }catch (UnknownAccountException uae){
                logger.error(token.getPrincipal()+"不存在");
            }catch (IncorrectCredentialsException ice){
                logger.error("密码不正确");
            }catch (LockedAccountException lae){
                logger.error("账户被锁定");
            }catch (AuthenticationException ae){
                logger.error("登陆失败");
            }


        }
    }

    /**
     * 根据用户名查询密码
     * @return
     */
    public String getPasswordByUserName(String username){
        String password = shiroDao.getPasswordByUserName(username);
        return password;
    }

    /**
     * 根据用户名查询权限信息
     * @param username
     * @return
     */
    public List<String> getPermissionByUsername(String username){
        List<String> list = shiroDao.getPermissionByUserName(username);
        return list;
    }
}
