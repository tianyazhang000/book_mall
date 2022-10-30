package com.web;

import com.pojo.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import com.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 销毁登录信息
        req.getSession().invalidate();
        //2 重定向到首页
        resp.sendRedirect(req.getContextPath());
    }

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            //1 获取请求参数
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            //2 调用Service处理业务
            User loginUser = userService.login(new User(null, username, password, null));

            if(loginUser == null){
                if(userService.existsUsername(username)){

                    req.setAttribute("msg","用户名或密码错误！");
                    req.setAttribute("username",username);

                    req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
                }
            }else{
                req.getSession().setAttribute("user",loginUser);
                req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
            }
        }

    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取Session中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        //1 获取请求参数
//      String username = req.getParameter("username");
//      String password = req.getParameter("password");
//      String email = req.getParameter("email");
        String code = req.getParameter("code");

        User user = (User) WebUtils.copyParamToBean(req.getParameterMap(), new User());

        //2 检查验证码是否正确
        if (token != null && token.equalsIgnoreCase(code)) {
            //如果正确
            //3 检查用户名是否可用
            if (userService.existsUsername(user.getUsername())) {
                System.out.println("用户名" + user.getUsername() + "已存在");
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            } else {
                userService.registUser(new User(null, user.getUsername(), user.getPassword(), user.getEmail()));
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }
        } else {
            //如果不正确跳回注册页面
            System.out.println("验证码" + code + "错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }
    }
}
