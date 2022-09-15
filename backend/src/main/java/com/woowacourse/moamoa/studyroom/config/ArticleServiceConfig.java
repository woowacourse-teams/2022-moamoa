package com.woowacourse.moamoa.studyroom.config;

import com.woowacourse.moamoa.studyroom.domain.article.LinkArticle;
import com.woowacourse.moamoa.studyroom.domain.article.LinkContent;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.service.ArticleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
public class ArticleServiceConfig {

    @Bean
    public ArticleService<LinkArticle, LinkContent> linkGenericArticleService(
            StudyRoomRepository studyRoomRepository, JpaRepository<LinkArticle, Long> articleRepository
    ) {
        return new ArticleService<>(studyRoomRepository, articleRepository);
    }
}
