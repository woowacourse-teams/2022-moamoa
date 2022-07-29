package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.study.service.response.MyStudyResponse;
import com.woowacourse.moamoa.study.query.data.MyStudySummaryData;
import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;

import java.util.ArrayList;
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
        if (!memberRepository.existsByGithubId(githubId)) {
            throw new UnauthorizedException(String.format("%d의 githubId를 가진 사용자는 없습니다.", githubId));
        }

        final List<MyStudySummaryData> myStudySummaryData = myStudyDao.findMyStudyByGithubId(githubId);

        final List<Long> studyIds = myStudySummaryData.stream()
                .map(MyStudySummaryData::getId)
                .collect(Collectors.toList());

        final Map<Long, Map<MemberData, List<TagSummaryData>>> ownerWithStudyTags = myStudyDao.findStudyOwnerWithTags(studyIds);

        return new MyStudiesResponse(mapToResponse(myStudySummaryData, ownerWithStudyTags));
    }

    private List<MyStudyResponse> mapToResponse(final List<MyStudySummaryData> myStudySummaryData,
                                                final Map<Long, Map<MemberData, List<TagSummaryData>>> ownerWithStudyTags) {

        List<MyStudyResponse> myStudyData = new ArrayList<>();
        for (MyStudySummaryData studyData : myStudySummaryData) {
            final Map<MemberData, List<TagSummaryData>> ownerWithTag = ownerWithStudyTags.get(studyData.getId());

            for (MemberData owner : ownerWithTag.keySet()) {
                myStudyData.add(new MyStudyResponse(studyData, owner, ownerWithTag.get(owner)));
            }
        }
        return myStudyData;
    }
}
