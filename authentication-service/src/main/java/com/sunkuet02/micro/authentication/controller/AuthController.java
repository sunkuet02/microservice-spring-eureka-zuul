package com.sunkuet02.micro.authentication.controller;

import com.sunkuet02.micro.authentication.dto.request.LoginRequest;
import com.sunkuet02.micro.authentication.dto.response.LoginResponse;
import com.sunkuet02.micro.authentication.security.jwt.JwtTokenProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final static Logger logger = LogManager.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = tokenProvider.createToken(request.getUsername(), request.getPassword());
        logger.info("Created token for {}", request.getUsername());
        return new LoginResponse(token);
    }


}
