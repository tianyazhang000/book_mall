package com.service.impl;

import com.dao.BookDao;
import com.dao.OrderDao;
import com.dao.OrderItemDao;
import com.dao.impl.BookDaoImpl;
import com.dao.impl.OrderDaoImpl;
import com.dao.impl.OrderItemDaoImpl;
import com.pojo.*;
import com.service.OrderService;

import java.util.Date;
import java.util.Map;

public class OrderServiceImpl implements OrderService{
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) {
        //订单号 时间戳
        String orderId = System.currentTimeMillis() + "" + userId;
        //创建一个订单对象
        Order order = new Order(orderId,userId,new Date(),cart.getTotalPrice(),0);
        //保存订单
        orderDao.saveOrder(order);
        //遍历购物车中每一个商品项转换成为订单项保存到数据库中
        cart.getItems().forEach((key,value) -> {
            OrderItem orderItem = new OrderItem(null,value.getName(),value.getCount(),value.getPrice(),value.getTotalPrice(),orderId);
            orderItemDao.saveOrderItem(orderItem);

            Book book = bookDao.queryBookById(value.getId());
            book.setSales(book.getSales() + value.getCount());
            book.setStock(book.getStock() - value.getCount());
            bookDao.updateBook(book);
        });
        cart.clear();
        return orderId;
    }
}
