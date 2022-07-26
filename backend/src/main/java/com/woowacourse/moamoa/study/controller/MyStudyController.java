package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.query.data.MyStudyData;
import com.woowacourse.moamoa.study.service.MyStudyService;
import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyStudyController {

    private final MyStudyService myStudyService;

    @GetMapping("/api/my/studies")
    public ResponseEntity<MyStudiesResponse> getMyStudies(@AuthenticationPrincipal Long githubId) {

        final MyStudiesResponse myStudiesResponse = new MyStudiesResponse(
                List.of(
                        new MyStudyData(1L, "Java 스터디", "IN_PROGRESS", 3, 10,
                                "2022-07-26", "2022-08-26",
                                new MemberData(2L, "jaejae-yoo", "images/123", "https://github.com/user/jaejae-yoo"),
                                List.of(new TagSummaryData(1L, "BE"), new TagSummaryData(5L, "Java")
                                )
                        )
                ));

        return ResponseEntity.ok().body(myStudiesResponse);
    }
}
