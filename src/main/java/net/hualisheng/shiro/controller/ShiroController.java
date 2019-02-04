package net.hualisheng.shiro.controller;

import net.hualisheng.shiro.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShiroController {
    @Autowired
    private ShiroService shiroService;

    /**
     * 跳转的登陆页面
     * @return
     */
    @RequestMapping("/goLogin.html")
    public String goLogin(){
        return "login.jsp";
    }
    @RequestMapping("/login.html")
    public ModelAndView login(String username,String password){
        try {
            shiroService.login(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("/error.jsp","msg",e.getMessage());
        }
        return new ModelAndView("/index.jsp");
    }
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "/login.jsp";
    }
    @RequestMapping("/do{act}.html")
    public ModelAndView get(@PathVariable String act){
        return new ModelAndView("/page.jsp","page",act);
    }

}
