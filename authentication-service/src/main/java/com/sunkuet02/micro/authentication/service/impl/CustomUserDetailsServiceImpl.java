package com.sunkuet02.micro.authentication.service.impl;

import com.sunkuet02.micro.authentication.dao.UserRepository;
import com.sunkuet02.micro.authentication.model.User;
import com.sunkuet02.micro.authentication.service.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found.");
        }

        return user;
    }

    @Override
    public User getUser(String username, String password) {
        User user = loadUserByUsername(username);

        return user;
    }
}
