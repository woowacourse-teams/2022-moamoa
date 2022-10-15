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

    /* 그린론 */
    public static final Long 그린론_아이디 = 2L;
    public static final Long 그린론_깃허브_아이디 = 2L;
    public static final String 그린론_유저네임 = "greenlawn";
    public static final String 그린론_이미지 = "https://greenlawn.png";
    public static final String 그린론_프로필 = "https://greenlawn.com";

    /* 디우 */
    public static final Long 디우_아이디 = 3L;
    public static final Long 디우_깃허브_아이디 = 3L;
    public static final String 디우_유저네임 = "dwoo";
    public static final String 디우_이미지 = "https://dwoo.png";
    public static final String 디우_프로필 = "https://dwoo.com";

    /* 베루스 */
    public static final Long 베루스_아이디 = 4L;
    public static final Long 베루스_깃허브_아이디 = 4L;
    public static final String 베루스_유저네임 = "verus";
    public static final String 베루스_이미지 = "https://verus.png";
    public static final String 베루스_프로필 = "https://verus.com";

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


    public static MemberData 짱구_응답(final Long id) {
        return 응답(id, 짱구_유저네임, 짱구_이미지, 짱구_프로필);
    }

    public static MemberData 그린론_응답(final Long id) {
        return 응답(id, 그린론_유저네임, 그린론_이미지, 그린론_프로필);
    }

    public static MemberData 디우_응답(final Long id) {
        return 응답(id, 디우_유저네임, 디우_이미지, 디우_프로필);
    }

    public static MemberData 베루스_응답(final Long id) {
        return 응답(id, 베루스_유저네임, 베루스_이미지, 베루스_프로필);
    }

    private static MemberData 응답(final Long id, final String name, final String image, final String profile) {
        return new MemberData(id, name, image, profile);
    }
}
