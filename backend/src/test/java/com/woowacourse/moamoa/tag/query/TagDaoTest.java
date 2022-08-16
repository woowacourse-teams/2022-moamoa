package com.woowacourse.moamoa.tag.query;

import static com.woowacourse.fixtures.MemberFixtures.그린론;
import static com.woowacourse.fixtures.MemberFixtures.디우;
import static com.woowacourse.fixtures.MemberFixtures.베루스;
import static com.woowacourse.fixtures.MemberFixtures.짱구;
import static com.woowacourse.fixtures.StudyFixtures.HTTP_스터디;
import static com.woowacourse.fixtures.StudyFixtures.리눅스_스터디;
import static com.woowacourse.fixtures.StudyFixtures.리액트_스터디;
import static com.woowacourse.fixtures.StudyFixtures.알고리즘_스터디;
import static com.woowacourse.fixtures.StudyFixtures.자바_스터디;
import static com.woowacourse.fixtures.StudyFixtures.자바스크립트_스터디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.tag.query.request.CategoryIdRequest;
import com.woowacourse.moamoa.tag.query.response.TagData;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class TagDaoTest {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private EntityManager entityManager;

    private Member 짱구;
    private Member 그린론;
    private Member 디우;
    private Member 베루스;

    private Study 자바_스터디;
    private Study 리액트_스터디;
    private Study 자바스크립트_스터디;
    private Study HTTP_스터디;
    private Study 알고리즘_스터디;
    private Study 리눅스_스터디;

    @BeforeEach
    void initDataBase() {
        짱구 = memberRepository.save(짱구());
        그린론 = memberRepository.save(그린론());
        디우 = memberRepository.save(디우());
        베루스 = memberRepository.save(베루스());

        자바_스터디 = studyRepository.save(자바_스터디(짱구.getId(), Set.of(그린론.getId(), 디우.getId())));
        리액트_스터디 = studyRepository.save(리액트_스터디(디우.getId(), Set.of(짱구.getId(), 그린론.getId(), 베루스.getId())));
        자바스크립트_스터디 = studyRepository.save(자바스크립트_스터디(그린론.getId(), Set.of(디우.getId(), 베루스.getId())));
        HTTP_스터디 = studyRepository.save(HTTP_스터디(디우.getId(), Set.of(베루스.getId(), 짱구.getId())));
        알고리즘_스터디 = studyRepository.save(알고리즘_스터디(베루스.getId(), Set.of(그린론.getId(), 디우.getId())));
        리눅스_스터디 = studyRepository.save(리눅스_스터디(베루스.getId(), Set.of(그린론.getId(), 디우.getId())));

        entityManager.flush();
    }

    @DisplayName("필터 없이 조회시 태그 목록 전체를 조회한다.")
    @Test
    void findAllByBlankTagName() {
        List<TagData> tagData = tagDao.searchByShortNameAndCategoryId("", CategoryIdRequest.empty());

        assertThat(tagData)
                .hasSize(5)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject"),
                        tuple(2L, "4기", "우테코4기", 1L, "generation"),
                        tuple(3L, "BE", "백엔드", 2L, "area"),
                        tuple(4L, "FE", "프론트엔드", 2L, "area"),
                        tuple(5L, "React", "리액트", 3L, "subject")
                );
    }

    @DisplayName("대소문자 구분없이 태그 이름으로 조회한다.")
    @Test
    void findAllByNameContainingIgnoreCase() {
        List<TagData> tagData = tagDao.searchByShortNameAndCategoryId("ja", CategoryIdRequest.empty());

        assertThat(tagData)
                .hasSize(1)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject")
                );
    }

    @DisplayName("카테고리로 태그를 조회한다.")
    @Test
    void findAllByCategory() {
        List<TagData> tagData = tagDao.searchByShortNameAndCategoryId("", new CategoryIdRequest(3L));

        assertThat(tagData)
                .hasSize(2)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject"),
                        tuple(5L, "React", "리액트", 3L, "subject")
                );
    }

    @DisplayName("카테고리와 이름으로 태그를 조회한다.")
    @Test
    void findAllByCategoryAndName() {
        List<TagData> tagData = tagDao.searchByShortNameAndCategoryId("ja", new CategoryIdRequest(3L));

        assertThat(tagData)
                .hasSize(1)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject")
                );
    }

    @DisplayName("스터디에 부여된 태그 목록을 조회한다.")
    @Test
    void getAttachedTagsByStudyId() {
        // Java 스터디에 부착된 태그 : Java, 4기, BE
        final List<TagData> attachedTags = tagDao.findTagsByStudyId(자바_스터디.getId());

        assertThat(attachedTags)
                .hasSize(3)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactlyInAnyOrder(
                        tuple(1L, "Java", "자바", 3L, "subject"),
                        tuple(2L, "4기", "우테코4기", 1L, "generation"),
                        tuple(3L, "BE", "백엔드", 2L, "area")
                );
    }

    @DisplayName("스터디에 부여된 태그가 없는 경우 빈 목록을 조회한다.")
    @Test
    void getEmptyAttachedTagsByStudyId() {
        final List<TagData> attachedTags = tagDao.findTagsByStudyId(6L);

        assertThat(attachedTags).isEmpty();
    }
}
