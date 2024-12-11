package com.review.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageDummy {
    public String uploadImage(MultipartFile file) {
        return "/images/"+file.getOriginalFilename();
    }
}
