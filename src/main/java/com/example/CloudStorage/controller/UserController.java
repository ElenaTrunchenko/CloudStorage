package com.example.CloudStorage.controller;

import com.example.CloudStorage.dto.TokenEntityDto;
import com.example.CloudStorage.dto.UserEntityDto;
import com.example.CloudStorage.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public TokenEntityDto login(@Valid @RequestBody UserEntityDto userEntityDto) {
        return userService.login(userEntityDto);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("auth-token") String authToken) {
        userService.logout(authToken);
    }
}