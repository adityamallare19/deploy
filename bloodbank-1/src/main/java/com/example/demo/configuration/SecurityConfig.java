package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Autowired
	CustomSuccessHandler customSuccessHandler;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    http.csrf(c -> c.disable())

	        .authorizeHttpRequests(request -> request
	        	.requestMatchers("/admin/static/css/**", "/admin/static/plugins/**", "/admin/static/js/**", "/admin/static/images/**",  "/admin/static/webjars/**").permitAll()
	        	.requestMatchers("/user/static/**", "/user/static/js/**","/user/static/css/**").permitAll()
	        	.requestMatchers("/static/css/assets/**","static/css/vendors/**").permitAll()
	        	.requestMatchers("/adminHome").hasAuthority("ADMIN")
	            .requestMatchers("/userHome").hasAuthority("USER")
	            .requestMatchers("/", "/userLogin", "/logout", "/register", "/sendOTP/{email}", "/forgetPassword", "resetPassword").permitAll()  // Allow public access for login and logout
	            // .requestMatchers("/viewProfileDetail").hasAuthority("ADMIN")  // Allow admins to access viewProfileDetail
	            //.requestMatchers("/admin/static/**").permitAll()  // Allow access to static resources under /admin/static/**
	            .anyRequest().authenticated())  // Require authentication for all other requests

	        .formLogin(form -> form
	            .loginPage("/userLogin").loginProcessingUrl("/userLogin")
	            .successHandler(new CustomSuccessHandler()).permitAll())

	        .logout(form -> form
	            .invalidateHttpSession(true).clearAuthentication(true)
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	            .logoutSuccessUrl("/userLogin?logout").permitAll());

	    return http.build();
	}

	
	  
	
	
	@Autowired
	public void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

}
