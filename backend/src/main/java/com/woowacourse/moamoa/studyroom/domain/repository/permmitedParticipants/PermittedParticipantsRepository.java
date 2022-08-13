package com.woowacourse.moamoa.studyroom.domain.repository.permmitedParticipants;

import com.woowacourse.moamoa.studyroom.domain.PermittedParticipants;
import java.util.Optional;

public interface PermittedParticipantsRepository {

    Optional<PermittedParticipants> findByStudyId(Long studyId);
}
