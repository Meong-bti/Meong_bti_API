package com.prokectB.meongbti.common.exception.badrequest;

public class NotPostWriterException extends InvalidRequestException {

    public NotPostWriterException() {
        addValidation("user", "글 작성자가 아닙니다.");
    }
}
