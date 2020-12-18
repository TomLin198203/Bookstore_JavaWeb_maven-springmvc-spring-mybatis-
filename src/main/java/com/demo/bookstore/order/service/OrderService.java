package com.demo.bookstore.order.service;


import com.demo.bookstore.order.dao.OrderDao;
import com.demo.bookstore.order.domain.Order;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface OrderService {

    public void add(Order order);

    public List<Order> myOrders(String uid);

    public Order load(String oid);

    public void confirm(String oid) throws OrderException;

    public List<Order> findAll();

    public List<Order> findByStatus(int status);

    public int findState(String oid);

    public void updateState(String oid, int i);
}
