package com.fastshop.net.service;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastshop.net.model.Account;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    _CookieService cookieService;
    @Autowired
    AccountService accountService;
    @Autowired
    BCryptPasswordEncoder pe;

    
    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Account account = accountService.findById(username);
            String password = account.getPassword();
            String[] roles = account.getAuthorities().stream()
                                    .map(au -> au.getRole().getId())
                                    .collect(Collectors.toList()).toArray(new String[0]);
            return User.withUsername(username).password(pe.encode(password)).roles(roles).build();
        } catch (Exception e) {
            throw new UsernameNotFoundException(username);
        }
    }


    public boolean loginFromOAuth2(OAuth2AuthenticationToken token) {
        String email = token.getPrincipal().getAttribute("email");
        String password = Long.toHexString(System.currentTimeMillis());
        UserDetails userDetails = User.withUsername(email)
                        .password(pe.encode(password)).roles("USER").build();
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        Boolean existAccount = accountService.existsByUsernameOrEmail(userDetails.getUsername(), email);
        if (existAccount) {
            Account account = accountService.findByUsernameOrEmail(userDetails.getUsername(), email);
            cookieService.add("username", account.getUsername(), 30*24*60*60);
            return true;
        }
        return false;
    }
}
