package com.prokectB.meongbti.member.service;

import com.prokectB.meongbti.common.exception.badrequest.DuplicatedUserException;
import com.prokectB.meongbti.member.controller.request.UserJoinRequest;
import com.prokectB.meongbti.member.dto.response.UserResponse;
import com.prokectB.meongbti.member.entity.Member;
import com.prokectB.meongbti.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberWriteService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse join(UserJoinRequest userJoinRequest) {
        validateDuplicateEmail(userJoinRequest.getEmail());
        validateDuplicateNickname(userJoinRequest.getNickname());
        String encodedPassword = passwordEncoder.encode(userJoinRequest.getPassword());

        Member newUser = Member.create(userJoinRequest.getEmail(), encodedPassword, userJoinRequest.getNickname());
        memberRepository.save(newUser);
        return UserResponse.create(newUser);
    }

    private void validateDuplicateEmail(String email) {
        Optional<Member> DuplicatedUser = memberRepository.findByEmail(email);
        if (DuplicatedUser.isPresent()) {
            throw new DuplicatedUserException();
        }
    }

    private void validateDuplicateNickname(String nickName) {
        Optional<Member> DuplicatedUser = memberRepository.findByNickname(nickName);
        if (DuplicatedUser.isPresent()) {
            throw new DuplicatedUserException();
        }
    }
}