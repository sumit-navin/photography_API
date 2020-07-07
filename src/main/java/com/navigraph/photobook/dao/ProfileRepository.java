package com.navigraph.photobook.dao;

import org.springframework.data.repository.CrudRepository;

import com.navigraph.photobook.entity.Profile;

public interface ProfileRepository extends CrudRepository<Profile,Long> {
	Profile findByType(String type);
}
