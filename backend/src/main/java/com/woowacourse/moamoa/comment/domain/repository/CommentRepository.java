package com.woowacourse.moamoa.comment.domain.repository;

import com.woowacourse.moamoa.comment.domain.Comment;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(Long id);

    Comment save(Comment comment);

    void deleteById(Long id);
}
