package com.example.miracle.image.server.controller;

import com.example.miracle.image.server.dto.ImageDto;
import com.example.miracle.image.server.service.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/image")
    public ResponseEntity<?> postItem(@RequestParam(value = "files", required = false) MultipartFile file,
                                      @RequestParam(value = "item") String itemDto) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ImageDto itemDtoPojo = objectMapper.readValue(itemDto, ImageDto.class);
        return ResponseEntity.ok(imageService.saveImage(file, itemDtoPojo));
    }


    @PostMapping("/image/save")
    public ResponseEntity<?> save(@RequestParam(value = "files", required = false) MultipartFile file,
                                  HttpServletRequest request) {
        return ResponseEntity.ok(imageService.save(file, request));
    }

    @PostMapping("/images/{url}")
    public ResponseEntity<?> read(@RequestParam(value = "url") String url) {
        return ResponseEntity.ok(imageService.read(url));
    }


}
