package com.prokectB.meongbti.member;

import com.prokectB.meongbti.exception.badrequest.DuplicatedUserException;
import com.prokectB.meongbti.dto.request.member.UserJoinRequest;
import com.prokectB.meongbti.dto.response.member.UserResponse;
import com.prokectB.meongbti.domain.Member.entity.Member;
import com.prokectB.meongbti.domain.Member.repository.MemberRepository;
import com.prokectB.meongbti.domain.Member.service.MemberWriteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserWriteServiceTest {

    @InjectMocks
    private MemberWriteService memberWriteService  ;

    @Mock
    private MemberRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    @DisplayName("회원가입이 완료되어야한다.")
    void join_shouldReturnCreatedUserResponse() {
        UserJoinRequest userJoinRequest = new UserJoinRequest("testuser@gmail.com", "password", "testuser");
        String encodedPassword = "encodedPassword";
        String nickname = "testuser";
        Member newUser = Member.create(userJoinRequest.getEmail(), encodedPassword, nickname);
        Member savedUser = Member.create("testuser@gmail.com", encodedPassword, nickname);
        when(passwordEncoder.encode(userJoinRequest.getPassword())).thenReturn(encodedPassword);
        when(userRepository.findByNickname("testuser")).thenReturn(Optional.empty());
        when(userRepository.save(any(Member.class))).thenReturn(savedUser);

        UserResponse userResponse = memberWriteService.join(userJoinRequest);

        assertEquals(newUser.getEmail(), userResponse.getEmail());
        assertEquals(newUser.getNickname(), userResponse.getNickname());
    }

    @Test
    @DisplayName("중복된 닉네임이 존재하면 DuplicatedUserException이 발생한다.")
    void join_duplicatedUser() {
        UserJoinRequest userJoinRequest = new UserJoinRequest("testuser@gmail.com", "password", "testuser");

        Member duplicatedUser = Member.create("testuser@gmail.com", "passwrod", "testuser");
        when(userRepository.findByNickname("testuser")).thenReturn(Optional.of(duplicatedUser));

        assertThrows(DuplicatedUserException.class, () -> memberWriteService.join(userJoinRequest));
    }

    @Test
    @DisplayName("중복된 이메일 존재하면 DuplicatedUserException이 발생한다.")
    void join_EmailduplicatedUser() {
        UserJoinRequest userJoinRequest = new UserJoinRequest("testuser@gmail.com", "password", "testuser");

        Member duplicatedUser = Member.create("testuser@gmail.com", "passwrod", "testuser");
        when(userRepository.findByEmail("testuser@gmail.com")).thenReturn(Optional.of(duplicatedUser));

        assertThrows(DuplicatedUserException.class, () -> memberWriteService.join(userJoinRequest));
    }
}