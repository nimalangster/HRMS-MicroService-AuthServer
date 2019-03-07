package com.microservice.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.microservice.authserver.serviceimpl.CustomUserDetailsService;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Bean
	protected AuthenticationManager getAuthenticationManager() throws Exception{
		return super.authenticationManagerBean();
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {		
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {

		 http.requestMatchers()
         .antMatchers("/login", "/oauth/authorize")
         .and()
         .authorizeRequests()
         .anyRequest()
         .authenticated()
         .and()
         .formLogin()
         .permitAll();
	    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	

}
