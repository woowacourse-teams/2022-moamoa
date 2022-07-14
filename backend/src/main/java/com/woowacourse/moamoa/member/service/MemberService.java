package com.woowacourse.moamoa.member.service;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
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

    public void saveOrUpdate(final Member member) {
        memberRepository.findByUsername(member.getUsername())
                .ifPresentOrElse(findMember -> findMember.updateProfileImageUrl(member.getImageUrl()),
        () -> memberRepository.save(member));
    }

    public Optional<Member> findByUsername(final String username) {
        return memberRepository.findByUsername(username);
    }
}
