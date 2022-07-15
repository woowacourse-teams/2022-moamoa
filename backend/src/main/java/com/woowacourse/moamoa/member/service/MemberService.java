package com.woowacourse.moamoa.member.service;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void saveOrUpdate(final Member member) {
        memberRepository.findByGithubId(member.getGithubId())
                .ifPresentOrElse(findMember -> findMember.updateProfileImageUrl(member.getImageUrl()),
        () -> memberRepository.save(member));
    }

    public MemberResponse searchBy(final Long githubId) {
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));
        return MemberResponse.from(member);
    }
}
