package com.prokectB.meongbti.domain.Member.service;

import com.prokectB.meongbti.exception.badrequest.DuplicatedUserException;
import com.prokectB.meongbti.dto.request.member.UserJoinRequest;
import com.prokectB.meongbti.dto.response.member.UserResponse;
import com.prokectB.meongbti.domain.Member.entity.Member;
import com.prokectB.meongbti.domain.Member.repository.MemberRepository;
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