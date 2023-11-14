package com.example.miracle.image.server.service;

import com.example.miracle.image.server.dto.ImageDto;
import com.example.miracle.image.server.entity.Image;
import com.example.miracle.image.server.helpers.ImageMapper;
import com.example.miracle.image.server.repository.ImageRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Value("${upload.path.images.linux}")
    private String uploadPath;
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    @Override
    public Object saveImage(MultipartFile file, ImageDto imageDto) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
            }

            String originalFileName = file.getOriginalFilename();
            assert originalFileName != null;
            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
//        String fileName = "" + System.currentTimeMillis() + ext;
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + ext;

            log.warn(fileName);

            Files.copy(file.getInputStream(), root.resolve(fileName));

            String path = uploadPath + "/" + fileName;

            Image image = ImageMapper.toImage(file, path, uuid);


            return imageRepository.save(image);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }


    @Override
    public String save(MultipartFile multipartFile, HttpServletRequest request) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
            }

            String originalFileName = multipartFile.getOriginalFilename();
            assert originalFileName != null;
            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));

            String fileName = UUID.randomUUID() + ext;

            Files.copy(multipartFile.getInputStream(), root.resolve(fileName));

            return getURL(request) + "/images/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BufferedImage read(String url) {
        try {

            // Чтение изображения с диска
            BufferedImage image = ImageIO.read(new File(url));

            // Дальнейшая обработка изображения, например, отображение или анализ

            System.out.println("Изображение успешно прочитано.");

            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String getURL(HttpServletRequest req) {

        String scheme = req.getScheme();             // http
        String serverName = req.getServerName();     // hostname.com
        int serverPort = req.getServerPort();        // 80
//        String contextPath = req.getContextPath();   // /mywebapp
//        String servletPath = req.getServletPath();   // /servlet/MyServlet
//        String pathInfo = req.getPathInfo();         // /a/b;c=123
//        String queryString = req.getQueryString();          // d=789

        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }

//        url.append(contextPath).append(servletPath);
//
//        if (pathInfo != null) {
//            url.append(pathInfo);
//        }
//        if (queryString != null) {
//            url.append("?").append(queryString);
//        }
        return url.toString();
    }
}
