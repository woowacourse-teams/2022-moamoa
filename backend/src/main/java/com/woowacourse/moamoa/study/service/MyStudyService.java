package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.member.query.MemberDao;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.study.query.data.MyStudyData;
import com.woowacourse.moamoa.study.query.data.MyStudySummaryData;
import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;
import com.woowacourse.moamoa.tag.query.TagDao;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyStudyService {

    private final MyStudyDao myStudyDao;
    private final MemberDao memberDao;
    private final TagDao tagDao;

    public MyStudiesResponse getStudies(final Long githubId) {
        if (!memberDao.isExistByGithubId(githubId)) {
            throw new UnauthorizedException(String.format("%d의 githubId를 가진 사용자는 없습니다.", githubId));
        }

        final List<MyStudySummaryData> myStudySummaryData = myStudyDao.findMyStudyByGithubId(githubId);

        final List<Long> studyIds = myStudySummaryData.stream()
                .map(MyStudySummaryData::getId)
                .collect(Collectors.toList());

        final List<MemberData> owners = memberDao.findOwnerByStudyIds(studyIds);

        final List<List<TagSummaryData>> tags = studyIds.stream()
                .map(tagDao::findTagsByStudyId)
                .map(tagsData -> tagsData.stream().map(
                                tagData -> new TagSummaryData(tagData.getId(), tagData.getName()))
                        .collect(Collectors.toList())
                )
                .collect(Collectors.toList());

        return new MyStudiesResponse(IntStream.range(0, myStudySummaryData.size())
                .boxed()
                .map(idx -> new MyStudyData(myStudySummaryData.get(idx), owners.get(idx), tags.get(idx)))
                .collect(Collectors.toList()));
    }
}
