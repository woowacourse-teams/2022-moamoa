package com.woowacourse.moamoa.member.domain.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.woowacourse.moamoa.member.domain.Member;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("신규 사용자일 경우 사용자 정보를 저장한다.")
    @Test
    void saveMember() {
        final Member member = memberRepository.save(new Member(1L, "sc0116", "https://image", "github.com"));

        assertAll(
                () -> assertThat(member.getId()).isNotNull(),
                () -> assertThat(member.getGithubId()).isEqualTo(1L),
                () -> assertThat(member.getUsername()).isEqualTo("sc0116"),
                () -> assertThat(member.getImageUrl()).isEqualTo("https://image"),
                () -> assertThat(member.getProfileUrl()).isEqualTo("github.com")
        );
    }

    @DisplayName("기존 사용자일 경우 사용자 정보를 갱신한다.")
    @Test
    void updateMember() {
        final Member member = memberRepository.save(new Member(1L, "sc0116", "https://image", "github.com"));
        member.updateProfileImageUrl("jjanggu.image");
        memberRepository.save(member);

        assertAll(
                () -> assertThat(member.getId()).isNotNull(),
                () -> assertThat(member.getGithubId()).isEqualTo(1L),
                () -> assertThat(member.getUsername()).isEqualTo("sc0116"),
                () -> assertThat(member.getImageUrl()).isEqualTo("jjanggu.image"),
                () -> assertThat(member.getProfileUrl()).isEqualTo("github.com")
        );
    }

    @DisplayName("사용자를 조회한다.")
    @Test
    void findMember() {
        final Member member = memberRepository.save(new Member(1L, "sc0116", "https://image", "github.com"));
        final Member findMember = memberRepository.findByGithubId(member.getGithubId()).get();

        assertThat(member).isEqualTo(findMember);
    }

    @DisplayName("존재하지 않는 사용자를 조회한다.")
    @Test
    void findByNotExistMember() {
        final Optional<Member> member = memberRepository.findByGithubId(1L);

        assertThat(member).isEmpty();
    }
}
