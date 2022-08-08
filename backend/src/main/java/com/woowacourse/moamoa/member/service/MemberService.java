package com.woowacourse.moamoa.member.service;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.MemberDao;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final MemberDao memberDao;

    @Transactional
    public void saveOrUpdate(final Member member) {
        final Optional<Member> foundMember = memberRepository.findByGithubId(member.getGithubId());

        foundMember.ifPresent(
                m -> m.update(member.getUsername(), member.getImageUrl(), member.getProfileUrl())
        );

        memberRepository.save(member);
    }

    public MemberResponse getByGithubId(final Long githubId) {
        final MemberData member = memberDao.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);

        return new MemberResponse(member.getGithubId(), member.getUsername(), member.getProfileUrl(),
                member.getImageUrl());
    }
}
