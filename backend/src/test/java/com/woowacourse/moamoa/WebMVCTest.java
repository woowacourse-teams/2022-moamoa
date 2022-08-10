package com.woowacourse.moamoa;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woowacourse.moamoa.auth.config.AuthRequestMatchConfig;
import com.woowacourse.moamoa.auth.controller.AuthenticationInterceptor;
import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.common.MockedServiceObjectsBeanRegister;

import javax.servlet.http.HttpServletRequest;
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
}
