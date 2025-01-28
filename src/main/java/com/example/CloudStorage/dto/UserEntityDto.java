package com.example.CloudStorage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

/**
 * DTO for {@link com.example.CloudStorage.entity.UserEntity}
 */
@Value
@Data
@AllArgsConstructor
@Validated
public class UserEntityDto {
    @NotBlank
    String login;
    @NotBlank
    String password;
}