package com.demo.bookstore.user.service;

import com.demo.bookstore.user.domain.User;

public interface UserService {
    User login(User form) throws UserException;

    void regist(User form) throws UserException;

    void active(String code) throws UserException;
}
