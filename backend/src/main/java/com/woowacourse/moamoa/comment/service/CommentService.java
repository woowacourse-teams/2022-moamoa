package com.woowacourse.moamoa.comment.service;

import static java.util.stream.Collectors.toList;

import com.woowacourse.moamoa.comment.domain.AssociatedCommunity;
import com.woowacourse.moamoa.comment.domain.Author;
import com.woowacourse.moamoa.comment.domain.Comment;
import com.woowacourse.moamoa.comment.repository.CommentRepository;
import com.woowacourse.moamoa.comment.service.request.CommentRequest;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.study.query.data.MyStudySummaryData;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MyStudyDao myStudyDao;

    public Long writeComment(final Long memberId, final Long studyId, final Long communityId,
                                        final CommentRequest request) {
        final List<MyStudySummaryData> myStudies = myStudyDao.findMyStudyByMemberId(memberId);
        validateAuthor(studyId, myStudies);

        final Comment comment = new Comment(new Author(memberId), new AssociatedCommunity(communityId),
                request.getContent());

        final Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }

    private void validateAuthor(final Long studyId, final List<MyStudySummaryData> myStudies) {
        final List<Long> myStudyIds = myStudies.stream()
                .map(MyStudySummaryData::getId)
                .collect(toList());

        if (!myStudyIds.contains(studyId)) {
            throw new IllegalArgumentException("댓글 작성 권한이 없습니다.");
        }
    }
}
