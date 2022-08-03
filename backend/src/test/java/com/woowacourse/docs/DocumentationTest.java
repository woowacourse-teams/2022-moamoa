package com.woowacourse.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.moamoa.MoamoaApplication;
import com.woowacourse.moamoa.auth.config.AuthConfig;
import com.woowacourse.moamoa.auth.controller.AuthController;
import com.woowacourse.moamoa.auth.controller.AuthenticationArgumentResolver;
import com.woowacourse.moamoa.auth.controller.AuthenticationInterceptor;
import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.auth.service.AuthService;
import com.woowacourse.moamoa.member.controller.MemberController;
import com.woowacourse.moamoa.member.service.MemberService;
import com.woowacourse.moamoa.review.controller.ReviewController;
import com.woowacourse.moamoa.review.controller.SearchingReviewController;
import com.woowacourse.moamoa.review.service.ReviewService;
import com.woowacourse.moamoa.review.service.SearchingReviewService;
import com.woowacourse.moamoa.study.controller.SearchingStudyController;
import com.woowacourse.moamoa.study.controller.StudyController;
import com.woowacourse.moamoa.study.service.SearchingStudyService;
import com.woowacourse.moamoa.study.service.StudyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(value = {
        AuthController.class,
        StudyController.class,
        SearchingStudyController.class,
        ReviewController.class,
        SearchingReviewController.class,
        MemberController.class
})
@ContextConfiguration(classes = {MoamoaApplication.class})
@ExtendWith(RestDocumentationExtension.class)
@Import(JwtTokenProvider.class)
public class DocumentationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected TokenProvider tokenProvider;

    @MockBean
    protected AuthConfig authConfig;

    @MockBean
    protected AuthenticationInterceptor authenticationInterceptor;

    @MockBean
    protected AuthenticationArgumentResolver authenticationArgumentResolver;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected StudyService studyService;

    @MockBean
    protected SearchingStudyService searchingStudyService;

    @MockBean
    protected ReviewService reviewService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected SearchingReviewService searchingReviewService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }
}
