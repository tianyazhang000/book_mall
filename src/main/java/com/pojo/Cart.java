package com.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Integer,CartItem> items = new LinkedHashMap<>();

    public void addItem(CartItem cartItem){
        //先查看购物车中是否添加过此商品
//        for (CartItem item : items) {
//            if(cartItem.getId() == item.getId()){
//                updateCount(cartItem.getId(),cartItem.getCount() + 1);
//                return;
//            }
//        }
//        items.add(cartItem);
        CartItem item = items.get(cartItem.getId());
        if(item == null){
            items.put(cartItem.getId(),cartItem);
        } else {
            item.setCount(item.getCount() + 1);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
    }
    public void deleteItem(Integer id){
        items.remove(id);
    }
    public void clear(){
        items.clear();
    }
    public void updateCount(Integer id,Integer count){
        CartItem item = items.get(id);
        if(item != null){
            item.setCount(count);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
    }

    public Integer getTotalCount() {
        Integer totalCount = 0;
//        for(Map.Entry<Integer,CartItem> entry : items.entrySet()){
//            totalCount += entry.getValue().getCount();
//        }
        for (CartItem value : items.values()) {
            totalCount += value.getCount();
        }
        return totalCount;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItem value : items.values()) {
            totalPrice = totalPrice.add(value.getPrice());
        }
        return totalPrice;
    }

    public Map<Integer,CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer,CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
