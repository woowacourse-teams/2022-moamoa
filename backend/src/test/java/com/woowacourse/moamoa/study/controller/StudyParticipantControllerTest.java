package com.woowacourse.moamoa.study.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.referenceroom.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyParticipantService;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.exception.OwnerCanNotLeaveException;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class StudyParticipantControllerTest {

    private static final MemberData 짱구 = new MemberData(1L, "jjanggu", "https://image", "github.com");
    private static final MemberData 그린론 = new MemberData(2L, "greenlawn", "https://image", "github.com");
    private static final MemberData 베루스 = new MemberData(4L, "verus", "https://image", "github.com");

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private EntityManager entityManager;

    private StudyParticipantController sut;

    private Member jjanggu;
    private Member verus;
    private Member greenlawn;
    private Study javaStudy;

    @BeforeEach
    void setUp() {
        sut = new StudyParticipantController(new StudyParticipantService(memberRepository, studyRepository));

        // 사용자 추가
        jjanggu = memberRepository.save(toMember(짱구));
        verus = memberRepository.save(toMember(베루스));
        greenlawn = memberRepository.save(toMember(그린론));

        // 스터디 생성
        final StudyService studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());

        final LocalDate startDate = LocalDate.now();
        CreatingStudyRequest javaStudyRequest = CreatingStudyRequest.builder()
                .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개")
                .startDate(startDate)
                .build();

        javaStudy = studyService.createStudy(jjanggu.getGithubId(), javaStudyRequest);

        studyService.participateStudy(verus.getGithubId(), javaStudy.getId());

        entityManager.flush();
        entityManager.clear();
    }

    private static Member toMember(MemberData memberData) {
        return new Member(memberData.getGithubId(), memberData.getUsername(), memberData.getImageUrl(),
                memberData.getProfileUrl());
    }

    @DisplayName("스터디에 참여하지 않은 회원은 탈퇴할 수 없다.")
    @Test
    void notLeaveByNonParticipatedMember() {
        assertThatThrownBy(() -> sut.leaveStudy(greenlawn.getId(), javaStudy.getId()))
                .isInstanceOf(NotParticipatedMemberException.class);
    }

    @DisplayName("스터디장은 스터디를 탈퇴할 수 없다.")
    @Test
    void notLeaveByOwner() {
        assertThatThrownBy(() -> sut.leaveStudy(jjanggu.getId(), javaStudy.getId()))
                .isInstanceOf(OwnerCanNotLeaveException.class);
    }
}