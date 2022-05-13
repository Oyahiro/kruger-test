package com.kruger.test.service;

import com.kruger.test.enitity.User;
import com.kruger.test.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        Assert.notNull(username, "Username can't be null");
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        Assert.notNull(username, "Username can't be null");
        return userRepository.existsByUsername(username);
    }

    @Override
    public void save(User user) {
        Assert.notNull(user, "User can't be null");
        userRepository.save(user);
    }

}
