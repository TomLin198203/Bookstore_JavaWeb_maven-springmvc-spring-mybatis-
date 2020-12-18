package com.demo.bookstore.category.service;

import cn.itcast.commons.CommonUtils;
import com.demo.bookstore.book.dao.BookDao;
import com.demo.bookstore.category.dao.CategoryDao;
import com.demo.bookstore.category.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private BookDao bookDao;

    public List<Category> findAll() {

        return categoryDao.findAll();

        /*
        InputStream in= null;
        try {
            in = Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
            SqlSessionFactory factory=builder.build(in);
            SqlSession sqlSession = factory.openSession(true);
            categoryDao = sqlSession.getMapper(CategoryDao.class);
            List<Category> all = categoryDao.findAll();
            sqlSession.close();
            in.close();
            return all;
        } catch (IOException e) {
            throw new CategoryException("找不到配置文件");
        }

         */

    }

    public void delete(String cid) throws CategoryException{

        int count = bookDao.getCountByCid(cid);
        System.out.println("bookDao.getCountByCid:"+cid+", count:"+count);
        if(count>0) throw new CategoryException("該類下還有圖書,不能刪除該類");
        categoryDao.deleteByCid(cid);
        /*
        InputStream in= null;
        try {
            in = Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
            SqlSessionFactory factory=builder.build(in);
            SqlSession sqlSession = factory.openSession(true);
            bookDao = sqlSession.getMapper(IBookDao.class);
            categoryDao = sqlSession.getMapper(CategoryDao.class);
            int count = bookDao.getCountByCid(cid);
            if(count>0) throw new CategoryException("該類下還有圖書,不能刪除該類");
            categoryDao.deleteByCid(cid);
            sqlSession.close();
            in.close();

        } catch (IOException e) {
            throw new CategoryException("找不到配置文件");
        }

         */
    }

    public Category load(String cid)  {

        return categoryDao.load(cid);

        /*
        InputStream in= null;
        try {
            in = Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
            SqlSessionFactory factory=builder.build(in);
            SqlSession sqlSession = factory.openSession(true);
            categoryDao = sqlSession.getMapper(CategoryDao.class);
            Category category = categoryDao.load(cid);
            sqlSession.close();
            in.close();
            return category;

        } catch (IOException e) {
            throw new CategoryException("找不到配置文件");
        }

         */
    }

    public void updateCname(Category category) {
        categoryDao.updateCname(category);
        /*
        InputStream in= null;
        try {
            in = Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
            SqlSessionFactory factory=builder.build(in);
            SqlSession sqlSession = factory.openSession(true);
            categoryDao = sqlSession.getMapper(CategoryDao.class);
            categoryDao.updateCname(category);
            sqlSession.close();
            in.close();
        } catch (IOException e) {
            throw new CategoryException("找不到配置文件");
        }

         */
    }

    @Override
    public void add(String cname) throws CategoryException {
        Category category = categoryDao.findBycname(cname);
        System.out.println("category:"+category);
        if(category!= null) {
            if(category.isDel()){
                categoryDao.updateStatus(cname,false);
                return;
            }
            else {
                throw new CategoryException("分類名稱已存在,不能添加");
            }
        }
        Category category_add=new Category();
        category_add.setCname(cname);
        category_add.setCid(CommonUtils.uuid());
        category_add.setDel(false);
        categoryDao.add(category_add);
    }
}
