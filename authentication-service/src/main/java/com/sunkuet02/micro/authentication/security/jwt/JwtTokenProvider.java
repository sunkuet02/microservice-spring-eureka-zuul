package com.sunkuet02.micro.authentication.security.jwt;

import com.sunkuet02.micro.authentication.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenProvider {
    String resolveTokenFromRequest(HttpServletRequest request);

    boolean validateToken(String token);

    String createToken(UserDto user);

    String createToken(String username, String password);

    UserDto parseToken(String token);
}
