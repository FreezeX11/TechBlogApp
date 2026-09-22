package com.techBloggingApp.Backend.Mapper;

import com.techBloggingApp.Backend.Entity.User;
import com.techBloggingApp.Backend.Payload.Request.UserCreationRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserCreationRequest userCreationRequest) {
        User user = new User();

        user.setEmail(userCreationRequest.getEmail());
        user.setUsername(userCreationRequest.getUsername());
        user.setPassword(userCreationRequest.getPassword());

        return user;
    }
}
