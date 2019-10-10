package com.sunkuet02.micro.authentication.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunkuet02.micro.authentication.dto.request.UserCreationRequest;
import com.sunkuet02.micro.authentication.dto.response.UserCreationResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createUserAccountTest() throws Exception {
        UserCreationRequest creationRequest = new UserCreationRequest("username", "password");
        MvcResult result = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(creationRequest)))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        UserCreationResponse response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<UserCreationResponse>() {
        });

        Assert.assertEquals(true, response.getResult());
    }


}