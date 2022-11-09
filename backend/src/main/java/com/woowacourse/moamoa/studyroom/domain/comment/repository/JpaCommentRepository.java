package com.woowacourse.moamoa.studyroom.domain.comment.repository;

import com.woowacourse.moamoa.studyroom.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaCommentRepository  extends JpaRepository<Comment, Long>, CommentRepository {
}
