package com.woowacourse.moamoa.comment.domain.repository;

import com.woowacourse.moamoa.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaCommentRepository  extends JpaRepository<Comment, Long>, CommentRepository {
}
