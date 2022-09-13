package com.woowacourse.moamoa.auth.domain.repository;

import com.woowacourse.moamoa.auth.domain.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByMemberId(Long memberId);
}
