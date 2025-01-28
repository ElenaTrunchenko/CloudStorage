package com.example.CloudStorage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tokens")
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {
    @Id
    private String token;
}
