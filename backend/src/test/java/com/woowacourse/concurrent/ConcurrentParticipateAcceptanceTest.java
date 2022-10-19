package com.woowacourse.concurrent;

import static com.woowacourse.acceptance.steps.LoginSteps.사용자가;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.alarm.SlackAlarmSender;
import com.woowacourse.moamoa.alarm.SlackUserProfile;
import com.woowacourse.moamoa.alarm.SlackUsersClient;
import com.woowacourse.moamoa.alarm.response.SlackUserResponse;
import com.woowacourse.moamoa.auth.service.oauthclient.OAuthClient;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

class ConcurrentParticipateAcceptanceTest extends AcceptanceTest{

    @MockBean
    private OAuthClient oAuthClient;

    @MockBean
    private SlackAlarmSender slackAlarmSender;

    @MockBean
    private SlackUsersClient slackUsersClient;

    @DisplayName("동시에 다수의 사용자가 스터디에 가입한다.")
    @Test
    void concurrentlyParticipateStudy() {
        // arrange
        final List<GithubProfileResponse> profiles = getProfiles(0, 100);
        final GithubProfileResponse 방장 = profiles.get(0);
        final List<GithubProfileResponse> 참여를_원하는_사용자 = profiles.subList(1, 101);
        final long 스터디_ID = 사용자가(방장).로그인하고().자바_스터디를().시작일자는(LocalDate.now()).모집인원은(21).생성한다();

        final List<SlackUserResponse> users = profiles.stream()
                .map(user -> new SlackUserResponse(user.getUsername(), new SlackUserProfile(user.getEmail())))
                .collect(Collectors.toList());

        for (SlackUserResponse profile : users) {
            when(slackUsersClient.getUserChannelByEmail(profile.getSlackUserProfile().getEmail())).thenReturn(profile.getChannel());
            doNothing().when(slackAlarmSender).requestSlackMessage(profile.getChannel());
        }

        // act
        final ConcurrentHttpRequester requester = new ConcurrentHttpRequester(참여를_원하는_사용자.size());
        for (GithubProfileResponse 프로필 : 참여를_원하는_사용자) {
            requester.submit(() -> 사용자가(프로필).로그인하고().스터디에(스터디_ID).참여를_시도한다());
        }
        requester.await();

        // assert
        assertThat(requester.getSuccessUser()).isEqualTo(20);
        assertThat(requester.getFailUser()).isEqualTo(80);
        assertThat(getCurrentMemberCount(스터디_ID)).isEqualTo(21);
    }

    @DisplayName("동시에 다수의 사용자가 스터디를 탈퇴한다.")
    @Test
    void concurrentlyLeaveStudy() {
        // arrange
        final List<GithubProfileResponse> profiles = getProfiles(0, 100);
        final GithubProfileResponse 방장 = profiles.get(0);
        final List<GithubProfileResponse> 탈퇴를_원하는_사용자 = profiles.subList(1, 101);
        final long 스터디_ID = 사용자가(방장).로그인하고().자바_스터디를().시작일자는(LocalDate.now()).생성한다();
        final List<SlackUserResponse> users = profiles.stream()
                .map(user -> new SlackUserResponse(user.getUsername(), new SlackUserProfile(user.getEmail())))
                .collect(Collectors.toList());

        for (SlackUserResponse profile : users) {
            when(slackUsersClient.getUserChannelByEmail(profile.getSlackUserProfile().getEmail())).thenReturn(profile.getChannel());
            doNothing().when(slackAlarmSender).requestSlackMessage(profile.getChannel());
        }

        for (GithubProfileResponse 프로필 : 탈퇴를_원하는_사용자) {
            사용자가(프로필).로그인하고().스터디에(스터디_ID).참여를_시도한다();
        }

        // act
        final ConcurrentHttpRequester requester = new ConcurrentHttpRequester(탈퇴를_원하는_사용자.size());
        for (GithubProfileResponse 프로필 : 탈퇴를_원하는_사용자) {
            requester.submit(() -> 사용자가(프로필).로그인하고().스터디에(스터디_ID).탈퇴를_시도한다());
        }
        requester.await();

        // assert
        assertThat(requester.getSuccessUser()).isEqualTo(100);
        assertThat(requester.getFailUser()).isEqualTo(0);
        assertThat(getCurrentMemberCount(스터디_ID)).isEqualTo(1);
    }

    @DisplayName("동시에 다수의 사용자가 스터디를 가입/탈퇴한다.")
    @Test
    void concurrentlyParticipateOrLeaveStudy() {
        // arrange
        final List<GithubProfileResponse> profiles = getProfiles(0, 100);
        final GithubProfileResponse 방장 = profiles.get(0);
        final List<GithubProfileResponse> 탈퇴를_원하는_사용자 = profiles.subList(1, 50);
        final List<GithubProfileResponse> 가입을_원하는_사용자 = profiles.subList(50, 101);
        final long 스터디_ID = 사용자가(방장).로그인하고().자바_스터디를().시작일자는(LocalDate.now()).생성한다();
        final List<SlackUserResponse> users = 가입을_원하는_사용자.stream()
                .map(user -> new SlackUserResponse(user.getUsername(), new SlackUserProfile(user.getEmail())))
                .collect(Collectors.toList());

        for (SlackUserResponse profile : users) {
            when(slackUsersClient.getUserChannelByEmail(profile.getSlackUserProfile().getEmail())).thenReturn(profile.getChannel());
            doNothing().when(slackAlarmSender).requestSlackMessage(profile.getChannel());
        }

        for (GithubProfileResponse 프로필 : 탈퇴를_원하는_사용자) {
            사용자가(프로필).로그인하고().스터디에(스터디_ID).참여를_시도한다();
        }

        // act
        final ConcurrentHttpRequester requester = new ConcurrentHttpRequester(
                탈퇴를_원하는_사용자.size() + 가입을_원하는_사용자.size()
        );

        for (GithubProfileResponse 프로필: 가입을_원하는_사용자) {
            requester.submit(() -> 사용자가(프로필).로그인하고().스터디에(스터디_ID).참여를_시도한다());
        }

        for (GithubProfileResponse 프로필 : 탈퇴를_원하는_사용자) {
            requester.submit(() -> 사용자가(프로필).로그인하고().스터디에(스터디_ID).탈퇴를_시도한다());
        }
        requester.await();

        // assert
        assertThat(requester.getSuccessUser()).isEqualTo(100);
        assertThat(requester.getFailUser()).isEqualTo(0);
        assertThat(getCurrentMemberCount(스터디_ID)).isEqualTo(52);
    }

    private List<GithubProfileResponse> getProfiles(int start, int end) {
        List<GithubProfileResponse> profiles = new ArrayList<>();
        for (long id = start; id <= end; id++) {
            profiles.add(new GithubProfileResponse(id, "username" + id, "moa" + id + "@moamoa.space",
                    "https://image", "profile"));
        }

        for (GithubProfileResponse profile : profiles) {
            when(oAuthClient.getProfile("Authorization Code" + profile.getGithubId())).thenReturn(profile);
        }
        return profiles;
    }

    private int getCurrentMemberCount(final long studyId) {
        return RestAssured.given().log().all()
                .pathParam("study-id", studyId)
                .when().log().all()
                .get("/api/studies/{study-id}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getInt("currentMemberCount");
    }
}
