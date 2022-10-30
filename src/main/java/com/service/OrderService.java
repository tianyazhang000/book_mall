package com.service;

import com.dao.OrderDao;
import com.dao.impl.OrderDaoImpl;
import com.pojo.Cart;

public interface OrderService {
    public String createOrder(Cart cart,Integer userId);
}
