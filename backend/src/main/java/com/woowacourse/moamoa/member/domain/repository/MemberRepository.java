package com.woowacourse.moamoa.member.domain.repository;

import com.woowacourse.moamoa.member.domain.Member;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findByUsername(String username);
}
