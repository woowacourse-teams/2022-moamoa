package com.woowacourse.moamoa.study.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.response.StudyDetailResponse;
import com.woowacourse.moamoa.tag.domain.repository.TagRepository;
import com.woowacourse.moamoa.tag.query.response.TagResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RepositoryTest
class StudyDetailServiceTest {

    private StudyDetailService studyDetailService;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        studyDetailService = new StudyDetailService(studyRepository, memberRepository, tagRepository);
    }

    @DisplayName("스터디 상세 정보를 조회할 수 있다.")
    @Test
    public void getStudyDetails() {
        final StudyDetailResponse studyDetails = studyDetailService.getStudyDetails(1L);

        final MemberResponse owner = studyDetails.getOwner();
        final List<MemberResponse> members = studyDetails.getMembers();
        final List<TagResponse> tags = studyDetails.getTags();

        assertThat(studyDetails)
                .extracting("title", "excerpt", "thumbnail", "status", "description", "currentMemberCount",
                        "maxMemberCount", "createdAt", "enrollmentEndDate", "startDate", "endDate")
                .containsExactly("Java 스터디", "자바 설명", "java thumbnail", "OPEN", "그린론의 우당탕탕 자바 스터디입니다.", 3, 10,
                        "2021-11-08", "", "2021-12-08", "");

        assertThat(owner).extracting("githubId", "username", "imageUrl", "profileUrl")
                .containsExactly(2L, "greenlawn", "https://image", "github.com");

        assertThat(members)
                .hasSize(2)
                .extracting("githubId", "username", "imageUrl", "profileUrl")
                .containsExactly(
                        tuple(3L, "dwoo", "https://image", "github.com"),
                        tuple(4L, "verus", "https://image", "github.com")
                );

        assertThat(tags)
                .hasSize(3)
                .filteredOn(tag -> tag.getId() != null)
                .extracting("name", "description")
                .containsExactly(
                        tuple("Java", "자바"),
                        tuple("4기", "우테코4기"),
                        tuple("BE", "백엔드")
                );
    }

    @DisplayName("선택적으로 입력 가능한 정보를 포함한 스터디 상세 정보를 조회할 수 있다.")
    @Test
    public void getStudyDetailsWithOptional() {
        final StudyDetailResponse studyDetails = studyDetailService.getStudyDetails(2L);

        final MemberResponse owner = studyDetails.getOwner();
        final List<MemberResponse> members = studyDetails.getMembers();
        final List<TagResponse> tags = studyDetails.getTags();

        assertThat(studyDetails)
                .extracting("title", "excerpt", "thumbnail", "status", "description", "currentMemberCount",
                        "maxMemberCount", "createdAt", "enrollmentEndDate", "startDate", "endDate")
                .containsExactly("React 스터디", "리액트 설명", "react thumbnail", "OPEN", "디우의 뤼액트 스터디입니다.", 4, 5,
                        "2021-11-08", "2021-11-09", "2021-11-10", "2021-12-08");

        assertThat(owner).extracting("githubId", "username", "imageUrl", "profileUrl")
                .containsExactly(3L, "dwoo", "https://image", "github.com");

        assertThat(members)
                .hasSize(3)
                .extracting("githubId", "username", "imageUrl", "profileUrl")
                .containsExactly(
                        tuple(1L, "jjanggu", "https://image", "github.com"),
                        tuple(2L, "greenlawn", "https://image", "github.com"),
                        tuple(4L, "verus", "https://image", "github.com")
                );

        assertThat(tags)
                .hasSize(3)
                .filteredOn(tag -> tag.getId() != null)
                .extracting("name", "description")
                .containsExactly(
                        tuple("4기", "우테코4기"),
                        tuple("FE", "프론트엔드"),
                        tuple("React", "리액트")
                );
    }
}
