package com.woowacourse.fixtures;

import static com.woowacourse.fixtures.MemberFixtures.그린론_깃허브_아이디;
import static com.woowacourse.fixtures.MemberFixtures.그린론_아이디;
import static com.woowacourse.fixtures.MemberFixtures.그린론_유저네임;
import static com.woowacourse.fixtures.MemberFixtures.그린론_이미지;
import static com.woowacourse.fixtures.MemberFixtures.그린론_프로필;
import static com.woowacourse.fixtures.MemberFixtures.디우_깃허브_아이디;
import static com.woowacourse.fixtures.MemberFixtures.디우_아이디;
import static com.woowacourse.fixtures.MemberFixtures.디우_유저네임;
import static com.woowacourse.fixtures.MemberFixtures.디우_이미지;
import static com.woowacourse.fixtures.MemberFixtures.디우_프로필;
import static com.woowacourse.fixtures.MemberFixtures.베루스_깃허브_아이디;
import static com.woowacourse.fixtures.MemberFixtures.베루스_아이디;
import static com.woowacourse.fixtures.MemberFixtures.베루스_유저네임;
import static com.woowacourse.fixtures.MemberFixtures.베루스_이미지;
import static com.woowacourse.fixtures.MemberFixtures.베루스_프로필;
import static com.woowacourse.fixtures.MemberFixtures.짱구_깃허브_아이디;
import static com.woowacourse.fixtures.MemberFixtures.짱구_아이디;
import static com.woowacourse.fixtures.MemberFixtures.짱구_유저네임;
import static com.woowacourse.fixtures.MemberFixtures.짱구_이미지;
import static com.woowacourse.fixtures.MemberFixtures.짱구_프로필;
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
import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_END;
import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_START;
import static com.woowacourse.moamoa.study.domain.StudyStatus.IN_PROGRESS;
import static com.woowacourse.moamoa.study.domain.StudyStatus.PREPARE;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.study.domain.AttachedTag;
import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Content;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.RecruitPlanner;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.StudyPlanner;
import com.woowacourse.moamoa.study.service.StudyResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class StudyFixtures {

    /* 자바 스터디 */
    public static final Member 자바_스터디장 = new Member(짱구_아이디, 짱구_깃허브_아이디, 짱구_유저네임, 짱구_이미지, 짱구_프로필);
    public static final Long 자바_스터디_아이디 = 1L;
    public static final Content 자바_스터디_내용 = new Content("신짱구의 자바의 정석", "자바 스터디 요약", "자바 스터디 썸네일", "자바 스터디 설명입니다.");
    public static final Participants 자바_스터디_참가자들 = new Participants(짱구_아이디, Set.of(그린론_아이디, 디우_아이디));
    public static final RecruitPlanner 자바_스터디_모집계획 = new RecruitPlanner(10, RECRUITMENT_START, LocalDate.now());
    public static final StudyPlanner 자바_스터디_계획 = new StudyPlanner(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), IN_PROGRESS);
    public static final AttachedTags 자바_스터디_태그 = new AttachedTags(List.of(new AttachedTag(자바_태그_아이디), new AttachedTag(우테코4기_태그_아이디), new AttachedTag(BE_태그_아이디)));
    public static final Study 자바_스터디 = new Study(자바_스터디_내용, 자바_스터디_참가자들, 자바_스터디_모집계획,
            자바_스터디_계획, 자바_스터디_태그, LocalDateTime.now());
    public static final StudyResponse 자바_스터디_응답 = new StudyResponse(자바_스터디_아이디, 자바_스터디_내용.getTitle(), 자바_스터디_내용.getExcerpt(),
            자바_스터디_내용.getThumbnail(), 자바_스터디_모집계획.getRecruitStatus().name(), List.of(자바_태그_요약, 우테코4기_태그_요약, BE_태그_요약));

    /* 리액트 스터디 */
    public static final Member 리액트_스터디장 = new Member(디우_아이디, 디우_깃허브_아이디, 디우_유저네임, 디우_이미지, 디우_프로필);
    public static final Long 리액트_스터디_아이디 = 2L;
    public static final Content 리액트_스터디_내용 = new Content("디우의 이것이 리액트다.", "리액트 스터디 요약", "리액트 스터디 썸네일", "리액트 스터디 설명입니다.");
    public static final Participants 리액트_스터디_참가자들 = new Participants(디우_아이디, Set.of(짱구_아이디, 그린론_아이디, 베루스_아이디));
    public static final RecruitPlanner 리액트_스터디_모집계획 = new RecruitPlanner(5, RECRUITMENT_START, LocalDate.now());
    public static final StudyPlanner 리액트_스터디_계획 = new StudyPlanner(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), PREPARE);
    public static final AttachedTags 리액트_스터디_태그 = new AttachedTags(List.of(new AttachedTag(우테코4기_태그_아이디), new AttachedTag(FE_태그_아이디), new AttachedTag(리액트_태그_아이디)));
    public static final Study 리액트_스터디 = new Study(리액트_스터디_내용, 리액트_스터디_참가자들, 리액트_스터디_모집계획,
            리액트_스터디_계획, 리액트_스터디_태그, LocalDateTime.now());
    public static final StudyResponse 리액트_스터디_응답 = new StudyResponse(리액트_스터디_아이디, 리액트_스터디_내용.getTitle(), 리액트_스터디_내용.getExcerpt(),
            리액트_스터디_내용.getThumbnail(), 리액트_스터디_모집계획.getRecruitStatus().name(), List.of(우테코4기_태그_요약, FE_태그_요약, 리액트_태그_요약));
    
    /* 자바스크립트스크립트 스터디 */
    public static final Member 자바스크립트_스터디장 = new Member(그린론_아이디, 그린론_깃허브_아이디, 그린론_유저네임, 그린론_이미지, 그린론_프로필);
    public static final Long 자바스크립트_스터디_아이디 = 3L;
    public static final Content 자바스크립트_스터디_내용 = new Content("그린론의 모던 자바스크립트 인 액션", "자바스크립트 스터디 요약", "자바스크립트 스터디 썸네일", "자바스크립트 스터디 설명입니다.");
    public static final Participants 자바스크립트_스터디_참가자들 = new Participants(그린론_아이디, Set.of(디우_아이디, 베루스_아이디));
    public static final RecruitPlanner 자바스크립트_스터디_모집계획 = new RecruitPlanner(20, RECRUITMENT_START, LocalDate.now());
    public static final StudyPlanner 자바스크립트_스터디_계획 = new StudyPlanner(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), PREPARE);
    public static final AttachedTags 자바스크립트_스터디_태그 = new AttachedTags(List.of(new AttachedTag(우테코4기_태그_아이디), new AttachedTag(FE_태그_아이디)));
    public static final Study 자바스크립트_스터디 = new Study(자바스크립트_스터디_내용, 자바스크립트_스터디_참가자들, 자바스크립트_스터디_모집계획,
            자바스크립트_스터디_계획, 자바스크립트_스터디_태그, LocalDateTime.now());
    public static final StudyResponse 자바스크립트_스터디_응답 = new StudyResponse(자바스크립트_스터디_아이디, 자바스크립트_스터디_내용.getTitle(), 자바스크립트_스터디_내용.getExcerpt(),
            자바스크립트_스터디_내용.getThumbnail(), 자바스크립트_스터디_모집계획.getRecruitStatus().name(), List.of(우테코4기_태그_요약, FE_태그_요약));

    /* HTTP 스터디 */
    public static final Member HTTP_스터디장 = new Member(디우_아이디, 디우_깃허브_아이디, 디우_유저네임, 디우_이미지, 디우_프로필);
    public static final Long HTTP_스터디_아이디 = 4L;
    public static final Content HTTP_스터디_내용 = new Content("디우의 HTTP", "HTTP 스터디 요약", "HTTP 스터디 썸네일", "HTTP 스터디 설명입니다.");
    public static final Participants HTTP_스터디_참가자들 = new Participants(디우_아이디, Set.of(베루스_아이디, 짱구_아이디));
    public static final RecruitPlanner HTTP_스터디_모집계획 = new RecruitPlanner(4, RECRUITMENT_END, LocalDate.now());
    public static final StudyPlanner HTTP_스터디_계획 = new StudyPlanner(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), PREPARE);
    public static final AttachedTags HTTP_스터디_태그 = new AttachedTags(List.of(new AttachedTag(우테코4기_태그_아이디), new AttachedTag(BE_태그_아이디)));
    public static final Study HTTP_스터디 = new Study(HTTP_스터디_내용, HTTP_스터디_참가자들, HTTP_스터디_모집계획,
            HTTP_스터디_계획, HTTP_스터디_태그, LocalDateTime.now());
    public static final StudyResponse HTTP_스터디_응답 = new StudyResponse(HTTP_스터디_아이디, HTTP_스터디_내용.getTitle(), HTTP_스터디_내용.getExcerpt(),
            HTTP_스터디_내용.getThumbnail(), HTTP_스터디_모집계획.getRecruitStatus().name(), List.of(우테코4기_태그_요약, BE_태그_요약));
    
    /* 알고리즘 스터디 */
    public static final Member 알고리즘_스터디장 = new Member(베루스_아이디, 베루스_깃허브_아이디, 베루스_유저네임, 베루스_이미지, 베루스_프로필);
    public static final Long 알고리즘_스터디_아이디 = 5L;
    public static final Content 알고리즘_스터디_내용 = new Content("알고리즘 주도 개발 1타 강사 베루스", "알고리즘 스터디 요약", "알고리즘 스터디 썸네일", "알고리즘 스터디 설명입니다.");
    public static final Participants 알고리즘_스터디_참가자들 = new Participants(베루스_아이디, Set.of(그린론_아이디, 디우_아이디));
    public static final RecruitPlanner 알고리즘_스터디_모집계획 = new RecruitPlanner(10, RECRUITMENT_END, LocalDate.now());
    public static final StudyPlanner 알고리즘_스터디_계획 = new StudyPlanner(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), PREPARE);
    public static final AttachedTags 알고리즘_스터디_태그 = new AttachedTags(List.of());
    public static final Study 알고리즘_스터디 = new Study(알고리즘_스터디_내용, 알고리즘_스터디_참가자들, 알고리즘_스터디_모집계획,
            알고리즘_스터디_계획, 알고리즘_스터디_태그, LocalDateTime.now());
    public static final StudyResponse 알고리즘_스터디_응답 = new StudyResponse(알고리즘_스터디_아이디, 알고리즘_스터디_내용.getTitle(), 알고리즘_스터디_내용.getExcerpt(),
            알고리즘_스터디_내용.getThumbnail(), 알고리즘_스터디_모집계획.getRecruitStatus().name(), List.of());
    
    /* 리눅스 스터디 */
    public static final Member 리눅스_스터디장 = new Member(베루스_아이디, 베루스_깃허브_아이디, 베루스_유저네임, 베루스_이미지, 베루스_프로필);
    public static final Long 리눅스_스터디_아이디 = 6L;
    public static final Content 리눅스_스터디_내용 = new Content("벨우스의 린우스", "리눅스 스터디 요약", "리눅스 스터디 썸네일", "리눅스 스터디 설명입니다.");
    public static final Participants 리눅스_스터디_참가자들 = new Participants(베루스_아이디, Set.of(그린론_아이디, 디우_아이디));
    public static final RecruitPlanner 리눅스_스터디_모집계획 = new RecruitPlanner(10, RECRUITMENT_START, LocalDate.now());
    public static final StudyPlanner 리눅스_스터디_계획 = new StudyPlanner(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), PREPARE);
    public static final AttachedTags 리눅스_스터디_태그 = new AttachedTags(List.of());
    public static final Study 리눅스_스터디 = new Study(리눅스_스터디_내용, 리눅스_스터디_참가자들, 리눅스_스터디_모집계획,
            리눅스_스터디_계획, 리눅스_스터디_태그, LocalDateTime.now());
    public static final StudyResponse 리눅스_스터디_응답 = new StudyResponse(리눅스_스터디_아이디, 리눅스_스터디_내용.getTitle(), 리눅스_스터디_내용.getExcerpt(),
            리눅스_스터디_내용.getThumbnail(), 리눅스_스터디_모집계획.getRecruitStatus().name(), List.of());


    public static Study 자바_스터디() {
        return new Study(자바_스터디_내용, 자바_스터디_참가자들, 자바_스터디_모집계획,
                자바_스터디_계획, 자바_스터디_태그, LocalDateTime.now());
    }

    public static Study 리액트_스터디() {
        return new Study(리액트_스터디_내용, 리액트_스터디_참가자들, 리액트_스터디_모집계획,
                리액트_스터디_계획, 리액트_스터디_태그, LocalDateTime.now());
    }

    public static Study 자바스크립트_스터디() {
        return new Study(자바스크립트_스터디_내용, 자바스크립트_스터디_참가자들, 자바스크립트_스터디_모집계획,
                자바스크립트_스터디_계획, 자바스크립트_스터디_태그, LocalDateTime.now());
    }

    public static Study HTTP_스터디() {
        return new Study(HTTP_스터디_내용, HTTP_스터디_참가자들, HTTP_스터디_모집계획,
                HTTP_스터디_계획, HTTP_스터디_태그, LocalDateTime.now());
    }

    public static Study 알고리즘_스터디() {
        return new Study(알고리즘_스터디_내용, 알고리즘_스터디_참가자들, 알고리즘_스터디_모집계획,
                알고리즘_스터디_계획, 알고리즘_스터디_태그, LocalDateTime.now());
    }

    public static Study 리눅스_스터디() {
        return new Study(리눅스_스터디_내용, 리눅스_스터디_참가자들, 리눅스_스터디_모집계획,
                리눅스_스터디_계획, 리눅스_스터디_태그, LocalDateTime.now());
    }

}
