package com.woowacourse.moamoa.fixtures;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론_유저네임;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론_이미지;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론_프로필;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우_유저네임;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우_이미지;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우_프로필;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스_유저네임;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스_이미지;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스_프로필;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구_유저네임;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구_이미지;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구_프로필;
import static com.woowacourse.moamoa.fixtures.TagFixtures.BE_태그_아이디;
import static com.woowacourse.moamoa.fixtures.TagFixtures.FE_태그_아이디;
import static com.woowacourse.moamoa.fixtures.TagFixtures.리액트_태그_아이디;
import static com.woowacourse.moamoa.fixtures.TagFixtures.우테코4기_태그_아이디;
import static com.woowacourse.moamoa.fixtures.TagFixtures.자바_태그_아이디;
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
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class StudyFixtures {

    /* 자바 스터디 */
    public static final Member 자바_스터디장 = new Member(짱구_아이디, 짱구_깃허브_아이디, 짱구_유저네임, 짱구_이미지, 짱구_프로필);
    public static final Content 자바_스터디_내용 = new Content("신짱구의 자바의 정석", "자바 스터디 요약", "자바 스터디 썸네일", "자바 스터디 설명입니다.");
    public static final Participants 자바_스터디_참가자들 = new Participants(짱구_아이디, Set.of(그린론_아이디, 디우_아이디));
    public static final RecruitPlanner 자바_스터디_모집계획 = new RecruitPlanner(10, RECRUITMENT_START, LocalDate.now());
    public static final StudyPlanner 자바_스터디_계획 = new StudyPlanner(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), IN_PROGRESS);
    public static final AttachedTags 자바_스터디_태그 = new AttachedTags(List.of(new AttachedTag(자바_태그_아이디), new AttachedTag(우테코4기_태그_아이디), new AttachedTag(BE_태그_아이디)));
    public static final Study 자바_스터디 = new Study(자바_스터디_내용, 자바_스터디_참가자들, 자바_스터디_모집계획,
            자바_스터디_계획, 자바_스터디_태그, LocalDateTime.now());

    public static Study 자바_스터디(final Long ownerId, final Set<Long> participants) {
        return new Study(자바_스터디_내용, new Participants(ownerId, participants), 자바_스터디_모집계획,
                자바_스터디_계획, 자바_스터디_태그, LocalDateTime.now());
    }

    /* 리액트 스터디 */
    public static final Member 리액트_스터디장 = new Member(디우_아이디, 디우_깃허브_아이디, 디우_유저네임, 디우_이미지, 디우_프로필);
    public static final Content 리액트_스터디_내용 = new Content("디우의 이것이 리액트다.", "리액트 스터디 요약", "리액트 스터디 썸네일", "리액트 스터디 설명입니다.");
    public static final Participants 리액트_스터디_참가자들 = new Participants(디우_아이디, Set.of(짱구_아이디, 그린론_아이디, 베루스_아이디));
    public static final RecruitPlanner 리액트_스터디_모집계획 = new RecruitPlanner(5, RECRUITMENT_START, LocalDate.now());
    public static final StudyPlanner 리액트_스터디_계획 = new StudyPlanner(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), PREPARE);
    public static final AttachedTags 리액트_스터디_태그 = new AttachedTags(List.of(new AttachedTag(우테코4기_태그_아이디), new AttachedTag(FE_태그_아이디), new AttachedTag(리액트_태그_아이디)));
    public static final Study 리액트_스터디 = new Study(리액트_스터디_내용, 리액트_스터디_참가자들, 리액트_스터디_모집계획,
            리액트_스터디_계획, 리액트_스터디_태그, LocalDateTime.now());

    public static Study 리액트_스터디(final Long ownerId, final Set<Long> participants) {
        return new Study(리액트_스터디_내용, new Participants(ownerId, participants), 리액트_스터디_모집계획,
                리액트_스터디_계획, 리액트_스터디_태그, LocalDateTime.now());
    }

    /* 자바스크립트스크립트 스터디 */
    public static final Member 자바스크립트_스터디장 = new Member(그린론_아이디, 그린론_깃허브_아이디, 그린론_유저네임, 그린론_이미지, 그린론_프로필);
    public static final Content 자바스크립트_스터디_내용 = new Content("그린론의 모던 자바스크립트 인 액션", "자바스크립트 스터디 요약", "자바스크립트 스터디 썸네일", "자바스크립트 스터디 설명입니다.");
    public static final Participants 자바스크립트_스터디_참가자들 = new Participants(그린론_아이디, Set.of(디우_아이디, 베루스_아이디));
    public static final RecruitPlanner 자바스크립트_스터디_모집계획 = new RecruitPlanner(20, RECRUITMENT_START, LocalDate.now());
    public static final StudyPlanner 자바스크립트_스터디_계획 = new StudyPlanner(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), PREPARE);
    public static final AttachedTags 자바스크립트_스터디_태그 = new AttachedTags(List.of(new AttachedTag(우테코4기_태그_아이디), new AttachedTag(FE_태그_아이디)));
    public static final Study 자바스크립트_스터디 = new Study(자바스크립트_스터디_내용, 자바스크립트_스터디_참가자들, 자바스크립트_스터디_모집계획,
            자바스크립트_스터디_계획, 자바스크립트_스터디_태그, LocalDateTime.now());

    public static Study 자바스크립트_스터디(final Long ownerId, final Set<Long> participants) {
        return new Study(자바스크립트_스터디_내용, new Participants(ownerId, participants), 자바스크립트_스터디_모집계획,
                자바스크립트_스터디_계획, 자바스크립트_스터디_태그, LocalDateTime.now());
    }

    /* HTTP 스터디 */
    public static final Member HTTP_스터디장 = new Member(디우_아이디, 디우_깃허브_아이디, 디우_유저네임, 디우_이미지, 디우_프로필);
    public static final Content HTTP_스터디_내용 = new Content("디우의 HTTP", "HTTP 스터디 요약", "HTTP 스터디 썸네일", "HTTP 스터디 설명입니다.");
    public static final Participants HTTP_스터디_참가자들 = new Participants(디우_아이디, Set.of(베루스_아이디, 짱구_아이디));
    public static final RecruitPlanner HTTP_스터디_모집계획 = new RecruitPlanner(4, RECRUITMENT_END, LocalDate.now());
    public static final StudyPlanner HTTP_스터디_계획 = new StudyPlanner(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), PREPARE);
    public static final AttachedTags HTTP_스터디_태그 = new AttachedTags(List.of(new AttachedTag(우테코4기_태그_아이디), new AttachedTag(BE_태그_아이디)));
    public static final Study HTTP_스터디 = new Study(HTTP_스터디_내용, HTTP_스터디_참가자들, HTTP_스터디_모집계획,
            HTTP_스터디_계획, HTTP_스터디_태그, LocalDateTime.now());

    public static Study HTTP_스터디(final Long ownerId, final Set<Long> participants) {
        return new Study(HTTP_스터디_내용, new Participants(ownerId, participants), HTTP_스터디_모집계획,
                HTTP_스터디_계획, HTTP_스터디_태그, LocalDateTime.now());
    }

    /* 알고리즘 스터디 (모집 기간과 스터디 종료일자가 없음) */
    public static final Member 알고리즘_스터디장 = new Member(베루스_아이디, 베루스_깃허브_아이디, 베루스_유저네임, 베루스_이미지, 베루스_프로필);
    public static final Content 알고리즘_스터디_내용 = new Content("알고리즘 주도 개발 1타 강사 베루스", "알고리즘 스터디 요약", "알고리즘 스터디 썸네일", "알고리즘 스터디 설명입니다.");
    public static final Participants 알고리즘_스터디_참가자들 = new Participants(베루스_아이디, Set.of(그린론_아이디, 디우_아이디));
    public static final RecruitPlanner 알고리즘_스터디_모집계획 = new RecruitPlanner(null, RECRUITMENT_END, null);
    public static final StudyPlanner 알고리즘_스터디_계획 = new StudyPlanner(LocalDate.now().plusMonths(1), null, PREPARE);
    public static final AttachedTags 알고리즘_스터디_태그 = new AttachedTags(List.of());
    public static final Study 알고리즘_스터디 = new Study(알고리즘_스터디_내용, 알고리즘_스터디_참가자들, 알고리즘_스터디_모집계획,
            알고리즘_스터디_계획, 알고리즘_스터디_태그, LocalDateTime.now());

    public static Study 알고리즘_스터디(final Long ownerId, final Set<Long> participants) {
        return new Study(알고리즘_스터디_내용, new Participants(ownerId, participants), 알고리즘_스터디_모집계획,
                알고리즘_스터디_계획, 알고리즘_스터디_태그, LocalDateTime.now());
    }

    /* 리눅스 스터디 (최대 인원 없음) */
    public static final Member 리눅스_스터디장 = new Member(베루스_아이디, 베루스_깃허브_아이디, 베루스_유저네임, 베루스_이미지, 베루스_프로필);
    public static final Content 리눅스_스터디_내용 = new Content("벨우스의 린우스", "리눅스 스터디 요약", "리눅스 스터디 썸네일", "리눅스 스터디 설명입니다.");
    public static final Participants 리눅스_스터디_참가자들 = new Participants(베루스_아이디, Set.of(그린론_아이디, 디우_아이디));
    public static final RecruitPlanner 리눅스_스터디_모집계획 = new RecruitPlanner(null, RECRUITMENT_START, LocalDate.now());
    public static final StudyPlanner 리눅스_스터디_계획 = new StudyPlanner(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), PREPARE);
    public static final AttachedTags 리눅스_스터디_태그 = new AttachedTags(List.of());
    public static final Study 리눅스_스터디 = new Study(리눅스_스터디_내용, 리눅스_스터디_참가자들, 리눅스_스터디_모집계획,
            리눅스_스터디_계획, 리눅스_스터디_태그, LocalDateTime.now());

    public static Study 리눅스_스터디(final Long ownerId, final Set<Long> participants) {
        return new Study(리눅스_스터디_내용, new Participants(ownerId, participants), 리눅스_스터디_모집계획,
                리눅스_스터디_계획, 리눅스_스터디_태그, LocalDateTime.now());
    }

    /* OS 스터디 */
    public static final Member OS_스터디장 = new Member(디우_아이디, 디우_깃허브_아이디, 디우_유저네임, 디우_이미지, 디우_프로필);
    public static final Content OS_스터디_내용 = new Content("디우의 OS 스터디", "OS 스터디 요약", "OS 스터디 썸네일", "OS 스터디 설명입니다.");
    public static final Participants OS_스터디_참가자들 = new Participants(디우_아이디, Set.of(그린론_아이디, 짱구_아이디, 베루스_아이디));
    public static final RecruitPlanner OS_스터디_모집계획 = new RecruitPlanner(10, RECRUITMENT_START, LocalDate.now());
    public static final StudyPlanner OS_스터디_계획 = new StudyPlanner(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), PREPARE);
    public static final AttachedTags OS_스터디_태그 = new AttachedTags(List.of(new AttachedTag(우테코4기_태그_아이디), new AttachedTag(BE_태그_아이디)));

    public static Study OS_스터디(final Long ownerId, final Set<Long> participants) {
        return new Study(OS_스터디_내용, new Participants(ownerId, participants), OS_스터디_모집계획,
                OS_스터디_계획, OS_스터디_태그, LocalDateTime.now());
    }

    public static CreatingStudyRequest 자바_스터디_신청서(LocalDate now) {
        return CreatingStudyRequest.builder()
                .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개")
                .startDate(now)
                .build();
    }

    public static CreatingStudyRequest 자바_스터디_신청서(List<Long> tagIds, int maxMemberCount, LocalDate now) {
        return CreatingStudyRequest.builder()
                .title("Java 스터디").excerpt("자바 설명").thumbnail("java thumbnail").description("그린론의 우당탕탕 자바 스터디입니다.")
                .startDate(now).tagIds(tagIds).maxMemberCount(maxMemberCount)
                .build();
    }

    public static CreatingStudyRequest 리액트_스터디_신청서(LocalDate now) {
        return CreatingStudyRequest.builder()
                .title("react 스터디").excerpt("리액트 설명").thumbnail("react image").description("리액트 소개")
                .startDate(now)
                .build();
    }

    public static CreatingStudyRequest 리액트_스터디_신청서(List<Long> tagIds, int maxMemberCount, LocalDate now) {
        return CreatingStudyRequest.builder()
                .title("React 스터디").excerpt("리액트 설명").thumbnail("react thumbnail").description("디우의 뤼액트 스터디입니다.")
                .startDate(LocalDate.now()).endDate(now).enrollmentEndDate(LocalDate.now())
                .tagIds(tagIds).maxMemberCount(maxMemberCount)
                .build();
    }

    public static CreatingStudyRequest 자바스크립트_스터디_신청서(List<Long> tagIds, LocalDate now) {
        return CreatingStudyRequest.builder()
                .title("javaScript 스터디").excerpt("자바스크립트 설명").thumbnail("javascript thumbnail").description("자바스크립트 설명")
                .startDate(now).tagIds(tagIds)
                .build();
    }

    public static CreatingStudyRequest HTTP_스터디_신청서(List<Long> tagIds, LocalDate now) {
        return CreatingStudyRequest.builder()
                .title("HTTP 스터디").excerpt("HTTP 설명").thumbnail("http thumbnail").description("HTTP 설명")
                .startDate(now).tagIds(tagIds)
                .build();
    }

    public static CreatingStudyRequest 알고리즘_스터디_신청서(List<Long> tagIds, LocalDate now) {
        return CreatingStudyRequest.builder()
                .title("알고리즘 스터디").excerpt("알고리즘 설명").thumbnail("algorithm thumbnail").description("알고리즘 설명")
                .startDate(now).tagIds(tagIds)
                .build();
    }
}
