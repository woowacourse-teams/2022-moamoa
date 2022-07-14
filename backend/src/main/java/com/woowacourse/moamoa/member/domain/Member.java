package com.woowacourse.moamoa.member.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long githubId;

    private String username;

    private String imageUrl;

    private String profileUrl;

    public Member(final Long githubId, final String username, final String imageUrl, final String profileUrl) {
        this(null, githubId, username, imageUrl, profileUrl);
    }

    public Member(final Long id, final Long githubId, final String username, final String imageUrl,
                  final String profileUrl) {
        this.id = id;
        this.githubId = githubId;
        this.username = username;
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
    }

    public void updateProfileImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
