package com.navigraph.photobook.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.navigraph.photobook.dao.ProfileRepository;
import com.navigraph.photobook.dao.UserRepository;
import com.navigraph.photobook.entity.Profile;
import com.navigraph.photobook.entity.ProfileType;
import com.navigraph.photobook.entity.User;
import com.navigraph.photobook.model.CreateUserRequest;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public User getActiveUserById(Long id) {
		Optional<User> optional = userRepository.findById(id);
		if (optional.isPresent()) {
			if (optional.get().isActive()) {
				return optional.get();
			}
		}
		return null;
	}

	public User getActiveUserByUsername(String username) {
		return userRepository.findByUsernameAndIsActive(username, true);
	}

	@Transactional
	public void createUser(CreateUserRequest request) {
		Set<Profile> profiles = new HashSet<Profile>();
		profiles.add(profileRepository.findByType(ProfileType.USER.getUserProfileType()));
		User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()),
				request.getFirstName(), request.getLastName(), request.getEmail(), profiles, false);
		this.userRepository.save(user);
	}
}
