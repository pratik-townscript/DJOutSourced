package com.townscript.demo.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.townscript.demo.model.Role;
import com.townscript.demo.model.User;
import com.townscript.demo.repository.RoleRepository;
import com.townscript.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    
	public void save(User user) {
		logger.info("Saving new user " + user);
		Role userRole = roleRepository.findByName("USER");
		user.setRole(userRole);
        userRepository.save(user);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
}
