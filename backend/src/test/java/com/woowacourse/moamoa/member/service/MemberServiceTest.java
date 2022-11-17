package com.woowacourse.moamoa.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.moamoa.alarm.service.alarmsender.SlackAlarmSender;
import com.woowacourse.moamoa.alarm.SlackUsersClient;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.fixtures.MemberFixtures;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.MemberDao;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@RepositoryTest
@Import({RestTemplate.class, SlackAlarmSender.class, SlackUsersClient.class})
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

        memberService.saveOrUpdate(MemberFixtures.짱구());
        memberService.saveOrUpdate(MemberFixtures.그린론());
        memberService.saveOrUpdate(MemberFixtures.디우());
    }

    @DisplayName("신규 사용자일 경우 사용자 정보를 저장한다.")
    @Test
    void saveMember() {
        final MemberResponse memberResponse = memberService.saveOrUpdate(
                MemberFixtures.베루스());

        final MemberResponse member = memberService.getByMemberId(memberResponse.getId());

        assertAll(
                () -> assertThat(member.getId()).isEqualTo(memberResponse.getId()),
                () -> assertThat(member.getUsername()).isEqualTo("verus"),
                () -> assertThat(member.getImageUrl()).isEqualTo("https://verus.png"),
                () -> assertThat(member.getProfileUrl()).isEqualTo("https://verus.com")
        );
    }

    @DisplayName("기존 사용자일 경우 사용자 정보를 갱신한다.")
    @Test
    void updateMember() {
        final MemberResponse memberResponse = memberService.saveOrUpdate(
                MemberFixtures.짱구());
        entityManager.flush();
        entityManager.clear();

        final MemberResponse member = memberService.getByMemberId(memberResponse.getId());

        assertAll(
                () -> assertThat(member.getId()).isEqualTo(memberResponse.getId()),
                () -> assertThat(member.getUsername()).isEqualTo("jjanggu"),
                () -> assertThat(member.getImageUrl()).isEqualTo("https://jjanggu.png"),
                () -> assertThat(member.getProfileUrl()).isEqualTo("https://jjanggu.com")
        );
    }
}
