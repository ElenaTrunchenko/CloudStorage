package com.example.CloudStorage.controller;

import com.example.CloudStorage.dto.FileEntityDto;
import com.example.CloudStorage.service.UserService;
import com.example.CloudStorage.service.FileService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@AllArgsConstructor
public class FileController {
    private FileService fileService;
    private UserService userService;

    @PostMapping("/file")
    public ResponseEntity<?> upload(@RequestHeader("auth-token") String authToken,
                                    @RequestParam("filename") String filename, MultipartFile multipartFile) {
        try {
            userService.checkToken(authToken);
            fileService.addFile(filename, filename.getBytes());
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/file")
    public ResponseEntity<Void> delete(@RequestHeader("auth-token") @NotBlank String authToken,
                                       @NotBlank String filename) {
        try {
            userService.checkToken(authToken);
            fileService.deleteFile(filename);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    public List<FileEntityDto> getFileList(@RequestHeader("auth-token") @NotBlank String authToken,
                                           @Min(1) int limit) {
        try {
            userService.checkToken(authToken);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return fileService.getFileList(limit);
    }

}
