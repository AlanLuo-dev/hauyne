package com.luoyx.hauyne.file.controller;

import com.luoyx.hauyne.file.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) throws Exception {

        String objectKey = fileService.upload(file);

        return Map.of(
                "objectKey", objectKey,
                "url", "/api/files/download?key=" + objectKey
        );
    }

    @GetMapping("/download")
    public void download(@RequestParam String key, HttpServletResponse response) throws Exception {

        InputStream stream = fileService.download(key);

        response.setContentType("application/octet-stream");

        StreamUtils.copy(stream, response.getOutputStream());
    }

    @GetMapping("/presigned-url")
    public String presignedUrl(@RequestParam String key) throws Exception {
        return fileService.presignedUrl(key);
    }
}
