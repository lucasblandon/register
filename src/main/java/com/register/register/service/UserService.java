package com.register.register.service;

import com.register.register.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserService extends CrudRepository<User, Integer> {
    Iterable<User> findByEmail(String email);
}
