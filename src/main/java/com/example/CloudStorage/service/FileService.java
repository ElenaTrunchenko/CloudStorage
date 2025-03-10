package com.example.CloudStorage.service;

import com.example.CloudStorage.dto.FileEntityDto;
import com.example.CloudStorage.entity.FileEntity;
import com.example.CloudStorage.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public synchronized void uploadFile(String filename, byte[] size) {
        fileRepository.save(new FileEntity(filename, size));
    }

    public synchronized void deleteFile(String filename) {
        if (!fileRepository.existsById(filename)) {
            throw new RuntimeException("File " + filename + " not found");
        }
        fileRepository.deleteById(filename);
    }

    public byte[] getFile(String filename) {
        final FileEntity fileEntity = getFileByName(filename);
        return fileEntity.getSize();
    }

    public synchronized void editFileName(String oldFilename, String newFilename) {
        final FileEntity fileEntity = getFileByName(oldFilename);
        final FileEntity newFileEntity = new FileEntity(newFilename, fileEntity.getSize());
        fileRepository.delete(fileEntity);
        fileRepository.save(newFileEntity);
    }

    public List<FileEntityDto> getFileList(int limit) {
        final List<FileEntity> files = fileRepository.download(limit);
        return files.stream()
                .map(file -> new FileEntityDto(file.getFilename(), file.getSize()))
                .collect(Collectors.toList());
    }

    private FileEntity getFileByName(String filename) {
        return fileRepository.findById(filename).orElseThrow(() ->
                new RuntimeException("File " + filename + " not found"));
    }
}