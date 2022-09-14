package com.woowacourse.moamoa.comment.service;

import static java.util.stream.Collectors.toList;

import com.woowacourse.moamoa.comment.domain.AssociatedCommunity;
import com.woowacourse.moamoa.comment.domain.Author;
import com.woowacourse.moamoa.comment.domain.Comment;
import com.woowacourse.moamoa.comment.domain.repository.CommentRepository;
import com.woowacourse.moamoa.comment.query.CommentDao;
import com.woowacourse.moamoa.comment.query.data.CommentData;
import com.woowacourse.moamoa.comment.service.exception.CommentNotFoundException;
import com.woowacourse.moamoa.comment.service.request.CommentRequest;
import com.woowacourse.moamoa.comment.service.request.EditingCommentRequest;
import com.woowacourse.moamoa.comment.service.response.CommentsResponse;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.study.query.data.MyStudySummaryData;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final MyStudyDao myStudyDao;
    private final CommentDao commentDao;

    public Long writeComment(final Long memberId, final Long studyId, final Long communityId,
                                        final CommentRequest request) {
        final List<MyStudySummaryData> myStudies = myStudyDao.findMyStudyByMemberId(memberId);
        validateAuthor(studyId, myStudies);

        final Comment comment = new Comment(new Author(memberId), new AssociatedCommunity(communityId),
                request.getContent());

        final Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }

    @Transactional(readOnly = true)
    public CommentsResponse getComments(final Long communityId, final Pageable pageable) {
        final List<CommentData> comments = commentDao.findAllByArticleId(communityId, pageable);

        return CommentsResponse.from(comments);
    }

    public void update(final Long memberId, final Long commentId, final EditingCommentRequest editingCommentRequest) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        comment.updateContent(new Author(member.getId()), editingCommentRequest.getContent());
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
