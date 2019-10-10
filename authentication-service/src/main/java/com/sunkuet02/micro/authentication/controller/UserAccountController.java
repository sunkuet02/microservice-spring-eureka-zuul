package com.sunkuet02.micro.authentication.controller;

import com.sunkuet02.micro.authentication.dto.request.UserCreationRequest;
import com.sunkuet02.micro.authentication.dto.response.UserCreationResponse;
import com.sunkuet02.micro.authentication.service.UserAccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/account")
public class UserAccountController {

    private final UserAccountService accountService;

    public UserAccountController(UserAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("")
    public UserCreationResponse createUserAccount(@RequestBody UserCreationRequest request) {
        accountService.createUser(request);

        return new UserCreationResponse(true);
    }

}
