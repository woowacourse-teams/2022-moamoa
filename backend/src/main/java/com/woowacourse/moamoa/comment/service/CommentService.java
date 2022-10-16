package com.woowacourse.moamoa.comment.service;

import com.woowacourse.moamoa.comment.domain.Author;
import com.woowacourse.moamoa.comment.domain.Comment;
import com.woowacourse.moamoa.comment.domain.repository.CommentRepository;
import com.woowacourse.moamoa.comment.query.CommentDao;
import com.woowacourse.moamoa.comment.query.data.CommentData;
import com.woowacourse.moamoa.comment.service.exception.CommentNotFoundException;
import com.woowacourse.moamoa.comment.service.request.CommentRequest;
import com.woowacourse.moamoa.comment.service.request.EditingCommentRequest;
import com.woowacourse.moamoa.comment.service.response.CommentsResponse;
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
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        comment.updateContent(studyId, new Author(memberId), editingCommentRequest.getContent());
    }

    public void delete(final Long memberId, final Long studyId, final Long commentId) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        comment.checkDeletePermission(studyId, new Author(memberId));
        commentRepository.deleteById(comment.getId());
    }
}
