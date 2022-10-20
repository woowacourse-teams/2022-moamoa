package com.woowacourse.moamoa.comment.service.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EditingCommentRequest {

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;
}
