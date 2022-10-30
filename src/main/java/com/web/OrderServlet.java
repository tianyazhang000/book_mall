package com.web;

import com.pojo.Cart;
import com.pojo.User;
import com.service.OrderService;
import com.service.impl.OrderServiceImpl;
import com.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderServlet extends BaseServlet{

    private OrderService orderService = new OrderServiceImpl();

    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //先获取Cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //获取UserId
        User loginUser = (User) req.getSession().getAttribute("user");
        //若用户未登录，则要求用户先登录
        if(loginUser == null){
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            return;
        }
        Integer userId = loginUser.getId();
        //调用orderService.createOrder(Cart,UserId);生成订单
        String orderId = orderService.createOrder(cart, userId);
//        req.setAttribute("orderId",orderId);
        //请求转发到结算页面
//        req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req,resp);
        req.getSession().setAttribute("orderId",orderId);
        resp.sendRedirect(req.getContextPath() + "/pages/cart/checkout.jsp");
    }
}
