package com.woowacourse.moamoa.member.service;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void saveOrUpdate(final Member member) {
        memberRepository.findByGithubId(member.getGithubId())
                .ifPresentOrElse(findMember -> findMember.update(member.getUsername(), member.getImageUrl(), member.getProfileUrl()),
        () -> memberRepository.save(member));
    }

    public MemberData searchBy(final Long githubId) {
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        return new MemberData(member.getGithubId(), member.getUsername(), member.getImageUrl(), member.getProfileUrl());
    }
}
