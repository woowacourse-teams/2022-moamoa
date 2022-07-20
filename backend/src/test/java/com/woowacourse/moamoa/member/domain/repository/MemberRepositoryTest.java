package com.woowacourse.moamoa.member.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.moamoa.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("신규 사용자일 경우 사용자 정보를 저장한다.")
    @Test
    void saveMember() {
        final Member member = memberRepository.findByGithubId(1L).get();

        assertAll(
                () -> assertThat(member.getId()).isNotNull(),
                () -> assertThat(member.getGithubId()).isEqualTo(1L),
                () -> assertThat(member.getUsername()).isEqualTo("jjanggu"),
                () -> assertThat(member.getImageUrl()).isEqualTo("https://image"),
                () -> assertThat(member.getProfileUrl()).isEqualTo("github.com")
        );
    }

    @DisplayName("기존 사용자일 경우 사용자 정보를 갱신한다.")
    @Test
    void updateMember() {
        final Member member = memberRepository.findByGithubId(1L).get();
        member.update("jjanggu", "jjanggu.image", "github.com");
        memberRepository.save(member);

        assertAll(
                () -> assertThat(member.getId()).isNotNull(),
                () -> assertThat(member.getGithubId()).isEqualTo(1L),
                () -> assertThat(member.getUsername()).isEqualTo("jjanggu"),
                () -> assertThat(member.getImageUrl()).isEqualTo("jjanggu.image"),
                () -> assertThat(member.getProfileUrl()).isEqualTo("github.com")
        );
    }

    @DisplayName("사용자를 조회한다.")
    @Test
    void findMember() {
        final Member member = memberRepository.findByGithubId(1L).get();
        final Member findMember = memberRepository.findByGithubId(member.getGithubId()).get();

        assertThat(member).isEqualTo(findMember);
    }

    @DisplayName("존재하지 않는 사용자를 조회한다.")
    @Test
    void findByNotExistMember() {
        final Optional<Member> member = memberRepository.findByGithubId(-1L);

        assertThat(member).isEmpty();
    }

    @DisplayName("member id들로 각각 일치하는 Member들을 찾은 후 반환한다.")
    @Test
    public void findAllById() {
        final Long memberId1 = memberRepository.findByGithubId(1L).get().getId();
        final Long memberId2 = memberRepository.findByGithubId(2L).get().getId();

        final List<Member> members = memberRepository.findAllById(List.of(memberId1, memberId2));

        assertThat(members)
                .hasSize(2)
                .filteredOn(member -> member.getId() != null)
                .extracting("githubId", "username", "imageUrl", "profileUrl")
                .containsExactly(
                        tuple(1L, "jjanggu", "https://image", "github.com"),
                        tuple(2L, "greenlawn", "https://image", "github.com")
                );
    }
}
