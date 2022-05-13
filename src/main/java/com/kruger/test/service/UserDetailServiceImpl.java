package com.kruger.test.service;

import com.kruger.test.common.Constants;
import com.kruger.test.enitity.PrimaryUser;
import com.kruger.test.enitity.User;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    private final Logger LOG = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    private final UserServiceImpl userServiceImpl;

    public UserDetailServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> properties = new HashMap<>();
        User user = userServiceImpl.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
        properties.put(Constants.CURRENT_USER, user);
        properties.put(Constants.CURRENT_USERNAME, username);
        properties.put(Constants.ROLES, user.getRoles());
        return PrimaryUser.build(user, properties);
    }

}
