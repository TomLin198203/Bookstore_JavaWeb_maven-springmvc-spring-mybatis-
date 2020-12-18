package com.demo.bookstore.book.service;

import com.demo.bookstore.PageBean;
import com.demo.bookstore.book.dao.BookDao;
import com.demo.bookstore.book.domain.Book;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    public List<Book> findAll(){
        return bookDao.findAll();
        /*
        try {
            InputStream in= Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            SqlSession sqlSession = factory.openSession(true);
            bookDao = sqlSession.getMapper(BookDao.class);
            List<Book> bookList = bookDao.findAll();
            sqlSession.close();
            in.close();
            return bookList;
        } catch (IOException e) {
            //e.printStackTrace();
            throw new BookException("找不到配置文件");
        }

         */
    }

    public List<Book> findByCategory(String cid) {
        return bookDao.findByCategory(cid);
        /*
        try {
            InputStream in= Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            SqlSession sqlSession = factory.openSession(true);
            bookDao = sqlSession.getMapper(BookDao.class);
            List<Book> bookList = bookDao.findByCategory(cid);
            sqlSession.close();
            in.close();
            return bookList;
        } catch (IOException e) {
            //e.printStackTrace();
            throw new BookException("找不到配置文件");
        }

         */

    }

    public Book load(String bid)  {

        return bookDao.load(bid);
        /*
        try {
            InputStream in= Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            SqlSession sqlSession = factory.openSession(true);
            bookDao = sqlSession.getMapper(BookDao.class);
            Book book = bookDao.load(bid);
            sqlSession.close();
            in.close();
            return book;
        } catch (IOException e) {
            //e.printStackTrace();
            throw new BookException("找不到配置文件");
        }

         */
    }

    public PageBean<Book> findAll(int pc, int ps) {
        PageBean<Book> pb=new PageBean<Book>();
        pb.setPc(pc);
        pb.setPs(ps);
        int count = bookDao.getCount();
        pb.setTr(count);
        List<Book> bookList=bookDao.page((pc-1)*ps,ps);
        pb.setBeanList(bookList);
        return pb;

        /*
        try {
            PageBean<Book> pb=new PageBean<Book>();
            pb.setPc(pc);
            pb.setPs(ps);
            InputStream in= Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            SqlSession sqlSession = factory.openSession(true);
            bookDao = sqlSession.getMapper(BookDao.class);
            int count = bookDao.getCount();
            pb.setTr(count);
            List<Book> bookList=bookDao.page((pc-1)*ps,ps);
            pb.setBeanList(bookList);
            sqlSession.close();
            in.close();
            return pb;
        } catch (IOException e) {
            //e.printStackTrace();
            throw new BookException("找不到配置文件");
        }

         */
    }

    public void add(Book book) {
        bookDao.add(book);

        /*
        try {

            InputStream in= Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            SqlSession sqlSession = factory.openSession(true);
            bookDao = sqlSession.getMapper(BookDao.class);
            bookDao.add(book);
            sqlSession.close();
            in.close();
        } catch (IOException e) {
            //e.printStackTrace();
            throw new BookException("找不到配置文件");
        }

         */
    }

    public PageBean<Book> findByCategory(String cid, int pc, int ps) {
        PageBean<Book> pb=new PageBean<Book>();
        pb.setPs(ps);
        pb.setPc(pc);
        int count = bookDao.getCountByCategory(cid);
        pb.setTr(count);
        List<Book> bookList=bookDao.findBookPageByCategory(cid,(pc - 1) * ps,ps);
        pb.setBeanList(bookList);
        return pb;
        /*
        try {

            InputStream in= Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            SqlSession sqlSession = factory.openSession(true);
            bookDao = sqlSession.getMapper(BookDao.class);
            int count = bookDao.getCountByCategory(cid);
            pb.setTr(count);
            List<Book> bookList=bookDao.findBookPageByCategory(cid,(pc - 1) * ps,ps);
            pb.setBeanList(bookList);
            sqlSession.close();
            in.close();
            return pb;
        } catch (IOException e) {
            //e.printStackTrace();
            throw new BookException("找不到配置文件");
        }

         */
    }

    public void del(String bid)   {
        bookDao.del(bid);
        /*
        try {

            InputStream in= Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            SqlSession sqlSession = factory.openSession(true);
            bookDao = sqlSession.getMapper(BookDao.class);
            bookDao.del(bid);
            sqlSession.close();
            in.close();
        } catch (IOException e) {
            //e.printStackTrace();
            throw new BookException("找不到配置文件");
        }

         */
    }

    @Override
    public void edit(Book book) {
        bookDao.edit(book);
    }
}
