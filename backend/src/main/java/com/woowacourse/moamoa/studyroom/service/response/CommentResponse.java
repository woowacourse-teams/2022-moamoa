package com.woowacourse.moamoa.studyroom.service.response;

import com.woowacourse.moamoa.studyroom.query.data.CommentData;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class CommentResponse {

    private Long id;
    private AuthorResponse author;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
    private String content;

    public CommentResponse(CommentData comment) {
        this(comment.getId(), new AuthorResponse(comment.getMember()), comment.getCreatedDate(),
                comment.getLastModifiedDate(), comment.getContent());
    }
}
