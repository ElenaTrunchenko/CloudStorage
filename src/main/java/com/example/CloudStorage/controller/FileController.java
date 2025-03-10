package com.example.CloudStorage.controller;

import com.example.CloudStorage.dto.FileEntityDto;
import com.example.CloudStorage.service.UserService;
import com.example.CloudStorage.service.FileService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@AllArgsConstructor
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    @PostMapping
    public void uploadFile(@RequestHeader("auth-token") @NotBlank String authToken, @NotBlank String filename, @NotNull @RequestBody MultipartFile file) throws IOException {
        userService.checkToken(authToken);
        fileService.uploadFile(filename, file.getBytes ());
    }

    @DeleteMapping
    public void deleteFile(@RequestHeader("auth-token") @NotBlank String authToken, @NotBlank String filename) {
        userService.checkToken(authToken);
        fileService.deleteFile(filename);
    }

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getFile(@RequestHeader("auth-token") @NotBlank String authToken, @NotBlank String filename) {
        userService.checkToken(authToken);
        return fileService.getFile(filename);
    }

    @PutMapping
    public void editFile(@RequestHeader("auth-token") @NotBlank String authToken, @NotBlank String filename, @Valid @RequestBody FileEntityDto newFilename) {
        userService.checkToken(authToken);
        fileService.editFileName(filename, newFilename.getFilename());
    }

}
