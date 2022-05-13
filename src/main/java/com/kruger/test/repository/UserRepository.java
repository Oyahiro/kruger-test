package com.kruger.test.repository;

import com.kruger.test.enitity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends AbstractEntityRepository<User> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    List<User> findByPersonIdIn(List<UUID> ids);
    Short countAllBy();

}
