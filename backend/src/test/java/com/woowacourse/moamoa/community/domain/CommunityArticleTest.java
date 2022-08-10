package com.woowacourse.moamoa.community.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Content;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.RecruitPlanner;
import com.woowacourse.moamoa.study.domain.RecruitStatus;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.StudyPlanner;
import com.woowacourse.moamoa.study.domain.StudyStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommunityArticleTest {

    @DisplayName("스터디에 참여한 참가자만 게시글을 작성할 수 있다.")
    @Test
    void writeCommunityArticleByParticipant() {
        final Study study = new Study(new Content("제목", "한 줄 소개", "http://image", "설명"),
                Participants.createBy(1L), new RecruitPlanner(10, RecruitStatus.RECRUITMENT_START, null),
                new StudyPlanner(LocalDate.now(), null, StudyStatus.PREPARE),
                new AttachedTags(List.of()),
                LocalDateTime.now());
    }
}