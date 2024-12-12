package com.review.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageDummy {
    public String upload(MultipartFile file) {
        return "https://dummy-s3-url.com/"+ file.getOriginalFilename();
    }
}
