package com.woowacourse.moamoa.study.service;

import static java.util.stream.Collectors.toList;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.study.domain.Participant;
import com.woowacourse.moamoa.study.domain.AttachedTag;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.exception.StudyNotExistException;
import com.woowacourse.moamoa.study.service.response.StudyDetailResponse;
import com.woowacourse.moamoa.tag.domain.Tag;
import com.woowacourse.moamoa.tag.domain.repository.TagRepository;
import com.woowacourse.moamoa.tag.query.response.TagResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyDetailService {

    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

    public StudyDetailResponse getStudyDetails(final Long studyId) {
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotExistException::new);

        final MemberResponse owner = MemberResponse.from(study.getOwner());
        final List<MemberResponse> participantsResponse = getParticipantsResponse(study);
        final List<TagResponse> tagsResponse = getTagsResponse(study);

        return new StudyDetailResponse(study, owner, participantsResponse, tagsResponse);
    }

    private List<MemberResponse> getParticipantsResponse(final Study study) {
        final List<Long> participantIds = getParticipantIds(study);

        final List<Member> participants = memberRepository.findAllById(participantIds);
        return participants.stream()
                .map(MemberResponse::from)
                .collect(toList());
    }

    private List<Long> getParticipantIds(final Study study) {
        final List<Participant> participants = study.getParticipants().getParticipants();
        return participants.stream()
                .map(Participant::getMemberId)
                .collect(toList());
    }

    private List<TagResponse> getTagsResponse(final Study study) {
        final List<Long> tagIds = getTagIds(study);

        final List<Tag> tags = tagRepository.findAllById(tagIds);
        return tags.stream()
                .map(TagResponse::new)
                .collect(toList());
    }

    private List<Long> getTagIds(final Study study) {
        final List<AttachedTag> attachedTags = study.getAttachedTags();
        return attachedTags
                .stream()
                .map(AttachedTag::getTagId)
                .collect(toList());
    }
}
