package com.sunkuet02.micro.authentication.service.impl;

import com.sunkuet02.micro.authentication.dao.UserRepository;
import com.sunkuet02.micro.authentication.dto.UserDto;
import com.sunkuet02.micro.authentication.dto.request.UserCreationRequest;
import com.sunkuet02.micro.authentication.model.User;
import com.sunkuet02.micro.authentication.service.UserAccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserAccountServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDto createUser(UserCreationRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .accountExpired(false)
                .accountLocked(false)
                .accountLocked(false)
                .enabled(true)
                .build();

        userRepository.save(user);

        return null;
    }
}
