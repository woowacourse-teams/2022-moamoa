package com.woowacourse.moamoa.studyroom.domain.comment.repository;

import com.woowacourse.moamoa.studyroom.domain.comment.Comment;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(Long id);

    Comment save(Comment comment);

    void deleteById(Long id);
}
