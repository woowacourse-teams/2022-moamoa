package com.woowacourse.moamoa.member.controller;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
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
public class MemberControllerTest {

    @Autowired
    MemberDao memberDao;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("github Id에 맞는 사용자를 조회한다.")
    @Test
    void getCurrentMember() {
        final MemberService memberService = new MemberService(memberRepository, memberDao);
        memberService.saveOrUpdate(new Member(1L, "verus", "image", "profile"));

        final MemberController memberController = new MemberController(memberService);
        final ResponseEntity<MemberResponse> response = memberController.getCurrentMember(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getUsername()).isEqualTo("verus");
        assertThat(response.getBody().getProfileUrl()).isEqualTo("profile");
        assertThat(response.getBody().getImageUrl()).isEqualTo("image");
    }

    @Test
    void findNotFoundGithubIdMember() {
        final MemberController memberController = new MemberController(new MemberService(memberRepository, memberDao));

        assertThatThrownBy(() -> memberController.getCurrentMember(2L))
            .isInstanceOf(MemberNotFoundException.class);
    }
}
