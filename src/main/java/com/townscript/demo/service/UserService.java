package com.townscript.demo.service;

import com.townscript.demo.model.User;

public interface UserService {

	void save(User user);
    User findByUsername(String username);
}
