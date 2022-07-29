package com.woowacourse.moamoa.member.domain.repository;

import com.woowacourse.moamoa.member.domain.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findByGithubId(Long githubId);

    List<Member> findAllById(Iterable<Long> ids);

    boolean existsByGithubId(Long githubId);
}
