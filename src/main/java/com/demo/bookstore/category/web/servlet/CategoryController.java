package com.demo.bookstore.category.web.servlet;

import com.demo.bookstore.category.domain.Category;
import com.demo.bookstore.category.service.CategoryException;
import com.demo.bookstore.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/CategoryServlet")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/findAll")
    public String findAll(HttpServletRequest request) {
        List<Category> categoryList = null;
        categoryList = categoryService.findAll();
        request.setAttribute("categoryList",categoryList);
        return "jsps/left";
    }
}
