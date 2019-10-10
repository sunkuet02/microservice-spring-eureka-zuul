package com.sunkuet02.micro.authentication.service;

import com.sunkuet02.micro.authentication.dto.UserDto;
import com.sunkuet02.micro.authentication.dto.request.UserCreationRequest;

public interface UserAccountService {
    UserDto createUser(UserCreationRequest request);
}
