package com.woowacourse.moamoa.comment.repository;

import com.woowacourse.moamoa.comment.domain.Comment;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(Long id);

    Comment save(Comment comment);
}
