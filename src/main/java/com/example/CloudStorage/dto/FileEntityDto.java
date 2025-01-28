package com.example.CloudStorage.dto;

import com.example.CloudStorage.entity.FileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * DTO for {@link FileEntity}
 */
@Value
@Builder
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileEntityDto {
    String filename;
    byte[] size;
}