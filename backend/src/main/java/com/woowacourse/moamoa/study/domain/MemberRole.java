package com.woowacourse.moamoa.study.domain;

public enum MemberRole {

    MEMBER, NON_MEMBER, OWNER;

    public boolean isOwner() {
        return this == OWNER;
    }

    public boolean isMember() {
        return this == MEMBER;
    }

    public boolean isNonMember() {
        return this == NON_MEMBER;
    }
}
