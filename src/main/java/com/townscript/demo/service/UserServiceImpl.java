package com.townscript.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.townscript.demo.model.Role;
import com.townscript.demo.model.User;
import com.townscript.demo.repository.RoleRepository;
import com.townscript.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    
	public void save(User user) {
		//Set<Role> roleSet = new HashSet<Role>();
		//roleSet.add();
		Role userRole = roleRepository.findByName("USER");
		System.out.println("user role retrieved is " + userRole);
		//user.setRoles(roleSet);
		user.setRole(userRole);
        userRepository.save(user);
	}

	public User findByUsername(String username) {
		//findByUserName();
		return userRepository.findByUsername(username);
	}
	
/*	public List<String> findByUserName() {
		List<String> testDistinctUsername = userRepository.findAllUsername();
		System.out.println(testDistinctUsername);
		return testDistinctUsername; 
	}*/
}
