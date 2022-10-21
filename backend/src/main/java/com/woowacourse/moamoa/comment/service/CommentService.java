package com.woowacourse.moamoa.comment.service;

import com.woowacourse.moamoa.comment.domain.Comment;
import com.woowacourse.moamoa.comment.domain.repository.CommentRepository;
import com.woowacourse.moamoa.comment.query.CommentDao;
import com.woowacourse.moamoa.comment.query.data.CommentData;
import com.woowacourse.moamoa.comment.service.exception.CommentNotFoundException;
import com.woowacourse.moamoa.comment.service.exception.UnEditingCommentException;
import com.woowacourse.moamoa.comment.service.request.CommentRequest;
import com.woowacourse.moamoa.comment.service.request.EditingCommentRequest;
import com.woowacourse.moamoa.comment.service.response.CommentsResponse;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.article.repository.ArticleRepository;
import com.woowacourse.moamoa.studyroom.service.exception.ArticleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final CommentDao commentDao;

    public Long writeComment(final Long memberId, final Long studyId, final Long articleId, final ArticleType type,
                             final CommentRequest request) {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, type.name()));

        final Accessor accessor = new Accessor(memberId, studyId);
        final Comment comment = Comment.write(accessor, article, request.getContent());

        final Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }

    @Transactional(readOnly = true)
    public CommentsResponse getComments(final Long articleId, final Pageable pageable) {
        final Slice<CommentData> comments = commentDao.findAllByArticleId(articleId, pageable);

        return CommentsResponse.from(comments.getContent(), comments.hasNext());
    }

    public void update(final Long memberId, final Long studyId, final Long commentId,
                       final EditingCommentRequest editingCommentRequest) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        comment.updateContent(new Accessor(memberId, studyId), editingCommentRequest.getContent());
    }

    public void delete(final Long memberId, final Long studyId, final Long commentId) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if (comment.isUneditableAccessor(new Accessor(memberId, studyId))) {
            throw new UnEditingCommentException();
        }
        commentRepository.deleteById(comment.getId());
    }
}
