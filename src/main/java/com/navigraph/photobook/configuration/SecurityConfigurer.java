package com.navigraph.photobook.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.navigraph.photobook.filter.JwtFilter;
import com.navigraph.photobook.service.CustomUserDetailService;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Autowired
	CustomUserDetailService customUserDetailService;

	@Autowired
	JwtFilter jwtFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService);
	}

//	@Autowired
//	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService);
//		auth.authenticationProvider(authenticationProvider());
//	}
//
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//		authenticationProvider.setUserDetailsService(userDetailsService);
//		authenticationProvider.setPasswordEncoder(passwordEncoder());
//		return authenticationProvider;
//	}
//
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
//
//	@Bean
//	public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
//		PersistentTokenBasedRememberMeServices tokenBasedservice = new PersistentTokenBasedRememberMeServices(
//				"remember-me", userDetailsService, tokenRepository);
//		return tokenBasedservice;
//	}
//
//	@Bean
//	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
//		return new AuthenticationTrustResolverImpl();
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/authToken").permitAll().anyRequest().authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//		.authorizeRequests().antMatchers("/list")
//				.access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
//				.antMatchers("/", "/bloglist")
//				.access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
//				.antMatchers("/addblog/**", "/delete-blog-*").access("hasRole('ADMIN')")
//				.antMatchers("/newuser/**", "/delete-user-*").access("hasRole('ADMIN')").antMatchers("/edit-user-*")
//				.access("hasRole('ADMIN') or hasRole('DBA')").and().formLogin().loginPage("/login")
//				.loginProcessingUrl("/login").usernameParameter("ssoId").passwordParameter("password").and()
//				.rememberMe().rememberMeParameter("remember-me")
//				.tokenRepository(tokenRepository).tokenValiditySeconds(86400).and().csrf().and().exceptionHandling().accessDeniedPage("/Access_Denied")
//				;
	}

}
