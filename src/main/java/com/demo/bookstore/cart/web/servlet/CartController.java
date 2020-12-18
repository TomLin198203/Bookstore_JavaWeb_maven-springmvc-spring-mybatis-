package com.demo.bookstore.cart.web.servlet;

import cn.itcast.servlet.BaseServlet;
import com.demo.bookstore.book.domain.Book;
import com.demo.bookstore.book.service.BookException;
import com.demo.bookstore.book.service.BookService;
import com.demo.bookstore.cart.domain.Cart;
import com.demo.bookstore.cart.domain.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/CartServlet")
@Controller
@RequestMapping("/CartServlet")
public class CartController{

    @Autowired
    private BookService bookService;

    @RequestMapping("/add")
    public String add(String bid,String count,HttpServletRequest request)  {
        //String bid = request.getParameter("bid");
        //String count = request.getParameter("count");
        //BookService bookService=new BookService();
        Book book = null;

        book = bookService.load(bid);

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        CartItem cartItem=new CartItem();
        cartItem.setCount(Integer.parseInt(count));
        cartItem.setBook(book);
        cart.add(cartItem);

        return "jsps/cart/list";
    }


    @RequestMapping("/clear")
    public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart =(Cart) request.getSession().getAttribute("cart");
        cart.clear();
        return "jsps/cart/list";
    }

    @RequestMapping("/delete/{bid}")
    public String delete(@PathVariable String bid, HttpServletRequest request)  {
        //String bid = request.getParameter("bid");
        Cart cart =(Cart) request.getSession().getAttribute("cart");
        cart.delete(bid);
        return "jsps/cart/list";
    }
}
