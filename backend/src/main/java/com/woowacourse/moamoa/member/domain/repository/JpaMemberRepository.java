package com.woowacourse.moamoa.member.domain.repository;

import com.woowacourse.moamoa.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
}
