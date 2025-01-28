package com.example.CloudStorage.dto;

import com.example.CloudStorage.entity.TokenEntity;
import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * DTO for {@link TokenEntity}
 */
@Value
@AllArgsConstructor
public class TokenEntityDto {
    @NotBlank
    String token;

    @JsonGetter("auth-token")
    public String getAuthToken() {
        return token;
    }
}