package com.woowacourse.moamoa.comment.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMemberId;
import com.woowacourse.moamoa.comment.service.CommentService;
import com.woowacourse.moamoa.comment.service.request.CommentRequest;
import com.woowacourse.moamoa.comment.service.request.EditingCommentRequest;
import com.woowacourse.moamoa.comment.service.response.CommentsResponse;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies/{study-id}/community/articles/{article-id}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@AuthenticatedMemberId final Long memberId,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("article-id") final Long communityId,
                                              @Valid @RequestBody final CommentRequest request
    ) {
        final Long commentId = commentService.writeComment(memberId, studyId, communityId, request);

        final URI location = URI.create(
                "/api/studies/" + studyId + "/community/articles/" + communityId + "/comments/" + commentId);
        return ResponseEntity.created(location)
                .header("Access-Control-Allow-Headers", HttpHeaders.LOCATION)
                .build();
    }

    @GetMapping
    public ResponseEntity<CommentsResponse> getComments(@PathVariable("article-id") final Long communityId,
                                                       @PageableDefault(size = 8) final Pageable pageable
    ) {
        final CommentsResponse commentsResponse = commentService.getComments(communityId, pageable);
        return ResponseEntity.ok(commentsResponse);
    }

    @PutMapping("/{comment-id}")
    public ResponseEntity<Void> updateComment(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("comment-id") final Long commentId,
            @Valid @RequestBody final EditingCommentRequest editingCommentRequest
    ) {
        commentService.update(memberId, studyId, commentId, editingCommentRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity<Void> deleteComment(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("comment-id") final Long commentId
    ) {
        commentService.delete(memberId, studyId, commentId);
        return ResponseEntity.noContent().build();
    }
}