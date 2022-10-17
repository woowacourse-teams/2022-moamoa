package com.woowacourse.moamoa.studyroom.service.request;

import com.woowacourse.moamoa.studyroom.domain.article.Content;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class ArticleRequest {

    @NotBlank(message = "내용을 입력해 주세요.")
    @Length(max = 30)
    private String title;

    @NotBlank(message = "내용을 입력해 주세요.")
    @Length(max = 50_000)
    private String content;

    public ArticleRequest(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public Content createContent() {
        return new Content(title, content);
    }
}
