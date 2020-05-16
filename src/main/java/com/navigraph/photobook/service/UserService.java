package com.navigraph.photobook.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navigraph.photobook.dao.UserRepository;
import com.navigraph.photobook.entity.User;

@Service 
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User getActiveUserById(Long id) {
		Optional<User> optional = userRepository.findById(id);
		if(optional.isPresent()) {
			if(optional.get().isActive()) {
				return optional.get();
			}
		}
		return null;
	}
	
	public User getActiveUserByUsername(String username) {
		return  userRepository.findByUsernameAndIsActive(username, true);
	}
	
}
