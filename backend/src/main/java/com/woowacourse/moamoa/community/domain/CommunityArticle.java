package com.woowacourse.moamoa.community.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommunityArticle {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private Long studyId;

    public CommunityArticle(final String title, final String content, final Long authorId, final Long studyId) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.studyId = studyId;
    }
}
