package com.navigraph.photobook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.navigraph.photobook.service.UserService;

@Controller
public class BaseController {

	@Autowired
	UserService userService;

	@RequestMapping({ "/hello" })
	public String hello() {
		return "Hello World";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER", "ROLE_SUPERADMIN" })
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<?> listUsers() {
		return ResponseEntity.ok(userService.getActiveUserByUsername("Snavin"));
	}
}
