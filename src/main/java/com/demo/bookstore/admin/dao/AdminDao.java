package com.demo.bookstore.admin.dao;

import com.demo.bookstore.admin.domain.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao {

    @Select("select * from t_admin where adminname=#{adminname}")
    Admin findByadminname(@Param("adminname") String adminname);
}
