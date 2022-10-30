package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private String orderId;
    private Integer userId;
    private Date createTime;
    private BigDecimal price;
    //0 未发货 1发货中 2签收
    private Integer status = 0;

    public Order() {
    }

    public Order(String orderId, Integer userId, Date createTime, BigDecimal price, Integer status) {
        this.orderId = orderId;
        this.userId = userId;
        this.createTime = createTime;
        this.price = price;
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", createTime=" + createTime +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
