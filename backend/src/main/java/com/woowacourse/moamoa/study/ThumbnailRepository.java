package com.woowacourse.moamoa.study;

public interface ThumbnailRepository {

    ThumbnailImage save(ThumbnailImage thumbnailImage);

    ThumbnailImage findByFileName(String fileName);
}
