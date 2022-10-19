package com.woowacourse.moamoa.studyroom.service.response;

import com.woowacourse.moamoa.studyroom.service.response.temp.TempArticleResponse;
import java.util.Iterator;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TempArticlesResponse implements Iterable<TempArticleResponse> {

    private List<TempArticleResponse> draftArticles;
    private int currentPage;
    private int lastPage;
    private long totalCount;


    public TempArticlesResponse(
            final List<TempArticleResponse> draftArticles, final int currentPage,
            final int lastPage, final long totalCount
    ) {
        this.draftArticles = draftArticles;
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.totalCount = totalCount;
    }

    @Override
    public Iterator<TempArticleResponse> iterator() {
        return draftArticles.iterator();
    }
}
