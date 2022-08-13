package com.woowacourse.moamoa.studyroom.domain.repository.permmitedParticipants;

import com.woowacourse.moamoa.studyroom.domain.PermittedParticipants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPermittedParticipantsRepository extends JpaRepository<PermittedParticipants, Long>,
        PermittedParticipantsRepository {
}
