package com.prokectB.meongbti.common.exception.unauthorized;

import com.prokectB.meongbti.configuration.advice.CustomException;

public class UnauthorizedException extends CustomException {

    private static final String MESSAGE = "인증되지 않은 사용자입니다.";

    private static final int STATUS_CODE = 401;

    public UnauthorizedException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return STATUS_CODE;
    }
}