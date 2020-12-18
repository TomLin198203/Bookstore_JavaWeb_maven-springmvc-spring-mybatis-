package com.demo.bookstore.category.dao;

import com.demo.bookstore.category.domain.Category;
import com.demo.bookstore.category.service.CategoryException;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDao {

    @Select("select * from category where del=false")
    List<Category> findAll();

    //@Delete("delete from category where cid=#{cid}")
    @Update("update category set del=true where cid=#{cid}")
    void deleteByCid(@Param("cid") String cid);

    @Select("select * from category where cid=#{cid} and del=false")
    Category load(@Param("cid") String cid);

    @Update("update category set cname=#{cname} where cid=#{cid} and del=false")
    void updateCname(Category category);

    @Update("update category set del=#{del} where cname=#{cname}")
    void updateStatus(@Param("cid") String cname,@Param("del") boolean del);

    @Select("select * from category where cname=#{cname}")
    Category findBycname(@Param("cname") String cname);

    @Insert("insert into category values(#{cid},#{cname},#{del})")
    void add(Category category) ;
}
