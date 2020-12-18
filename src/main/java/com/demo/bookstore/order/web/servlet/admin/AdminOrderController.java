package com.demo.bookstore.order.web.servlet.admin;

import cn.itcast.servlet.BaseServlet;
import com.demo.bookstore.order.domain.Order;
import com.demo.bookstore.order.service.OrderException;
import com.demo.bookstore.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebServlet("/admin/AdminOrderServlet")
@Controller
@RequestMapping("/admin/AdminOrderServlet")
public class AdminOrderController  {
    @Autowired
    private OrderService orderService;

    @GetMapping("/findAll")
    public String findAll(HttpServletRequest request) {
        List<Order> orderList = null;
        orderList = orderService.findAll();
        request.setAttribute("orderList",orderList);
        return "adminjsps/admin/order/list";
    }

    @RequestMapping("/findByStatus/{status}")
    public String findByStatus(@PathVariable String status,HttpServletRequest request) {
        List<Order> orderList= null;
        orderList = orderService.findByStatus(Integer.parseInt(status));
        request.setAttribute("orderList",orderList);
        return "adminjsps/admin/order/list";
    }

    @GetMapping("/load/{oid}")
    public String load(@PathVariable String oid,HttpServletRequest request) {
        Order order = null;
        order = orderService.load(oid);
        request.setAttribute("order",order);
        return "adminjsps/admin/order/desc";
    }

    @RequestMapping("/cancel/{oid}")
    public String cancel(@PathVariable String oid, HttpServletRequest request)  {
        System.out.println("oid:"+oid);
        int state=-1;
        state=orderService.findState(oid);
        if(state!=1){
            request.setAttribute("adminmsg","訂單狀態錯誤,不能取消");
            return "adminjsps/msg";
        }
        orderService.updateState(oid,5);
        request.setAttribute("adminmsg","你的訂單已取消");
        return "adminjsps/msg";
    }

    @RequestMapping("/deliver/{oid}")
    public String deliver(@PathVariable String oid,HttpServletRequest request)  {
        int state=-1;
        state=orderService.findState(oid);
        if(state!=2){
            request.setAttribute("adminmsg","訂單狀態錯誤");
            return "adminjsps/msg";
        }
        orderService.updateState(oid,3);
        request.setAttribute("adminmsg","你的訂單已發貨,請查看物流");
        return "adminjsps/msg";
    }
}
