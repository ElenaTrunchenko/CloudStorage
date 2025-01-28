package com.example.CloudStorage.controller;

import com.example.CloudStorage.dto.FileEntityDto;
import com.example.CloudStorage.entity.FileEntity;
import com.example.CloudStorage.repository.FileRepository;
import com.example.CloudStorage.service.FileService;
import com.example.CloudStorage.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link FileController}
 */
@Import(UserService.class)
@WebMvcTest({FileController.class})
public class FileControllerTest {
    public static final String EXISTING_FILE = "existingFile";
    public static final String NOT_EXISTING_FILE = "notExistingFile";
    public static final int SIZE_FILE = 10;
    public static final String LIMIT_SIZE_FILE = "30";

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

    }

    private final FileEntity existingFileEntity = new FileEntity(EXISTING_FILE, new byte[SIZE_FILE]);

    private final FileRepository fileRepository = upload();
    private final FileService fileService = new FileService(fileRepository);

    private FileRepository upload() {
        final FileRepository fileRepository = Mockito.mock(FileRepository.class);

        when(fileRepository.findById(EXISTING_FILE)).thenReturn(Optional.of(existingFileEntity));
        when(fileRepository.findById(NOT_EXISTING_FILE)).thenReturn(Optional.empty());

        when(fileRepository.existsById(EXISTING_FILE)).thenReturn(true);
        when(fileRepository.existsById(NOT_EXISTING_FILE)).thenReturn(false);

        return fileRepository;
    }

    @Test
    void delete() {
        Assertions.assertDoesNotThrow(()
                -> fileService.deleteFile(EXISTING_FILE));
    }

    @Test
    void getFileList() {
        final List<FileEntityDto> expectedFileList = List.of(new FileEntityDto(EXISTING_FILE, new byte[SIZE_FILE]));
        final List<FileEntityDto> fileList = fileService.getFileList(1);
        Assertions.assertEquals(expectedFileList, fileList);
    }
}