package com.demo.bookstore.category.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
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

//@WebServlet("/AdminCategoryServlet")
@Controller
@RequestMapping("/admin/AdminCategoryServlet")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/findAll")
    public String findAll(HttpServletRequest request){
        List<Category> categoryList = null;
        categoryList = categoryService.findAll();
        request.setAttribute("categoryList",categoryList);
        return "adminjsps/admin/category/list";
    }

    @RequestMapping("/delete/{cid}")
    public String delete(@PathVariable String cid,HttpServletRequest request) {
        //String cid =(String) request.getAttribute("cid");
        try {
            categoryService.delete(cid);
        } catch (CategoryException e) {
            //e.printStackTrace();
            System.out.println("adminmsg:"+e.getMessage());
            request.setAttribute("adminmsg",e.getMessage());
        }
        List<Category> list = null;
        list = categoryService.findAll();
        request.setAttribute("categoryList",list);
        return "adminjsps/admin/category/list";
    }

    @GetMapping("/editParentPre/{cid}")
    public String editParentPre(@PathVariable String cid, HttpServletRequest request) {
        //String cid =(String) request.getAttribute("cid");

        Category category=categoryService.load(cid);
        request.setAttribute("category",category);

        return "adminjsps/admin/category/edit";
    }

    @RequestMapping("/editParent")
    public String editParent(Category category) {
        //Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        System.out.println("category:"+category);
        categoryService.updateCname(category);

        //return findAll(request,response);
        return "forward:findAll";
    }

    @RequestMapping("/addParent")
    public String addParent(HttpServletRequest request)  {
        String cname = request.getParameter("cname");
        System.out.println("cname:"+cname);
        try {
            categoryService.add(cname);

        } catch (CategoryException e) {
            //e.printStackTrace();
            request.setAttribute("adminmsg",e.getMessage());
            return "forward:/adminjsps/admin/category/list.jsp";
        }
        List<Category> list = categoryService.findAll();
        request.setAttribute("categoryList",list);
        request.setAttribute("adminmsg","添加分類成功");
        return "forward:/adminjsps/admin/category/list.jsp";
    }


}
