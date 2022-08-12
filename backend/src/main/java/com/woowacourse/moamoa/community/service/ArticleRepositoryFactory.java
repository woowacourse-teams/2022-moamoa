package com.woowacourse.moamoa.community.service;

import com.woowacourse.moamoa.community.domain.repository.ArticleRepository;
import com.woowacourse.moamoa.community.domain.repository.CommunityArticleRepository;
import com.woowacourse.moamoa.community.domain.repository.NoticeArticleRepository;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class ArticleRepositoryFactory {

    private final CommunityArticleRepository communityArticleRepository;
    private final NoticeArticleRepository noticeArticleRepository;

    public ArticleRepositoryFactory(
            final CommunityArticleRepository communityArticleRepository,
            final NoticeArticleRepository noticeArticleRepository) {
        this.communityArticleRepository = communityArticleRepository;
        this.noticeArticleRepository = noticeArticleRepository;
    }

    public ArticleRepository getRepository() {
        return communityArticleRepository;
    }

    public ArticleRepository getRepository(final String articleType) {
        if (articleType.equals("community")) {
            return communityArticleRepository;
        }
        return noticeArticleRepository;
    }
}
