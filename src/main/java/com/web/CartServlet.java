package com.web;

import com.pojo.Book;
import com.pojo.Cart;
import com.pojo.CartItem;
import com.service.BookService;
import com.service.impl.BookServiceImpl;
import com.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartServlet extends BaseServlet{

    private BookService bookService = new BookServiceImpl();

    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //获取图书信息
        Book book = bookService.queryBookById(id);
        //转换成CarItem商品项
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());
        //添加商品
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart == null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);
        req.getSession().setAttribute("lastName",cartItem.getName());
        resp.sendRedirect(req.getHeader("Referer"));
    }

    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if(cart != null){
            cart.deleteItem(id);
            //跳回页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if(cart != null){
            cart.clear();
            //跳回页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        int count = WebUtils.parseInt(req.getParameter("count"),0);
        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if(cart != null){
            cart.updateCount(id,count);
            //跳回页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }
}
