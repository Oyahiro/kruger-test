package com.kruger.test.service;

import com.kruger.test.enitity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getByUsername(String username);
    boolean existsByUsername(String username);
    void save(User user);

}
