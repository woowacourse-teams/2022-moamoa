package com.woowacourse.moamoa;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.moamoa.auth.config.AuthRequestMatchConfig;
import com.woowacourse.moamoa.auth.controller.AuthenticationInterceptor;
import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.common.MockedServiceObjectsBeanRegister;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.referenceroom.service.ReferenceRoomService;
import com.woowacourse.moamoa.referenceroom.service.SearchingReferenceRoomService;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

@WebMvcTest(includeFilters = @Filter(type = FilterType.ANNOTATION, classes = RestController.class))
@Import({JwtTokenProvider.class, AuthRequestMatchConfig.class, MockedServiceObjectsBeanRegister.class})
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
    private MemberRepository memberRepository;

    @MockBean
    protected ReferenceRoomService referenceRoomService;

    @MockBean
    protected SearchingReferenceRoomService searchingReferenceRoomService;

    @MockBean
    protected HttpServletRequest httpServletRequest;

    @MockBean
    protected NativeWebRequest nativeWebRequest;

    @BeforeEach
    void setUp() {
        when(memberRepository.findByGithubId(any()))
                .thenReturn(Optional.of(new Member(1L, 1L, "username", "image", "profile")));
    }
}
