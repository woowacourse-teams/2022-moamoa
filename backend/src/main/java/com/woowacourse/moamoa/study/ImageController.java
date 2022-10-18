package com.woowacourse.moamoa.study;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/api/study/thumbnail")
    public ResponseEntity<Void> upload(@RequestParam("thumbnail") MultipartFile multipartFile) throws IOException {
        final ThumbnailImage thumbnailImage = imageService.saveUploadFile(multipartFile);
        return ResponseEntity.ok().build();
    }
}
