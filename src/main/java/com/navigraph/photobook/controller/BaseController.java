package com.navigraph.photobook.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.navigraph.photobook.model.CreateUserRequest;
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
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<?> listUsers() {
		return ResponseEntity.ok(userService.getActiveUserByUsername("Snavin"));
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER", "ROLE_SUPERADMIN" })
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public void createUser(@RequestBody CreateUserRequest request, HttpServletResponse httpServletResponse) {
		userService.createUser(request);
		httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
	}

}
