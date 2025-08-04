package com.CarPool.service;

import com.CarPool.model.User;

public interface UserService {
    void saveUser(User user);
    User findById(Long id);

    User findByNameAndPassword(String name, String password);



}
