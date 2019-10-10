package com.sunkuet02.micro.authentication.service;

import com.sunkuet02.micro.authentication.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    User getUser(String username, String password);
}
