package com.prokectB.meongbti.common.exception.notfound;

import com.prokectB.meongbti.common.configuration.advice.CustomException;

public class NotFoundException extends CustomException {

    private static final String MESSAGE = "리소스를 찾을 수 없습니다.";

    private static final int STATUS_CODE = 404;

    public NotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return STATUS_CODE;
    }
}