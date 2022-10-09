package com.woowacourse.moamoa.member.controller;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.fixtures.MemberFixtures;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.MemberDao;
import com.woowacourse.moamoa.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
class MemberControllerTest {

    @Autowired
    MemberDao memberDao;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("id에 맞는 사용자를 조회한다.")
    @Test
    void getCurrentMember() {
        final MemberService memberService = new MemberService(memberRepository, memberDao);
        final MemberResponse memberResponse = memberService.saveOrUpdate(MemberFixtures.베루스());

        final MemberController memberController = new MemberController(memberService);
        final ResponseEntity<MemberResponse> response = memberController.getCurrentMember(memberResponse.getId());

        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(response.getBody()).isNotNull(),
                () -> assertThat(response.getBody().getId()).isNotNull(),
                () -> assertThat(response.getBody().getUsername()).isEqualTo("verus"),
                () -> assertThat(response.getBody().getImageUrl()).isEqualTo("https://verus.png"),
                () -> assertThat(response.getBody().getProfileUrl()).isEqualTo("https://verus.com")
        );
    }

    @DisplayName("id에 맞는 사용자가 없는 경우 예외가 발생한다.")
    @Test
    void findNotFoundGithubIdMember() {
        final MemberController memberController = new MemberController(new MemberService(memberRepository, memberDao));

        assertThatThrownBy(() -> memberController.getCurrentMember(1L))
                .isInstanceOf(MemberNotFoundException.class);
    }
}
