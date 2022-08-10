package com.woowacourse.moamoa.auth.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long githubId;

    private String refreshToken;

    public Token(final Long githubId, final String refreshToken) {
        this.githubId = githubId;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
