package com.prokectB.meongbti.member.service;

import com.prokectB.meongbti.common.exception.notfound.MemberNotFoundException;
import com.prokectB.meongbti.member.entity.Member;
import com.prokectB.meongbti.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReadService {

    private final MemberRepository memberRepository;

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
