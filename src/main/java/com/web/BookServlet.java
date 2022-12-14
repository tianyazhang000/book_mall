package com.web;

import com.pojo.Book;
import com.pojo.Page;
import com.service.BookService;
import com.service.impl.BookServiceImpl;
import com.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BookServlet extends BaseServlet{

    private BookService bookService = new BookServiceImpl();

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 0);
        pageNo+=1;
        //1 获取请求的参数==封装成为Book对象
        Book book = (Book) WebUtils.copyParamToBean(req.getParameterMap(),new Book());
        //2 调用BookService.add
        bookService.addBook(book);
        //3 跳到图书列表页面
        resp.sendRedirect(req.getContextPath() + "manager/bookServlet?action=page&pageNo=" + pageNo);
    }
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 获取请求的参数id
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //2 调用bookService.deleteBookById();
        bookService.deleteBookById(id);
        //3 重新定向
        resp.sendRedirect(req.getContextPath() + "manager/bookServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }
    protected void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 获取请求的图书编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //2 调用bookService.queryBookById查询图书
        Book book = bookService.queryBookById(id);
        //3 保存图书搭配Requst中
        req.setAttribute("book",book);
        //4 请求转发到book_edit.jsp页面中
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);
    }
    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 获取请求的参数封装成book
        Book book = (Book) WebUtils.copyParamToBean(req.getParameterMap(),new Book());
        //2 调用方法
        bookService.updateBook(book);
        //3 重定向
        resp.sendRedirect(req.getContextPath() + "manager/bookServlet?action=page");
    }
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 通过BookService查询全部图书
        List<Book> books = bookService.queryBooks();
        //2 把全部图书保存到Request域中
        req.setAttribute("books",books);
        //3 请求转发到manager页面中
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 获取请求的参数pageNo和pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //2 调用BookService.page方法
        Page<Book> page = bookService.page(pageNo,pageSize);
        page.setUrl("manage/bookServlet?action=page");
        //3 保存Page对象到Request域名中
        req.setAttribute("page",page);
        //4 请求转发
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }
}
