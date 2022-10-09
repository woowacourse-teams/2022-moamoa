package com.woowacourse.acceptance.fixture;

import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;

public class MemberFixtures {

    public static final long 짱구_깃허브_ID = 1L;
    public static final String 짱구_이름 = "jjanggu";
    public static final String 짱구_이메일 = "jjanggu@moamoa.space";
    public static final String 짱구_이미지_URL = "https://jjanggu";
    public static final String 짱구_프로필_URL = "github.com/jjanggu";

    public static final long 그린론_깃허브_ID = 2L;
    public static final String 그린론_이름 = "greenlawn";
    public static final String 그린론_이메일 = "greenlawn@moamoa.space";
    public static final String 그린론_이미지_URL = "https://greenlawn";
    public static final String 그린론_프로필_URL = "github.com/greenlawn";

    public static final long 디우_깃허브_ID = 3L;
    public static final String 디우_이름 = "dwoo";
    public static final String 디우_이메일 = "dwo@moamoa.space";
    public static final String 디우_이미지_URL = "https://dwoo";
    public static final String 디우_프로필_URL = "github.com/dwoo";

    public static final long 베루스_깃허브_ID = 4L;
    public static final String 베루스_이름 = "verus";
    public static final String 베루스_이메일 = "verus@moamoa.space";
    public static final String 베루스_이미지_URL = "https://verus";
    public static final String 베루스_프로필_URL = "github.com/verus";

    public static final GithubProfileResponse 짱구_깃허브_프로필 =
            new GithubProfileResponse(짱구_깃허브_ID, 짱구_이름, 짱구_이메일, 짱구_이미지_URL, 짱구_프로필_URL);

    public static final GithubProfileResponse 그린론_깃허브_프로필 =
            new GithubProfileResponse(그린론_깃허브_ID, 그린론_이름, 그린론_이메일, 그린론_이미지_URL, 그린론_프로필_URL);

    public static final GithubProfileResponse 디우_깃허브_프로필 =
            new GithubProfileResponse(디우_깃허브_ID, 디우_이름, 디우_이메일, 디우_이미지_URL, 디우_프로필_URL);

    public static final GithubProfileResponse 베루스_깃허브_프로필 =
            new GithubProfileResponse(베루스_깃허브_ID, 베루스_이름, 베루스_이메일, 베루스_이미지_URL, 베루스_프로필_URL);
}
