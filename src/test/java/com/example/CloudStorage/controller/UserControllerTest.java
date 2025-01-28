package com.example.CloudStorage.controller;

import com.example.CloudStorage.dto.UserEntityDto;
import com.example.CloudStorage.entity.UserEntity;
import com.example.CloudStorage.repository.TokenRepository;
import com.example.CloudStorage.repository.UserRepository;
import com.example.CloudStorage.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link UserController}
 */
@Import(UserService.class)
@WebMvcTest({UserController.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    public static final String AUTH_TOKEN = "root";
    public static final String UNKNOWN_AUTH_TOKEN = "123";
    public static final String EXISTING_USER = "ivanov";
    public static final String NOT_EXISTING_USER = "petrov";
    public static final String CORRECT_PASSWORD = "password";

    private final UserRepository userRepository = createUserRepository();
    private final TokenRepository tokenRepository = createTokenRepository();

    private UserRepository createUserRepository() {
        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        when(userRepository.findById(EXISTING_USER)).thenReturn(Optional.of(new UserEntity(EXISTING_USER, CORRECT_PASSWORD)));
        when(userRepository.findById(NOT_EXISTING_USER)).thenReturn(Optional.empty());
        return userRepository;
    }

    private TokenRepository createTokenRepository() {
        final TokenRepository tokenRepository = Mockito.mock(TokenRepository.class);
        when(tokenRepository.existsById(AUTH_TOKEN.split(" ")[1].trim())).thenReturn(true);
        when(tokenRepository.existsById(UNKNOWN_AUTH_TOKEN)).thenReturn(false);
        return tokenRepository;
    }

    @Test
    void login() {
        final UserService userService = new UserService(userRepository, tokenRepository);
        Assertions.assertDoesNotThrow(() -> userService.login(new UserEntityDto(EXISTING_USER, CORRECT_PASSWORD)));
    }

    @Test
    void login_userNotFound() {
        final UserService userService = new UserService(userRepository, tokenRepository);
        Assertions.assertThrows(RuntimeException.class, () -> userService.login(new UserEntityDto(NOT_EXISTING_USER, CORRECT_PASSWORD)));
    }

    @Test
    void login_incorrectPassword() {
        final UserService userService = new UserService(userRepository, tokenRepository);
        Assertions.assertThrows(RuntimeException.class, () -> userService.login(new UserEntityDto(EXISTING_USER, "qwerty")));
    }

    @Test
    void logout() {
        final UserService userService = new UserService(userRepository, tokenRepository);
        Assertions.assertDoesNotThrow(() -> userService.logout(AUTH_TOKEN));
    }

    @Test
    void checkToken() {
        final UserService userService = new UserService(userRepository, tokenRepository);
        Assertions.assertDoesNotThrow(() -> userService.checkToken(AUTH_TOKEN));
    }

    @Test
    void checkToken_failed() {
        final UserService userService = new UserService(userRepository, tokenRepository);
        Assertions.assertThrows(RuntimeException.class, () -> userService.checkToken(UNKNOWN_AUTH_TOKEN));
    }
}