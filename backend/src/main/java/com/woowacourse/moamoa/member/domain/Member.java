package com.woowacourse.moamoa.member.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long githubId;

    private String username;

    private String email;

    private String imageUrl;

    private String profileUrl;

    public Member(final Long githubId, final String username, final String email, final String imageUrl, final String profileUrl) {
        this(null, githubId, username, email, imageUrl, profileUrl);
    }

    public Member(final Long id, final Long githubId, final String username, final String email, final String imageUrl,
                  final String profileUrl) {
        this.id = id;
        this.githubId = githubId;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
    }

    public void update(final String username, final String email, final String imageUrl, final String profileUrl) {
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
    }
}
