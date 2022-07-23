package com.woowacourse.moamoa.member.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.woowacourse.moamoa.study.domain.Study;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.OneToMany;
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

    public void update(final String username, final String imageUrl, final String profileUrl) {
        this.username = username;
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
    }
}
