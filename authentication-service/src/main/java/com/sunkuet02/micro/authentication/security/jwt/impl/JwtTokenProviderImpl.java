package com.sunkuet02.micro.authentication.security.jwt.impl;

import com.sunkuet02.micro.authentication.dto.UserDto;
import com.sunkuet02.micro.authentication.model.User;
import com.sunkuet02.micro.authentication.security.jwt.JwtTokenProvider;
import com.sunkuet02.micro.authentication.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProviderImpl implements JwtTokenProvider {

    private final Long jwtTokenValidity = 30 * 60 * 1000L;
    private final String jwtSecret = "jwt-secret";
    private byte[] encodedJwtSecret;

    private final CustomUserDetailsService userDetailsService;

    public JwtTokenProviderImpl(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        encodedJwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes()).getBytes();
    }

    @Override
    public String resolveTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    @Override
    public boolean validateToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(encodedJwtSecret).parseClaimsJws(token);

        return !claimsJws.getBody().getExpiration().before(new Date());
    }

    @Override
    public String createToken(UserDto user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("roles", user.getRoles());

        Date issuedAt = new Date();
        Date validUntil = new Date(issuedAt.getTime() + jwtTokenValidity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(validUntil)
                .signWith(SignatureAlgorithm.HS256, encodedJwtSecret)
                .compact();
    }

    @Override
    public String createToken(String username, String password) {
        User user = userDetailsService.getUser(username, password);

        List<String> userRoles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toList());

        return createToken(new UserDto(username, password, userRoles));
    }

    @Override
    public UserDto parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(encodedJwtSecret)
                    .parseClaimsJws(token)
                    .getBody();

            return UserDto.builder()
                    .username(claims.getSubject())
                    .roles((List<String>) claims.get("roles"))
                    .build();
        } catch (Exception ex) {
            return null;
        }
    }
}
