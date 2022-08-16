package com.woowacourse.moamoa.auth.infrastructure;

import com.woowacourse.moamoa.auth.service.oauthclient.OAuthClient;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.auth.service.request.AccessTokenRequest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubOAuthClient implements OAuthClient {

    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String PROFILE_URL = "https://api.github.com/user";

    private final String clientId;
    private final String clientSecret;
    private final RestTemplate restTemplate;

    public GithubOAuthClient(
            @Value("${oauth2.github.client-id}") final String clientId,
            @Value("${oauth2.github.client-secret}") final String clientSecret,
            final RestTemplate restTemplate) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.restTemplate = restTemplate;
    }

    /*
        Github에서 유효하지 않는 Authorization Code로 Access Token을 요청 시 200 응답이 오고,
        body로 error, error_description, error_uri가 담겨서 온다.
        에러 처리를 위해서는 body의 필드를 확인하여 처리해야 하므로 Map을 사용했다.
        Access Token 요청이 정상적인 경우 body로 access_token, token_type, scope를 응답한다.
     */

    @SuppressWarnings("unchecked")
    @Override
    public String getAccessToken(final String code) {
        final AccessTokenRequest accessTokenRequest = new AccessTokenRequest(clientId, clientSecret, code);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        final HttpEntity<AccessTokenRequest> httpEntity = new HttpEntity<>(accessTokenRequest, headers);

        try {
            final ResponseEntity<?> response = restTemplate.exchange(
                    ACCESS_TOKEN_URL, HttpMethod.POST, httpEntity, Map.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                Map<Object, Object> body = (Map<Object, Object>) Objects.requireNonNull(response.getBody());
                if (body.containsKey("error")) {
                    throw new UnauthorizedException("Access Token을 가져올 수 없습니다.");
                }
                return String.valueOf(body.get("access_token"));
            }
            throw new UnauthorizedException("Access Token을 가져올 수 없습니다.");
        } catch (HttpClientErrorException e) {
            throw new UnauthorizedException("Access Token을 가져올 수 없습니다.");
        }
    }

    @Override
    public GithubProfileResponse getProfile(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "token " + accessToken);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        try {
            final ResponseEntity<GithubProfileResponse> response = restTemplate.exchange(
                    PROFILE_URL, HttpMethod.GET, httpEntity, GithubProfileResponse.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return Objects.requireNonNull(response.getBody());
            }
            throw new UnauthorizedException("Github에서 사용자 정보를 가져올 수 없습니다.");
        } catch (HttpClientErrorException e) {
            throw new UnauthorizedException("Github에서 사용자 정보를 가져올 수 없습니다.");
        }
    }
}
