package com.woowacourse.moamoa.review.query;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.review.query.data.ReviewData;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
class ReviewDaoTest {

    private static final MemberData JJANGGU = new MemberData(1L, "jjanggu", "https://image", "github.com");
    private static final MemberData GREENLAWN = new MemberData(2L, "greenlawn", "https://image", "github.com");
    private static final MemberData DWOO = new MemberData(3L, "dwoo", "https://image", "github.com");
    private static final MemberData VERUS = new MemberData(4L, "verus", "https://image", "github.com");

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReviewDao sut;

    private Study javaStudy;

    private Study reactStudy;

    private List<ReviewData> javaReviews;

    private List<ReviewData> reactReviews;

    @BeforeEach
    void setUp() {
        // 사용자 추가
        final Member jjanggu = memberRepository.save(toMember(JJANGGU));
        final Member greenlawn = memberRepository.save(toMember(GREENLAWN));
        final Member dwoo = memberRepository.save(toMember(DWOO));
        final Member verus = memberRepository.save(toMember(VERUS));

        // 스터디 생성
        StudyService createStudyService = new StudyService(studyRepository, memberRepository);

        final LocalDate startDate = LocalDate.now();
        CreatingStudyRequest javaStudyRequest = CreatingStudyRequest.builder()
                .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개")
                .startDate(startDate)
                .build();
        CreatingStudyRequest reactStudyRequest = CreatingStudyRequest.builder()
                .title("react 스터디").excerpt("리액트 설명").thumbnail("react image").description("리액트 소개")
                .startDate(startDate)
                .build();

        javaStudy = createStudyService.createStudy(1L, javaStudyRequest);
        reactStudy = createStudyService.createStudy(1L, reactStudyRequest);

        final LocalDate createdDate = startDate.plusDays(1);
        final LocalDate lastModifiedDate = startDate.plusDays(2);

        entityManager.flush();
        entityManager.clear();

        // 리뷰 추가
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (1, " + javaStudy.getId() + ", " + jjanggu.getId() + ", '리뷰 내용1', '"
                + createdDate.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (2, " + javaStudy.getId() + ", " + greenlawn.getId() + ", '리뷰 내용2', '"
                + createdDate.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (3, " + javaStudy.getId() + ", " + dwoo.getId()+ ", '리뷰 내용3', '"
                + createdDate.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (4, " + javaStudy.getId() + ", " + verus.getId() + ", '리뷰 내용4', '"
                + createdDate.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (5, " + reactStudy.getId() + ", " + jjanggu.getId() + ", '리뷰 내용5', '"
                + createdDate.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (6, " + reactStudy.getId() + ", " + greenlawn.getId()+ ", '리뷰 내용6', '"
                + createdDate.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (7, " + reactStudy.getId() + ", " + dwoo.getId()+ ", '리뷰 내용7', '"
                + createdDate.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");

        javaReviews = List.of(
                new ReviewData(1L, JJANGGU, createdDate, lastModifiedDate, "리뷰 내용1"),
                new ReviewData(2L, GREENLAWN, createdDate, lastModifiedDate, "리뷰 내용2"),
                new ReviewData(3L, DWOO, createdDate, lastModifiedDate, "리뷰 내용3"),
                new ReviewData(4L, VERUS, createdDate, lastModifiedDate, "리뷰 내용4")
        );

        reactReviews = List.of(
                new ReviewData(5L, JJANGGU, createdDate, lastModifiedDate, "리뷰 내용5"),
                new ReviewData(6L, GREENLAWN, createdDate, lastModifiedDate, "리뷰 내용6"),
                new ReviewData(7L, DWOO, createdDate, lastModifiedDate, "리뷰 내용7")
        );
    }

    private static Member toMember(MemberData memberData) {
        return new Member(memberData.getGithubId(), memberData.getUsername(), memberData.getImageUrl(),
                memberData.getProfileUrl());
    }

    @DisplayName("스터디 Id로 작성된 후기를 조회한다.")
    @Test
    void findAllReviewsByStudyId() {
        List<ReviewData> reviews = sut.findAllByStudyId(javaStudy.getId());

        assertThat(reviews).isNotEmpty();
        assertThat(reviews).hasSize(4);
        assertThat(reviews).containsExactlyInAnyOrderElementsOf(javaReviews);
    }
}
