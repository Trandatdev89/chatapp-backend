package com.project01.ecommerce.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class UploadfileUtil {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename().replace(" ", "_");

        File uploadFile = new File(uploadDir);
        if (!uploadFile.exists()) {
            new File(uploadDir).mkdirs();
        }

        FileCopyUtils.copy(file.getBytes(),new File(uploadDir+fileName));
        return fileName;
    }
}
