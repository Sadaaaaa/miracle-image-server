package com.example.miracle.image.server.helpers;

import com.example.miracle.image.server.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

public class ImageMapper {
    public static Image toImage(MultipartFile file, String path, UUID uuid) {
        return Image.builder()
                .id(null)
                .filename(file.getOriginalFilename())
                .size(file.getSize())
                .contentType(file.getContentType())
                .path(path)
                .uuid(uuid)
                .posted(LocalDateTime.now())
                .build();
    }
}
