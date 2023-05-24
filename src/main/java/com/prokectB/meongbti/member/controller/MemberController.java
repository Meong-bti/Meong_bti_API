package com.prokectB.meongbti.member.controller;

import com.prokectB.meongbti.member.controller.request.UserJoinRequest;
import com.prokectB.meongbti.member.dto.response.UserResponse;
import com.prokectB.meongbti.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberController {
    private final MemberWriteService memberWriteService;

    @PostMapping
    public UserResponse join(@Validated @RequestBody UserJoinRequest userJoinRequest) {
        return memberWriteService.join(userJoinRequest);
    }
}
