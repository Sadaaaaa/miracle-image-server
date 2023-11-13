package com.example.miracle.image.server.service;

import com.example.miracle.image.server.dto.ImageDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

public interface ImageService {

    Object saveImage(MultipartFile file, ImageDto itemDtoPojo);

    String save(MultipartFile multipartFile, HttpServletRequest request);
    BufferedImage read(String url);
}
