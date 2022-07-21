package com.woowacourse.moamoa.auth.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.moamoa.auth.service.AuthService;
import com.woowacourse.moamoa.auth.service.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.oauthclient.response.OAuthAccessTokenResponse;
import com.woowacourse.moamoa.auth.service.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class FakeAuthController {

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthService authService;

    @PostMapping("/api/fake/login/token")
    public ResponseEntity<TokenResponse> fakeLogin() throws JsonProcessingException {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        final OAuthAccessTokenResponse accessToken = new OAuthAccessTokenResponse("access-token", "", "");

        mockServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(accessToken)));

        final GithubProfileResponse profileResponse = new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com");

        mockServer.expect(requestTo("https://api.github.com/user"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(profileResponse)));

        final TokenResponse jwtToken = new TokenResponse("jwt-token");

        mockServer.expect(requestTo("http://localhost:8080/api/login/token"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(jwtToken)));

        final TokenResponse expected = authService.createToken("jwt");

        return ResponseEntity.ok().body(expected);
    }
}
