package com.woowacourse.moamoa.study.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.query.data.StudyData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class StudyDaoTest {

    @Autowired
    private StudyDao studyDao;

    @Test
    void getNotHasParticipantsStudyDetails() {
        final StudyData studyData = studyDao.getById(5L);

        assertThat(studyData.getId()).isEqualTo(5L);
        assertThat(studyData.getTitle()).isEqualTo("알고리즘 스터디");
        assertThat(studyData.getExcerpt()).isEqualTo("알고리즘 설명");
        assertThat(studyData.getThumbnail()).isEqualTo("algorithm thumbnail");
        assertThat(studyData.getStatus()).isEqualTo("CLOSE");
        assertThat(studyData.getDescription()).isEqualTo("알고리즘을 TDD로 풀자의 베루스입니다.");
        assertThat(studyData.getMaxMemberCount()).isEqualTo(2);
        assertThat(studyData.getOwner()).isEqualTo(new MemberData(4L, "verus", "https://image", "github.com"));
    }
}
