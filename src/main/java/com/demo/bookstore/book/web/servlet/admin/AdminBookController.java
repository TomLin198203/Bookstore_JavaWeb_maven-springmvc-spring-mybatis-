package com.demo.bookstore.book.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import com.demo.bookstore.PageBean;
import com.demo.bookstore.book.domain.Book;
import com.demo.bookstore.book.service.BookException;
import com.demo.bookstore.book.service.BookService;
import com.demo.bookstore.category.domain.Category;
import com.demo.bookstore.category.service.CategoryException;
import com.demo.bookstore.category.service.CategoryService;
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

//@WebServlet("/AdminBookServlet")
@Controller
@RequestMapping("/admin/AdminBookServlet")
public class AdminBookController  {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/findAll")
    public String findAll(HttpServletRequest request)  {
        int ps=5;
        String pc_s = request.getParameter("pc");
        int pc;
        if (pc_s == null || pc_s.trim().isEmpty()) {
            pc=1;
        }
        else
            pc=Integer.parseInt(pc_s);

        String url=getUrl(request);
        if(url.contains("&pc=")){
            int i = url.indexOf("&pc=");
            url = url.substring(0, i);
        }

        PageBean<Book> pb = null;

        pb = bookService.findAll(pc,ps);

        pb.setUrl(url);
        request.setAttribute("pb",pb);
        return "adminjsps/admin/book/list";
    }

    @GetMapping("/load/{bid}")
    public String load(@PathVariable String bid, HttpServletRequest request){
        Book book = null;
        book = bookService.load(bid);
        List<Category> categoryList=null;
        categoryList = categoryService.findAll();
        request.setAttribute("book",book);
        request.setAttribute("categoryList",categoryList);
        return "adminjsps/admin/book/desc";
    }

    @GetMapping("/addPre")
    public String addPre(HttpServletRequest request) {
        List<Category> categoryList = null;
        categoryList = categoryService.findAll();
        request.setAttribute("categoryList",categoryList);
        return "adminjsps/admin/book/add";
    }

    private String getUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String queryString = request.getQueryString();
        return contextPath+servletPath+"?"+queryString;
    }

    private int getPc(HttpServletRequest request) {
        String pc = request.getParameter("pc");
        if(pc==null || pc.trim().isEmpty()){
            return 1;
        }
        return Integer.parseInt(pc);
    }

    @GetMapping("/findByCategory/{cid}")
    public String findByCategory(@PathVariable String cid, HttpServletRequest request) {
        int pc=getPc(request);
        int ps=5;
        String url=getUrl(request);
        if(url.contains("&pc=")){
            int i = url.indexOf("&pc=");
            url = url.substring(0, i);
        }

        System.out.println("cid:"+cid);
        PageBean<Book> pb = null;
        pb = bookService.findByCategory(cid,pc,ps);
        pb.setUrl(url);
        request.setAttribute("pb",pb);
        return "adminjsps/admin/book/list";
    }

    @RequestMapping("/del")
    public String del(HttpServletRequest request)  {
        String bid = request.getParameter("bid");
        bookService.del(bid);
        //return findAll(request,response);
        return "forward:findAll";
    }

    @GetMapping("/findCategoryAll")
    public String findCategoryAll(HttpServletRequest request){
        List<Category> list= null;

        list = categoryService.findAll();

        //System.out.println(list);
        request.setAttribute("categoryList",list);
        return "adminjsps/admin/book/left";
    }

    @RequestMapping("/edit")
    public String edit(Book book,Category category)  {
        book.setCategory(category);
        bookService.edit(book);

        //return findAll(request,response);
        return "forward:findAll";
    }
}
