package com.navigraph.photobook.dao;

import org.springframework.data.repository.CrudRepository;

import com.navigraph.photobook.entity.User;

public interface UserRepository extends CrudRepository<User,Long> {
	User findByUsernameAndIsActive(String username, boolean isActive);
	User findByUsername(String username);
}
