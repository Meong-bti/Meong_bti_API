package com.prokectB.meongbti.common.exception.badrequest;

public class DuplicatedUserException extends InvalidRequestException {

    public DuplicatedUserException() {
        addValidation("nickname", "중복된 닉네임이 존재합니다.");
    }
}