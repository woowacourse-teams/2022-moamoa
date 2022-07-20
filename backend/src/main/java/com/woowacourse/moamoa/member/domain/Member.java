package com.woowacourse.moamoa.member.domain;

import com.woowacourse.moamoa.study.domain.Study;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "member")
    private List<Study> establishedStudies = new ArrayList<>();

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
