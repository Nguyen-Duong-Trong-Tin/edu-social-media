package com.eduSocialMedia.services;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadFile(MultipartFile file);
    void deleteFileIfExists(String url);
}