package com.woowacourse.moamoa.comment.service;

import static java.util.stream.Collectors.toList;

import com.woowacourse.moamoa.comment.domain.Author;
import com.woowacourse.moamoa.comment.domain.Comment;
import com.woowacourse.moamoa.comment.domain.repository.CommentRepository;
import com.woowacourse.moamoa.comment.query.CommentDao;
import com.woowacourse.moamoa.comment.query.data.CommentData;
import com.woowacourse.moamoa.comment.service.exception.CommentNotFoundException;
import com.woowacourse.moamoa.comment.service.exception.UnDeletionCommentException;
import com.woowacourse.moamoa.comment.service.request.CommentRequest;
import com.woowacourse.moamoa.comment.service.request.EditingCommentRequest;
import com.woowacourse.moamoa.comment.service.response.CommentsResponse;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.study.query.data.MyStudySummaryData;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.article.repository.ArticleRepository;
import com.woowacourse.moamoa.studyroom.service.exception.ArticleNotFoundException;
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
    private final ArticleRepository articleRepository;
    private final CommentDao commentDao;
    private final MyStudyDao myStudyDao;

    public Long writeComment(final Long memberId, final Long studyId, final Long articleId, final ArticleType type,
                             final CommentRequest request) {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, type.name()));

        final Comment comment = article.writeComment(new Accessor(memberId, studyId), request.getContent());

        final Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }

    @Transactional(readOnly = true)
    public CommentsResponse getComments(final Long articleId, final Pageable pageable) {
        final List<CommentData> comments = commentDao.findAllByArticleId(articleId, pageable);

        return CommentsResponse.from(comments);
    }

    public void update(final Long memberId, final Long studyId, final Long commentId,
                       final EditingCommentRequest editingCommentRequest) {
        validateParticipantStudy(studyId, memberId);

        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        comment.updateContent(new Author(memberId), editingCommentRequest.getContent());
    }

    public void delete(final Long memberId, final Long studyId, final Long commentId) {
        validateParticipantStudy(studyId, memberId);

        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        checkDeletePermission(memberId, comment);
        commentRepository.deleteById(comment.getId());
    }

    private static void checkDeletePermission(final Long memberId, final Comment comment) {
        if (!comment.isAuthor(new Author(memberId))) {
            throw new UnDeletionCommentException();
        }
    }

    private void validateParticipantStudy(final Long studyId, final Long memberId) {
        final List<MyStudySummaryData> myStudies = myStudyDao.findMyStudyByMemberId(memberId);
        final List<Long> myStudyIds = myStudies.stream()
                .map(MyStudySummaryData::getId)
                .collect(toList());

        if (!myStudyIds.contains(studyId)) {
            throw new IllegalArgumentException("스터디를 탈퇴하여 댓글 작성 및 수정 권한이 없습니다.");
        }
    }
}
