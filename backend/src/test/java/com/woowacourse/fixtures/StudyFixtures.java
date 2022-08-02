package com.woowacourse.fixtures;

import static com.woowacourse.fixtures.MemberFixtures.그린론_아이디;
import static com.woowacourse.fixtures.MemberFixtures.디우_아이디;
import static com.woowacourse.fixtures.MemberFixtures.베루스_아이디;
import static com.woowacourse.fixtures.MemberFixtures.짱구_아이디;
import static com.woowacourse.fixtures.TagFixtures.BE_태그_아이디;
import static com.woowacourse.fixtures.TagFixtures.BE_태그_요약;
import static com.woowacourse.fixtures.TagFixtures.FE_태그_아이디;
import static com.woowacourse.fixtures.TagFixtures.FE_태그_요약;
import static com.woowacourse.fixtures.TagFixtures.리액트_태그_아이디;
import static com.woowacourse.fixtures.TagFixtures.리액트_태그_요약;
import static com.woowacourse.fixtures.TagFixtures.우테코4기_태그_아이디;
import static com.woowacourse.fixtures.TagFixtures.우테코4기_태그_요약;
import static com.woowacourse.fixtures.TagFixtures.자바_태그_아이디;
import static com.woowacourse.fixtures.TagFixtures.자바_태그_요약;

import com.woowacourse.moamoa.study.domain.AttachedTag;
import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Details;
import com.woowacourse.moamoa.study.domain.Participant;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.Period;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.StudyStatus;
import com.woowacourse.moamoa.study.service.StudyResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class StudyFixtures {

    /* 자바 스터디 */
    public static final Long 자바_스터디_아이디 = 1L;
    public static final Details 자바_스터디_디테일 = new Details("신짱구의 자바의 정석", "자바 스터디 요약", "자바 스터디 썸네일", "OPEN", StudyStatus.IN_PROGRESS, "자바 스터디 설명입니다.");
    public static final Participants 자바_스터디_참가자들 = new Participants(1, 10, Set.of(new Participant(그린론_아이디), new Participant(디우_아이디)), 짱구_아이디);
    public static final Period 자바_스터디_기간 = new Period(LocalDate.now(), LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2));
    public static final AttachedTags 자바_스터디_태그 = new AttachedTags(List.of(new AttachedTag(자바_태그_아이디), new AttachedTag(우테코4기_태그_아이디), new AttachedTag(BE_태그_아이디)));
    public static final Study 자바_스터디 = new Study(자바_스터디_아이디, 자바_스터디_디테일, 자바_스터디_참가자들, 자바_스터디_기간, 자바_스터디_태그);
    public static final StudyResponse 자바_스터디_응답 = new StudyResponse(자바_스터디_아이디, 자바_스터디_디테일.getTitle(), 자바_스터디_디테일.getExcerpt(),
            자바_스터디_디테일.getThumbnail(), 자바_스터디_디테일.getRecruitStatus(), List.of(자바_태그_요약, 우테코4기_태그_요약, BE_태그_요약));

    /* 리액트 스터디 */
    public static final Long 리액트_스터디_아이디 = 2L;
    public static final Details 리액트_스터디_디테일 = new Details("디우의 이것이 리액트다.", "리액트 스터디 요약", "리액트 스터디 썸네일", "OPEN", StudyStatus.PREPARE, "리액트 스터디 설명입니다.");
    public static final Participants 리액트_스터디_참가자들 = new Participants(4, 5, Set.of(new Participant(짱구_아이디), new Participant(그린론_아이디), new Participant(베루스_아이디)), 디우_아이디);
    public static final Period 리액트_스터디_기간 = new Period(LocalDate.now(), LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2));
    public static final AttachedTags 리액트_스터디_태그 = new AttachedTags(List.of(new AttachedTag(우테코4기_태그_아이디), new AttachedTag(FE_태그_아이디), new AttachedTag(리액트_태그_아이디)));
    public static final Study 리액트_스터디 = new Study(리액트_스터디_아이디, 리액트_스터디_디테일, 리액트_스터디_참가자들, 리액트_스터디_기간, 리액트_스터디_태그);
    public static final StudyResponse 리액트_스터디_응답 = new StudyResponse(리액트_스터디_아이디, 리액트_스터디_디테일.getTitle(), 리액트_스터디_디테일.getExcerpt(),
            리액트_스터디_디테일.getThumbnail(), 리액트_스터디_디테일.getRecruitStatus(), List.of(우테코4기_태그_요약, FE_태그_요약, 리액트_태그_요약));
    
    /* 자바스크립트스크립트 스터디 */
    public static final Long 자바스크립트_스터디_아이디 = 3L;
    public static final Details 자바스크립트_스터디_디테일 = new Details("그린론의 모던 자바스크립트 인 액션", "자바스크립트 스터디 요약", "자바스크립트 스터디 썸네일", "OPEN", StudyStatus.PREPARE, "자바스크립트 스터디 설명입니다.");
    public static final Participants 자바스크립트_스터디_참가자들 = new Participants(3, 20, Set.of(new Participant(디우_아이디), new Participant(베루스_아이디)), 그린론_아이디);
    public static final Period 자바스크립트_스터디_기간 = new Period(LocalDate.now(), LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2));
    public static final AttachedTags 자바스크립트_스터디_태그 = new AttachedTags(List.of(new AttachedTag(우테코4기_태그_아이디), new AttachedTag(FE_태그_아이디)));
    public static final Study 자바스크립트_스터디 = new Study(자바스크립트_스터디_아이디, 자바스크립트_스터디_디테일, 자바스크립트_스터디_참가자들, 자바스크립트_스터디_기간, 자바스크립트_스터디_태그);
    public static final StudyResponse 자바스크립트_스터디_응답 = new StudyResponse(자바스크립트_스터디_아이디, 자바스크립트_스터디_디테일.getTitle(), 자바스크립트_스터디_디테일.getExcerpt(),
            자바스크립트_스터디_디테일.getThumbnail(), 자바스크립트_스터디_디테일.getRecruitStatus(), List.of(우테코4기_태그_요약, FE_태그_요약));


    /* HTTP 스터디 */
    public static final Long HTTP_스터디_아이디 = 4L;
    public static final Details HTTP_스터디_디테일 = new Details("디우의 HTTP", "HTTP 스터디 요약", "HTTP 스터디 썸네일", "CLOSE", StudyStatus.PREPARE, "HTTP 스터디 설명입니다.");
    public static final Participants HTTP_스터디_참가자들 = new Participants(1, 4, Set.of(new Participant(베루스_아이디), new Participant(짱구_아이디)), 디우_아이디);
    public static final Period HTTP_스터디_기간 = new Period(LocalDate.now(), LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2));
    public static final AttachedTags HTTP_스터디_태그 = new AttachedTags(List.of(new AttachedTag(우테코4기_태그_아이디), new AttachedTag(BE_태그_아이디)));
    public static final Study HTTP_스터디 = new Study(HTTP_스터디_아이디, HTTP_스터디_디테일, HTTP_스터디_참가자들, HTTP_스터디_기간, HTTP_스터디_태그);
    public static final StudyResponse HTTP_스터디_응답 = new StudyResponse(HTTP_스터디_아이디, HTTP_스터디_디테일.getTitle(), HTTP_스터디_디테일.getExcerpt(),
            HTTP_스터디_디테일.getThumbnail(), HTTP_스터디_디테일.getRecruitStatus(), List.of(우테코4기_태그_요약, BE_태그_요약));
    
    /* 알고리즘 스터디 */
    public static final Long 알고리즘_스터디_아이디 = 5L;
    public static final Details 알고리즘_스터디_디테일 = new Details("알고리즘 주도 개발 1타 강사 베루스", "알고리즘 스터디 요약", "알고리즘 스터디 썸네일", "CLOSE", StudyStatus.PREPARE, "알고리즘 스터디 설명입니다.");
    public static final Participants 알고리즘_스터디_참가자들 = new Participants(3, 10, Set.of(new Participant(그린론_아이디), new Participant(디우_아이디)), 베루스_아이디);
    public static final Period 알고리즘_스터디_기간 = new Period(LocalDate.now(), LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2));
    public static final AttachedTags 알고리즘_스터디_태그 = new AttachedTags(List.of());
    public static final Study 알고리즘_스터디 = new Study(알고리즘_스터디_아이디, 알고리즘_스터디_디테일, 알고리즘_스터디_참가자들, 알고리즘_스터디_기간, 알고리즘_스터디_태그);
    public static final StudyResponse 알고리즘_스터디_응답 = new StudyResponse(알고리즘_스터디_아이디, 알고리즘_스터디_디테일.getTitle(), 알고리즘_스터디_디테일.getExcerpt(),
            알고리즘_스터디_디테일.getThumbnail(), 알고리즘_스터디_디테일.getRecruitStatus(), List.of());
    
    /* 리눅스 스터디 */
    public static final Long 리눅스_스터디_아이디 = 1L;
    public static final Details 리눅스_스터디_디테일 = new Details("벨우스의 린우스", "리눅스 스터디 요약", "리눅스 스터디 썸네일", "OPEN", StudyStatus.PREPARE, "리눅스 스터디 설명입니다.");
    public static final Participants 리눅스_스터디_참가자들 = new Participants(3, 10, Set.of(new Participant(그린론_아이디), new Participant(디우_아이디)), 베루스_아이디);
    public static final Period 리눅스_스터디_기간 = new Period(LocalDate.now(), LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2));
    public static final AttachedTags 리눅스_스터디_태그 = new AttachedTags(List.of());
    public static final Study 리눅스_스터디 = new Study(리눅스_스터디_아이디, 리눅스_스터디_디테일, 리눅스_스터디_참가자들, 리눅스_스터디_기간, 리눅스_스터디_태그);
    public static final StudyResponse 리눅스_스터디_응답 = new StudyResponse(리눅스_스터디_아이디, 리눅스_스터디_디테일.getTitle(), 리눅스_스터디_디테일.getExcerpt(),
            리눅스_스터디_디테일.getThumbnail(), 리눅스_스터디_디테일.getRecruitStatus(), List.of());
}
