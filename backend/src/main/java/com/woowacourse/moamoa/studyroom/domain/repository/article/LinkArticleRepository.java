package com.woowacourse.moamoa.studyroom.domain.repository.article;

import com.woowacourse.moamoa.studyroom.domain.article.LinkArticle;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkArticleRepository extends JpaRepository<LinkArticle, Long> {

    LinkArticle save(LinkArticle linkArticle);

    Optional<LinkArticle> findById(Long id);
}
