package com.demo.bookstore.admin.service;

import com.demo.bookstore.admin.dao.AdminDao;
import com.demo.bookstore.admin.domain.Admin;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;


public interface AdminService {

    Admin login(Admin form) throws AdminException;
    /*
    private AdminDao adminDao;
    public Admin login(Admin form) throws AdminException {
        try {
            InputStream in= Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
            SqlSessionFactory factory = builder.build(in);
            SqlSession sqlSession = factory.openSession(true);
            adminDao=sqlSession.getMapper(AdminDao.class);
            Admin admin=adminDao.findByadminname(form.getAdminname());
            System.out.println("service Admin:"+admin);
            if(admin==null)
                throw new AdminException("管理員名稱不存在");
            if(!admin.getAdminpwd().equals(form.getAdminpwd()))
                throw new AdminException("管理員密碼錯誤");
            sqlSession.close();
            in.close();
            return admin;
        } catch (IOException e) {
            throw new AdminException("找不到配置文件");
        }
    }

     */
}
