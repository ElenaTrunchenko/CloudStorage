package com.example.CloudStorage;

import com.example.CloudStorage.entity.FileEntity;
import com.example.CloudStorage.service.FileService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/admin-ui/fileEntities")
@RequiredArgsConstructor
public class FileEntityResource {

    private final FileService fileService;

    @GetMapping
    public PagedModel<FileEntity> getAll(Pageable pageable) {
        Page<FileEntity> fileEntities = fileService.getAll(pageable);
        return new PagedModel<>(fileEntities);
    }

    @GetMapping("/{id}")
    public FileEntity getOne(@PathVariable String id) {
        return fileService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<FileEntity> getMany(@RequestParam List<String> ids) {
        return fileService.getMany(ids);
    }

    @PostMapping
    public FileEntity create(@RequestBody FileEntity fileEntity) {
        return fileService.create(fileEntity);
    }

    @PatchMapping("/{id}")
    public FileEntity patch(@PathVariable String id, @RequestBody JsonNode patchNode) {
        return fileService.patch(id, patchNode);
    }

    @PatchMapping
    public List<String> patchMany(@RequestParam List<String> ids, @RequestBody JsonNode patchNode) {
        return fileService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public FileEntity delete(@PathVariable String id) {
        return fileService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<String> ids) {
        fileService.deleteMany(ids);
    }
}
