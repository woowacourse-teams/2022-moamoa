package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.study.service.response.MyRoleResponse;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.query.data.StudyOwnerAndTagsData;
import com.woowacourse.moamoa.study.service.response.MyStudyResponse;
import com.woowacourse.moamoa.study.query.data.MyStudySummaryData;
import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;

import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyStudyService {

    private final MyStudyDao myStudyDao;

    private final MemberRepository memberRepository;

    private final StudyRepository studyRepository;

    public MyStudiesResponse getStudies(final Long githubId) {
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);

        final List<MyStudySummaryData> myStudySummaryData = myStudyDao.findMyStudyByMemberId(member.getId());

        final List<Long> studyIds = myStudySummaryData.stream()
                .map(MyStudySummaryData::getId)
                .collect(Collectors.toList());

        Map<Long, StudyOwnerAndTagsData> ownerWithTags = getOwnerWithTags(studyIds);

        return new MyStudiesResponse(mapToResponse(myStudySummaryData, ownerWithTags));
    }

    private Map<Long, StudyOwnerAndTagsData> getOwnerWithTags(final List<Long> studyIds) {
        final Map<Long, MemberData> owners = myStudyDao.findOwners(studyIds);
        final Map<Long, List<TagSummaryData>> tags = myStudyDao.findTags(studyIds);

        return mapOwnerWithTags(studyIds, owners, tags);
    }

    private Map<Long, StudyOwnerAndTagsData> mapOwnerWithTags(final List<Long> studyIds,
                                                              final Map<Long, MemberData> owners,
                                                              final Map<Long, List<TagSummaryData>> tags) {
        Map<Long, StudyOwnerAndTagsData> result = new HashMap<>();
        for (Long id : studyIds) {
            if (tags.get(id) == null) {
                result.put(id, new StudyOwnerAndTagsData(owners.get(id), List.of()));
                continue;
            }
            result.put(id, new StudyOwnerAndTagsData(owners.get(id), tags.get(id)));
        }
        return result;
    }

    private List<MyStudyResponse> mapToResponse(final List<MyStudySummaryData> myStudySummaryData,
                                                final Map<Long, StudyOwnerAndTagsData> ownerWithStudyTags) {
        return myStudySummaryData.stream()
                .map(studySummary -> new MyStudyResponse(
                        studySummary, ownerWithStudyTags.get(studySummary.getId()).getOwner(),
                        ownerWithStudyTags.get(studySummary.getId()).getTags()
                ))
                .collect(Collectors.toList());
    }

    public MyRoleResponse findMyRoleInStudy(final Long githubId, final Long studyId) {
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);

        return new MyRoleResponse(study.getRole(member.getId()));
    }
}
