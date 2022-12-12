package com.woowacourse.moamoa.studyroom.service.response;

import static java.util.stream.Collectors.toList;

import com.woowacourse.moamoa.studyroom.query.data.CommentData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class CommentsResponse {

    private List<CommentResponse> comments;
    private long totalCount;
    private boolean hasNext;

    public static CommentsResponse from(final List<CommentData> comments, final boolean hasNext, final long totalCount) {
        final List<CommentResponse> commentResponses = comments.stream()
                .map(CommentResponse::new)
                .collect(toList());

        return new CommentsResponse(commentResponses, totalCount, hasNext);
    }
}
