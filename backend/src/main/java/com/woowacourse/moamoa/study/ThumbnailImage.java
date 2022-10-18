package com.woowacourse.moamoa.study;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import lombok.AccessLevel;
import static javax.persistence.GenerationType.IDENTITY;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "thumbnail_image")
@Getter
public class ThumbnailImage {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String fileName;
    private String fileUrl;

    public ThumbnailImage(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
