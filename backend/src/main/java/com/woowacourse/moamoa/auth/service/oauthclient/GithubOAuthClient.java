package com.woowacourse.moamoa.auth.service.oauthclient;
import com.woowacourse.moamoa.auth.service.oauthclient.exception.InvalidMemberException;
import com.woowacourse.moamoa.auth.service.request.AccessTokenRequest;
import com.woowacourse.moamoa.auth.service.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.oauthclient.response.OAuthAccessTokenResponse;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
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

    @Override
    public String getAccessToken(final String code) {
        final AccessTokenRequest accessTokenRequest = new AccessTokenRequest(clientId, clientSecret, code);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        final HttpEntity<AccessTokenRequest> httpEntity = new HttpEntity<>(accessTokenRequest, headers);
        final OAuthAccessTokenResponse accessTokenResponse = restTemplate.exchange(
                ACCESS_TOKEN_URL, HttpMethod.POST, httpEntity, OAuthAccessTokenResponse.class
        ).getBody();

        if (Objects.isNull(accessTokenResponse)) {
            throw new InvalidMemberException("Access Token을 가져올 수 없습니다.");
        }
        return accessTokenResponse.getAccessToken();
    }

    @Override
    public GithubProfileResponse getProfile(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "token " + accessToken);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(PROFILE_URL, HttpMethod.GET, httpEntity, GithubProfileResponse.class)
                .getBody();
    }
}
