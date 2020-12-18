package com.demo.bookstore.book.web.servlet;

import com.demo.bookstore.book.domain.Book;
import com.demo.bookstore.book.service.BookException;
import com.demo.bookstore.book.service.BookService;
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

@Controller
@RequestMapping("/BookServlet")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/findAll")
    public String findAll(HttpServletRequest request) {
            List<Book> bookList=bookService.findAll();
            request.setAttribute("booklist",bookList);
        return "jsps/book/list";
    }


    @GetMapping("/findByCategory/{cid}")
    public String findByCategory(@PathVariable String cid, HttpServletRequest request)  {
            List<Book> bookList=bookService.findByCategory(cid);
            request.setAttribute("booklist",bookList);

        return "jsps/book/list";
    }

    @GetMapping("/load/{bid}")
    public String load(@PathVariable String bid, HttpServletRequest request) {
        Book book=bookService.load(bid);
        request.setAttribute("book",book);
        return "jsps/book/desc";
    }
}
