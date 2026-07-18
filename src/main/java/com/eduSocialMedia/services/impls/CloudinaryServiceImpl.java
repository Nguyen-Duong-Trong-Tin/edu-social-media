package com.eduSocialMedia.services.impls;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eduSocialMedia.services.CloudinaryService;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed");
        }
    }

    @Override
    public void deleteFileIfExists(String url) {
        if (url == null || url.trim().isEmpty()) {
            return;
        }

        String publicId = extractPublicId(url);
        if (publicId == null || publicId.isEmpty()) {
            return;
        }

        try {
            // Check if resource exists on Cloudinary server
            cloudinary.api().resource(publicId, ObjectUtils.emptyMap());

            // If it exists, delete it
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (com.cloudinary.api.exceptions.NotFound e) {
            // Resource doesn't exist on Cloudinary, ignore and return
        } catch (Exception e) {
            throw new RuntimeException("Cloudinary file deletion failed", e);
        }
    }

    private String extractPublicId(String url) {
        int uploadIndex = url.indexOf("/upload/");
        if (uploadIndex == -1) {
            return null;
        }

        // Get everything after "/upload/"
        String path = url.substring(uploadIndex + "/upload/".length());

        // Remove the version prefix if present, e.g., "v1570979139/"
        if (path.matches("^v\\d+/.*")) {
            path = path.substring(path.indexOf('/') + 1);
        }

        // Remove the file extension (last dot and everything after)
        int lastDot = path.lastIndexOf('.');
        if (lastDot != -1) {
            path = path.substring(0, lastDot);
        }

        return path;
    }
}
