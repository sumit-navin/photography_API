package com.navigraph.photobook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.navigraph.photobook.model.AuthRequest;
import com.navigraph.photobook.model.AuthResponse;
import com.navigraph.photobook.service.CustomUserDetailService;
import com.navigraph.photobook.util.JwtUtil;

@Controller
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	CustomUserDetailService customUserDetailService;

	@Autowired
	JwtUtil jwtUtil;

	@RequestMapping(value = "/authToken", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest request) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException bce) {
			throw new Exception("Incorrect USername Password", bce);
		}
		final UserDetails userDetails = customUserDetailService.loadUserByUsername(request.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails); 
		return ResponseEntity.ok(new AuthResponse(jwt));
	}
}
