package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserResponse register(@Valid RegisterRequest request) {
        if(repository.existsByEmail(request.getEmail())){
            User exitingUser= repository.findByEmail(request.getEmail());
            UserResponse userResponse=new UserResponse();
            userResponse.setEmail(exitingUser.getEmail());
            userResponse.setId(exitingUser.getId());
            userResponse.setKeycloakId(exitingUser.getKeycloakId());
            userResponse.setPassword(exitingUser.getPassword());
            userResponse.setFirstName(exitingUser.getFirstName());
            userResponse.setLastName(exitingUser.getLastName());
            userResponse.setCreatedAt(exitingUser.getCreatedAt());
            userResponse.setUpdatedAt(exitingUser.getUpdatedAt());

            return userResponse;
        }

        User user=new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setKeycloakId(request.getKeycloakId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser= repository.save(user);
        UserResponse userResponse=new UserResponse();
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setId(savedUser.getId());
        userResponse.setKeycloakId(savedUser.getKeycloakId());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());

        return userResponse;
    }

    public UserResponse getUserProfile(String userId) {
        User user=repository.findById(userId).orElseThrow(()-> new RuntimeException("User Not Found"));
        UserResponse userResponse=new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());

        return userResponse;
    }

    public Boolean existByUserId(String userId) {
        log.info("Calling User Validation API for userId : {}",userId);
        return repository.existsByKeycloakId(userId);
    }
}
