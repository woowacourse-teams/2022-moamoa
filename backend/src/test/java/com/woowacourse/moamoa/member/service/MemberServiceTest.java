package com.woowacourse.moamoa.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.MemberDao;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private EntityManager entityManager;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, memberDao);

        memberService.saveOrUpdate(new Member(1L, "jjanggu", "https://image", "github.com"));
        memberService.saveOrUpdate(new Member(2L, "greenlawn", "https://image", "github.com"));
        memberService.saveOrUpdate(new Member(3L, "dwoo", "https://image", "github.com"));
        memberService.saveOrUpdate(new Member(4L, "verus", "https://image", "github.com"));
    }

    @DisplayName("신규 사용자일 경우 사용자 정보를 저장한다.")
    @Test
    void saveMember() {
        memberService.saveOrUpdate(new Member(5L, "sc0116", "https://image", "github.com"));

        final MemberResponse member = memberService.getByGithubId(5L);

        assertAll(
                () -> assertThat(member.getId()).isEqualTo(5L),
                () -> assertThat(member.getUsername()).isEqualTo("sc0116"),
                () -> assertThat(member.getImageUrl()).isEqualTo("https://image"),
                () -> assertThat(member.getProfileUrl()).isEqualTo("github.com")
        );
    }

    @DisplayName("기존 사용자일 경우 사용자 정보를 갱신한다.")
    @Test
    void updateMember() {
        memberService.saveOrUpdate(new Member(1L, "sc0116", "jjanggu.image", "github.com"));
        entityManager.flush();
        entityManager.clear();

        final MemberResponse member = memberService.getByGithubId(1L);

        assertAll(
                () -> assertThat(member.getId()).isEqualTo(1L),
                () -> assertThat(member.getUsername()).isEqualTo("sc0116"),
                () -> assertThat(member.getImageUrl()).isEqualTo("jjanggu.image"),
                () -> assertThat(member.getProfileUrl()).isEqualTo("github.com")
        );
    }
}
