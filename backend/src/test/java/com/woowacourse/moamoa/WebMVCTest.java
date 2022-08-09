package com.woowacourse.moamoa;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woowacourse.moamoa.auth.config.AuthRequestMatchConfig;
import com.woowacourse.moamoa.auth.controller.AuthController;
import com.woowacourse.moamoa.auth.controller.AuthenticationInterceptor;
import com.woowacourse.moamoa.auth.controller.matcher.AuthenticationRequestMatcher;
import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.auth.service.AuthService;
import com.woowacourse.moamoa.member.controller.MemberController;
import com.woowacourse.moamoa.member.service.MemberService;
import com.woowacourse.moamoa.review.controller.ReviewController;
import com.woowacourse.moamoa.review.controller.SearchingReviewController;
import com.woowacourse.moamoa.review.service.ReviewService;
import com.woowacourse.moamoa.review.service.SearchingReviewService;
import com.woowacourse.moamoa.study.controller.MyStudyController;
import com.woowacourse.moamoa.study.service.MyStudyService;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.NativeWebRequest;

@WebMvcTest({
        MyStudyController.class,
        ReviewController.class,
        SearchingReviewController.class,
        MemberController.class,
        MyStudyController.class,
        AuthController.class
})
@Import({JwtTokenProvider.class, AuthRequestMatchConfig.class})
public abstract class WebMVCTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected TokenProvider tokenProvider;

    @Autowired
    protected AuthRequestMatchConfig authRequestMatchConfig;

    @Autowired
    protected AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ReviewService reviewService;

    @MockBean
    protected SearchingReviewService searchingReviewService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected MyStudyService myStudyService;

    @MockBean
    protected HttpServletRequest httpServletRequest;

    @MockBean
    protected NativeWebRequest nativeWebRequest;
}
