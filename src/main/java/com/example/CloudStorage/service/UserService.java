package com.example.CloudStorage.service;

import com.example.CloudStorage.dto.TokenEntityDto;
import com.example.CloudStorage.dto.UserEntityDto;
import com.example.CloudStorage.entity.TokenEntity;
import com.example.CloudStorage.entity.UserEntity;
import com.example.CloudStorage.repository.TokenRepository;
import com.example.CloudStorage.repository.UserRepository;
import jakarta.validation.Valid;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;

import java.util.Random;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Slf4j
@Service
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final Random random = new Random();

    public TokenEntityDto login(@Valid UserEntityDto userEntityDto) {
        final String login = userEntityDto.getLogin();
        final UserEntity user = userRepository.findById(login).orElseThrow(() ->
                new BadCredentialsException("User with login " + login + " not found"));

        if (!user.getPassword().equals(userEntityDto.getPassword())) {
            throw new BadCredentialsException("Incorrect password for user " + login);
        }

        final String authToken = String.valueOf(random.nextLong());
        tokenRepository.save(new TokenEntity(authToken));
        log.info("User " + login + " entered with token " + authToken);
        return new TokenEntityDto(authToken);
    }

    public void logout(String authToken) {
        tokenRepository.deleteById(authToken.split(" ")[1].trim());
    }

    public void checkToken(String authToken) throws RuntimeException {
        if (!tokenRepository.existsById(authToken.split(" ")[1].trim()))
            throw new RuntimeException("User Service: Unauthorized");
    }
}
