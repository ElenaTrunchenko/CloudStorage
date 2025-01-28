package com.example.CloudStorage.service;

import com.example.CloudStorage.dto.FileEntityDto;
import com.example.CloudStorage.entity.FileEntity;
import com.example.CloudStorage.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public synchronized void addFile(String filename, byte[] size) {
        fileRepository.save(new FileEntity(filename, size));
    }

    @Synchronized
    public void deleteFile(String filename) {
        if (!fileRepository.existsByFilename(filename)) {
            throw new RuntimeException("File " + filename + " not found");
        }
        fileRepository.deleteFileByFilename(filename);
    }

    public List<FileEntityDto> getFileList(int limit) {
        List<FileEntity> listFiles = fileRepository.download(limit);
        return listFiles.stream()
                .map(file -> FileEntityDto.builder()
                        .filename(file.getFilename())
                        .size(file.getSize())
                        .build()).collect(Collectors.toList());
    }
}