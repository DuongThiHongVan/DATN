package com.fastshop.net;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fastshop.net.model.Account;
import com.fastshop.net.service.AccountService;


@Configuration
@EnableWebSecurity
@EnableAutoConfiguration  // đã them cho nay 9/9/2022
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{
    @Autowired
	AccountService accountService;

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {
				Account user = accountService.findById(username);
				String password = getPasswordEncoder().encode(user.getPassword());
				String[] roles = user.getAuthorities().stream().map(er -> er.getRole().getId())
						.collect(Collectors.toList()).toArray(new String[0]);
				return User.withUsername(username).password(password).roles(roles).build();

			} catch (NoSuchElementException e) {
				throw new UsernameNotFoundException(username + "not found!");
			}
		});
	}


	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        http.authorizeRequests()
            .antMatchers("/rest/authorities").hasAnyRole("ADMIN")
            .anyRequest().permitAll();
        
        http.oauth2Login()
			.loginPage("/google/logout")
			.defaultSuccessUrl("/google/login/success", false)
			.failureUrl("/google/logout")
			.authorizationEndpoint()
			.baseUri("/oauth2/authorization");
		
		return http.build();
	}


	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
}
