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
    public MemberResponse saveOrUpdate(final Member member) {
        final Optional<Member> foundMember = memberRepository.findByGithubId(member.getGithubId());

        if (foundMember.isPresent()) {
            foundMember.get().update(
                    member.getUsername(), member.getEmail(), member.getImageUrl(), member.getProfileUrl()
            );
            return new MemberResponse(foundMember.get());
        }

        return new MemberResponse(memberRepository.save(member));
    }

    public MemberResponse getByMemberId(final Long memberId) {
        final MemberData member = memberDao.findByMemberId(memberId)
                .orElseThrow(MemberNotFoundException::new);
        return new MemberResponse(member.getId(), member.getUsername(),
                member.getProfileUrl(), member.getImageUrl());
    }
}
