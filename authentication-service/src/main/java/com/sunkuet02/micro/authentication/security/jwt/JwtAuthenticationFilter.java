package com.sunkuet02.micro.authentication.security.jwt;

import com.sunkuet02.micro.authentication.dto.UserDto;
import com.sunkuet02.micro.authentication.model.Role;
import com.sunkuet02.micro.authentication.model.User;
import com.sunkuet02.micro.authentication.model.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends GenericFilterBean {
    private JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = tokenProvider.resolveTokenFromRequest((HttpServletRequest) request);

        if (token != null && tokenProvider.validateToken(token)) {
            UserDto userDto = tokenProvider.parseToken(token);

            List<Role> userRoles = userDto.getRoles().stream()
                    .map(roleStr -> {
                        Role role = new Role();
                        role.setName(roleStr);
                        return role;
                    }).collect(Collectors.toList());

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword(), userRoles));
        }

        chain.doFilter(request, response);
    }
}
