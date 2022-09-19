package com.woowacourse.moamoa.fixtures;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.query.data.MemberData;

public class MemberFixtures {

    /* 짱구 */
    public static final Long 짱구_아이디 = 1L;
    public static final Long 짱구_깃허브_아이디 = 1L;
    public static final String 짱구_유저네임 = "jjanggu";
    public static final String 짱구_이미지 = "https://jjanggu.png";
    public static final String 짱구_프로필 = "https://jjanggu.com";
    public static final MemberData 짱구_응답 = new MemberData(짱구_깃허브_아이디, 짱구_유저네임, 짱구_이미지, 짱구_프로필);

    /* 그린론 */
    public static final Long 그린론_아이디 = 2L;
    public static final Long 그린론_깃허브_아이디 = 2L;
    public static final String 그린론_유저네임 = "greenlawn";
    public static final String 그린론_이미지 = "https://greenlawn.png";
    public static final String 그린론_프로필 = "https://greenlawn.com";
    public static final MemberData 그린론_응답 = new MemberData(그린론_깃허브_아이디, 그린론_유저네임, 그린론_이미지, 그린론_프로필);

    /* 디우 */
    public static final Long 디우_아이디 = 3L;
    public static final Long 디우_깃허브_아이디 = 3L;
    public static final String 디우_유저네임 = "dwoo";
    public static final String 디우_이미지 = "https://dwoo.png";
    public static final String 디우_프로필 = "https://dwoo.com";
    public static final MemberData 디우_응답 = new MemberData(디우_깃허브_아이디, 디우_유저네임, 디우_이미지, 디우_프로필);

    /* 베루스 */
    public static final Long 베루스_아이디 = 4L;
    public static final Long 베루스_깃허브_아이디 = 4L;
    public static final String 베루스_유저네임 = "verus";
    public static final String 베루스_이미지 = "https://verus.png";
    public static final String 베루스_프로필 = "https://verus.com";
    public static final MemberData 베루스_응답 = new MemberData(베루스_깃허브_아이디, 베루스_유저네임, 베루스_이미지, 베루스_프로필);

    /* 병민 */
    public static final Long 병민_깃허브_아이디 = 5L;
    public static final String 병민_유저네임 = "airman";
    public static final String 병민_이미지 = "https://airman.png";
    public static final String 병민_프로필 = "https://airman.com";

    /* 태태 */
    public static final Long 태태_깃허브_아이디 = 6L;
    public static final String 태태_유저네임 = "nannoo";
    public static final String 태태_이미지 = "https://nannoo.png";
    public static final String 태태_프로필 = "https://nannoo.com";

    public static Member 짱구() {
        return new Member(짱구_깃허브_아이디, 짱구_유저네임, 짱구_이미지, 짱구_프로필);
    }

    public static Member 그린론() {
        return new Member(그린론_깃허브_아이디, 그린론_유저네임, 그린론_이미지, 그린론_프로필);
    }

    public static Member 디우() {
        return new Member(디우_깃허브_아이디, 디우_유저네임, 디우_이미지, 디우_프로필);
    }

    public static Member 베루스() {
        return new Member(베루스_깃허브_아이디, 베루스_유저네임, 베루스_이미지, 베루스_프로필);
    }

    public static Member 병민() {
        return new Member(병민_깃허브_아이디, 병민_유저네임, 병민_이미지, 병민_프로필);
    }

    public static Member 태태() {
        return new Member(태태_깃허브_아이디, 태태_유저네임, 태태_이미지, 태태_프로필);
    }
}
