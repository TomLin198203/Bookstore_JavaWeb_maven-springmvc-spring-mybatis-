package com.demo.bookstore.admin.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import com.demo.bookstore.admin.domain.Admin;
import com.demo.bookstore.admin.service.AdminException;
import com.demo.bookstore.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/AdminServlet")
@Controller
@RequestMapping("/AdminServlet")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    public String login(Admin form,HttpServletRequest request)  {
        try {
            Admin admin=adminService.login(form);
            request.getSession().setAttribute("admin",admin);
            request.setAttribute("adminmsg",admin.getAdminname()+",登入成功");
        } catch (AdminException e) {
            //e.printStackTrace();
            request.setAttribute("adminmsg",e.getMessage());
            return "adminjsps/login";
        }
        return "adminjsps/admin/index";
    }
}
