package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.study.query.data.StudyOwnerWithTagsData;
import com.woowacourse.moamoa.study.service.response.MyStudyResponse;
import com.woowacourse.moamoa.study.query.data.MyStudySummaryData;
import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;

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

    public MyStudiesResponse getStudies(final Long githubId) {
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);

        final List<MyStudySummaryData> myStudySummaryData = myStudyDao.findMyStudyByMemberId(member.getId());

        final List<Long> studyIds = myStudySummaryData.stream()
                .map(MyStudySummaryData::getId)
                .collect(Collectors.toList());

        final Map<Long, StudyOwnerWithTagsData> studyOwnerWithTags = myStudyDao.findStudyOwnerWithTags(studyIds);

        return new MyStudiesResponse(mapToResponse(myStudySummaryData, studyOwnerWithTags));
    }

    private List<MyStudyResponse> mapToResponse(final List<MyStudySummaryData> myStudySummaryData,
                                                final Map<Long, StudyOwnerWithTagsData> ownerWithStudyTags) {
        return myStudySummaryData.stream()
                .map(studySummary -> new MyStudyResponse(
                        studySummary, ownerWithStudyTags.get(studySummary.getId()).getOwner(),
                        ownerWithStudyTags.get(studySummary.getId()).getTags()
                ))
                .collect(Collectors.toList());
    }
}
