package com.woowacourse.moamoa.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.woowacourse.moamoa.MoamoaApplication;
import com.woowacourse.moamoa.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = MoamoaApplication.class
)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @DisplayName("신규 사용자일 경우 사용자 정보를 저장한다.")
    @Test
    void saveMember() {
        memberService.saveOrUpdate(new Member(1L, "sc0116", "https://image", "github.com"));

        final Member member = memberService.findByUsername("sc0116").get();

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
        memberService.saveOrUpdate(new Member(1L, "sc0116", "https://image", "github.com"));
        final Member member = memberService.findByUsername("sc0116").get();

        member.updateProfileImageUrl("jjanggu.image");
        memberService.saveOrUpdate(member);

        assertAll(
                () -> assertThat(member.getId()).isNotNull(),
                () -> assertThat(member.getGithubId()).isEqualTo(1L),
                () -> assertThat(member.getUsername()).isEqualTo("sc0116"),
                () -> assertThat(member.getImageUrl()).isEqualTo("jjanggu.image"),
                () -> assertThat(member.getProfileUrl()).isEqualTo("github.com")
        );
    }
}
